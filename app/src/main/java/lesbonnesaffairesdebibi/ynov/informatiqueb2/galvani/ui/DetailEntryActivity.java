package lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.R;

public class DetailEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("LesBonnesAffairesDeBibi.fr");

        // Recupère tous les objets

        TextView TitleDetail = findViewById(R.id.TitleDetailView);
        TextView CategorieDetail = findViewById(R.id.CatDetailView);
        TextView  PriceDetail = findViewById(R.id.PriceDetailView);
        TextView DescriptionDetail = findViewById(R.id.DescriptionDetailView);
        TextView NomVendeurDetail = findViewById(R.id.NomVendeurDetailView);
        ImageView PictureDetail = findViewById(R.id.imageView);

        Intent intentDetails = getIntent();

        String Titre = intentDetails.getStringExtra("Titre");
        String Categorie = intentDetails.getStringExtra("Categorie");
        String Prix = intentDetails.getStringExtra("Prix");
        String Description = intentDetails.getStringExtra("Description");
        String NomVendeur = intentDetails.getStringExtra("NomVendeur");
        String Image = intentDetails.getStringExtra("Picture");

        TitleDetail.setText(Titre);
        CategorieDetail.setText("Catégorie: " + Categorie);
        PriceDetail.setText(Prix + " €");
        DescriptionDetail.setText(Description);
        NomVendeurDetail.setText("Vendu par " + NomVendeur);
        Glide.with(DetailEntryActivity.this)
                .load("http://139.99.98.119:8080/" + Image.substring(25))
                .into(PictureDetail);

        Button idcontact = findViewById(R.id.IDContact);
        idcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailEntryActivity.this, ContactSellerActivity.class);

                startActivity(intent);
            }
        });


        // Menu
    }
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