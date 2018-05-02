package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
        // Splash Screen
public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Affiche la page du Splash Screen pendant 3 secondes puis passe au Dashboard
        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash = new Intent(ActivitySplashScreen.this, ActivityDashboard.class);
                startActivity(splash);
                finish();
            }
        }, SPLASH_TIME_OUT);

        // Va chercher l'image de l'écran de chargement
        ImageView gif = findViewById(R.id.gifId);
        // Va chercher le gif de l'écran de chargement
        Glide.with(ActivitySplashScreen.this).load(R.drawable.giphy).into(gif);

    }
}
