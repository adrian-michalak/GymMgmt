package com.example.myegineerapplication.company_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.FirebaseInit;
import com.example.myegineerapplication.model.CompanyModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateMainInformation extends AppCompatActivity implements View.OnClickListener {
    private static final int GET_FROM_GALLERY_MULTIPLE = 1 ;
    private EditText brief_desc_et , phone_et, city_et, street_et, price_et, number_et;
    private TextView nip_tv, name_tv, email_tv;
    private String id;
    private static final String notification = "Field required";

    // Access a Cloud Firestore instance from your Activity
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //FirebaseAuth auth = FirebaseAuth.getInstance();


    //private CollectionReference collectionCompany = db.collection("Companies");

    private static final String TAG = "Company";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comapany_main_information);
        findView();
        System.out.println("id: "+ getIdFromGymLogin());
        getData();
    }
private void findView(){
    nip_tv = findViewById(R.id.nip_edit_text);
    email_tv = findViewById(R.id.email_edit_text);
    name_tv = findViewById(R.id.company_edit_text);
    phone_et =findViewById(R.id.phone_edit_text);
    brief_desc_et = findViewById(R.id.create_brief_description);
    city_et = findViewById(R.id.city_edit_text);
    street_et = findViewById(R.id.street_edit_text);
    price_et = findViewById(R.id.price_edit_text);
    number_et = findViewById(R.id.number_edit_text);
    Button update = findViewById(R.id.button_update);

    update.setOnClickListener(this);
}
    private void updateCompany(){
        final String nip = nip_tv.getText().toString().trim();
        final String email = email_tv.getText().toString().trim();
        final String name = name_tv.getText().toString().trim();
        final String phone = phone_et.getText().toString().trim();
        final String city = city_et.getText().toString().trim();
        final String street = street_et.getText().toString().trim();
        final int price = Integer.valueOf(price_et.getText().toString().trim());
        final String number = number_et.getText().toString().trim();
        final String brief_desc = brief_desc_et.getText().toString().trim();


       FirebaseInit.db.collection("Companies").document(id).update("email", email,
               "phone", phone, "city", city, "street", street, "price",
               price, "number", number, "brief_desc", brief_desc)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       Log.d(TAG, "DocumentSnapshot successfully updated!");
                       Toast.makeText(getApplicationContext(),"Profile updated", Toast.LENGTH_SHORT).show();
                   }
               }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Log.w(TAG, "Error updating document", e);
           }
       });
    }

    private String getIdFromGymLogin(){
        Intent intent = getIntent();
        String id =  intent.getStringExtra("email_gym");
        Log.d("id gym ", id);
        System.out.println("Successfully fetched user data: " + FirebaseInit.firebaseAuth.getUid());
        System.out.println("Successfully fetched user data: " + FirebaseInit.firebaseAuth.getCurrentUser().getEmail());
        System.out.println("Gym email " + intent.getStringExtra("email_gym"));
        return  intent.getStringExtra("email_gym");
    }
    private void getData(){

        FirebaseInit.db.collection("Companies")
                .whereEqualTo("email", getIdFromGymLogin())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CompanyModel companyModel  = document.toObject(CompanyModel.class);
                                id =  companyModel.getId();
                                name_tv.setText(companyModel.getName());
                                nip_tv.setText(companyModel.getNip());
                                email_tv.setText(companyModel.getEmail());
                                city_et.setText(companyModel.getCity());
                                street_et.setText(companyModel.getStreet());
                                number_et.setText(companyModel.getNumber());
                                phone_et.setText(companyModel.getPhone());
                                price_et.setText(companyModel.getPrice().toString());
                                brief_desc_et.setText(companyModel.getBrief_desc());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Intent intentNamePath = new Intent(UpdateMainInformation.this, UpdateFitness.class);
                                intentNamePath.putExtra("gym_name_path", id);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_update) {
            updateCompany();
        }
    }
}
