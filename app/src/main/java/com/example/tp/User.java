package com.example.tp;

import androidx.annotation.Nullable;

public class User {

    private String mail;
    private String pseudo;
    @Nullable
    private String urlPicture;

    public User() { }

    public User(String mail, String pseudo, @Nullable String urlPicture) {
        this.mail = mail;
        this.pseudo = pseudo;
        this.urlPicture = urlPicture;
    }

    // --- GETTERS ---
    public String getUid() { return mail; }
    public String getUsername() { return pseudo; }
    @Nullable
    public String getUrlPicture() { return urlPicture; }

    // --- SETTERS ---
    public void setUsername(String username) { this.pseudo = username; }
    public void setUid(String uid) { this.mail = uid; }
    public void setUrlPicture(@Nullable String urlPicture) { this.urlPicture = urlPicture; }
}
