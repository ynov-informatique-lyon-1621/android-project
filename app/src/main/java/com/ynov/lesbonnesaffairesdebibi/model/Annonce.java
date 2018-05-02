package com.ynov.lesbonnesaffairesdebibi.model;

import java.io.Serializable;
import java.util.Locale;

public class Annonce implements Serializable {

    private Integer id;
    private String nomVendeur;
    private String email;
    private String mdp;
    private String titre;
    private String localisation;
    private String categorie;
    private Float prix;
    private String description;
    private String image;
    private String dateCreation;
    private Boolean favorite = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomVendeur() {
        return nomVendeur;
    }

    public void setNomVendeur(String nomVendeur) {
        this.nomVendeur = nomVendeur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getPrix() {
        return String.format(Locale.FRENCH, "%,.2f €", prix);
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image.replace("src/main/resources/static/", "http://139.99.98.119:8080/");
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getFavorite() { return favorite; }

    public void setFavorite(Boolean favorite) { this.favorite = favorite; }
}
