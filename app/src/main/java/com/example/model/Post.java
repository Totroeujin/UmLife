package com.example.model;

import java.util.List;

public class Post {
    // this class is use for upload event purpose

    //String Info
    private String postUserId;
    private String postDetail;
    private String postImageUrl;
    private List<Comment> commentList;

    String postId = "";
    String postUserImageUrl = "";
    String postUsername = "";

    public Post() {

    }

    public Post(String postUserId, String postDetail, String postImageUrl){
        this.postUserId = postUserId;
        this.postDetail = postDetail.trim();
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

    public String getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(String userId) {
        this.postUserId = userId;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostUserImageUrl() {
        return postUserImageUrl;
    }

    public void setPostUserImageUrl(String postUserImageUrl) {
        this.postUserImageUrl = postUserImageUrl;
    }

    public String getPostUsername() {
        return postUsername;
    }

    public void setPostUsername(String postUsername) {
        this.postUsername = postUsername;
    }
}

