package com.example.tp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText pseudo,pwd;
    Button google,log=null;
    FirebaseAuth mAuth;
    TextView create;
    FirebaseUser user;
    String email=null;
    private static final int RC_SIGN_IN=100;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pseudo=findViewById(R.id.Pseudo);
        pwd=findViewById(R.id.pwd);
        create=findViewById(R.id.create);
        mAuth = FirebaseAuth.getInstance();
        google=findViewById(R.id.google);
        log=findViewById(R.id.log);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ps= pseudo.getText().toString();
                Toast.makeText(getApplicationContext(),"heey",Toast.LENGTH_LONG).show();
                String passwd= pwd.getText().toString();
                CollectionReference users= FirebaseFirestore.getInstance().collection("users");
                users.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            String pseud = (String) doc.get("pseudo");
                            if(pseud.equals(ps)){
                                email= (String) doc.get("email");
                                break;
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"RAS1",Toast.LENGTH_LONG).show();
                            }
                        }
                        if(email!= null){
                        mAuth.signInWithEmailAndPassword(email, passwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent i = new Intent(getApplicationContext(), Menu.class);
                                i.putExtra("pseudo", ps);
                                startActivity(i);
                            }
                        });}
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    }
                });

            }
            }
        ); google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startSignInActivity();
             user = mAuth.getCurrentUser();
             if (user!= null){
                 Intent i = new Intent(getApplicationContext(), Menu.class);
                 startActivity(i);
             }
            }
            }
        );create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateAccoumt.class);
                startActivity(i);
            }
            }
        );
    }

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
                    RC_SIGN_IN);
        }

}
