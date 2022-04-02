package com.example.myegineerapplication.company_panel;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.CompanyModel;
import com.example.myegineerapplication.model.FitnessModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class UpdateFitness extends AppCompatActivity implements View.OnClickListener   {

    private static final String TAG = "Fitness";
    Button add_fitness;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private UpdateFitnessAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fitness);
        findView();
        getDocNameFromPanel();
        add_fitness.setOnClickListener(this);
        getDocName();
    }
    private String getDocNameFromPanel(){
        Intent intent = getIntent();
        System.out.println("Gym doc name " + intent.getStringExtra("doc"));
        return  intent.getStringExtra("doc");
    }

    private void findView(){
        add_fitness = findViewById(R.id.add_fitness);
        recyclerView = findViewById(R.id.rv_update);
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
                               CompanyModel companyModel  = document.toObject(CompanyModel.class);
                               System.out.println("My doc name from UpdateFitness class : " +companyModel.getName()  );
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void runAdapter(){
        CollectionReference reference_to_fitness = db.collection("Companies")
                .document(getDocNameFromPanel()).collection("Fitness");
        Query query = reference_to_fitness.orderBy("fitnessName",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<FitnessModel> options = new FirestoreRecyclerOptions.Builder<FitnessModel>()
                .setQuery(query, FitnessModel.class).build();
        options.getSnapshots();
        adapter = new UpdateFitnessAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        runAdapter();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onClick(View v) {
            if( v.getId() == R.id.add_fitness){
                Intent intentUpdate = new Intent(UpdateFitness.this, UpdateFitnessClasses.class);
                intentUpdate.putExtra("id_gym", getDocNameFromPanel());
                startActivity(intentUpdate);
            }
        }

}


