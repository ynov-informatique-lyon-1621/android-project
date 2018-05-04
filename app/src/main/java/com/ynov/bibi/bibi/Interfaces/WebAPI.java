package com.ynov.bibi.bibi.Interfaces;

import android.support.v7.util.SortedList;

import okhttp3.ResponseBody;
import retrofit2.Call;

import com.ynov.bibi.bibi.Models.Ad;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebAPI {

    @GET("findAnnonces")
    Call<ArrayList<Ad>> Ads();

    @GET("authorization")
    Call<ResponseBody> login(
            @Query("login") String login,
            @Query("mdp") String password
    );

    @FormUrlEncoded
    @POST("sendMessage")
    void sending(
            @Field("client_id") String id,
            @Field("client_secret") String secret,
            @Field("username") String uname,
            @Field("password") String password,
            Callback<JSONObject> cb
    );
}
