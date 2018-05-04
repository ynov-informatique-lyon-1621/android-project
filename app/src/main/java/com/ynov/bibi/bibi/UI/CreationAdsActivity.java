package com.ynov.bibi.bibi.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ynov.bibi.bibi.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreationAdsActivity extends AppCompatActivity {

    private Button selectImage;
    private EditText editNom;
    private EditText editEmail;
    private EditText editPass;
    private EditText editConfPass;
    private EditText editPrix;
    private EditText editDescription;
    private Button valider;
    private Button annuler;
    private Spinner selectCategory;
    private ImageView imageCrea;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_ads);

        setTitle("LesBonnesAffairesDeBibi.fr");

        selectImage = findViewById(R.id.btnSelectImage);
        editNom = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editMail);
        editPass = findViewById(R.id.editMDP);
        editConfPass = findViewById(R.id.editConfirmationMDP);
        editPrix = findViewById(R.id.editPrice);
        editDescription = findViewById(R.id.editDescription);
        valider = findViewById(R.id.btnValider);
        annuler = findViewById(R.id.btnAnnuler);
        selectCategory = findViewById(R.id.spinnerCategory);

        //setup du spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        // On spécifie le layout à utiliser lorsque les choix apparaissent
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategory.setAdapter(adapter);


        //Gestion de la confirmation du mdp de mainière instantanée après la saisie
        editConfPass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String mdp = editPass.getText().toString();
                String confMdp = editConfPass.getText().toString();

                if (!mdp.equals(confMdp)) {
                    editConfPass.setError("La saisie ne correspond pas");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Gestion du boutton valider
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinner = selectCategory.getSelectedItem().toString();

                String nom = editNom.getText().toString();
                String email = editEmail.getText().toString();
                String mdp = editPass.getText().toString();
                String conf = editConfPass.getText().toString();
                String prix = editPrix.getText().toString();
                String description = editDescription.getText().toString();


                //Condition qui vérifie que les champs ont bien étés remplis
                if (TextUtils.isEmpty(editNom.getText().toString())) {
                    editNom.setError("Le champ Nom est obligatoire");
                }
                if (TextUtils.isEmpty(editEmail.getText().toString())) {
                    editEmail.setError("Le champ Email est obligatoire");
                }
                if (!isValidEmailAddress(editEmail.getText().toString())) {
                    editEmail.setError("Le format de l'email n'est pas bon");
                }
                if (TextUtils.isEmpty(editPass.getText().toString())) {
                    editPass.setError("Le mot de passe est obligatoire");
                }
                if (TextUtils.isEmpty(editPrix.getText().toString())) {
                    editPrix.setError("Le champ Prix est obligatoire");
                }
                if (TextUtils.isEmpty(editDescription.getText().toString())) {
                    editDescription.setError("Le champ Description est obligatoire");
                }
                if (spinner.equals("Catégories*")) {
                    setSpinnerError(selectCategory, "Veillez sélectionner une catégorie");
                }
                //On stock les infos dans un tableau lorsque tout les champs sont remplits
                if (!TextUtils.isEmpty(editNom.getText().toString()) && !TextUtils.isEmpty(editEmail.getText().toString()) && !TextUtils.isEmpty(editPass.getText().toString()) && (mdp.equals(conf)) && !TextUtils.isEmpty(editPrix.getText().toString()) && !TextUtils.isEmpty(editDescription.getText().toString()) && (!spinner.equals("Catégories*")) && isValidEmailAddress(editEmail.getText().toString())) {
                    data.add(nom);
                    data.add(email);
                    data.add(mdp);
                    data.add(prix);
                    data.add(description);

                    Intent confirmation = new Intent(CreationAdsActivity.this, ConfirmationCreaActivity.class);
                    startActivity(confirmation);//rediriger vers confirmation de creation
                }
            }
        });

        //gestion du boutton annuler
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//on kill l'activité
            }
        });

        //gestion du bouton selection d'image
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //demande de permission puis redirection vers la librairie si permission accordée
                if (checkPermission()) {
                    Log.e("permission", "Permission already granted.");
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 0);
                } else {
                    requestPermission();
                }
            }
        });
        //recup reponse de la perm
        //si rep positiv on recup le fichier

    }

    //gestion de la permission
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(CreationAdsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(CreationAdsActivity.this,
                            "Permission accepted", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(CreationAdsActivity.this,
                            "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataRes) {
        // methode de recupération du fichier et de son chemin pour l'exploiter lors d'envoie au serveur
        super.onActivityResult(requestCode, resultCode, dataRes);

        imageCrea = findViewById(R.id.imageCreaView);
        String pathImage;

        if (resultCode == RESULT_OK) {
            Uri targetUri = dataRes.getData();
            pathImage = targetUri.toString();
            data.add(pathImage);
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                imageCrea.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;

        Log.d("verfiication", "verif mail");

        // Regex pour une adresse email valide
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // on compare le regex avec l'email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    //gestion des erreurs liés au spinner
    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); //setup de l'erreur
            selectedTextView.setTextColor(Color.RED);
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // on ouvre le spinner si une erreur est trouvée

        }
    }
}
