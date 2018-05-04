package fr.lesbonnesaffairesdebibi.prjandroid.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class IdentificationActivity extends AppCompatActivity {

    EditText login;
    EditText pass;

    Button btnVal;
    Button btnAnn;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        //Récupération du système de cache grace au lien avec notre activités
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        login = findViewById(R.id.emailLogin);
        pass = findViewById(R.id.passwordLogin);

        btnVal = findViewById(R.id.validerLogin);
        btnAnn = findViewById(R.id.annulerLogin);

        btnVal.setOnClickListener(genericOnClicListener);
        btnAnn.setOnClickListener(genericOnClicListener);

        //Si les login ont déjà été entré
        login.setText(sharedPreferences.getString("mail", ""));
        pass.setText(sharedPreferences.getString("mdp", ""));
    }

    private final View.OnClickListener genericOnClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.validerLogin:
                    goToListUser();
                    break;
                case R.id.annulerLogin:
                    goToListAnnonce();
                    break;
                default:
                    break;
            }
        }
    };

    private void goToListUser() {
        if (TextUtils.isEmpty(login.getText().toString())) {
            login.setError("Ce champ doit être renseigné");
        } else {
            final Pattern pattern = Pattern.compile(
                    "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
            );
            if (!pattern.matcher(login.getText().toString()).matches()) {
                login.setError("Veuillez entrer un mail valide");
            } else {
                if (TextUtils.isEmpty(pass.getText().toString())) {
                    pass.setError("Ce champ doit être renseigné");
                } else {
                    //Si la checkbox n'est pas coché vide le cache des clé login et motDePasse
                    sharedPreferences.edit()
                            .remove("mail")
                            .remove("mdp")
                            .putString("mail", login.getText().toString())
                            .putString("mdp", pass.getText().toString())
                            .commit();

                    Intent intent = new Intent(this, ListeAnnonceUtilisateurActivity.class)
                            .putExtra("mail", login.getText().toString())
                            .putExtra("mdp", pass.getText().toString());
                    startActivity(intent);
                }
            }
        }
    }

    private void goToListAnnonce() {
        Intent intent = new Intent(this, ListAnnonceActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_list_annonce, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accueilMenu:
                Intent intent = new Intent(this, ListAnnonceActivity.class);
                startActivity(intent);
                break;
            case R.id.favorisMenu:
                Intent intent2 = new Intent(this, ListeFavorisActivity.class);
                startActivity(intent2);
                break;
            case R.id.deposeAnnonceMenu:
                Intent intent3 = new Intent(this, CreationAnnonceActivity.class);
                startActivity(intent3);
                break;
            case R.id.modifierAnnonceMenu:
                Intent intent4 = new Intent(this, IdentificationActivity.class);
                startActivity(intent4);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
