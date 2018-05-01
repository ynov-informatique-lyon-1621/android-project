package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter.TimestampDateAdapter;

import java.text.DateFormat;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ApiService implements ApiInterface  {
    private static  ApiInterface ourInstance;

    public static ApiInterface getInstance() {
        if(ourInstance != null)
        {
            return ourInstance;
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .registerTypeHierarchyAdapter(Date.class,new TimestampDateAdapter())
                    .registerTypeAdapter(Date.class,new TimestampDateAdapter());
            Gson gson = gsonBuilder.create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiInterface.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            ourInstance = retrofit.create(ApiInterface.class);
            return ourInstance;
        }
    }

    private ApiService() {
    }
}
