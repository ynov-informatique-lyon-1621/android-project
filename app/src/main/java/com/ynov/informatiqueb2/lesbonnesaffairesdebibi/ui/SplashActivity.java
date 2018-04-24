package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        Timer timer = new Timer();
        timer.schedule(navigate,3000);

    }

    private TimerTask navigate = new TimerTask() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, AnnouncementListActivity.class);
            startActivity(intent);
        }
    };
}
