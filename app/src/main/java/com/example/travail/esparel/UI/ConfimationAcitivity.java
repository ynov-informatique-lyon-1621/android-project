package com.example.travail.esparel.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.travail.esparel.R;

public class ConfimationAcitivity extends AppCompatActivity {
    TextView message;
    Button retourAcceuil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confimation_acitivity);

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
                Intent intentAccConfAn = new Intent(ConfimationAcitivity.this, MainActivity.class);
                startActivity(intentAccConfAn);
            }
        });
    }
}
