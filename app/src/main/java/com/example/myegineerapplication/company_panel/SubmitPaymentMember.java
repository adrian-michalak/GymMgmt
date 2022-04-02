package com.example.myegineerapplication.company_panel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.Globals;
import com.example.myegineerapplication.model.PaymentMemberModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SubmitPaymentMember extends AppCompatActivity {
    RecyclerView submit_payment_member_rv;
    SubmitPaymentMemberAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_payment_member);
        findView();
        fetchData();
    }
    
    private void findView(){
        submit_payment_member_rv = findViewById(R.id.submit_payment_member_rv);
    }
    private String getDocumentPath(){
        Intent intent = getIntent();
        return intent.getStringExtra("id_gym");
    }

    private void fetchData() {
        CollectionReference collection = db.collection("Companies/"+getDocumentPath()+"/"+ Globals.MEMBER);
        Query query = collection.orderBy("user_name",Query.Direction.DESCENDING);
        System.out.println("GYM NAME submit payment: "+ getDocumentPath());

        FirestoreRecyclerOptions<PaymentMemberModel> options = new FirestoreRecyclerOptions.Builder<PaymentMemberModel>()
                .setQuery(query,PaymentMemberModel.class).build();
        options.getSnapshots();

        adapter = new SubmitPaymentMemberAdapter(options);
        submit_payment_member_rv.setHasFixedSize(true);
        submit_payment_member_rv.setLayoutManager(new LinearLayoutManager(this));
        submit_payment_member_rv.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}