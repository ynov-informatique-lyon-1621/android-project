package fr.lesbonnesaffairesdebibi.prjandroid.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.lesbonnesaffairesdebibi.prjandroid.Activity.AnnonceDetailActivity;
import fr.lesbonnesaffairesdebibi.prjandroid.Activity.ReponseValideActivity;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Reponse;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class PostReponse extends AsyncTask<Reponse, String, String> {

    WeakReference<Activity> weakActivity;
    Annonce annonce;

    public PostReponse(Activity activity, Annonce annonce) {
        weakActivity = new WeakReference<Activity>(activity);
        this.annonce = annonce;
    }

    @Override
    protected String doInBackground(Reponse... reponses) {

        JSONObject newEntry = new JSONObject();
        OutputStream outputStream;

        try {
            //formation de l'url avec les informations entrées par l'utilisateur.
            URL url = new URL("http://139.99.98.119:8080/sendMessage");

            //ouverture de la connection au serveur via l'url.
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //définition de la méthode post:
            httpURLConnection.setRequestMethod("POST");
            //on précise le type de contenu transmis (ici, du json.)
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            //ajout des différents attributs de la nouvelle entrée au Json.
            newEntry.put("nom", reponses[0].getNom());
            newEntry.put("email", reponses[0].getEmail());
            newEntry.put("numero", reponses[0].getNumero());
            newEntry.put("message", reponses[0].getMessage());

            //envoi du json au serveur
            writer.write(newEntry.toString());
            writer.flush();
            writer.close();
            httpURLConnection.connect();

            //récupération & traitement de la réponse du serveur
            int responseCode = httpURLConnection.getResponseCode();

            Log.i("responseCode", String.valueOf(responseCode));

            if (responseCode == 201) {
                //Création d'un toast (alert) sur l'écran pour dire que l'entrée à ete ajouté
                weakActivity.get().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(
                                weakActivity.get().getBaseContext(),
                                "Envoie de la réponse réussi",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });

                Intent intent = new Intent(weakActivity.get().getBaseContext(), ReponseValideActivity.class);
                intent.putExtra("detail", annonce);
                weakActivity.get().startActivity(intent);
            } else {
                //Création d'un toast (alert) sur l'écran pour dire que l'entrée n'a pas pu être créée
                weakActivity.get().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(
                                weakActivity.get().getBaseContext(),
                                "Erreur lors de l'envoie de la réponse",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
