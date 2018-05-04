package fr.lesbonnesaffairesdebibi.prjandroid.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import fr.lesbonnesaffairesdebibi.prjandroid.R;

public class CreationAnnonceValActivity extends AppCompatActivity {

    Button btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_annonce_val);

        btnRetour = findViewById(R.id.retourAnnonceCreaVal);
        btnRetour.setOnClickListener(genericOnClicListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_list_annonce, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accueilMenu:
                Intent intent = new Intent(this, ListAnnonceActivity.class);
                startActivity(intent);
                break;
            case R.id.favorisMenu:
                Intent intent2 = new Intent(this, ListeFavorisActivity.class);
                startActivity(intent2);
                break;
            case R.id.deposeAnnonceMenu:
                Intent intent3 = new Intent(this, CreationAnnonceActivity.class);
                startActivity(intent3);
                break;
            case R.id.modifierAnnonceMenu:
                Intent intent4 = new Intent(this, IdentificationActivity.class);
                startActivity(intent4);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private final View.OnClickListener genericOnClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.retourAnnonceCreaVal:
                    Intent intent = new Intent(CreationAnnonceValActivity.this,ListAnnonceActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
}
