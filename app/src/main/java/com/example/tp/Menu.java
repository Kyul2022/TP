package com.example.tp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class Menu extends AppCompatActivity {
    ListView lv=null;
    Button click;
    String[] text=new String[]{"Texte du texte toujours du texte et puis du texte!","Eeeeeh oui rien que du texte!!"};
    String[] imgs=new String[]{"ad22add1-a6b1-4d93-bd6c-c0d10fb53087","sure.png"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        lv=findViewById(R.id.listview);
        click=findViewById(R.id.click);
        PostDesig adapter=new PostDesig(getApplicationContext(), text, imgs);
        lv.setAdapter(adapter);
    }
}