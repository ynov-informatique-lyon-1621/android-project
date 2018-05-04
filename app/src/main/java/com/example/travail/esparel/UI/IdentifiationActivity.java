package com.example.travail.esparel.UI;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travail.esparel.Controller.Auth;
import com.example.travail.esparel.R;

public class IdentifiationActivity extends AppCompatActivity {

    // Views declarations
    public TextView loginInput;
    public TextView passwordInput;
    Button loginButton;
    Button resetButton;
    public CheckBox rememberMeCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identifiation);

        // Initialisation SharedPreferences
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("LogInfo", MODE_PRIVATE);

        // Initialisation TextView
        loginInput = findViewById(R.id.loginInput);
        passwordInput = findViewById(R.id.passwordInput);


        // Recup les champs
        loginInput.setText(prefs.getString("login", ""));
        passwordInput.setText(prefs.getString("pwd", ""));

        // Initialisation des bouttons
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(genericOnClickListener);
        resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(genericOnClickListener);
        // Initialise la checkbox
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);

    }

    // fonction clique et changement de page si réussite du test d'authenfication
    final View.OnClickListener genericOnClickListener = new View.OnClickListener() {
        public void onClick(final View view) {
            switch (view.getId()) {
                case R.id.loginButton:
                    // Test si les champs sont remplis
                    if (TextUtils.isEmpty(loginInput.getText())){
                        loginInput.setError("Please enter a username");
                    }
                    if (TextUtils.isEmpty(passwordInput.getText())){
                        passwordInput.setError("Please enter a password");
                    }
                    // si ils ne sont pas vide test authentification
                    if (!TextUtils.isEmpty(loginInput.getText()) && !TextUtils.isEmpty(passwordInput.getText())) {
                        new Auth(IdentifiationActivity.this).execute(loginInput.getText().toString(), passwordInput.getText().toString());
                    }
                    break;
                case R.id.resetButton:
                    // Reset
                    loginInput.setText(null);
                    passwordInput.setText(null);
                default:
                    break;
            }
        }

    };
}