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

        // On initialise le service SharedPreferences qui permet l'accès à la mémoire et on récupère la valeur "favorites"
        sp = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        String favorites = sp.getString("favorites", null);

        if(favorites != null) {
            // Si la valeur n'est pas null, on transforme le JSON contenu en liste d'objets Annonce
            favoritesList = gson.fromJson(favorites, new TypeToken<List<Annonce>>(){}.getType());
        }
    }

    public void draw(Annonce annonce, ImageButton button) {
        // Pour afficher si l'annonce est en favori ou non
        // On itère sur la liste de favoris définie dans le constructeur
        for(Iterator<Annonce> iter = favoritesList.listIterator(); iter.hasNext();) {
            Annonce ann = iter.next();
            if(ann.getId().equals(annonce.getId())) {
                // Si l'ID de l'annonce passée en paramètre correspond à un ID dans la liste de favoris, on définit l'annonce
                // comme étant en favori et on affiche l'icone correspondante
                button.setImageResource(R.drawable.ic_favorite_black_24dp);
                annonce.setFavorite(true);
            }
        }
    }

    public void click(Annonce annonce, ImageButton button) {
        // Pour gérer le clic sur un bouton favori
        if(annonce.getFavorite()) {
            // Si l'annonce est déjà en favori, on la supprime des favoris
            button.setImageResource(R.drawable.ic_favorite_border_black_24dp);

            // On itère sur la liste de favoris définie dans le constructeur
            for(Iterator<Annonce> iter = favoritesList.listIterator(); iter.hasNext();) {
                Annonce ann = iter.next();
                if(ann.getId().equals(annonce.getId())) {
                    // Quand on tombe sur l'ID correspondant, on retire l'annonce de la liste
                    iter.remove();
                }
            }

            // On affiche un message confirmant la suppression
            Toast.makeText(mContext, "Annonce retirée des favoris : " + annonce.getTitre(), Toast.LENGTH_SHORT).show();

        } else {
            // Si l'annonce n'est pas encore en favoris, on l'ajoute aux favoris
            button.setImageResource(R.drawable.ic_favorite_black_24dp);

            // On ajoute l'annonce à la liste des favoris
            favoritesList.add(annonce);

            // On affiche un message confirmant l'ajout
            Toast.makeText(mContext, "Annonce ajoutée aux favoris : " + annonce.getTitre(), Toast.LENGTH_SHORT).show();

        }

        // On convertit la liste de favoris en JSON et on met à jour la valeur "favorites" dans la mémoire
        sp.edit().putString("favorites", gson.toJson(favoritesList)).apply();

    }

    public List<Annonce> getList() {
        // Retourne la liste des favoris
        return favoritesList;
    }

}
