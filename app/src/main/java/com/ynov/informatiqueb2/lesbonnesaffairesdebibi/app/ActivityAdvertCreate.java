package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


        final EditText NameCreation = findViewById(R.id.nameCreation);
        final EditText MailCreation = findViewById(R.id.mailCreation);
        final EditText MdpCreation = findViewById(R.id.mdpCreation);
        final EditText MdpConfirmCreation = findViewById(R.id.mdpConfirmCreation);
        final EditText PrixCreation = findViewById(R.id.prixCreation);
        final EditText DescriptionCreation = findViewById(R.id.descriptionCreation);
        final EditText CategorieCreation = findViewById(R.id.categorieCreation);

        validerCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = NameCreation.getText().toString();
                String email = MailCreation.getText().toString();
                String mdp = MdpCreation.getText().toString();
                String mdpConfirm = MdpConfirmCreation.getText().toString();
                String categorie = CategorieCreation.getText().toString();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings_connect) {
            Intent intent = new Intent(this, ActivityForm.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.action_settings_add){
            Intent intentAdd = new Intent(this, ActivityAdvertCreate.class);
            this.startActivity(intentAdd);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
