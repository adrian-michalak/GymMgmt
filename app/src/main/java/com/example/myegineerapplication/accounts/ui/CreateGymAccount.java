package com.example.myegineerapplication.accounts.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.FirebaseInit;
import com.example.myegineerapplication.config.Globals;
import com.example.myegineerapplication.model.CompanyModel;
import com.example.myegineerapplication.model.ImageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class CreateGymAccount extends AppCompatActivity implements View.OnClickListener {
    private static final int GET_FROM_GALLERY_MULTIPLE = 1 ;
    private TextView id_tv;
    private EditText email_et, brief_desc_et , name_et, phone_et, city_et, street_et, price_et, number_et, password_et,nip_et;

   // Map<String,String> hour = new HashMap<>();
    public static final String PREFIX = "gym_";

    // Access a Cloud Firestore instance from your Activity
    private  ProgressBar addImgProgressBar;
    private static final String TAG = "Company";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_gym_account);

        findView();

        Button create_btn = findViewById(R.id.button_create_company);
        Button addPhoto = findViewById(R.id.gallery_image_button);

        //===============UNIQUE-ID ===============================
        Globals.uniqueID = UUID.randomUUID().toString();
        Log.d("uniqueid ", Globals.uniqueID);
        //========================================================
        id_tv.setText(Globals.uniqueID);

        Log.d(TAG, id_tv.getText().toString());
        addPhoto.setOnClickListener(this);
        create_btn.setOnClickListener(this);
    }
    private void findView(){
        nip_et = findViewById(R.id.nip_edit_text);
        email_et = findViewById(R.id.email_edit_text);
        password_et = findViewById(R.id.password_edit_text);
        name_et = findViewById(R.id.company_edit_text);
        phone_et =findViewById(R.id.phone_edit_text);
        brief_desc_et = findViewById(R.id.create_brief_description);
        city_et = findViewById(R.id.city_edit_text);
        street_et = findViewById(R.id.street_edit_text);
        price_et = findViewById(R.id.price_edit_text);
        number_et = findViewById(R.id.number_edit_text);
        addImgProgressBar = findViewById(R.id.addImgProgressBar);
        id_tv = findViewById(R.id.id_text_view);
    }
    void isValid(EditText editText, String s){
        if(s.isEmpty()){
            editText.setError(Globals.NOTIFICATION);
            editText.requestFocus();
        }
    }
    private void createCompany(){
        final String nip = nip_et.getText().toString().trim();
        final String email = email_et.getText().toString().toLowerCase().trim();
        final String password = password_et.getText().toString().trim();
        final String name = name_et.getText().toString().trim();
        final String phone = phone_et.getText().toString().trim();
        final String city = city_et.getText().toString().trim();
        final String street = street_et.getText().toString().trim();
        final String price = price_et.getText().toString().trim();
        final String number = number_et.getText().toString().trim();
        final String brief_desc = brief_desc_et.getText().toString().trim();
        final String uid = id_tv.getText().toString();

       if (name.isEmpty()){
            name_et.setError(Globals.NOTIFICATION);
            name_et.requestFocus();
        }
       else if (email.isEmpty()){
            email_et.setError(Globals.NOTIFICATION);
            email_et.requestFocus();
        }
       else if (password.isEmpty()){
            email_et.setError(Globals.NOTIFICATION);
            email_et.requestFocus();
        }
        else if (city.isEmpty()){
            city_et.setError(Globals.NOTIFICATION);
            city_et.requestFocus();
        }
        else if (street.isEmpty()){
            city_et.setError(Globals.NOTIFICATION);
            city_et.requestFocus();
        }
        else if (number.isEmpty()){
            number_et.setError(Globals.NOTIFICATION);
            number_et.requestFocus();
        }
        else if (price.isEmpty()){
            price_et.setError(Globals.NOTIFICATION);
            price_et.requestFocus();
        }
        else if (nip.length() < 10){
            nip_et.setError(Globals.VALIDATION);
            nip_et.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_et.setError(Globals.VALIDATION);
            email_et.requestFocus();
       }
        else if (password.length()<6){
            password_et.setError(Globals.PASSWORD);
            password_et.requestFocus();
       } else {

           FirebaseInit.firebaseAuth.createUserWithEmailAndPassword(PREFIX.trim() + email, password)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()) {
                       UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(email).build();
                       FirebaseInit.user = FirebaseInit.firebaseAuth.getCurrentUser();

                       FirebaseInit.user.updateProfile(profileUpdates);
                       Log.d(TAG, "createUserWithEmail:success");
                        } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                }
               }
           });

           final CompanyModel companyModel =
                   new CompanyModel(uid ,name, city, phone, email, street, Integer.valueOf(price), brief_desc, number, nip, FirebaseInit.firebaseAuth.getUid());

           FirebaseInit.db.collection("Companies").document(Globals.uniqueID)
                   .set(companyModel).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(CreateGymAccount.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                   Log.d(TAG, e.toString());
               }
           });
           Intent goToAddFitness = new Intent(this, AddFitnessClasses.class);
           goToAddFitness.putExtra("gymRef", companyModel.getId());
           startActivity(goToAddFitness);
       }
        }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, GET_FROM_GALLERY_MULTIPLE);
    }

    //===================================================================================================
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY_MULTIPLE && resultCode == RESULT_OK && data != null){
            if (data.getClipData() != null){
                int selectedItems = data.getClipData().getItemCount();
                for (int i=0 ; i<selectedItems ; i++){
                   // Log.d("uid result: ", id_tv.getText().toString());
                    StorageReference storageReference = FirebaseInit.firebaseStorage.getReference(id_tv.getText().toString());
                    Uri imgUri = data.getClipData().getItemAt(i).getUri();
                    ImageModel model = new ImageModel(imgUri.toString(), UUID.randomUUID().toString());
                    FirebaseInit.mDatabaseRef = FirebaseDatabase.getInstance().getReference(id_tv.getText().toString());
                    System.out.println("URL: "+model.getImageUrl());


                    final StorageReference upload = storageReference.
                            child(System.currentTimeMillis()+"."+getFileExtension(imgUri));
                    final int finalI = i;

                    upload.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //When the image has successfully uploaded, get its download URL
                            upload.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri uri_ = uri;
                                    ImageModel imgModel = new ImageModel(UUID.randomUUID().toString(),uri_.toString());
                                    FirebaseInit.mDatabaseRef.push().setValue(imgModel);
                                }
                            });
                            addImgProgressBar.setVisibility(View.VISIBLE);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateGymAccount.this,"Coś poszło nie tak"+ e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            addImgProgressBar.setProgress((int) progress);
                        }
                    });

                }

            } else {
                Toast.makeText(this, "Wybrano jeden obraz", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_create_company:
                createCompany();

                break;
            case  R.id.gallery_image_button:
                chooseImage();
                break;
        }
    }
}
