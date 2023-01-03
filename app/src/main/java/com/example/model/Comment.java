package com.example.model;

public class Comment {

    private String commenterId;
    private String commentDetail;

    public Comment() {

    }

    public Comment(String commenterId, String commentDetail) {
        this.commenterId = commenterId;
        this.commentDetail = commentDetail;
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
}
