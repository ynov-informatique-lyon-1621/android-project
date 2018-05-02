package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

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


public class ContacterVendeurActivity extends AppCompatActivity {

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacter_vendeur);

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

        Intent intentContact = getIntent();
        idAnnonce = intentContact.getStringExtra("id");
        vendeur = intentContact.getStringExtra("vendeur");
        image = intentContact.getStringExtra("image");
        titre = intentContact.getStringExtra("titre");
        prix = intentContact.getStringExtra("prix");
        categorie = intentContact.getStringExtra("categorie");
        //String date = intentContact.getStringExtra("date");


        titreContact.setText(titre);
        prixContact.setText(prix);
        vendeurContact.setText(vendeur);
        categorieContact.setText(categorie);
        new DownloadImage(imageContact )
                .execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" + image);
//        dateContact.setText(date);

        initbutton();


    }

    private void initbutton(){
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
                //Clique sur le bouton visualiser bouton
                case R.id.validerContact:
                    if (TextUtils.isEmpty(nomContact.getText().toString())){
                        nomContact.setError("Veuilliez renseigner votre nom");
                    }
                    else if(TextUtils.isEmpty(emailContact.getText().toString())){
                        nomContact.setError("Veuilliez renseigner votre email");
                    }
                    else if(TextUtils.isEmpty(msgContact.getText().toString())){
                        nomContact.setError("Veuilliez écrire votre message");
                    }
                    else if (Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$",emailContact.getText().toString()) == false){
                        Toast.makeText(ContacterVendeurActivity.this, "Veuilliez renseigner un email valide", Toast.LENGTH_SHORT).show();
                    }
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
                //Clique sur le bouton Ajouter un personnage
                case R.id.resetContact:

                    nomContact.setText("");
                    emailContact.setText("");
                    telContact.setText("");
                    msgContact.setText("");

                    break;

                case R.id.retourContact:
                   ContacterVendeurActivity.this.finish();
                    break;
            }
        }
    };

}
