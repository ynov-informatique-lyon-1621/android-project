package fr.lesbonnesaffairesdebibi.prjandroid.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fr.lesbonnesaffairesdebibi.prjandroid.Adapter.ListEntreeAdapter;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Favoris;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class ListeFavorisActivity extends AppCompatActivity {

    ArrayList<Annonce> fav = Favoris.getInstance().getFavoris();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_favoris);
        loadFav();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFav();
    }

    private void loadFav() {
        //Création d'un adaptateur pour l'affichage de nos entités
        ListEntreeAdapter entreeListAdapter = new ListEntreeAdapter(ListeFavorisActivity.this, "fav", fav);
        //Création de la list view cible
        listView = findViewById(R.id.listFavoris);
        //Lien avec l'adaptateur
        listView.setAdapter(entreeListAdapter);

        //Ajouter la posibilités de cliquer sur les item
        listView.setClickable(true);
        //Mise en place de l'event on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //Récupération de l'entité pour l'item cliqué
                Annonce item = (Annonce) listView.getItemAtPosition(position);
                //Création de l'intent vers la page détail
                Intent intent = new Intent(ListeFavorisActivity.this, AnnonceDetailActivity.class);
                //Ajout de l'entiés à l'intent pour pouvoir l'avoir sur la page intent
                intent.putExtra("detail", item);
                //Changement d'activité
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_list_annonce, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accueilMenu:
                Intent intent = new Intent(this, ListAnnonceActivity.class);
                startActivity(intent);
                break;
            case R.id.favorisMenu:
                Intent intent2 = new Intent(this, ListeFavorisActivity.class);
                startActivity(intent2);
                break;
            case R.id.deposeAnnonceMenu:
                Intent intent3 = new Intent(this, CreationAnnonceActivity.class);
                startActivity(intent3);
                break;
            case R.id.modifierAnnonceMenu:
                Intent intent4 = new Intent(this, IdentificationActivity.class);
                startActivity(intent4);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
