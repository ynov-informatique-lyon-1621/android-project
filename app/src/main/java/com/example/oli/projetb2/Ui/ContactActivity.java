package com.example.oli.projetb2.Ui;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oli.projetb2.R;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.MessageContact;

public class ContactActivity extends AppCompatActivity {

    Intent intent;
    TextView vTitre;
    TextView vDate;
    TextView vPrix;
    TextView vCategorie;
    EditText eNom;
    EditText eEmail;
    EditText ePhone;
    EditText eDesc;
    Button btnEnvoyer;
    Button btnReset;
    Button btnBack;
    MessageContact messageContact = new MessageContact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        intent = getIntent();
        vTitre = findViewById(R.id.ctcTitre);
        vDate = findViewById(R.id.ctcDate);
        vPrix = findViewById(R.id.ctcPrix);
        vCategorie = findViewById(R.id.ctcCategorie);
        eNom = findViewById(R.id.ctcNom);
        eEmail = findViewById(R.id.ctcEmail);
        ePhone = findViewById(R.id.ctcPhone);
        eDesc = findViewById(R.id.ctcDesc);
        btnEnvoyer = findViewById(R.id.btnEnvoyer);
        btnReset = findViewById(R.id.btnReset);
        btnBack = findViewById(R.id.btnBack);


        final String titre = intent.getStringExtra("titre");
        final int prix = intent.getIntExtra("prix", 0);
        final int date = intent.getIntExtra("date", 0);
        final String desc = intent.getStringExtra("desc");

        vTitre.setText(titre);
        vPrix.setText(String.valueOf(prix + "€"));
        vDate.setText(String.valueOf(date));

        btnEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // j'affiche une erreur si l'un des champs obligatoire est vide, je n'ai pas mis un if dans un autre if parceque ça ne marche pas
                if (TextUtils.isEmpty(eNom.getText().toString())) {
                    eNom.setError("Champ est obligatoire!");
                }
                if (TextUtils.isEmpty(eEmail.getText().toString())) {
                    eEmail.setError("Champ obligatoire!");

                }

                if (TextUtils.isEmpty(eDesc.getText().toString())) {
                    eDesc.setError("Champ obligatoire!");

                } else {

                    // je créé un objet entrée en récupérer les informations rentrés par le users
                    messageContact.setNom(eNom.getText().toString());
                    messageContact.setEmail(eEmail.getText().toString());
                    messageContact.setPhone(ePhone.getId());
                    messageContact.setDesc(eDesc.getText().toString());
                    sendPost(); // j'envoie l'objet sur le site
                    intent = new Intent(ContactActivity.this, ConfirmationActivity.class);
                    intent.putExtra("titre", titre);
                    intent.putExtra("prix", prix);
                    intent.putExtra("date", date);
                    intent.putExtra("desc", desc);
                    startActivity(intent);

                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    // bouton qui permert de reset les champs
                eNom.setText("");
                eEmail.setText("");
                ePhone.setText("");
                eDesc.setText("");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ContactActivity.this, ListAdvertActivity.class);
                startActivity(intent);
            }
        });
    }

    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://139.99.98.119:8080/sendMessage "); //connexion
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    httpURLConnection.setRequestProperty("Accept", "application/json");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();    // création de  l'object json
                    jsonParam.put("nom", messageContact.getNom());  // on rempli
                    jsonParam.put("email", messageContact.getEmail());
                    jsonParam.put("phone", messageContact.getPhone());
                    jsonParam.put("description", messageContact.getDesc());

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(httpURLConnection.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());
                    // on vide et et ferme poru quitter
                    os.flush();
                    os.close();
                    //log
                    Log.i("STATUS", String.valueOf(httpURLConnection.getResponseCode()));
                    Log.i("MSG", httpURLConnection.getResponseMessage());

                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }


    //Menu hamburger
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    private void acceuil() { //permet de revenir sur la liste des annonces filtrer selon les filtres utilisateurs s’il y en a
        Toast.makeText(this, R.string.action_accueil, Toast.LENGTH_LONG).show();
        intent = new Intent(ContactActivity.this, ListAdvertActivity.class);
        startActivity(intent);
    }

    private void deposer() { //redirige vers la création d’une annonce
        Toast.makeText(this, R.string.action_depo_annonce, Toast.LENGTH_LONG).show();
        intent = new Intent(ContactActivity.this, CreateAdvertActivity.class);
        startActivity(intent);
    }

    private void modifier() {
        Toast.makeText(this, R.string.action_modi_annonce, Toast.LENGTH_LONG).show();
    }

    private void favoris() {
        Toast.makeText(this, R.string.action_favoris, Toast.LENGTH_LONG).show();
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_acceuil:
                acceuil();
                break;
            case R.id.action_depo_annonce:
                deposer();
                break;
            case R.id.action_modi_annonce:
                modifier();
                break;
            case R.id.action_favoris:
                favoris();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
