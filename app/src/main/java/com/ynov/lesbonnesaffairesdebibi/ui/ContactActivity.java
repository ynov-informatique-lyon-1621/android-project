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
        //setContentView(R.layout.activity_contact);

        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_contact, contentLayout);

        Intent intentData = getIntent();
        Annonce annonce = (Annonce) intentData.getSerializableExtra("data");

        //setTitle("Contacter " + annonce.getNomVendeur());

        ImageView annImage = findViewById(R.id.annImage);
        TextView annTitle = findViewById(R.id.annTitle);
        TextView annPrice = findViewById(R.id.annPrice);
        TextView annVendeur = findViewById(R.id.annVendeur);

        EditText contactName = findViewById(R.id.contactName);
        EditText contactEmail = findViewById(R.id.contactEmail);
        EditText contactPhone = findViewById(R.id.contactPhone);
        EditText contactMessage = findViewById(R.id.contactMessage);
        contactButton = findViewById(R.id.contactButton);

        Picasso.get().load(annonce.getImage()).into(annImage);
        annTitle.setText(annonce.getTitre());
        annPrice.setText(annonce.getPrix());
        annVendeur.setText(annonce.getNomVendeur());

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = contactName.getText().toString();
                String email = contactEmail.getText().toString();
                String phone = contactPhone.getText().toString();
                String message = contactMessage.getText().toString();

                if(TextUtils.isEmpty(name)) {
                    contactName.setError("Vous devez renseigner un nom");
                } else if(TextUtils.isEmpty(email) || !isValidEmail(email)) {
                    contactEmail.setError("Vous devez renseigner une adresse mail");
                } else if(TextUtils.isEmpty(message)) {
                    contactMessage.setError("Vous devez renseigner un message");
                } else {
                    contactButton.setEnabled(false);

                    Message messageObject = new Message();
                    messageObject.setNom(name);
                    messageObject.setEmail(email);
                    messageObject.setNumeroTelephone(phone);
                    messageObject.setMessage(message);
                    messageObject.setNomVendeur(annonce.getNomVendeur());
                    messageObject.setIdAnnonce(annonce.getId().toString());

                    sendMessage(messageObject);
                }

            }
        });

        httpService = new Retrofit.Builder().baseUrl(HttpService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(HttpService.class);

    }

    public void sendMessage(final Message message) {

        call = httpService.sendMessage(message);

        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                contactButton.setEnabled(true);
                if(response.code() == 201) {
                    new AlertDialog.Builder(ContactActivity.this)
                        .setIcon(R.drawable.ic_check_black_24dp)
                        .setTitle("Message envoyé")
                        .setMessage("Votre message a bien été envoyé au vendeur. Il renviendra vers vous rapidement.")
                        .setPositiveButton("Retour à l'annonce", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
                } else if(response.code() == 400) {
                    Toast.makeText(ContactActivity.this, "Votre message n'a pas pu être envoyé. Veuillez rééssayer ultérieurement.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                contactButton.setEnabled(true);
                Toast.makeText(ContactActivity.this, "Une erreur est survenue lors de l'envoi. Vérifiez votre connexion internet puis rééssayez.", Toast.LENGTH_SHORT).show();
                Log.d("WebRequest", "Error : " + t.getMessage());
            }

        });
    }

}
