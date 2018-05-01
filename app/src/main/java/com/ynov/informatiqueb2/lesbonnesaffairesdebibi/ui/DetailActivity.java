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
        description.setText(announcement.getDescription());

        TextView title = findViewById(R.id.titleDsp);
        title.setText(announcement.getTitre());

        TextView categorie = findViewById(R.id.categorieDsp);
        categorie.setText(announcement.getCategorie());

        ImageView image = findViewById(R.id.image);
        Glide.with(this).load(announcement.getImage()).into(image);

        TextView vendorName = findViewById(R.id.vendorNameDsp);
        vendorName.setText(announcement.getNomVendeur());


        //        setContentView(R.layout.activity_detail);
//        Toolbar myToolbar = findViewById(R.id.toolbar);
//        this.setSupportActionBar(myToolbar);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }
}
