package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;

public class DetailActivity extends BaseActivity {

    Announcement announcement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        announcement = (Announcement)intent.getSerializableExtra("annoucement");

        TextView description = findViewById(R.id.descDsp);
        TextView title = findViewById(R.id.titleDsp);
        TextView categorie = findViewById(R.id.categorieDsp);
        ImageView image = findViewById(R.id.image);
        TextView vendorName = findViewById(R.id.vendorNameDsp);

        description.setText(announcement.getDescription());
        title.setText(announcement.getTitre());
        categorie.setText(announcement.getCategorie());
        Glide.with(this).load(announcement.getImage()).into(image);
        vendorName.setText(announcement.getNomVendeur());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }
}
