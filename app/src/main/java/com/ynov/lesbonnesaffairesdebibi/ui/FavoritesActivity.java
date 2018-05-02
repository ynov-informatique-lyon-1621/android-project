package com.ynov.lesbonnesaffairesdebibi.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.adapter.AnnonceAdapter;
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;
import com.ynov.lesbonnesaffairesdebibi.service.FavoriteService;

import java.util.List;

public class FavoritesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Favoris");

        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_favorites, contentLayout);

        ListView listFavorites = findViewById(R.id.listFavorites);
        FavoriteService favoriteService = new FavoriteService(FavoritesActivity.this);
        List<Annonce> favoritesList = favoriteService.getList();

        if(favoritesList.size() > 0) {
            AnnonceAdapter adapter = new AnnonceAdapter(FavoritesActivity.this, favoritesList);
            listFavorites.setAdapter(adapter);
        } else {
            ((TextView) findViewById(R.id.noFavoriteText)).setVisibility(View.VISIBLE);
        }

    }
}
