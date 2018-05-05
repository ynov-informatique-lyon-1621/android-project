package com.ynov.lesbonnesaffairesdebibi.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

import com.squareup.picasso.Picasso;
import com.ynov.lesbonnesaffairesdebibi.R;
import com.ynov.lesbonnesaffairesdebibi.model.Annonce;
import com.ynov.lesbonnesaffairesdebibi.model.Message;
import com.ynov.lesbonnesaffairesdebibi.service.HttpService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactActivity extends BaseActivity {

    private Button contactButton;
    private HttpService httpService;
    Call<Void> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Appel du layout parent pour inclure la barre d'action globale et le menu (extension de l'activité de base - BaseActivity)
        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_contact, contentLayout);

        // On récupère l'objet sérialisé passé dans l'intent et on le convertit on objet Annonce
        Intent intentData = getIntent();
        Annonce annonce = (Annonce) intentData.getSerializableExtra("data");

        // On déclare les éléments du layout
        ImageView annImage = findViewById(R.id.annImage);
        TextView annTitle = findViewById(R.id.annTitle);
        TextView annPrice = findViewById(R.id.annPrice);
        TextView annVendeur = findViewById(R.id.annVendeur);

        EditText contactName = findViewById(R.id.contactName);
        EditText contactEmail = findViewById(R.id.contactEmail);
        EditText contactPhone = findViewById(R.id.contactPhone);
        EditText contactMessage = findViewById(R.id.contactMessage);
        contactButton = findViewById(R.id.contactButton);

        // Avec la librairie Picasso, on charge l'image de l'annonce dans l'imageView
        Picasso.get().load(annonce.getImage()).into(annImage);

        // On change les textes pour afficher les informations de l'annonce
        annTitle.setText(annonce.getTitre());
        annPrice.setText(annonce.getPrix());
        annVendeur.setText(annonce.getNomVendeur());

        // Ajout d'un écouteur de clic sur le bouton "Envoyer le message"
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Récupération des valeurs des champs du formulaire
                String name = contactName.getText().toString();
                String email = contactEmail.getText().toString();
                String phone = contactPhone.getText().toString();
                String message = contactMessage.getText().toString();

                // On vérifie que les champs ne sont pas vides
                if(TextUtils.isEmpty(name)) {
                    contactName.setError("Vous devez renseigner un nom");
                } else if(TextUtils.isEmpty(email) || !isValidEmail(email)) {
                    // On vérifie que l'adresse mail est valide
                    contactEmail.setError("Vous devez renseigner une adresse mail");
                } else if(TextUtils.isEmpty(message)) {
                    contactMessage.setError("Vous devez renseigner un message");
                } else {
                    // On désactive le clic sur le bouton
                    contactButton.setEnabled(false);

                    // On instancie un nouvel objet Message avec les valeurs du formulaire
                    Message messageObject = new Message();
                    messageObject.setNom(name);
                    messageObject.setEmail(email);
                    messageObject.setNumeroTelephone(phone);
                    messageObject.setMessage(message);
                    messageObject.setNomVendeur(annonce.getNomVendeur());
                    messageObject.setIdAnnonce(annonce.getId().toString());

                    // On passe l'objet Message dans la méthode d'envoi
                    sendMessage(messageObject);
                }

            }
        });

        // On initialise la librairie Retrofit2 permettant de gérer les appels au webservice
        httpService = new Retrofit.Builder().baseUrl(HttpService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(HttpService.class);

    }

    public void sendMessage(final Message message) {

        // On prépare l'appel à l'endpoint correspondant (sendMessage) en passant l'objet Message en paramètre
        call = httpService.sendMessage(message);

        // On met l'appel en file d'attente (appel asynchrone), la réponse sera renvoyée dans la callback
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Si on obtient une réponse, on réactive le clic sur le bouton
                contactButton.setEnabled(true);

                // On vérifie le code http de la réponse
                if(response.code() == 201) {
                    // Si l'envoi s'est bien déroulé, on affiche un dialog de confirmation
                    new AlertDialog.Builder(ContactActivity.this)
                        .setIcon(R.drawable.ic_check_black_24dp)
                        .setTitle("Message envoyé")
                        .setMessage("Votre message a bien été envoyé au vendeur. Il renviendra vers vous rapidement.")
                        .setPositiveButton("Retour à l'annonce", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Au clic sur le bouton du dialog, on revient à l'activité précédente (Détail de l'annonce)
                                finish();
                            }
                        })
                        .show();
                } else if(response.code() == 400) {
                    // Si il y a eu une erreur avec l'envoi du message, on affiche un message d'erreur
                    Toast.makeText(ContactActivity.this, "Votre message n'a pas pu être envoyé. Veuillez rééssayer ultérieurement.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si on obtient une erreur, on réactive le clic sur le bouton et on affiche un message d'erreur
                contactButton.setEnabled(true);
                Toast.makeText(ContactActivity.this, "Une erreur est survenue lors de l'envoi. Vérifiez votre connexion internet puis rééssayez.", Toast.LENGTH_SHORT).show();
                Log.d("WebRequest", "Error : " + t.getMessage());
            }

        });
    }

}
