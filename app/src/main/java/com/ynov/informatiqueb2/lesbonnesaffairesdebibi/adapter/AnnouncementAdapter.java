package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.FavoritesAnnoucementsManager;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui.DetailActivity;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui.EditionActivity;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui.OwnedAnnoucementListActivity;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.DateFormater;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    public static final int DEFAULT_MODE = 0;
    public static final int FAV_ONLY_MODE = 1;
    public static final int EDITITON_MODE = 2;

    private List<Announcement> dataset;
    private RequestManager glide;
    private FavoritesAnnoucementsManager favoritesAnnoucementsManager;
    private WeakReference<Activity> activityWeakReference;
    private int mode;
    public AnnouncementAdapter(List<Announcement> dataset, Activity activity) {
        this(dataset,activity,DEFAULT_MODE);
    }

    public AnnouncementAdapter(List<Announcement> dataset, Activity activity, int mode) {
        this.dataset = dataset;

        this.activityWeakReference = new WeakReference<Activity>(activity);

        this.glide = Glide.with(this.activityWeakReference.get());

        this.favoritesAnnoucementsManager = new FavoritesAnnoucementsManager(this.activityWeakReference);

        this.mode = mode;

        if(this.mode == FAV_ONLY_MODE) {
            List<Announcement> filteredDataset = new ArrayList<>();
            for (int i = 0; i < dataset.size(); i++) {
                if(this.favoritesAnnoucementsManager.isFav(dataset.get(i).getId()))
                {
                    filteredDataset.add(dataset.get(i));
                }
            }
            this.dataset = filteredDataset;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView label;
        public TextView date;
        public TextView price;
        public ImageView imageView;
        public ToggleButton favButton;
        public View fullView;
        public ImageButton editButton;
        public ImageButton deleteButton;
        public LinearLayout editionTools;


        private ViewHolder(View v) {
            super(v);
            this.fullView = v;
            this.label = v.findViewById(R.id.label);
            this.imageView = v.findViewById(R.id.imageView);
            this.price = v.findViewById(R.id.price);
            this.date = v.findViewById(R.id.date);
            this.favButton = v.findViewById(R.id.favBtn);
            this.editButton = v.findViewById(R.id.editBtn);
            this.deleteButton = v.findViewById(R.id.deleteBtn);
            this.editionTools = v.findViewById(R.id.editionTools);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.annoucement_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Announcement announcement = this.dataset.get(position);

        //Load date in fields.
        holder.label.setText(announcement.getTitre());
        holder.price.setText(this.activityWeakReference.get().getString(R.string.price_placeholder,announcement.getPrix()));
        holder.date.setText(DateFormater.format(announcement.getDateCreation()));
        this.glide.load(announcement.getImage()).into(holder.imageView);
        holder.favButton.setChecked(this.favoritesAnnoucementsManager.isFav(announcement.getId()));

        //Add listener to fav button to toggle fav.
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritesAnnoucementsManager.toggleFav(announcement.getId());
            }
        });

        //Add listener to full view to navigate to detail.
        holder.fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navToDetailView(announcement);
            }
        });

        //If edition mode display edition tools
        if(mode == EDITITON_MODE) {
            holder.editionTools.setVisibility(View.VISIBLE);
            holder.favButton.setVisibility(View.GONE);

            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navToEditionView(announcement);
                }
            });

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteAnnouncement(announcement);
                }
            });

            //TODO : DELETE BUTTON;
        }


    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }

    private void navToDetailView(Announcement announcement) {
        Intent intent = new Intent(activityWeakReference.get(), DetailActivity.class);
        intent.putExtra("annoucement",announcement);
        activityWeakReference.get().startActivity(intent);
    }

    private void navToEditionView(Announcement announcement) {
        Intent intent = new Intent(activityWeakReference.get(), EditionActivity.class);
        intent.putExtra("annoucement",announcement);
        activityWeakReference.get().startActivity(intent);
    }

    private void deleteAnnouncement(Announcement announcement) {
        ApiService.getInstance().deleteAnnonce(announcement.getId()).enqueue(deleteCallback);
    }

    private Callback<Object> deleteCallback = new Callback<Object>() {
        @Override
        public void onResponse(Call call, Response response) {
            if(response.code() == 204) {
                new AlertDialog.Builder(activityWeakReference.get())
                        .setMessage("L'annonce à été supprimée")
                        .setTitle("Opération réussie")
                        .create()
                        .show();
                if(activityWeakReference.get().getClass().equals(OwnedAnnoucementListActivity.class)){
                    ((OwnedAnnoucementListActivity)activityWeakReference.get()).fetchAnnouncements();
                }
            }
        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }
    };

}
