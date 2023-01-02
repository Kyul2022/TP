package com.example.tp;

import android.net.Uri;

public class user {
    private String pseudo;
    private String email;
    private Uri profilepicture;

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getProfilepricture() {
        return profilepricture;
    }

    public void setProfilepricture(Uri profilepricture) {
        this.profilepricture = profilepricture;
    }

    private Uri profilepricture;

    public user(){}
    public user(String pseudo, String email){
        this.pseudo=pseudo;
        this.email=email;
    }

}
