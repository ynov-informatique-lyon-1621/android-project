package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

public class FormUtils {
    public static boolean validateNotEmpty(Context context, EditText[] views){
        boolean empty  = false;
        for (EditText v:views) {
            if(TextUtils.isEmpty(v.getText())) {
                v.setError(context.getString(R.string.empty_error));
                empty = true;
            }
        }
        return !empty;
    }

    public static boolean validateEmail(Context context, EditText v) {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(v.getText()).matches()){
            v.setError(context.getString(R.string.mail_invalid_error));
            return false;
        }
        return true;
    }

    public static boolean validateSpinnerNotEmpty(Context context, Spinner spinner, int defaultValue) {
        if (spinner.getSelectedItem().toString().equals(context.getString(defaultValue))){
            spinner.performClick();
            return false;
        }
        return true;
    }
}
