package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.FormUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditionFragment extends Fragment {
    private static final String ARG_ANNOUNCEMENT = "ANN";
    private static final int EDITION_MODE = 1;
    private static final int NEW_MODE = 0;
    private EditText nameIpt;
    private EditText mailIpt;
    private EditText passwdIpt;
    private EditText passwdConfirmIpt;
    private EditText descIpt;
    private EditText priceIpt;
    private EditText titreIpt;
    private EditText localisationIpt;
    private Spinner categorieSpinner;
    private Announcement announcement;
    private Button selectImageBtn;
    private Button sendButton;
    private  ImageView imagePreview;
    private Uri newImageUri;
    private ImagePicker imagePicker;
    private int mode;

    public EditionFragment() {
        // Required empty public constructor
    }

    public static EditionFragment newInstance() {
        return newInstance(null);
    }

    // TODO: Rename and change types and number of parameters
    public static EditionFragment newInstance(Announcement announcement) {
        EditionFragment fragment = new EditionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ANNOUNCEMENT, announcement);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.announcement = (Announcement) getArguments().getSerializable(ARG_ANNOUNCEMENT);
            if(announcement != null) {
                this.mode = EDITION_MODE;
            }else
            {
                this.mode = NEW_MODE;
                this.announcement = new Announcement();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_edition, container, false);
        this.nameIpt = v.findViewById(R.id.vendorIpt);
        this.mailIpt = v.findViewById(R.id.mailIpt);
        this.passwdIpt = v.findViewById(R.id.passwordIpt );
        this.passwdConfirmIpt = v.findViewById(R.id.passwordConfirmIpt );
        this.descIpt = v.findViewById(R.id.descIpt);
        this.priceIpt = v.findViewById(R.id.priceIpt);
        this.selectImageBtn = v.findViewById(R.id.findImageBtn);
        this.categorieSpinner = v.findViewById(R.id.categorieSpinner);
        this.titreIpt = v.findViewById(R.id.titreIpt);
        this.imagePreview = v.findViewById(R.id.imagePreview);
        this.localisationIpt = v.findViewById(R.id.localisationIpt);
        this.sendButton = v.findViewById(R.id.confirmBtn);

        this.sendButton.setOnClickListener(this.listener);

        this.imagePicker = new ImagePicker(getActivity(),this,imagePickedListener)
                .setWithImageCrop(1,1 );

        if(this.mode == EDITION_MODE){
            this.fillFields();
            this.selectImageBtn.setVisibility(View.GONE);
        }

        this.selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.choosePicture(true);
            }
        });
        return v;
    }

    // Generic listener
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.confirmBtn:
                    EditionFragment.this.onSendClicked();
                    break;
                case R.id.cancelBtn:
                    EditionFragment.this.onCanelClicked();
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
        Fragment fragment = AnnouncementListFragment.newInstance();
        ((BaseActivity)getActivity()).navigate(fragment);

    }


    protected boolean checkForm() {
        boolean hasError = false;
        EditText[] toChek = {this.titreIpt, this.localisationIpt, this.nameIpt, this.descIpt,
                this.passwdConfirmIpt,this.passwdIpt, this.priceIpt};
        hasError = FormUtils.validateNotEmpty(getActivity(),toChek);
        hasError =  !FormUtils.validateEmail(getActivity(),this.mailIpt) || hasError;
        hasError = !FormUtils.validateSpinnerNotEmpty(getActivity(),this.categorieSpinner,R.string.cate_all) || hasError;

        if(!this.passwdIpt.getText().toString().equals(this.passwdConfirmIpt.getText().toString())){
            this.passwdConfirmIpt.setError(getString(R.string.password_unmatch_error));
            hasError = true;
        }
        if(this.newImageUri == null && this.mode == NEW_MODE) {
            AlertUtils.alertFailure(getActivity(), getActivity().getString(R.string.image_missing));
            hasError = true;
        }
        return !hasError;
    }

    private Callback<Announcement> callback = new Callback<Announcement>() {
        @Override
        public void onResponse(@NonNull Call<Announcement> call, Response<Announcement> response) {
            if(response.code() == 200 && response.body() != null) {
                announcement = response.body();
                AlertDialog alertDialog =  AlertUtils.alertSucess(getActivity(),
                        mode == NEW_MODE ? getString(R.string.create_success) : getString(R.string.edit_success));
                alertDialog.setOnDismissListener(navToDetail);
                alertDialog.show();
            } else {
                AlertUtils.alertFailure(getActivity()).show();
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
            Fragment fragment = DetailFragment.newInstance(announcement);
            ((BaseActivity)getActivity()).navigate(fragment);
        }
    };

    public OnImagePickedListener imagePickedListener = new OnImagePickedListener() {
        @Override
        public void onImagePicked(Uri imageUri) {
            EditionFragment.this.newImageUri = imageUri;
            Glide.with(getActivity()).load(EditionFragment.this.newImageUri).into(EditionFragment.this.imagePreview);
        }
    };

    //Image picker stuff
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }
}
