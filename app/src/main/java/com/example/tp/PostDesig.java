package com.example.tp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class PostDesig extends BaseAdapter {
    private Context ctx=null;
    private FirebaseStorage fs;
    private StorageReference ref;
    private StorageReference pref;
    ArrayList<String> texts = new ArrayList<String>();
    ArrayList<String> img = new ArrayList<String>();
    String pp;
    String ids;
    private LayoutInflater ift;

    public  PostDesig(Context ctx, @Nullable ArrayList<String> text, @Nullable ArrayList<String> img, @Nullable String pp, @Nullable String ids){
        this.ctx=ctx;
        this.texts=text;
        this.img =img;
        this.pp =pp;
        this.ids =ids;
        fs=FirebaseStorage.getInstance();
        ref=fs.getReference("posts/");
        pref=fs.getReference("pp/");
        Toast.makeText(ctx,"Failed to download!!"+texts.size(),Toast.LENGTH_LONG).show();
    }
    @Override
    public int getCount() {
        return texts.size();
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
        TextView txt, id;
        ImageView img;
        ImageButton im;
        ift=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=ift.inflate(R.layout.display_posts,viewGroup,false);
            txt=view.findViewById(R.id.post_content);
            id=view.findViewById(R.id.id);
            img=view.findViewById(R.id.post_view);
            im=view.findViewById(R.id.pp);
            txt.setText(texts.get(i));
            id.setText(ids);
            View itemview=view;
            ref.child(this.img.get(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(itemview).load(uri).into(img);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ctx,"Failed to download!!",Toast.LENGTH_LONG).show();
                    Log.d("Erreur display", e.getMessage());
                }
            });
            pref.child(this.pp).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(itemview).load(uri).into(im);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ctx,"Failed to download!!",Toast.LENGTH_LONG).show();
                    Log.d("Erreur display", e.getMessage());
                }
            });
        return view;
    }
}
