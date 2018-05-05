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

        // Appel du layout parent pour inclure la barre d'action globale et le menu (extension de l'activité de base - BaseActivity)
        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_login, contentLayout);

        // On initialise SharedPreferences, qui nous permet d'accéder à la mémoire
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        // On déclare les éléments du layout
        EditText loginEmail = findViewById(R.id.loginEmail);
        EditText loginPassword = findViewById(R.id.loginPassword);
        Button loginButton = findViewById(R.id.loginButton);
        Button loginReset = findViewById(R.id.loginReset);

        // Ajout d'un écouteur de clic sur le bouton "Connexion"
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des valeurs des champs email et password
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                // On vérifie que les champs email et password ne sont pas vides
                if(TextUtils.isEmpty(email)) {
                    loginEmail.setError("Vous devez renseigner une adresse mail");
                } else if(TextUtils.isEmpty(password)) {
                    loginPassword.setError("Vous devez renseigner un mot de passe");
                } else {

                    // On enregistre l'email et le password en mémoire
                    sp.edit().putString("email", email).putString("password", password).apply();

                    // On passe à l'activité MyList (Ma liste des annonces)
                    Intent intent = new Intent(LoginActivity.this, MyListActivity.class);
                    startActivity(intent);

                }
            }
        });

        // Ajout d'un écouteur de clic sur le bouton "Effacer"
        loginReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Au clic, on efface le contenu des champs login et password
                loginEmail.setText("");
                loginPassword.setText("");
            }
        });

    }
}
