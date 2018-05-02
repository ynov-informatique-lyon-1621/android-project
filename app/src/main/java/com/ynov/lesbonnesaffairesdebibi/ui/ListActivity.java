package com.ynov.lesbonnesaffairesdebibi.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.adapter.AnnonceAdapter;
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;
import com.ynov.lesbonnesaffairesdebibi.service.HttpService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends BaseActivity {

    private List<Annonce> annonces;

    private ListView listAnnonces = null;
    private ProgressBar listLoader = null;
    private Button btnFilters = null;
    private Spinner filterCategory = null;

    private HttpService httpService;
    Call<List<Annonce>> call;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Liste des annonces");

        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_list, contentLayout);

        listAnnonces = findViewById(R.id.listAnnonces);
        listLoader = findViewById(R.id.listLoader);

        btnFilters = findViewById(R.id.btnFilters);
        final Button btnApplyFilters = findViewById(R.id.btnApplyFilters);

        final EditText filterKeywords = findViewById(R.id.filterKeywords);
        filterCategory = findViewById(R.id.filterCategory);
        final EditText filterLocation = findViewById(R.id.filterLocation);

        btnFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFilters();
            }
        });

        btnApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFilters();
                listAnnonces.setAdapter(null);
                annonces.clear();

                listLoader.setVisibility(View.VISIBLE);
                loadAnnonces(filterKeywords.getText().toString(),
                        filterCategory.getSelectedItemPosition() != 0 ? filterCategory.getSelectedItem().toString() : "",
                        filterLocation.getText().toString());
            }
        });

        listAnnonces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra("data", (Serializable) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });

        httpService = new Retrofit.Builder().baseUrl(HttpService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(HttpService.class);

        loadAnnonces("", "", "");
    }

    public void loadAnnonces(final String keywords, final String category, final String location) {

        call = httpService.findAnnonces(keywords, category, location);

        call.enqueue(new Callback<List<Annonce>>() {

            @Override
            public void onResponse(Call<List<Annonce>> call, Response<List<Annonce>> response) {

                listLoader.setVisibility(View.GONE);
                annonces = response.body();

                if(TextUtils.isEmpty(keywords) && TextUtils.isEmpty(category) && TextUtils.isEmpty(location)) {
                    List<String> categories = new ArrayList<String>();
                    categories.add("Catégorie");

                    for (Annonce annonce : annonces) {
                        if(!categories.contains(annonce.getCategorie())) {
                            categories.add(annonce.getCategorie());
                        }
                    }

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_spinner_item, categories);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    filterCategory.setAdapter(spinnerAdapter);
                }

                btnFilters.setText("Filtres (" + annonces.size() + " résultats)");

                AnnonceAdapter adapter = new AnnonceAdapter(ListActivity.this, annonces);
                listAnnonces.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Annonce>> call, Throwable t) {
                listLoader.setVisibility(View.GONE);
                Toast.makeText(ListActivity.this, "Une erreur est survenue lors du chargement. Vérifiez votre connexion internet puis rééssayez.", Toast.LENGTH_SHORT).show();
                Log.d("WebRequest", "Error : " + t.getMessage());
            }

        });
    }

    public void toggleFilters() {

        FrameLayout filters = findViewById(R.id.filtersView);
        RelativeLayout inFilters = findViewById(R.id.inFilters);

        View view = this.getCurrentFocus();
        Integer layoutHeight = inFilters.getHeight();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

            if(imm.isActive()) {
                layoutHeight *= 2;
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

        if(filters.getY() == 150) { // Ouvert
            filters.animate().y(-layoutHeight).setDuration(500).setListener(null);
        } else { // Fermé
            filters.setVisibility(View.VISIBLE);
            filters.animate().y(150).setDuration(500).setListener(null);
        }
    }

}
