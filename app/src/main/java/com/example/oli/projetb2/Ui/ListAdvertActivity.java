package com.example.oli.projetb2.Ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.oli.projetb2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Model.Advert;

import static android.net.wifi.WifiConfiguration.Status.strings;

public class ListAdvertActivity extends AppCompatActivity {

    Button btn;
    Intent intent;
    ImageView mImageView;
    RecyclerView recyclerView;
    List<Advert> advertTab;
    LinearLayoutManager linearLayoutManager;
    DividerItemDecoration dividerItemDecoration;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_advert);
        recyclerView = findViewById(R.id.recycler);
        mImageView = (ImageView) findViewById(R.id.imageView);
        advertTab = new ArrayList<>();


        adapter = new Adapter.ListAdapter(this, advertTab);

        // set le layout du recycleview
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());


        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);


        getData();

    }


    //Webservice

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://139.99.98.119:8080/findAnnonces", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {


                        JSONObject jsonObject = response.getJSONObject(i);
                        // récupération des données json sur la classe
                        Advert advert = new Advert();
                        advert.setId(jsonObject.getInt("id"));
                        advert.setNomVendeur(jsonObject.getString("nomVendeur"));
                        advert.setEmail(jsonObject.getString("email"));
                        advert.setMdp(jsonObject.getString("mdp"));
                        advert.setTitre(jsonObject.getString("titre"));
                        advert.setCategorie(jsonObject.getString("categorie"));
                        advert.setPrix(jsonObject.getInt("prix"));
                        advert.setDescritpion(jsonObject.getString("description"));
                        advert.setImage(ImageUrl(jsonObject.getString("image")));
                        advert.setDateCreation(jsonObject.getInt("dateCreation"));


                        advertTab.add(advert);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }


                adapter.notifyDataSetChanged(); //l'adapter est updaet
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public String ImageUrl(String fullPath){
        String imageName;

        String[] parts = fullPath.split("/");
        imageName = parts[6];

        return imageName;
    }

//
//    public String FilterDownload(String[] strings)
//    {
//        String imageUrl;
//        String[] endPointTab;
//
//        imageUrl = "http://139.99.98.119:8080/findAnnonces";
//
//        if(strings.length>0) {
//            endPointTab = new String[]{"motCle", "categorie", "localisation"};
//
//            for (int i = 0; i < strings.length; i++) {
//                String currentFilterContent = strings[i];
//                String currentFilterKey = endPointTab[i];
//
//                if(currentFilterContent != null){
//                    if(imageUrl.indexOf("?") < 0){
//                        imageUrl = imageUrl + "?" + currentFilterKey + "=" + currentFilterContent;
//                    }
//                    else{
//                        imageUrl = imageUrl + "&" + currentFilterKey + "=" + currentFilterContent;
//                    }
//                }
//            }
//        }
//        return imageUrl;
//    }



    // Le menu hamburger en haut à droite
    private void acceuil() { //permet de revenir sur la liste des annonces filtrer selon les filtres utilisateurs s’il y en a
        Toast.makeText(this, R.string.action_accueil, Toast.LENGTH_LONG).show();
        intent = new Intent(ListAdvertActivity.this, ListAdvertActivity.class);
        startActivity(intent);
    }

    private void deposer() { //redirige vers la création d’une annonce
        Toast.makeText(this, R.string.action_depo_annonce, Toast.LENGTH_LONG).show();
        intent = new Intent(ListAdvertActivity.this, CreateAdvertActivity.class);
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



