package fr.lesbonnesaffairesdebibi.prjandroid.Controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class PostAnnonce extends AsyncTask<Annonce, String, String> {

    WeakReference<Activity> weakActivity;

    public PostAnnonce(Activity activity) {
        weakActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected String doInBackground(Annonce... annonces) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost("http://139.99.98.119:8080/saveAnnonce");
            Long tsLong = System.currentTimeMillis();

            String json = new JSONObject()
                    .put("nomVendeur", annonces[0].getNomVendeur())
                    .put("email", annonces[0].getEmail())
                    .put("mdp", annonces[0].getMdp())
                    .put("titre", annonces[0].getTitre())
                    .put("localisation", annonces[0].getLocalisation())
                    .put("categorie", annonces[0].getCategorie())
                    .put("prix", annonces[0].getPrix())
                    .put("dateCreation", tsLong)
                    .put("description", annonces[0].getDescription())
                    .toString();

            //create a file to write bitmap data
            File f = new File(weakActivity.get().getCacheDir(), "temp");

            //Convert bitmap to byte array
            ImageView image = weakActivity.get().findViewById(R.id.ImageGetter);
            image.buildDrawingCache();
            Bitmap bmap = image.getDrawingCache();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();


            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody("file", f, ContentType.DEFAULT_BINARY,"celestin.jpg");
            builder.addTextBody("annonce", json,ContentType.APPLICATION_JSON);

            final HttpEntity entity = builder.build();
            post.setEntity(entity);
            HttpResponse response = client.execute(post);

            //récupération & traitement de la réponse du serveur
            int responseCode = response.getStatusLine().getStatusCode();

            Log.i("responseCode", String.valueOf(responseCode));
            Log.i("responseCode", EntityUtils.toString(response.getEntity(), "UTF-8"));

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
