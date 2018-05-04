package com.example.travail.esparel.Adapter;

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

import com.example.travail.esparel.Controller.ImageControleller;
import com.example.travail.esparel.R;
import com.example.travail.esparel.UI.AnnonceActivity;
import com.example.travail.esparel.model.AnnonceModel;

import java.util.List;

public class AnnonceAdapter extends ArrayAdapter<AnnonceModel> {


    private static class ViewHolder {
        TextView title;
        TextView prix;
        TextView date;
        TextView categorie;
        ImageView imageArticle;

    }

    public AnnonceAdapter(@NonNull Context context, int resource, @NonNull List<AnnonceModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Nous prenons l'item de la classe
        AnnonceModel AnnonceModel = getItem(position);
        //On check si il existe deja une view, si elle n'existe pas, on crée un nouveau ViewHolder, on defini sur quel layout on va travailler et on recupère nos champs sur le layout.
        ViewHolder h;
        if (convertView == null) {
            h = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.templates, parent, false);
            //On recupère nos champs
            h.title = (TextView) convertView.findViewById(R.id.titreMenu);
            h.prix = (TextView) convertView.findViewById(R.id.prixMenu);
            h.categorie = (TextView) convertView.findViewById(R.id.categorieMenu);
            //v.date = (TextView) convertView.findViewById(R.id.dateMenu);
            h.imageArticle = (ImageView) convertView.findViewById(R.id.imageArticle);
            //On met notre viewHolder en cache
            convertView.setTag(h);
        }
        else{
            //Si convertView n'est pas null, on retrouve notre viewHolder avec le tag
            h = (ViewHolder) convertView.getTag();

        }


        //On set les données dans le template en utilisant les objets

        h.title.setText(AnnonceModel.getTitre());
        h.categorie.setText(AnnonceModel.getCategorie());
        //v.date.setText(listAnnonceModel.getDate());
        h.prix.setText(AnnonceModel.getPrix() + " €");


        //Pour les images, on utilise notre methode DownloadImage, avec notre champ d'image présent dans le template en parametres.
                new ImageControleller((ImageView) h.imageArticle)
                .execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" + AnnonceModel.getImage());


        //Pour pouvoir cliquer sur un objet et voir les details, on set un clickListener sur notre convertView.
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On recupère la position de l'objet sur le quel on clique.
                AnnonceModel details = getItem(position);
                //On crée un nouvel intent entre notre context (ici ListPersoActivity) et le DetailPersoActivity.
                Intent intentDetailAnnonce = new Intent(getContext(), AnnonceActivity.class);
                //On fait passer toutes les valeurs de l'objet sur lequel on a cliqué dans notre Intent.
                intentDetailAnnonce.putExtra("titre", details.getTitre());
                intentDetailAnnonce.putExtra("categorie", details.getCategorie());
                intentDetailAnnonce.putExtra("prix", details.getPrix());
                intentDetailAnnonce.putExtra("description", details.getDescription());
                //intentDetailAnnonce.putExtra("date", details.getDate());
                intentDetailAnnonce.putExtra("vendeur", details.getVendeur());
                intentDetailAnnonce.putExtra("image", details.getImage());


                //On démarre notre activity qui va nous montrer les details du perso sur lequel on a cliqué.
                getContext().startActivity(intentDetailAnnonce);

            }
        });


        //On retourne le vue precedemment complétée pour notre List de personnages
        return convertView;

    }
}