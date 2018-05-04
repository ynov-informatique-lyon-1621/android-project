package fr.lesbonnesaffairesdebibi.prjandroid.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView imageLoader;
    TextView textEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageLoader = findViewById(R.id.ImageLoader);
        textEnd = findViewById(R.id.textViewChargement);

        Glide.with(SplashScreenActivity.this).load(R.drawable.loading).into(imageLoader);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                textEnd.setText("Ready");
                //Changement de l'activité pour aller sur la page List Annonce
                Intent intent = new Intent(SplashScreenActivity.this, ListAnnonceActivity.class);
                startActivity(intent);

            }
        }, 3000);
    }
}
