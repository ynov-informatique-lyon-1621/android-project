package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.AdvertList;

import java.util.List;



public class AdvertViewAdapter extends ArrayAdapter<AdvertList> {

    public AdvertViewAdapter(@NonNull Context context, int resource, @NonNull List<AdvertList> objects) {

        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        final AdvertList listItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_dashboard, parent, false);
        }

        // Permet de remplacer le TextView comportant les IDs
        TextView NomVendeur = convertView.findViewById(R.id.NameView);
        TextView Email = convertView.findViewById(R.id.EmailView);
        TextView Titre = convertView.findViewById(R.id.TitleView);
        TextView Localisation = convertView.findViewById(R.id.LcoView);
        TextView Categorie = convertView.findViewById(R.id.CatView);
        TextView Prix = convertView.findViewById(R.id.PriceView);

        // Remplace les informations
        NomVendeur.setText(listItem.getNomVendeur());
        Email.setText(listItem.getEmail());
        Titre.setText(listItem.getTitre());
        Localisation.setText(listItem.getLocalisation());
        Categorie.setText(listItem.getCategorie());
        Prix.setText(listItem.getPrix());

        return convertView;
    }

    @Nullable
    @Override
    public AdvertList getItem(int position) {
        return super.getItem(position);
    }


}

