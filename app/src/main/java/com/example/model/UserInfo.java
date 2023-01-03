package com.example.model;

import com.google.firebase.firestore.auth.User;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {
    private String username;
    private String email;
    private String uuid;
    private List<String> redeemedRewardsName;
    private String profileImage;

    public UserInfo(){

    }

    public UserInfo(String username, String email, String uuid, List<String> redeemedRewardsName){
        this.uuid = uuid;
        this.email = email;
        this.username = username;
        this.redeemedRewardsName = redeemedRewardsName;
    }

    public UserInfo(String username, String email, String password, String uuid, List<String> redeemedRewardsName){
        this.uuid = uuid;
        this.email = email;
        this.username = username;
        this.redeemedRewardsName = redeemedRewardsName;
    }

    public UserInfo(String username, String email, String password, String uuid, List<String> redeemedRewardsName, String profileImage){
        this.uuid = uuid;
        this.email = email;
        this.username = username;
        this.redeemedRewardsName = redeemedRewardsName;
        this.profileImage = profileImage;
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

    public String toString(){
        String info = "Name: " + this.username + " /Email: " + this.email + "/uuid: " + this.uuid;
        return info;
    }

    public List<String> getRedeemedRewardsName() {
        return redeemedRewardsName;
    }

    public void setRedeemedRewardsName(List<String> redeemedRewardsName) {
        this.redeemedRewardsName = redeemedRewardsName;
    }

    public String getPassword(){
        return "";
    }

    public void setPassword(String password) {

    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String userImageUrl) {
        this.profileImage = userImageUrl;
    }
}
