package com.example.myegineerapplication.DetailsCompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.CompanyModel;
import com.example.myegineerapplication.model.PostModel;
import com.example.myegineerapplication.ui.home.HomeFragmentAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Posts extends AppCompatActivity implements View.OnClickListener{
    private static final String DOCUMENT ="gymName" ;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collection = db.collection("Posts");

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        Button call_post_activity_btn = findViewById(R.id.intent_add_post_activity);
        call_post_activity_btn.setOnClickListener(this);
        recyclerView = findViewById(R.id.rv_post_container);

       // fetchData();
    }
    private String getDocumentPath(){
        Intent intent = getIntent();
        return intent.getStringExtra("gym_id");
    }

    //fetching specify data from firestore
    private void fetchData() {
        Query query = collection.orderBy("date",Query.Direction.DESCENDING).whereEqualTo("gymName", getDocumentPath());
        System.out.println("GYM NAME: "+ getDocumentPath());

        //Query query = collection;
        FirestoreRecyclerOptions<PostModel> options = new FirestoreRecyclerOptions.Builder<PostModel>()
                .setQuery(query,PostModel.class).build();
        options.getSnapshots();

        adapter = new PostAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
       // loadData();
        fetchData();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void loadData(){
        collection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(QuerySnapshot documentSnapshot) {
                for (QueryDocumentSnapshot queryDocumentSnapshot: documentSnapshot){
                    PostModel postModel = queryDocumentSnapshot.toObject(PostModel.class);
                    Query query = collection.whereEqualTo("gymName", getDocumentPath());

                    //postModel.setDocumentId(queryDocumentSnapshot.getId());
                    String uid = postModel.getUid();
                    String body = Objects.requireNonNull(postModel).getBody();
                    String date = postModel.getDate();
                    String gymName = postModel.getGymName();
                    System.out.println("\nPOST"+" "+uid +" "+body+" "+date+" "+gymName);
                }

            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.intent_add_post_activity){
            Intent addPostActivity = new Intent(this, AddPost.class);
            addPostActivity.putExtra("gym_id", getDocumentPath());
            startActivity(addPostActivity);
        }
    }
}
