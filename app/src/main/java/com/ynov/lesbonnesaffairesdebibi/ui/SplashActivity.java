package com.ynov.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ynov.lesbonnesaffairesdebibi.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // On cache la barre d'action pour avoir le splash sur tout l'écran
        getSupportActionBar().hide();

        // On laisse le splash pendant 3 secondes puis on passe à l'activité d'accueil (Liste des annonces)
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}
