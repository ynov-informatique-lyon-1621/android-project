package com.example.oli.projetb2.Ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oli.projetb2.R;

public class ConfirmationActivity extends AppCompatActivity {

    Intent intent;
    TextView vTitre;
    TextView vDate;
    TextView vPrix;
    Button btnAccueil;
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        intent = getIntent();
        vTitre = findViewById(R.id.titreannonce);
        vDate = findViewById(R.id.dateannonce);
        vPrix = findViewById(R.id.prixannonce);
        btnAccueil = findViewById(R.id.confirAcceuil);
        btnback = findViewById(R.id.confirBack);

        final String titre = intent.getStringExtra("titre");
        final int prix = intent.getIntExtra("prix", 0);
        final int date = intent.getIntExtra("date", 0);
        final String desc = intent.getStringExtra("desc");

        vTitre.setText(titre);
        vPrix.setText(String.valueOf(prix + "€"));
        vDate.setText(String.valueOf(date));


        btnAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ConfirmationActivity.this, ListAdvertActivity.class);
                startActivity(intent);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ConfirmationActivity.this, AdvertCellActivity.class);
                intent.putExtra("titre", titre);
                intent.putExtra("prix", prix);
                intent.putExtra("date", date);
                intent.putExtra("desc", desc);
                startActivity(intent);

            }
        });


    }


    //Menu hamburger
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    private void acceuil() { //permet de revenir sur la liste des annonces filtrer selon les filtres utilisateurs s’il y en a
        Toast.makeText(this, R.string.action_accueil, Toast.LENGTH_LONG).show();
        intent = new Intent(ConfirmationActivity.this, ListAdvertActivity.class);
        startActivity(intent);
    }

    private void deposer() { //redirige vers la création d’une annonce
        Toast.makeText(this, R.string.action_depo_annonce, Toast.LENGTH_LONG).show();
        intent = new Intent(ConfirmationActivity.this, CreateAdvertActivity.class);
        startActivity(intent);
    }

    private void modifier() {
        Toast.makeText(this, R.string.action_modi_annonce, Toast.LENGTH_LONG).show();
    }

    private void favoris() {
        Toast.makeText(this, R.string.action_favoris, Toast.LENGTH_LONG).show();
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_acceuil:
                acceuil();
                break;
            case R.id.action_depo_annonce:
                deposer();
                break;
            case R.id.action_modi_annonce:
                modifier();
                break;
            case R.id.action_favoris:
                favoris();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
