package com.ynov.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ynov.lesbonnesaffairesdebibi.R;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("Connexion");

        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_login, contentLayout);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        EditText loginEmail = findViewById(R.id.loginEmail);
        EditText loginPassword = findViewById(R.id.loginPassword);
        Button loginButton = findViewById(R.id.loginButton);
        Button loginReset = findViewById(R.id.loginReset);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    loginEmail.setError("Vous devez renseigner une adresse mail");
                } else if(TextUtils.isEmpty(password)) {
                    loginPassword.setError("Vous devez renseigner un mot de passe");
                } else {

                    sp.edit().putString("email", email).putString("password", password).apply();

                    Intent intent = new Intent(LoginActivity.this, MyListActivity.class);
                    startActivity(intent);

                }
            }
        });

        loginReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmail.setText("");
                loginPassword.setText("");
            }
        });

    }
}
