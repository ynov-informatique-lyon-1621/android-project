package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
