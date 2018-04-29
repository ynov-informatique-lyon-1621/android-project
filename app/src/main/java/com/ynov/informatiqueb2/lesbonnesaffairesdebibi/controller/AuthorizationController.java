package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app.ActivityDashboard;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthorizationController extends AsyncTask<String, String, String> {

    private WeakReference<Activity> weakActivity;

    public AuthorizationController(Activity activity) {
        weakActivity = new WeakReference<>(activity);
    }

    private StringBuilder stringBuilder;

    @Override
    protected String doInBackground(String... strings) {
        // Connexion à l'API
        HttpURLConnection httpUrlConnection;
        String contend;
        try {
            // Vérification des informations MDP & LOGIN
            URL url = new URL("http://thibault01.com:8081/authorization?" +
                    "login=" + strings[0] + "&mdp=" + strings[1]);

            httpUrlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = httpUrlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            stringBuilder = new StringBuilder();

            while ((contend = reader.readLine()) != null) {
                stringBuilder.append(contend);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equals("true")) {
            //Faire le traitement de la string reçu
            Intent intent = new Intent(weakActivity.get().getBaseContext(),
                    ActivityDashboard.class);

            //Récupération de l'ID
            EditText editText = weakActivity.get().findViewById(R.id.IDuser);

            //Remplace le 'login' par le nom de l'utilisateur
            intent.putExtra("login", editText.getText().toString());

            //Renvoie les informations dans le log
            Log.i("START ACTIVITY", "Connexion au Dashboard");
            weakActivity.get().startActivity(intent);

            //Affiche l'information 'Connexion réussie'
            Toast.makeText(weakActivity.get().getBaseContext(), "Connexion réussie", Toast.LENGTH_LONG).show();


        } else {
            // Affiche l'information 'Votre login ou mot de passe ne correspond pas'
            Toast.makeText(weakActivity.get().getBaseContext(), "Votre login ou mot de passe ne correspond pas", Toast.LENGTH_LONG).show();
        }
    }
}
