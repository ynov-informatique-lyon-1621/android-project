package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

        // Affiche le détail d'une annonce
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
        ImageView PictureDetail = findViewById(R.id.imageView);

        Intent intentDetails = getIntent();

        String Titre = intentDetails.getStringExtra("Titre");
        String Categorie = intentDetails.getStringExtra("Categorie");
        String Prix = intentDetails.getStringExtra("Prix");
        String Description = intentDetails.getStringExtra("Description");
        String NomVendeur = intentDetails.getStringExtra("NomVendeur");
        String Image = intentDetails.getStringExtra("Picture");

        TitreDetail.setText(Titre);
        CategorieDetail.setText("Catégorie: " + Categorie);
        PrixDetail.setText("Prix: " + Prix + " €");
        DescriptionDetail.setText("Description: " + Description);
        NomVendeurDetail.setText("Non du vendeur: " + NomVendeur);
        Glide.with(ActivityDetail.this)
                .load("http://139.99.98.119:8080/" + Image.substring(25))
                .into(PictureDetail);

        // Button qui permet de contacter le vendeur
        Button idcontact = findViewById(R.id.IDContact);
        idcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDetail.this, ActivityContact.class);




                startActivity(intent);
            }
        });

    }
        // Toujours notre menu hamburger
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

        if (id == R.id.action_settings_add){
            Intent intentAdd = new Intent(this, ActivityAdvertCreate.class);
            this.startActivity(intentAdd);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
