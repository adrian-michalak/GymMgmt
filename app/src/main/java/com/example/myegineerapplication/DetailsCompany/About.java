package com.example.myegineerapplication.DetailsCompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.WeekModel;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class About extends AppCompatActivity {
    TextView address_tv, phone_tv, email_tv, desc_tv,
            monday_tv, tuesday_tv, wednesday_tv,thursday_tv, friday_tv, saturday_tv, sunday_tv;
    private static final String street_ = "ul. ";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findView();
        setAboutData();
        openingHours();
    }
    private void findView(){
         address_tv = findViewById(R.id.about_address_text_view);
         email_tv = findViewById(R.id.about_email_text_view);
         phone_tv = findViewById(R.id.about_phone_text_view);
         desc_tv = findViewById(R.id.about_brief_description);

        monday_tv = findViewById(R.id.monday);
        tuesday_tv = findViewById(R.id.tuesday);
        wednesday_tv = findViewById(R.id.wednesday);
        thursday_tv = findViewById(R.id.thursday);
        friday_tv = findViewById(R.id.friday);
        saturday_tv = findViewById(R.id.saturday);
        sunday_tv = findViewById(R.id.sunday);
 }
/*    private void setData(){
        Intent intent = getIntent();
        String street = intent.getStringExtra("street");
        String buildNumber = intent.getStringExtra("buildNumber");
        String city = intent.getStringExtra("city");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        String desc = intent.getStringExtra("desc");
        String gym_id = intent.getStringExtra("gym_id");
        address_tv.setText(String.format("%s %s %s %s",city,street_ ,street, buildNumber));
        phone_tv.setText(phone);
        email_tv.setText(email);
        desc_tv.setText(desc);
    }
    private void setOpeningHours(){
        Intent intent = getIntent();
        String monday = intent.getStringExtra("monday");
        String tuesday = intent.getStringExtra("tuesday");
        String wednesday = intent.getStringExtra("wednesday");
        String thursday = intent.getStringExtra("thursday");
        String friday = intent.getStringExtra("friday");
        String saturday = intent.getStringExtra("saturday");
        String sunday = intent.getStringExtra("sunday");
        System.out.println("monday: "+ monday);
        System.out.println("tuesday: "+ tuesday);
        monday_tv.setText(monday);
        tuesday_tv.setText(tuesday);
        wednesday_tv.setText(wednesday);
        thursday_tv.setText(thursday);
        friday_tv.setText(friday);
        saturday_tv.setText(saturday);
        sunday_tv.setText(sunday);
    }*/

    private String getDocumentPath(){
        Intent path = getIntent();
        return path.getStringExtra("gym_id");
    }

    private void setAboutData(){
        System.out.println("gym ref " + getDocumentPath());
        documentReference = db.collection("Companies").document(getDocumentPath()) ;
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                        String street = documentSnapshot.get("street").toString();
                        String buildNumber = documentSnapshot.get("number").toString();
                        String city = documentSnapshot.get("city").toString();
                        String phone = documentSnapshot.get("phone").toString();
                        String email = documentSnapshot.get("email").toString();
                        String desc = documentSnapshot.get("brief_desc").toString();
                        String gym_id = documentSnapshot.get("name").toString();

                        address_tv.setText(String.format("%s %s %s %s",city,street_ ,street, buildNumber));
                        phone_tv.setText(phone);
                        email_tv.setText(email);
                        desc_tv.setText(desc);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error", e.getMessage());
            }
        });

    }

    private void openingHours() {
        final CollectionReference collection = db.collection("Companies/" + getDocumentPath().trim() + "/OpeningHours");
        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        String monday =  queryDocumentSnapshot.get("monday").toString();
                        String tuesday =  queryDocumentSnapshot.get("tuesday").toString();
                        String wednesday = queryDocumentSnapshot.get("wednesday").toString();
                        String thursday = queryDocumentSnapshot.get("thursday").toString();
                        String friday = queryDocumentSnapshot.get("friday").toString();
                        String saturday = queryDocumentSnapshot.getString("saturday");
                        String sunday = queryDocumentSnapshot.getString("sunday");

                        monday_tv.setText(monday);
                        tuesday_tv.setText(tuesday);
                        wednesday_tv.setText(wednesday);
                        thursday_tv.setText(thursday);
                        friday_tv.setText(friday);
                        saturday_tv.setText(saturday);
                        sunday_tv.setText(sunday);
                        System.out.println(" QUERY DATA" + queryDocumentSnapshot.getData());
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error", e.getMessage());
            }
        });
    }
}
