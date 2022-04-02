package com.example.myegineerapplication.accounts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.CompanyModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class Prices extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText price_et;
    RadioButton gym_type_btn, fitness_type_btn, open_btn, four_btn, eight_btn, ten_btn,normal_radio_btn, concessionary_radio_btn;
    RadioGroup type_radio_group, duration_radio_group,radio_group_gym_membership_card_type;
    Button submit_gym_offer , add_service_btn;
    Map<String,String> payments = new HashMap<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        System.out.println("GYM REF: " + getDocumentPath());
        findView();

        type_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.price_gym_radio_btn){
                    payments.put("type","Gym");
                }

                if(checkedId == R.id.price_fitness_radio_btn){
                    payments.put("type","Fitness");
                }
            }
        });

        duration_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.price_open_radio_btn){
                    payments.put("duration" , "Open");
                }
                if(checkedId == R.id.price_four_radio_btn){
                    payments.put("duration", "4");
                }
                if(checkedId == R.id.price_eight_radio_btn){
                    payments.put("duration" , "8") ;
                }
                if (checkedId == R.id.price_ten_btn){
                    payments.put("duration", "10");
                }
            }
        });

        radio_group_gym_membership_card_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.price_gym_membership_card_type_normal){
                    payments.put("gym_membership_card_type", "normal");
                }
                if(checkedId == R.id.price_gym_membership_card_type_concessionary){
                    payments.put("gym_membership_card_type", "concessionary"); //ulgowy
                }
            }
        });
        submit_gym_offer.setOnClickListener(this);
        add_service_btn.setOnClickListener(this);
    }
    private void findView(){

        type_radio_group = findViewById(R.id.radio_group_type);
        duration_radio_group = findViewById(R.id.radio_group_duration);
        radio_group_gym_membership_card_type = findViewById(R.id.radio_group_gym_membership_card_type);

        price_et  = findViewById(R.id.service_price_et);
        gym_type_btn = findViewById(R.id.price_gym_radio_btn);
        fitness_type_btn = findViewById(R.id.price_fitness_radio_btn);
        open_btn = findViewById(R.id.price_open_radio_btn);
        four_btn = findViewById(R.id.price_four_radio_btn);
        eight_btn = findViewById(R.id.price_eight_radio_btn);
        ten_btn = findViewById(R.id.price_ten_btn);

        normal_radio_btn = findViewById(R.id.price_gym_membership_card_type_normal);
        concessionary_radio_btn = findViewById(R.id.price_gym_membership_card_type_concessionary);

        add_service_btn = findViewById(R.id.add_service_btn);
        submit_gym_offer = findViewById(R.id.submit_prices);
    }

   private String getDocumentPath(){
       Intent intent = getIntent();
       return intent.getStringExtra("gymRef");
   }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.add_service_btn){
            if (payments.isEmpty()){
                Toast.makeText(Prices.this, "You have nothing to add .",
                        Toast.LENGTH_SHORT).show();
            } else {
                payments.put("price", price_et.getText().toString().trim());
                DocumentReference reference = db.collection("Companies").document(getDocumentPath())
                        .collection("Payments").document();
//                DocumentReference reference = db.collection("Companies").document(getDocumentPath())
//                        .collection("Payments").document();
                reference.set(payments);

                gym_type_btn.setChecked(false);
                fitness_type_btn.setChecked(false);
                open_btn.setChecked(false);
                four_btn.setChecked(false);
                eight_btn.setChecked(false);
                ten_btn.setChecked(false);
                concessionary_radio_btn.setChecked(false);
                normal_radio_btn.setChecked(false);

                price_et.setText("");
            }

        }
        if (v.getId() == R.id.submit_prices){
            Intent i = new Intent(this, SelectablePanel.class);
            startActivity(i);
            Toast.makeText(this, "Profile created ", Toast.LENGTH_SHORT).show();
        }

    }

}
