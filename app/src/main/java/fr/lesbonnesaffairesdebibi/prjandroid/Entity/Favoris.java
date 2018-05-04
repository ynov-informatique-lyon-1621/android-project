package fr.lesbonnesaffairesdebibi.prjandroid.Entity;

import java.util.ArrayList;

public class Favoris {
    private static Favoris instance;
    private ArrayList<Annonce> favoris = new ArrayList<>();

    public static Favoris getInstance() {
        if (instance == null)
            instance = new Favoris();
        return instance;
    }

    private Favoris() {
    }

    public ArrayList<Annonce> getFavoris() {
        return favoris;
    }

    public boolean addFavoris(Annonce a){
        for(int y=0; y < favoris.size();y++){
            if(a.getId().equals(favoris.get(y).getId())){
                return false;
            }
        }
        favoris.add(a);
        return true;
    }
    public boolean delFavoris(Annonce a){
        for(int y=0; y < favoris.size();y++){
            if(a.getId().equals(favoris.get(y).getId())){
                favoris.remove(y);
                return true;
            }
        }
        return false;
    }
    public boolean isInFavoris(Annonce a){
        for(int y=0; y < favoris.size();y++){
            if(a.getId().equals(favoris.get(y).getId())){
                return true;
            }
        }
        return false;
    }

}
