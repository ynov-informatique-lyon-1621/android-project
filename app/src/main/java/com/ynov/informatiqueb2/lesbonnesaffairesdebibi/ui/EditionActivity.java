package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.PathUtils;

import java.io.File;
import java.net.URI;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditionActivity extends BaseActivity {

    EditText nameIpt;
    EditText mailIpt;
    EditText passwdIpt;
    EditText passwdConfirmIpt;
    EditText descIpt;
    EditText priceIpt;
    Spinner categorieSpinner;
    Announcement announcement;
    Button selectImageBtn;
    Button sendButton;
    ImageView imagePreview;
    Uri newImageUri;
    private static final int EDITION_MODE = 1;
    private static final int NEW_MODE = 0;
    private static final int IMAGE_REQUEST_CODE = 201;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.nameIpt = findViewById(R.id.vendorNameIpt);
        this.mailIpt = findViewById(R.id.mailIpt);
        this.passwdIpt = findViewById(R.id.passwordIpt );
        this.passwdConfirmIpt = findViewById(R.id.passwordConfirmIpt );
        this.descIpt = findViewById(R.id.descIpt);
        this.priceIpt = findViewById(R.id.priceIpt);
        this.selectImageBtn = findViewById(R.id.findImageBtn);
        this.categorieSpinner = findViewById(R.id.categorieSpinner);
        this.imagePreview = findViewById(R.id.imagePreview);

        this.sendButton = findViewById(R.id.confirmBtn);
        this.sendButton.setOnClickListener(this.listener);
        Intent intent = getIntent();
        Announcement announcement = (Announcement)intent.getSerializableExtra("annoucement");
        if(announcement != null) {
            this.mode = EDITION_MODE;
            this.announcement = announcement;
            this.selectImageBtn.setVisibility(View.GONE);
            this.fillFields();
        } else {
            this.mode = NEW_MODE;
            this.announcement = new Announcement();
        }

        this.selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });
    }


    // Generic listener
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.confirmBtn:
                    EditionActivity.this.onSendClicked();
                    break;
                case R.id.cancelBtn:
                    EditionActivity.this.onCanelClicked();
            }
        }
    };

    private void fillFields() {
        this.mailIpt.setText(this.announcement.getEmail());
    }

    protected void onSendClicked() {
        Log.i("SEND","SEND");
        if(this.checkForm()){
            this.announcement.setNomVendeur(this.nameIpt.getText().toString());
            this.announcement.setDescription(this.descIpt.getText().toString());
            this.announcement.setEmail(this.mailIpt.getText().toString());
            this.announcement.setMdp(this.passwdIpt.getText().toString());
            this.announcement.setPrix(Integer.valueOf(priceIpt.getText().toString()));
            this.announcement.setCategorie(this.categorieSpinner.getSelectedItem().toString());


            try {
                File file = new File(PathUtils.getPath(this,this.newImageUri));
                RequestBody requestFile =  RequestBody.create(MediaType.parse("image/*"), file);

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("image", file.getName(), requestFile);

                ApiService.getInstance().addAnnonce(this.announcement, body).enqueue(this.callback);
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    protected void onCanelClicked() {
        Intent intent = new Intent(EditionActivity.this, AnnouncementListActivity.class);
        startActivity(intent);

    }


    protected boolean checkForm() {
        boolean hasError = false;
        EditText[] toChek = {this.nameIpt, this.mailIpt, this.descIpt,
        this.passwdConfirmIpt,this.passwdIpt, this.priceIpt};
        for(EditText input: toChek) {
            if(TextUtils.isEmpty(input.getText())){
                input.setError(getString(R.string.empty_error));
                hasError = true;
            }
        }
        if(!this.passwdIpt.getText().toString().equals(this.passwdConfirmIpt.getText().toString())){
           this.passwdConfirmIpt.setError("Les mots de passes ne correspondent pas");
           hasError = true;
        }
        if(this.newImageUri == null && this.mode == NEW_MODE) {
            new AlertDialog.Builder(EditionActivity.this)
                    .setMessage("Merci d'ajouter une image à votre annonce")
                    .setTitle("Le formulaire contient une erreur")
                    .create()
                    .show();
            hasError = true;
        }
        return !hasError;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_edition;
    }

    private Callback<Announcement> callback = new Callback<Announcement>() {
        @Override
        public void onResponse(Call<Announcement> call, Response<Announcement> response) {
            Log.i("BODY",response.body().toString());
        }

        @Override
        public void onFailure(Call<Announcement> call, Throwable t) {
            Log.i("ERROR",t.getMessage());

        }
    };
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                this.newImageUri  =  data.getData();
                Log.i("URI", "Uri: " + this.newImageUri.toString());

                Glide.with(this).load(this.newImageUri).into(this.imagePreview);
            }
        }
    }
}
