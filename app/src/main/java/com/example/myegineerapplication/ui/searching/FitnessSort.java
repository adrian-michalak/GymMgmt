package com.example.myegineerapplication.ui.searching;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.CompanyModel;
import com.example.myegineerapplication.ui.home.HomeFragmentAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FitnessSort extends Fragment {

    public static FitnessSort newInstance() {
        FitnessSort fragment = new FitnessSort();
        return fragment;
    }
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionCompany = db.collection("Companies");
    private HomeFragmentAdapter adapter;
    private RecyclerView recyclerView;
    private Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.display_sorted_items_fitness, container, false);
        System.out.println("fitness search "+ getBundleFitnessName());
        findView(root);
        retrieveDataOnSpecifyFitness();
        return root;
    }

    private void findView(View view){
        recyclerView = view.findViewById(R.id.rv_display_sorted_items_fitness);
    }
    private String getBundleFitnessName(){
        bundle = getArguments();
        String bundleString = bundle.getString("fitnessSearch");
        return bundleString;
    }

    private void  retrieveDataOnSpecifyFitness(){
        System.out.println("BUNDLE FITNESS SORT: "+ getBundleFitnessName());
        CollectionReference collection = db.collection("Companies");
        Query query = collection.whereArrayContains("fitness", getBundleFitnessName());
        FirestoreRecyclerOptions<CompanyModel> options = new FirestoreRecyclerOptions.Builder<CompanyModel>()
                .setQuery(query, CompanyModel.class).build();
        options.getSnapshots();
        adapter = new HomeFragmentAdapter(options);
        adapter.startListening();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
