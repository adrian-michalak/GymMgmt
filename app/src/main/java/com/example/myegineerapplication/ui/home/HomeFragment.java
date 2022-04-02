package com.example.myegineerapplication.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myegineerapplication.DetailsCompany.DetailsActivity;
import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.CompanyModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private  static final String NAME = "name";
    private  static final String PRICE = "price";
    private  static final String CITY = "city";
    private RecyclerView recyclerView;

    private final static  String TAG = "HomeViewModel";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionCompany = db.collection("Companies");
    private HomeFragmentAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_home, container, false);
         findView(root);
             //Toast.makeText(getContext(),"Downloading...",Toast.LENGTH_SHORT).show();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//fetching all data from firestore
    private void fetchData() {
        Query query = collectionCompany.orderBy("price",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<CompanyModel> options = new FirestoreRecyclerOptions.Builder<CompanyModel>()
                .setQuery(query,CompanyModel.class).build();
        options.getSnapshots();
        adapter = new HomeFragmentAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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



    private void findView( View view){
        recyclerView = view.findViewById(R.id.home_rv);
      //  toolbar = view.findViewById(R.id.main_toolbar);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.choose_btn){
            Intent intent = new Intent(getContext(),DetailsActivity.class);
            startActivity(intent);
        }

    }
}