package com.ynov.bibi.bibi.Models;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String sex;
    private String age;
    private String email;
    private String adress;
    private int vendorMark;
    private int phone;
    //icon;
    public ArrayList<Ad> adsOwned;
    public ArrayList<Ad> adsBooked;

    public User(int id, String name, String sex, String age, String email, String adress, int vendorMark, int phone, ArrayList<Ad> adsOwned, ArrayList<Ad> adsBooked) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.email = email;
        this.adress = adress;
        this.vendorMark = vendorMark;
        this.phone = phone;
        this.adsOwned = adsOwned;
        this.adsBooked = adsBooked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getVendorMark() {
        return vendorMark;
    }

    public void setVendorMark(int vendorMark) {
        this.vendorMark = vendorMark;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
