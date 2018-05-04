package com.ynov.bibi.bibi.Interfaces;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import com.ynov.bibi.bibi.Models.Ad;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface WebAPI {

    @GET("findAnnonces")
    Call<ArrayList<Ad>> Ads();

    @GET("authorization")
    Call<ResponseBody> login(
            @Query("login") String login,
            @Query("mdp") String password
    );

    @Multipart
    @POST("saveAnnonce")
    Call<String> sending(
            //@Part MultipartBody.Part file,
            //@Part("file") File img,
            @Part("file") RequestBody file,
            @Part("annonce") String ad
            );
}
