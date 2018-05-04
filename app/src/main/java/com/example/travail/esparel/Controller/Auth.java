package com.example.travail.esparel.Controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.travail.esparel.R;
import com.example.travail.esparel.UI.IdentifiationActivity;
import com.example.travail.esparel.UI.MainActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class Auth extends AsyncTask<String, String , String> {

    WeakReference<IdentifiationActivity> weakActivity;

    public Auth(IdentifiationActivity activity) {
        this.weakActivity = new WeakReference<IdentifiationActivity>(activity);
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection urlConnection;

        String content = "";

        try {
            this.weakActivity.get().getResources().getString(R.string.app_name);
            url = new URL("http://thibault01.com:8081/authorization?login=" + strings[0] + "&mdp=" + strings[1]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            StringBuilder sb = new StringBuilder(); // +=

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((content = reader.readLine()) != null) {
                sb.append(content);
            }

            content = sb.toString();
        } catch (Exception ex) {
            Log.e("MON ERREUR", "Erreur lors de l'accès au WS", ex);
        }

        publishProgress(content);

        return content;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        if ("true".equals(values[0])) {
            SharedPreferences sharedPreferences = this.weakActivity.get().getApplicationContext().getSharedPreferences("LogInfo", this.weakActivity.get().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // Si la checkbox est coché met les info login et pwd dans le cache
            if (this.weakActivity.get().rememberMeCheckbox.isChecked()) {
                editor.putString("login", this.weakActivity.get().loginInput.getText().toString());
                editor.putString("pwd", this.weakActivity.get().passwordInput.getText().toString());
                editor.commit();
            } else {
                editor.putString("login", "");
                editor.putString("pwd", "");
                editor.commit();
            }
            // ouverture de MainActivity
            Intent intent = new Intent(this.weakActivity.get(), MainActivity.class);
            this.weakActivity.get().startActivity(intent);
        } else {
            Toast.makeText(this.weakActivity.get().getBaseContext(), "Utilisateur non authorisé", Toast.LENGTH_LONG).show();
        }
    }
}

