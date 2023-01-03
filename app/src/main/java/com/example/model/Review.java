package com.example.model;

import java.util.Comparator;

public class Review implements Comparable<Review> {
    private String reviewId;
    private Float rating;
    private String comment;
    private String organiserId;
    private String userId;
    private String username;
    private String eventId;
    private String date;
    private int likeCount;
    private int dislikeCount;

    public Review() {
    }

    public Review(Float rating, String comment, String organiserId, String userId, String username, String eventId, String date, int likeCount, int dislikeCount) {
        this.rating = rating;
        this.comment = comment.trim();
        this.organiserId = organiserId;
        this.userId = userId.trim();
        this.username = username.trim();
        this.eventId = eventId.trim();
        this.date = date.trim();
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    public String getOrganiserId() {
        return organiserId;
    }

    public void setOrganiserId(String organiserId) {
        this.organiserId = organiserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    @Override
    public int compareTo(Review review) {
        return this.likeCount - review.likeCount;
    }
}
