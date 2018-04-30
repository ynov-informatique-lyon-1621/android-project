package com.ynov.informatiqueb2.lesbonnesaffairesdebibi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app.ActivityDashboard;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app.ActivityDetail;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        setTitle("Formulaire de contact");

        Button resetcontact = findViewById(R.id.resetcontact);
        Button submitcontact = findViewById(R.id.submitcontact);
        Button retourcontact = findViewById(R.id.retourcontact);


        final EditText NameContact = findViewById(R.id.namecontact);
        final EditText MailContact = findViewById(R.id.mailcontact);
        final EditText TelContact = findViewById(R.id.telcontact);
        final EditText MessageContact = findViewById(R.id.messagecontact);


        // Button reset qui permet d'annuler toute les saisies
        resetcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameContact.setText("");
                MailContact.setText("");
                TelContact.setText("");
                MessageContact.setText("");
            }
        });

        TextView TitreContact = findViewById(R.id.titrecontact);
        TextView CategorieContact = findViewById(R.id.categoriecontact);
        TextView DateContact = findViewById(R.id.datecontact);
        TextView PrixContact = findViewById(R.id.prixcontact);
        TextView VendeurContact = findViewById(R.id.vendeurcontact);

        Intent intentContact = getIntent();

        String TitreAnnonce = intentContact.getStringExtra("Titre");
        String CategorieAnnonce = intentContact.getStringExtra("Catégorie");
        String DateAnnonce = intentContact.getStringExtra("Date");
        String PrixAnnonce = intentContact.getStringExtra("Prix");
        String VendeurAnnonce = intentContact.getStringExtra("NomVendeur");

        TitreContact.setText(TitreAnnonce);
        CategorieContact.setText("Catégorie: " + CategorieAnnonce);
        DateContact.setText(DateAnnonce);
        PrixContact.setText("Prix: " + PrixAnnonce + " €");
        VendeurContact.setText("Nom du vendeur: " + VendeurAnnonce);

        ImageView imagecontact = findViewById(R.id.imagecontact);
        Glide.with(ContactActivity.this).load(R.drawable.logo).into(imagecontact);


        retourcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(ContactActivity.this, ActivityDashboard.class);
                startActivity(intent);
            }
        });

        submitcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = NameContact.getText().toString();
                String email = MailContact.getText().toString();
                String message = MessageContact.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    NameContact.setError("Nom obligatoire");
                } else if (TextUtils.isEmpty(email)) {
                    MailContact.setError("Email obligatoire");
                } else (TextUtils.isEmpty(message)) {
                    MessageContact.setError("Message obligatoire");
                }

            }
        });

            }


}
