package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.chuross.library.ExpandableLayout;
import com.google.gson.Gson;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter.AnnouncementAdapter;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiInterface;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnouncementListFragment extends Fragment {
    private static final String ARG_MODE = "mode";
    private static final String FILTER_TYPE = "categorie";
    private static final String FILTER_PLACE = "localisation";
    private static final String FILTER_SEARCH = "motCle";
    private static final String PREF_FILTERS = "filters";
    private int mode;
    RecyclerView list;
    Map<String, String> filters = new HashMap<>();
    ExpandableLayout expandableLayout;
    TextView emptyAlert;
    EditText locationIpt;
    EditText search;
    Spinner typeSpinner;


    public AnnouncementListFragment() {}

    public static AnnouncementListFragment newInstance() {
        return newInstance(AnnouncementAdapter.DEFAULT_MODE);
    }

    public static AnnouncementListFragment newInstance(int mode) {
        AnnouncementListFragment fragment = new AnnouncementListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mode = getArguments().getInt(ARG_MODE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_announcement_list, container, false);
        //Init fields
        search = v.findViewById(R.id.search);
        this.list = v.findViewById(R.id.list);
        typeSpinner = v.findViewById(R.id.type);
        ToggleButton filterToogle = v.findViewById(R.id.filterToogle);
        locationIpt = v.findViewById(R.id.locationIpt);
        Button filterBtn = v.findViewById(R.id.filterBtn);
        this.expandableLayout = v.findViewById(R.id.expandable);
        emptyAlert = v.findViewById(R.id.empty);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.list.setLayoutManager(layoutManager);
        AnnouncementAdapter adapter = new AnnouncementAdapter(new ArrayList<Announcement>(), getActivity(), mode);
        this.list.setAdapter(adapter);

        //Add listeners
        filterToogle.setOnClickListener(collapseListener);
        filterBtn.setOnClickListener(onFilterClicked);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSavedFilters();
        fetchAnnouncements();
    }

    private void fetchAnnouncements() {
        ApiInterface apiInterface = ApiService.getInstance();
        apiInterface.getAnnonces(this.filters).enqueue(this.callback);
    }

    private Callback<List<Announcement>> callback = new Callback<List<Announcement>>() {
        @Override
        public void onResponse(@NonNull Call<List<Announcement>> call, Response<List<Announcement>> response) {
            if (response.body() != null && response.code() == 200) {
                AnnouncementAdapter adapter = new AnnouncementAdapter(response.body(), getActivity(), mode);
                list.setAdapter(adapter);
                if (adapter.getItemCount() > 0) {
                    emptyAlert.setVisibility(View.INVISIBLE);
                } else {
                    emptyAlert.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<Announcement>> call, Throwable t) {
            Log.e("HTTP FAILURE", t.getMessage());
        }
    };


    private View.OnClickListener collapseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (expandableLayout.isExpanded()) {
                list.requestFocus();
                expandableLayout.collapse();
                filter();
            } else if (expandableLayout.isCollapsed()) {
                expandableLayout.expand();
            }
        }
    };

    private View.OnClickListener onFilterClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expandableLayout.collapse();
            filter();
        }
    };

    private void filter() {
        if (!TextUtils.isEmpty(locationIpt.getText())) {
            filters.put(FILTER_PLACE, locationIpt.getText().toString());
        } else {
            filters.remove(FILTER_PLACE);
        }
        if (!TextUtils.isEmpty(search.getText())) {
            filters.put(FILTER_SEARCH, search.getText().toString());
        } else {
            filters.remove(FILTER_SEARCH);
        }
        String typeSelected = typeSpinner.getSelectedItem().toString();
        if (!typeSelected.equals(getString(R.string.cate_all))) {
            filters.put(FILTER_TYPE, typeSelected);
        } else {
            filters.remove(FILTER_TYPE);
        }
        fetchAnnouncements();
        saveFilters();
    }


    private void saveFilters() {
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                .putString(PREF_FILTERS, new Gson().toJson(this.filters))
                .apply();
    }

    private void getSavedFilters() {
        String filterString = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(PREF_FILTERS, "");
        if (!TextUtils.isEmpty(filterString)) {
            this.filters = new Gson().fromJson(filterString, new HashMap<String, String>().getClass());
            this.locationIpt.setText(this.filters.get(FILTER_PLACE));
            this.search.setText(this.filters.get(FILTER_SEARCH));
            ArrayAdapter<String> array_spinner = (ArrayAdapter<String>) this.typeSpinner.getAdapter();
            String selectedString = this.filters.get(FILTER_TYPE);
            if (!TextUtils.isEmpty(selectedString)) {
                this.typeSpinner.setSelection(array_spinner.getPosition(selectedString));
            }
        }
    }
}
