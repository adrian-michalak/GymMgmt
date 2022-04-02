package com.example.myegineerapplication.DetailsCompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.Globals;
import com.example.myegineerapplication.model.FitnessUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SubmitFitnessRecord extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FitnessUser user;
    TextView day_tv, user_name_tv, name_activity_tv, hour_tv, id_tv, email_tv;
    Button submit_fitness_record;
    private String user_name, name_activity, day, hour, id, email;
    Globals config = new Globals();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_fitness_record);
        findView();
        setStrings();


    }

    private void findView() {
        submit_fitness_record = findViewById(R.id.submit_fitness_record);
        day_tv = findViewById(R.id.record_day);
        user_name_tv = findViewById(R.id.record_user_name);
        name_activity_tv = findViewById(R.id.record_name_activity);
        hour_tv = findViewById(R.id.record_hour);
        id_tv = findViewById(R.id.id);
        email_tv = findViewById(R.id.record_email);
    }

    private void setStrings() {
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        name_activity = intent.getStringExtra("name_activity");
        day = intent.getStringExtra("day");
        hour = intent.getStringExtra("hour");
        id = intent.getStringExtra("id");
        email = intent.getStringExtra("email");

        user = new FitnessUser(id, user_name, name_activity, day, hour, email);

        day_tv.setText(day);
        user_name_tv.setText(user_name);
        name_activity_tv.setText(name_activity);
        hour_tv.setText(hour);
        id_tv.setText(id);
        email_tv.setText(email);
    }

    private String getSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("gym_id", MODE_PRIVATE);
        String gym_id = preferences.getString("gym_id_value", "");
        Log.d("RECORD_GYM_ID", gym_id);
        return gym_id;
    }

    private void addFitnessRecords() {
        Log.d("RECORD_info", user.getId() + " = " + user.getActivity_name());

        db.collection("Companies/" + getSharedPreferences() + "/FitnessUsers").document(config.translate(user.getDay())).collection(Globals.members)
                .document(user.getId()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed, please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void checkData(){
        db.collection("Companies/" + getSharedPreferences() + "/FitnessUsers")
            .document(config.translate(user.getDay())).collection(Globals.members)      //user.getActivity_name()
           .document(user.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("ShowToast")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if( task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if( documentSnapshot.exists()){
                        String uid = documentSnapshot.get("id").toString();
                        if (user.getId().equals(uid)) {
                            submit_fitness_record.setAlpha(.5f);
                            submit_fitness_record.setClickable(false);
                            Log.d("note: ","Sorry but this function is not available for you" );
                            Toast.makeText(getApplication(), "Przepraszamy, nie możesz dokonac rezerwacji", Toast.LENGTH_LONG);
                        }
                    }else {
                        submit_fitness_record.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addFitnessRecords();
                                submit_fitness_record.setAlpha(.5f);
                                submit_fitness_record.setClickable(false);
                            }
                        });
                    }


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("note: ",e.getMessage() );
            }
        });
    }


        @Override
        protected void onStart() {
            super.onStart();
            checkData();
        }



}