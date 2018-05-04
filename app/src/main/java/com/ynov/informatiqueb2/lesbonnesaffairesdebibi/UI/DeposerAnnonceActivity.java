package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Controller.ApiClass;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Model.ListAnnonceModel;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;


//Cette Activity nous sert à déposer une annonce.


public class DeposerAnnonceActivity extends com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI.Menu {
    Spinner categorie;
    TextView pathFile;
    EditText nom;
    EditText email;
    EditText password;
    EditText password2;
    EditText titre;
    EditText prix;
    EditText description;
    Button valider;
    Button annuler;
    Button selectFile;
    ImageView imageDepAn;
    ListAnnonceModel nouvelleAnnonce;
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
        pathFile = (TextView) findViewById(R.id.filePathDepAn) ;
        selectFile = (Button) findViewById(R.id.selectImageDepAn);
        imageDepAn = (ImageView) findViewById(R.id.imageDepAn);

        //On set les items de notre spinner
        String[] itemsCat = new String[]{"Categorie", "Vêtements", "Voitures"};
        //
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeposerAnnonceActivity.this, R.layout.support_simple_spinner_dropdown_item, itemsCat) {

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

        selectFile.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
    }
    //Set l'image selectionnée dans notre ImageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            pathFile.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                imageDepAn.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
                        email.setError("Veuillez renseigner botre email");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(password.getText().toString())){
                        password.setError("Veuillez renseigner votre mot de passe");
                    }
                    else if (TextUtils.isEmpty(password2.getText().toString())){
                        password2.setError("Veuillez confirmer votre mot de passe");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(titre.getText().toString())){
                        titre.setError("Veuillez renseigner le titre de votre annonce");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(prix.getText().toString())){
                        prix.setError("Veuillez renseigner le prix de votre article");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(description.getText().toString())){
                        description.setError("Veuillez renseigner une description pour votre annonce");
                    }
                    //Si item catégorie est select, on léve un toast pour l'utilisateur
                    else if (categorie.getSelectedItem().toString().equals("Categorie")){
                        Toast.makeText(DeposerAnnonceActivity.this, "Veuilliez renseigner une catégorie", Toast.LENGTH_SHORT).show();

                    }
                    //On check si les deux password sont identiques
                    else if(!password.getText().toString().equals(password2.getText().toString())){
                        Toast.makeText(DeposerAnnonceActivity.this, "Les password ne correspondent pas", Toast.LENGTH_SHORT).show();
                    }
                    //On check si l'email renseigné correspond aux standards
                    else if (Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$",email.getText().toString()) == false){
                        Toast.makeText(DeposerAnnonceActivity.this, "Veuillez renseigner un email valide", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        //Appel de notre methode si toutes les conditions sont respectées
                        //On crée un nouvel objet ListAnnonceModel
                        nouvelleAnnonce = new ListAnnonceModel();
                        // On se nos attributs avec les valeurs
                       nouvelleAnnonce.setCategorie(categorie.getSelectedItem().toString());
                       nouvelleAnnonce.setDescription(description.getText().toString());
                       nouvelleAnnonce.setPrix(prix.getText().toString());
                       nouvelleAnnonce.setTitle(titre.getText().toString());
                       nouvelleAnnonce.setVendeur(nom.getText().toString());
                       nouvelleAnnonce.setEmail(email.getText().toString());
                       nouvelleAnnonce.setLocalisation("Lyon");
                       nouvelleAnnonce.setMdp(password.getText().toString());


                        try {
                            //On call notre méthode POST retrofit2.0, on y passe les arguements
                            File file = new File(GetImageName(pathFile.getText().toString()));
                            RequestBody requestBody = RequestBody.create(MediaType.parse("src/main/resources/static/images/lesbonsplansdebibi/"), file);

                            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

                            ApiClass.getInstance().addAnnonce(nouvelleAnnonce, body).enqueue(new retrofit2.Callback<ListAnnonceModel>() {
                                @Override
                                //methode onReponse appelée une fois le message envoyé
                                public void onResponse(Call<ListAnnonceModel> call, retrofit2.Response<ListAnnonceModel> response) {
                                    Log.e("POSTANNONCE", "POST OuytK");
                                    if (response.isSuccessful()) {
//                                        Toast.makeText(DeposerAnnonceActivity.this, "Success post", Toast.LENGTH_SHORT).show();
                                        Log.e("POSTANNONCE", "POST OK");
                                    } else {
                                        Log.e("POSTANNONCE", "POST NOPE");
                                    }
                                }

                                @Override
                                //Si le post a fail, call de la methode onFailure
                                public void onFailure(Call<ListAnnonceModel> call, Throwable t) {

                                    Log.e("POSTANNONCE", t.toString());

                                }
                            });

                            Log.d("HeyCbon", "Hey c'est bon");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        //Toast.makeText(DeposerAnnonceActivity.this, "Valider ", Toast.LENGTH_SHORT).show();
                        //On passe sur notre écran de confirmation
                        Intent intentConfirmationAn = new Intent(DeposerAnnonceActivity.this, ConfirmationDeposerAnnonce.class);
                        startActivity(intentConfirmationAn);
                    }
                    break;

                //Clique sur le bouton Annuler
                case R.id.annulerDepAn:
                    //On termine notre activity pour revenir à la précedente.
                    DeposerAnnonceActivity.this.finish();
                    break;

            }
        }
//Methode pour paser les strings
    public String GetImageName(String fullPath){
        String imageName = null;

        //découpe du lien selon le caractère "/"
        String[] parts = fullPath.split("://");

        for(int i=0; i<parts.length; i++){
            //si la partie contient l'extension jpg ou jpeg ou png alors on récupère la string correspondante.
            if(i == parts.length-1)
            {
                imageName = parts[i];
            }
        }
        return imageName;
    }

    };
}
