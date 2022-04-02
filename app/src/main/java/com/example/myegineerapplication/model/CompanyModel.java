package com.example.myegineerapplication.model;

import java.io.Serializable;
import java.util.List;

public class CompanyModel implements Serializable {
    private String id;
    private String name;
    private String city;
    private String phone;
    private String email;
    private String street;
    private Integer price;
    private String brief_desc;
    private String number;
    private String password;
    private String nip;
    private String firebaseId;
    private List<String> fitness;

    public CompanyModel() {
    }

    public CompanyModel(String id, String name, String city, String phone, String email, String street, Integer price, String brief_desc, String number, String nip, String firebaseId) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.street = street;
        this.price = price;
        this.brief_desc = brief_desc;
        this.number = number;
        this.nip = nip;
        this.firebaseId = firebaseId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet() {
        return street;
    }

    public String getBrief_desc() {
        return brief_desc;
    }

    public String getNumber() {
        return number;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public void setCity(String city) {
        this.city = city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    public void setBrief_desc(String brief_desc) {
        this.brief_desc = brief_desc;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public List<String> getFitness() {
        return fitness;
    }

    public void setFitness(List<String> fitness) {
        this.fitness = fitness;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }
}
