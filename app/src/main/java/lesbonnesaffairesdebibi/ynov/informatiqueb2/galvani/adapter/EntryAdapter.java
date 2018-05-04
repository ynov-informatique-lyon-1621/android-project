package lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.adapter;

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
import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.R;
import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.ui.DetailEntryActivity;
import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.model.EntryList;

import java.util.List;

// Adapter pour la liste d'entrées

public class EntryAdapter extends ArrayAdapter<EntryList> {

    public EntryAdapter(@NonNull Context context, int resource, @NonNull List<EntryList> objects) {

        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

         EntryList listItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_homepage, parent, false);
        }

        TextView Titre = convertView.findViewById(R.id.titleEntry );
        TextView Categorie = convertView.findViewById(R.id.categorieEntry);
        TextView Prix = convertView.findViewById(R.id.priceEntry );
        ImageView Picture = convertView.findViewById(R.id.imageEntry );

        // On remplace les infos
        assert listItem != null;
        Titre.setText(listItem.getTitre());
        Categorie.setText(String.format("Catégorie: %s", listItem.getCategorie()));
        Prix.setText(String.format("%s €", listItem.getPrix()));
        Glide.with(getContext())
                .load("http://139.99.98.119:8080/" + listItem.getPicture().substring(25))
                .into(Picture);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EntryList listItemDetail = getItem(position);

                Intent Detail = new Intent(getContext(), DetailEntryActivity.class);

                assert listItemDetail != null;
                Detail.putExtra("Titre", listItemDetail.getTitre());
                Detail.putExtra("Categorie", listItemDetail.getCategorie());
                Detail.putExtra("Prix", listItemDetail.getPrix());
                Detail.putExtra("Description", listItemDetail.getDescription());
                Detail.putExtra("NomVendeur", listItemDetail.getNomVendeur());
                Detail.putExtra("Picture", listItemDetail.getPicture());

                getContext().startActivity(Detail);
            }
        });


        return convertView;
    }

    @Nullable
    @Override
    public EntryList getItem(int position) {
        return super.getItem(position);
    }


}

