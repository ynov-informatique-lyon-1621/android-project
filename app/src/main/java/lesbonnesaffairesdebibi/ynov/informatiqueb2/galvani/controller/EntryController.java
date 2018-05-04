package lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.R;
import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.adapter.EntryAdapter;
import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.model.EntryList;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class EntryController extends AsyncTask<String, String, String> {

    private WeakReference<Activity> weakActivity;

    public EntryController(Activity activity) {
        weakActivity = new WeakReference<>(activity);
    }

    private StringBuilder stringBuilder;

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection httpUrlConnection;
        String contenu;
        try {
            // Connexion à l'API
            URL url = new URL("http://139.99.98.119:8080/findAnnonces");

            httpUrlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = httpUrlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            stringBuilder = new StringBuilder();

            while ((contenu = reader.readLine()) != null) {
                stringBuilder.append(contenu);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        
        // On récupère les données dans un tableau
        
        JSONArray arr;
        List<EntryList> viewlists = new ArrayList<>();
        EntryList entry;
        try {
            arr = new JSONArray(s);
            for (int i = 0; i < arr.length(); i++) {
                entry = new EntryList();
                entry.setNomVendeur(arr.getJSONObject(i).getString("nomVendeur"));
                entry.setEmail(arr.getJSONObject(i).getString("email"));
                entry.setTitre(arr.getJSONObject(i).getString("titre"));
                entry.setDescription(arr.getJSONObject(i).getString("description"));
                entry.setLocalisation(arr.getJSONObject(i).getString("localisation"));
                entry.setCategorie(arr.getJSONObject(i).getString("categorie"));
                entry.setPrix(arr.getJSONObject(i).getString("prix"));
                entry.setPicture(arr.getJSONObject(i).getString("image"));
                entry.setDate(arr.getJSONObject(i).getString("dateCreation"));
                viewlists.add(entry);
            }


            EntryAdapter a = new EntryAdapter(weakActivity.get().getBaseContext(), 0, viewlists);

            ListView listView = weakActivity.get().findViewById(R.id.ListEntry );
            listView.setAdapter(a);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
