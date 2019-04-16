package com.techinnovationltd.jabenriderapp.Model;

public class User {


    private String id;
    private String Name;
    private String Email;
    private String Phone;
    private String Birth_Date;
    private String Password;
    private String Service_Type;
    private String image_url;

    public User() {
    }

    public User(String id, String name, String email, String phone, String birth_Date, String password, String service_Type, String image_url) {
        this.id = id;
        Name = name;
        Email = email;
        Phone = phone;
        Birth_Date = birth_Date;
        Password = password;
        Service_Type = service_Type;
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getBirth_Date() {
        return Birth_Date;
    }

    public void setBirth_Date(String birth_Date) {
        Birth_Date = birth_Date;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getService_Type() {
        return Service_Type;
    }

    public void setService_Type(String service_Type) {
        Service_Type = service_Type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}