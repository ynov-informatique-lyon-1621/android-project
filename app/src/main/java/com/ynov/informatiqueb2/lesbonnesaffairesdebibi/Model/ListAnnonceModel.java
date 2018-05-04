package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Model;

import java.io.Serializable;

public class ListAnnonceModel implements Serializable {
    //Class permettant d'utiliser notre list Json dans le main activity.
    private String id;
    private String title;
    private String prix;
    private String date;
    private String categorie;
    private String description;
    private String vendeur;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendeur() {
        return vendeur;
    }

    public void setVendeur(String vendeur) {
        this.vendeur = vendeur;
    }

    public String getId() { return id; }

    public void setId(String id){ this.id = id; }
}




