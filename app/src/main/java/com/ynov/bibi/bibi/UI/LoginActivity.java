package com.ynov.bibi.bibi.UI;

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

import com.ynov.bibi.bibi.R;
import com.ynov.bibi.bibi.Services.GetData;

import static com.ynov.bibi.bibi.StaticClass.SupplyDepot.connected;

/*
* LoginActivity:
*   Activité gérant la page d'identification.
* */
public class LoginActivity extends AppCompatActivity {

    private EditText _loginBox;
    private EditText _pwdBox;
    private Button _valider;
    private Button _reset;
    private CheckBox _remerberMe;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _loginBox = findViewById(R.id.loginBox);
        _pwdBox = findViewById(R.id.pwdBox);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        /*
        * Si nous sommes déjà connecté l'application nous connecte automatiquement et nous renvois à la page de listing d'annonces.
        * */
        if (sharedPreferences.getString("login", "") != "" && sharedPreferences.getString("pwd", "") != "")
        {
            connected = true;
            Intent backToList = new Intent(LoginActivity.this, AdsListingActivity.class);
            startActivity(backToList);
            finish();
        }

        initbutton();

    }

    private void initbutton(){
        _valider = findViewById(R.id.valider);
        _reset = findViewById(R.id.reset);

        _valider.setOnClickListener(myButtonSwitch);
        _reset.setOnClickListener(myButtonSwitch);
    }

    private final View.OnClickListener myButtonSwitch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.valider:
                    _remerberMe = findViewById(R.id.rmbMe);
                    if (TextUtils.isEmpty(_loginBox.getText().toString()))
                    {
                        _loginBox.setError("Le login est vide, renseignez le login");
                    } else if (TextUtils.isEmpty(_pwdBox.getText().toString())) {
                        _pwdBox.setError("Renseignez le pwd");
                    } else {


                        new GetData().login(LoginActivity.this, _loginBox.getText().toString(), _pwdBox.getText().toString());

                        if(_remerberMe.isChecked()){
                            sharedPreferences.edit()
                                    .putString("login", _loginBox.getText().toString())
                                    .putString("pwd", _pwdBox.getText().toString())
                                    .commit();
                        }

                    }
                    break;
                case R.id.reset:
                    EditText editLogin = findViewById(R.id.loginBox);
                    EditText editPwd = findViewById(R.id.pwdBox);
                    editLogin.setText("");
                    editPwd.setText("");
                    break;
            }

        }
    };
}
