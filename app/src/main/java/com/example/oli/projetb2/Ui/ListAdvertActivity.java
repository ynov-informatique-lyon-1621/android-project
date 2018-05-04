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

import java.util.ArrayList;
import java.util.List;

import Model.Advert;

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

                        Advert advert = new Advert();
                        advert.setId(jsonObject.getInt("id"));
                        advert.setNomVendeur(jsonObject.getString("nomVendeur"));
                        advert.setEmail(jsonObject.getString("email"));
                        advert.setMdp(jsonObject.getString("mdp"));
                        advert.setTitre(jsonObject.getString("titre"));
                        advert.setLocalisation(jsonObject.getString("localisation"));
                        advert.setCategorie(jsonObject.getString("categorie"));
                        advert.setPrix(jsonObject.getInt("prix"));
                        advert.setDescritpion(jsonObject.getString("description"));
                        advert.setImageUrl(jsonObject.getString("image"));
                        advert.setDateCreation(jsonObject.getInt("dateCreation"));
                        advert.setImageView(mImageView);


                        advertTab.add(advert);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
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


    // Le menu hamburger en haut à droite
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


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



