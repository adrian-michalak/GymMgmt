package com.example.myegineerapplication.accounts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myegineerapplication.MainAppActivity;
import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.FirebaseInit;
import com.example.myegineerapplication.config.Globals;
import com.example.myegineerapplication.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class CreateUserAccount extends AppCompatActivity implements View.OnClickListener{
    private EditText email_et, password_et , name_et, phone_et;
    private Button create_btn;
    private static final String NOTICE = "Pole wymagane";
    private static final String PASSWORD = "Hasło nie może zawierać mniej niż 6 znaków";
    private static final String VALID_EMAIL = "Sprawdź poprawnośc e-maila";
    private  static final String TAG ="User";
    private  static final String USER_NAME = "name";
    private  static final String EMAIL = "email";
    private  static final String PHONE = "phone";
    private  static final String UID = "uid";

    private FirebaseAuth firebaseAuth;
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_account);
       // firebaseAuth = FirebaseAuth.getInstance();   // Initialize Firebase Auth

        email_et = findViewById(R.id.email_edit_text);
        name_et = findViewById(R.id.name_edit_text);
        phone_et = findViewById(R.id.phone_edit_text);
        password_et = findViewById(R.id.password_edit_text);
        create_btn = findViewById(R.id.button_create_user);
        create_btn.setOnClickListener(this);
    }

    private void createUser(){
        final String email = email_et.getText().toString().toLowerCase().trim();
        String password = password_et.getText().toString().trim();
        final String userName = name_et.getText().toString().trim();
        final String phone = phone_et.getText().toString().trim();

        if (userName.isEmpty()){
            name_et.setError(Globals.NOTIFICATION);
            name_et.requestFocus();
            return;
        }
        if (email.isEmpty()){
            email_et.setError(Globals.NOTIFICATION);
            email_et.requestFocus();
            return;
        }
        if (password.isEmpty()){
            password_et.setError(Globals.NOTIFICATION);
            password_et.requestFocus();
            return;
        }
        if (password.length()<6){
            password_et.setError(Globals.PASSWORD);
            password_et.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_et.setError(Globals.VALIDATION);
            email_et.requestFocus();
            return;
        }

        FirebaseInit.firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();

                    firebaseUser.updateProfile(profileUpdates);
                    UserModel user = new UserModel(FirebaseInit.firebaseAuth.getUid(),userName,phone,email);

                    FirebaseInit.db.collection("Users").document(email).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(CreateUserAccount.this,"Witaj !",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateUserAccount.this,"Błąd",Toast.LENGTH_SHORT).show();
                            Log.d(TAG,e.toString());
                        }
                    });
                    Toast.makeText(CreateUserAccount.this, "Profil stworzono",Toast.LENGTH_SHORT).show();

                    name_et.setText("");
                    email_et.setText("");
                    password_et.setText("");
                    phone_et.setText("");

                    Intent intent = new Intent(CreateUserAccount.this, MainAppActivity.class);
                    startActivity(intent);

                } else  {
                    Toast.makeText(CreateUserAccount.this,task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_create_user) {
            createUser();
        }
    }
}
