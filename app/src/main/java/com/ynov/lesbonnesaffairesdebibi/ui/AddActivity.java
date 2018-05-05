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

        // Appel du layout parent pour inclure la barre d'action globale et le menu (extension de l'activité de base - BaseActivity)
        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_add, contentLayout);

        // On déclare les éléments du layout
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

        // On récupère la valeur contenant le mode (ajout / édition) du formulaire passée en extra dans l'intent
        Boolean editMode = getIntent().getBooleanExtra("editMode", false);

        if(editMode) {
            // Si le mode est en édition d'une annonce

            // On modifie le texte du bouton
            addButton.setText("Enregistrer les modifications");

            // On cache les champs non nécessaires pour l'édition
            textView.setVisibility(GONE);
            addName.setVisibility(GONE);
            addEmail.setVisibility(GONE);
            addPassword.setVisibility(GONE);
            addPasswordConf.setVisibility(GONE);

            // On récupère l'objet sérialisé passé dans l'intent et on le convertit on objet Annonce
            Annonce annonce = (Annonce) getIntent().getSerializableExtra("data");

            // Avec la librairie Picasso, on charge l'image de l'annonce dans l'imageView
            Picasso.get().load(annonce.getImage()).into(imageView);

            // On change les valeurs des champs pour afficher les informations de l'annonce
            addTitle.setText(annonce.getTitre());
            addLocation.setText(annonce.getLocalisation());
            addCategory.setText(annonce.getCategorie());
            addPrice.setText(formatFix(annonce.getPrix()));
            addDescription.setText(annonce.getDescription());

        } else {
            // Si le mode est en ajout d'une annonce

            // On initialise la librairie de sélection d'image (avec redimensionnement au ratio 1:1)
            imagePicker = new ImagePicker(AddActivity.this,null, imageUri -> {
                // Après sélection, on cache le texte "Sélectionner une photo"
                textView.setVisibility(GONE);
                // On affiche l'image dans l'imageView du formulaire
                imageView.setImageURI(imageUri);
                // On stocke le chemin de l'image dans une variable globale
                imagePath = imageUri;
            }).setWithImageCrop(1, 1);

            // Ajout d'un écouteur de clic sur l'imageView (sélection/changement de photo)
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Au clic, on ouvre le menu de sélection d'image (avec possibilité de prendre une photo directement)
                    imagePicker.choosePicture(true);
                }
            });

        }

        // Ajout d'un écouteur de clic sur le bouton "Déposer mon annonce" / "Enregistrer les modifications"
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Récupération des valeurs des champs du formulaire
                String name = addName.getText().toString();
                String email = addEmail.getText().toString();
                String password = addPassword.getText().toString();
                String passwordConf = addPasswordConf.getText().toString();
                String title = addTitle.getText().toString();
                String location = addLocation.getText().toString();
                String category = addCategory.getText().toString();
                String price = addPrice.getText().toString();
                String description = addDescription.getText().toString();

                // On vérifie que les champs ne sont pas vides
                if(!editMode && imagePath == null)
                    Toast.makeText(AddActivity.this, "Vous devez sélectionner une photo", Toast.LENGTH_SHORT).show();
                else if(!editMode && TextUtils.isEmpty(name))
                    addName.setError("Vous devez renseigner un nom");
                else if(!editMode && (TextUtils.isEmpty(email) || !isValidEmail(email))) // On vérifie que l'adresse mail est valide
                    addEmail.setError("Vous devez renseigner une adresse mail valide");
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
                    // On désactive le clic sur le bouton
                    addButton.setEnabled(false);

                    if(editMode) {
                        // Si le mode est édition, on instancie un nouvel objet Annonce avec les valeurs des champs du formulaire
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

                        // On passe l'objet en paramètre de la méthode de mise à jour de l'annonce
                        updateAnnonce(annonceObject);
                    } else {
                        // Si le mode est création, on crée un nouvel objet JSON avec les valeurs des champs du formulaire
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
                            // Si il y a une erreur lors de la création de l'objet JSON, on affiche un message d'erreur
                            Toast.makeText(AddActivity.this, "Erreur lors de la création de l'annonce. Veuillez rééssayer ultérieurement.", Toast.LENGTH_SHORT).show();
                        }

                        // On passe l'objet JSON et le chemin de l'image en paramètres de la méthode de création de l'annonce
                        saveAnnonce(jsonObject, imagePath);
                    }
                }
            }
        });

        // On initialise la librairie Retrofit2 permettant de gérer les appels au webservice
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
        // Converti le format "human readable" du prix en float parsable
        return String.valueOf(input)
                .replaceAll("\\s", "")
                .replaceAll(",", ".")
                .replace("€", "");
    }

    public void saveAnnonce(final JSONObject annonce, final Uri imageUri) {

        // On instancie un nouvel objet File en lui passant le chemin de l'image
        File file = new File(imageUri.getPath());

        // On crée les différentes parties du multipart ("file" contenant l'image et "annonce" contenant le JSON de l'annonce)
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part bodyFile = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody bodyText = RequestBody.create(MediaType.parse("text/plain"), annonce.toString());

        // On prépare l'appel à l'endpoint correspondant (saveAnnonce) en passant les parties du multipart en paramètres
        call = httpService.saveAnnonce(bodyText, bodyFile);

        // On met l'appel en file d'attente (appel asynchrone), la réponse sera renvoyée dans la callback
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Si on obtient une réponse, on réactive le clic sur le bouton
                addButton.setEnabled(true);

                // On vérifie le code http de la réponse
                if(response.code() == 201) {
                    // Si l'ajout s'est bien déroulé, on affiche un dialog de confirmation
                    new AlertDialog.Builder(AddActivity.this)
                        .setIcon(R.drawable.ic_check_black_24dp)
                        .setTitle("Annonce enregistrée")
                        .setMessage("Votre annonce a bien été enregistrée.")
                        .setPositiveButton("Retour à la liste", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Au clic sur le bouton du dialog, on passe à l'activité List (Liste des annonces)
                                switchToActivity(ListActivity.class, false);
                            }
                        })
                        .show();
                } else if(response.code() == 400) {
                    // Si il y a eu une erreur avec l'ajout de l'annonce, on affiche un message d'erreur
                    Toast.makeText(AddActivity.this, "Votre annonce n'a pas pu être envoyée. Veuillez rééssayer ultérieurement.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si on obtient une erreur, on réactive le clic sur le bouton et on affiche un message d'erreur
                addButton.setEnabled(true);
                Toast.makeText(AddActivity.this, "Une erreur est survenue lors de l'envoi. Vérifiez votre connexion internet puis rééssayez.", Toast.LENGTH_SHORT).show();
                Log.d("WebRequest", "Error : " + t.getMessage());
            }

        });
    }

    public void updateAnnonce(final Annonce annonce) {

        // On prépare l'appel à l'endpoint correspondant (updateAnnonce) en passant l'objet Annonce en paramètre
        call = httpService.updateAnnonce(annonce);

        // On met l'appel en file d'attente (appel asynchrone), la réponse sera renvoyée dans la callback
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Si on obtient une réponse, on réactive le clic sur le bouton
                addButton.setEnabled(true);

                // On vérifie le code http de la réponse
                if(response.code() == 200) {
                    // Si la mise à jour s'est bien déroulé, on affiche un dialog de confirmation
                    new AlertDialog.Builder(AddActivity.this)
                        .setIcon(R.drawable.ic_check_black_24dp)
                        .setTitle("Annonce enregistrée")
                        .setMessage("Votre annonce a bien été enregistrée. Les modifications prennent effet immédiatement.")
                        .setPositiveButton("Retour à mes annonces", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Au clic sur le bouton du dialog, on passe à l'activité MyList (Ma liste d'annonces)
                                switchToActivity(MyListActivity.class, false);
                            }
                        })
                        .show();
                } else if(response.code() == 400) {
                    // Si il y a eu une erreur avec la mise à jour de l'annonce, on affiche un message d'erreur
                    Toast.makeText(AddActivity.this, "Une erreur est survenue lors de l'enregistrement de votre annonce. Veuillez rééssayer ultérieurement.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si on obtient une erreur, on réactive le clic sur le bouton et on affiche un message d'erreur
                addButton.setEnabled(true);
                Toast.makeText(AddActivity.this, "Une erreur est survenue lors de l'envoi. Vérifiez votre connexion internet puis rééssayez.", Toast.LENGTH_SHORT).show();
                Log.d("WebRequest", "Error : " + t.getMessage());
            }
        });

    }
}
