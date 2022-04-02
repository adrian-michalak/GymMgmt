package com.example.myegineerapplication.DetailsCompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.ui.home.HomeFragmentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class DetailsActivity extends AppCompatActivity  implements View.OnClickListener {
    LinearLayout gallery_ll , comments_ll, about_ll, pay_ll, fitness_ll;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;

    private static final String DOCUMENT = "gymName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        gallery_ll = findViewById(R.id.gallery_ll_button);
        comments_ll = findViewById(R.id.comments_ll_button);
        fitness_ll = findViewById(R.id.fitness_ll_button);
        about_ll = findViewById(R.id.about_ll_button);
        pay_ll = findViewById(R.id.payment_ll_button);
        gallery_ll.setOnClickListener(this);
        comments_ll.setOnClickListener(this);
        about_ll.setOnClickListener(this);
        pay_ll.setOnClickListener(this);
        fitness_ll.setOnClickListener(this);

    }

    public String setDocumentPath(){
        Intent intent = getIntent();
        return intent.getStringExtra(DOCUMENT);
    }

    private void intentGallerySlider(){
        Intent intentGallery = new Intent(this, GallerySlider.class);
        intentGallery.putExtra("gym_id",setDocumentPath());
        startActivity(intentGallery);
    }
    private void intentPost(){
        Intent intentPost = new Intent(this, Posts.class);
        intentPost.putExtra("gym_id", setDocumentPath());
        startActivity(intentPost);
    }

    private void readDescription() {
        Intent intentGallery = new Intent(this, About.class);
        intentGallery.putExtra("gym_id",setDocumentPath());
        startActivity(intentGallery);
    }

    private void readFitnessInfo(){
        Intent fitnessIntent = new Intent(getApplication(), FitnessSchedule.class);
        fitnessIntent.putExtra("gym_id", setDocumentPath());
        startActivity(fitnessIntent);
    }
    private void paymentInfo(){
        Intent payIntent = new Intent(getApplication(), PaymentsActivity.class);
        payIntent.putExtra("gym_id", setDocumentPath());
        startActivity(payIntent);
    }

//==================================================================================================
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.gallery_ll_button:
                intentGallerySlider();
                return;
            case  R.id.comments_ll_button:
                intentPost();
                return;
            case  R.id.fitness_ll_button:
                readFitnessInfo();
                return;
            case  R.id.about_ll_button:
                readDescription();
                return;
            case R.id.payment_ll_button:
                paymentInfo();
                return;
        }
    }


}
