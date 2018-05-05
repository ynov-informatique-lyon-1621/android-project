package com.ynov.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.adapter.AnnonceAdapter;
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;
import com.ynov.lesbonnesaffairesdebibi.service.HttpService;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyListActivity extends BaseActivity {

    private List<Annonce> annonces;
    private ListView listAnnonces = null;
    private TextView listTextNotFound = null;
    private ProgressBar listLoader = null;
    private HttpService httpService;

    Call<List<Annonce>> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("Mes annonces");

        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_my_list, contentLayout);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyListActivity.this);

        String email = sp.getString("email", "");
        String password = sp.getString("password", "");

        listAnnonces = findViewById(R.id.listView);
        listLoader = findViewById(R.id.listLoader);
        listTextNotFound = findViewById(R.id.listTextNotFound);

        TextView listEmail = findViewById(R.id.listEmail);
        ImageButton listLogout = findViewById(R.id.listLogout);

        listEmail.setText(email);

        listAnnonces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyListActivity.this, DetailActivity.class);
                intent.putExtra("data", (Serializable) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });

        listLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putString("email", "").putString("password", "").apply();
                Toast.makeText(MyListActivity.this, "Vous avez été déconnecté", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MyListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        httpService = new Retrofit.Builder().baseUrl(HttpService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(HttpService.class);

        loadAnnonces(email, password);

    }

    public void loadAnnonces(final String email, final String password) {

        call = httpService.findAnnoncesByEmail(email, password);

        call.enqueue(new Callback<List<Annonce>>() {

            @Override
            public void onResponse(Call<List<Annonce>> call, Response<List<Annonce>> response) {

                listLoader.setVisibility(View.GONE);
                annonces = response.body();

                if(annonces.size() < 1)
                    listTextNotFound.setVisibility(View.VISIBLE);
                else
                    listTextNotFound.setVisibility(View.GONE);

                Collections.sort(annonces, new ListComparator());
                AnnonceAdapter adapter = new AnnonceAdapter(MyListActivity.this, annonces);
                listAnnonces.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Annonce>> call, Throwable t) {
                listLoader.setVisibility(View.GONE);
                Toast.makeText(MyListActivity.this, "Une erreur est survenue lors du chargement. Vérifiez votre connexion internet puis rééssayez.", Toast.LENGTH_SHORT).show();
                Log.d("WebRequest", "Error : " + t.getMessage());
            }

        });
    }

}
