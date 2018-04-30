package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.controller.AuthorizationController;

public class ActivityForm extends AppCompatActivity {

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ActivityForm.this);

        Button submit = findViewById(R.id.Btn_submit);
        Button reset = findViewById(R.id.Btn_reset);

        final EditText LogUser = findViewById(R.id.IDuser);
        final EditText LogPass = findViewById(R.id.IDpass);
        final CheckBox save = findViewById(R.id.IDcheck);

        if (sharedPreferences.getBoolean("save", false)) {
            save.setChecked(true);
            LogUser.setText(sharedPreferences.getString("email", ""));
            LogPass.setText(sharedPreferences.getString("password", ""));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = LogUser.getText().toString();
                String password = LogPass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    LogUser.setError("email manquant");
                } else if (TextUtils.isEmpty(password)) {
                    LogPass.setError("Mot de passe manquant");
                } else {
                    Intent intent = new Intent(ActivityForm.this, ActivityDashboard.class);

                    if (save.isChecked()) {
                        sharedPreferences.edit()
                                .putBoolean("save", true)
                                .putString("email", email)
                                .putString("password", password)
                                .apply();
                    } else {
                        sharedPreferences.edit()
                                .putBoolean("save", false)
                                .apply();
                    }

                    Toast.makeText(ActivityForm.this
                            , "Connexion au serveur"
                            , Toast.LENGTH_LONG).show();

                    new AuthorizationController(ActivityForm.this).execute(LogUser.getText().toString(), LogPass.getText().toString());
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUser.setText("");
                LogPass.setText("");
            }
        });
    }
    }
