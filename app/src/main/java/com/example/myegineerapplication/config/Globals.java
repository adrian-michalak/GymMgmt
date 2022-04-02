package com.example.myegineerapplication.config;

import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Globals {
    public static final String MEMBER = "Members";
    //================================================================================
    public static final String VALIDATION = "Sprawdź poprawność wprowadzonych danych";
    public static final String PASSWORD = "Hasło nie może zawierać mniej niż 6 znaków";
    public static final String ENTER_PASSWORD = "Wprowadź hasło";
    public static final String NOTIFICATION = "Pole wymagane";
   //========================================================================
    public static final String MONDAY = "Poniedziałek";
    public static final String TUESDAY = "Wtorek";
    public static final String WEDNESDAY = "Środa";
    public static final String THURSDAY = "Czwartek";
    public static final String FRIDAY = "Piątek";
    public static final String SATURDAY = "Sobota";
    public static final String SUNDAY = "Niedziela";

    public static String uniqueID ;
    public static final String members = "FitnessMembers" ; // name FitnessUsers collection
    public static String DATE;
    //========================================================================

    public String translate(String s){
        if (s.trim().equals(MONDAY)){ s = "MONDAY"; }
        if (s.trim().equals(TUESDAY)){ s = "TUESDAY"; }
        if (s.trim().equals(WEDNESDAY)){ s = "WEDNESDAY"; }
        if (s.trim().equals(THURSDAY)){ s = "THURSDAY"; }
        if (s.trim().equals(FRIDAY)){ s = "FRIDAY"; }
        if (s.trim().equals(SATURDAY)){ s = "SATURDAY"; }
        if (s.trim().equals(SUNDAY)){ s = "SUNDAY"; }
        Log.d("weekday: ", s);
        return s;
    }
    //==================================================================
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String timeFormat(TimePicker timePicker){
         Calendar calendar = Calendar.getInstance();
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
}
//    public  static final String COMPANY_NAME = "name";
//    public  static final String EMAIL = "email";
//    public  static final String PHONE = "phone";
//    public  static final String CITY = "city";
//    public  static final String STREET = "street";
//    public  static final String PRICE = "price";
//    public  static final String NUMBER = "number";
//    public  static final String DESC = "description";
//    public    static final String NIP = "nip";
//    public  static final String CLIENT_ID = "ID";