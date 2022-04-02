package com.example.myegineerapplication.model;

public class PayModel {
    String type;
    String duration;
    String price;
    String gym_membership_card_type;

    public PayModel(){}
    public PayModel(String type, String duration, String price, String gym_membership_card_type) {
        this.type = type;
        this.duration = duration;
        this.price = price;
        this.gym_membership_card_type = gym_membership_card_type;
    }

    public String getGym_membership_card_type() {
        return gym_membership_card_type;
    }

    public void setGym_membership_card_type(String gym_membership_card_type) {
        this.gym_membership_card_type = gym_membership_card_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
