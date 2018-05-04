package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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

public class MainActivity extends com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI.Menu {

    Spinner spinner;
    Button rechercheButton;
    EditText rechercheMenu;
    EditText rechercheCPVille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetAnnonces().execute();

        //récupérer les éléments du layout.
        spinner = findViewById(R.id.spinner);
        rechercheButton = findViewById(R.id.boutonRechercher);
        rechercheMenu = findViewById(R.id.rechercheMenu);
        rechercheCPVille = findViewById(R.id.villecpMenu);

        //définition des différentes catégories
        String[] itemsCat = new String[]{"Tous", "Vêtements", "Voitures"};

        //mise en place de la dropdown liste
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, itemsCat) {

        };
        spinner.setAdapter(adapter);

        rechercheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //récupération des filtres sur le clique du bouton recherche
                String filtre1 = rechercheMenu.getText().toString();
                String filtre2 = spinner.getSelectedItem().toString();
                String filtre3 = rechercheCPVille.getText().toString();

                //rafraichissement de la liste des annonces en appelant la classe GetAnnonces avec les filtres en paramètre.
                new GetAnnonces().execute(filtre1, filtre2, filtre3);
            }
        });
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
                    list.setId(o.getString("id"));
                    list.setTitle(o.getString("titre"));
                    list.setCategorie(o.getString("categorie"));
                    list.setDescription(o.getString("description"));
                    list.setPrix(o.getString("prix"));
                    list.setVendeur(o.getString("nomVendeur"));
                    list.setImage(GetImageName(o.getString("image")));

                    //Log.d("FMLJPP",o.getString("dateCreation"));

                    if(o.getString("dateCreation") == null || o.getString("dateCreation") == "null"){
                        list.setDate("Date inconnue");
                    }
                    else if(o.getString("dateCreation") != null){
                        myDate = new Date(new Long(o.getString("dateCreation")));
                        list.setDate(prettyTime.format(myDate));
                    }

                    //Log.d("FMLJPP2",list.getDate());

                    listAnnonce.add(list);
                    //Log.d("Temps", prettyTime.format(new Date(System.currentTimeMillis())));
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

    //méthode pour récupérer la partie nécessaire à la formation de l'url à partir du lien de l'image
    public String GetImageName(String fullPath){
        String imageName = null;

        //découpe du lien selon le caractère "/"
        String[] parts = fullPath.split("/");

        for(int i=0; i<parts.length; i++){
            //si la partie contient l'extension jpg ou jpeg ou png alors on récupère la string correspondante.
            if(parts[i].indexOf("jpg") >= 0 || parts[i].indexOf("jpeg") >= 0 || parts[i].indexOf("png") >= 0)
            {
                imageName = parts[i];
            }
        }
        return imageName;
    }

    //méthode de génération d'url selon les filtres choisis par l'utilisateur.
    public String GenerateFilteredURL(String[] strings)
    {
        String url;
        String[] keysFilter;

        url = "http://139.99.98.119:8080/findAnnonces";

        if(strings.length>0) {
            //définition du tableau contenant les clés des filtres.
            keysFilter = new String[]{"motCle", "categorie", "localisation"};

            //pour chaque élément de filtre passé en paramètre...
            for (int i = 0; i < strings.length; i++) {
                //... on stock la clé et la valeur correspondante dans les deux variables ci-dessous...
                String currentFilterContent = strings[i];
                String currentFilterKey = keysFilter[i];

                //si l'utilisateur choisi "Tous" pour voir toutes les annonces, on passe le élimine le paramètre
                //pour qu'il ne passe pas dans l'url.
                if(currentFilterContent == "Tous" && currentFilterKey == "categorie"){
                    currentFilterContent = null;
                }

                //... si l'utilisateur a bien entré une valeur...
                if(currentFilterContent != null){
                    //... on vérifie si l'url contient déjà un élément de filtre en paramètre ou non via la présence du "?"
                    //puis on l'ajoute de la bonne manière.
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
