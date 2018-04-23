package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.DownloadImage;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;


public class SplashScreenActivity extends AppCompatActivity {
    CountDownTimer countDownTimer;
    TextView title;
    ImageView assKicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        title = findViewById(R.id.titleSplash);
        assKicker = findViewById(R.id.assKicker);

        title.setText("LesBonnesAffairesDeBibi");
        new DownloadImage((ImageView)assKicker)
                .execute("https://cdn.pixabay.com/photo/2016/03/31/22/57/business-1297302_960_720.png");



        countDownTimer = new CountDownTimer(8000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent SplashIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(SplashIntent);

            }


        }.start();


    }
}
