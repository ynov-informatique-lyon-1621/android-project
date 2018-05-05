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
        //setContentView(R.layout.activity_detail);

        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_detail, contentLayout);
        //setTitle("Détail de l'annonce");

        Intent intentData = getIntent();
        final Annonce annonce = (Annonce) intentData.getSerializableExtra("data");
        final FavoriteService favoriteService = new FavoriteService(DetailActivity.this);

        ImageView annImage = findViewById(R.id.annImage);
        TextView annTitre = findViewById(R.id.annTitle);
        TextView annPrix = findViewById(R.id.annPrix);
        TextView annDate = findViewById(R.id.annDate);
        TextView annVendeur = findViewById(R.id.annVendeur);
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
                Intent intent = new Intent(DetailActivity.this, ContactActivity.class);
                intent.putExtra("data", (Serializable) annonce);
                startActivity(intent);
            }
        });

        Picasso.get().load(annonce.getImage()).into(annImage);
        annTitre.setText(annonce.getTitre());
        annPrix.setText(annonce.getPrix());
        annDate.setText(new DateService(annonce.getDateCreation()).show());
        annVendeur.setText(annonce.getNomVendeur());
        annDescription.setText(annonce.getDescription());

    }
}
