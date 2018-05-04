package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.DateFormater;

public class DetailSmallFragment extends Fragment {
    private static final String ARG_ANNOUNCEMENT = "ann";
    private Announcement announcement;

    public DetailSmallFragment() { }


    public static DetailSmallFragment newInstance(Announcement announcement) {
        DetailSmallFragment fragment = new DetailSmallFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ANNOUNCEMENT, announcement);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            announcement =(Announcement)getArguments().getSerializable(ARG_ANNOUNCEMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_detail_small, container, false);
        TextView titleDsp = v.findViewById(R.id.titleDsp);
        TextView priceDsp = v.findViewById(R.id.priceDsp);
        TextView cateDsp = v.findViewById(R.id.categorieDsp);
        TextView dateDsp = v.findViewById(R.id.dateDsp);
        TextView vendorDsp = v.findViewById(R.id.vendorDsp);
        ImageView imageView = v.findViewById(R.id.imageDsp);

        titleDsp.setText(this.announcement.getTitre());
        priceDsp.setText(getActivity().getString(R.string.price_placeholder,this.announcement.getPrix()));
        cateDsp.setText(getActivity().getString(R.string.cate_placeholder,this.announcement.getCategorie()));
        dateDsp.setText(DateFormater.format(this.announcement.getDateCreation()));
        vendorDsp.setText(getString(R.string.vendor_placeholder,this.announcement.getNomVendeur()));
        Glide.with(getActivity()).load(this.announcement.getImage()).into(imageView);
        return v;
    }
}
