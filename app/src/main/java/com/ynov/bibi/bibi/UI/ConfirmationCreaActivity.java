package com.ynov.bibi.bibi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ynov.bibi.bibi.R;

public class ConfirmationCreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_crea);

        Button retourList = findViewById(R.id.btnRetour);

        retourList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retour = new Intent(ConfirmationCreaActivity.this, AdsListingActivity.class);
                startActivity(retour);
                finish();//redirection vers liste d'annonces
            }
        });
    }
}
