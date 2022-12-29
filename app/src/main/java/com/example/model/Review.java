package com.example.model;

public class Review {
    private Float rating;
    private String comment;
    private String date;

    public Review(Float rating, String comment, String date) {
        this.rating = rating;
        this.comment = comment;
        this.date = date;
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
}
