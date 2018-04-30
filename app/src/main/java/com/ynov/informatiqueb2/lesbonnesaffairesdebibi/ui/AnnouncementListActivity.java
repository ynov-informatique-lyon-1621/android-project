package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chuross.library.ExpandableLayout;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter.AnnouncementAdapter;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiInterface;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.Constant;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnouncementListActivity extends BaseActivity {

    RecyclerView list;
    Map<String,String> filters = new HashMap<>();
    ExpandableLayout expandableLayout;
    TextView emptyAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_announcement_list);

        EditText dateInput = findViewById(R.id.dateIpt);
        dateInput.setOnFocusChangeListener(this.dateInputListener);

        this.list = findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.list.setLayoutManager(layoutManager);

        EditText search = findViewById(R.id.search);
        search.addTextChangedListener(this.searchWatcher);

        Spinner typeSpinner = findViewById(R.id.type);
        typeSpinner.setOnItemSelectedListener(typeChangeListener);

        TextView filterToogle = findViewById(R.id.filterToogle);
        this.expandableLayout = findViewById(R.id.expandable);

        emptyAlert = findViewById(R.id.empty);

        filterToogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(expandableLayout.isExpanded()) {
                    list.requestFocus();
                    expandableLayout.collapse();
                    Log.i("click","collpse");
                } else if(expandableLayout.isCollapsed()) {
                    expandableLayout.expand();
                    Log.i("click","exp");
                }
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_announcement_list;
    }

    private void fetchAnnouncements() {
        ApiInterface apiInterface = ApiService.getInstance();
        apiInterface.getAnnonces(this.filters).enqueue(this.callback);
    }

    private View.OnFocusChangeListener dateInputListener = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                DatePickerFragment.newInstance(dateChangedListener).show(getSupportFragmentManager(), "dp");
            }
        }
    };

    private Callback<List<Announcement>> callback = new Callback<List<Announcement>>() {
        @Override

        public void onResponse(@NonNull Call<List<Announcement>> call, Response<List<Announcement>> response) {
            AnnouncementAdapter adapter = new AnnouncementAdapter(response.body(),AnnouncementListActivity.this);
            list.setAdapter(adapter);
            if(adapter.getItemCount() == 0) {
                emptyAlert.setVisibility(View.VISIBLE);
            } else {
                emptyAlert.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<Announcement>> call, Throwable t) {
           t.printStackTrace();
        }
    };

    protected DatePickerDialog.OnDateSetListener dateChangedListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            EditText dateIpt = findViewById(R.id.dateIpt);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            if(dateIpt != null) {
                dateIpt.setText(Constant.dateFormat.format(calendar.getTime()));
            }

            //TODO Convert to right date format
            filters.put("date",Constant.ISODateFormat.format(calendar.getTime()));
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
            if(TextUtils.isEmpty(s)) {
                filters.remove("motCle");
            } else {
                filters.put("motCle", s.toString());
            }
            Log.i("debug","fetching");
           fetchAnnouncements();
        }
    };

    protected AdapterView.OnItemSelectedListener typeChangeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0) {
                filters.remove("categorie");
            } else {
                filters.put("categorie",(String)parent.getItemAtPosition(position));
            }
            fetchAnnouncements();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
