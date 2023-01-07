package com.example.tp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class CreateAccoumt extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText pseudo, mail, pwd=null;
    String ps, email,password;
    Button google, ok=null;
    TextView load=null;
    private FirebaseFirestore db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_accoumt);
        pseudo=findViewById(R.id.pseudo);
        mail=findViewById(R.id.mail);
        pwd=findViewById(R.id.pwd);
        ok=findViewById(R.id.ok);
        google=findViewById(R.id.google);
        load=findViewById(R.id.load);
        auth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ps = pseudo.getText().toString();
                email = mail.getText().toString();
                password = pwd.getText().toString();
                if (TextUtils.isEmpty(ps)) {
                    pseudo.setError("Please enter Course Name");
                } else if (TextUtils.isEmpty(email)) {
                    mail.setError("Please enter Course Description");
                } else if (TextUtils.isEmpty(password)) {
                    pwd.setError("Please enter Course Duration");
                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(ps, email, password);
                }

            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignInActivity();
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //
            }
        });
    }

    private void addDataToFirestore(String ps, String email, String password) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbUsers = db.collection("users");

        // adding our data to our courses object class.
        user user = new user(ps, email);
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getApplicationContext(), "This user has been added to Firebase Auth", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed to add user Auth", Toast.LENGTH_SHORT).show();
                Log.d("Failed with fireAuth",e.getMessage());
            }
        });
        if(auth.getCurrentUser()!= null){
                    Intent i = new Intent(getApplicationContext(), more.class);
                    i.putExtra("pseudo", ps);
                    i.putExtra("email", email);
                    startActivity(i);
                }
        else{
            Toast.makeText(getApplicationContext(), "Eeeeh ....", Toast.LENGTH_SHORT).show();
        }

        // below method is use to add data to Firebase Firestore.
    }

    // UploadImage method


    private void startSignInActivity(){

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers =
                Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder().build() );

        // Launch the activity
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(true, true)
                        .build(),
                200);

        CollectionReference  dbUsers=FirebaseFirestore.getInstance().collection("users");
        if(auth.getCurrentUser()!= null){
            user user = new user(auth.getCurrentUser().getDisplayName().toString().trim(), auth.getCurrentUser().getEmail().toString());
            dbUsers.document(auth.getCurrentUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "This user has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("pseudo", ps);
                    startActivity(i);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Failed to add user Firestore", Toast.LENGTH_SHORT).show();
                    Log.d("Failed with firestore",e.getMessage());
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "Eeeeh ....", Toast.LENGTH_SHORT).show();
        }


    }



    //Create a User sur firefire
    public void createUser(String email, String pwd){
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getApplicationContext(),"User successfully created!",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"User failed!",Toast.LENGTH_LONG).show();
            }
        });
    }

    }




