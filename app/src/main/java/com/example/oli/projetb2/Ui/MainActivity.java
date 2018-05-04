package com.example.oli.projetb2.Ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import com.example.oli.projetb2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Timer().schedule(new TimerTask() {
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(new Intent(MainActivity.this, ListAdvertActivity.class));
                    }
                });
            }
        }, 3000);
    }
}
