package com.example.ynov.lesbonnesaffairesdebibi.ViewModel;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ynov.lesbonnesaffairesdebibi.Converter.CallAPI;
import com.example.ynov.lesbonnesaffairesdebibi.Model.Annonce;
import com.example.ynov.lesbonnesaffairesdebibi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;

public class AddAd extends AppCompatActivity implements View.OnClickListener {

    protected EditText userName, userEmail, userPwd, userPwdConf, adTitle, adLocation, adPrice, adDescription;
    protected Spinner adCateg;
    protected ImageView adImg;
    protected Button uploadImg, submitAd, cancelAd, buttonBackAfter, deleteButton;
    Annonce _annonce;
    private static int RESULT_LOAD_IMAGE = 1;
    boolean inModif = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Controls de l'ajout d'une annonce
        userName =  (EditText) findViewById(R.id.userName);
        userEmail = (EditText) findViewById(R.id.userEmail);
        userPwd =  (EditText) findViewById(R.id.userPwd);
        userPwdConf = (EditText) findViewById(R.id.userPwdConf);
        adTitle =  (EditText) findViewById(R.id.adTitle);
        adLocation = (EditText) findViewById(R.id.adLocation);
        adPrice =  (EditText) findViewById(R.id.adPrice);
        adDescription = (EditText) findViewById(R.id.adDescription);

        adImg = (ImageView) findViewById(R.id.adImg);

        // instanciations des catégories
        adCateg = (Spinner) findViewById(R.id.adCateg);
        String[] items = new String[]{"Véhicules", "Vêtements"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        adCateg.setAdapter(adapter);

        uploadImg = (Button)  findViewById(R.id.uploadImg);
        uploadImg.setOnClickListener(this);
        submitAd = (Button)  findViewById(R.id.submitAd);
        submitAd.setOnClickListener(this);
        cancelAd = (Button)  findViewById(R.id.cancelAd);
        cancelAd.setOnClickListener(this);
        buttonBackAfter = (Button) findViewById(R.id.buttonBackAfter);
        buttonBackAfter.setOnClickListener(this);


        try{

            Intent myIntent = getIntent(); // gets the previously created intent
            _annonce = (Annonce) myIntent.getSerializableExtra("myAd"); // will return "FirstKeyValue


            userName.setText(_annonce.get_seller().get_username());
            userEmail.setText(_annonce.get_seller().get_email());

            adTitle.setText(_annonce.get_title());
            adLocation.setText(_annonce.get_localisation());
            adPrice.setText(_annonce.get_price().toString());
            adDescription.setText(_annonce.get_description());

            inModif = true;

            deleteButton = (Button) findViewById(R.id.deleteAdButton);
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_accueil) {
            finish();
        }
        if( id == R.id.action_add_ad){
            Toast.makeText(this, R.string.alreadyHere, Toast.LENGTH_SHORT).show();
        }
        if( id == R.id.action_edit_ad) {
            Intent editAd = new Intent(this, AdEdit.class);
            startActivity(editAd);
            finish();
        }
        if( id == R.id.action_fav) {
            Toast.makeText(this, R.string.notYet, Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteAdButton:
                CallAPI delAd = new CallAPI(this);

                JSONObject adDelete = new JSONObject();
                try {

                    adDelete.put("id", _annonce.get_id());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                delAd.execute("http://139.99.98.119:8080/deleteAnnonce", "DELETE", adDelete.toString());

                break;

            case R.id.buttonBackAfter:
                finish();
                break;
            case R.id.uploadImg:
                Intent i = new Intent(
                        Intent.ACTION_GET_CONTENT,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;

            case R.id.submitAd:

                // REGEX afin de vérifier si on a une adresse mail
                Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                Matcher mat = pattern.matcher(userEmail.getText().toString());

                if(userName.getText().toString().equals("")){
                    Toast.makeText(this, "Veuillez mettre votre nom !", Toast.LENGTH_SHORT).show();
                }else if(!mat.matches()){
                    Toast.makeText(this, "Veuillez mettre votre email !", Toast.LENGTH_SHORT).show();
                }else if(adPrice.getText().toString().equals("") || !adPrice.getText().toString().matches("-?\\d+(\\.\\d+)?")){
                    Toast.makeText(this, "Veuillez mettre un prix numérique !", Toast.LENGTH_SHORT).show();
                }else if(adDescription.getText().toString().equals("")){
                    Toast.makeText(this, "Veuillez mettre une description !", Toast.LENGTH_SHORT).show();
                }else if(adCateg.getSelectedItem().toString().equals("")){
                    Toast.makeText(this, "Veuillez mettre une catégorie !", Toast.LENGTH_SHORT).show();
                }else{
                    CallAPI sendAd = new CallAPI(this);
                    // On serialize notre objet avant envoi
                    JSONObject myNewAd = new JSONObject();
                    try {
                        Timestamp dNow = new Timestamp(System.currentTimeMillis()/1000);

                        myNewAd.put("nomVendeur", userName.getText().toString());
                        myNewAd.put("email", userEmail.getText().toString());
                        myNewAd.put("mdp", userPwd.getText().toString());
                        myNewAd.put("titre", adTitle.getText().toString());
                        myNewAd.put("localisation", adLocation.getText().toString());
                        myNewAd.put("categorie", adCateg.getSelectedItem().toString());
                        myNewAd.put("prix", adPrice.getText().toString());
                        myNewAd.put("description", adDescription.getText().toString());
                        myNewAd.put("dateCreation", dNow.getTime());
                        myNewAd.put("image", "test");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("new :" ,myNewAd.toString());
                    if(inModif){
                        sendAd.execute("http://139.99.98.119:8080/updateAnnonce", "PUT", myNewAd.toString());
                    }else {
                        sendAd.execute("http://139.99.98.119:8080/saveAnnonce", "POST", myNewAd.toString());
                    }




                    ScrollView scrollViewForm = (ScrollView) findViewById(R.id.scrollViewForm);
                    RelativeLayout relativeLayoutAfterForm = (RelativeLayout) findViewById(R.id.relativeLayoutAfterForm);
                    scrollViewForm.setVisibility(View.GONE);
                    relativeLayoutAfterForm.setVisibility(View.VISIBLE);

                }
                break;

            case R.id.cancelAd:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            adImg.setImageURI(imageUri);

        }


    }
}
