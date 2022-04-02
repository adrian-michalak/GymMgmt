package com.example.myegineerapplication.model;

public class FitnessMemberModel {

    private String name ;
    private String name_activity ;
    private String day ;
    private String hour ;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public FitnessMemberModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_activity() {
        return name_activity;
    }

    public void setName_activity(String name_activity) {
        this.name_activity = name_activity;
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



    public FitnessMemberModel(String id, String name, String name_activity, String day, String hour, String email) {
        this.name = name;
        this.name_activity = name_activity;
        this.day = day;
        this.hour = hour;
        this.email = email;
        this.id = id;
    }


}
