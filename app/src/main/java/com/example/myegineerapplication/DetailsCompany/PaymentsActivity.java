package com.example.myegineerapplication.DetailsCompany;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.braintreepayments.cardform.view.CardForm;
import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.PayModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PaymentsActivity extends AppCompatActivity{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView gym_concessionary_rv, gym_normal_rv,fitness_concessionary_rv,fitness_normal_rv;
    private PayAdapter gym_adapter_concessionary, gym_adapter_normal,fitness_adapter_concessionary,fitness_adapter_normal;
    CardForm cardForm;
    Button show_gym_payment, show_fitness_payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        passDocPath();
        gym_concessionary_rv = findViewById(R.id.gym_concessionary_rv);
        gym_normal_rv = findViewById(R.id.gym_normal_rv);
        fitness_concessionary_rv = findViewById(R.id.fitness_concessionary_rv);
        fitness_normal_rv = findViewById(R.id.fitness_normal_rv);

    }

    private String getDocumentPath(){
        Intent intent = getIntent();
        return intent.getStringExtra("gym_id");
    }
    private void passDocPath(){
        SharedPreferences preferences = getSharedPreferences("gym_id", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("gym_id_value", getDocumentPath());
        editor.apply();
    }

    private void fetchConcessionaryDataGym(){
        CollectionReference collection = db.collection("Companies/"+getDocumentPath()+"/Payments");
        Query query = collection.whereEqualTo("gym_membership_card_type","concessionary").whereEqualTo("type", "Gym");

        FirestoreRecyclerOptions<PayModel> options = new FirestoreRecyclerOptions.Builder<PayModel>()
                .setQuery(query, PayModel.class).build();
        options.getSnapshots();
        gym_adapter_concessionary = new PayAdapter(options);
        gym_concessionary_rv.setHasFixedSize(true);
        gym_concessionary_rv.setLayoutManager(new LinearLayoutManager(this));
        gym_concessionary_rv.setAdapter(gym_adapter_concessionary);
    }
    private void fetchNormalDataGym(){
        CollectionReference collection = db.collection("Companies/"+getDocumentPath()+"/Payments");
        Query query = collection.whereEqualTo("gym_membership_card_type","normal").whereEqualTo("type", "Gym");
        FirestoreRecyclerOptions<PayModel> options = new FirestoreRecyclerOptions.Builder<PayModel>()
                .setQuery(query, PayModel.class).build();
        options.getSnapshots();
        gym_adapter_normal = new PayAdapter(options);
        gym_normal_rv.setAdapter(gym_adapter_normal);
        gym_normal_rv.setHasFixedSize(true);
        gym_normal_rv.setLayoutManager(new LinearLayoutManager(this));
    }
    private void fetchConcessionaryDataFitness(){
        CollectionReference collection = db.collection("Companies/"+getDocumentPath()+"/Payments");
        Query query = collection.whereEqualTo("gym_membership_card_type","concessionary").whereEqualTo("type", "Fitness");

        FirestoreRecyclerOptions<PayModel> options = new FirestoreRecyclerOptions.Builder<PayModel>()
                .setQuery(query, PayModel.class).build();
        options.getSnapshots();
        fitness_adapter_concessionary = new PayAdapter(options);
        fitness_concessionary_rv.setHasFixedSize(true);
        fitness_concessionary_rv.setLayoutManager(new LinearLayoutManager(this));
        fitness_concessionary_rv.setAdapter(fitness_adapter_concessionary);
    }
    private void fetchNormalDataFitness(){
        CollectionReference collection = db.collection("Companies/"+getDocumentPath()+"/Payments");
        Query query = collection.whereEqualTo("gym_membership_card_type","normal").whereEqualTo("type", "Fitness");
        FirestoreRecyclerOptions<PayModel> options = new FirestoreRecyclerOptions.Builder<PayModel>()
                .setQuery(query, PayModel.class).build();
        options.getSnapshots();
        fitness_adapter_normal = new PayAdapter(options);
        fitness_normal_rv.setAdapter(fitness_adapter_normal);
        fitness_normal_rv.setHasFixedSize(true);
        fitness_normal_rv.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void onStart() {
        super.onStart();
        fetchNormalDataGym();
        fetchConcessionaryDataGym();
        fetchConcessionaryDataFitness();
        fetchNormalDataFitness();
        gym_adapter_normal.startListening();
        gym_adapter_concessionary.startListening();
        fitness_adapter_concessionary.startListening();
        fitness_adapter_normal.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        gym_adapter_concessionary.stopListening();
        gym_adapter_normal.stopListening();
        fitness_adapter_normal.stopListening();
        fitness_adapter_concessionary.stopListening();
    }

}