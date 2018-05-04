package com.example.travail.esparel.model;

public class AnnonceModel {
    private String titre;
    private String prix;
    private String date;
    private String categorie;
    private String description;
    private String vendeur;
    private String image;
    private String email;
    private String password;
    private String id;

    public AnnonceModel (String initId, String initV, String initEmail, String initPw, String initG, String initT, String initP,  String initDescription) {
        id = initId;
        vendeur = initV;
        email = initEmail;
        password = initPw;
        categorie = initG;
        titre = initT;
        prix = initP;
        description = initDescription;
    }

    public AnnonceModel(){

    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }



    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }



    public String getPrix() {
        return prix;
    }
    public void setPrix(String prix) {
        this.prix = prix;
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



    public String getVendeur() {return vendeur; }
    public void setVendeur(String vendeur) {
        this.vendeur = vendeur;
    }

    public String getImage() { return image; }
    public void setImage(String image) {
        this.image = image;
    }


}

