package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

public class ActivityDetail extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Detail de l'annonce");


        TextView TitreDetail = findViewById(R.id.TitleDetailView);
        TextView CategorieDetail = findViewById(R.id.CatDetailView);
        TextView  PrixDetail = findViewById(R.id.PriceDetailView);
        TextView DescriptionDetail = findViewById(R.id.DescriptionDetailView);
        TextView NomVendeurDetail = findViewById(R.id.NomVendeurDetailView);

        Intent intentDetails = getIntent();

        String Titre = intentDetails.getStringExtra("Titre");
        String Categorie = intentDetails.getStringExtra("Categorie");
        String Prix = intentDetails.getStringExtra("Prix");
        String Description = intentDetails.getStringExtra("Description");
        String NomVendeur = intentDetails.getStringExtra("NomVendeur");

        TitreDetail.setText(Titre);
        CategorieDetail.setText("Catégorie: " + Categorie);
        PrixDetail.setText("Prix: " + Prix + " €");
        DescriptionDetail.setText("Description: " + Description);
        NomVendeurDetail.setText("Non du vendeur: " + NomVendeur);

        ImageView logo = findViewById(R.id.imageView);

        Glide.with(ActivityDetail.this).load(R.drawable.logo).into(logo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings_connect) {
            Intent intent = new Intent(this, ActivityForm.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
