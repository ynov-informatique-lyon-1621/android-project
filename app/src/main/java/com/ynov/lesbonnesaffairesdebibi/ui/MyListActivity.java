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

        // Appel du layout parent pour inclure la barre d'action globale et le menu (extension de l'activité de base - BaseActivity)
        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_my_list, contentLayout);

        // On utilisate SharedPreferences pour accéder à la mémoire et récupérer l'email et le password de l'utilisateur connecté
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyListActivity.this);
        String email = sp.getString("email", "");
        String password = sp.getString("password", "");

        // On déclare les éléments du layout
        listAnnonces = findViewById(R.id.listView);
        listLoader = findViewById(R.id.listLoader);
        listTextNotFound = findViewById(R.id.listTextNotFound);

        TextView listEmail = findViewById(R.id.listEmail);
        ImageButton listLogout = findViewById(R.id.listLogout);

        // On change le texte email par l'adresse mail de l'utilisateur (récupérée en mémoire)
        listEmail.setText(email);

        // On ajoute un écouteur de clic sur les items de la Listview (liste des annonces)
        listAnnonces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Au clic sur un item de la liste, on récupère l'objet correspondant et on le passe en sérialisé à l'activité Detail (Détail d'une annonce)
                Intent intent = new Intent(MyListActivity.this, DetailActivity.class);
                intent.putExtra("data", (Serializable) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });

        // On ajoute un écouteur de clic sur le bouton de déconnexion
        listLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Au clic sur le bouton de déconnexion, on vide les valeurs email et password en mémoire et on affiche un texte de confirmation
                sp.edit().putString("email", "").putString("password", "").apply();
                Toast.makeText(MyListActivity.this, "Vous avez été déconnecté", Toast.LENGTH_SHORT).show();

                // On redirige ensuite vers l'activité Login (Connexion)
                Intent intent = new Intent(MyListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // On initialise la librairie Retrofit2 permettant de gérer les appels au webservice
        httpService = new Retrofit.Builder().baseUrl(HttpService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(HttpService.class);

        // On charge les annonces spécifiques au compte connecté
        loadAnnonces(email, password);

    }

    public void loadAnnonces(final String email, final String password) {

        // On prépare l'appel à l'endpoint correspondant (findAnnoncesByEmail) en passant en paramètre l'email et le password du compte connecté
        call = httpService.findAnnoncesByEmail(email, password);

        // On met l'appel en file d'attente (appel asynchrone), la réponse sera renvoyée dans la callback
        call.enqueue(new Callback<List<Annonce>>() {

            @Override
            public void onResponse(Call<List<Annonce>> call, Response<List<Annonce>> response) {

                // Si on obtient une réponse, on cache le loader et on stocke la réponse dans la liste annonces
                listLoader.setVisibility(View.GONE);
                annonces = response.body();

                // Si la liste retournée ne contient aucune annonce, on affiche un texte "il n'y a aucune annonce..."
                if(annonces.size() < 1)
                    listTextNotFound.setVisibility(View.VISIBLE);
                else
                    listTextNotFound.setVisibility(View.GONE);

                // On applique notre tri par date sur la liste annonces
                Collections.sort(annonces, new ListComparator());

                // On instancie l'adaptateur avec la liste annonces et on le lie à notre Listview annonces
                AnnonceAdapter adapter = new AnnonceAdapter(MyListActivity.this, annonces);
                listAnnonces.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Annonce>> call, Throwable t) {
                // Si on obtient une erreur, on cache le loader et on affiche un message d'erreur
                listLoader.setVisibility(View.GONE);
                Toast.makeText(MyListActivity.this, "Une erreur est survenue lors du chargement. Vérifiez votre connexion internet puis rééssayez.", Toast.LENGTH_SHORT).show();
                Log.d("WebRequest", "Error : " + t.getMessage());
            }

        });
    }

}
