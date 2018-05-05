package com.ynov.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.adapter.AnnonceAdapter;
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;
import com.ynov.lesbonnesaffairesdebibi.service.FavoriteService;

import java.io.Serializable;
import java.util.List;

public class FavoritesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Appel du layout parent pour inclure la barre d'action globale et le menu (extension de l'activité de base - BaseActivity)
        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_favorites, contentLayout);

        // On déclare la liste contenant les favoris et on initialise le service pour gérer les favoris
        ListView listFavorites = findViewById(R.id.listFavorites);
        FavoriteService favoriteService = new FavoriteService(FavoritesActivity.this);
        List<Annonce> favoritesList = favoriteService.getList();

        // On ajoute un écouteur de clic sur les items de la Listview (liste des favoris)
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Au clic sur un item de la liste, on récupère l'objet correspondant et on le passe en sérialisé à l'activité Detail (Détail d'une annonce)
                Intent intent = new Intent(FavoritesActivity.this, DetailActivity.class);
                intent.putExtra("data", (Serializable) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });


        if(favoritesList.size() > 0) {
            // Si la liste contient des annonces
            // On instancie l'adaptateur avec la liste des favoris (favoritesList) et on le lie à notre Listview favoris (listFavorites)
            AnnonceAdapter adapter = new AnnonceAdapter(FavoritesActivity.this, favoritesList);
            listFavorites.setAdapter(adapter);
        } else {
            // Si la liste ne contient aucune annonce, on affiche un texte "Vous n'avez aucun favori..."
            ((TextView) findViewById(R.id.noFavoriteText)).setVisibility(View.VISIBLE);
        }

    }
}
