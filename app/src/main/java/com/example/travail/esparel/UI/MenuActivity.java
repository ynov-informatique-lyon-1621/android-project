package com.example.travail.esparel.UI;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travail.esparel.Controller.ImageControleller;
import com.example.travail.esparel.R;

public class MenuActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    TextView title;
    ImageView assKicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        title = findViewById(R.id.titleSplash);
        assKicker = findViewById(R.id.assKicker);

        title.setText("LesBonnesAffairesDeBibi");
        new ImageControleller((ImageView)assKicker)
                .execute("http://dansmonlabo.com/wp-content/uploads/2015/07/5P8-ghRvYE8.jpg");



        countDownTimer = new CountDownTimer(3000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent SplashIntent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(SplashIntent);

            }


        }.start();


    }
}