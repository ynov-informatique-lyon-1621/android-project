package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.AdvertList;

import java.util.List;


public class AdvertViewAdapter extends ArrayAdapter<AdvertList> {

    public AdvertViewAdapter(@NonNull Context context, int resource, @NonNull List<AdvertList> objects) {

        super(context, resource, objects);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final AdvertList listItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_dashboard, parent, false);
        }

        // Permet de remplacer le TextView comportant les IDs
        TextView Titre = convertView.findViewById(R.id.TitleView);
        TextView Categorie = convertView.findViewById(R.id.CatView);
        TextView Prix = convertView.findViewById(R.id.PriceView);

        // Remplace les informations
        Titre.setText(listItem.getTitre());
        Categorie.setText(String.format("Catégorie: %s", listItem.getCategorie()));
        Prix.setText(String.format("%s €", Integer.toString(listItem.getPrix())));


        return convertView;
    }

    @Nullable
    @Override
    public AdvertList getItem(int position) {
        return super.getItem(position);
    }


}

