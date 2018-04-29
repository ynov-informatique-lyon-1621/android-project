package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.controller.AuthorizationController;


public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(FormActivity.this);

        // Récuperation des IDs
        Button submit = findViewById(R.id.Btn_submit);
        Button reset = findViewById(R.id.Btn_reset);

        // Récuperation des IDs et les définits
        final EditText LogUser = findViewById(R.id.IDuser);
        final EditText LogPass = findViewById(R.id.IDpass);
        final CheckBox save = findViewById(R.id.IDcheck);

        // Vérification du 'se souvenir'
        if (sharedPreferences.getBoolean("save", false)) {
            save.setChecked(true);
            LogUser.setText(sharedPreferences.getString("login", ""));
            LogPass.setText(sharedPreferences.getString("password", ""));
        }

        // Création du bouton 'envoyer'
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Mettre en cache le nom d'utilisateur et mot de passe
                String login = LogUser.getText().toString();
                String password = LogPass.getText().toString();

                // Vérifie si les informations sont bien remplie
                if (TextUtils.isEmpty(login)) {
                    LogUser.setError("Nom d'utilisateur manquant");
                } else if (TextUtils.isEmpty(password)) {
                    LogPass.setError("Mot de passe manquant");
                } else {
                    // Vérification si 'se souvenir' et bien coché
                    if (save.isChecked()) {
                        sharedPreferences.edit()
                                .putBoolean("save", true)
                                .putString("login", login)
                                .putString("password", password)
                                .apply();
                    } else {
                        sharedPreferences.edit()
                                .putBoolean("save", false)
                                .apply();
                    }
                    // Vérification de l'authentification à partir du fichier 'AuthorizationController'
                    new AuthorizationController(FormActivity.this).execute(LogUser.getText().toString(), LogPass.getText().toString());
                }
            }
        });

        // Création du bouton 'vider'
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUser.setText("");
                LogPass.setText("");
            }
        });

    }
}
