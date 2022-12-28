package com.example.umlife;

import java.util.ArrayList;
import java.util.Date;

public class Review {
    private ArrayList<String> username;
    private ArrayList<Integer> image;
    private ArrayList<Double> ratings;
    private ArrayList<String> date;
    private ArrayList<String> comments;

    public Review(ArrayList<String> username, ArrayList<Integer> image, ArrayList<Double> ratings, ArrayList<String> date, ArrayList<String> comments) {
        this.username = username;
        this.image = image;
        this.ratings = ratings;
        this.date = date;
        this.comments = comments;
    }

    public ArrayList<String> getUsername() {
        return username;
    }

    public void setUsername(ArrayList<String> username) {
        this.username = username;
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Double> ratings) {
        this.ratings = ratings;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public ArrayList<Integer> getImage() {
        return image;
    }

    public void setImage(ArrayList<Integer> image) {
        this.image = image;
    }
}
