package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ApiService implements ApiInterface  {
    private static  ApiInterface ourInstance;

    public static ApiInterface getInstance() {
        if(ourInstance != null)
        {
            return ourInstance;
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiInterface.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ourInstance = retrofit.create(ApiInterface.class);
            return ourInstance;
        }
    }

    private ApiService() {
    }
}
