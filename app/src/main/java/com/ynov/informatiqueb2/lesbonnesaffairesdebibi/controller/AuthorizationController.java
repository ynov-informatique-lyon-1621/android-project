package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app.ActivityDashboard;

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
        HttpURLConnection httpUrlConnection;
        String contend;
        try {
            URL url = new URL("http://139.99.98.119:8080/findAnnonces?" +
                    "nomVendeur=" + strings[0] + "&mdp=" + strings[1]);

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

            Log.i("START ACTIVITY", "Connexion réussie");
            weakActivity.get().startActivity(intent);

        } else {
            Toast.makeText(weakActivity.get().getBaseContext(), "Votre email ou mot de passe ne correspond pas", Toast.LENGTH_LONG).show();
        }


    }
}
