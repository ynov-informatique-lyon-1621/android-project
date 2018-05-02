package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.AlertUtils;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.PathUtils;

import java.io.File;

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
    EditText titreIpt;
    EditText localisationIpt;
    Spinner categorieSpinner;
    Announcement announcement;
    Button selectImageBtn;
    Button sendButton;
    ImageView imagePreview;
    Uri newImageUri;
    ImagePicker imagePicker;
    private static final int EDITION_MODE = 1;
    private static final int NEW_MODE = 0;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.nameIpt = findViewById(R.id.vendorIpt);
        this.mailIpt = findViewById(R.id.mailIpt);
        this.passwdIpt = findViewById(R.id.passwordIpt );
        this.passwdConfirmIpt = findViewById(R.id.passwordConfirmIpt );
        this.descIpt = findViewById(R.id.descIpt);
        this.priceIpt = findViewById(R.id.priceIpt);
        this.selectImageBtn = findViewById(R.id.findImageBtn);
        this.categorieSpinner = findViewById(R.id.categorieSpinner);
        this.titreIpt = findViewById(R.id.titreIpt);
        this.imagePreview = findViewById(R.id.imagePreview);
        this.localisationIpt = findViewById(R.id.localisationIpt);
        this.sendButton = findViewById(R.id.confirmBtn);
        this.sendButton.setOnClickListener(this.listener);

        this.imagePicker = new ImagePicker(this,null,imagePickedListener)
                .setWithImageCrop(1,1 );

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
               imagePicker.choosePicture(true);
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
        this.nameIpt.setText(this.announcement.getNomVendeur());
        this.mailIpt.setText(this.announcement.getEmail());
        this.descIpt.setText(this.announcement.getDescription());
        this.passwdIpt.setText(this.announcement.getMdp());
        this.passwdConfirmIpt.setText(this.announcement.getMdp());
        this.priceIpt.setText(String.valueOf(this.announcement.getPrix()));
        this.titreIpt.setText(this.announcement.getTitre());
        this.localisationIpt.setText(this.announcement.getLocalisation());
        Glide.with(this).load(this.announcement.getImage()).into(this.imagePreview);
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
            this.announcement.setTitre(this.titreIpt.getText().toString());
            this.announcement.setLocalisation(this.localisationIpt.getText().toString());

            if(this.mode == NEW_MODE) {
                try {
                    File file = new File(this.newImageUri.getPath());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("image", file.getName(), requestFile);

                    ApiService.getInstance().addAnnonce(this.announcement, body).enqueue(this.callback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                ApiService.getInstance().updateAnnonce(announcement.getId(),this.announcement).enqueue(this.callback);
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
        public void onResponse(@NonNull Call<Announcement> call, Response<Announcement> response) {
           if(response.code() == 200 && response.body() != null) {
               announcement = response.body();
               AlertDialog alertDialog =  AlertUtils.alertSucess(EditionActivity.this ,
                       mode == NEW_MODE ? getString(R.string.create_success) : getString(R.string.edit_success));
                       alertDialog.setOnDismissListener(navToDetail);
                       alertDialog.show();
           } else {
               AlertUtils.alertFailure(EditionActivity.this).show();
           }
        }

        @Override
        public void onFailure (@NonNull Call<Announcement> call, Throwable t) {
            Log.i("HTTP FAILURE",t.getMessage());
        }
    };


    private DialogInterface.OnDismissListener navToDetail = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            Intent intent = new Intent(EditionActivity.this,DetailActivity.class);
            intent.putExtra("annoucement",announcement);
            startActivity(intent);
        }
    };


    //Image picker stuff.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

    public OnImagePickedListener imagePickedListener = new OnImagePickedListener() {
        @Override
        public void onImagePicked(Uri imageUri) {
                EditionActivity.this.newImageUri = imageUri;
                Glide.with(EditionActivity.this).load(EditionActivity.this.newImageUri).into(EditionActivity.this.imagePreview);
        }
    };
}
