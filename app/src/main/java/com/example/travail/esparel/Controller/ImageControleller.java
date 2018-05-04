package com.example.travail.esparel.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ImageControleller extends AsyncTask<String,Void,Bitmap> {

    ImageView bmImage;

    public ImageControleller(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        //On récupère notre URL passé en arguement
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        URL url = null;

        try {
            //On ouvre un stream pour recuperer le flux de donné
            InputStream in = new java.net.URL(urldisplay).openStream();
            //On decode notre flux de donné
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            //Gestion des exceptions
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return mIcon11;

    }

    protected void onPostExecute(Bitmap result) {
        //Dans notre onPostExecute, on set notre image dans notre layout (passé en paramètre)
        bmImage.setImageBitmap(result);
    }
}

