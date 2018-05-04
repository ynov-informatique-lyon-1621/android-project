package com.ynov.bibi.bibi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.bibi.bibi.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

import static com.ynov.bibi.bibi.StaticClass.SupplyDepot._currentAds;

public class ContactVendeurActivity extends AppCompatActivity {

    private ImageView imageContact;
    private TextView titreContact;
    private TextView prixContact;
    private TextView date;
    private TextView nomVendreurContact;
    private TextView categorieContact;

    private int _currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_vendeur);

        //Recuperation des infos de l'item en question
        Intent infos = getIntent();
        _currentPosition = infos.getIntExtra("postition", 0);

        initWidgets();
        setupInformations();
    }

    public void initWidgets()
    {
        imageContact = findViewById(R.id.pictureContact);
        titreContact = findViewById(R.id.titleContact);
        prixContact = findViewById(R.id.priceContact);
        date = findViewById(R.id.dateContact);
        categorieContact = findViewById(R.id.categoryDetails);
        nomVendreurContact = findViewById(R.id.textNomVendeur);
    }

    public void setupInformations()
    {
        PrettyTime p = new PrettyTime(Locale.FRENCH);

        //setup des informations dans les champs correspondants
        Glide.with(this).load("http://139.99.98.119:8080/" +  _currentAds.get(_currentPosition).getPicture().substring(25)).into(imageContact);
        titreContact.setText(_currentAds.get(_currentPosition).getName());
        categorieContact.setText(_currentAds.get(_currentPosition).getCategory());
        date.setText(p.format(new Date(new Long(_currentAds.get(_currentPosition).getDateCreation()))));
        prixContact.setText( _currentAds.get(_currentPosition).getPrice() + "€");
        nomVendreurContact.setText(_currentAds.get(_currentPosition).getOwner());

    }
}
