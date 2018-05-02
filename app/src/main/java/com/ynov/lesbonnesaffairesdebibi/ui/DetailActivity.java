package com.ynov.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;
import com.ynov.lesbonnesaffairesdebibi.service.DateService;
import com.ynov.lesbonnesaffairesdebibi.service.FavoriteService;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Détail de l'annonce");

        Intent intentData = getIntent();
        final Annonce annonce = (Annonce) intentData.getSerializableExtra("data");
        final FavoriteService favoriteService = new FavoriteService(DetailActivity.this);

        ImageView annImage = findViewById(R.id.annImage);
        TextView annTitre = findViewById(R.id.annTitre);
        TextView annPrix = findViewById(R.id.annPrix);
        TextView annDate = findViewById(R.id.annDate);
        TextView annDescription = findViewById(R.id.annDescription);
        ImageButton annFavorite = findViewById(R.id.annFavorite);
        FloatingActionButton annContact = findViewById(R.id.annContact);

        favoriteService.draw(annonce, annFavorite);

        annFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteService.click(annonce, ((ImageButton) v));
            }
        });

        annContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Formulaire contact vendeur", 3000).show();
            }
        });

        Picasso.get().load(annonce.getImage()).into(annImage);
        annTitre.setText(annonce.getTitre());
        annPrix.setText(annonce.getPrix());
        annDate.setText(new DateService(annonce.getDateCreation()).show());
        annDescription.setText(annonce.getDescription());

    }
}
