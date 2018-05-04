package com.ynov.bibi.bibi.Interfaces;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import com.ynov.bibi.bibi.Models.Ad;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


/*
* WebAPI
*  Interface comprenant nos requêtes au back-end.
*  Ces fonctions sont appellées dans Services/GetData.java
*  Nos requêtes sont gérés par Retrofit2 de Square Open Source ==> https://square.github.io/retrofit/
* */
public interface WebAPI {

    // Requête pour toutes les annonces.
    @GET("findAnnonces")
    Call<ArrayList<Ad>> Ads();

    // Requête l'authentification.
    @GET("authorization")
    Call<ResponseBody> login(
            @Query("login") String login,
            @Query("mdp") String password
    );

    // Requête pour ajouter une annonce.
    @Multipart
    @POST("saveAnnonce")
    Call<String> sending(
            @Part("file") RequestBody file,
            @Part("annonce") String ad
            );
}
