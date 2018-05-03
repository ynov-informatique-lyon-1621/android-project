package com.ynov.lesbonnesaffairesdebibi.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.squareup.picasso.Picasso;
import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;
import com.ynov.lesbonnesaffairesdebibi.service.HttpService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

public class AddActivity extends BaseActivity {

    private ImageView imageView;
    private TextView textView;
    private ImagePicker imagePicker;
    private Button addButton;
    private Uri imagePath = null;

    private HttpService httpService;
    Call<Void> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_add, contentLayout);

        imageView = (ImageView) findViewById(R.id.addImage);
        textView = (TextView) findViewById(R.id.addImageText);

        EditText addName = findViewById(R.id.addName);
        EditText addEmail = findViewById(R.id.addEmail);
        EditText addPassword = findViewById(R.id.addPassword);
        EditText addPasswordConf = findViewById(R.id.addPasswordConf);
        EditText addTitle = findViewById(R.id.addTitle);
        EditText addLocation = findViewById(R.id.addLocation);
        EditText addCategory = findViewById(R.id.addCategory);
        EditText addPrice = findViewById(R.id.addPrice);
        EditText addDescription = findViewById(R.id.addDescription);
        addButton = findViewById(R.id.addButton);

        Boolean editMode = getIntent().getBooleanExtra("editMode", false);

        if(editMode) {

            setTitle("Modifier une annonce");
            addButton.setText("Enregistrer les modifications");

            textView.setVisibility(GONE);
            addName.setVisibility(GONE);
            addEmail.setVisibility(GONE);
            addPassword.setVisibility(GONE);
            addPasswordConf.setVisibility(GONE);

            Annonce annonce = (Annonce) getIntent().getSerializableExtra("data");
            Picasso.get().load(annonce.getImage()).into(imageView);

            addTitle.setText(annonce.getTitre());
            addLocation.setText(annonce.getLocalisation());
            addCategory.setText(annonce.getCategorie());
            addPrice.setText(formatFix(annonce.getPrix()));
            addDescription.setText(annonce.getDescription());

        } else {

            setTitle("Déposer une annonce");

            imagePicker = new ImagePicker(AddActivity.this,null, imageUri -> {
                textView.setVisibility(GONE);
                imageView.setImageURI(imageUri);
                imagePath = imageUri;
            }).setWithImageCrop(1, 1);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imagePicker.choosePicture(true);
                }
            });

        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = addName.getText().toString();
                String email = addEmail.getText().toString();
                String password = addPassword.getText().toString();
                String passwordConf = addPasswordConf.getText().toString();
                String title = addTitle.getText().toString();
                String location = addLocation.getText().toString();
                String category = addCategory.getText().toString();
                String price = addPrice.getText().toString();
                String description = addDescription.getText().toString();

                if(!editMode && imagePath == null)
                    Toast.makeText(AddActivity.this, "Vous devez sélectionner une photo", Toast.LENGTH_SHORT).show();
                else if(!editMode && TextUtils.isEmpty(name))
                    addName.setError("Vous devez renseigner un nom");
                else if(!editMode && TextUtils.isEmpty(email))
                    addEmail.setError("Vous devez renseigner une adresse mail");
                else if(!editMode && TextUtils.isEmpty(password))
                    addPassword.setError("Vous devez renseigner un mot de passe");
                else if(!editMode && TextUtils.isEmpty(passwordConf))
                    addPasswordConf.setError("Vous devez confirmer votre mot de passe");
                else if(!editMode && !password.equals(passwordConf))
                    addPasswordConf.setError("Les mots de passe doivent correspondre");
                else if(TextUtils.isEmpty(title))
                    addTitle.setError("Vous devez renseigner un titre");
                else if(TextUtils.isEmpty(location))
                    addLocation.setError("Vous devez renseigner une ville ou CP");
                else if(TextUtils.isEmpty(category))
                    addCategory.setError("Vous devez renseigner une catégorie");
                else if(TextUtils.isEmpty(price))
                    addPrice.setError("Vous devez renseigner un prix");
                else if(TextUtils.isEmpty(description))
                    addDescription.setError("Vous devez renseigner une description");
                else
                {
                    addButton.setEnabled(false);

                    if(editMode) {
                        Annonce annonceObject = new Annonce();
                        Annonce annonce = (Annonce) getIntent().getSerializableExtra("data");

                        annonceObject.setTitre(title);
                        annonceObject.setLocalisation(location);
                        annonceObject.setCategorie(category);
                        annonceObject.setPrix(Float.parseFloat(price));
                        annonceObject.setDescription(description);
                        annonceObject.setDateCreation(Long.valueOf(System.currentTimeMillis()).toString());

                        annonceObject.setNomVendeur(annonce.getNomVendeur());
                        annonceObject.setEmail(annonce.getEmail());
                        annonceObject.setMdp(annonce.getMdp());
                        annonceObject.setId(annonce.getId());
                        annonceObject.setImage(annonce.getImage().replace("http://139.99.98.119:8080/", "src/main/resources/static/"));

                        updateAnnonce(annonceObject);
                    } else {
                        JSONObject jsonObject = new JSONObject();

                        try {
                            jsonObject.put("titre", title);
                            jsonObject.put("localisation", location);
                            jsonObject.put("categorie", category);
                            jsonObject.put("prix", Float.parseFloat(price));
                            jsonObject.put("description", description);
                            jsonObject.put("dateCreation", Long.valueOf(System.currentTimeMillis()).toString());
                            jsonObject.put("nomVendeur", name);
                            jsonObject.put("email", email);
                            jsonObject.put("mdp", password);
                        } catch (JSONException e) {
                            Toast.makeText(AddActivity.this, "Erreur lors de la création de l'annonce. Veuillez rééssayer ultérieurement.", Toast.LENGTH_SHORT).show();
                        }

                        saveAnnonce(jsonObject, imagePath);
                    }
                }
            }
        });

        httpService = new Retrofit.Builder().baseUrl(HttpService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(HttpService.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

    public String formatFix(String input) {
        return String.valueOf(input)
                .replaceAll("\\s", "")
                .replaceAll(",", ".")
                .replace("€", "");
    }

    public void saveAnnonce(final JSONObject annonce, final Uri imageUri) {

        File file = new File(imageUri.getPath());

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part bodyFile = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody bodyText = RequestBody.create(MediaType.parse("text/plain"), annonce.toString());

        call = httpService.saveAnnonce(bodyText, bodyFile);

        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                addButton.setEnabled(true);

                if(response.code() == 201) {
                    new AlertDialog.Builder(AddActivity.this)
                        .setIcon(R.drawable.ic_check_black_24dp)
                        .setTitle("Annonce enregistrée")
                        .setMessage("Votre annonce a bien été enregistrée.")
                        .setPositiveButton("Retour à la liste", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switchToActivity(ListActivity.class, false);
                            }
                        })
                        .show();
                } else if(response.code() == 400) {
                    Toast.makeText(AddActivity.this, "Votre annonce n'a pas pu être envoyée. Veuillez rééssayer ultérieurement.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                addButton.setEnabled(true);
                Toast.makeText(AddActivity.this, "Une erreur est survenue lors de l'envoi. Vérifiez votre connexion internet puis rééssayez.", Toast.LENGTH_SHORT).show();
                Log.d("WebRequest", "Error : " + t.getMessage());
            }

        });
    }

    public void updateAnnonce(final Annonce annonce) {

        call = httpService.updateAnnonce(annonce);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                addButton.setEnabled(true);

                if(response.code() == 200) {
                    new AlertDialog.Builder(AddActivity.this)
                        .setIcon(R.drawable.ic_check_black_24dp)
                        .setTitle("Annonce enregistrée")
                        .setMessage("Votre annonce a bien été enregistrée. Les modifications prennent effet immédiatement.")
                        .setPositiveButton("Retour à mes annonces", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switchToActivity(MyListActivity.class, false);
                            }
                        })
                        .show();
                } else if(response.code() == 400) {
                    Toast.makeText(AddActivity.this, "Une erreur est survenue lors de l'enregistrement de votre annonce. Veuillez rééssayer ultérieurement.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                addButton.setEnabled(true);
                Toast.makeText(AddActivity.this, "Une erreur est survenue lors de l'envoi. Vérifiez votre connexion internet puis rééssayez.", Toast.LENGTH_SHORT).show();
                Log.d("WebRequest", "Error : " + t.getMessage());
            }
        });

    }
}
