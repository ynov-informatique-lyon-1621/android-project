package fr.lesbonnesaffairesdebibi.prjandroid.Activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Favoris;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class AnnonceDetailActivity extends AppCompatActivity {

    TextView titreAnnonce;
    TextView descriptionAnnonce;
    TextView prixAnnonce;
    TextView vendeurAnnonce;
    TextView dateAnnonce;
    TextView categorieAnnonce;

    ImageView imageAnnonce;

    Button btnGoToRep;

    //Récupération du système de cache grace au lien avec notre activités
    Favoris fav = Favoris.getInstance();

    Annonce annonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_detail);

        ArrayList<Annonce> favTab = Favoris.getInstance().getFavoris();
        for (int y=0;y < favTab.size();y++){
            Log.i("debug fav[" + String.valueOf(y) + "]:",favTab.get(y).getId());
        }

        //Récupération Text Views
        titreAnnonce = findViewById(R.id.TitreAnnonce);
        descriptionAnnonce = findViewById(R.id.DescriptionAnnonce);
        prixAnnonce = findViewById(R.id.PrixAnnonce);
        vendeurAnnonce = findViewById(R.id.VendeurAnnonce);
        dateAnnonce = findViewById(R.id.DateAnnonce);
        categorieAnnonce = findViewById(R.id.CategorieAnnonce);

        btnGoToRep = findViewById(R.id.btnRepAnnonceList);

        //Récupération Image View
        imageAnnonce = findViewById(R.id.ImageAnnonce);

        //Récupération de notre entité transmise par l'Intent
        Intent i = getIntent();
        annonce = (Annonce) i.getSerializableExtra("detail");


        //Téléchargement de l'image et insertion dans l'image view
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image);
        Glide.with(this).load(getString(R.string.urlEndPoint) + "images/lesbonsplansdebibi/" + annonce.getImage()).apply(options).into(imageAnnonce);

        //Mise en place des infos des les text view
        titreAnnonce.setText(annonce.getTitre());
        descriptionAnnonce.setText(annonce.getDescription());
        prixAnnonce.setText(String.valueOf(annonce.getPrix()) + "€");
        vendeurAnnonce.setText(annonce.getNomVendeur());
        dateAnnonce.setText(annonce.getDateCreation());
        categorieAnnonce.setText("Categorie : " + annonce.getCategorie());

        Glide.with(AnnonceDetailActivity.this).load(R.drawable.loading).into(imageAnnonce);

        btnGoToRep.setOnClickListener(genericOnClicListener);
    }

    private final View.OnClickListener genericOnClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnRepAnnonceList:
                    goToReponseActivity();
                    break;
                default:
                    break;
            }
        }
    };

    private void goToReponseActivity(){
        Intent intent = new Intent(this, ReponseActivity.class);
        intent.putExtra("detail", annonce);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_fav, menu);
        if(fav.isInFavoris(annonce)){
            MenuItem item = menu.getItem(0);
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.coeur_plein));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.favorisIcon:
                if(fav.isInFavoris(annonce)){
                    fav.delFavoris(annonce);
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.coeur_vide));
                } else {
                    fav.addFavoris(annonce);
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.coeur_plein));
                }
                return true;
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
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onContextItemSelected(item);
    }
}
