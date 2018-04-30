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

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash = new Intent(ActivitySplashScreen.this, ActivityDashboard.class);
                startActivity(splash);
                finish();
            }
        }, SPLASH_TIME_OUT);

        ImageView gif = findViewById(R.id.gifId);

        Glide.with(ActivitySplashScreen.this).load(R.drawable.giphy).into(gif);

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
        return super.onOptionsItemSelected(item);
    }


}
