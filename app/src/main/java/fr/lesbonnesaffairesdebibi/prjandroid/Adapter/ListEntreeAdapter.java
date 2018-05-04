package fr.lesbonnesaffairesdebibi.prjandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import fr.lesbonnesaffairesdebibi.prjandroid.Activity.EditerAnnonceActivity;
import fr.lesbonnesaffairesdebibi.prjandroid.Controller.DeleteAnnonce;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Favoris;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class ListEntreeAdapter extends ArrayAdapter<Annonce> {

    Context context;
    String mod;

    public ListEntreeAdapter(Context context, String mod, ArrayList<Annonce> entrees) {
        super(context, 0, entrees);
        this.context = context;
        this.mod = mod;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Annonce annonce = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_annonce, parent, false);
        }
        // Lookup view for data population
        ImageView annonceImage = (ImageView) convertView.findViewById(R.id.listViewImage);
        TextView annonceTitre = (TextView) convertView.findViewById(R.id.listViewTitre);
        TextView annonceCategorie = (TextView) convertView.findViewById(R.id.listViewCate);
        TextView annonceDate = (TextView) convertView.findViewById(R.id.listViewDate);
        TextView annoncePrix = (TextView) convertView.findViewById(R.id.listViewPrix);
        ImageView annonceFav = (ImageView) convertView.findViewById(R.id.listViewFav);
        ImageView annonceDel = (ImageView) convertView.findViewById(R.id.listViewDel);

        // Populate the data into the template view using the data object
        annonceTitre.setText(annonce.getTitre());
        annonceCategorie.setText(annonce.getCategorie());
        annonceDate.setText(annonce.getDateCreation());
        annoncePrix.setText(String.valueOf(annonce.getPrix()) + "€");


        if (mod.equals("fav")) {
            if (Favoris.getInstance().isInFavoris(annonce)) {
                annonceFav.setBackgroundResource(R.drawable.coeur_plein);
            } else {
                annonceFav.setBackgroundResource(R.drawable.coeur_vide);
            }
        } else if (mod.equals("modif")) {
            annonceFav.setBackgroundResource(R.drawable.edit);
            annonceDel.setBackgroundResource(R.drawable.del);
        }

        annonceFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mod.equals("fav")) {
                    Favoris fav = Favoris.getInstance();
                    ImageButton img = (ImageButton) view;
                    if (fav.isInFavoris(annonce)) {
                        fav.delFavoris(annonce);
                        img.setBackgroundResource(R.drawable.coeur_vide);
                    } else {
                        fav.addFavoris(annonce);
                        img.setBackgroundResource(R.drawable.coeur_plein);
                    }
                } else if (mod.equals("modif")) {
                    Intent intent = new Intent(context, EditerAnnonceActivity.class)
                            .putExtra("detail",annonce);
                    context.startActivity(intent);
                }
            }
        });

        annonceDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mod.equals("modif")) {
                    new DeleteAnnonce().execute(annonce.getId());
                }
            }
        });

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image);

        Glide.with(context).load(context.getString(R.string.urlEndPoint) + "images/lesbonsplansdebibi/" + annonce.getImage()).apply(options).into(annonceImage);

        // Return the completed view to render on screen
        return convertView;
    }
}
