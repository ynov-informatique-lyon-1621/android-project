package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.Constant;

import java.util.Date;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    private List<Announcement> dataset;
    RequestManager glide;
    public AnnouncementAdapter(List<Announcement> dataset, RequestManager glide) {
        this.dataset = dataset;
        this.glide = glide;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView label;
        public TextView date;
        public TextView price;
        public ImageView imageView;

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
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Announcement announcement = this.dataset.get(position);
        holder.label.setText(announcement.getTitre());
        holder.price.setText(String.valueOf(announcement.getPrix()) + " €");
        holder.date.setText(Constant.dateFormat.format(new Date()));
        this.glide.load(announcement.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }
}
