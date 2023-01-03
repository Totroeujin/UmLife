package com.example.model;

import java.util.List;

public class Post {
    // this class is use for upload event purpose

    //String Info
    private String userId;
    private String postDetail;
    private String postImageUrl;
    private String userName;

    private String postId;
    private List<Comment> commentList;

    public Post() {

    }

    public Post(String postImageUrl, String postDetail, String userId){
        this.userId = userId;
        this.userName = userName;
        this.postDetail = postDetail.trim();
        this.postImageUrl = postImageUrl;
    }

    public Post(String userId, String postDetail, String postImageUrl, String userName, String postId, List<Comment> commentList) {
        this.userId = userId;
        this.postDetail = postDetail;
        this.postImageUrl = postImageUrl;
        this.userName = userName;
        this.postId = postId;
        this.commentList = commentList;
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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}

