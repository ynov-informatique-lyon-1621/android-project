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
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;

import java.util.Comparator;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    EndDrawerToggle toggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Définition du titre global
        setTitle("LesBonnesAffairesDeBibi.fr");

        // Déclaration de la barre d'action globale
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Déclaration du menu global
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new EndDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // Ajout d'un écouteur d'évènements sur le menu et synchronisation du menu
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Déclaration du contenu du menu et ajout d'un écouteur de clic des items
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        // Si le bouton "Retour" du téléphone est cliqué et que le menu est ouvert, on le ferme
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
        // Appelé quand un item du menu est cliqué
        // On récupère l'id de l'item et on le définit comme sélectionné
        int id = item.getItemId();
        item.setChecked(true);

        // Redirection vers les activités correspondantes en fonction de l'item sélectionné
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
            case R.id.nav_add: // Mon compte: Activité ajouter une annonce
                switchToActivity(AddActivity.class, false);
                break;
        }

        return true;
    }

    public void switchToActivity(final Class activity, boolean authNeeded) {
        if(activity != null) {
            // Si l'activité nécessite une connexion, on vérifie que l'utilisateur est bien connecté
            if(!authNeeded || checkAuthenticated()) {
                // Timer court (150 ms) pour éviter les bugs d'affichage lors de la fermeture du menu et du changement d'activité
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Redirection vers l'activité
                        Intent intent = new Intent(getApplicationContext(), activity);
                        startActivity(intent);
                        // Désactivation de l'animation de transition entre les activités
                        overridePendingTransition(0, 0);
                    }
                }, 150);
            }

            // Fermeture du menu
            drawer.closeDrawers();
        }
    }

    public boolean isValidEmail(CharSequence target) {
        // Vérifie si l'adresse mail passée en paramètre est valide (retourne true ou false)
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public class ListComparator implements Comparator<Annonce> {
        // Compare deux dates d'objets Annonce (utilisé pour le tri par date dans les Listview)
        @Override
        public int compare(Annonce obj1, Annonce obj2) {
            if(obj1.getDateCreation() == null || obj2.getDateCreation() == null)
                return 0;
            else
                return obj2.getDateCreation().compareTo(obj1.getDateCreation());
        }
    }

    private boolean checkAuthenticated() {
        // Vérifie si l'utilisateur est connecté
        // Utilisation de SharedPreferences pour accéder aux valeurs en mémoire (récupération de la valeur email et password)
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String email = sp.getString("email", "");
        String password = sp.getString("password", "");

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            // Si les valeurs email et password sont vides, l'utilisateur n'est pas connecté
            // On affiche alors un message et on le redirige vers l'activité contenant le formulaire de connexion
            Toast.makeText(this, "Vous devez être connecté", Toast.LENGTH_SHORT).show();
            switchToActivity(LoginActivity.class, false);
            return false;
        } else {
            // Si les valeurs email et password sont renseignées, l'utilisateur est connecté
            return true;
        }
    }
}
