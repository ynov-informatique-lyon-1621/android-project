package com.ynov.bibi.bibi.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;
import com.ynov.bibi.bibi.R;
import com.ynov.bibi.bibi.Services.GetData;

import static com.ynov.bibi.bibi.StaticClass.SupplyDepot.connected;

public class SplashScreenActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreenActivity.this);

        if (sharedPreferences.getString("login", "") != "" && sharedPreferences.getString("pwd", "") != "")
        {
            connected = true;
        }

        AVLoadingIndicatorView loader = findViewById(R.id.avi);
            loader.show();

            new GetData().get(this);
    }
}
