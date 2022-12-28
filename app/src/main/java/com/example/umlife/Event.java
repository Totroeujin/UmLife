package com.example.umlife;

import java.util.ArrayList;

public class Event {
    private ArrayList<String> Name;
    private ArrayList<Integer> Image;
    private ArrayList<String> Date;
    private ArrayList<String> Venue;
    private ArrayList<Integer> NumberOfParticipants;
    private ArrayList<String> OrganiserName;
    private ArrayList<Integer> OrganiserImage;
    private ArrayList<String> RegistrationDate;
    private ArrayList<Double> Rating;
    private ArrayList<Integer> NumberOfRatings;
    private ArrayList<String> Info;
    private ArrayList<Review> Reviews;

    public Event(ArrayList<String> name, ArrayList<Integer> image, ArrayList<String> date, ArrayList<String> venue,
                 ArrayList<Integer> numberOfParticipants, ArrayList<String> organiserName, ArrayList<Integer> organiserImage,
                 ArrayList<String> registrationDate, ArrayList<Double> rating, ArrayList<Integer> numberOfRatings, ArrayList<String> info, ArrayList<Review> reviews) {
        Name = name;
        Image = image;
        Date = date;
        Venue = venue;
        NumberOfParticipants = numberOfParticipants;
        OrganiserName = organiserName;
        OrganiserImage = organiserImage;
        RegistrationDate = registrationDate;
        Rating = rating;
        NumberOfRatings = numberOfRatings;
        Info = info;
        Reviews = reviews;
    }

    public ArrayList<String> getName() {
        return Name;
    }

    public void setName(ArrayList<String> name) {
        Name = name;
    }

    public ArrayList<Integer> getImage() {
        return Image;
    }

    public void setImage(ArrayList<Integer> image) {
        Image = image;
    }

    public ArrayList<String> getDate() {
        return Date;
    }

    public void setDate(ArrayList<String> date) {
        Date = date;
    }

    public ArrayList<String> getVenue() {
        return Venue;
    }

    public void setVenue(ArrayList<String> venue) {
        Venue = venue;
    }

    public ArrayList<Integer> getNumberOfParticipants() {
        return NumberOfParticipants;
    }

    public void setNumberOfParticipants(ArrayList<Integer> numberOfParticipants) {
        NumberOfParticipants = numberOfParticipants;
    }

    public ArrayList<String> getOrganiserName() {
        return OrganiserName;
    }

    public void setOrganiserName(ArrayList<String> organiserName) {
        OrganiserName = organiserName;
    }

    public ArrayList<Integer> getOrganiserImage() {
        return OrganiserImage;
    }

    public void setOrganiserImage(ArrayList<Integer> organiserImage) {
        OrganiserImage = organiserImage;
    }

    public ArrayList<String> getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(ArrayList<String> registrationDate) {
        RegistrationDate = registrationDate;
    }

    public ArrayList<Double> getRating() {
        return Rating;
    }

    public void setRating(ArrayList<Double> rating) {
        Rating = rating;
    }

    public ArrayList<Integer> getNumberOfRatings() {
        return NumberOfRatings;
    }

    public void setNumberOfRatings(ArrayList<Integer> numberOfRatings) {
        NumberOfRatings = numberOfRatings;
    }

    public ArrayList<String> getInfo() {
        return Info;
    }

    public void setInfo(ArrayList<String> info) {
        Info = info;
    }

    public ArrayList<Review> getReviews() {
        return Reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        Reviews = reviews;
    }
}
