package fr.lesbonnesaffairesdebibi.prjandroid.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import fr.lesbonnesaffairesdebibi.prjandroid.Controller.PostReponse;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Annonce;
import fr.lesbonnesaffairesdebibi.prjandroid.Entity.Reponse;
import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class ReponseActivity extends AppCompatActivity {

    Annonce annonce;


    TextView titreAnnonce;
    TextView descriptionAnnonce;
    TextView prixAnnonce;
    TextView vendeurAnnonce;
    TextView dateAnnonce;
    TextView categorieAnnonce;

    ImageView imageAnnonce;

    EditText nomRep;
    EditText emailRep;
    EditText numRep;
    EditText messageRep;

    Button envoyerBtn;
    Button retourBtn;
    Button reinitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponse);


        //Récupération Text Views
        titreAnnonce = findViewById(R.id.titreAnnonceRep);
        prixAnnonce = findViewById(R.id.prixAnnonceRep);
        vendeurAnnonce = findViewById(R.id.vendeurAnnonceRep);
        dateAnnonce = findViewById(R.id.dateAnnonceRep);
        categorieAnnonce = findViewById(R.id.categorieAnnonceRep);

        imageAnnonce = findViewById(R.id.imageAnnonceRep);

        nomRep = findViewById(R.id.nomReponse);
        emailRep = findViewById(R.id.emailReponse);
        numRep = findViewById(R.id.numReponse);
        messageRep = findViewById(R.id.messageRep);

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

        envoyerBtn = findViewById(R.id.btnEnvoyerRep);
        reinitBtn = findViewById(R.id.btnReinitRep);
        retourBtn = findViewById(R.id.returnAnnonceRep);

        envoyerBtn.setOnClickListener(genericOnClicListener);
        reinitBtn.setOnClickListener(genericOnClicListener);
        retourBtn.setOnClickListener(genericOnClicListener);
    }

    private final View.OnClickListener genericOnClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.returnAnnonceRep:
                    returnPrevious();
                    break;
                case R.id.btnReinitRep:
                    reset();
                    break;
                case R.id.btnEnvoyerRep:
                    sendRep();
                    break;
                default:
                    break;
            }
        }
    };

    private void returnPrevious(){
        Intent intent = new Intent(this, AnnonceDetailActivity.class);
        intent.putExtra("detail", annonce);
        startActivity(intent);
    }

    private void reset(){
        nomRep.setText("");
        emailRep.setText("");
        numRep.setText("");
        messageRep.setText("");
    }

    private void sendRep(){
        if(TextUtils.isEmpty(nomRep.getText())){
            nomRep.setError("Ce champ doit être renseigné");
        }else{
            if(TextUtils.isEmpty(emailRep.getText())){
                emailRep.setError("Ce champ doit être renseigné");
            }else{
                if(TextUtils.isEmpty(messageRep.getText())){
                    messageRep.setError("Ce champ doit être renseigné");
                }else{
                    Reponse rep = new Reponse();
                    rep.setNom(nomRep.getText().toString());
                    rep.setEmail(emailRep.getText().toString());
                    rep.setMessage(messageRep.getText().toString());
                    rep.setNumero(numRep.getText().toString());
                    rep.setIdAnnonce(annonce.getId());
                    rep.setNomVendeur(annonce.getNomVendeur());
                    new PostReponse(ReponseActivity.this,annonce).execute(rep);
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
