package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.DownloadImage;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Model.ListAnnonceModel;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI.DetailAnnonceActivity;

import java.util.List;

public class AdapterFavoris extends ArrayAdapter<ListAnnonceModel> {
    public AdapterFavoris(@NonNull Context context, int resource) {
        super(context, resource);
    }



/*
    SharedPreferences favPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    SharedPreferences.Editor prefEditor = favPreferences.edit();

    private static class ViewHolder {
        TextView title;
        TextView prix;
        TextView date;
        TextView categorie;
        ImageView imageArticle;
        CheckBox favArticle;
    }

    public AdapterFavoris(@NonNull Context context, int resource) {
            super(context, resource);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        String nbrFav = favPreferences.getString("nbrFav","");

        Gson gson = new Gson();
        String json = favPreferences.getString("Annonce1", "");
        *//*
        int nbrFavAsInt = Integer.parseInt(nbrFav)-1;
        nbrFav = String.valueOf(nbrFavAsInt);

        favPreferences.edit().putString("nbrFav", nbrFav);*//*

        final ListAnnonceModel annonce = gson.fromJson(json, ListAnnonceModel.class);

        Log.e("cat", annonce.getCategorie());
        Log.e("titre",annonce.getTitle());
        Log.e("desc", annonce.getDescription());
        Log.e("id", annonce.getId());
        //Nous prenons l'item de la classe
        ListAnnonceModel listAnnonceModel = getItem(position);
        //On check si il existe deja une view, si elle n'existe pas, on crée un nouveau ViewHolder, on defini sur quel layout on va travailler et on recupère nos champs sur le layout.
        ViewHolder v;
        if (convertView == null) {
            v = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_annonce_template, parent, false);
            //On recupère nos champs
            v.title = (TextView) convertView.findViewById(R.id.titreMenu);
            v.prix = (TextView) convertView.findViewById(R.id.prixMenu);
            v.categorie = (TextView) convertView.findViewById(R.id.categorieMenu);
            v.date = (TextView) convertView.findViewById(R.id.dateMenu);
            v.imageArticle = (ImageView) convertView.findViewById(R.id.imageArticle);
            v.favArticle = (CheckBox) convertView.findViewById(R.id.favorisID);

            //On met notre viewHolder en cache
            convertView.setTag(v);
        }
        else{
            //Si convertView n'est pas null, on retrouve notre viewHolder avec le tag
            v = (ViewHolder) convertView.getTag();
        }


        //On set les données dans le template en utilisant les objets

        v.title.setText(annonce.getTitle());
        v.categorie.setText(annonce.getCategorie());
        v.date.setText(annonce.getDate());
        v.prix.setText(annonce.getPrix() + " €");


        //Pour les images, on utilise notre methode DownloadImage, avec notre champ d'image présent dans le template en parametres.
        new DownloadImage((ImageView) v.imageArticle)
                .execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" + annonce.getImage());


        //Pour pouvoir cliquer sur un objet et voir les details, on set un clickListener sur notre convertView.
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On recupère la position de l'objet sur le quel on clique.
                //ListAnnonceModel details = getItem(position);
                //On crée un nouvel intent entre notre context (ici MainActivity) et le DetailAnnonceActivity.
                Intent intentDetailAnnonce = new Intent(getContext(), DetailAnnonceActivity.class);
                //On fait passer toutes les valeurs de l'objet sur lequel on a cliqué dans notre Intent.
                intentDetailAnnonce.putExtra("id", annonce.getId());
                intentDetailAnnonce.putExtra("titre", annonce.getTitle());
                intentDetailAnnonce.putExtra("categorie", annonce.getCategorie());
                intentDetailAnnonce.putExtra("prix", annonce.getPrix());
                intentDetailAnnonce.putExtra("description", annonce.getDescription());
                intentDetailAnnonce.putExtra("date", annonce.getDate());
                intentDetailAnnonce.putExtra("vendeur", annonce.getVendeur());
                intentDetailAnnonce.putExtra("image", annonce.getImage());


                //On démarre notre activity qui va nous montrer les details du perso sur lequel on a cliqué.
                getContext().startActivity(intentDetailAnnonce);

            }
        });

        v.favArticle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            ListAnnonceModel selectedAnnonce = getItem(position);

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( !isChecked ){

                }
            }
        });


        //On retourne le vue precedemment complétée pour notre List de personnages
        return convertView;

    }*/
}
