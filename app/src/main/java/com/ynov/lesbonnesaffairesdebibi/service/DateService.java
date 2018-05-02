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
        p = new PrettyTime(Locale.FRANCE);
    }

    public String show() {
        outputDate = new Date(Long.valueOf(inputDate));
        return p.format(outputDate);
    }

}
