package com.example.myegineerapplication.model;

public class FitnessUser {
    private  String id;
    private  String  user_name;
    private  String  activity_name;
    private  String day;
    private  String hour;
    private  String email;

    public FitnessUser() {
    }

    public FitnessUser(String id, String user_name, String activity_name, String day, String hour, String email) {
        this.id = id;
        this.user_name = user_name;
        this.activity_name = activity_name;
        this.day = day;
        this.hour = hour;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
