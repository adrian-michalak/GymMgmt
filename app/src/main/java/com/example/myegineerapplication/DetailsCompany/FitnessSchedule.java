package com.example.myegineerapplication.DetailsCompany;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.FitnessModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FitnessSchedule extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView recyclerView;
    private FitnessAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_schedule);
        sendDocumentPath();
        recyclerView = findViewById(R.id.rv_fitness_container);
    }

    private String getDocumentPath(){
        Intent intent = getIntent();
        Log.d("schedule: ",intent.getStringExtra("gym_id") );
        return intent.getStringExtra("gym_id");
    }
    private  void sendDocumentPath(){
       SharedPreferences preferences = getSharedPreferences("gym_id", MODE_PRIVATE);
       SharedPreferences.Editor editor = preferences.edit();
       editor.putString("gym_id_value", getDocumentPath());
       editor.apply();

    }
    private void fetchData() {
        CollectionReference collection = db.collection("Companies/"+getDocumentPath()+"/Fitness");
        Query query = collection.orderBy("fitnessName",Query.Direction.DESCENDING);
        System.out.println("GYM NAME: "+ getDocumentPath());

        FirestoreRecyclerOptions<FitnessModel> options = new FirestoreRecyclerOptions.Builder<FitnessModel>()
                .setQuery(query, FitnessModel.class).build();
        options.getSnapshots();

        adapter = new FitnessAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchData();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
