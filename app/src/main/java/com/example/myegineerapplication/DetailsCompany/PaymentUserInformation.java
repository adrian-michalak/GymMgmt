package com.example.myegineerapplication.DetailsCompany;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.Globals;
import com.example.myegineerapplication.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class PaymentUserInformation extends AppCompatActivity implements View.OnClickListener {

    List<String> list = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView payment_user_tv, payment_id_tv,payment_email_tv,payment_price_tv,payment_duration_tv, payment_phone_tv,payment_user_gym_name_tv;
    Button submit_payment_user;

    private CalendarView calendarView;
    private final Calendar calendar = Calendar.getInstance();
    private Map<String,Object> MemberInfoList = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_user_information);

        findView();
        getSharedPreferences();
        setTextViewValue();
        getPaymentUserInfo();
        chooseDay();

        Log.d("TYPE+PAY", getMembershipCardType() +" "+ getType());

        submit_payment_user.setOnClickListener(this);
    }
    private void chooseDay(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                int m = month + 1;
                Globals.DATE = dayOfMonth +"/"+ m +"/"+ year;
                payment_id_tv.setText(dayOfMonth +"/"+ m +"/"+ year);
                Log.d("PAY DATE: ", dayOfMonth +" "+ m +" "+ year);
            }
        });
    }
    private String getSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("gym_id", MODE_PRIVATE);
        String gym_id = preferences.getString("gym_id_value", "");
        Log.d("RECORD_GYM_ID", gym_id);
        return gym_id;
    }
    private void findView(){
        calendarView = findViewById(R.id.payment_user_calendar);
        payment_user_gym_name_tv = findViewById(R.id.payment_user_gym_name);
        payment_id_tv = findViewById(R.id.payment_user_id);
        payment_user_tv = findViewById(R.id.payment_user_name);
        payment_email_tv = findViewById(R.id.payment_user_email);
        payment_phone_tv = findViewById(R.id.payment_user_phone);
        payment_duration_tv = findViewById(R.id.payment_user_duration);
        payment_price_tv = findViewById(R.id.payment_user_price);
        submit_payment_user = findViewById(R.id.submit_payment_user);
    }
    private String getPrice(){
        Intent intent = getIntent();
        return intent.getStringExtra("pay_price");
    }
    private String getDuration(){
        Intent intent = getIntent();
        return intent.getStringExtra("pay_duration");
    }
    private void getPaymentUserInfo(){
        Intent intent = getIntent();
        Log.d("user data:", user.getDisplayName() +"\n"+user.getEmail()+"\n"+user.getUid()+"\n"+payment_phone_tv.getText() );
        Log.d("user_pay_price: ",intent.getStringExtra("pay_price") );
        Log.d("user_pay_duration: ",intent.getStringExtra("pay_duration") );
    }
    private String getType(){
        Intent intent = getIntent();
        return intent.getStringExtra("pay_type");
    }
    private String getMembershipCardType(){
        Intent intent = getIntent();
        return intent.getStringExtra("gym_membership_card_type");
    }
    private void gymName(){
        DocumentReference reference = db.collection("Companies").document(getSharedPreferences());
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String name_gym =  documentSnapshot.get("name").toString();
                    String city =  documentSnapshot.get("city").toString();
                    String street =  documentSnapshot.get("street").toString();
                    String number = documentSnapshot.get("number").toString();
                    String full_address = name_gym +" ,"+ city +" ul. "+ street+" "+number;
                    payment_user_gym_name_tv.setText(full_address);
                }
            }
        });
    }
    private void setTextViewValue(){
        DocumentReference docRef = db.collection("Users").document(user.getEmail());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserModel userModel = documentSnapshot.toObject(UserModel.class);
                payment_phone_tv.setText(userModel.getPhone());
                MemberInfoList.put("phone", userModel.getPhone());
            }
        });
        payment_user_tv.setText(user.getDisplayName());
        payment_email_tv.setText(user.getEmail());
        payment_price_tv.setText(getPrice());
        payment_duration_tv.setText(getDuration());
        payment_user_gym_name_tv.setText(getSharedPreferences());
        gymName();

    }
    private void addDataToMap(){
        Log.d("date_from_tv", payment_id_tv.getText().toString());

        MemberInfoList.put("phone", payment_phone_tv.getText());
        MemberInfoList.put("uid", user.getUid());
        MemberInfoList.put("user_name", user.getDisplayName());
        MemberInfoList.put("email", user.getEmail());
        MemberInfoList.put("price", getPrice());
        MemberInfoList.put("entries",getDuration());
        MemberInfoList.put("date", payment_id_tv.getText().toString());
        MemberInfoList.put("type", getType());
        MemberInfoList.put("membership_card_type", getMembershipCardType());
        MemberInfoList.put("full_gym_address", payment_user_gym_name_tv.getText().toString());
    }

    private void addDataToFirestore(){
        addDataToMap();
        db.collection("Companies").document(getSharedPreferences()).collection(Globals.MEMBER).document(user.getUid()).set(MemberInfoList, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Dziękujemy, zgłoszenie zostało przyjete", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Przepraszamy, coś poszło nie tak", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit_payment_user){
            addDataToFirestore();
        }
    }
}