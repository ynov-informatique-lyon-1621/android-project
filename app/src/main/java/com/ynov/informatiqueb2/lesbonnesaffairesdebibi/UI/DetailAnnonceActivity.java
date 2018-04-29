package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;


public class DetailAnnonceActivity extends AppCompatActivity {

    ImageView imageDetail;
    TextView titleDetail;
    TextView categorieDetail;
    TextView prixDetail;
    TextView dateDetail;
    TextView vendeurDetail;
    TextView descriptionDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_annonce);

        Intent intentDetails = getIntent();

        String title = intentDetails.getStringExtra("titre");
        String categorie = intentDetails.getStringExtra("categorie");
        String prix = intentDetails.getStringExtra("prix");
        //String date = intentDetails.getStringExtra("date");
        String vendeur = intentDetails.getStringExtra("vendeur");
        String description = intentDetails.getStringExtra("description");

        titleDetail = findViewById(R.id.titleDetail);
        categorieDetail = findViewById(R.id.categorieDetail);
        prixDetail = findViewById(R.id.prixDetail);
        //dateDetail = findViewById(R.id.dateDetail);
        vendeurDetail = findViewById(R.id.vendeurDetail);
        descriptionDetail = findViewById(R.id.descriptionDetail);


        titleDetail.setText(title);
        categorieDetail.setText(categorie);
        prixDetail.setText(prix);
        //dateDetail.setText(date);
        vendeurDetail.setText(vendeur);
        descriptionDetail.setText(description);
    }
}
