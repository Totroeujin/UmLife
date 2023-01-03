package com.example.model;

public class Post {
    // this class is use for upload event purpose

    //String Info
    private String userId;
    private String postDetail;
    private String postImageUrl;
    private String userName;

    private String postId;

    public Post() {

    }

    public Post(String postImageUrl, String postDetail, String userId){
        this.userId = userId;
        this.userName = userName;
        this.postDetail = postDetail.trim();
        this.postImageUrl = postImageUrl;
    }

    public String getPostId() { return postId; }

    public void setPostId(String postId) { this.postId = postId; }

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

