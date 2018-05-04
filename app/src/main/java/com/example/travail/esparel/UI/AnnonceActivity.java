package com.example.travail.esparel.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.travail.esparel.Controller.ImageControleller;
import com.example.travail.esparel.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AnnonceActivity extends AppCompatActivity {

    ImageView image;
    TextView titre;
    TextView categorie;
    TextView prix;
    TextView vendeur;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce);

        Intent intent = getIntent();

        String annonce = intent.getStringExtra("titre");
        titre = findViewById(R.id.titre);
        titre.setText(annonce);

        String categorieannonce = intent.getStringExtra("categorie");
        categorie = findViewById(R.id.categorie);
        categorie.setText("Catégorie : " + categorieannonce);

        String prixannonce = intent.getStringExtra("prix");
        prix = findViewById(R.id.prix);
        prix.setText("Prix : " + prixannonce + " €");

        String vendeurannonce = intent.getStringExtra("vendeur");
        vendeur = findViewById(R.id.vendeur);
        vendeur.setText("Vendeur : " + vendeurannonce);

        String descriptionannonce = intent.getStringExtra("description");
        description = findViewById(R.id.description);
        description.setText(descriptionannonce);

        String imageannonce = intent.getStringExtra("image");
        image = (ImageView) findViewById(R.id.image);


        new ImageControleller((ImageView) image)
                .execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" + imageannonce);
    }
}