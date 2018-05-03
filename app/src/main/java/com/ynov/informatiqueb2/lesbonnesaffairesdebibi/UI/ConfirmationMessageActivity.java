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





public class ConfirmationMessageActivity extends com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI.Menu {

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

        Intent intentConfirm = getIntent();
        image = intentConfirm.getStringExtra("image");
        vendeur = intentConfirm.getStringExtra("nomVendeur");
        titre = intentConfirm.getStringExtra("titre");
        categorie = intentConfirm.getStringExtra("categorie");
        date = intentConfirm.getStringExtra("date");
        prix = intentConfirm.getStringExtra("prix");


         imageConfirm = (ImageView) findViewById(R.id.imageContact);
         titreConfirm = (TextView) findViewById(R.id.titreConfirm);
         prixConfirm = (TextView) findViewById(R.id.prixConfirm);
         vendeurConfirm = (TextView) findViewById(R.id.vendeurConfirm);
         catConfirm = (TextView) findViewById(R.id.catConfirm);
         dateConfirm = (TextView) findViewById(R.id.dateConfirm);
         messageConfirm = (TextView) findViewById(R.id.messageDeConfirm);

         new DownloadImage((ImageView) imageConfirm).execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" + image);
         titreConfirm.setText(titre);
         prixConfirm.setText(prix);
         vendeurConfirm.setText(vendeur);
         catConfirm.setText(categorie);
         dateConfirm.setText(date);

         messageConfirm.setText("Votre message a bien été envoyé\n"+ vendeur + " reviendra vers vous rapidement\n\n" +
                 "Bonne journée.\n\n" +
                 "L'équipe de lesbonnesaffairesdebibi.fr");



    }

    private void  initbutton(){
        retourAcceuil = (Button) findViewById(R.id.retourConfirm);
        retourAnnonce = (Button) findViewById(R.id.retourAnnonceConfirm);

        retourAcceuil.setOnClickListener(myButtonSwitch);
        retourAnnonce.setOnClickListener(myButtonSwitch);
    }
    private final View.OnClickListener myButtonSwitch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //Clique sur le bouton visualiser bouton
                case R.id.retourConfirm:
                    //on crée notre intent et on le start
                    Intent idIntent = new Intent(ConfirmationMessageActivity.this, MainActivity.class);
                    startActivity(idIntent);
                    break;
                //Clique sur le bouton Ajouter un personnage
                case R.id.retourAnnonceConfirm:
                    Intent intentDepAn = new Intent(ConfirmationMessageActivity.this,DetailAnnonceActivity.class);
                    startActivity(intentDepAn);
                    break;
            }
        }
    };
}
