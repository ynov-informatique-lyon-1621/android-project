package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Adapter.AdapterCurrentDate;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//API retrofit2.0
public abstract class ApiClass implements CallApi {
    private static CallApi callApi;

    public static CallApi getInstance() {
        if (callApi != null) {
            return callApi;
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .registerTypeHierarchyAdapter(Date.class, new AdapterCurrentDate())
                    .registerTypeAdapter(Date.class, new AdapterCurrentDate());
            Gson gson = gsonBuilder.create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CallApi.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            callApi = retrofit.create(CallApi.class);
            return callApi;
        }
    }


    private ApiClass() {}
}
