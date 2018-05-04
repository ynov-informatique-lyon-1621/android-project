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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.DownloadImage;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Model.ListAnnonceModel;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI.DetailAnnonceActivity;


import java.util.List;

import static java.security.AccessController.getContext;

public class AdapterListAnnonce extends ArrayAdapter<ListAnnonceModel>{

    //definir un sharedPref custom pour les favoris.
    SharedPreferences favPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    SharedPreferences.Editor prefEditor = favPreferences.edit();

    private static class ViewHolder {
        TextView title;
        TextView prix;
        TextView date;
        TextView categorie;
        ImageView imageArticle;
        ImageView favArticle;
    }

    public AdapterListAnnonce(@NonNull Context context, int resource, @NonNull List<ListAnnonceModel> objects) {
        super(context, resource, objects);
        favPreferences.edit().putString("nbrFav", "1").commit();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

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
            v.favArticle = (ImageView) convertView.findViewById(R.id.favorisID);

            //On met notre viewHolder en cache
            convertView.setTag(v);
        }
        else{
            //Si convertView n'est pas null, on retrouve notre viewHolder avec le tag
            v = (ViewHolder) convertView.getTag();

        }

        //On set les données dans le template en utilisant les objets
        v.title.setText(listAnnonceModel.getTitle());
        v.categorie.setText(listAnnonceModel.getCategorie());
        v.date.setText(listAnnonceModel.getDate());
        v.prix.setText(listAnnonceModel.getPrix() + " €");

        //Pour les images, on utilise notre methode DownloadImage, avec notre champ d'image présent dans le template en parametres.
        new DownloadImage((ImageView) v.imageArticle)
                .execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" + listAnnonceModel.getImage());

        //Pour pouvoir cliquer sur un objet et voir les details, on set un clickListener sur notre convertView.
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On recupère la position de l'objet sur le quel on clique.
                ListAnnonceModel details = getItem(position);
                //On crée un nouvel intent entre notre context (ici MainActivity) et le DetailAnnonceActivity.
                Intent intentDetailAnnonce = new Intent(getContext(), DetailAnnonceActivity.class);
                //On fait passer toutes les valeurs de l'objet sur lequel on a cliqué dans notre Intent.
                intentDetailAnnonce.putExtra("id", details.getId());
                intentDetailAnnonce.putExtra("titre", details.getTitle());
                intentDetailAnnonce.putExtra("categorie", details.getCategorie());
                intentDetailAnnonce.putExtra("prix", details.getPrix());
                intentDetailAnnonce.putExtra("description", details.getDescription());
                intentDetailAnnonce.putExtra("date", details.getDate());
                intentDetailAnnonce.putExtra("vendeur", details.getVendeur());
                intentDetailAnnonce.putExtra("image", details.getImage());

                //On démarre notre activity qui va nous montrer les details du perso sur lequel on a cliqué.
                getContext().startActivity(intentDetailAnnonce);

            }
        });
//Cette classe était censer gerer les favoris. Malheureusement, nous n'y sommes pas arrivé.
// Nous avons tout de même laissé le code.
        v.favArticle.setOnClickListener(new View.OnClickListener()  {

            ListAnnonceModel selectedAnnonce = getItem(position);
            Gson gson = new Gson();
            String annonceJson = gson.toJson(selectedAnnonce);

            @Override
            public void onClick(View v) {
                String numCurrentFav = favPreferences.getString("nbrFav", "");

                /*if (){
                    prefEditor.putString("Annonce1", annonceJson).commit();

                    int nbrFavAsInt = Integer.parseInt(numCurrentFav)+1;

                    numCurrentFav = String.valueOf(nbrFavAsInt);

                    favPreferences.edit().putString("nbrFav", numCurrentFav).commit();
Log.e("apres", numCurrentFav);

                    favPreferences.edit().putString("id", selectedAnnonce.getId())
                            .putString("titre", selectedAnnonce.getTitle())
                            .putString("categorie", selectedAnnonce.getCategorie())
                            .putString("prix", selectedAnnonce.getPrix())
                            .putString("description", selectedAnnonce.getDescription())
                            .putString("date", selectedAnnonce.getDate())
                            .putString("vendeur", selectedAnnonce.getVendeur())
                            .putString("image", selectedAnnonce.getImage()).commit();

                   Toast.makeText(getContext(), favPreferences.getString("nbrFav", ""), Toast.LENGTH_SHORT).show();
                }
                else if(!isChecked){

                    int nbrFavAsInt = Integer.parseInt(numCurrentFav)-1;
                }*/
            }
        });


        //On retourne le vue precedemment complétée pour notre List de personnages
        return convertView;

    }
}
