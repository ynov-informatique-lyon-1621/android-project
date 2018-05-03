package com.ynov.lesbonnesaffairesdebibi.service;

import com.ynov.lesbonnesaffairesdebibi.model.Annonce;
import com.ynov.lesbonnesaffairesdebibi.model.Message;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface HttpService {

    public static String ENDPOINT = "http://139.99.98.119:8080/";

    @GET("findAnnonces")
    Call<List<Annonce>> findAnnonces(@Query("motCle") String motCle, @Query("categorie") String categorie, @Query("localisation") String localisation);

    @GET("findAnnoncesByEmail")
    Call<List<Annonce>> findAnnoncesByEmail(@Query("email") String email, @Query("mpd") String mdp);

    @Multipart
    @POST("saveAnnonce")
    Call<Void> saveAnnonce(@Part("annonce") RequestBody annonce, @Part MultipartBody.Part file);

    @DELETE("deleteAnnonce")
    Call<Void> deleteAnnonce(@Query("id") Integer id);

    @PUT("updateAnnonce")
    Call<Void> updateAnnonce(@Body Annonce annonce);

    @POST("sendMessage")
    Call<Void> sendMessage(@Body Message message);

}
