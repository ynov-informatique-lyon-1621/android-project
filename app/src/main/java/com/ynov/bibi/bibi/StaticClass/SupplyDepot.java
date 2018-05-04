package com.ynov.bibi.bibi.StaticClass;

import com.ynov.bibi.bibi.Models.Ad;

import java.util.ArrayList;

//Classe static permettant de stocker des données afin d'y accéder de partout dans l'application.
public class SupplyDepot {

    public static ArrayList<Ad> _currentAds;
    public static Boolean connected = false;
}
