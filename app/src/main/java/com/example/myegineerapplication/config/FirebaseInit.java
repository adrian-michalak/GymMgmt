package com.example.myegineerapplication.config;

import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseInit {
    public static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static  FirebaseUser user;
    public static final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://myegineerapplication.appspot.com");
    public static  DatabaseReference mDatabaseRef ;

}
