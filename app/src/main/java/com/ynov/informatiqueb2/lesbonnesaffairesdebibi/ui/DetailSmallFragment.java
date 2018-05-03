package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.DateFormater;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailSmallFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailSmallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailSmallFragment extends Fragment {
    private static final String ARG_ANNOUNCEMENT = "ann";
    private Announcement announcement;
    private OnFragmentInteractionListener mListener;

    public DetailSmallFragment() {
        // Required empty public constructor
    }


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
        ImageView imageView = v.findViewById(R.id.imageDsp);

        titleDsp.setText(this.announcement.getTitre());
        priceDsp.setText(getActivity().getString(R.string.price_placeholder,this.announcement.getPrix()));
        cateDsp.setText(getActivity().getString(R.string.cate_placeholder,this.announcement.getCategorie()));
        dateDsp.setText(DateFormater.format(this.announcement.getDateCreation()));
        Glide.with(getActivity()).load(this.announcement.getImage()).into(imageView);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
