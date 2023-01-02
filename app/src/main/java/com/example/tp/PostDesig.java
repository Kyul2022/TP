package com.example.tp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.zip.Inflater;

public class PostDesig extends BaseAdapter {
    private Context ctx=null;
    private FirebaseStorage fs;
    private StorageReference ref;
    private String[] text=null;
    private String[] imgs=null;
    private LayoutInflater ift;
    public  PostDesig(Context ctx, String[] text,String[] imgs){
        this.ctx=ctx;
        this.text=text;
        this.imgs=imgs;
        fs=FirebaseStorage.getInstance();
        ref=fs.getReference("images/");
    }
    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView txt;
        ImageView img;
        ift=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=ift.inflate(R.layout.display_posts,viewGroup,false);
            txt=view.findViewById(R.id.post_content);
            img=view.findViewById(R.id.post_view);
            txt.setText(text[i]);
            View itemview=view;
            ref.child(imgs[i]).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(itemview).load(uri).into(img);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ctx,"Failed to download!!",Toast.LENGTH_LONG).show();
                }
            });
        return view;
    }
}
