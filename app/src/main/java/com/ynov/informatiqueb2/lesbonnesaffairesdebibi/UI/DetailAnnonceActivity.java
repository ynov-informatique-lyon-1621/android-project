package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.DownloadImage;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;


public class DetailAnnonceActivity extends AppCompatActivity {

    ImageView imageDetail;
    TextView titleDetail;
    TextView categorieDetail;
    TextView prixDetail;
    //TextView dateDetail;
    TextView vendeurDetail;
    TextView descriptionDetail;
    Button contacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_annonce);

        Intent intentDetails = getIntent();
        final String id = intentDetails.getStringExtra("id");
        final String title = intentDetails.getStringExtra("titre");
        final String categorie = intentDetails.getStringExtra("categorie");
        final String prix = intentDetails.getStringExtra("prix");
        //String date = intentDetails.getStringExtra("date");
        final String vendeur = intentDetails.getStringExtra("vendeur");
        String description = intentDetails.getStringExtra("description");
        final String image = intentDetails.getStringExtra("image");

        titleDetail = findViewById(R.id.titleDetail);
        categorieDetail = findViewById(R.id.categorieDetail);
        prixDetail = findViewById(R.id.prixDetail);
        //dateDetail = findViewById(R.id.dateDetail);
        vendeurDetail = findViewById(R.id.vendeurDetail);
        descriptionDetail = findViewById(R.id.descriptionDetail);
        imageDetail = (ImageView) findViewById(R.id.imageDetail);


        titleDetail.setText(title);
        categorieDetail.setText("Catégorie : " + categorie);
        prixDetail.setText("Prix : " + prix + " €");
        //dateDetail.setText(date);
        vendeurDetail.setText("Vendeur : " + vendeur);
        descriptionDetail.setText(description);

        new DownloadImage((ImageView) imageDetail)
                .execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" + image);

        contacter = (Button) findViewById(R.id.contacterDetail);
        contacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentContact = new Intent(DetailAnnonceActivity.this,ContacterVendeurActivity.class);

                intentContact.putExtra("id",id);
                intentContact.putExtra("titre",title);
                intentContact.putExtra("image",image);
                intentContact.putExtra("prix",prix);
                //intentContact.putExtra("date",date);
                intentContact.putExtra("vendeur",vendeur);
                intentContact.putExtra("categorie",categorie);
                startActivity(intentContact);

            }
        });
    }
}
