package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter.AnnouncementAdapter;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

public class OwnedAnnoucementListActivity extends BaseActivity {

    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        List<Announcement> announcements = (List<Announcement>) intent.getSerializableExtra("annoucements");
        Log.i("caca",announcements.toString());

        this.list = findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.list.setLayoutManager(layoutManager);
        this.list.setAdapter(new AnnouncementAdapter(announcements, OwnedAnnoucementListActivity.this,AnnouncementAdapter.EDITITON_MODE));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_owned_annoucement_list;
    }
}
