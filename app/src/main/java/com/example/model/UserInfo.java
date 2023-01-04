package com.example.model;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String username;
    private String email;
    private String uuid;
    private String profileImage;

    public UserInfo(){

    }

    public UserInfo(String username, String email, String uuid, String profilePicture){
        this.uuid = uuid;
        this.email = email;
        this.username = username;
        this.profileImage = profilePicture;
    }

    public UserInfo(UserInfo userInfo){
        this.username = userInfo.getUsername();
        this.email = userInfo.getEmail();
        this.uuid = userInfo.getUuid();
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
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

    public String toString(){
        String info = "Name: " + this.username + " /Email: " + this.email + "/uuid: " + this.uuid;
        return info;
    }
}
