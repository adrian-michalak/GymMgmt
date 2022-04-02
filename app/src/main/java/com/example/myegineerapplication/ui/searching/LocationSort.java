package com.example.myegineerapplication.ui.searching;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.CompanyModel;
import com.example.myegineerapplication.ui.home.HomeFragmentAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LocationSort extends Fragment {

    public static LocationSort newInstance() {
        LocationSort fragment = new LocationSort();
        return fragment;
    }
    private ImageButton commit_search_location;
    private EditText search;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionCompany = db.collection("Companies");
    private HomeFragmentAdapter adapter;
    private RecyclerView recyclerView;
    private Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.display_sorted_items_location, container, false);

        findView(root);
        retrieveDataOnSpecifyLocation();
        return root;
    }


    private void findView(View view){
        commit_search_location = view.findViewById(R.id.commit_button_search);
        recyclerView = view.findViewById(R.id.rv_display_sorted_items_location);
        search = view.findViewById(R.id.search);
    }
 private String getBundleCityName(){
     bundle = getArguments();
     String bundleString = bundle.getString("cityName");
     return bundleString;
 }
   private void  retrieveDataOnSpecifyLocation(){
       Query query = collectionCompany.whereEqualTo("city", getBundleCityName());
       FirestoreRecyclerOptions<CompanyModel> options = new FirestoreRecyclerOptions.Builder<CompanyModel>()
               .setQuery(query,CompanyModel.class).build();
       options.getSnapshots();
        adapter = new HomeFragmentAdapter(options);
        adapter.startListening();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
   }



}
