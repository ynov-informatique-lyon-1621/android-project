package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;





public class ConfirmationMessageActivity extends AppCompatActivity {

    ImageView imageContact;
    TextView titreConfirm;
    TextView prixConfirm;
    TextView vendeurConfirm;
    TextView catConfirm;
    TextView dateConfirm;
    TextView messageConfirm;

    String image;
    String titre;
    String vendeur;
    String categorie;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_message);

        Intent intentConfirm = getIntent();
        image = intentConfirm.getStringExtra("image");
        titre = intentConfirm.getStringExtra("image");
        vendeur = intentConfirm.getStringExtra("image");
        categorie = intentConfirm.getStringExtra("image");
        date = intentConfirm.getStringExtra("image");





    }
}
