package com.ynov.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;
import com.ynov.lesbonnesaffairesdebibi.service.DateService;
import com.ynov.lesbonnesaffairesdebibi.service.FavoriteService;

import java.io.Serializable;

public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Appel du layout parent pour inclure la barre d'action globale et le menu (extension de l'activité de base - BaseActivity)
        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_detail, contentLayout);

        // On récupère l'objet sérialisé passé dans l'intent et on le convertit on objet Annonce
        Intent intentData = getIntent();
        final Annonce annonce = (Annonce) intentData.getSerializableExtra("data");

        // On initialise le service pour gérer les favoris
        final FavoriteService favoriteService = new FavoriteService(DetailActivity.this);

        // On déclare les éléments du layout
        ImageView annImage = findViewById(R.id.annImage);
        TextView annTitre = findViewById(R.id.annTitle);
        TextView annPrix = findViewById(R.id.annPrix);
        TextView annDate = findViewById(R.id.annDate);
        TextView annVendeur = findViewById(R.id.annVendeur);
        TextView annDescription = findViewById(R.id.annDescription);
        ImageButton annFavorite = findViewById(R.id.annFavorite);
        FloatingActionButton annContact = findViewById(R.id.annContact);

        // On appelle la méthode du service favoris qui affichera si l'annonce est en favoris ou non
        favoriteService.draw(annonce, annFavorite);

        // Ajout d'un écouteur de clic sur le bouton favoris
        annFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On appelle la méthode du service favoris qui gère le clic sur l'ajout de l'annonce en favoris
                favoriteService.click(annonce, ((ImageButton) v));
            }
        });

        // Ajout d'un écouteur sur le bouton pour contacter le vendeur
        annContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On passe l'objet Annonce sérialisé à l'activité Contact (Contacter le vendeur)
                Intent intent = new Intent(DetailActivity.this, ContactActivity.class);
                intent.putExtra("data", (Serializable) annonce);
                startActivity(intent);
            }
        });

        // Avec la librairie Picasso, on charge l'image de l'annonce dans l'imageView
        Picasso.get().load(annonce.getImage()).into(annImage);

        // On change les textes pour afficher les informations de l'annonce
        annTitre.setText(annonce.getTitre());
        annPrix.setText(annonce.getPrix());
        annDate.setText(new DateService(annonce.getDateCreation()).show());
        annVendeur.setText(annonce.getNomVendeur());
        annDescription.setText(annonce.getDescription());

    }
}
