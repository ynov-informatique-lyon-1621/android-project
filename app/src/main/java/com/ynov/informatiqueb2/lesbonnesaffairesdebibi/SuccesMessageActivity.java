package com.ynov.informatiqueb2.lesbonnesaffairesdebibi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app.ActivityDashboard;

public class SuccesMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_message);

        Button recherchesucces = findViewById(R.id.recherchesucces);
        Button annoncesucces = findViewById(R.id.annoncesucces);

        TextView TitreSucces =findViewById(R.id.titresucces);
        TextView CategorieSucces = findViewById(R.id.categoriesucces);
        TextView DateSucces = findViewById(R.id.datesucces);
        TextView PrixSucces = findViewById(R.id.prixsucces);
        TextView VendeurSucces = findViewById(R.id.vendeursucces);
        TextView MessageSucces = findViewById(R.id.messagesucces);

        Intent intentSucces = getIntent();

        String TitreAnnonce = intentSucces.getStringExtra("Titre");
        String CategorieAnnonce = intentSucces.getStringExtra("Catégorie");
        String DateAnnonce = intentSucces.getStringExtra("Date");
        String PrixAnnonce = intentSucces.getStringExtra("Prix");
        String VendeurAnnonce = intentSucces.getStringExtra("NomVendeur");
        String MessageAnnonce = intentSucces.getStringExtra("Message");

        TitreSucces.setText(TitreAnnonce);
        CategorieSucces.setText("Catégorie: " + String.valueOf(CategorieAnnonce));
        DateSucces.setText(DateAnnonce);
        PrixSucces.setText("Prix: " + String.valueOf(PrixAnnonce) + " €");
        VendeurSucces.setText("Nom du vendeur: " + String.valueOf(VendeurAnnonce));
        MessageSucces.setText("Votre message a bien été envoyé au vendeur.\n" + String.valueOf(VendeurAnnonce) + " reviendra vers vous rapidement.\n" + " \n" + "Bonne journée.\n" + " \n" + "L'équipe de lesbonnesaffairesdebibi.fr");

        ImageView imagesucces = findViewById(R.id.imageannoncesucces);
        Glide.with(SuccesMessageActivity.this).load(R.drawable.logo).into(imageannoncesucces);

        recherchesucces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccesMessageActivity.this, ActivityDashboard.class);
                    startActivity(intent);
            }
        });
}
