package com.example.myegineerapplication.DetailsCompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.FirebaseInit;
import com.example.myegineerapplication.model.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddPost extends AppCompatActivity implements View.OnClickListener {
    private FirebaseUser user = FirebaseInit.firebaseAuth.getCurrentUser();
   // private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postCollectionRef = FirebaseInit.db.collection("Posts");
    EditText post_body;
    Button add_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        post_body = findViewById(R.id.post_body);
        add_btn = findViewById(R.id.add_post_btn);
        add_btn.setOnClickListener(this);
    }

    private String getDocumentPath(){
        Intent intent = getIntent();
        return intent.getStringExtra("gym_id");
    }

    private void addPost(){
        final String uid = user.getDisplayName();
        final String body= post_body.getText().toString();
        final String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        final String gymName = getDocumentPath();
        if (body.isEmpty() ){

            Toast.makeText(this,"Please insert a comment body",Toast.LENGTH_LONG).show();
        }
        PostModel postModel = new PostModel( uid, body, date,gymName);
        final Map<String,Object> post = new HashMap<>();
        post.put("uid", uid);
        post.put("body", body);
        post.put("date", date);
        post.put("gymName", gymName);
        postCollectionRef.add(postModel);
        System.out.println("UID: " + uid);

        post_body.setText("");
        Toast.makeText(this,"Post added ",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_post_btn){
            addPost();
        }
    }
}
