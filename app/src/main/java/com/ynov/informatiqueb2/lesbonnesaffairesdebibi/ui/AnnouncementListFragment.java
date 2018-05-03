package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.chuross.library.ExpandableLayout;
import com.google.gson.Gson;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter.AnnouncementAdapter;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiInterface;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnnouncementListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnnouncementListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnnouncementListFragment extends Fragment {

    private static final String ARG_MODE = "mode";
    private int mode;
    RecyclerView list;
    Map<String, String> filters = new HashMap<>();
    ExpandableLayout expandableLayout;
    TextView emptyAlert;
    EditText locationIpt;
    EditText search;
    Spinner typeSpinner;

    private OnFragmentInteractionListener mListener;

    public AnnouncementListFragment() {
        // Required empty public constructor
    }

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
        TextView filterToogle = v.findViewById(R.id.filterToogle);
        locationIpt = v.findViewById(R.id.locationIpt);
        Button filterBtn = v.findViewById(R.id.filterBtn);
        this.expandableLayout = v.findViewById(R.id.expandable);
        emptyAlert = v.findViewById(R.id.empty);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.list.setLayoutManager(layoutManager);

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

    private void fetchAnnouncements() {
        Log.i("filters", filters.toString());
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
                Log.i("click", "collpse");
            } else if (expandableLayout.isCollapsed()) {
                expandableLayout.expand();
                Log.i("click", "exp");
            }
        }
    };

    private View.OnClickListener onFilterClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!TextUtils.isEmpty(locationIpt.getText())) {
                filters.put("localisation", locationIpt.getText().toString());
            } else {
                filters.remove("localisation");
            }
            if (!TextUtils.isEmpty(search.getText())) {
                filters.put("motCle", search.getText().toString());
            } else {
                filters.remove("motCle");
            }
            String typeSelected = typeSpinner.getSelectedItem().toString();
            if (!typeSelected.equals(getString(R.string.all))) {
                filters.put("categorie", typeSelected);
            } else {
                filters.remove("categorie");
            }
            fetchAnnouncements();
            saveFilters();
        }
    };

    private void saveFilters() {
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                .putString("filters", new Gson().toJson(this.filters))
                .apply();
    }

    private void getSavedFilters() {
        String filterString = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("filters", "");
        if (!TextUtils.isEmpty(filterString)) {
            this.filters = new Gson().fromJson(filterString, new HashMap<String, String>().getClass());
            this.locationIpt.setText(this.filters.get("localisation"));
            this.search.setText(this.filters.get("motCle"));
            ArrayAdapter<String> array_spinner = (ArrayAdapter<String>) this.typeSpinner.getAdapter();
            String selectedString = this.filters.get("categorie");
            if (!TextUtils.isEmpty(selectedString)) {
                this.typeSpinner.setSelection(array_spinner.getPosition(selectedString));
            }
        }
    }
}
