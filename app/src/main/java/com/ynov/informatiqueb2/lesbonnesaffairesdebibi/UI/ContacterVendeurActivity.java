package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.DownloadImage;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.PostMessageVendeur;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

import java.util.regex.Pattern;



//Cette class va nous servir à contacter le vendeur de l'annonce.

public class ContacterVendeurActivity extends com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI.Menu {

    ImageView imageContact;
    TextView titreContact;
    TextView prixContact;
    TextView vendeurContact;
    TextView categorieContact;
    TextView dateContact;
    EditText nomContact;
    EditText emailContact;
    EditText telContact;
    EditText msgContact;
    Button valider;
    Button reset;
    Button retour;

    String idAnnonce;
    public String vendeur;
    public String image;
    public String titre;
    public String prix;
    public String categorie;
    public String date;
//Ceci va nous servir à fermer une activity depuis une autre.
    public static Activity actiContacter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacter_vendeur);

        //Déclaration pour fermer une acti depuis une autre.
        actiContacter = this;
        //On set nos champs
        titreContact = (TextView) findViewById(R.id.titreConfirm);
        prixContact = (TextView) findViewById(R.id.prixConfirm);
        vendeurContact = (TextView) findViewById(R.id.vendeurContact);
        categorieContact = (TextView) findViewById(R.id.catContact);
        dateContact = (TextView) findViewById(R.id.dateConfirm);
        nomContact = (EditText) findViewById(R.id.nomContact);
        emailContact = (EditText) findViewById(R.id.emailContact);
        telContact = (EditText) findViewById(R.id.telContact);
        msgContact = (EditText) findViewById(R.id.messageContact);
        imageContact = (ImageView) findViewById(R.id.imageContact);

        //On récup les info de provenant de l'annonce.
        Intent intentContact = getIntent();
        idAnnonce = intentContact.getStringExtra("id");
        vendeur = intentContact.getStringExtra("vendeur");
        image = intentContact.getStringExtra("image");
        titre = intentContact.getStringExtra("titre");
        prix = intentContact.getStringExtra("prix");
        categorie = intentContact.getStringExtra("categorie");
        date = intentContact.getStringExtra("date");

        //On set nos champs
        titreContact.setText(titre);
        prixContact.setText("Prix: " + prix + " €");
        vendeurContact.setText("Vendeur: "+vendeur);
        categorieContact.setText("Catégorie: " + categorie);
        new DownloadImage(imageContact)
                .execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" + image);
        dateContact.setText(date);
        //Appel de nos bouttons
        initbutton();
    }

    private void initbutton(){
        //Fonction pour nos boutons
        valider = (Button) findViewById(R.id.validerContact);
        reset = (Button) findViewById(R.id.resetContact);
        retour = (Button) findViewById(R.id.retourContact);

        valider.setOnClickListener(myButtonSwitch);
        reset.setOnClickListener(myButtonSwitch);
        retour.setOnClickListener(myButtonSwitch);

    }

    private final View.OnClickListener myButtonSwitch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //Clique sur le bouton valider
                case R.id.validerContact:
                    //Check des champs Mandatory
                    if (TextUtils.isEmpty(nomContact.getText().toString())){
                        nomContact.setError("Veuilliez renseigner votre nom");
                    }
                    //Check des champs Mandatory
                    else if(TextUtils.isEmpty(emailContact.getText().toString())){
                        emailContact.setError("Veuilliez renseigner votre email");
                    }
                    //Check des champs Mandatory
                    else if(TextUtils.isEmpty(msgContact.getText().toString())){
                        msgContact.setError("Veuilliez écrire votre message");
                    }
                    //Check si l'adresse email correspond aux standards
                    else if (Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$",emailContact.getText().toString()) == false){
                        Toast.makeText(ContacterVendeurActivity.this, "Veuilliez renseigner un email valide", Toast.LENGTH_SHORT).show();
                    }
                    //Si tout est ok, on appel notre méthode POST.
                    else {
                        new PostMessageVendeur(ContacterVendeurActivity.this).execute(idAnnonce.toString(),
                            nomContact.getText().toString(),
                            vendeur.toString(),
                            emailContact.getText().toString(),
                            telContact.getText().toString(),
                            msgContact.getText().toString(),
                            image.toString());
                    }

                    break;
                //Clique sur le bouton Reset, on reset tout nos champs.
                case R.id.resetContact:

                    nomContact.setText("");
                    emailContact.setText("");
                    telContact.setText("");
                    msgContact.setText("");

                    break;
                //Clique sur le bouton retour, on termine notre activité ContacterVendeur pour retourner à notre annonce.
                case R.id.retourContact:
                   ContacterVendeurActivity.this.finish();
                    break;
            }
        }
    };

}
