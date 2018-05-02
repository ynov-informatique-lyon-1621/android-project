package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiInterface;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    Button loginBtn;
    Button resetBtn;
    EditText usernameIpt;
    EditText passwordIpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.resetBtn = findViewById(R.id.resetBtn);
        this.loginBtn = findViewById(R.id.loginBtn);
        this.usernameIpt = findViewById(R.id.usernameIpt);
        this.passwordIpt = findViewById(R.id.passwordIpt);

        this.resetBtn.setOnClickListener(listener);
        this.loginBtn.setOnClickListener(listener);
    }


    protected void onCancelClicked() {
       Intent intent = new Intent(LoginActivity.this,AnnouncementListActivity.class);
       startActivity(intent);
    }

    protected void onLoginClicked() {
        boolean error = false;
        //Check if fields are not empty
        if(TextUtils.isEmpty(passwordIpt.getText())){
            passwordIpt.setError("Ce champ est obligatoire");
            error = true;
        }if(TextUtils.isEmpty(usernameIpt.getText())){
            usernameIpt.setError("Ce champ est obligatoire");
            error = true;
        }
        if(!error) {
            ApiInterface apiInterface = ApiService.getInstance();
            Map<String,String> params  = new HashMap<String,String>();
            params.put("email",usernameIpt.getText().toString());
            params.put("mdp",passwordIpt.getText().toString());
            apiInterface.getOwnedAnnonces(params).enqueue(this.callback);
        }
    }

    private Callback<List<Announcement>> callback = new Callback<List<Announcement>>() {
        @Override
        public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
            if(response.body().size() <= 0) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setMessage("Vos identifiants ne sont pas reconnus")
                        .setTitle("Désolé")
                        .create()
                        .show();
            }else {
                Log.d("CACA2",response.body().toString());
                Intent intent = new Intent(LoginActivity.this, OwnedAnnoucementListActivity.class);
                intent.putExtra("annoucements",(Serializable) response.body());
                startActivity(intent);
            }
        }

        @Override
        public void onFailure(Call<List<Announcement>> call, Throwable t) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setMessage("Une erreur est survenue")
                    .setTitle("Ooops ")
                    .create()
                    .show();
        }
    };

    // Generic listener
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginBtn:
                    LoginActivity.this.onLoginClicked();
                    break;
                case R.id.resetBtn:
                    LoginActivity.this.onCancelClicked();
            }
        }
    };
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }
}
