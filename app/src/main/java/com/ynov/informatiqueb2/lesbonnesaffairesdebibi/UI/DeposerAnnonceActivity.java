package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.PostDeposerAnnonce;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

import java.util.regex.Pattern;

//Cette Activity nous sert à déposer une annonce.
public class DeposerAnnonceActivity extends AppCompatActivity {
    Spinner categorie;
    EditText nom;
    EditText email;
    EditText password;
    EditText password2;
    EditText titre;
    EditText prix;
    EditText description;
    Button valider;
    Button annuler;
    //Déclaration pour pouvoir fermer notre activity depuis une autre.
    public static Activity actiDepAnn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposer_annonce);

        //Pour pouvoir fermer notre activity depuis une autre
        actiDepAnn = this;

        categorie = (Spinner) findViewById(R.id.spinnerDepAn);
        nom = (EditText) findViewById(R.id.nomDepAn);
        email = (EditText) findViewById(R.id.emailDepAn);
        password = (EditText) findViewById(R.id.pwdDepAn);
        password2 = (EditText) findViewById(R.id.pwd2DepAn);
        titre = (EditText) findViewById(R.id.titreDepAn);
        prix = (EditText) findViewById(R.id.prixDepAn);
        description = (EditText) findViewById(R.id.descDepAn);

        //On set les items de notre spinner
        String[] itemsCat = new String[]{"Categorie", "Vêtements", "Voitures"};
        //
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeposerAnnonceActivity.this, R.layout.support_simple_spinner_dropdown_item, itemsCat) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                   // On Disable le 1er item de notre spinner, il sert de hint.
                   return false;
               } else {
                   return true;
                }
            }

            @Override
           public View getDropDownView(int position, View convertView,
                                       ViewGroup parent) {
               View view = super.getDropDownView(position, convertView, parent);
               TextView tv = (TextView) view;
               if (position == 0) {
                  // Pour passer notre premier item en gris
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view; }
        };
        //On set notre spinner
        categorie.setAdapter(adapter);
        //Appel fonction bouton
        initbutton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_menu_un:
                Intent mainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(mainActivityIntent);
                return true;
            case R.id.action_menu_deux:
                Intent deposerAnnonceIntent = new Intent(getBaseContext(), DeposerAnnonceActivity.class);
                startActivity(deposerAnnonceIntent);
                return true;
            case R.id.action_menu_trois:
                Intent favorisIntent = new Intent(getBaseContext(), IdentificationActivity.class);
                startActivity(favorisIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Fonction boutton
    private void initbutton(){
        valider = (Button) findViewById(R.id.validerDepAn);
        annuler = (Button) findViewById(R.id.annulerDepAn);

        valider.setOnClickListener(myButtonSwitch);
        annuler.setOnClickListener(myButtonSwitch);

    }

    private final View.OnClickListener myButtonSwitch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //Clique sur le bouton Valider
                case R.id.validerDepAn:
                    //Champs mandatory
                    if (TextUtils.isEmpty(nom.getText().toString())){
                        nom.setError("Veuillez renseigner le nom du personnage");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(email.getText().toString())){
                        email.setError("Veuillez renseigner le nom du personnage");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(password.getText().toString())){
                        password.setError("Veuillez renseigner le nom du personnage");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(titre.getText().toString())){
                        titre.setError("Veuillez renseigner le nom du personnage");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(prix.getText().toString())){
                        prix.setError("Veuillez renseigner le nom du personnage");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(description.getText().toString())){
                        description.setError("Veuillez renseigner le nom du personnage");
                    }
                    //On check si les deux password sont pareil
                    else if(!password.getText().toString().equals(password2.getText().toString())){
                        Toast.makeText(DeposerAnnonceActivity.this, "Les password ne correspondent pas", Toast.LENGTH_SHORT).show();
                    }
                    //On check si l'email renseigné correspond aux standards
                    else if (Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$",email.getText().toString()) == false){
                        Toast.makeText(DeposerAnnonceActivity.this, "Veuillez renseigner un email valide", Toast.LENGTH_SHORT).show();
                    }
                    else
                        //Appel de notre methode si toutes les conditions sont respectées
                    //new PostDeposerAnnonce().execute(
                        // nom.getText().toString(),
                        // email.getText().toString(),
                        // password.getText().toString(),
                        // categorie.getSelectedItem().toString(),
                        // prix.getText().toString(),
                        // titre.getText().toString(),
                        // description.getText().toString());
                        Toast.makeText(DeposerAnnonceActivity.this, "Valider ", Toast.LENGTH_SHORT).show();
                    //On passe sur notre écran de confirmation
                    Intent intentConfirmationAn = new Intent(DeposerAnnonceActivity.this,ConfirmationDeposerAnnonce.class);
                    startActivity(intentConfirmationAn);

                    break;
                //Clique sur le bouton Annuler
                case R.id.annulerDepAn:
                    //On termine notre activity pour revenir à la précedente.
                    DeposerAnnonceActivity.this.finish();
                    break;
            }
        }
    };
}
