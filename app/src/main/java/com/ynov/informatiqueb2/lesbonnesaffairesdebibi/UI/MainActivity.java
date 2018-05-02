package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Adapter.AdapterListAnnonce;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Model.ListAnnonceModel;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
        new GetAnnonces().execute();
        menu = (Button) findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChoiceActivityIntent = new Intent(MainActivity.this, ChoiceActivity.class);
                startActivity(ChoiceActivityIntent);
            }
        });

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

                new GetAnnonces().execute(filtre1, filtre2, filtre3);
            }
        });
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

    public class GetAnnonces extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection myWebService = null;
            String contenu = "";
            StringBuilder stringBuilder = null;
            String urlAsString;

            //Nous faisons un try catch pour gérer les exceptions unchecked. C'est à dire les exceptions qui ne sont pas gérées par l'application.
            try {
                urlAsString = GenerateFilteredURL(strings);

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
                    //tant qu'il y'a des lignes, on les lis et on les concatène
                    stringBuilder.append(contenu);
                }

                return stringBuilder.toString();
            } catch (Exception e) {
                //Gestion des erreurs
                e.printStackTrace();
            }
            return stringBuilder.toString();
            //On retourne notre string concaténée
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //On crée une liste pour notre JSON
                JSONArray jsonArray = new JSONArray(s);
                List<ListAnnonceModel> listAnnonce = new ArrayList<>();
                PrettyTime prettyTime = new PrettyTime(Locale.FRENCH);
                Date myDate;
                //Cette boucle va permettre de récupérer tout les objets JSON présent dans sur le serveur
                for (int i = 0; i<jsonArray.length(); i++){
                    JSONObject o = (JSONObject) jsonArray.getJSONObject(i);
                    ListAnnonceModel list = new ListAnnonceModel();

                    list.setTitle(o.getString("titre"));
                    list.setCategorie(o.getString("categorie"));
                    list.setDescription(o.getString("description"));
                    list.setPrix(o.getString("prix"));
                    list.setVendeur(o.getString("nomVendeur"));
                    list.setImage(GetImageName(o.getString("image")));

                    myDate = new Date(new Long(o.getString("dateCreation")));
                    list.setDate(prettyTime.format(myDate));
                    listAnnonce.add(list);
                }
                //On instancie notre Adapter et lui passe en argument notre liste d'objets
                AdapterListAnnonce adapter = new AdapterListAnnonce(MainActivity.this,0,listAnnonce);
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
