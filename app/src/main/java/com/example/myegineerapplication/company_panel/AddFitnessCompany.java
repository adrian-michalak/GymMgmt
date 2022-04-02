package com.example.myegineerapplication.company_panel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.FitnessModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddFitnessCompany extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Calendar calendar = Calendar.getInstance();
    TimePicker timePicker_from,timePicker_to;
    Button add_btn, save_btn;
    EditText fitness_name_et, training_day_et, fitness_price_et, fitness_desc_et;

    Map<String, Object> fitness = new HashMap<>();
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fitness__company);

        findView();
        System.out.println("AddFitness_Company : "+ getDocumentPath());
        getDocumentPath();
        add_btn.setOnClickListener(this);
        save_btn.setOnClickListener(this);
    }

    private void findView(){
        add_btn = findViewById(R.id.add_fitness_button);
        fitness_name_et = findViewById(R.id.fintess_name_et);
        training_day_et = findViewById(R.id.training_day_name_et);
        fitness_price_et = findViewById(R.id.fitness_leader_name_et);
        fitness_desc_et = findViewById(R.id.fitness_brief_description);

        timePicker_from = findViewById(R.id.time_picker_from);
        timePicker_to = findViewById(R.id.time_picker_to);

        timePicker_from.setIs24HourView(true);
        timePicker_to.setIs24HourView(true);

    }
    private String getDocumentPath(){
        Intent intent = getIntent();
        return intent.getStringExtra("document");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String timeFormat(TimePicker timePicker){
        Date time  ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" HH:mm", Locale.getDefault());
        int h = timePicker.getHour();
        int m = timePicker.getMinute();
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND, 0);
        time = calendar.getTime();
        return  simpleDateFormat.format(time);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addFitness(String s) {

        String fitnessName = fitness_name_et.getText().toString().trim();
        String fitnessDay = training_day_et.getText().toString().trim();
        String fitnessDesc = fitness_desc_et.getText().toString().trim();
        String fitnessHour = timeFormat(timePicker_from) +" - "+ timeFormat(timePicker_to);

        list.add(fitnessName);
        DocumentReference reference = db.collection("Companies").document(s).collection("Fitness").document();

        FitnessModel fitnessModel = new FitnessModel(fitnessName,"0",fitnessDesc,fitnessDay, fitnessHour);

        reference.set(fitnessModel, SetOptions.merge());
        fitness.put("fitness", list);

        db.collection("Companies").document(s).set(fitness,SetOptions.merge());
        fitness_name_et.setText("");
        training_day_et.setText("");
        fitness_desc_et.setText("");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_fitness_button) {
            addFitness(getDocumentPath());
        }
    }
}
