package com.example.model;

public class Comment {

    private String commenterId;
    private String commentDetail;
    private String commenterProfileImage;
    private String commenterUsername;

    public Comment() {

    }

    public Comment(String commenterId, String commentDetail) {
        this.commenterId = commenterId;
        this.commentDetail = commentDetail;
    }

    public Comment(String commenterId, String commentDetail, String commenterProfileImage, String commenterUsername) {
        this.commentDetail = commentDetail;
        this.commenterId = commenterId;
        this.commenterProfileImage = commenterProfileImage;
        this.commenterUsername = commenterUsername;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }

    public String getCommentDetail() {
        return commentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.commentDetail = commentDetail;
    }

    public String getCommenterProfileImage() {
        return commenterProfileImage;
    }

    public void setCommenterProfileImage(String commenterProfileImage) {
        this.commenterProfileImage = commenterProfileImage;
    }

    public String getCommenterUsername() {
        return commenterUsername;
    }

    public void setCommenterUsername(String commenterUsername) {
        this.commenterUsername = commenterUsername;
    }
}
