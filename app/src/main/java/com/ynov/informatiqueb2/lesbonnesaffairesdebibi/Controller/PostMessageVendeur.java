package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI.ConfirmationMessageActivity;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI.ContacterVendeurActivity;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostMessageVendeur extends AsyncTask<String,String,String> {
    String nom;
    String nomVendeur;
    String email;
    String telephone;
    String message;
    String idAnnonce;
    WeakReference<Activity> weakActivity;

    //On déclare une weakActivity pour pouvoir déclare dans quel contexte on utilise la méthode Authentification.
    public PostMessageVendeur(Activity activity) {
        weakActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected String doInBackground(String... strings) {
        //On donne une variable aux arguements récupérées lors de l'appel de notre méthode
        idAnnonce = strings[0];
        nom = strings[1];
        nomVendeur = strings[2];
        email = strings[3];
        telephone = strings[4];
        message = strings[5];


        try {
            URL url = new URL("http://139.99.98.119:8080/sendMessage "); //On indique  l'URL du WebService pour notre POST.
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection(); //On ouvre une connexion.
            httpURLConnection.setRequestMethod("POST"); //On indique ici quel type de requête on souhaite effectuer, ici c'est une requête POST.
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); // Ici, on definie le `Content-Type` pour les données qu'on envoi `application/json`
            //On set nos OutPut et InPut à true.
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            //On crée un objet JSON, et on ajouté nos attributs récupéré précédemment dans notre tableau strings.
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idAnnonce", idAnnonce);
            jsonObject.put("nom", nom);
            jsonObject.put("nomVendeur", nomVendeur);
            jsonObject.put("email", email);
            jsonObject.put("numeroTelephone", telephone);
            jsonObject.put("message", message);
            // On decalre notre bufferWrite pour ecrire sur le serveur
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter((httpURLConnection.getOutputStream())));
            wr.write(jsonObject.toString()); //On ecrit, en passant notre objet en string.
            wr.flush();//On nettoie les données
            wr.close();//On ferme la connexion

            httpURLConnection.connect();//On envoie sur le serveur

            //On recupère le code reponse du serveur
            int responseCode=httpURLConnection.getResponseCode();
            //Si le serveur retourne le code 200, on fais un log OK, sinon, un log FAIL.
            if (responseCode == 201) {
                Log.e("POST CONFIRM", "Envoi OK");
                Intent intentConfirmMsg = new Intent(weakActivity.get().getBaseContext(), ConfirmationMessageActivity.class);
                intentConfirmMsg.putExtra("nomVendeur",nomVendeur);
                intentConfirmMsg.putExtra("image", ((ContacterVendeurActivity)weakActivity.get()).image);
                intentConfirmMsg.putExtra("titre", ((ContacterVendeurActivity)weakActivity.get()).titre);
                intentConfirmMsg.putExtra("date", ((ContacterVendeurActivity)weakActivity.get()).date);
                intentConfirmMsg.putExtra("prix", ((ContacterVendeurActivity)weakActivity.get()).prix );
                intentConfirmMsg.putExtra("categorie", ((ContacterVendeurActivity)weakActivity.get()).categorie );
                weakActivity.get().startActivity(intentConfirmMsg);
            }
            else {
                Log.e("POST CONFIRM", "Envoi FAIL");
                Toast.makeText(weakActivity.get().getBaseContext(), "Une erreur est survenue, veuilliez réessayer plus tard", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            //Gestion des exceptions
            Log.e("POST ERROR", "Error", e);
        }




        return null;
    }
}
