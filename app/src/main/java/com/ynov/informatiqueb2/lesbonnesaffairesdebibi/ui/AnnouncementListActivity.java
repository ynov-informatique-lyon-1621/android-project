package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.chuross.library.ExpandableLayout;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter.AnnouncementAdapter;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiInterface;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnouncementListActivity extends BaseActivity {

    RecyclerView list;
    Map<String, String> filters = new HashMap<>();
    ExpandableLayout expandableLayout;
    TextView emptyAlert;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init fields
        EditText search = findViewById(R.id.search);
        this.list = findViewById(R.id.list);
        Spinner typeSpinner = findViewById(R.id.type);
        TextView filterToogle = findViewById(R.id.filterToogle);
        EditText locationIpt = findViewById(R.id.locationIpt);
        this.expandableLayout = findViewById(R.id.expandable);
        emptyAlert = findViewById(R.id.empty);

        Intent intent = getIntent();
        this.mode = intent.getIntExtra("mode", AnnouncementAdapter.DEFAULT_MODE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.list.setLayoutManager(layoutManager);

        //Add listeners
        locationIpt.addTextChangedListener(this.locationWatcher);
        search.addTextChangedListener(this.searchWatcher);
        typeSpinner.setOnItemSelectedListener(typeChangeListener);
        filterToogle.setOnClickListener(collapseListener);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_announcement_list;
    }

    private void fetchAnnouncements() {
        ApiInterface apiInterface = ApiService.getInstance();
        apiInterface.getAnnonces(this.filters).enqueue(this.callback);
    }

    private Callback<List<Announcement>> callback = new Callback<List<Announcement>>() {
        @Override
        public void onResponse(@NonNull Call<List<Announcement>> call, Response<List<Announcement>> response) {
            if (response.body() != null && response.code() == 200) {
                AnnouncementAdapter adapter = new AnnouncementAdapter(response.body(), AnnouncementListActivity.this, AnnouncementListActivity.this.mode);
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


    TextWatcher locationWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                filters.remove("localisation");
            } else {
                filters.put("localisation", s.toString());
            }
            fetchAnnouncements();
        }
    };

    TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                filters.remove("motCle");
            } else {
                filters.put("motCle", s.toString());
            }
            fetchAnnouncements();
        }
    };

    protected AdapterView.OnItemSelectedListener typeChangeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                filters.remove("categorie");
            } else {
                filters.put("categorie", (String) parent.getItemAtPosition(position));
            }
            fetchAnnouncements();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
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
}
