package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.Authentification;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

public class IdentificationActivity extends AppCompatActivity {

    EditText loginBox;
    EditText pwdBox;
    Button valider;
    Button reset;
    CheckBox remerberMe;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        loginBox = (EditText) findViewById(R.id.loginBox);
        pwdBox = (EditText) findViewById(R.id.pwdBox);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(IdentificationActivity.this);

        String login = sharedPreferences.getString("login", "");
        String pwd = sharedPreferences.getString("pwd", "");
        loginBox.setText(login);
        pwdBox.setText(pwd);

        initbutton();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_menu_un:
                Intent mainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(mainActivityIntent);
                return true;
            case R.id.action_menu_deux:
                Intent deposerAnnonceIntent = new Intent(getBaseContext(), DeposerAnnonceActivity.class);
                startActivity(deposerAnnonceIntent);
                return true;
            case R.id.action_menu_trois:
                Intent favorisIntent = new Intent(getBaseContext(), IdentificationActivity.class);
                startActivity(favorisIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initbutton(){
        valider = (Button) findViewById(R.id.valider);
        reset = (Button) findViewById(R.id.reset);

        valider.setOnClickListener(myButtonSwitch);
        reset.setOnClickListener(myButtonSwitch);
    }

    private final View.OnClickListener myButtonSwitch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //Premier boutons : valider
                case R.id.valider:
                    remerberMe = findViewById(R.id.rmbMe);
                    //Gestion des champs vides, si l'un des deux est empty -> error. Si le champs sont plein -> Appel du check par Web-Service
                    if (TextUtils.isEmpty(loginBox.getText().toString())) {
                        loginBox.setError("Le login est vide, renseigner le login");
                    } else if (TextUtils.isEmpty(pwdBox.getText().toString())) {
                        pwdBox.setError("Renseigner le pwd");
                    } else {

                        //Appel du web-service
                        new Authentification(IdentificationActivity.this).execute(loginBox.getText().toString(),pwdBox.getText().toString());

                        //Intent ActivityChoice = new Intent(MainActivity.this, ChoiceActivity.class);
                        //startActivity(ActivityChoice);
                        //Si la case remember me est cochée, on met en cache les donnée dans notre sharedPreferences
                        if(remerberMe.isChecked()){
                            sharedPreferences.edit()
                                    .putString("login", loginBox.getText().toString())
                                    .putString("pwd", pwdBox.getText().toString())
                                    .commit();
                        }

                    }
                    break;
                //Deuxième bouton : Vider
                case R.id.reset:
                    EditText editLogin = findViewById(R.id.loginBox);
                    EditText editPwd = findViewById(R.id.pwdBox);
                    //Nous setons simplement les champs
                    editLogin.setText("");
                    editPwd.setText("");
                    break;
            }

        }
    };

}
