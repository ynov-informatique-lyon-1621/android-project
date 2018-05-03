package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.DownloadImage;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;





public class ConfirmationMessageActivity extends AppCompatActivity {
// Cette activité sert à confirmer à l'utilisateur l'envoi de son message.
    ImageView imageConfirm;
    TextView titreConfirm;
    TextView prixConfirm;
    TextView vendeurConfirm;
    TextView catConfirm;
    TextView dateConfirm;
    TextView messageConfirm;
    Button retourAcceuil;
    Button retourAnnonce;

    String image;
    String titre;
    String vendeur;
    String categorie;
    String date;
    String prix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_message);
        //On récupère notre intent provenant du formulaire de message pour pouvoir implémenter l'image, la date etc...
        Intent intentConfirm = getIntent();
        image = intentConfirm.getStringExtra("image");
        vendeur = intentConfirm.getStringExtra("nomVendeur");
        titre = intentConfirm.getStringExtra("titre");
        categorie = intentConfirm.getStringExtra("categorie");
        date = intentConfirm.getStringExtra("date");
        prix = intentConfirm.getStringExtra("prix");

        //On récupère les objets de notre layout
         imageConfirm = (ImageView) findViewById(R.id.imageContact);
         titreConfirm = (TextView) findViewById(R.id.titreConfirm);
         prixConfirm = (TextView) findViewById(R.id.prixConfirm);
         vendeurConfirm = (TextView) findViewById(R.id.vendeurConfirm);
         catConfirm = (TextView) findViewById(R.id.catConfirm);
         dateConfirm = (TextView) findViewById(R.id.dateConfirm);
         messageConfirm = (TextView) findViewById(R.id.messageDeConfirm);

         //On set nos layout avec notre methode pour l'image et les info recupérée de l'intent
         new DownloadImage((ImageView) imageConfirm).execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" + image);
         titreConfirm.setText(titre);
         prixConfirm.setText("Prix: " +prix + " €");
         vendeurConfirm.setText("Vendeur: " + vendeur);
         catConfirm.setText("Caétegorie: " + categorie);
         dateConfirm.setText("date: "+ date);

         messageConfirm.setText("Votre message a bien été envoyé\n"+ vendeur + " reviendra vers vous rapidement\n\n" +
                 "Bonne journée.\n\n" +
                 "L'équipe de lesbonnesaffairesdebibi.fr");

        //Appel methode bouttons
        initbutton();
    }

    private void  initbutton(){
        //methode pour nos boutons
        retourAcceuil = (Button) findViewById(R.id.retourConfirm);
        retourAnnonce = (Button) findViewById(R.id.retourAnnonceConfirm);

        retourAcceuil.setOnClickListener(myButtonSwitch);
        retourAnnonce.setOnClickListener(myButtonSwitch);
    }
    private final View.OnClickListener myButtonSwitch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //Clique sur le bouton Retour Acceuil
                case R.id.retourConfirm:
                    //On ferme les activités ConfirmationMessage, ContacterVendeur ainsi que Detail annonce pour retourner au MainActivity (acceul)
                    ConfirmationMessageActivity.this.finish();
                    ContacterVendeurActivity.actiContacter.finish();
                    DetailAnnonceActivity.actiDetail.finish();
                    break;
                //Clique sur le bouton Retour annonce
                case R.id.retourAnnonceConfirm:
                    //On ferme les activités ConfirmationMessage et ContacterVendeur pour retrouver le detail de l'annonce
                    ConfirmationMessageActivity.this.finish();
                    ContacterVendeurActivity.actiContacter.finish();
                    break;
            }
        }
    };
}
