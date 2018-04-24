package Controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

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

import Adapter.AdvertViewAdapter;
import Model.AdvertList;


public class GetViewController extends AsyncTask<String, String, String> {

    private WeakReference<Activity> weakActivity;

    public GetViewController(Activity activity) {
        weakActivity = new WeakReference<>(activity);
    }

    private StringBuilder stringBuilder;

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection httpUrlConnection;
        String contenu;
        try {
            // Connexion à l'API
            URL url = new URL("http://thibault01.com:8081/getEntree");

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

        JSONArray arr;
        List<AdvertList> viewlists = new ArrayList<>();
        AdvertList view;
        try {
            // Création du tableaux de l'affichage de liste
            arr = new JSONArray(s);
            for (int i = 0; i < arr.length(); i++) {
                // Récupération des information de la base données
                view = new AdvertList();
                view.setNom(arr.getJSONObject(i).getString("nom"));
                view.setEspece(arr.getJSONObject(i).getString("espece"));
                view.setSexe(arr.getJSONObject(i).getString("sexe"));
                view.setDescription(arr.getJSONObject(i).getString("description"));
                viewlists.add(view);
            }


            AdvertViewAdapter a = new AdvertViewAdapter(weakActivity.get().getBaseContext(), 0, viewlists);

            // Récupération de l'ID ListView et affichage sur le ListView
            ListView listView = weakActivity.get().findViewById(R.id.PanelView);
            listView.setAdapter(a);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}