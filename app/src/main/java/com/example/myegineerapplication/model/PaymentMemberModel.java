package com.example.myegineerapplication.model;

public class PaymentMemberModel {

    private String uid;
    private String user_name;
    private String date;
    private String membership_card_type;
    private String type;
    private String phone;
    private String entries;
    private String email;
    private String price;
    private String full_gym_address;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String member_name) {
        this.user_name = member_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMembership_card_type() {
        return membership_card_type;
    }

    public void setMembership_card_type(String membership_card_type) {
        this.membership_card_type = membership_card_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEntries() {
        return entries;
    }

    public void setEntries(String entries) {
        this.entries = entries;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFull_gym_address() {
        return full_gym_address;
    }

    public void setFull_gym_address(String full_gym_address) {
        this.full_gym_address = full_gym_address;
    }
}
