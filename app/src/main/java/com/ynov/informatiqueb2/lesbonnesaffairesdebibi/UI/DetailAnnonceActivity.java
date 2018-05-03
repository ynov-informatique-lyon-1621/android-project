package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.DownloadImage;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;


public class DetailAnnonceActivity extends AppCompatActivity {
//Cette Activity nous sert à donner des details une fois que l'on clique sur l'annonce
    ImageView imageDetail;
    TextView titleDetail;
    TextView categorieDetail;
    TextView prixDetail;
    TextView dateDetail;
    TextView vendeurDetail;
    TextView descriptionDetail;
    Button contacter;
    //Déclaration pour pouvoir terminer une activity depuis une autre
    public static Activity actiDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_annonce);
        //Pour pouvoir terminer une activity depuis une autre.
        actiDetail = this;

        //On récup nos info dans notre intent.
        Intent intentDetails = getIntent();
        final String id = intentDetails.getStringExtra("id");
        final String title = intentDetails.getStringExtra("titre");
        final String categorie = intentDetails.getStringExtra("categorie");
        final String prix = intentDetails.getStringExtra("prix");
        final String date = intentDetails.getStringExtra("date");
        final String vendeur = intentDetails.getStringExtra("vendeur");
        final String description = intentDetails.getStringExtra("description");
        final String image = intentDetails.getStringExtra("image");

        //On recup nos champs
        titleDetail = findViewById(R.id.titleDetail);
        categorieDetail = findViewById(R.id.categorieDetail);
        prixDetail = findViewById(R.id.prixDetail);
        dateDetail = findViewById(R.id.dateDetail);
        vendeurDetail = findViewById(R.id.vendeurDetail);
        descriptionDetail = findViewById(R.id.descriptionDetail);
        imageDetail = (ImageView) findViewById(R.id.imageDetail);

        //On set nos champs
        titleDetail.setText(title);
        categorieDetail.setText("Catégorie : " + categorie);
        prixDetail.setText("Prix : " + prix + " €");
        dateDetail.setText(date);
        vendeurDetail.setText("Vendeur : " + vendeur);
        descriptionDetail.setText(description);

        new DownloadImage((ImageView) imageDetail)
                .execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" + image);
        //Clique sur le boutons contracter vendeur
        contacter = (Button) findViewById(R.id.contacterDetail);
        contacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On set notre intent, on fait passer les infos dont on a besoin, on lance notre intent.
                Intent intentContact = new Intent(DetailAnnonceActivity.this,ContacterVendeurActivity.class);

                intentContact.putExtra("id",id);
                intentContact.putExtra("titre",title);
                intentContact.putExtra("image",image);
                intentContact.putExtra("prix",prix);
                intentContact.putExtra("date",date);
                intentContact.putExtra("vendeur",vendeur);
                intentContact.putExtra("categorie",categorie);
                startActivity(intentContact);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_menu_un:
                Intent mainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(mainActivityIntent);
                return true;
            case R.id.action_menu_deux:
                Intent deposerAnnonceIntent = new Intent(getBaseContext(), DeposerAnnonceActivity.class);
                startActivity(deposerAnnonceIntent);
                return true;
            case R.id.action_menu_trois:
                Intent favorisIntent = new Intent(getBaseContext(), IdentificationActivity.class);
                startActivity(favorisIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
