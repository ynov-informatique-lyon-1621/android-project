package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.controller.AuthorizationController;
        // Formulaire de connexion
public class ActivityForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ActivityForm.this);

        Button submit = findViewById(R.id.Btn_submit);
        Button reset = findViewById(R.id.Btn_reset);

        final EditText LogUser = findViewById(R.id.idUser);
        final EditText LogPass = findViewById(R.id.idPass);
        final CheckBox save = findViewById(R.id.IDcheck);

        // Par defaut le boolean est faux, on lui dit de verifier si la checkbox est false il n'eenregistre pas les informations sinon il passe a la suite
        if (sharedPreferences.getBoolean("save", false)) {
            save.setChecked(true);
            LogUser.setText(sharedPreferences.getString("email", ""));
            LogPass.setText(sharedPreferences.getString("password", ""));
        }

        // Button envoyer qui va verifier si les champs obligatoires sont remplis puis envoie sa requete au serveur
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = LogUser.getText().toString();
                String password = LogPass.getText().toString();

                if (TextUtils.isEmpty(email + password)) {
                    LogUser.setError("email manquant");
                } else if (TextUtils.isEmpty(password)) {
                    LogPass.setError("Mot de passe manquant");
                } else {

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
        // Button reset qui remplace les champs mdp et user par un champ vide
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUser.setText("");
                LogPass.setText("");
            }
        });

    }
    // toujours notre menu hamburger
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings_connect) {
            Intent intent = new Intent(this, ActivityForm.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
