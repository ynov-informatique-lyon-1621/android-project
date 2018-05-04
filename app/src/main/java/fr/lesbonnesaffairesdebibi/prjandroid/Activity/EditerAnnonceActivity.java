package fr.lesbonnesaffairesdebibi.prjandroid.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import fr.lesbonnesaffairesdebibi.prjandroid.Controller.UpdateAnnonce;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class EditerAnnonceActivity extends AppCompatActivity {
    ImageView getImage;

    Button btnEnvoie;
    Button btnAnnuler;

    Spinner categorie;

    EditText nomVendeur;
    EditText titre;
    EditText prix;
    EditText localisation;
    EditText description;

    Annonce annonce;

    String[] categorieStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editer_annonce);

        getImage = findViewById(R.id.ImageGetterEdition);

        categorie = findViewById(R.id.categorieEditionAnnonce);

        btnEnvoie = findViewById(R.id.btnEnvoyerEdition);
        btnAnnuler = findViewById(R.id.btnAnnulerEdition);

        nomVendeur = findViewById(R.id.nomEdition);
        titre = findViewById(R.id.titreEdition);
        prix = findViewById(R.id.prixEdition);
        localisation = findViewById(R.id.villeEdition);
        description = findViewById(R.id.descriptionEdition);


        //Récupération de notre entité transmise par l'Intent
        Intent i = getIntent();
        annonce = (Annonce) i.getSerializableExtra("detail");

        nomVendeur.setText(annonce.getNomVendeur());
        titre.setText(annonce.getTitre());
        prix.setText(String.valueOf(annonce.getPrix()));
        localisation.setText(annonce.getLocalisation());
        description.setText(annonce.getDescription());

        //Configuration du Spinner
        categorieStr = new String[]{annonce.getCategorie(), "Voitures", "Vêtements"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categorieStr);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorie.setAdapter(adapter);

        //Téléchargement de l'image et insertion dans l'image view
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image);
        Glide.with(this).load(getString(R.string.urlEndPoint) + "images/lesbonsplansdebibi/" + annonce.getImage()).apply(options).into(getImage);

        btnAnnuler.setOnClickListener(genericOnClicListener);
        btnEnvoie.setOnClickListener(genericOnClicListener);
    }

    private final View.OnClickListener genericOnClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnEnvoyerEdition:
                    envoyerEdition();
                    break;
                case R.id.btnAnnulerEdition:
                    annulerEdition();
                    break;
                default:
                    break;
            }
        }
    };

    private void annulerEdition() {

        //Récupération du système de cache grace au lien avec notre activités
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = new Intent(this, ListeAnnonceUtilisateurActivity.class)
                .putExtra("mail", sharedPreferences.getString("mail", ""))
                .putExtra("mdp", sharedPreferences.getString("mdp", ""));
        startActivity(intent);
    }

    private void envoyerEdition() {
        if (TextUtils.isEmpty(nomVendeur.getText().toString())) {
            nomVendeur.setError("Ce champ doit être Renseigné");
        } else {
            if (TextUtils.isEmpty(titre.getText().toString())) {
                titre.setError("Ce champ doit être Renseigné");
            } else {
                if (TextUtils.isEmpty(prix.getText().toString())) {
                    prix.setError("Ce champ doit être Renseigné");
                } else {
                    if (TextUtils.isEmpty(localisation.getText().toString())) {
                        localisation.setError("Ce champ doit être Renseigné");
                    } else {
                        if (TextUtils.isEmpty(description.getText().toString())) {
                            description.setError("Ce champ doit être Renseigné");
                        } else {
                            annonce.setNomVendeur(nomVendeur.getText().toString());
                            annonce.setTitre(titre.getText().toString());
                            annonce.setPrix(Double.valueOf(prix.getText().toString()));
                            annonce.setLocalisation(localisation.getText().toString());
                            annonce.setDescription(description.getText().toString());
                            new UpdateAnnonce(this).execute(annonce);
                        }
                    }
                }
            }
        }
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
