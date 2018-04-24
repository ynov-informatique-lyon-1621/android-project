package com.ynov.informatiqueb2.lesbonnesaffairesdebibi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash = new Intent(ActivitySplashScreen.this, ActivityDashboard.class);
                startActivity(splash);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
