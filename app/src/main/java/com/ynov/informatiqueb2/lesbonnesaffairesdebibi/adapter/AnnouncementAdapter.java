package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.FavoritesAnnoucementsManager;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui.AnnouncementListActivity;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui.DetailActivity;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.Constant;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    private List<Announcement> dataset;
    private RequestManager glide;
    private FavoritesAnnoucementsManager favoritesAnnoucementsManager;
    private WeakReference<Activity> activityWeakReference;
    public AnnouncementAdapter(List<Announcement> dataset, Activity activity) {
        this(dataset,activity,false);
    }

    public AnnouncementAdapter(List<Announcement> dataset, Activity activity, boolean favOnly) {
        this.dataset = dataset;

        this.activityWeakReference = new WeakReference<Activity>(activity);

        this.glide = Glide.with(this.activityWeakReference.get());

        this.favoritesAnnoucementsManager = new FavoritesAnnoucementsManager(this.activityWeakReference);

        if(favOnly) {
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
        // each data item is just a string in this case
        public TextView label;
        public TextView date;
        public TextView price;
        public ImageView imageView;
        public ToggleButton favButton;
        public View fullView = itemView;

        private ViewHolder(View itemView) {
            super(itemView);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.annoucement_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        vh.label = v.findViewById(R.id.label);
        vh.imageView = v.findViewById(R.id.imageView);
        vh.price = v.findViewById(R.id.price);
        vh.date = v.findViewById(R.id.date);
        vh.favButton = v.findViewById(R.id.favBtn);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Announcement announcement = this.dataset.get(position);
        holder.label.setText(announcement.getTitre());
        holder.price.setText(String.valueOf(announcement.getPrix()) + " €");
        holder.date.setText(Constant.dateFormat.format(new Date()));
        this.glide.load(announcement.getImage()).into(holder.imageView);
        holder.favButton.setChecked(this.favoritesAnnoucementsManager.isFav(announcement.getId()));
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CLICKED","FAV BUTTON");
                favoritesAnnoucementsManager.toggleFav(announcement.getId());
            }
        });
        holder.fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CLICKED","FULL VIEW");
                navToDetailView(announcement);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }

    public void navToDetailView(Announcement announcement) {
        Intent intent = new Intent(activityWeakReference.get(), DetailActivity.class);
        intent.putExtra("annoucement",announcement);
        activityWeakReference.get().startActivity(intent);
    }
}
