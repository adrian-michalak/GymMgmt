package com.example.myegineerapplication.model;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.List;

public class PostModel {

    private String documentId;
    private String uid;
    private String body;
    private String date;
    private String gymName;

    public PostModel(){}

    public PostModel( String uid, String body, String date, String gymName) {

        this.uid = uid;
        this.body = body;
        this.date = date;
        this.gymName = gymName;

    }



    //@Exclude


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }


   /* public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }*/
}
