package fr.lesbonnesaffairesdebibi.prjandroid.Controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lesbonnesaffairesdebibi.prjandroid.Activity.ListeAnnonceUtilisateurActivity;
import fr.lesbonnesaffairesdebibi.prjandroid.Adapter.ListEntreeAdapter;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class GetAllAnnonce extends AsyncTask<String, String, String> {
    //Lien entre notre controller et notre activité nous permettant d'utiliser les fonctions et
    private WeakReference<Activity> weakActivity;
    String txtUrl ;
    String mod;

    public GetAllAnnonce(Activity activity, String motsCles, String categorie, String localisation) {
        weakActivity = new WeakReference<Activity>(activity);
        txtUrl = weakActivity.get().getString(R.string.urlEndPoint) +"/findAnnonces";
        if (!TextUtils.isEmpty(motsCles) || !TextUtils.isEmpty(categorie) || !TextUtils.isEmpty(localisation)) {
            txtUrl += "?";
            int count = 0;
            if (!TextUtils.isEmpty(motsCles)) {
                txtUrl += "motCle=" + motsCles;
                count++;
            }
            if (!TextUtils.isEmpty(categorie)) {
                if (count > 0) {
                    txtUrl += "&";
                }
                txtUrl += "categorie=" + categorie;
                count++;
            }
            if (!TextUtils.isEmpty(localisation)) {
                if (count > 0) {
                    txtUrl += "&";
                }
                txtUrl += "localisation=" + localisation;
            }
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            //Parse de la string de réponse en objet JSON Array
            JSONArray arr = new JSONArray(s);
            //Création d'un tableau contenant toute les reponses
            ArrayList<Annonce> arrayOfEntrees = new ArrayList<Annonce>();

            for (int i = 0; i < arr.length(); i++) {
                //Instanciation des entités
                Annonce annonce = new Annonce();
                annonce.setId(arr.getJSONObject(i).getString("id"));
                annonce.setTitre(arr.getJSONObject(i).getString("titre"));
                annonce.setCategorie(arr.getJSONObject(i).getString("categorie"));
                annonce.setEmail(arr.getJSONObject(i).getString("email"));
                annonce.setMdp(arr.getJSONObject(i).getString("mdp"));
                annonce.setDescription(arr.getJSONObject(i).getString("description"));
                annonce.setPrix((double)((int)(arr.getJSONObject(i).getDouble("prix") * 100)) / 100);
                annonce.setLocalisation(arr.getJSONObject(i).getString("localisation"));

                annonce.setNomVendeur(arr.getJSONObject(i).getString("nomVendeur"));

                try {
                    PrettyTime p = new PrettyTime(Locale.FRENCH);
                    Date myDate = new Date(new Long(arr.getJSONObject(i).getLong("dateCreation")));
                    annonce.setDateCreation(p.format(myDate));
                } catch (Exception e) {
                    annonce.setDateCreation("");
                }

                annonce.setImage(arr.getJSONObject(i).getString("image").replace("src/main/resources/static/images/lesbonsplansdebibi/", ""));

                //Stockage dans le tableau
                arrayOfEntrees.add(annonce);
            }

            //Création d'un adaptateur pour l'affichage de nos entités
            ListEntreeAdapter entreeListAdapter = new ListEntreeAdapter(weakActivity.get().getBaseContext(),"fav", arrayOfEntrees);
            //Création de la list view cible
            ListView listView = weakActivity.get().findViewById(R.id.listAnnonce);
            //Lien avec l'adaptateur
            listView.setAdapter(entreeListAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection httpURLConnection = null;
        StringBuilder stringBuilder = new StringBuilder();
        String contenu = "";
        try {
            //Instantciation de l'URL
            Log.i("debug url", txtUrl);
            URL url = new URL(txtUrl);

            //Création de la connection / requete HTTP
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //Récupération de la réponse HTTP dans un Input stream pour pouvoir lire la réponse
            InputStream in = httpURLConnection.getInputStream();
            //Mise en place d'un buffer pour pouvoir lire chaque ligne de la réponse
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //lecture de chaque ligne de la réponse
            while ((contenu = reader.readLine()) != null) {
                //ajout de chaque lignes dans notre string pour pouvoir la parser de string à objet
                stringBuilder.append(contenu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
