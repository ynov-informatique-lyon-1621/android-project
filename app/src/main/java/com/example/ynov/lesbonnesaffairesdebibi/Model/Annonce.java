package com.example.ynov.lesbonnesaffairesdebibi.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.ynov.lesbonnesaffairesdebibi.R;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Thoma on 03/05/2018.
 */

public class Annonce implements Serializable {

    private int _id;
    private String _picture;
    private String _title;
    private Double _price;
    private Timestamp _date;
    private String _category;
    private String _description;
    private User _seller;
    private String _localisation;


    DecimalFormat decimalFormat = new DecimalFormat("0.##");

    public Annonce(int _id, String _picture, String _title, Double _price,
                   Timestamp _date, String _category, String _description, User _seller, String _localisation) {
        this._id = _id;
        this._picture = _picture;
        this._title = _title;
        try {
            _price = Double.valueOf(decimalFormat.format(_price));
        } catch( Exception e) {
            e.printStackTrace();
            // En fonction du device et de la région on peut avoir le . ou la ,
            _price = Double.valueOf(decimalFormat.format(_price).replace(',','.'));
        }
        this._price = _price;
        this._date = _date;
        this._category = _category;
        this._description = _description;
        this._seller = _seller;
        this._localisation = _localisation;
    }


    public int get_id() {
        return _id;
    }

    public String get_picture() {
        return _picture;
    }

    public void set_picture(String _picture) {
        this._picture = _picture;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public Double get_price() {
        return _price;
    }

    public void set_price(Double _price) {

        this._price = Double.parseDouble(decimalFormat.format(_price));
    }

    public Timestamp get_date() {
        return _date;
    }

    public void set_date(Timestamp _date) {
        this._date = _date;
    }

    public String get_category() {
        return _category;
    }

    public void set_category(String _category) {
        this._category = _category;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public User get_seller() {
        return _seller;
    }

    public void set_seller(User _seller) {
        this._seller = _seller;
    }

    @Override
    public String toString() {
        return "Annonce{" +
                "_id=" + _id +
                ", _picture='" + _picture + '\'' +
                ", _title='" + _title + '\'' +
                ", _price=" + _price +
                ", _date=" + _date +
                ", _category='" + _category + '\'' +
                ", _description='" + _description + '\'' +
                ", _seller=" + _seller +
                '}';
    }


    public String formatDate() {


        Date adDate = new Date(this._date.getTime());
        Date dateNow = new Date();

        // On crée 2 calendrier basés sur nos 2 dates afin de les compater et de detecter si elle date d'aujourd'hui
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(adDate);
        cal2.setTime(dateNow);
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

        // Formatage de la date
        if(sameDay){
            return "Aujourd'hui à " + cal1.HOUR + ":" + cal1.MINUTE + ":" + cal1.SECOND;
        } else {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return dateFormat.format(adDate);
        }

    }

    public Drawable findPic(Context context) {

        Drawable myDrawable;
        // On essaye de trouver le drawable indiqué
        File file = new File(this._picture);
        if(file.exists()){
            myDrawable  = Drawable.createFromPath(this._picture);
        } else {
            Toast.makeText(context, "Erreur lors du chargement de l'image !", Toast.LENGTH_LONG).show();
            myDrawable = ContextCompat.getDrawable(context, R.drawable.default_ad);

        }

        return myDrawable;
    }

    public String get_localisation() {
        return _localisation;
    }

    public void set_localisation(String _localisation) {
        this._localisation = _localisation;
    }
}
