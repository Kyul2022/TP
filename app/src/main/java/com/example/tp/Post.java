package com.example.tp;

import java.util.Date;

public class Post {

    private String message;
    //private String dateCreated;
    private User userSender;
    private String urlImage;

    public Post() { }

    public Post(String message, User userSender) {
        this.message = message;
        this.userSender = userSender;
    }

    public Post(String message, String urlImage, User userSender) {
        this.message = message;
        this.urlImage = urlImage;
        this.userSender = userSender;
    }

    // --- GETTERS ---
    public String getMessage() { return message; }
    //@ServerTimestamp public Date getDateCreated() { return dateCreated; }
    public User getUserSender() { return userSender; }
    public String getUrlImage() { return urlImage; }

    // --- SETTERS ---
    public void setMessage(String message) { this.message = message; }
    //public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    public void setUserSender(User userSender) { this.userSender = userSender; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }
}
