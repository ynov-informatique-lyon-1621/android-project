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


//Cette acti sert a confirmer le dépot d'annonce.
public class ConfirmationDeposerAnnonce extends com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI.Menu {

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
        //Lorsque l'on clique sur le boutton, on fini l'activité ConfirmationDéposerAnnonce ainsi que DeposerAnnonceActivity.
        retourAcceuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ConfirmationDeposerAnnonce.this.finish();
               DeposerAnnonceActivity.actiDepAnn.finish();
            }
        });
    }
}
