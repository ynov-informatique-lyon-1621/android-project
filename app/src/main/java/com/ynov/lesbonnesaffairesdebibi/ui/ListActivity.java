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
import java.util.Collections;
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

        // Appel du layout parent pour inclure la barre d'action globale et le menu (extension de l'activité de base - BaseActivity)
        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_list, contentLayout);

        // On déclare les éléments du layout
        listAnnonces = findViewById(R.id.listAnnonces);
        listLoader = findViewById(R.id.listLoader);

        btnFilters = findViewById(R.id.btnFilters);
        final Button btnApplyFilters = findViewById(R.id.btnApplyFilters);

        final EditText filterKeywords = findViewById(R.id.filterKeywords);
        filterCategory = findViewById(R.id.filterCategory);
        final EditText filterLocation = findViewById(R.id.filterLocation);

        // On ajoute un écouteur de clic sur le bouton "Filtres"
        btnFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Au clic, on affiche/cache le panneau des filtres
                toggleFilters();
            }
        });

        // On ajoute un écouteur de clic sur le bouton "Appliquer les filtres"
        btnApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Au clic, on affiche/cache le panneau des filtres
                toggleFilters();

                // On vide la listview déjà affichée et la liste des annonces
                listAnnonces.setAdapter(null);
                annonces.clear();

                // On affiche le loader et on recharge les annonces avec les filtres en paramètres
                listLoader.setVisibility(View.VISIBLE);
                loadAnnonces(filterKeywords.getText().toString(),
                        filterCategory.getSelectedItemPosition() != 0 ? filterCategory.getSelectedItem().toString() : "",
                        filterLocation.getText().toString());
            }
        });

        // On ajoute un écouteur de clic sur les items de la Listview (liste des annonces)
        listAnnonces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Au clic sur un item de la liste, on récupère l'objet correspondant et on le passe en sérialisé à l'activité Detail (Détail d'une annonce)
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra("data", (Serializable) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });

        // On initialise la librairie Retrofit2 permettant de gérer les appels au webservice
        httpService = new Retrofit.Builder().baseUrl(HttpService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(HttpService.class);

        // On charge toutes les annonces (sans filtres)
        loadAnnonces("", "", "");
    }

    public void loadAnnonces(final String keywords, final String category, final String location) {

        // On prépare l'appel à l'endpoint correspondant (findAnnonces) en passant les filtres en paramètres
        call = httpService.findAnnonces(keywords, category, location);

        // On met l'appel en file d'attente (appel asynchrone), la réponse sera renvoyée dans la callback
        call.enqueue(new Callback<List<Annonce>>() {

            @Override
            public void onResponse(Call<List<Annonce>> call, Response<List<Annonce>> response) {

                // Si on obtient une réponse, on cache le loader et on stocke la réponse dans la liste annonces
                listLoader.setVisibility(View.GONE);
                annonces = response.body();

                // Si aucune filtre n'est défini, on charge les catégories
                if(TextUtils.isEmpty(keywords) && TextUtils.isEmpty(category) && TextUtils.isEmpty(location)) {
                    // On déclare la liste contenant les catégories
                    List<String> categories = new ArrayList<String>();
                    categories.add("Catégorie");

                    // On itère sur la liste des annonces chargées précédemment
                    for (Annonce annonce : annonces) {
                        if(!categories.contains(annonce.getCategorie()) && annonce.getCategorie() != null) {
                            // Si la liste de catégories ne contient pas cette catégorie et si elle n'est pas null, on l'ajoute à la liste
                            categories.add(annonce.getCategorie());
                        }
                    }

                    // On instancie un adaptateur de liste déroulante (spinner) avec la liste des catégories construite précédemment
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_spinner_item, categories);

                    // On lie l'adaptateur à la liste déroulante (spinner) des catégories
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    filterCategory.setAdapter(spinnerAdapter);
                }

                // On affiche le nombre de résultats trouvés sur le bouton filtres
                btnFilters.setText("Filtres (" + annonces.size() + " résultats)");

                // On applique notre tri par date sur la liste annonces
                Collections.sort(annonces, new ListComparator());

                // On instancie l'adaptateur avec la liste annonces et on le lie à notre Listview annonces
                AnnonceAdapter adapter = new AnnonceAdapter(ListActivity.this, annonces);
                listAnnonces.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Annonce>> call, Throwable t) {
                // Si on obtient une erreur, on cache le loader et on affiche un message d'erreur
                listLoader.setVisibility(View.GONE);
                Toast.makeText(ListActivity.this, "Une erreur est survenue lors du chargement. Vérifiez votre connexion internet puis rééssayez.", Toast.LENGTH_SHORT).show();
                Log.d("WebRequest", "Error : " + t.getMessage());
            }

        });
    }

    public void toggleFilters() {

        // On déclare les éléments du layout contenant les filtres
        FrameLayout filters = findViewById(R.id.filtersView);
        RelativeLayout inFilters = findViewById(R.id.inFilters);

        View view = this.getCurrentFocus();
        Integer layoutHeight = inFilters.getHeight();

        // Si le clavier est ouvert, on le ferme avant d'appliquer l'animation pour éviter les bugs d'affichage
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

            if(imm.isActive()) {
                layoutHeight *= 2;
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

        // On applique un effet d'animation "slide" sur le panneau des filtres en faisant varier la hauteur (y)
        if(filters.getY() == 150) { // Ouvert
            filters.animate().y(-layoutHeight).setDuration(500).setListener(null);
        } else { // Fermé
            filters.setVisibility(View.VISIBLE);
            filters.animate().y(150).setDuration(500).setListener(null);
        }
    }

}
