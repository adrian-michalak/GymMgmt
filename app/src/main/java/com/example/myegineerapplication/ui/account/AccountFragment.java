package com.example.myegineerapplication.ui.account;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myegineerapplication.R;

import com.example.myegineerapplication.accounts.ui.SelectablePanel;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class AccountFragment extends Fragment implements View.OnClickListener{

    private  static final String NAME = "name";
    private  static final String EMAIL = "email";
    private  static final String PHONE = "phone";

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private  static final String TAG ="User";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView user_tv, phone_tv, email_tv , uid_tv, price_tv, entries_tv,object_type_tv, membership_card_type_tv, start_date_tv ;
    private LinearLayout linearLayout;
    private final String email = firebaseAuth.getCurrentUser().getEmail();
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

         user_tv = root.findViewById(R.id.user_name_text_view);
         email_tv = root.findViewById(R.id.user_email_text_view);
         phone_tv = root.findViewById(R.id.user_phone_text_view);
         linearLayout = root.findViewById(R.id.membership_ll);

         uid_tv = root.findViewById(R.id.account_id_tv);
         price_tv = root.findViewById(R.id.account_price_tv);
         entries_tv = root.findViewById(R.id.account_entries_tv);
         object_type_tv = root.findViewById(R.id.account_object_type_tv);
         membership_card_type_tv = root.findViewById(R.id.account_membership_tv);
         start_date_tv = root.findViewById(R.id.account_date_tv);

         Button sign_out_button = root.findViewById(R.id.btn_sign_out);
         sign_out_button.setOnClickListener(this);

         fetchUserData();
         getMembershipInfo();
        return root;
    }


     private void fetchUserData(){
        DocumentReference docRef = db.collection("Users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document  = task.getResult();
                    if (document.exists()) {
                        user_tv.setText(document.getString(NAME));
                        email_tv.setText(document.getString(EMAIL));
                        phone_tv.setText(document.getString(PHONE));

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    private String translate( String s){
        if ( s.toLowerCase().equals("concessionary") ){ s =  "Ulgowy"; }
        if (s.toLowerCase().equals("normal")){ s = "Normalny"; }
        if (s.toLowerCase().equals("gym")) { s = "Siłownia"; }
        return s;
    }
    private void getMembershipInfo(){
        final CollectionReference collection = db.collection("Users/" + email.trim() + "/Memberships");
        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                        if (queryDocumentSnapshot.exists()){
                            setVisibilityLinearLayout(linearLayout);
                            object_type_tv.setText(translate(queryDocumentSnapshot.get("ticket_type").toString()));
                            uid_tv.setText( queryDocumentSnapshot.get("gym_full_address").toString());
                            start_date_tv.setText(queryDocumentSnapshot.get("date").toString());
                            price_tv.setText( queryDocumentSnapshot.get("price").toString() + " zł");
                            entries_tv.setText(queryDocumentSnapshot.get("entries").toString());
                            membership_card_type_tv.setText(translate( queryDocumentSnapshot.get("membership_card_type").toString()));
                        }

                    }
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sign_out) {
            signOut();
        }
    }
    private void setVisibilityLinearLayout(LinearLayout linearLayout){
    linearLayout.setVisibility(View.VISIBLE);
}
    private void signOut() {
      FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
      firebaseAuth.signOut();
      Log.d(TAG,"SIGN OUT");
        Toast.makeText(getContext(),"Wylogowano", Toast.LENGTH_SHORT ).show();
        Intent intent = new Intent(getActivity(), SelectablePanel.class);
        startActivity(intent);
        user_tv.setText("");
        email_tv.setText("");
        phone_tv.setText("");
    }

}
