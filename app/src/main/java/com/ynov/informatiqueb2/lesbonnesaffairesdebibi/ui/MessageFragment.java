package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;

public class MessageFragment extends Fragment {
    private static final String ARG_ANNOUNCEMENT = "ann";
    private Announcement announcement;

    public MessageFragment() {}

    public static MessageFragment newInstance(Announcement announcement) {
        MessageFragment fragment = new MessageFragment();
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
        View v =  inflater.inflate(R.layout.fragment_message, container, false);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.smallDetailDsp,DetailSmallFragment.newInstance(this.announcement))
                .commit();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.formOrConfirm,MessageFormFragment.newInstance(Integer.valueOf(this.announcement.getId()),this.announcement.getNomVendeur()))
                .commit();

        return v;
    }

    public void showConfirmation() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.formOrConfirm,MessageConfirmationFragment.newInstance())
                .commit();
    }
}
