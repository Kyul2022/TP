package com.example.tp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccoumt extends AppCompatActivity {
    EditText pseudo, mail, pwd=null;
    String ps, email,password;
    Button ok=null;
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
                    addDataToFirestore(ps, email);
                }

            }
        });
    }

    private void addDataToFirestore(String ps, String email) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbUsers = db.collection("Users");

        // adding our data to our courses object class.
        User user = new User(email, ps, null);

        // below method is use to add data to Firebase Firestore.
        dbUsers.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(getApplicationContext(), "This user has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(getApplicationContext(), "Fail to add user \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}