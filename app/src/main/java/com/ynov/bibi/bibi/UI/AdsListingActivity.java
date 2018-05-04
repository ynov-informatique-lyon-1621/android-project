package com.ynov.bibi.bibi.UI;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.ynov.bibi.bibi.R;

import com.ynov.bibi.bibi.Adapter.AdsAdapater;
import com.ynov.bibi.bibi.Services.GetData;

import static com.ynov.bibi.bibi.StaticClass.SupplyDepot._currentAds;
import static com.ynov.bibi.bibi.StaticClass.SupplyDepot.connected;

//Activité listant les annonces
public class AdsListingActivity extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener{

    private ListView content;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_listing);

        setupDrawer();

        setTitle("Liste d'annonces");

        //setup du spinner
        Spinner categorieSelec = findViewById(R.id.categorySearch);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        // On spécifie le layout à utiliser lorsque les choix apparaissent
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorieSelec.setAdapter(adapterSpinner);

        content = findViewById(R.id._AdsList);
        if (_currentAds == null)
            new GetData().get(this);
        else
            content.setAdapter(new AdsAdapater(this, _currentAds));
    }

    public void setupDrawer()
    {

        //On active la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //On active le Drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Le drawer va écouter les actions utilisateur pour s'ouvrir ou se fermé
        drawer.addDrawerListener(toggle);
        toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Nous vérifions si l'utilisateur et connecter et nous affichons / dissimulons certains élément en conséquence.
        if (!connected)
        {
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(false);
        }
        else
        {
            navigationView.getMenu().getItem(0).setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Fonction permettant de faire des actions au click d'un élément dans le drawer
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.drawer_add_add) {
            Intent goToAdd = new Intent(AdsListingActivity.this, CreationAdsActivity.class);
            startActivity(goToAdd);
        }
        else if (id == R.id.drawer_favorite) {

        }
        else if (id == R.id.drawer_identification) {
            Intent goToLogin = new Intent(AdsListingActivity.this, LoginActivity.class);
            startActivity(goToLogin);
        }
        else if (id == R.id.drawer_layout) {

        }
        else if (id == R.id.drawer_list_ads)
        {

        }
        else if (id == R.id.drawer_logout)
        {
            connected = false;
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AdsListingActivity.this);
            sharedPreferences.edit()
                    .putString("login", "")
                    .putString("pwd", "")
                    .commit();
            Intent goToHere = new Intent(AdsListingActivity.this, AdsListingActivity.class);
            startActivity(goToHere);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
