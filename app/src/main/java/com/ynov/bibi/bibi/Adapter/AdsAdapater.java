package com.ynov.bibi.bibi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.bibi.bibi.Models.Ad;
import com.ynov.bibi.bibi.R;
import com.ynov.bibi.bibi.UI.DetailsActivity;

import java.util.ArrayList;
import java.util.Date;

/*
* AdsAdapater :
*   Classe adaptater pour la liste d'annonce principale.
* */
public class AdsAdapater extends ArrayAdapter<Ad> {

    private Context _currentCtx;

    public AdsAdapater(Context ctx, ArrayList<Ad> ads)
    {
        super(ctx, 0, ads);
        _currentCtx = ctx;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //On récupère l'annonce actuelle
        Ad annonce = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ad_in_list, parent, false);

        //On récupère tous les éléments qui compose un élément de la liste dans ad_in_list.xml
        RelativeLayout layout = convertView.findViewById(R.id.lineLayout);
        ImageView pictureAds = convertView.findViewById(R.id.pictureAds);
        TextView category = convertView.findViewById(R.id.category);
        TextView title = convertView.findViewById(R.id.title);
        TextView priceView = convertView.findViewById(R.id.priceView);
        TextView dateCreation = convertView.findViewById(R.id.dateView);
        Switch switchFavorit = convertView.findViewById(R.id.switchFavorit);

        //On attribue chaque valeurs de l'annonce actuelle à chaque élément en vérifiant que nous ne recevons pas de données érronnée (ex. null)
        try {
            Glide.with(convertView).load("http://139.99.98.119:8080/" + annonce.getPicture().substring(25)).into(pictureAds);
        }
        catch (Exception e) {
            Log.e("AdsAdapater : ", e.getMessage());
        }
        try {
            category.setText(annonce.getCategory());
        } catch (Exception e) {
            Log.e("AdsAdapater : ", e.getMessage());
        }
        try {
            title.setText(annonce.getName());
        } catch (Exception e) {
            Log.e("AdsAdapater : ", e.getMessage());
        }
        try {
            dateCreation.setText(new Date(annonce.getDateCreation()).toString().substring(0, 10));
        } catch (Exception e) {
            Log.e("AdsAdapater : ", e.getMessage());
        }
        try {
            priceView.setText(String.valueOf(annonce.getPrice()) + "€");
        } catch (Exception e) {
            Log.e("AdsAdapater : ", e.getMessage());
        }

        // Le layout sera clickable et amènera à la page détail en envoyant la position de l'élément séléctionné
        layout.setClickable(true);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToDetails = new Intent(_currentCtx, DetailsActivity.class);
                goToDetails.putExtra("postition", position);
                _currentCtx.startActivity(goToDetails);
            }
        });

        return convertView;
    }
}
