package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui.LoginActivity;

public class AlertUtils {
    public static AlertDialog alertSucess(Context context, String message){
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setTitle("Désolé")
                .create();
    }

    public static AlertDialog alertFailure(Context context) {
        return alertFailure(context,"Une erreur est survenue");
    }

    public  static AlertDialog alertFailure(Context context,String message){
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setTitle("Désolé")
                .create();
    }
}
