package com.ynov.bibi.bibi.UI;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;
import com.ynov.bibi.bibi.R;
import com.ynov.bibi.bibi.Services.GetData;

import static com.ynov.bibi.bibi.StaticClass.SupplyDepot.connected;

/*
* SplashScreenActivity:
*   Activité gérant la première page de l'application
* */
public class SplashScreenActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreenActivity.this);

        /*
        * Si nous sommes déjà connecté l'application nous met sur connecté par défaut.
        * */
        if (sharedPreferences.getString("login", "") != "" && sharedPreferences.getString("pwd", "") != "")
        {
            connected = true;
        }

        //Librairie nous permettant de mettre des loader fluide.
        AVLoadingIndicatorView loader = findViewById(R.id.avi);
            loader.show();

        //nous récupérons les donnés du serveur.
        new GetData().get(this);
    }
}
