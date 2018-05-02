package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.DownloadImage;
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
        String date = intentDetails.getStringExtra("date");
        String vendeur = intentDetails.getStringExtra("vendeur");
        String description = intentDetails.getStringExtra("description");
        String image = intentDetails.getStringExtra("image");

        titleDetail = findViewById(R.id.titleDetail);
        categorieDetail = findViewById(R.id.categorieDetail);
        prixDetail = findViewById(R.id.prixDetail);
        dateDetail = findViewById(R.id.dateDetail);
        vendeurDetail = findViewById(R.id.vendeurDetail);
        descriptionDetail = findViewById(R.id.descriptionDetail);
        imageDetail = (ImageView) findViewById(R.id.imageDetail);


        titleDetail.setText(title);
        categorieDetail.setText("Catégorie : " + categorie);
        prixDetail.setText("Prix : " + prix + " €");
        dateDetail.setText(date);
        vendeurDetail.setText("Vendeur : " + vendeur);
        descriptionDetail.setText(description);

        new DownloadImage((ImageView) imageDetail)
                .execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" + image);
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
