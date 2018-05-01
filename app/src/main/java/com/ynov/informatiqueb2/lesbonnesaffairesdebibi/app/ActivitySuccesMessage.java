package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

public class ActivitySuccesMessage extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_message);

        Button searchSuccess = findViewById(R.id.search);
        Button advertSuccess = findViewById(R.id.advert);

        TextView contactDetail = findViewById(R.id.recapContact);
        TextView emailDetail = findViewById(R.id.recapEmail);
        TextView phoneDetail = findViewById(R.id.recapTelephone);
        TextView messageDetail = findViewById(R.id.recapMessage);

        Intent recapMessage = getIntent();

        String contact = recapMessage.getStringExtra("recapContact");
        String email = recapMessage.getStringExtra("recapEmail");
        String phone = recapMessage.getStringExtra("recapTelephone");
        String message = recapMessage.getStringExtra("recapMessage");

        contactDetail.setText("Votre Nom: " + contact);
        emailDetail.setText("Votre Email: " + email);
        if (TextUtils.isEmpty(phone)) {
            phoneDetail.setText("Numéro de téléphone: Inconnue");
        } else {
            phoneDetail.setText("Numéro de téléphone: " + phone);
        }
        messageDetail.setText(message);

        ImageView imagesucces = findViewById(R.id.imageLocal);
        Glide.with(ActivitySuccesMessage.this).load(R.drawable.noun).into(imagesucces);

        searchSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySuccesMessage.this, ActivityDashboard.class);
                startActivity(intent);
            }
        });

        advertSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySuccesMessage.this, ActivityDashboard.class);
                startActivity(intent);

                Toast.makeText(ActivitySuccesMessage.this
                        , "Votre message a bien été envoyé au vendeur. " +
                                "L'équipe de lesbonnesaffairesdebibi.fr"
                        , Toast.LENGTH_LONG).show();
            }
        });
    }
}
