package com.example.myegineerapplication.company_panel;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.Globals;
import com.example.myegineerapplication.model.CompanyModel;
import com.example.myegineerapplication.model.FitnessModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class UpdateFitnessClasses extends AppCompatActivity implements View.OnClickListener {

    TimePicker update_timePicker_from,update_timePicker_to;
    Button update_add_btn, submit_update_fitness;
    EditText update_fitness_name_et, update_training_day_et, update_fitness_leader_et, update_fitness_desc_et;
    Map<String, Object> update_fitness = new HashMap<String, Object>();
    List<String> update_list = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CompanyModel companyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fitness_classes);
        findView();
        getIdFromGymLogin();
        //updateFitness();
    }
    private String getIdFromGymLogin(){
        Intent intent = getIntent();
        return intent.getStringExtra("id_gym");
    }

    private void findView(){
        update_add_btn = findViewById(R.id.update_add_fitness_button);
        submit_update_fitness = findViewById(R.id.update_submit_fitness_button);
        update_fitness_name_et = findViewById(R.id.update_fintess_name_et);
        update_training_day_et = findViewById(R.id.update_training_day_name_et);
        update_fitness_leader_et = findViewById(R.id.update_fitness_leader_name_et);
        update_fitness_desc_et = findViewById(R.id.update_fitness_brief_description);

        update_timePicker_from = findViewById(R.id.update_time_picker_from);
        update_timePicker_to = findViewById(R.id.update_time_picker_to);

        update_timePicker_from.setIs24HourView(true);
        update_timePicker_to.setIs24HourView(true);

        update_add_btn.setOnClickListener(this);
        submit_update_fitness.setOnClickListener(this);

    }

    private void updateFitness(){
        final Globals config = new Globals();
        db.collection("Companies")
                .whereEqualTo("id", getIdFromGymLogin())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                companyModel  = document.toObject(CompanyModel.class);
                                String fitnessName = update_fitness_name_et.getText().toString().trim();
                                String fitnessDay = update_training_day_et.getText().toString().trim();
                                String fitnessPrice = update_fitness_leader_et.getText().toString().trim();
                                String fitnessDesc = update_fitness_desc_et.getText().toString().trim();
                                String fitnessHour = config.timeFormat(update_timePicker_from) +" - "+ config.timeFormat(update_timePicker_to);

                                update_list.add(fitnessName);

                                DocumentReference reference = db.collection("Companies")
                                        .document(companyModel.getId()).collection("Fitness").document();

                                FitnessModel fitnessModel = new FitnessModel(fitnessName,fitnessPrice,fitnessDesc,fitnessDay, fitnessHour);
                                reference.set(fitnessModel, SetOptions.merge());
                                update_fitness_name_et.setText("");
                                update_training_day_et.setText("");
                                update_fitness_desc_et.setText("");
                                update_fitness_leader_et.setText("");

                                update_fitness.put("fitness", update_list);

                                 db.collection("Companies").document(companyModel.getId()).set(update_fitness,SetOptions.merge());
                                System.out.println("UpdateFitnessClasses object: \n "+companyModel.getName() +"\n"+companyModel.getEmail()+"\n"+companyModel.getCity());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update_add_fitness_button :
                updateFitness();
                break;
            case R.id.update_submit_fitness_button:
                super.onBackPressed();
        }
    }
}
