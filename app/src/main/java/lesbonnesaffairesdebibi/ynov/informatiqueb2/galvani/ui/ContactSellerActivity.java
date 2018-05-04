package lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.ui;

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

import com.bumptech.glide.Glide;

import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.R;

public class ContactSellerActivity extends AppCompatActivity {

        // Class pour contacter le vendeur

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        Button resetContact = findViewById(R.id.resetContact);
        Button submitContact = findViewById(R.id.submitContact );
        Button retourContact = findViewById(R.id.returnContact);


        final EditText NameContact = findViewById(R.id.nameContact );
        final EditText MailContact = findViewById(R.id.mailContact );
        final EditText TelContact = findViewById(R.id.telContact );
        final EditText MessageContact = findViewById(R.id.messageContact );

        //Utilisation de Glide pour l'image

        
        ImageView seller = findViewById(R.id.imagevendeurContact);
        Glide.with(ContactSellerActivity.this).load(R.drawable.seller).into(seller);

        // Appel du ws pour envoyer le message
        
        submitContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = NameContact.getText().toString();
                String email = MailContact.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    NameContact.setError("Nom obligatoire");
                } else if (TextUtils.isEmpty(email)) {
                    MailContact.setError("Email obligatoire");
                }
            }
        });

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

        retourContact.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactSellerActivity.this, DetailEntryActivity.class);
                startActivity(intent);
            }
        });
    }

    // Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_activity, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addMenu) {
            Intent intent = new Intent( this, AddEntryActivity.class );
            this.startActivity( intent );
            return true;
        }

        if (id == R.id.homeMenu) {
            Intent intent = new Intent( this, HomepageActivity.class );
            this.startActivity( intent );
            return true;
        }

        if (id == R.id.editMenu) {
            Intent intent = new Intent( this, EditEntryActivity.class );
            this.startActivity( intent );
            return true;
        }

        if (id == R.id.favoriteMenu) {
            Intent intent = new Intent( this, FavoriteActivity.class );
            this.startActivity( intent );
            return true;
        }

        return super.onOptionsItemSelected( item );
    }
}
