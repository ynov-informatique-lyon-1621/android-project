package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter.AnnouncementAdapter;

import java.util.List;

public class BaseActivity extends AppCompatActivity  implements
        MessageFormFragment.OnFragmentInteractionListener
{

    Toolbar toolbar;
    DrawerLayout navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        toolbar = findViewById(R.id.toolbar);
        navDrawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        navigationView.setNavigationItemSelectedListener(this.navigationItemSelectedListener);

        navigate( AnnouncementListFragment.newInstance());

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.default_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                navDrawer.openDrawer(Gravity.END);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_fav:
                    fragment= AnnouncementListFragment.newInstance(AnnouncementAdapter.FAV_ONLY_MODE);
                break;
                case R.id.action_add:
                    fragment = EditionFragment.newInstance();
                    break;
                case R.id.action_edit:
                    fragment = LoginFragment.newInstance();
                    break;
                case R.id.action_home:
                default:
                    fragment = AnnouncementListFragment.newInstance();
                    break;
            }
            navigate(fragment);
            navDrawer.closeDrawer(Gravity.END);
            return true;
        }
    };

    public void navigate(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack("PREVIOUS")
                .commit();
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof EditionFragment) {
                    f.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    public void onMessageSent() {
        ((MessageFragment)getSupportFragmentManager().findFragmentById(R.id.content_frame)).showConfirmation();
    }
}
