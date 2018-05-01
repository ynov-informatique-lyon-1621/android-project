package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormater {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.", Locale.FRANCE);
    public static final SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.FRANCE);
    public static final SimpleDateFormat ISODateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

    public static String  format(Date date)  {
        if(date.equals(new Date())) {
            return "Aujourd'hui à" + hourFormat.format(date);
        } else {
            return dateFormat.format(date);
        }
    }
}
