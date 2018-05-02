package com.ynov.lesbonnesaffairesdebibi.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavoriteService {

    private Context mContext;
    private SharedPreferences sp;

    private Gson gson = new Gson();
    private List<Annonce> favoritesList = new ArrayList<Annonce>();

    public FavoriteService(Context context) {
        mContext = context;

        sp = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        String favorites = sp.getString("favorites", null);

        if(favorites != null) {
            favoritesList = gson.fromJson(favorites, new TypeToken<List<Annonce>>(){}.getType());
        }
    }

    public void draw(Annonce annonce, ImageButton button) {
        for(Iterator<Annonce> iter = favoritesList.listIterator(); iter.hasNext();) {
            Annonce ann = iter.next();
            if(ann.getId().equals(annonce.getId())) {
                button.setImageResource(R.drawable.ic_favorite_black_24dp);
                annonce.setFavorite(true);
            }
        }
    }

    public void click(Annonce annonce, ImageButton button) {

        if(annonce.getFavorite()) {

            button.setImageResource(R.drawable.ic_favorite_border_black_24dp);

            for(Iterator<Annonce> iter = favoritesList.listIterator(); iter.hasNext();) {
                Annonce ann = iter.next();
                if(ann.getId().equals(annonce.getId())) {
                    iter.remove();
                }
            }

            Toast.makeText(mContext, "Annonce retirée des favoris : " + annonce.getTitre(), Toast.LENGTH_SHORT).show();

        } else {

            button.setImageResource(R.drawable.ic_favorite_black_24dp);
            favoritesList.add(annonce);

            Toast.makeText(mContext, "Annonce ajoutée aux favoris : " + annonce.getTitre(), Toast.LENGTH_SHORT).show();

        }

        sp.edit().putString("favorites", gson.toJson(favoritesList)).apply();

    }

    public List<Annonce> getList() {
        return favoritesList;
    }

}
