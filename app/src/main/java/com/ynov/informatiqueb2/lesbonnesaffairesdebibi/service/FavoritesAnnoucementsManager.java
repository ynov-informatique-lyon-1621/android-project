package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

public class FavoritesAnnoucementsManager {

    private SharedPreferences sharedPreferences;
    private WeakReference<Activity> activityWeakReference;

    public FavoritesAnnoucementsManager(Activity activity) {
        this(new WeakReference<Activity>(activity));
    }

    public FavoritesAnnoucementsManager(WeakReference<Activity> wr) {
        this.activityWeakReference = wr;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activityWeakReference.get());
    }

    public boolean isFav(String annoucementId) {
        Set<String> favorites =  this.sharedPreferences.getStringSet("favorites",new HashSet<String>());
        return favorites.contains(annoucementId);
    }

    private void setFav(String annoucementId) {
        Set<String> favorites =  this.sharedPreferences.getStringSet("favorites",new HashSet<String>());
        favorites.add(annoucementId);
        this.sharedPreferences
                .edit()
                .putInt("favorites_size",favorites.size())
                .putStringSet("favorites",favorites)
                .apply();
    }

    private void unsetFav(String announcementId) {
        Set<String> favorites =  this.sharedPreferences.getStringSet("favorites",new HashSet<String>());
        favorites.remove(announcementId);
        sharedPreferences
                .edit()
                .putInt("favorites_size",favorites.size())
                .putStringSet("favorites",favorites)
                .apply();
    }

    public void toggleFav(String annoucmentId) {
        if(isFav(annoucmentId)) {
            unsetFav(annoucmentId);
        } else {
            setFav(annoucmentId);
        }
    }

}
