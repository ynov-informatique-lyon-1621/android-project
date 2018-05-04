package com.example.travail.esparel.UI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.travail.esparel.Adapter.AnnonceAdapter;
import com.example.travail.esparel.R;
import com.example.travail.esparel.model.AnnonceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    Button rechercheButton;
    EditText rechercheMenu;
    EditText rechercheCPVille;
    Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetAnnonces().execute("", "", "");
        menu = (Button) findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
               Intent ChoiceActivityIntent = new Intent(MainActivity.this, ChoiceActivity.class);
               startActivity(ChoiceActivityIntent);
            }
       });

        final ListView listView = findViewById(R.id.listAnnonce);
        spinner = findViewById(R.id.spinner);
        rechercheButton = findViewById(R.id.boutonRechercher);
        rechercheMenu = findViewById(R.id.rechercheMenu);
        rechercheCPVille = findViewById(R.id.villecpMenu);
        String[] itemsCat = new String[]{"Tous", "Vêtements", "Voitures"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, itemsCat) {

        };
        spinner.setAdapter(adapter);

        rechercheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String filtre1 = rechercheMenu.getText().toString();
            String filtre2 = spinner.getSelectedItem().toString();
            String filtre3 = rechercheCPVille.getText().toString();

            Toast.makeText(MainActivity.this, filtre1 + " - " + filtre2 + " - " + filtre3, Toast.LENGTH_SHORT).show();

            listView.setAdapter(null);
            new GetAnnonces().execute(filtre1, filtre2, filtre3);
            }
        });

    }

    public class GetAnnonces extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection myWebService = null;
            String contenu = "";
            StringBuilder stringBuilder = null;
            String urlAsString;

            //Nous faisons un try catch pour gérer les exceptions non traité par l'appli
            try {
                //urlAsString = GenerateFilteredURL(strings);
                urlAsString = "http://139.99.98.119:8080/findAnnonces?motCle=" + strings[0] + "&categorie=" + strings[1] + "&localisation=" + strings[2];

                URL urlWS = new URL(urlAsString);
                //On ouvre une connexion
                myWebService = (HttpURLConnection)urlWS.openConnection();

                //on recupère la réponse du serveur
                InputStream in = myWebService.getInputStream();

                //On set un bufferReader pour pouvoir lire les données du serveur
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                //On va concaténer le les donnée que l'on recupère
                stringBuilder = new StringBuilder();

                while ((contenu = reader.readLine()) != null){

                    stringBuilder.append(contenu);
                }

                return stringBuilder.toString();

            } catch (Exception e) {
                //Gestion des erreurs
                e.printStackTrace();
            }

            return stringBuilder.toString();
            //On retourne notre string
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //On crée une liste pour notre JSON
                JSONArray jsonArray = new JSONArray(s);
                List<AnnonceModel> listAnnonce = new ArrayList<>();
                //Cette boucle va permettre de récupérer tout les objets JSON présent dans sur le serveur
                for (int i = 0; i<jsonArray.length(); i++){
                    JSONObject o = (JSONObject) jsonArray.getJSONObject(i);
                    AnnonceModel list = new AnnonceModel();

                    list.setTitre(o.getString("titre"));
                    list.setCategorie(o.getString("categorie"));
                    //list.setDate(o.getString("date"));
                    list.setDescription(o.getString("description"));
                    list.setPrix(o.getString("prix"));
                    list.setVendeur(o.getString("nomVendeur"));
                    list.setImage(GetImageName(o.getString("image")));

                    listAnnonce.add(list);
                }
                //On instancie notre Adapter et lui passe en argument notre liste d'objets
                AnnonceAdapter adapter = new AnnonceAdapter(MainActivity.this,0,listAnnonce);
                //On recupère notre liste sur notre layout
                ListView listView = findViewById(R.id.listAnnonce);
                //On set notre list à l'adapter
                listView.setAdapter(adapter);

            }
            catch (JSONException e) {
                Log.e("Erreur","Pas de données",e);
            }
        }
    }

    public String GetImageName(String fullPath){
        String imageName;

        String[] parts = fullPath.split("/");
        imageName = parts[6];

        return imageName;
    }

    public String GenerateFilteredURL(String[] strings)
    {
        String url;
        String[] keysFilter;

        url = "http://139.99.98.119:8080/findAnnonces";

        if(strings.length>0) {
            keysFilter = new String[]{"motCle", "categorie", "localisation"};

            for (int i = 0; i < strings.length; i++) {
                String currentFilterContent = strings[i];
                String currentFilterKey = keysFilter[i];

                if(currentFilterContent != null){
                    if(url.indexOf("?") < 0){
                        url = url + "?" + currentFilterKey + "=" + currentFilterContent;
                    }
                    else{
                        url = url + "&" + currentFilterKey + "=" + currentFilterContent;
                    }
                }
            }
        }
        return url;
    }
}