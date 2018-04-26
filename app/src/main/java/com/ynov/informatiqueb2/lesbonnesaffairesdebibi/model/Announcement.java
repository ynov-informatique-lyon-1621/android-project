package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model;


import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiInterface;

import java.io.Serializable;
//
//"id": 1,
//        "nomVendeur": "AssKicker",
//        "email": "asskicker@ynov.com",
//        "mdp": "asskicker",
//        "titre": "A vendre Polos Lacoste toutes tailles (neuf)",
//        "localisation": "Lyon",
//        "categorie": "Vêtements",
//        "prix": 35,
//        "description": "Bonjour,\nJe vends des polos Lacoste neufs.\n35 euros l'unité.\nBonne journée.",
//        "image": "src/main/resources/static/images/lesbonsplansdebibi/polo.jpeg"

public class Announcement implements Serializable {
    private String id = null;
    private String email = "";
    private String mdp = "";
    private String titre = "";
    private String description = "";
    private Integer prix = 0;
    private String categorie = "";
    private String image = "";
    private String nomVendeur = "";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImage() {
        return ApiInterface.ENDPOINT  +  image.replace("src/main/resources/static","");
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNomVendeur() {
        return nomVendeur;
    }

    public void setNomVendeur(String nomVendeur) {
        this.nomVendeur = nomVendeur;
    }
}
