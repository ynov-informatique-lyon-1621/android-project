package Model;

import android.media.Image;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Map;


public class Advert {

    public int id;
    public String nomVendeur;
    public String email;
    public String mdp;
    public String titre;
    public String localisation;
    public String categorie;
    public int prix;
    public String descritpion;
    public int dateCreation;
    public ImageView imageView;
    public static String image;


    public Advert() {

    }

    public  Advert(String titre, String categorie, int prix) {
        this.titre = titre;
        this.categorie = categorie;
        this.prix = prix;
        //this.dateCreation = dateCreation;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomVendeur() { return nomVendeur; }
    public void setNomVendeur(String nomVendeur) { this.nomVendeur = nomVendeur; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMdp() { return mdp; }
    public void setMdp(String mdp) { this.mdp = mdp; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public int getPrix() { return prix; }
    public void setPrix(int prix) { this.prix = prix; }

    public String getDescritpion() { return descritpion; }
    public void setDescritpion(String descritpion) {this.descritpion = descritpion; }

    public int getDateCreation() { return dateCreation; }
    public void setDateCreation(int dateCreation) { this.dateCreation = dateCreation; }

    public ImageView getImageView() { return imageView; }
    public void setImageView(ImageView imageView) { this.imageView = imageView; }

    public void setImage(String image) { this.image = image; }
    public static String getImage() { return image; }
}
