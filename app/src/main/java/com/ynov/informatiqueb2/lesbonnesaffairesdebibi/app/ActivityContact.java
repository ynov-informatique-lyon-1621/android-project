package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

public class ActivityContact extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        setTitle("Formulaire de contact");

        Button resetContact = findViewById(R.id.resetcontact);
        Button submitContact = findViewById(R.id.submitcontact);
        Button retourContact = findViewById(R.id.retourcontact);


        final EditText NameContact = findViewById(R.id.namecontact);
        final EditText MailContact = findViewById(R.id.mailcontact);
        final EditText TelContact = findViewById(R.id.telcontact);
        final EditText MessageContact = findViewById(R.id.messagecontact);

        ImageView imagecontact = findViewById(R.id.imagecontact);
        Glide.with(ActivityContact.this).load(R.drawable.logo).into(imagecontact);

        submitContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = NameContact.getText().toString();
                String email = MailContact.getText().toString();
                String phone = TelContact.getText().toString();
                String message = MessageContact.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    NameContact.setError("Nom obligatoire");
                } else if (TextUtils.isEmpty(email)) {
                    MailContact.setError("Email obligatoire");
                } else {

                    if (TextUtils.isEmpty(message)) {
                        MessageContact.setError("Message Obligatoire");
                    } else {

                        Intent recapMessage = new Intent(ActivityContact.this, ActivitySuccesMessage.class);

                        recapMessage.putExtra("recapContact", name);
                        recapMessage.putExtra("recapMessage", message);
                        recapMessage.putExtra("recapEmail", email);
                        recapMessage.putExtra("recapTelephone", phone);

                        startActivity(recapMessage);
                    }

                }
            }
        });

        // Button reset qui permet d'annuler toute les saisies
        resetContact.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                NameContact.setText("");
                MailContact.setText("");
                TelContact.setText("");
                MessageContact.setText("");
            }
        });

        // Button retour qui permet de retourner aux detail de l'annonce
        retourContact.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityContact.this, ActivityDetail.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings_connect) {
            Intent intent = new Intent(this, ActivityForm.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
