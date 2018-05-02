package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    public static final String ENDPOINT =  "http://affaires-bibi-api.projects.juleslaurent.tk";

    @GET("announcements")
    Call<List<Announcement>> getAnnonces(
            @QueryMap Map<String,String> options
    );

    @GET("announcements")
    Call<List<Announcement>> getOwnedAnnonces(
            @QueryMap Map<String,String> options
    );

    @Multipart
    @POST("announcements")
    Call<Announcement> addAnnonce(@Part("announcement") Announcement announcement, @Part MultipartBody.Part image);


    @PUT("announcements/{id}")
    Call<Announcement> updateAnnonce(@Path("id") String id, @Body Announcement announcement);

    @DELETE("announcements/{id}")
    Call<Object> deleteAnnonce(@Path("id") String id);
}
