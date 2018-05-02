package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter.AnnouncementAdapter;

public abstract class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout navDrawer;
    protected abstract int getLayoutResource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        FrameLayout contentLayout =  findViewById(R.id.content_frame);
        getLayoutInflater().inflate(getLayoutResource(), contentLayout, true);

        toolbar = findViewById(R.id.toolbar);
        navDrawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        navigationView.setNavigationItemSelectedListener(this.navigationItemSelectedListener);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.default_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navDrawer.openDrawer(Gravity.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            item.setChecked(true);
            switch (item.getItemId()) {
                case R.id.action_fav:
                intent = new Intent(BaseActivity.this, AnnouncementListActivity.class);
                intent.putExtra("mode", AnnouncementAdapter.FAV_ONLY_MODE);
                break;
                case R.id.action_add:
                    intent = new Intent(BaseActivity.this, EditionActivity.class);
                    break;
                case R.id.action_edit:
                    intent = new Intent(BaseActivity.this,LoginActivity.class);
                    break;
                case R.id.action_home:
                default:
                    intent = new Intent(BaseActivity.this, AnnouncementListActivity.class);
                    break;
            }
            startActivity(intent);
            return true;
        }
    };
}
