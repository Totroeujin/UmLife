package com.example.model;

public class UploadPost {
    // this class is use for upload event purpose

    //String Info
    private String userId;
    private String postDetail;
    private String postImageUrl;
    private String userName;

    public UploadPost(){

    }
    public UploadPost(String postImageUrl, String postDetail, String userId){
        this.userId = userId;
        this.postDetail = postDetail;
        this.postImageUrl = postImageUrl;
    }

    public UploadPost(String postImageUrl, String postDetail, String userName, String userId){
        this.userId = userId;
        this.postDetail = postDetail;
        this.userName = userName;
        this.postImageUrl = postImageUrl;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getPostDetail() {
        return postDetail;
    }

    public void setPostDetail(String postDetail) {
        this.postDetail = postDetail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }
}
