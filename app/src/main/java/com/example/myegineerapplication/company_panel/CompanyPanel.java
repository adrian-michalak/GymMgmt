package com.example.myegineerapplication.company_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.accounts.ui.SelectablePanel;
import com.example.myegineerapplication.model.CompanyModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompanyPanel extends AppCompatActivity implements View.OnClickListener {
    LinearLayout general_info, fitness_info ,payment_members_info,members_ll_button, sign_out_ll_button;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionCompany = db.collection("Companies");
    CompanyModel companyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_panel);
        findView();
        Log.d("company model", getIdFromGymLogin());
        general_info.setOnClickListener(this);
        fitness_info.setOnClickListener(this);
        payment_members_info.setOnClickListener(this);
        members_ll_button.setOnClickListener(this);
        sign_out_ll_button.setOnClickListener(this);
    }
    private void findView(){
        general_info = findViewById(R.id.general_information_btn);
        fitness_info = findViewById(R.id.fitness_update_ll_button);
        payment_members_info = findViewById(R.id.payment_members_info_ll_button);
        members_ll_button = findViewById(R.id.members_ll_button);
        sign_out_ll_button = findViewById(R.id.sign_out_ll_button);
    }
    private String getIdFromGymLogin(){
        Intent intent = getIntent();
        String email =  intent.getStringExtra("email_gym");
        Pattern pattern = Pattern.compile(email);
        Matcher matcher = pattern.matcher(email);
        email.split("gym_");
        // See the UserRecord reference doc for the contents of userRecord.
        System.out.println("Split user email: " + Arrays.toString(email.split("gym_")));
        System.out.println("Gym id " + intent.getStringExtra("email_gym"));
        return  intent.getStringExtra("email_gym");
    }


private void retrieveGymId(){
    db.collection("Companies")
            .whereEqualTo("email", getIdFromGymLogin())
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            companyModel  = document.toObject(CompanyModel.class);
                            Intent intent = new Intent(getApplicationContext(), UpdateFitness.class);
                            intent.putExtra("doc", companyModel.getId());
                            startActivity(intent);
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                }
            });
}
    private void retrieveGymIdMember(){
        db.collection("Companies")
                .whereEqualTo("email", getIdFromGymLogin())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                companyModel  = document.toObject(CompanyModel.class);
                                Intent intent = new Intent(getApplicationContext(), FitnessDayDivision.class);
                                intent.putExtra("id_gym", companyModel.getId());
                                startActivity(intent);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void retrieveGymIdSubmitPayment(){
        db.collection("Companies")
                .whereEqualTo("email", getIdFromGymLogin())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                companyModel  = document.toObject(CompanyModel.class);
                                Intent intent = new Intent(getApplicationContext(), SubmitPaymentMember.class);
                                intent.putExtra("id_gym", companyModel.getId());
                                startActivity(intent);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void signOut() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Toast.makeText(this,"Wylogowano", Toast.LENGTH_SHORT ).show();
        Intent intent = new Intent(this, SelectablePanel.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.general_information_btn:
                Intent general = new Intent(CompanyPanel.this, UpdateMainInformation.class);
                general.putExtra("email_gym", getIdFromGymLogin());
                startActivity(general);
                break;
            case  R.id.fitness_update_ll_button:
                retrieveGymId();
                break;
            case R.id.payment_members_info_ll_button:
                retrieveGymIdSubmitPayment();
                break;
            case R.id.members_ll_button:
                retrieveGymIdMember();
                break;
            case  R.id.sign_out_ll_button:
                signOut();

        }
    }
}
