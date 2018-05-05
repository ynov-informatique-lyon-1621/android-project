package com.example.elesa.projet_lelt.Ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elesa.projet_lelt.Model.DownloadImage;
import com.example.elesa.projet_lelt.R;

public class Contacter extends AppCompatActivity {

    TextView titre;
    TextView pris;
    TextView seller;
    TextView cate;
    TextView date;
    Button send;
    Button reset;
    Button retour;
    ImageView imageView;
    EditText nom;
    EditText mail;
    EditText phone;
    EditText msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacter);

        Intent intent = getIntent();


//récupère les variables transmit précédement
        final String titre2 = intent.getStringExtra("titre");
        final String prix2 = intent.getStringExtra("prix");
        final String seller2 = intent.getStringExtra("seller");
        final String categories2 = intent.getStringExtra("categories");
        final String img = intent.getStringExtra("img");
        final String createDate = intent.getStringExtra("date");

        //stock les TextView dans des variables
        titre = findViewById(R.id.titre);
        pris = findViewById(R.id.prix);
        seller = findViewById(R.id.seller);
        cate = findViewById(R.id.categorie);
        date = findViewById(R.id.date);
        send = findViewById(R.id.send);
        reset = findViewById(R.id.annuler);
        retour = findViewById(R.id.retour);
        imageView = findViewById(R.id.imageView2);
        nom = findViewById(R.id.nom);
        mail = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        msg = findViewById(R.id.msg);
        //change le text view par les attributs
        titre.setText(titre2);
        pris.setText(prix2);
        seller.setText(seller2);
        cate.setText(categories2);
        date.setText(createDate);
//        //affiche l'image depuis l'Url + le nom de l'image à la place de l'imageView
        new DownloadImage((ImageView) imageView).execute("http://139.99.98.119:8080/images/lesbonsplansdebibi/" +img);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contacter.this, Confirmation.class);
                intent.putExtra("titre", titre2 );
                intent.putExtra("cat", categories2 );
                intent.putExtra("date", createDate );
                intent.putExtra("prix", prix2 );
                intent.putExtra("seller", seller2 );
                intent.putExtra("img", img );

                startActivity(intent);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nom.setText("");
                mail.setText((""));
                phone.setText((""));
                msg.setText((""));
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contacter.this, Detail.class);
                startActivity(intent);
            }
        });
    }
}
