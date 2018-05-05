package com.example.ynov.lesbonnesaffairesdebibi.Converter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.ynov.lesbonnesaffairesdebibi.Model.Annonce;
import com.example.ynov.lesbonnesaffairesdebibi.Model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Timestamp;

/**
 * Created by Thoma on 04/05/2018.
 */

public class CallAPI extends AsyncTask<String, String, String> {

    private Context mContext;


    public CallAPI(Context context){
        mContext = context;
        //set context variables if required
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... params) {

        try {
            // Creating & connection Connection with url and required Header.


            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("header-param_3", "value-3");
            urlConnection.setRequestProperty("header-param_4", "value-4");
            urlConnection.setRequestProperty("Authorization", "Basic Y2tfNDIyODg0NWI1YmZiZT1234ZjZWNlOTA3ZDYyZjI4MDMxY2MyNmZkZjpjc181YjdjYTY5ZGM0OTUwODE3NzYwMWJhMmQ2OGQ0YTY3Njk1ZGYwYzcw");
            urlConnection.setRequestMethod(params[1]);   //POST or GET
            urlConnection.connect();


            // Create JSONObject Request
            JSONObject jsonRequest = new JSONObject(params[2]);
            Log.d(params[2], jsonRequest.toString());
            // Write Request to output stream to server.
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonRequest.toString());
            out.close();

            // Check the connection status.
            int statusCode = urlConnection.getResponseCode();
            String statusMsg = urlConnection.getResponseMessage();

            // Connection success. Proceed to fetch the response.
            if (statusCode == 200 || statusCode == 201) {
                InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader read = new InputStreamReader(it);
                BufferedReader buff = new BufferedReader(read);
                StringBuilder dta = new StringBuilder();
                String chunks;
                while ((chunks = buff.readLine()) != null) {
                    dta.append(chunks);
                }
                String returndata = dta.toString();
                return returndata;
            } else {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(mContext, "Erreur lors de l'appel au webservice !", Toast.LENGTH_LONG).show();
                    }
                });

                return "Error";
                //Handle else cases
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
