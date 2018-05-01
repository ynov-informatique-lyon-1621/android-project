package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app.ActivityDetail;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.AdvertList;

import java.util.List;


public class AdvertViewAdapter extends ArrayAdapter<AdvertList> {

    public AdvertViewAdapter(@NonNull Context context, int resource, @NonNull List<AdvertList> objects) {

        super(context, resource, objects);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

         AdvertList listItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_dashboard, parent, false);
        }

        // Permet de remplacer le TextView comportant les IDs
        TextView Titre = convertView.findViewById(R.id.TitleView);
        TextView Categorie = convertView.findViewById(R.id.CatView);
        TextView Prix = convertView.findViewById(R.id.PriceView);
        ImageView Picture = convertView.findViewById(R.id.imageViewDash);

        // Remplace les informations
        assert listItem != null;
        Titre.setText(listItem.getTitre());
        Categorie.setText(String.format("Catégorie: %s", listItem.getCategorie()));
        Prix.setText(String.format("Prix: %s €", listItem.getPrix()));
        Glide.with(getContext())
                .load(listItem.getPicture())
                .into(Picture);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdvertList listItemDetail = getItem(position);

                Intent DetailAdvert = new Intent(getContext(), ActivityDetail.class);

                assert listItemDetail != null;
                DetailAdvert.putExtra("Titre", listItemDetail.getTitre());
                DetailAdvert.putExtra("Categorie", listItemDetail.getCategorie());
                DetailAdvert.putExtra("Prix", listItemDetail.getPrix());
                DetailAdvert.putExtra("Description", listItemDetail.getDescription());
                DetailAdvert.putExtra("NomVendeur", listItemDetail.getNomVendeur());
                DetailAdvert.putExtra("Picture", listItemDetail.getPicture());

                getContext().startActivity(DetailAdvert);
            }
        });


        return convertView;
    }

    @Nullable
    @Override
    public AdvertList getItem(int position) {
        return super.getItem(position);
    }


}

