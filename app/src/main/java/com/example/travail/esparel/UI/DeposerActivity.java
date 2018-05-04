package com.example.travail.esparel.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travail.esparel.Controller.AnnonceController;
import com.example.travail.esparel.R;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;

public class DeposerActivity extends AppCompatActivity {
    ImageView image;
    Button bouton;
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
    TextView textTargetUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposer);
        textTargetUri = (TextView)findViewById(R.id.targeturi);

        image = (ImageView) findViewById(R.id.imageView);
        bouton = (Button) findViewById(R.id.button);
        categorie = (Spinner) findViewById(R.id.spinnerDepAn);
        nom = (EditText) findViewById(R.id.nomDepAn);
        email = (EditText) findViewById(R.id.emailDepAn);
        password = (EditText) findViewById(R.id.pwdDepAn);
        password2 = (EditText) findViewById(R.id.pwd2DepAn);
        titre = (EditText) findViewById(R.id.titreDepAn);
        prix = (EditText) findViewById(R.id.prixDepAn);
        description = (EditText) findViewById(R.id.descDepAn);
        String[] itemsCat = new String[]{"Categorie", "Vêtement", "Voiture"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeposerActivity.this, R.layout.support_simple_spinner_dropdown_item, itemsCat) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {

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
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view; }
        };
        categorie.setAdapter(adapter);

        initbutton();

        bouton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                try {
                    if (ActivityCompat.checkSelfPermission(DeposerActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(DeposerActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void initbutton(){
        valider = (Button) findViewById(R.id.validerDepAn);
        annuler = (Button) findViewById(R.id.annulerDepAn);

        valider.setOnClickListener(myButtonSwitch);
        annuler.setOnClickListener(myButtonSwitch);

    }
    private static final int PICK_FROM_GALLERY = 1;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    private final View.OnClickListener myButtonSwitch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //Clique sur le bouton visualiser bouton
                case R.id.validerDepAn:
                    //Champs mandatory
                    if (TextUtils.isEmpty(nom.getText().toString())){
                        nom.setError("Veuilliez renseigner le nom ");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(email.getText().toString())){
                        email.setError("Veuilliez renseigner l'email");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(password.getText().toString())){
                        password.setError("Veuilliez renseigner le mot de passe");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(titre.getText().toString())){
                        titre.setError("Veuilliez renseigner le nom du personnage");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(prix.getText().toString())){
                        prix.setError("Veuilliez renseigner le nom du personnage");
                    }
                    //Champs mandatory
                    else if (TextUtils.isEmpty(description.getText().toString())){
                        description.setError("Veuilliez renseigner le nom du personnage");
                    }
                    else if(!password.getText().toString().equals(password2.getText().toString())){
                        Toast.makeText(DeposerActivity.this, "Les password ne correspondent pas", Toast.LENGTH_SHORT).show();
                    }
                    else if (Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$",email.getText().toString()) == false){
                        Toast.makeText(DeposerActivity.this, "Veuilliez renseigner un email valide", Toast.LENGTH_SHORT).show();
                    }


                    new AnnonceController(DeposerActivity.this).execute(getResources().getString(R.string.api_url) + "saveAnnonce", nom.getText().toString(),
                            email.getText().toString(),
                            password.getText().toString(),
                            categorie.getSelectedItem().toString(),
                            prix.getText().toString(),
                            titre.getText().toString(),
                            description.getText().toString());

                    Toast.makeText(DeposerActivity.this, "Valider ", Toast.LENGTH_SHORT).show();
                    Intent intentConfirmationAn = new Intent(DeposerActivity.this,ConfimationAcitivity.class);
                     startActivity(intentConfirmationAn);

                    break;
                //Clique sur le bouton Ajouter un personnage
                case R.id.annulerDepAn:
                    //on crée notre intent et on le start
                    Intent AddIntent = new Intent(DeposerActivity.this, MainActivity.class);
                    startActivity(AddIntent);
                    break;
            }
        }
    };
}
