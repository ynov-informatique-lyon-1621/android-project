package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;


public class AlertUtils {
    public static AlertDialog alertSucess(Context context, String message){
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setTitle(context.getString(R.string.generic_sucess))
                .create();
    }

    public static AlertDialog alertFailure(Context context) {
        return alertFailure(context,context.getString(R.string.error_generic));
    }

    public  static AlertDialog alertFailure(Context context,String message){
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setTitle(context.getString(R.string.sorry))
                .create();
    }
}
