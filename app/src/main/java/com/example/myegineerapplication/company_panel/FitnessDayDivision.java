package com.example.myegineerapplication.company_panel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.Globals;

public class FitnessDayDivision extends AppCompatActivity implements View.OnClickListener{
    private Button monday_btn, wednesday_btn, tuesday_btn, thursday_btn, friday_btn, saturday_btn, sunday_btn;
    Globals globals = new Globals();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_day_division);
        findView();
        initButtons();
    }


    private void findView(){
        monday_btn = findViewById(R.id.fitness_week_division_monday_btn);
        tuesday_btn = findViewById(R.id.fitness_week_division_tuesday_btn);
        wednesday_btn = findViewById(R.id.fitness_week_division_wednesday_btn);
        thursday_btn = findViewById(R.id.fitness_week_division_thursday_btn);
        friday_btn = findViewById(R.id.fitness_week_division_friday_btn);
        saturday_btn = findViewById(R.id.fitness_week_division_saturday_btn);
        sunday_btn = findViewById(R.id.fitness_week_division_sunday_btn);
    }
    private void initButtons(){
        monday_btn.setOnClickListener(this);
        tuesday_btn.setOnClickListener(this);
        wednesday_btn.setOnClickListener(this);
        thursday_btn.setOnClickListener(this);
        friday_btn.setOnClickListener(this);
        saturday_btn.setOnClickListener(this);
        sunday_btn.setOnClickListener(this);
    }
    private String getDocNameFromPanel(){
        Intent intent = getIntent();
        System.out.println("Gym doc name " + intent.getStringExtra("id_gym"));
        return  intent.getStringExtra("id_gym");
    }
    private void intentId(){
        Intent intent = new Intent(getApplicationContext(), FitnessMembersManagement.class);
        intent.putExtra("id_gym", getDocNameFromPanel());
        finish();
    }
    private void intentDayOfWeek(String s){
        Intent intent = new Intent(getApplicationContext(), FitnessMembersManagement.class);
        intent.putExtra("day_of_week", s);
        intent.putExtra("id_gym", getDocNameFromPanel());
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fitness_week_division_monday_btn: intentDayOfWeek(globals.translate(Globals.MONDAY)); break;

            case R.id.fitness_week_division_tuesday_btn: intentDayOfWeek(globals.translate(Globals.TUESDAY)); break;

            case R.id.fitness_week_division_wednesday_btn:intentDayOfWeek(globals.translate(Globals.WEDNESDAY)); break;

            case R.id.fitness_week_division_thursday_btn:intentDayOfWeek(globals.translate(Globals.THURSDAY)); break;

            case R.id.fitness_week_division_friday_btn:intentDayOfWeek(globals.translate(Globals.FRIDAY)); break;

            case R.id.fitness_week_division_saturday_btn:intentDayOfWeek(globals.translate(Globals.SATURDAY)); break;

            case R.id.fitness_week_division_sunday_btn:intentDayOfWeek(globals.translate(Globals.SUNDAY)); break;

        }
    }
}