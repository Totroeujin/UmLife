package com.example.model;

import com.google.firebase.firestore.auth.User;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String username;
    private String email;
    private String uuid;

    public UserInfo(){

    }

    public UserInfo(String username, String email, String uuid){
        this.uuid = uuid;
        this.email = email;
        this.username = username;
    }

    public UserInfo(UserInfo userInfo){
        this.username = userInfo.getUsername();
        this.email = userInfo.getEmail();
        this.uuid = userInfo.getUuid();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
