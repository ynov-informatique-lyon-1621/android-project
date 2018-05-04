package com.ynov.bibi.bibi.Services;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ynov.bibi.bibi.Interfaces.WebAPI;
import com.ynov.bibi.bibi.Models.Ad;
import com.ynov.bibi.bibi.UI.AdsListingActivity;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ynov.bibi.bibi.StaticClass.SupplyDepot._currentAds;
import static com.ynov.bibi.bibi.StaticClass.SupplyDepot.connected;

public class GetData
{
    public void get(final String type, Activity act)
    {
        final WeakReference<Activity> current = new WeakReference<>(act);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://139.99.98.119:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebAPI service = retrofit.create(WebAPI.class);
        Call<ArrayList<Ad>> call = service.Ads();

        switch (type)
        {
            case "all":
                call = service.Ads();
                break;
        }

        call.enqueue(new Callback<ArrayList<Ad>>() {
            @Override
            public void onResponse(Call<ArrayList<Ad>> call, Response<ArrayList<Ad>> response) {
                Intent intent = new Intent(current.get().getBaseContext(), AdsListingActivity.class);
                _currentAds = response.body();
                current.get().startActivity(intent);
                current.get().finish();
            }

            @Override
            public void onFailure(Call<ArrayList<Ad>> call, Throwable t) {
                Log.e("GetData on " + type + " : ", t.getMessage());
            }
        });
    }

    public void login(Activity act, String login, String password)
    {
        final WeakReference<Activity> current = new WeakReference<>(act);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thibault01.com:8081/")
                .build();

        WebAPI service = retrofit.create(WebAPI.class);
        Call<ResponseBody> call = service.login(login, password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String res = "";
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (res.equals("true"))
                {
                    connected = true;
                    Intent backToList = new Intent(current.get(), AdsListingActivity.class);
                    current.get().startActivity(backToList);
                    current.get().finish();
                }
                else
                {
                    Toast.makeText(current.get(), "Combinaison login / mot de passe incorrecte.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Login request : ", t.getMessage());
            }
        });
    }

    public void send(Activity act, JSONObject data, File file)
    {
        final WeakReference<Activity> current = new WeakReference<>(act);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://139.99.98.119:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        WebAPI service = retrofit.create(WebAPI.class);
        Call<String> call = service.sending(
                requestFile,
                data.toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("SUCESS", response.message());
                Log.d("SUCESS", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
    }
}
