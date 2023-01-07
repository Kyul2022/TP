package com.example.tp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Menu extends AppCompatActivity {
    ListView lv=null;
    Button click, click2;
    FloatingActionButton post;
    FirebaseAuth mAuth;
    public ArrayList<String> texts = new ArrayList<String>();
    public ArrayList<String> img = new ArrayList<String>();
    CollectionReference ref = FirebaseFirestore.getInstance().collection("posts");
    CollectionReference ppref = FirebaseFirestore.getInstance().collection("users");
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        lv = findViewById(R.id.listview);
                post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), infosup.class);
                startActivity(i);
            }
        });
        click2=findViewById(R.id.refresh);
        mAuth= FirebaseAuth.getInstance();
        click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Menu.class);
                startActivity(i);
            }
        });
        ///
        ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshot) {
                ArrayList<String> text = new ArrayList<String>();
                ArrayList<String> imgs = new ArrayList<String>();
                for (@Nullable QueryDocumentSnapshot doc : queryDocumentSnapshot) {
                    text.add(doc.get("text").toString());
                    imgs.add(doc.get("img").toString());
                    Toast.makeText(getApplicationContext(), "val "+imgs.get(0), Toast.LENGTH_SHORT).show();

                }

                ppref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String pp = null;
                        String ids = null;
                        for (@Nullable QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.get("email").toString().equals(mAuth.getCurrentUser().getEmail())) {
                                pp= doc.get("profilepricture").toString();
                                ids=doc.get("pseudo").toString();
                            }
                            //Toast.makeText(getApplicationContext(), "val " + pp.size(), Toast.LENGTH_SHORT).show();
                            PostDesig adapter = new PostDesig(getApplicationContext(), text, imgs, pp, ids);
                            lv.setAdapter(adapter);
                        }
                    }
                });
            }
        });

        //

    }

   // @Override
   // protected void onStart() {
       // super.onStart();
       // debut();
    //}
}