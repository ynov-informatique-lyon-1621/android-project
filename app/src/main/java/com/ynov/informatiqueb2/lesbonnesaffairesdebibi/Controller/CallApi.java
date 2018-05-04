package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Model.ListAnnonceModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface CallApi {
//Fonctions retrofit2.0 pour appelez nos Web Servies
    String ENDPOINT = "http://139.99.98.119:8080/";

    @GET("/findAnnonces")
    Call<List<ListAnnonceModel>> getAnnonces(
            @QueryMap Map<String,String> options
    );

//Appel du service POST



    @Multipart
    @POST("/saveAnnonce")
    Call<ListAnnonceModel> addAnnonce(@Part("annonce") ListAnnonceModel nouvelleAnnonce, @Part MultipartBody.Part imageAnnonce);

}
