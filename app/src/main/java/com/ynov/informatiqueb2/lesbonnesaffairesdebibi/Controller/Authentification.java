package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI.MainActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class Authentification extends AsyncTask<String, String , String> {

    WeakReference<Activity> weakActivity;
    String login;
    String mdp;

    //On déclare une weakActivity pour pouvoir déclare dans quel contexte on utilise la méthode Authentification.
    public Authentification(Activity activity) {
        weakActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection httepURLConnection = null;
        String contenu = "";
        StringBuilder stringBuilder = null;

        //On indique ou se trouve le login et mot de passe dans le tableau strings
        login = strings[0];
        mdp = strings[1];
        //Nous faisons un try catch pour gérer les exceptions unchecked. C'est à dire les exceptions qui ne sont pas gérées par l'application.
        try {
            //On déclare ntore URL, on passe en login/mot de passe nos variables qui contiennent le login/mdp qu'on a recupe dans le MainActivity (passé en arguement dans l'appelle de méthode)
            URL url = new URL("http://thibault01.com:8081/authorization?" + "login=" + login + "&mdp=" + mdp);

            //On ouvre une connexion au WebService
            httepURLConnection = (HttpURLConnection) url.openConnection();

            //On récupère la réponse du serveur
            InputStream in = httepURLConnection.getInputStream();

            //On initialise notre bufferreader afin de lire la réponse du serveur
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            //Cela va servir à concatener la string qu'on recupère du serveur
            stringBuilder = new StringBuilder();


            while ((contenu = reader.readLine()) != null) {
                //Tant qu'il y'a des lignesn on récupère le content en les concatenant.
                stringBuilder.append(contenu);
            }

        } catch (Exception e) {
            //Gestion des exceptions
            e.printStackTrace();
            Log.e("Erreur Authentification","Mauvaise URL");
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //Traitement de la string reçu, si la réponse du serveur est "true", on crée notre intent entre le la MainActivity et IdentificationActivity.
        if (s.equals("true")) {
            Intent intentIdOk = new Intent(weakActivity.get().getBaseContext(),
                    MainActivity.class);

            Log.i("Auth OK", "Auth OK");
            Toast.makeText(weakActivity.get().getBaseContext(), "Bienvenue " + login, Toast.LENGTH_SHORT).show();
            weakActivity.get().startActivity(intentIdOk);
            //On termine l'activity d'identification
            weakActivity.get().finish();


        } else {
            //Si l'authentification est mauvaise, on lève un Toast pour notifier l'utilisateur.
            Toast.makeText(weakActivity.get().getBaseContext(), "Login ou Mot de passe incorrecte", Toast.LENGTH_SHORT).show();
        }
    }
}
