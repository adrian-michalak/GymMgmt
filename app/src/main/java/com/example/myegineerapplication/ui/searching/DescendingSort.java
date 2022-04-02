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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DescendingSort extends Fragment {

    public static DescendingSort newInstance() {
        DescendingSort fragment = new DescendingSort();
        return fragment;
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionCompany = db.collection("Companies");
    private HomeFragmentAdapter adapter;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.display_sorted_items_desc, container, false);

        findView(root);
        setDescendingFilter();
        return  root;
    }

    private void findView(View view){
        recyclerView = view.findViewById(R.id.rv_display_sorted_items_desc);
    }

    private void setDescendingFilter(){

        Query query = collectionCompany.orderBy("price",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<CompanyModel> options = new FirestoreRecyclerOptions.Builder<CompanyModel>()
                .setQuery(query,CompanyModel.class).build();
        options.getSnapshots();
        adapter = new HomeFragmentAdapter(options);
        adapter.startListening();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        setDescendingFilter();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
