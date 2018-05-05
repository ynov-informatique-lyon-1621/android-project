package com.example.elesa.projet_lelt.Ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.elesa.projet_lelt.R;

public class Favoris extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //si clique sur "poster article" appel la méthode post
            case R.id.post:
                post();
                return true;
            //si clique sur "modifier article" appel la méthode change
            case R.id.modif:
                change();
                return true;
            case R.id.favoris:
                favoris();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //redirige sur l'activié favoris
    public void favoris(){
        Intent intent = new Intent(this, Favoris.class);
        startActivity(intent);
    }
    //redirige sur l'activié post
    public void post(){
        Intent intent = new Intent(this, post.class);
        startActivity(intent);
    }
    //redirige sur l'activié chanlog
    public void change(){
        Intent intent = new Intent(this, changeLog.class);
        startActivity(intent);
    }
}
