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

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.company_panel.CompanyPanel;
import com.example.myegineerapplication.config.FirebaseInit;
import com.example.myegineerapplication.config.Globals;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

public class SignIn extends AppCompatActivity  implements View.OnClickListener{
    private EditText email_edit_text ;
    private EditText password_edit_text ;
    protected Button create_account_btn,login_btn;
    private FirebaseAuth firebaseAuth;
    public static final String validation = "Sprawdź poprawność wprowadzonych danych";
    private static final String PASSWORD = "Hasło nie może zawierać mniej niż 6 znaków";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_login);
        firebaseAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth
        create_account_btn= findViewById(R.id.create_new_account_btn_gym);
        login_btn = findViewById(R.id.login__user_btn_gym);
        email_edit_text = findViewById(R.id.email_edit_text_gym);
        password_edit_text = findViewById(R.id.password_edit_text_gym);
        create_account_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }
    public void initDialog() {
        DialogAlert dialog = new DialogAlert();
        dialog.show(getSupportFragmentManager(), " dialog alert");
    }
//before was public
    private void login(){
        final String email = email_edit_text.getText().toString().trim();
        String password = password_edit_text.getText().toString().trim();
        if (email.isEmpty()){
            email_edit_text.setError(Globals.NOTIFICATION);
            email_edit_text.requestFocus();
            return;
        }
        if (password.isEmpty()){
            password_edit_text.setError(Globals.ENTER_PASSWORD);
            password_edit_text.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_edit_text.setError(validation);
            email_edit_text.requestFocus();
            return;
        }
        if (password.length()<6){
            password_edit_text.setError(PASSWORD);
            password_edit_text.requestFocus();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(CreateGymAccount.PREFIX+email, password)
                .addOnCompleteListener(SignIn.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() ){
                    FirebaseInit.db.collection("Companies").whereEqualTo("firebaseId", firebaseAuth.getUid()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.getResult().isEmpty()) {
                                        firebaseAuth.getCurrentUser().delete();
                                        initDialog();
                                        email_edit_text.setText("");
                                        password_edit_text.setText("");
                                        //Toast.makeText(getApplicationContext(), "Twoje konto zostalo usuniete", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent intent = new Intent(SignIn.this, CompanyPanel.class);
                                        intent.putExtra("email_gym", email);
                                        startActivity(intent);
                                        Log.d("GYMLOGIN uid", firebaseAuth.getUid());
                                        Toast.makeText(getApplicationContext(), "Zalogowano", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });
//                    Intent intent = new Intent(GymLogin.this, CompanyPanel.class);
//                    intent.putExtra("email_gym", email);
//                    startActivity(intent);
//
//                        Toast.makeText(getApplicationContext(), "Zalogowano", Toast.LENGTH_SHORT).show();
//                        finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Coś poszło nie tak, spróbuj ponownie", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch ( v.getId()){
            case R.id.create_new_account_btn_gym:
                startActivity(new Intent(this, CreateGymAccount.class));
                break;
            case  R.id.login__user_btn_gym:
                login();
                break;
        }
    }
}
