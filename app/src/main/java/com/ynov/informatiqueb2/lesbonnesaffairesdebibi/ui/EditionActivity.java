package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

import javax.microedition.khronos.egl.EGLDisplay;

public class EditionActivity extends BaseActivity {

    EditText nameIpt;
    EditText mailIpt;
    EditText passwdIpt;
    EditText passwdConfirmIpt;
    EditText descIpt;
    EditText priceIpt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.nameIpt = findViewById(R.id.vendorNameIpt);
        this.mailIpt = findViewById(R.id.mailIpt);
        this.passwdIpt = findViewById(R.id.passwordIpt );
        this.passwdConfirmIpt = findViewById(R.id.passwordConfirmIpt );
        this.descIpt = findViewById(R.id.descIpt);
        this.priceIpt = findViewById(R.id.priceIpt);
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

    protected void onSendClicked() {
        if(this.checkForm()){

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
        return !hasError;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_edition;
    }
}
