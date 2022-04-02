package com.example.myegineerapplication.model;

public class UserModel {
    public String name;
    public String phone;
    public String email;
    public String uid;

    public UserModel(){}


    public  UserModel(String uid, String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.uid = uid;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





}
