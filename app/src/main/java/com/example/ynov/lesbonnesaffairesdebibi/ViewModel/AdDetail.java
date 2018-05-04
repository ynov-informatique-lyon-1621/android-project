package com.example.ynov.lesbonnesaffairesdebibi.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ynov.lesbonnesaffairesdebibi.Converter.CallAPI;
import com.example.ynov.lesbonnesaffairesdebibi.Model.Annonce;
import com.example.ynov.lesbonnesaffairesdebibi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;

public class AdDetail extends AppCompatActivity implements View.OnClickListener {

    protected TextView adTitleTextView, adPriceTextView, adDateTextView, adCategTextView, adAuthorTextView, adDescriptionTextView;
    protected ImageView adImg;
    protected Button contactButton;

    protected EditText senderNameEditText, senderEmailEditText, senderPhoneEditText, senderMessageEditText;
    protected Button sendMessage, resetMessage, cancelMessage, goBack, backHome;

    protected ArrayList<String> Message;

    protected Annonce _annonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initAdDetail();
        initContactForm();



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
            Intent addAd = new Intent(this, MainActivity.class);
            startActivity(addAd);
            finish();
        }
        if (id == R.id.action_add_ad) {
            Intent addAd = new Intent(this, AddAd.class);
            startActivity(addAd);
            finish();
        }
        if( id == R.id.action_edit_ad) {
            Toast.makeText(this, R.string.notYet, Toast.LENGTH_SHORT).show();
        }
        if( id == R.id.action_fav) {
            Toast.makeText(this, R.string.notYet, Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void initAdDetail() {
        contactButton = (Button)  findViewById(R.id.contactButton);
        contactButton.setOnClickListener(this);

        adTitleTextView =  (TextView) findViewById(R.id.adTitle);
        adPriceTextView = (TextView) findViewById(R.id.adPrice);
        adDateTextView =  (TextView) findViewById(R.id.adDate);
        adCategTextView = (TextView) findViewById(R.id.adCateg);
        adDescriptionTextView =  (TextView) findViewById(R.id.adDescription);
        adImg = (ImageView) findViewById(R.id.adImg);

        adAuthorTextView = (TextView) findViewById(R.id.adAuthor);

        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        _annonce = (Annonce) myIntent.getSerializableExtra("myAd"); // will return "FirstKeyValue


        adTitleTextView.setText(_annonce.get_title());
        adPriceTextView.setText(_annonce.get_price().toString());
        adDateTextView.setText(_annonce.formatDate());
        adCategTextView.setText(getString(R.string.categoryLibelle_adDetail) + _annonce.get_category());
        adDescriptionTextView.setText(_annonce.get_description());
        adImg.setBackground(_annonce.findPic(this));
        adAuthorTextView.setText(_annonce.get_seller().get_username());
    }

    void initContactForm(){
        senderNameEditText =  (EditText) findViewById(R.id.senderName);
        senderEmailEditText = (EditText) findViewById(R.id.senderEmail);
        senderPhoneEditText =  (EditText) findViewById(R.id.senderPhone);
        senderMessageEditText = (EditText) findViewById(R.id.senderMessage);


        sendMessage = (Button)  findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(this);
        resetMessage = (Button)  findViewById(R.id.resetMessage);
        resetMessage.setOnClickListener(this);
        cancelMessage = (Button)  findViewById(R.id.cancelMessage);
        cancelMessage.setOnClickListener(this);

        goBack = (Button) findViewById(R.id.goBack);
        goBack.setOnClickListener(this);
        backHome = (Button) findViewById(R.id.backHome);
        backHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contactButton :
                Toast.makeText(this, _annonce.get_seller().get_email(), Toast.LENGTH_LONG);

                adDescriptionTextView.setVisibility(TextView.GONE);
                contactButton.setVisibility(TextView.GONE);
                LinearLayout linearLayoutMessage = (LinearLayout) findViewById(R.id.linearLayoutMessage);
                linearLayoutMessage.setVisibility(View.VISIBLE);

                break;

            case R.id.sendMessage:

                Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                Matcher mat = pattern.matcher(senderEmailEditText.getText().toString());

                if(senderNameEditText.getText().toString().equals("")){
                    Toast.makeText(this, "Veuillez mettre un nom !", Toast.LENGTH_SHORT).show();
                }else if(!mat.matches()){
                    Toast.makeText(this, "Veuillez mettre une adresse email !", Toast.LENGTH_SHORT).show();
                }else if(senderMessageEditText.getText().toString().equals("")){
                    Toast.makeText(this, "Veuillez mettre un message !", Toast.LENGTH_SHORT).show();
                }else{
                    CallAPI sendMsg = new CallAPI();
                    JSONObject myMsg = new JSONObject();
                    try {
                        myMsg.put("Nom", senderNameEditText.getText().toString());
                        myMsg.put("Email", senderEmailEditText.getText().toString());
                        myMsg.put("Telephone", senderPhoneEditText.getText().toString());
                        myMsg.put("Message", senderMessageEditText.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sendMsg.execute("http://139.99.98.119:8080/sendMessage", myMsg.toString());
                    linearLayoutMessage = (LinearLayout) findViewById(R.id.linearLayoutMessage);
                    LinearLayout linearLayoutMessageAfter = (LinearLayout) findViewById(R.id.linearLayoutAfterContact);
                    linearLayoutMessage.setVisibility(View.GONE);
                    resetFields();

                    TextView sellerLine = (TextView) findViewById(R.id.sellerLine);
                    sellerLine.setText(_annonce.get_seller().get_username() + " reviendra vers vous rapidement.");
                    linearLayoutMessageAfter.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.resetMessage:

                resetFields();

                break;

            case R.id.cancelMessage:
                resetFields();
                adDescriptionTextView.setVisibility(TextView.VISIBLE);
                contactButton.setVisibility(TextView.VISIBLE);
                linearLayoutMessage = (LinearLayout) findViewById(R.id.linearLayoutMessage);
                linearLayoutMessage.setVisibility(View.GONE);

                break;

            case R.id.backHome:
                finish();
                break;

            case R.id.goBack:
                adDescriptionTextView.setVisibility(TextView.VISIBLE);
                contactButton.setVisibility(TextView.VISIBLE);
                LinearLayout linearLayoutMessageAfter = (LinearLayout) findViewById(R.id.linearLayoutAfterContact);
                linearLayoutMessageAfter.setVisibility(View.GONE);

                break;
        }
    }

    void resetFields(){
        senderNameEditText.setText("");
        senderEmailEditText.setText("");
        senderPhoneEditText.setText("");
        senderMessageEditText.setText("");
    }




}
