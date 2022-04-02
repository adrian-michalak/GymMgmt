package com.example.myegineerapplication.accounts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myegineerapplication.MainAppActivity;
import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.FirebaseInit;
import com.example.myegineerapplication.config.Globals;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserLogin extends AppCompatActivity implements View.OnClickListener {
    private EditText email_edit_text ;
    private EditText password_edit_text ;
    protected Button create_account_btn,login_btn;
    private FirebaseAuth firebaseAuth;

    private static final int RC_SIGN_IN = 7171 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth

        setContentView(R.layout.activity_login);

        create_account_btn= findViewById(R.id.create_new_account_btn);
        login_btn = findViewById(R.id.login__user_btn);
        email_edit_text = findViewById(R.id.email_edit_text);
        password_edit_text = findViewById(R.id.password_edit_text);
        create_account_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }
    public void initDialog() {
        DialogAlert dialog = new DialogAlert();
        dialog.show(getSupportFragmentManager(), " dialog alert");
    }
    public void login(){
        final String email = email_edit_text.getText().toString().trim();
        String password = password_edit_text.getText().toString().trim();
        if (email.isEmpty()){
            email_edit_text.setError(Globals.VALIDATION);
            email_edit_text.requestFocus();
            return;
        }
        if (password.isEmpty()){
            password_edit_text.setError(Globals.ENTER_PASSWORD);
            password_edit_text.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_edit_text.setError(Globals.VALIDATION);
            email_edit_text.requestFocus();
            return;
        }
        if (password.length()<6){
            password_edit_text.setError(Globals.PASSWORD);
            password_edit_text.requestFocus();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(UserLogin.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseInit.db.collection("Users").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult().exists()){
                                Toast.makeText(getApplicationContext(), "Witaj ! ",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserLogin.this,MainAppActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                firebaseAuth.getCurrentUser().delete();
                                initDialog();
                                email_edit_text.setText("");
                                password_edit_text.setText("");
                               // Toast.makeText(getApplicationContext(), "Twoje konto zostalo usuniete", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Coś poszło nie tak, spróbuj ponownie\"", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Toast.makeText(getApplicationContext(), "Witaj ! ",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(UserLogin.this,MainAppActivity.class);
//                    startActivity(intent);
//                    finish();
                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Coś poszło nie tak, spróbuj ponownie\"", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch ( v.getId()){
            case R.id.create_new_account_btn:
                startActivity(new Intent(this, CreateUserAccount.class));
                break;
            case  R.id.login__user_btn:
                  login();
                  break;
        }
    }
    // PREVENT BACK APP
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
