package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.controller;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;


import java.io.BufferedWriter;
import java.io.OutputStreamWriter;



public class PostViewCreateController extends AsyncTask<String, String, String> {


    private StringBuilder stringBuilder;

    @Override
    protected String doInBackground(String... strings) {

        String nomVendeur = strings[0];
        String email = strings[1];
        String password = strings[2];
        String categorie = strings[3];
        String prix = strings[4];
        String localisation = strings[5];
        String titre = strings[6];
        String description = strings[7];

        HttpURLConnection httpUrlConnection;
        String contenu;
        try {
            // Connexion à l'API
            URL url = new URL("http://139.99.98.119:8080/saveAnnonce");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpUrlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = httpUrlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            stringBuilder = new StringBuilder();

            while ((contenu = reader.readLine()) != null) {
                stringBuilder.append(contenu);

            }

            JSONObject EditAdvert = new JSONObject();
            EditAdvert.put("nomVendeur", nomVendeur);
            EditAdvert.put("email", email);
            EditAdvert.put("mdp", password);
            EditAdvert.put("titre", titre);
            EditAdvert.put("localisation", localisation);
            EditAdvert.put("categorie", categorie);
            EditAdvert.put("prix", prix);
            EditAdvert.put("description", description);

            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter((httpURLConnection.getOutputStream())));
            wr.write(EditAdvert.toString());
            wr.flush();
            wr.close();

            int responseCode=httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
                Log.e("POST CONFIRM","Envoyer");
            else
                Log.e("POST CONFIRM","Raté");


        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}