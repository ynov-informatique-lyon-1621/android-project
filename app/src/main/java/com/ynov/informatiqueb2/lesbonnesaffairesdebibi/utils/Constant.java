package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constant {
    public static final String endpoint = "http://thibault01.com:8081";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.", Locale.FRANCE);
    public static final SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.FRANCE);
}
