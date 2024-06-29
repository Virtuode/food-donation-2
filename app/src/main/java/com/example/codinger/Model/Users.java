package com.example.codinger.Model;

public class Users {
    private String id;
    private String userName;
    private String imageURL;

    public Users() {
    }

    public Users(String id, String userName, String imageURL) {
        this.id = id;
        this.userName = userName;
        this.imageURL = imageURL;
    }

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for userName
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter and Setter for imageURL
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
