package com.example.travail.esparel.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.travail.esparel.HTTP.HTTP;
import com.example.travail.esparel.UI.DeposerActivity;
import com.example.travail.esparel.UI.MainActivity;
import com.example.travail.esparel.model.AnnonceModel;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

public class AnnonceController extends AsyncTask<String,String,String> {

    WeakReference<DeposerActivity> weakActivity;

    public  AnnonceController(DeposerActivity activity) {
        this.weakActivity = new WeakReference<DeposerActivity>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // doInBackground
    protected String doInBackground (String... params) {
        AnnonceModel newPerso = new AnnonceModel("" ,params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
        return HTTP.createNewAnnonce(newPerso);
    }

    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(this.weakActivity.get(), "Annonce created successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this.weakActivity.get(), MainActivity.class);
        this.weakActivity.get().startActivity(intent);
    }
}
