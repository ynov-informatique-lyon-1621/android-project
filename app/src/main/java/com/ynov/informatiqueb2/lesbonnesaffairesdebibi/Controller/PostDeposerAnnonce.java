package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostDeposerAnnonce extends AsyncTask<String,String,String> {

    String nom;
    String email;
    String pwd;
    String categorie;
    String prix;
    String titre;
    String description;

    @Override
    protected String doInBackground(String... strings) {
        //On récupère nos valeurs
        nom = strings[0];
        email = strings[1];
        pwd = strings[2];
        categorie = strings[3];
        prix = strings[4];
        titre = strings[5];
        description = strings[6];

        try {
            URL url = new URL("http://139.99.98.119:8080/saveAnnonce"); //On indique  l'URL du WebService pour notre POST.
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection(); //On ouvre une connexion.
            httpURLConnection.setRequestMethod("POST"); //On indique ici quel type de requête on souhaite effectuer, ici c'est une requête POST.
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); // Ici, on definie le `Content-Type` pour les données qu'on envoi `application/json`
            //On set nos OutPut et InPut à true.
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            //On crée un objet JSON, et on ajouté nos attributs récupéré précédemment dans notre tableau strings.
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nomVendeur", nom);
            jsonObject.put("email", email);
            jsonObject.put("mdp", pwd);
            jsonObject.put("titre", titre);
            jsonObject.put("localisation", "Lyon");
            jsonObject.put("categorie", categorie);
            jsonObject.put("prix", prix);
            jsonObject.put("description", description);
            jsonObject.put("dateCreation", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS"));
            // On decalre notre bufferWrite pour ecrire sur le serveur
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter((httpURLConnection.getOutputStream())));
            wr.write(jsonObject.toString()); //On ecrit, en passant notre objet en string.
            wr.flush();//On nettoie les données
            wr.close();//On ferme la connexion

            httpURLConnection.connect();//On envoie sur le serveur

            //On recupère le code reponse du serveur
            int responseCode=httpURLConnection.getResponseCode();
            //Si le serveur retourne le code 200, on fais un log OK, sinon, un log FAIL.
            if (responseCode == httpURLConnection.HTTP_OK)
                Log.e("POST CONFIRM","Envoi OK");
            else
                Log.e("POST CONFIRM","Envoi FAIL");


        } catch (Exception e) {
            //Gestion des exceptions
            Log.e("POST ERROR", "Error", e);
        }

        return null;
    }
}
