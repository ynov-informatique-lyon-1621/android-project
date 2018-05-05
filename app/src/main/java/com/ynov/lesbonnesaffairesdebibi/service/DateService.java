package com.ynov.lesbonnesaffairesdebibi.service;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

public class DateService {

    private PrettyTime p;
    private String inputDate;
    private Date outputDate;

    public DateService(String inputDate) {
        this.inputDate = inputDate;

        // On initialise la librairie d'affichage des dates
        p = new PrettyTime(Locale.FRANCE);
    }

    public String show() {
        if(inputDate != null) {
            // Si la date n'est pas null, on la convertit en une valeur "human readable" (il y a X heures, etc...) et on la retourne
            outputDate = new Date(Long.valueOf(inputDate));
            return p.format(outputDate);
        }

        // Sinon on retourne "Aucune date"
        return "Aucune date";
    }

}
