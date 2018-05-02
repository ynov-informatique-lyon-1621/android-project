package com.ynov.lesbonnesaffairesdebibi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;
import com.ynov.lesbonnesaffairesdebibi.service.DateService;
import com.ynov.lesbonnesaffairesdebibi.service.FavoriteService;
import com.ynov.lesbonnesaffairesdebibi.ui.MyListActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnnonceAdapter extends BaseAdapter {

    private Context mContext;
    private List<Annonce> mAnnonce;
    private FavoriteService favoriteService;

    public AnnonceAdapter(Context mContext, List<Annonce> mContact) {
        this.mContext = mContext;
        this.mAnnonce = mContact;
        favoriteService = new FavoriteService(mContext);
    }

    public class ViewHolder {
        public ImageView annImage;
        public TextView annTitre;
        public TextView annCategorie;
        public TextView annPrix;
        public TextView annDate;
        public ImageButton annFavorite;
        public ImageButton annEdit;
        public ImageButton annDelete;
    }

    @Override
    public int getCount() {
        return mAnnonce.size();
    }

    @Override
    public Object getItem(int position) {
        return mAnnonce.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View vi = convertView;
        final ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            vi = inflater.inflate(R.layout.row_list, null);

            holder = new ViewHolder();
            holder.annImage = (ImageView) vi.findViewById(R.id.annImage);
            holder.annTitre = (TextView) vi.findViewById(R.id.annTitre);
            holder.annCategorie = (TextView) vi.findViewById(R.id.annCategorie);
            holder.annPrix = (TextView) vi.findViewById(R.id.annPrix);
            holder.annDate = (TextView) vi.findViewById(R.id.annDate);
            holder.annFavorite = (ImageButton) vi.findViewById(R.id.btnFavorite);

            if(mContext.getClass() == MyListActivity.class) {
                holder.annEdit = (ImageButton) vi.findViewById(R.id.btnEdit);
                holder.annDelete = (ImageButton) vi.findViewById(R.id.btnDelete);
                holder.annEdit.setVisibility(View.VISIBLE);
                holder.annDelete.setVisibility(View.VISIBLE);
            }

            vi.setTag(holder);

        } else {
            holder = (ViewHolder) vi.getTag();
        }

        final Annonce annonce = mAnnonce.get(position);

        Picasso.get().load(annonce.getImage()).into(holder.annImage);
        holder.annTitre.setText(annonce.getTitre());
        holder.annCategorie.setText(annonce.getCategorie());
        holder.annPrix.setText(annonce.getPrix());
        holder.annDate.setText(new DateService(annonce.getDateCreation()).show());

        favoriteService.draw(annonce, holder.annFavorite);

        holder.annFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteService.click(annonce, ((ImageButton) v));
            }
        });

        if(mContext.getClass() == MyListActivity.class)
        {
            holder.annEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Édition de l'annonce", Toast.LENGTH_SHORT).show();
                }
            });

            holder.annDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                        .setIcon(R.drawable.ic_warning_black_24dp)
                        .setTitle("Suppression")
                        .setMessage("Voulez-vous vraiment supprimer cette annonce ?")
                        .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "Annonce supprimée", Toast.LENGTH_SHORT).show();
                                // Suppression de l'annonce...
                            }
                        })
                        .setNegativeButton("Annuler", null)
                        .show();
                }
            });
        }

        return vi;
    }


}
