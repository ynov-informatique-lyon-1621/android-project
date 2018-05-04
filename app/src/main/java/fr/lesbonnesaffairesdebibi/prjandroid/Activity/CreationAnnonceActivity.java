package fr.lesbonnesaffairesdebibi.prjandroid.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.myhexaville.smartimagepicker.ImagePicker;

import org.apache.http.entity.mime.content.FileBody;


import java.util.regex.Pattern;

import fr.lesbonnesaffairesdebibi.prjandroid.Controller.PostAnnonce;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class CreationAnnonceActivity extends AppCompatActivity {

    ImageView getImage;
    Button btnEnvoie;
    Button btnAnnuler;

    EditText categorie;

    ImagePicker imagePicker;

    EditText email;
    EditText mdp;
    EditText mdpConfirm;
    EditText nomVendeur;
    EditText titre;
    EditText prix;
    EditText localisation;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_annonce);

        getImage = findViewById(R.id.ImageGetter);

        categorie = findViewById(R.id.categorieCreationAnnonce);
        btnEnvoie = findViewById(R.id.btnEnvoyerCreation);
        btnAnnuler = findViewById(R.id.btnAnnulerCreation);

        email = findViewById(R.id.emailCreation);
        mdp = findViewById(R.id.motDePasseCreation);
        mdpConfirm = findViewById(R.id.confirmMotDePasseCreation);
        nomVendeur = findViewById(R.id.nomCreation);
        titre = findViewById(R.id.titreCreation);
        prix = findViewById(R.id.prixCreation);
        localisation = findViewById(R.id.villeCreation);
        description = findViewById(R.id.descriptionCreation);

        getImage.setOnClickListener(genericOnClicListener);
        btnEnvoie.setOnClickListener(genericOnClicListener);
        btnAnnuler.setOnClickListener(genericOnClicListener);
    }

    private final View.OnClickListener genericOnClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ImageGetter:
                    launchImagePicker();
                    break;
                case R.id.btnEnvoyerCreation:
                    sendAnnonce();
                    break;
                case R.id.btnAnnulerCreation:
                    goToListAnnonce();
                    break;
                default:
                    break;
            }
        }
    };

    private void sendAnnonce() {
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Ce champ doit être Renseigné");
        } else {
            final Pattern pattern = Pattern.compile(
                    "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
                    );
            if (!pattern.matcher(email.getText().toString()).matches()) {
                email.setError("Veuillez entrer un mail valide");
            } else {
                if (TextUtils.isEmpty(mdp.getText().toString())) {
                    mdp.setError("Ce champ doit être Renseigné");
                } else {
                    if (TextUtils.isEmpty(mdpConfirm.getText().toString())) {
                        mdpConfirm.setError("Ce champ doit être Renseigné");
                    } else {
                        if (mdp.getText().toString().equals(mdpConfirm.getText().toString())) {
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
                                                Annonce annonce = new Annonce();

                                                annonce.setEmail(email.getText().toString());
                                                annonce.setMdp(mdp.getText().toString());
                                                annonce.setNomVendeur(nomVendeur.getText().toString());
                                                annonce.setTitre(titre.getText().toString());
                                                annonce.setPrix(Double.valueOf(prix.getText().toString()));
                                                annonce.setLocalisation(localisation.getText().toString());
                                                annonce.setDescription(description.getText().toString());
                                                annonce.setCategorie(categorie.getText().toString());

                                                new PostAnnonce(this).execute(annonce);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            mdp.setError("Le mot de passe ne correspond pas");
                            mdpConfirm.setError("Le mot de passe ne correspond pas");
                        }
                    }
                }
            }
        }
    }

    private void goToListAnnonce() {
        Intent intent = new Intent(this, ListAnnonceActivity.class);
        startActivity(intent);
    }

    private void launchImagePicker() {

        imagePicker = new ImagePicker(this, /* activity non null*/
                null, /* fragment nullable*/
                imageUri -> {/*on image picked */
                    getImage.setImageURI(imageUri);
                })
                .setWithImageCrop(
                        2 /*aspect ratio x*/,
                        2/*aspect ratio y*/);

        imagePicker.choosePicture(true /*show camera intents*/);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
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
