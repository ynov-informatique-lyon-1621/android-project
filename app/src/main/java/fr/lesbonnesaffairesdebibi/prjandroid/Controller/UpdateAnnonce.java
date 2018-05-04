package fr.lesbonnesaffairesdebibi.prjandroid.Controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class UpdateAnnonce extends AsyncTask<Annonce, String, String> {

    WeakReference<Activity> weakActivity;

    public UpdateAnnonce(Activity activity) {
        weakActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected String doInBackground(Annonce... annonces) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPut post = new HttpPut("http://139.99.98.119:8080/updateAnnonce");
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            Long tsLong = System.currentTimeMillis();
            String json = new JSONObject()
                    .put("id", annonces[0].getId())
                    .put("nomVendeur", annonces[0].getNomVendeur())
                    .put("email", annonces[0].getEmail())
                    .put("mdp", annonces[0].getMdp())
                    .put("titre", annonces[0].getTitre())
                    .put("localisation", annonces[0].getLocalisation())
                    .put("categorie", annonces[0].getCategorie())
                    .put("prix", annonces[0].getPrix())
                    .put("dateCreation", tsLong)
                    .put("description", annonces[0].getDescription())
                    .put("image", "src/main/resources/static/images/lesbonsplansdebibi/" + annonces[0].getImage())
                    .toString();


            post.setEntity(new StringEntity(json, "UTF-8"));
            HttpResponse response = client.execute(post);

            //récupération & traitement de la réponse du serveur
            int responseCode = response.getStatusLine().getStatusCode();

            Log.i("responseCode", String.valueOf(responseCode));
            Log.i("responseCode", EntityUtils.toString(response.getEntity(), "UTF-8"));

            if (responseCode == 200) {
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
