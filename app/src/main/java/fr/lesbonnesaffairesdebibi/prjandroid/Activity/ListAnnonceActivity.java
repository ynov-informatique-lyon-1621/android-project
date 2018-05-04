package fr.lesbonnesaffairesdebibi.prjandroid.Activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import fr.lesbonnesaffairesdebibi.prjandroid.Adapter.ListEntreeAdapter;
import fr.lesbonnesaffairesdebibi.prjandroid.Controller.GetAllAnnonce;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Favoris;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class ListAnnonceActivity extends AppCompatActivity {

    ListView listAnnonce;

    Button btn;

    EditText researchText;
    EditText localisation;

    Spinner categorie;
    static final String[] categorieStr = {"", "Voitures", "Vêtements"};

    //Récupération du système de cache grace au lien avec notre activités
    Favoris fav = Favoris.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_annonce);

        //Lien Edit Text / Button
        researchText = findViewById(R.id.researchListAnnonce);
        localisation = findViewById(R.id.localisationListAnnonce);

        categorie = findViewById(R.id.categorieCreationAnnonce);

        btn = findViewById(R.id.rechercherListAnnonce);

        //Configuration du Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListAnnonceActivity.this,
                android.R.layout.simple_spinner_item, categorieStr);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorie.setAdapter(adapter);

        RelativeLayout myLayout = findViewById(R.id.relativeLayoutListAnnonce);
        myLayout.requestFocus();
        btn.setOnClickListener(genericOnClicListener);

        //Récupération de la lst view
        listAnnonce = findViewById(R.id.listAnnonce);
        loadList();
        //Ajouter la posibilités de cliquer sur les item
        listAnnonce.setClickable(true);
        //Mise en place de l'event on click
        listAnnonce.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //Récupération de l'entité pour l'item cliqué
                Annonce item = (Annonce) listAnnonce.getItemAtPosition(position);
                //Création de l'intent vers la page détail
                Intent intent = new Intent(ListAnnonceActivity.this, AnnonceDetailActivity.class);
                //Ajout de l'entiés à l'intent pour pouvoir l'avoir sur la page intent
                intent.putExtra("detail", item);
                //Changement d'activité
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadList();
    }

    private void loadList() {
        //Récupération des données et mise en place dans la list view personnalisé
        new GetAllAnnonce(ListAnnonceActivity.this, null, null, null).execute();
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

    private final View.OnClickListener genericOnClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rechercherListAnnonce:
                    research();
                    break;
                default:
                    break;
            }
        }
    };

    private void research() {
        new GetAllAnnonce(ListAnnonceActivity.this, researchText.getText().toString(), categorie.getSelectedItem().toString(), localisation.getText().toString()).execute();
    }
}
