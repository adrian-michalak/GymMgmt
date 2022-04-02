package com.example.myegineerapplication.company_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.Globals;
import com.example.myegineerapplication.model.FitnessMemberModel;
import com.example.myegineerapplication.model.FitnessUser;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class FitnessMembersManagement extends AppCompatActivity {
    private static final String TAG = "FitnessMembers";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private FitnessMembersManagementAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDocNameFromPanel();
        getDocName();

        setContentView(R.layout.activity_fitness_members_management);
    }
    private void findView(){
        recyclerView = findViewById(R.id.fitness_member_rv);
    }
    private String getDocNameFromPanel(){
        Intent intent = getIntent();
        System.out.println("Gym doc name " + intent.getStringExtra("id_gym"));
        return  intent.getStringExtra("id_gym");
    }
    private void getDocName() {
        Log.d(TAG, "CHECK => in getDocName " );
        db.collection("Companies")
                .whereEqualTo("id", getDocNameFromPanel())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                FitnessMemberModel memberModel  = document.toObject(FitnessMemberModel.class);
                                System.out.println("My doc name from UpdateFitness class : " +memberModel.getName()  );
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void runAdapter(){
        Intent intent = getIntent();
        String day =  intent.getStringExtra("day_of_week");
        CollectionReference reference_to_fitness = db.collection("Companies")
                .document(getDocNameFromPanel()).collection("FitnessUsers").document(day).collection(Globals.members);
        Query query = reference_to_fitness.orderBy("activity_name",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<FitnessUser> options = new FirestoreRecyclerOptions.Builder<FitnessUser>()
                .setQuery(query, FitnessUser.class).build();
        options.getSnapshots();
        adapter = new FitnessMembersManagementAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        findView();
        runAdapter();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}