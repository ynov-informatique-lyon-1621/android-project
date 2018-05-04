package lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.R;

public class SplashScreenActivity extends AppCompatActivity {
    
    // Class SplashScreen pour afficher un SplashScreen quand l'application se lance et reste 3sec avant de changer pour la Homepage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        // SplashScreenActivity de 3sec
        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashscreen = new Intent(SplashScreenActivity.this, HomepageActivity.class);
                startActivity(splashscreen);
                finish();
            }
        }, SPLASH_TIME_OUT);


        // Utilisation de Glide pour les 2 gifs

        ImageView gif = findViewById(R.id.gifId);
        Glide.with(SplashScreenActivity.this).load(R.drawable.blue_loading).into(gif);
        ImageView travolta = findViewById(R.id.travoltaId);
        Glide.with(SplashScreenActivity.this).load(R.drawable.travolta).into(travolta);

    }
}
