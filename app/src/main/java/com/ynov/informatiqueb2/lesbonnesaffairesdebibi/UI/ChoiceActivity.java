package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;


public class ChoiceActivity extends AppCompatActivity {

    Button identification;
    Button depAnnonce;
    Button modifAnnonce;
    Button favoris;
    Button acceuil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        initbutton();

    }


    private void initbutton(){
        //On recupère nos boutons
        identification = (Button) findViewById(R.id.identification);
        depAnnonce = (Button) findViewById(R.id.depAnnonce);
        modifAnnonce = (Button) findViewById(R.id.modifAnnonce);
        favoris = (Button) findViewById(R.id.favoris);
        acceuil = (Button) findViewById(R.id.acceuil);
        //On leur indique le switch
        identification.setOnClickListener(myButtonSwitch);
        depAnnonce.setOnClickListener(myButtonSwitch);
        modifAnnonce.setOnClickListener(myButtonSwitch);
        favoris.setOnClickListener(myButtonSwitch);
        acceuil.setOnClickListener(myButtonSwitch);

    }

    private final View.OnClickListener myButtonSwitch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //Clique sur le bouton visualiser bouton
                case R.id.identification:
                    //on crée notre intent et on le start
                    Intent idIntent = new Intent(ChoiceActivity.this, IdentificationActivity.class);
                    startActivity(idIntent);
                    break;
                //Clique sur le bouton Ajouter un personnage
                case R.id.depAnnonce:
                    //on crée notre intent et on le start
//                    Intent AddIntent = new Intent(ChoiceActivity.this, AddPersoActivity.class);
//                    startActivity(AddIntent);
                    break;

                case R.id.modifAnnonce:
                    //on crée notre intent et on le start
//                    Intent AddIntent = new Intent(ChoiceActivity.this, AddPersoActivity.class);
//                    startActivity(AddIntent);
                    break;

                case R.id.favoris:
                    //on crée notre intent et on le start
//                    Intent AddIntent = new Intent(ChoiceActivity.this, AddPersoActivity.class);
//                    startActivity(AddIntent);
                    break;

                case R.id.acceuil:
                    //on crée notre intent et on le start
                    Intent accIntent = new Intent(ChoiceActivity.this, MainActivity.class);
                    startActivity(accIntent);
                    break;
            }
        }
    };
}
