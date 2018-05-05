package com.example.ynov.lesbonnesaffairesdebibi.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Thoma on 03/05/2018.
 */

public class User implements Serializable {

    private String _username;
    private String _email;
    private ArrayList<Annonce> _userAnnonces;
    private ArrayList<Annonce> _userFavs;

    public User(String _username, String _email) {
        this._username = _username;
        this._email = _email;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public ArrayList<Annonce> get_userAnnonces() {
        return _userAnnonces;
    }

    public void set_userAnnonces(ArrayList<Annonce> _userAnnonces) {
        this._userAnnonces = _userAnnonces;
    }

    public ArrayList<Annonce> get_userFavs() {
        return _userFavs;
    }

    public void set_userFavs(ArrayList<Annonce> _userFavs) {
        this._userFavs = _userFavs;
    }
}
