package com.example.oli.projetb2.Ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oli.projetb2.R;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.Advert;

public class CreateAdvertActivity extends AppCompatActivity {

    Intent intent;
    ImageView imageView;
    Button btn;
    EditText nom;
    EditText email;
    EditText mdp;
    EditText mdp2;
    Spinner spinner;
    EditText prix;
    EditText desc;
    Button valider;
    Button annuler;
    Advert advert = new Advert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);
        imageView = findViewById(R.id.addImage);
        btn = findViewById(R.id.btnselectphoto);
        nom = findViewById(R.id.addNom);
        email = findViewById(R.id.addEmail);
        mdp = findViewById(R.id.addMdp);
        mdp2 = findViewById(R.id.addMdp2);
        prix = findViewById(R.id.addPrix);
        desc = findViewById(R.id.addDesc);
        valider = findViewById(R.id.ajoutvalider);
        annuler = findViewById(R.id.ajoutannuler);


        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // j'affiche une erreur si l'un des champs obligatoire est vide, je n'ai pas mis un if dans un autre if parceque ça ne marche pas
                if (TextUtils.isEmpty(nom.getText().toString())) {
                    nom.setError("Champ est obligatoire!");
                }
                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Champ obligatoire!");

                }
                if (TextUtils.isEmpty(mdp.getText().toString())) {
                    mdp.setError("Champ obligatoire!");

                }

                if (TextUtils.isEmpty(mdp2.getText().toString())) {
                    mdp2.setError("Champ obligatoire!");

                }

                if (TextUtils.isEmpty(prix.getText().toString())) {
                    prix.setError("Champ obligatoire!");

                }

                if (TextUtils.isEmpty(desc.getText().toString())) {
                    desc.setError("Champ obligatoire!");

                } else {

                    // je créé un objet entrée en récupérer les informations rentrés par le users
                    advert.setNomVendeur(nom.getText().toString());
                    advert.setEmail(email.getText().toString());
                    advert.setMdp(mdp.getText().toString());
                    advert.setPrix(prix.getId());
                    advert.setDescritpion(desc.getText().toString());
                    sendPost(); // j'envoie l'objet sur le site
                }
            }
        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    // bouton qui permert de reset les champs
                nom.setText("");
                email.setText("");
                mdp.setText("");
                mdp2.setText("");
                prix.setText("");
                desc.setText("");
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
                    jsonParam.put("nom", advert.getNomVendeur());  // on rempli
                    jsonParam.put("email", advert.getEmail());
                    jsonParam.put("mdp", advert.getMdp());
                    jsonParam.put("prix", advert.getPrix());
                    jsonParam.put("description", advert.getDescritpion());

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
        intent = new Intent(CreateAdvertActivity.this, ListAdvertActivity.class);
        startActivity(intent);
    }

    private void deposer() { //redirige vers la création d’une annonce
        Toast.makeText(this, R.string.action_depo_annonce, Toast.LENGTH_LONG).show();
        intent = new Intent(CreateAdvertActivity.this, CreateAdvertActivity.class);
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
