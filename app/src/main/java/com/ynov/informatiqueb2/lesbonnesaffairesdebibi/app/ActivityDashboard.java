package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.controller.GetViewController;

    // Liste des annonces
public class ActivityDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Execution du 'GetViewController' sur le ActivityDashboard
        new GetViewController(ActivityDashboard.this).execute();
    }
        // Mise en place du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_dashboard, menu);
        return true;
    }
        // Menu hamburger où l'on accède à l'accueil ou à la partie déposer une annonce
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

