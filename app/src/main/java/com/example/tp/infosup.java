package com.example.tp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class infosup extends AppCompatActivity {
    EditText text;
    Button tof, semd;
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 100;
    private static final int RC_CHOOSE_PHOTO = 200;
    private Uri uriImageSelected;
    private String uriUpload = "s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infosup);
        text=findViewById(R.id.text);
        tof=findViewById(R.id.tof);
        semd=findViewById(R.id.semd);
        tof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFile();
            }
        });
        semd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
                String texte= text.getText().toString();
                String usr= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                Toast.makeText(getApplicationContext(), "salut "+usr, Toast.LENGTH_SHORT).show();
                post P = new post(texte, usr, uriUpload);
                Post(P);
            }
        });
    }

    //////////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    private void addFile(){
        if (!EasyPermissions.hasPermissions(this, PERMS)) {
            EasyPermissions.requestPermissions(this, "Acceder a la galerie", RC_IMAGE_PERMS, PERMS);
            return;
        }
        Toast.makeText(this, "Vous avez le droit d'acc??der aux images !", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RC_CHOOSE_PHOTO);
    }

    private void handleResponse(int requestCode, int resultCode, Intent data){
        if (requestCode == RC_CHOOSE_PHOTO) {
            if (resultCode == RESULT_OK) { //SUCCESS
                this.uriImageSelected = data.getData();
            } else {
                Toast.makeText(this, "pas marche", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponse(requestCode, resultCode, data);
    }

    ////////

    private void uploadImage() {
        Uri filePath =  this.uriImageSelected;
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            this.uriUpload = UUID.randomUUID().toString();
            // Defining the child of storageReference
            StorageReference ref
                    = FirebaseStorage.getInstance().getReference()
                    .child(
                            "posts/"+this.uriUpload );
            Toast.makeText(getApplicationContext(), "Wait...", Toast.LENGTH_LONG).show();
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Upload ok!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Upload failed!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        }

        }
        public void Post(post poste){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        CollectionReference ref = FirebaseFirestore.getInstance().collection("posts");
        ref.document(UUID.randomUUID().toString()).set(poste).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Post succeeded!!", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Post failed!!", Toast.LENGTH_LONG).show();
            }
        });
        }
}