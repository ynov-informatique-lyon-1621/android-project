package fr.lesbonnesaffairesdebibi.prjandroid.Controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteAnnonce extends AsyncTask<String, String, String> {

    public DeleteAnnonce() {
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection httpURLConnection = null;
        StringBuilder stringBuilder = new StringBuilder();
        String contenu = "";
        try {
            //Instantciation de l'URL
            URL url = new URL("http://139.99.98.119:8080/deleteAnnonce?id=" + strings[0]);

            //Création de la connection / requete HTTP
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //définition de la méthode post:
            httpURLConnection.setRequestMethod("DELETE");
            //Récupération de la réponse HTTP dans un Input stream pour pouvoir lire la réponse
            InputStream in = httpURLConnection.getInputStream();
            //Mise en place d'un buffer pour pouvoir lire chaque ligne de la réponse
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //lecture de chaque ligne de la réponse
            while ((contenu = reader.readLine()) != null) {
                //ajout de chaque lignes dans notre string pour pouvoir la parser de string à objet
                stringBuilder.append(contenu);
            }

            //récupération & traitement de la réponse du serveur
            int responseCode = httpURLConnection.getResponseCode();
            Log.i("responseCode", String.valueOf(responseCode));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}