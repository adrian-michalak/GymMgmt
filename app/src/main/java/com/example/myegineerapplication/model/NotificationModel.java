package com.example.myegineerapplication.model;

public class NotificationModel {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    String date;
    String body;
    public NotificationModel(String date,String body){
            this.body = body;
            this.date = date;
    }

    public NotificationModel() {
    }
    /*public NotificationModel(int day, int month, int hour,int minute, String title, String body) {
        this.day = day;
        this.month = month;
       // this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.body = body;
        this.title = title;

    }

    private int day;
    private int month;
    //private int year;
    private int hour;
    private int minute;


    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String title ;
    private String body;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    /*public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
*/

    /*public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

*/

}
