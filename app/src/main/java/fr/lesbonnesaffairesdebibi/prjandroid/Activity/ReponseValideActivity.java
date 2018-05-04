package fr.lesbonnesaffairesdebibi.prjandroid.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class ReponseValideActivity extends AppCompatActivity {

    Annonce annonce;


    TextView titreAnnonce;
    TextView descriptionAnnonce;
    TextView prixAnnonce;
    TextView vendeurAnnonce;
    TextView dateAnnonce;
    TextView categorieAnnonce;

    ImageView imageAnnonce;

    Button retourListAnnonce;
    Button retourBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponse_valide);

        //Récupération Text Views
        titreAnnonce = findViewById(R.id.titreAnnonceRep);
        prixAnnonce = findViewById(R.id.prixAnnonceRep);
        vendeurAnnonce = findViewById(R.id.vendeurAnnonceRep);
        dateAnnonce = findViewById(R.id.dateAnnonceRep);
        categorieAnnonce = findViewById(R.id.categorieAnnonceRep);

        imageAnnonce = findViewById(R.id.imageAnnonceRep);

        //Récupération de notre entité transmise par l'Intent
        Intent i = getIntent();
        annonce = (Annonce) i.getSerializableExtra("detail");

        titreAnnonce.setText(annonce.getTitre());
        prixAnnonce.setText(annonce.getPrix() + "€");
        vendeurAnnonce.setText("Vendeur : " + annonce.getNomVendeur());
        dateAnnonce.setText(annonce.getDateCreation());
        categorieAnnonce.setText(annonce.getCategorie());


        //Téléchargement de l'image et insertion dans l'image view
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image);
        Glide.with(this).load(getString(R.string.urlEndPoint) + "images/lesbonsplansdebibi/" + annonce.getImage()).apply(options).into(imageAnnonce);

        retourListAnnonce = findViewById(R.id.btnRetourListAnnonce);
        retourBtn = findViewById(R.id.returnAnnonceRep);

        retourListAnnonce.setOnClickListener(genericOnClicListener);
        retourBtn.setOnClickListener(genericOnClicListener);
    }

    private final View.OnClickListener genericOnClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnRetourListAnnonce:
                    Intent intent = new Intent(ReponseValideActivity.this, ListAnnonceActivity.class);
                    startActivity(intent);
                    break;
                case R.id.returnAnnonceRep:
                    Intent intent2 = new Intent(ReponseValideActivity.this, AnnonceDetailActivity.class);
                    intent2.putExtra("detail",annonce);
                    startActivity(intent2);
                    break;
                default:
                    break;
            }
        }
    };

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
