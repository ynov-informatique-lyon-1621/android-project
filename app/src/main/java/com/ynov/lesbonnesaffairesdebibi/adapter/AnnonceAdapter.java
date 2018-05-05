package com.ynov.lesbonnesaffairesdebibi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;
import com.ynov.lesbonnesaffairesdebibi.service.DateService;
import com.ynov.lesbonnesaffairesdebibi.service.FavoriteService;
import com.ynov.lesbonnesaffairesdebibi.service.HttpService;
import com.ynov.lesbonnesaffairesdebibi.ui.AddActivity;
import com.ynov.lesbonnesaffairesdebibi.ui.MyListActivity;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnnonceAdapter extends BaseAdapter {

    private Context mContext;
    private List<Annonce> mAnnonce;
    private FavoriteService favoriteService;

    public AnnonceAdapter(Context mContext, List<Annonce> mAnnonce) {
        // On récupère le contexte et la liste d'objets Annonce et on initialise le service de favoris
        this.mContext = mContext;
        this.mAnnonce = mAnnonce;
        favoriteService = new FavoriteService(mContext);
    }

    public class ViewHolder {
        // Classe destinée à conserver les éléments de chaque row de la listView
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
            holder.annTitre = (TextView) vi.findViewById(R.id.annTitle);
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

        // Récupération de l'objet Annonce à la position actuelle
        final Annonce annonce = mAnnonce.get(position);

        // Avec la librairie Picasso, on charge l'image de l'annonce dans l'imageView
        Picasso.get().load(annonce.getImage()).into(holder.annImage);

        // On change les textes pour afficher les informations de l'annonce
        holder.annTitre.setText(annonce.getTitre());
        holder.annCategorie.setText(annonce.getCategorie());
        holder.annPrix.setText(annonce.getPrix());
        holder.annDate.setText(new DateService(annonce.getDateCreation()).show());

        // On appelle la méthode du service favoris qui affichera si l'annonce est en favoris ou non
        favoriteService.draw(annonce, holder.annFavorite);

        // Ajout d'un écouteur de clic sur le bouton d'ajout aux favoris
        holder.annFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On appelle la méthode du service favoris qui gère le clic sur l'ajout de l'annonce en favoris
                favoriteService.click(annonce, ((ImageButton) v));
            }
        });

        // Si l'adaptateur est utilisé depuis l'activité MyList (Ma liste d'annonces)
        if(mContext.getClass() == MyListActivity.class)
        {
            // Ajout d'un écouteur de clic sur le bouton d'édition
            holder.annEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Au clic, on passe à l'activité d'ajout d'une annonce en mode édition en lui passant l'objet Annonce correspondant
                    Intent intent = new Intent(mContext, AddActivity.class);
                    intent.putExtra("editMode", true);
                    intent.putExtra("data", (Serializable) annonce);
                    mContext.startActivity(intent);
                }
            });

            // Ajout d'un écouteur de clic sur le bouton de suppression
            holder.annDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Au clic, on affiche un dialog de confirmation de suppression
                    new AlertDialog.Builder(mContext)
                        .setIcon(R.drawable.ic_warning_black_24dp)
                        .setTitle("Suppression")
                        .setMessage("Voulez-vous vraiment supprimer cette annonce ?")
                        .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Au clic sur le bouton de confirmation, on initialise la librairie Retrofit2 permettant de gérer les appels au webservice
                                HttpService httpService = new Retrofit.Builder().baseUrl(HttpService.ENDPOINT)
                                        .addConverterFactory(GsonConverterFactory.create()).build()
                                        .create(HttpService.class);

                                // On met l'appel de l'endpoint correspondant (deleteAnnonce) en file d'attente (appel asynchrone), la réponse sera renvoyée dans la callback
                                httpService.deleteAnnonce(annonce.getId()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        // Si on obtient une réponse, on vérifie le code http de la réponse
                                        if(response.code() == 200) {
                                            // Si la suppression s'est bien déroulé, on affiche un message de confirmation
                                            Toast.makeText(mContext, "Annonce supprimée avec succès.", Toast.LENGTH_SHORT).show();

                                            if(mContext instanceof MyListActivity) {
                                                // On passe à l'activité MyList (Ma liste d'annonces)
                                                ((MyListActivity) mContext).switchToActivity(MyListActivity.class, false);
                                            }
                                        } else if(response.code() == 400) {
                                            // Si il y a eu une erreur avec la suppression de l'annonce, on affiche un message d'erreur
                                            Toast.makeText(mContext, "Erreur lors de la suppression de l'annonce. Veuillez rééssayer ultérieurement.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        // Si on obtient une erreur, on affiche un message d'erreur
                                        Toast.makeText(mContext, "Une erreur est survenue lors de l'envoi. Vérifiez votre connexion internet puis rééssayez.", Toast.LENGTH_SHORT).show();
                                        Log.d("WebRequest", "Error : " + t.getMessage());
                                    }
                                });

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