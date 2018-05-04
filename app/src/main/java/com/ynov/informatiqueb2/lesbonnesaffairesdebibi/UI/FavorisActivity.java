package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Adapter.AdapterFavoris;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

public class FavorisActivity extends com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI.Menu {
//Activity affichant les favoris. Nous ne sommes pas parvenue à coder correctement la partie favoris.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);


        AdapterFavoris adapter = new AdapterFavoris(FavorisActivity.this,0);
    }
}
