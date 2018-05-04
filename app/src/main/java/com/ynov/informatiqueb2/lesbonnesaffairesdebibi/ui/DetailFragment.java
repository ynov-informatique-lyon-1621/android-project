package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.DateFormater;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private static final String ARG_ANNOUNCEMENT = "param1";
    private Announcement announcement;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(Announcement announcement) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ANNOUNCEMENT, announcement);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            announcement = (Announcement)getArguments().getSerializable(ARG_ANNOUNCEMENT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_detail, container, false);
        TextView description = v.findViewById(R.id.descDsp);
        TextView title = v.findViewById(R.id.titleDsp);
        TextView categorie = v.findViewById(R.id.categorieDsp);
        ImageView image = v.findViewById(R.id.image);
        TextView vendorName = v.findViewById(R.id.vendorNameDsp);
        TextView date = v.findViewById(R.id.dateDsp);
        TextView price = v.findViewById(R.id.priceDsp);
        Button contactBtn = v.findViewById(R.id.contactBtn);

        description.setText(announcement.getDescription());
        title.setText(announcement.getTitre());
        categorie.setText(getString(R.string.cate_placeholder,announcement.getCategorie()));
        Glide.with(this).load(announcement.getImage()).into(image);
        vendorName.setText(announcement.getNomVendeur());
        date.setText(DateFormater.format(this.announcement.getDateCreation()));
        price.setText(getString(R.string.price_placeholder,this.announcement.getPrix()));


        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = MessageFragment.newInstance(announcement);
                ((BaseActivity)getActivity()).navigate(fragment);
            }
        });

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
