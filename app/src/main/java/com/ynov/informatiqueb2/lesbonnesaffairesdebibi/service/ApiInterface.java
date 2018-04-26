package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    public static final String ENDPOINT =  "http://139.99.98.119:8080";

    @GET("findAnnonces")
    Call<List<Announcement>> getAnnonces(
            @QueryMap Map<String,String> options
    );


}
