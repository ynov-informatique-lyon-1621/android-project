package com.ynov.bibi.bibi.Services;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ynov.bibi.bibi.Interfaces.WebAPI;
import com.ynov.bibi.bibi.Models.Ad;
import com.ynov.bibi.bibi.UI.AdsListingActivity;
import com.ynov.bibi.bibi.UI.ConfirmationCreaActivity;
import com.ynov.bibi.bibi.UI.CreationAdsActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ynov.bibi.bibi.StaticClass.SupplyDepot._currentAds;
import static com.ynov.bibi.bibi.StaticClass.SupplyDepot.connected;


/*
* GetData:
*   Classe regroupant tous les appels aux webservices
*   Nos requêtes sont gérés par Retrofit2 de Square Open Source ==> https://square.github.io/retrofit/
* */
public class GetData
{
    /*
    * Permet d'allez chercher toute les annonces.
    * Prend en paramètre l'activité appellante.
    * */
    public void get(Activity act)
    {
        //Nous récuprons une weak référence de l'activité appellante.
        final WeakReference<Activity> current = new WeakReference<>(act);

        //Nous créeons un builder retrofit qui va récupérer les données JSON et les mettre dans notre Objet /Models/Ad.java
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://139.99.98.119:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Nous créeons un objet rétrofit pour communiquer avec notre interface d'API
        WebAPI service = retrofit.create(WebAPI.class);

        //Nous utilisons le call pour récupérer la liste des annonces.
        Call<ArrayList<Ad>> call = service.Ads();

        //
        call.enqueue(new Callback<ArrayList<Ad>>() {
            @Override
            public void onResponse(Call<ArrayList<Ad>> call, Response<ArrayList<Ad>> response) {
                Intent intent = new Intent(current.get().getBaseContext(), AdsListingActivity.class);
                _currentAds = response.body();
                current.get().startActivity(intent);
                current.get().finish();
            }

            @Override
            public void onFailure(Call<ArrayList<Ad>> call, Throwable t) {
                Log.e("GetData : ", t.getMessage());
            }
        });
    }


    /*
    * Permet de se connecter
    * Prend en paramètre :
    *   act : Activité appellante
    *   login : Nom de compte
    *   password : Mot de passe
    * */
    public void login(Activity act, String login, String password)
    {
        //Nous récuprons une weak référence de l'activité appellante.
        final WeakReference<Activity> current = new WeakReference<>(act);

        //On connecte rétrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thibault01.com:8081/")
                .build();

        WebAPI service = retrofit.create(WebAPI.class);
        Call<ResponseBody> call = service.login(login, password);

        //On execute le call
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String res = "";
                try
                {
                    //on récupère la réponse true ou false
                    res = response.body().string();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                //Si le serveur autorise la connexion
                if (res.equals("true"))
                {
                    //on met une variable boolean connected sur true
                    connected = true;

                    //Et on retourne sur l'activité avec la liste des annonces.
                    Intent backToList = new Intent(current.get(), AdsListingActivity.class);
                    current.get().startActivity(backToList);
                    current.get().finish();
                }
                else
                {
                    Toast.makeText(current.get(), "Combinaison login / mot de passe incorrecte.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Login request : ", t.getMessage());
            }
        });
    }

    /*
    * send:
    *   Activité permettant d'envoyer les annonces.
    *   un JSONObject avec les données de l'annonce
    *   Et l'image stockée dans un fichier
    * */
    public void send(Activity act, JSONObject data, File file)
    {
        //Nous récuprons une weak référence de l'activité appellante.
        final WeakReference<Activity> current = new WeakReference<>(act);

        //On connecte rétrofit à l'api.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://139.99.98.119:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Nous mettons notre image dans un RequestBody.
        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);

        //On appel notre service.
        WebAPI service = retrofit.create(WebAPI.class);
        Call<String> call = service.sending(
                requestFile,
                data.toString());

        //on execute notre requete
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //une fois la requête fini nous partons sur l'activité de confirmation d'envois.
                Intent confirmation = new Intent(current.get(), ConfirmationCreaActivity.class);
                current.get().startActivity(confirmation);//rediriger vers confirmation de creation
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }
}
