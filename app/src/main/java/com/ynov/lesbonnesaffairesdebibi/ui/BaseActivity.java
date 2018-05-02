package com.ynov.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.component.EndDrawerToggle;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    EndDrawerToggle toggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new EndDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.END)) {
            drawer.closeDrawer(Gravity.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);

        switch (id) {
            case R.id.nav_list: // Activité liste des annonces
                switchToActivity(ListActivity.class, false);
                break;
            case R.id.nav_fav: // Activité liste des favoris
                switchToActivity(FavoritesActivity.class, false);
                break;
            case R.id.nav_my_list: // Mon compte: Activité liste de mes annonces
                switchToActivity(MyListActivity.class, true);
                break;
            case R.id.nav_add: // Activité ajouter une annonce
                switchToActivity(AddActivity.class, false);
                break;
        }

        return true;
    }

    private void switchToActivity(final Class activity, boolean authNeeded) {
        if(activity != null) {
            if(!authNeeded || checkAuthenticated()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), activity);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                }, 150);
            }

            drawer.closeDrawers();
        }
    }

    private boolean checkAuthenticated() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String email = sp.getString("email", "");
        String password = sp.getString("password", "");

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vous devez être connecté", Toast.LENGTH_SHORT).show();
            switchToActivity(LoginActivity.class, false);
            return false;
        } else {
            return true;
        }
    }
}
