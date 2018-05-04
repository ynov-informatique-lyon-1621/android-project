package com.ynov.bibi.bibi.Models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Ad implements Serializable{
    @SerializedName("id")
    private int id;

    @SerializedName("nomVendeur")
    private String owner;

    @SerializedName("email")
    private String email;

    @SerializedName("mdp")
    private String password;

    @SerializedName("titre")
    private String name;

    @SerializedName("localisation")
    private String location;

    @SerializedName("categorie")
    private String category;

    @SerializedName("prix")
    private Float price;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String picture;

    @SerializedName("dateCreation")
    private Long dateCreation;

    public Ad(int id, String owner, String email, String password, String name, String location, String category, Float price, String description, String picture, Long date)
    {
        this.id = id;
        this.owner = owner;
        this.email = email;
        this.password = password;
        this.name = name;
        this.location = location;
        this.category = category;
        this.price = price;
        this.description = description;
        this.picture = picture;
        if (date != null)
            this.dateCreation = date;
        else
            this.dateCreation = new Long(0);

    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public Float getPrice()
    {
        return price;
    }

    public void setPrice(Float price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public void setDateCreation (Long newDate) { this.dateCreation = newDate; }

    public Long getDateCreation () { return this.dateCreation; }

    public JSONObject toJson()
    {
        JSONObject res = new JSONObject();
        try
        {
            res.put("nomVendeur", owner);
            res.put("email", email);
            res.put("mdp", password);
            res.put("title", name);
            res.put("localisation", location);
            res.put("categorie", category);
            res.put("prix", price);
            res.put("description", description);
            res.put("dateCreation", dateCreation);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return res;
    }
}
