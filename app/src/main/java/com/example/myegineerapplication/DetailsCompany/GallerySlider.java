package com.example.myegineerapplication.DetailsCompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.ImageModel;
import com.example.myegineerapplication.ui.home.HomeFragmentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GallerySlider extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GallerySliderAdapter adapter;
    private DatabaseReference databaseReference ;
    private List<ImageModel> imageModelList ;

    ProgressBar gallerySliderProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_slider);

        imageModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.gallery_slider_rv);


        databaseReference =  FirebaseDatabase.getInstance().getReference(getDocumentPath());
        databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                       ImageModel imageModel = snapshot.getValue(ImageModel.class);
                      // System.out.println("URL:" + imageModel.getImageUrl());
                       imageModelList.add(imageModel);
                       //TRzy nastepne linijki były były po za petla
                   adapter = new GallerySliderAdapter(GallerySlider.this, imageModelList);
                   recyclerView.setAdapter(adapter);
                   recyclerView.setHasFixedSize(true);
                   recyclerView.setLayoutManager(new LinearLayoutManager(GallerySlider.this));
               }


               System.out.println("size: "+ imageModelList.size());
             // // gallerySliderProgressBar.setVisibility(View.INVISIBLE);
           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Toast.makeText(GallerySlider.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
               //gallerySliderProgressBar.setVisibility(View.INVISIBLE);
           }
       });

    }

    private String getDocumentPath(){
        Intent intent = getIntent();
        return intent.getStringExtra("gym_id");
    }

}
