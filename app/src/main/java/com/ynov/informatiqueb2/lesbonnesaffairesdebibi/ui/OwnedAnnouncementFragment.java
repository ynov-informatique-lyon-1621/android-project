package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

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

public class OwnedAnnouncementFragment extends Fragment {
    private static final String ARG_CREDENTIALS  = "credentials";

    Map<String,String> credentials  = new HashMap<String,String>();
    RecyclerView list;

    public OwnedAnnouncementFragment() {}


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
