package com.ynov.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myhexaville.smartimagepicker.ImagePicker;
import com.ynov.lesbonnesaffairesdebibi.R;

import java.io.IOException;
import java.io.InputStream;

public class AddActivity extends BaseActivity {

    private ImageView imageView;
    private TextView textView;
    private ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Déposer une annonce");

        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_add, contentLayout);

        imageView = (ImageView) findViewById(R.id.addImage);
        textView = (TextView) findViewById(R.id.addImageText);

        imagePicker = new ImagePicker(AddActivity.this,null, imageUri -> {
            textView.setVisibility(View.GONE);
            imageView.setImageURI(imageUri);
        }).setWithImageCrop(1, 1);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.choosePicture(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

}
