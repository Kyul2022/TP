package com.example.tp;

import android.net.Uri;

public class post {
    private String text;
    private String author;
    private String img;

    public post(String text, String author, String img) {
        this.text = text;
        this.author = author;
        this.img = img;
    }
    public post(){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
