package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class OwnedAnnoucementListActivity extends BaseActivity {

    RecyclerView list;
    Map<String,String> params  = new HashMap<String,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.list = findViewById(R.id.list);
        this.params = ((Map<String, String>)getIntent().getSerializableExtra("credentials"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.list.setLayoutManager(layoutManager);
        fetchAnnouncements();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_owned_annoucement_list;
    }

    public void fetchAnnouncements() {
        ApiService.getInstance().getOwnedAnnonces(this.params).enqueue(this.onAnnouncementsReceived);
    }

    private void navToHome() {
        Intent intent = new Intent(OwnedAnnoucementListActivity.this,AnnouncementListActivity.class);
        startActivity(intent);
    }

    protected Callback<List<Announcement>> onAnnouncementsReceived = new Callback<List<Announcement>>() {
        @Override
        public void onResponse(@NonNull Call<List<Announcement>> call, Response<List<Announcement>> response) {
            if(response.body() != null & response.code()==200) {
                if (response.body().size() > 0) {

                    list.setAdapter(new AnnouncementAdapter(response.body(), OwnedAnnoucementListActivity.this, AnnouncementAdapter.EDITITON_MODE));
                } else {
                    navToHome();
                }
            }else {
                Log.w("HTTP FAILURE CODE",String.valueOf(response.code()));
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<Announcement>> call, Throwable t) {
            AlertUtils.alertFailure(OwnedAnnoucementListActivity.this);
            Log.e("REQUEST FAILURE",t.getMessage());
        }
    };
}
