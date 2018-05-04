package com.example.ynov.lesbonnesaffairesdebibi.ViewModel;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ynov.lesbonnesaffairesdebibi.Converter.InputStreamToString;
import com.example.ynov.lesbonnesaffairesdebibi.Model.Annonce;
import com.example.ynov.lesbonnesaffairesdebibi.Model.User;
import com.example.ynov.lesbonnesaffairesdebibi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WebService monWs;

    protected Button searchButton;
    protected EditText zipCodeSearch;
    protected EditText inputSearch;
    protected Spinner categorySearch;

    ArrayList<Annonce> _annonces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchButton = (Button)  findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        inputSearch =  (EditText) findViewById(R.id.searchEditText);
        zipCodeSearch = (EditText) findViewById(R.id.zipCodeEditText);
        categorySearch = (Spinner) findViewById(R.id.categoryEditText);


        //create a list of items for the spinner.
        String[] items = new String[]{"","Véhicules", "Vêtements"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        categorySearch.setAdapter(adapter);

        // Instanciation et lancement du web service
        // la mise à jour de la liste est effectué dans le onProgressUpdate
        monWs = new WebService();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //gestion du menu option
        if (id == R.id.action_add_ad) {
            Intent addAd = new Intent(this, AddAd.class);
            startActivity(addAd);
        }
        if( id == R.id.action_accueil) {
            Toast.makeText(this, R.string.alreadyHere, Toast.LENGTH_SHORT).show();
        }
        if( id == R.id.action_edit_ad) {
            Toast.makeText(this, R.string.notYet, Toast.LENGTH_SHORT).show();
        }
        if( id == R.id.action_fav) {
            Toast.makeText(this, R.string.notYet, Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        // Si < 1000 donc numérique, donc ça correspond à une ligne du tableau
        if(v.getId() < 1000) {
            try {
                Intent anAd = new Intent(this, AdDetail.class);
                anAd.putExtra("myAd", _annonces.get(v.getId()));
                startActivity(anAd);
            }catch( Exception e){
                Toast.makeText(this, "Erreur lors du chargement de l'annonce !", Toast.LENGTH_SHORT).show();
            }
        } else {
            switch (v.getId()) {
                case R.id.searchButton:
                    ArrayList<Annonce> myAnnonces;
                    // Lancement du ws afin de récupérer les annonces suivant les paramètres rentrés
                    monWs.getAds(inputSearch.getText().toString(), zipCodeSearch.getText().toString(), categorySearch.getSelectedItem().toString());
                    break;
            }
        }

    }


    void fillTable(ArrayList<Annonce> myAnnonces){

        Log.d("fill table :", "e");
        TableLayout tl = (TableLayout) findViewById(R.id.main_table);
        tl.removeAllViews();
        tl.removeAllViewsInLayout();
        TableRow tr_head = new TableRow(this);

        int trHeight = 100;
        // Création du header
        tr_head.setId(10+5);
        tr_head.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRow));
        tr_head.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                trHeight));

        TextView hImg = new TextView(this);
        hImg.setText("Aperçu");
        tr_head.addView(hImg);

        TextView hTitle = new TextView(this);
        hTitle.setText("Titre");
        tr_head.addView(hTitle);

        TextView hPrice = new TextView(this);
        hPrice.setText("Pris (Euros)");
        tr_head.addView(hPrice);

        TextView hDate = new TextView(this);
        hDate.setText("Date d'ajout");
        tr_head.addView(hDate);


        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                trHeight + 30));


        // Création des lignes
        Integer count=0;
        for (int i = 0; i < myAnnonces.size(); i++){
            Annonce myAnnonce = myAnnonces.get(i);
            //Log.d("ad affichage", myAnnonce.toString());

            // Formatage de la date
            String date = myAnnonce.formatDate();// get the first variable
            Double price = myAnnonce.get_price();// get the second variable
            String title = myAnnonce.get_title();
            String imgLoc = myAnnonce.get_picture();

            // Create the table row
            TableRow tr = new TableRow(this);
            if(count%2!=0) tr.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRowMain));
            tr.setId(i);

            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    trHeight));
            tr.setOnClickListener(this);
            //Create two columns to add as table data
            // Create a TextView to add date
            ImageView imageAd = new ImageView(this);
            imageAd.setId(2000+count);
            imageAd.setBackground(Drawable.createFromPath(imgLoc));
            imageAd.setPadding(2, 0, 5, 0);
            tr.addView(imageAd);

            TextView labelTitle = new TextView(this);
            labelTitle.setId(3000+count);
            labelTitle.setText(title);
            tr.addView(labelTitle);

            TextView labelPrice = new TextView(this);
            labelPrice.setId(4000+count);
            labelPrice.setText(price.toString());
            tr.addView(labelPrice);

            TextView labelDate = new TextView(this);
            labelDate.setId(5000+count);
            labelDate.setText(date);
            tr.addView(labelDate);

            // finally add this to the table row
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    trHeight));
            count++;
        }
    }


    public class WebService extends AsyncTask<String, String, String>{



        public void getAds(String searchField, String zipCode, String category){


            WebService monWs = new WebService();

            monWs.execute("http://139.99.98.119:8080/findAnnonces" ,searchField, zipCode, category);

        }


        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            String content = "titre="+ params[1] + "&localisation=" + params[2] + "&categorie=" + params[3];
            try{
                // On prépare puis on lance la connexion
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                content = InputStreamToString.convert(in);
            }catch(IOException e){
                e.printStackTrace();
            }
            publishProgress(content);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            try {
                JSONArray jsonArray = new JSONArray(values[0]);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // On évite les exceptions ainsi
                    Timestamp hCreate;
                    try {
                        hCreate = new Timestamp(jsonObject.getLong("dateCreation"));
                    } catch (Exception e) {
                        hCreate = new Timestamp(System.currentTimeMillis()/1000);
                    }



                    Annonce newAnnonce = new Annonce(
                            jsonObject.getInt("id"),
                            jsonObject.getString("image"),
                            jsonObject.getString("titre"),
                            jsonObject.getDouble("prix"),
                            hCreate,
                            jsonObject.getString("categorie"),
                            jsonObject.getString("description"),
                            new User(jsonObject.getString("nomVendeur"),jsonObject.getString("email")));
                    // On vérifie que l'élèment n'existe pas déjà dans notre liste
                    Log.d("val : ", newAnnonce.toString());
                    _annonces.add(newAnnonce);
                }
                try{
                    // On dispose de toutes nos annonces on peut essayer de remplir la table
                    fillTable(_annonces);

                }catch (Exception e){
                    Log.wtf("Erreur", e.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }



    }
}
