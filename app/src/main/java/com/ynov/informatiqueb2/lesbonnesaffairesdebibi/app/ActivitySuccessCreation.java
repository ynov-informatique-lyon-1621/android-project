package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

public class ActivitySuccessCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_creation);

        Button accueil = findViewById(R.id.accueil);

        final TextView remerciement =findViewById(R.id.remerciement);

        Toast.makeText(ActivitySuccessCreation.this
                , "Votre annonce a bien été enregistrée. " +
                        "Un email de confirmation de la publication de celle ci " +
                        "vous sera envoyé dans les meilleurs délais" +
                        " " +
                        "Bonne journée." +
                        " " +
                        "L'équipe de lesbonnesaffairesdebibi.fr"
                , Toast.LENGTH_LONG).show();
    }
}
