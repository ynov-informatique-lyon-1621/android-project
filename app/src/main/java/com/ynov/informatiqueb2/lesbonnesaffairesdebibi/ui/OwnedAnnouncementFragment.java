package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter.AnnouncementAdapter;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.AlertUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OwnedAnnouncementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OwnedAnnouncementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnedAnnouncementFragment extends Fragment {
    private static final String ARG_CREDENTIALS  = "credentials";

    // TODO: Rename and change types of parameters
    Map<String,String> credentials  = new HashMap<String,String>();
    RecyclerView list;

    private OnFragmentInteractionListener mListener;

    public OwnedAnnouncementFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static OwnedAnnouncementFragment newInstance(HashMap<String,String> credentials) {
        OwnedAnnouncementFragment fragment = new OwnedAnnouncementFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CREDENTIALS,credentials);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            credentials = (HashMap<String,String>)getArguments().getSerializable(ARG_CREDENTIALS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_owned_announcement, container, false);
        this.list = v.findViewById(R.id.list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.list.setLayoutManager(layoutManager);
        fetchAnnouncements();
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

    public void fetchAnnouncements() {
        ApiService.getInstance().getOwnedAnnonces(this.credentials).enqueue(this.onAnnouncementsReceived);
    }

    private void navToHome() {
        Fragment fragment = AnnouncementListFragment.newInstance();
        ((BaseActivity)getActivity()).navigate(fragment);
    }

    protected Callback<List<Announcement>> onAnnouncementsReceived = new Callback<List<Announcement>>() {
        @Override
        public void onResponse(@NonNull Call<List<Announcement>> call, Response<List<Announcement>> response) {
            if(response.body() != null & response.code()==200) {
                if (response.body().size() > 0) {

                    list.setAdapter(new AnnouncementAdapter(response.body(), getActivity(), AnnouncementAdapter.EDITITON_MODE));
                } else {
                    navToHome();
                }
            }else {
                Log.w("HTTP FAILURE CODE",String.valueOf(response.code()));
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<Announcement>> call, Throwable t) {
            AlertUtils.alertFailure(getActivity());
            Log.e("REQUEST FAILURE",t.getMessage());
        }
    };
}
