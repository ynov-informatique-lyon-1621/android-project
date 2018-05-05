package com.example.elesa.projet_lelt.Ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elesa.projet_lelt.Model.DownloadImage;
import com.example.elesa.projet_lelt.R;

public class Confirmation extends AppCompatActivity {

    TextView titre;
    TextView pris;
    TextView seller;
    TextView seller3;
    TextView cate;
    TextView date;
    ImageView imageView;
    Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Intent intent = getIntent();


//récupère les variables transmit précédement
        final String titre2 = intent.getStringExtra("titre");
        final String prix2 = intent.getStringExtra("prix");
        final String seller2 = intent.getStringExtra("seller");
        final String categories2 = intent.getStringExtra("categories");
        final String img = intent.getStringExtra("img");
        final String createDate = intent.getStringExtra("date");

        titre = findViewById(R.id.titre);
        pris = findViewById(R.id.prix);
        seller = findViewById(R.id.seller);
        seller3 = findViewById(R.id.seller2);
        cate = findViewById(R.id.categorie);
        date = findViewById(R.id.date);
        retour = findViewById(R.id.retour);
        imageView = findViewById(R.id.imageView);
        //change le text view par les attributs
        titre.setText(titre2);
        pris.setText(prix2);
        seller.setText(seller2);
        seller3.setText(seller2);
        cate.setText(categories2);
        date.setText(createDate);
//        //affiche l'image depuis l'Url + le nom de l'image à la place de l'imageView
        new DownloadImage((ImageView) imageView).execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" +img);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Confirmation.this, liste.class);
                startActivity(intent);
            }
        });
    }
}
