package com.example.travail.esparel.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.travail.esparel.R;

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
        //On donne le switch
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
                    //créer intent et on le start
                    Intent idIntent = new Intent(ChoiceActivity.this, IdentifiationActivity.class);
                    startActivity(idIntent);
                    break;
                //les boutons
                case R.id.depAnnonce:
                    Intent intentDepAn = new Intent(ChoiceActivity.this,DeposerActivity.class);
                    startActivity(intentDepAn);
                    break;

                case R.id.modifAnnonce:

                    break;

                case R.id.favoris:

                    break;

                case R.id.acceuil:

                    Intent accIntent = new Intent(ChoiceActivity.this, MainActivity.class);
                    startActivity(accIntent);
                    break;
            }
        }
    };
}