package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;


public class ConfirmationDeposerAnnonce extends AppCompatActivity {
    TextView message;
    Button retourAcceuil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_deposer_annonce);

        message = (TextView) findViewById(R.id.confPostAn);
        retourAcceuil = (Button) findViewById(R.id.acceuilConf);

        message.setText("Votre annonce a bien été enregistrée.\n\n" +
                "Un email de confirmation (qui n'arrivera jamais) de la publication de celle ci" +
                "vous sera envoyé dans les meilleurs délais.\n\n" +
                "Bonne journée.\n\n" +
                "L'équipe de lesbonneaffairesdebibi.fr");

        retourAcceuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAccConfAn = new Intent(ConfirmationDeposerAnnonce.this, MainActivity.class);
                startActivity(intentAccConfAn);
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
