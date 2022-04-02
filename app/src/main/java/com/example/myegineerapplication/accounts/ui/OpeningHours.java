package com.example.myegineerapplication.accounts.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.CompanyModel;
import com.example.myegineerapplication.model.WeekModel;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class OpeningHours extends AppCompatActivity implements View.OnClickListener{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Calendar calendar = Calendar.getInstance();
    String m, t, w, th, f, sat, sun;

    TimePicker timePicker_monday_open,timePicker_monday_closed,timePicker_tuesday_open,timePicker_tuesday_close,
               timePicker_wednesday_open,timePicker_wednesday_close, timePicker_thursday_open, timePicker_thursday_close,
               timePicker_friday_open,timePicker_friday_close, timePicker_saturday_open,timePicker_saturday_close,
               timePicker_sunday_open,timePicker_sunday_close;
    CheckBox monday_chbox, tuesday_chbox, wednesday_chbox, thursday_chbox, friday_chbox, saturday_chbox, sunday_chbox;
    public static final String CLOSED = "Closed";
    Button submit_opening_hours;
    Map<String,String> hour = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_hours);

        findView();
        setTimePickerHourView();

    }
    private void setTimePickerHourView(){
        timePicker_monday_open.setIs24HourView(true);
        timePicker_monday_closed.setIs24HourView(true);
        timePicker_tuesday_open.setIs24HourView(true);
        timePicker_tuesday_close.setIs24HourView(true);
        timePicker_wednesday_open.setIs24HourView(true);
        timePicker_wednesday_close.setIs24HourView(true);
        timePicker_thursday_open.setIs24HourView(true);
        timePicker_thursday_close.setIs24HourView(true);
        timePicker_friday_open.setIs24HourView(true);
        timePicker_friday_close.setIs24HourView(true);
        timePicker_saturday_open.setIs24HourView(true);
        timePicker_saturday_close.setIs24HourView(true);
        timePicker_sunday_open.setIs24HourView(true);
        timePicker_sunday_close.setIs24HourView(true);
    }
    public void findView(){
        submit_opening_hours = findViewById(R.id.submit_opening_hours);

        monday_chbox = findViewById(R.id.checkbox_monday);
        tuesday_chbox = findViewById(R.id.checkbox_tuesday);
        wednesday_chbox = findViewById(R.id.checkbox_wednesday);
        thursday_chbox = findViewById(R.id.checkbox_thursday);
        friday_chbox = findViewById(R.id.checkbox_friday);
        saturday_chbox = findViewById(R.id.checkbox_saturday);
        sunday_chbox = findViewById(R.id.checkbox_sunday);


        timePicker_monday_open = findViewById(R.id.time_picker_monday_open);
        timePicker_monday_closed = findViewById(R.id.time_picker_monday_close);
        timePicker_tuesday_open = findViewById(R.id.time_picker_tuesday_open);
        timePicker_tuesday_close = findViewById(R.id.time_picker_tuesday_close);
        timePicker_wednesday_open = findViewById(R.id.time_picker_wednesday_open);
        timePicker_wednesday_close = findViewById(R.id.time_picker_wednesday_close);
        timePicker_thursday_open = findViewById(R.id.time_picker_thursday_open);
        timePicker_thursday_close = findViewById(R.id.time_picker_thursday_close);
        timePicker_friday_open  = findViewById(R.id.time_picker_friday_open);
        timePicker_friday_close = findViewById(R.id.time_picker_friday_close);
        timePicker_saturday_open = findViewById(R.id.time_picker_saturday_open);
        timePicker_saturday_close = findViewById(R.id.time_picker_saturday_close);
        timePicker_sunday_open = findViewById(R.id.time_picker_sunday_open);
        timePicker_sunday_close = findViewById(R.id.time_picker_sunday_close);

        submit_opening_hours.setOnClickListener(this);
    }

    private void initCheckBox(){
        monday_chbox.setChecked(true);
        tuesday_chbox.setChecked(true);
        wednesday_chbox.setChecked(true);
        thursday_chbox.setChecked(true);
        friday_chbox.setChecked(true);
        saturday_chbox.setChecked(true);
        sunday_chbox.setChecked(true);
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

    public void onCheckboxClicked( View view){
        switch (view.getId()){
            case R.id.checkbox_monday:
                if(monday_chbox.isChecked()){
                    timePicker_monday_open.setEnabled(true);
                    timePicker_monday_closed.setEnabled(true);

                } else {
                    timePicker_monday_open.setEnabled(false);
                    timePicker_monday_closed.setEnabled(false);

                }
            case  R.id.checkbox_tuesday:
                if(tuesday_chbox.isChecked()){
                    timePicker_tuesday_open.setEnabled(true);
                    timePicker_tuesday_close.setEnabled(true);
                } else {
                    timePicker_tuesday_open.setEnabled(false);
                    timePicker_tuesday_close.setEnabled(false);

                }
            case  R.id.checkbox_wednesday:
                if(wednesday_chbox.isChecked()){
                    timePicker_wednesday_open.setEnabled(true);
                    timePicker_wednesday_close.setEnabled(true);

                } else {
                    timePicker_wednesday_open.setEnabled(false);
                    timePicker_wednesday_close.setEnabled(false);

                }
            case  R.id.checkbox_thursday:
                if(thursday_chbox.isChecked()){
                    timePicker_thursday_open.setEnabled(true);
                    timePicker_thursday_close.setEnabled(true);
                } else {
                    timePicker_thursday_open.setEnabled(false);
                    timePicker_thursday_close.setEnabled(false);

                }
            case  R.id.checkbox_friday:
                if(friday_chbox.isChecked()){
                    timePicker_friday_open.setEnabled(true);
                    timePicker_friday_close.setEnabled(true);
                } else {
                    timePicker_friday_open.setEnabled(false);
                    timePicker_friday_close.setEnabled(false);

                }
            case  R.id.checkbox_saturday:
                if(saturday_chbox.isChecked()){
                    timePicker_saturday_open.setEnabled(true);
                    timePicker_saturday_close.setEnabled(true);
                } else {
                    timePicker_saturday_open.setEnabled(false);
                    timePicker_saturday_close.setEnabled(false);

                }
            case  R.id.checkbox_sunday:
                if(sunday_chbox.isChecked()){
                    timePicker_sunday_open.setEnabled(true);
                    timePicker_sunday_close.setEnabled(true);
                } else {
                    timePicker_sunday_open.setEnabled(false);
                    timePicker_sunday_close.setEnabled(false);

                }
        }
    }

    private String getDocumentPath(){
        Intent intent = getIntent();
        return intent.getStringExtra("gymRef");
    }
    private void addOpeningHoursToDatabase(String m,String t,String w,String th,String f,String sat,String sun ){
        System.out.println("gym ref: "+ getDocumentPath());
        WeekModel model = new WeekModel(m,t,w,th,f,sat,sun);
        DocumentReference reference = db.collection("Companies").document(getDocumentPath())
                        .collection("OpeningHours").document("hours");
                reference.set(model);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.submit_opening_hours) {
            if (monday_chbox.isChecked()) {
                m = timeFormat(timePicker_monday_open) + "-" + timeFormat(timePicker_monday_closed);
                hour.put("monday", timeFormat(timePicker_monday_open) + "-" + timeFormat(timePicker_monday_closed));
            } else {
                m = CLOSED;
                hour.put("monday", CLOSED);
            }
            if (tuesday_chbox.isChecked()) {
                t = timeFormat(timePicker_tuesday_open) + "-" + timeFormat(timePicker_tuesday_close);
                hour.put("tuesday", timeFormat(timePicker_tuesday_open) + "-" + timeFormat(timePicker_tuesday_close));
            } else {
                t = CLOSED;
                hour.put("tuesday", CLOSED);
            }
            if (wednesday_chbox.isChecked()) {
                w = timeFormat(timePicker_wednesday_open) + "-" + timeFormat(timePicker_wednesday_close);
                hour.put("wednesday", timeFormat(timePicker_wednesday_open) + "-" + timeFormat(timePicker_wednesday_close));
            } else {
                w = CLOSED;
                hour.put("wednesday", CLOSED);
            }
            if (thursday_chbox.isChecked()) {
                th = timeFormat(timePicker_thursday_open) + "-" + timeFormat(timePicker_thursday_close);
                hour.put("thursday", timeFormat(timePicker_thursday_open) + "-" + timeFormat(timePicker_thursday_close));
            } else {
                th = CLOSED;
                hour.put("thursday", CLOSED);
            }
            if (friday_chbox.isChecked()) {
                f = timeFormat(timePicker_friday_open) + "-" + timeFormat(timePicker_friday_close);
                hour.put("friday", timeFormat(timePicker_friday_open) + "-" + timeFormat(timePicker_friday_close));
            } else {
                f = CLOSED;
                hour.put("friday", CLOSED);
            }
            if (saturday_chbox.isChecked()) {
                sat = timeFormat(timePicker_saturday_open) + "-" + timeFormat(timePicker_saturday_close);
                hour.put("saturday", timeFormat(timePicker_saturday_open) + "-" + timeFormat(timePicker_saturday_close));
            } else {
                sat = CLOSED;
                hour.put("saturday", CLOSED);
            }
            if (sunday_chbox.isChecked()) {
                sun = timeFormat(timePicker_sunday_open) + "-" + timeFormat(timePicker_sunday_close);
                hour.put("sunday", timeFormat(timePicker_sunday_open) + "-" + timeFormat(timePicker_sunday_close));
            } else {
                sun = CLOSED;
                hour.put("sunday", CLOSED);
            }

            System.out.println("MONDAY  " + hour.get("monday"));
            System.out.println("TUESDAY " + hour.get("tuesday"));
            addOpeningHoursToDatabase(m, t, w, th, f, sat, sun);
            Intent intent = new Intent(this, Prices.class);
            intent.putExtra("gymRef", getDocumentPath());
            startActivity(intent);
        }

    }

}
