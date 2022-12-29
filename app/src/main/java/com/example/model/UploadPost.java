package com.example.model;

public class UploadPost {
    // this class is use for upload event purpose

    //String Info
    private String userId;
    private String postDetail;
    private String postImageUrl;

    public UploadPost(){

    }

    public UploadPost(String postImageUrl, String postDetail, String userId){
        this.userId = userId;
        this.postDetail = postDetail.trim();
        this.postImageUrl = postImageUrl;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String mPostImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getPostDetail() {
        return postDetail;
    }

    public void setPostDetail(String eventDetail) {
        this.postDetail = postDetail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
