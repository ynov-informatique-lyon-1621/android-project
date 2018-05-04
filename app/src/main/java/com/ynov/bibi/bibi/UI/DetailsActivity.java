package com.ynov.bibi.bibi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.bibi.bibi.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

import static com.ynov.bibi.bibi.StaticClass.SupplyDepot._currentAds;


/*
* DetailsActivity:
*   Activité de la page détails d'un article.
* */
public class DetailsActivity extends AppCompatActivity {

    private ImageView _picture;
    private TextView _title;
    private TextView _category;
    private TextView _date;
    private TextView _price;
    private TextView _description;
    private Button _contact;
    private TextView _nameVendor;

    private int _currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Nous récupérons un Intent de AdsListingActivity
        Intent infos = getIntent();
        //Nous récupérons la position de l'item qui a été séléctionné
        _currentPosition = infos.getIntExtra("postition", 0);

        initWidgets();
        setupInformations();
    }

    //Nous initialisons les éléments de la page.
    public void initWidgets()
    {
        _picture = findViewById(R.id.pictureDetail);
        _title = findViewById(R.id.titleDetails);
        _category = findViewById(R.id.categoryDetails);
        _date = findViewById(R.id.dateDetails);
        _price = findViewById(R.id.priceDetails);
        _description = findViewById(R.id.descriptionDetails);
        _contact = findViewById(R.id.contactBtn);
        _nameVendor = findViewById(R.id.vendorDetails);
    }

    //Nous attribuons des valeurs à ces éléments en fonction de la position récupéré.
    public void setupInformations()
    {
        PrettyTime p = new PrettyTime(Locale.FRENCH);

        Glide.with(this).load("http://139.99.98.119:8080/" +  _currentAds.get(_currentPosition).getPicture().substring(25)).into(_picture);
        _title.setText(_currentAds.get(_currentPosition).getName());
        _category.setText(_currentAds.get(_currentPosition).getCategory());
        _date.setText(p.format(new Date(new Long(_currentAds.get(_currentPosition).getDateCreation()))));
        _price.setText( _currentAds.get(_currentPosition).getPrice() + "€");
        _description.setText(_currentAds.get(_currentPosition).getDescription());
        _nameVendor.setText(_currentAds.get(_currentPosition).getOwner());
        _contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contact = new Intent(DetailsActivity.this, ContactVendeurActivity.class);
                startActivity(contact);//rediriger vers confirmation de contact vendeur
            }
        });
    }
}
