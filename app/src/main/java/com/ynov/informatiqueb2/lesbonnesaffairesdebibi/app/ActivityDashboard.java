package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.controller.GetViewController;


public class ActivityDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle("Dashboard");

        // Execution du 'GetViewController' sur le ActivityDashboard
        new GetViewController(ActivityDashboard.this).execute();
    }
}
