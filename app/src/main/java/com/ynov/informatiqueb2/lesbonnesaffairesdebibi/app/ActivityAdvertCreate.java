package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

public class ActivityAdvertCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        Button validerCreation = findViewById(R.id.validerCreation);
        Button cancelCreation = findViewById(R.id.cancelCreation);
        Button selectImgCreation = findViewById(R.id.selectImgCreation);

        final EditText NameCreation = findViewById(R.id.nameCreation);
        final EditText MailCreation = findViewById(R.id.mailCreation);
        final EditText MdpCreation = findViewById(R.id.mdpCreation);
        final EditText MdpConfirmCreation = findViewById(R.id.mdpConfirmCreation);
        final EditText PrixCreation = findViewById(R.id.prixCreation);
        final EditText DescriptionCreation = findViewById(R.id.descriptionCreation);
        final Spinner CategorieCreation = findViewById(R.id.categorieCreation);

        validerCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = NameCreation.getText().toString();
                String email = MailCreation.getText().toString();
                String mdp = MdpCreation.getText().toString();
                String mdpConfirm = MdpConfirmCreation.getText().toString();
                String categorie = CategorieCreation.getId().toString();
                String prix = PrixCreation.getText().toString();
                String description = DescriptionCreation.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    NameCreation.setError("Nom obligatoire");
                } else if (TextUtils.isEmpty(email)) {
                    MailCreation.setError("Email obligatoire");
                } else {
                    if (TextUtils.isEmpty(categorie)) {
                        CategorieCreation.setError("Categorie obligatoire");
                    }  else if (TextUtils.isEmpty(prix)) {
                        PrixCreation.setError("Prix obligatoire");
                } else {
                        if (TextUtils.isEmpty(description)) {
                            DescriptionCreation.setError("Description obligatoire");
                        } else {
                            if (mdp.equals(mdpConfirm)) {
                                Intent succesCreation = new Intent(ActivityAdvertCreate.this, ActivitySuccessCreation.class);
                                startActivity(succesCreation);
                            } else {
                                MdpCreation.setError("Les mots de passe ne correspondent pas");
                            }


                        }
                    }

                    }


                    if (mdp.equals(mdpConfirm)) {

                    }
            }
        });

        cancelCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelCreation = new Intent(ActivityAdvertCreate.this, ActivityDashboard.class);
                startActivity(cancelCreation);
            }
        });
    }
}
