package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class EventInfo {
    // this class is use for store event after the data fetched

    //Image Info
    private String mImageUrl;

    //String Info
    private String eventName;
    private String openRegistration;
    private String endRegistration;
    private String eventDetail;
    private String organiserEmail;
    private String uuid;
    private String eventDate;
    private String eventVenue;
    private Float overallRating;
    private List<Review> review = new ArrayList<>();

    public EventInfo(){

    }

    public EventInfo(String eventImage, String eventName, String openRegistration, String endRegistration, String eventDetail, String organiserEmail, String organiserUuid,
                     String date, String venue){
        this.mImageUrl = eventImage;
        this.eventName = eventName;
        this.openRegistration = openRegistration;
        this.endRegistration = endRegistration;
        this.eventDetail = eventDetail;
        this.organiserEmail = organiserEmail;
        this.uuid = organiserUuid;
        this.eventDate = date;
        this.eventVenue = venue;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOpenRegistration() {
        return openRegistration;
    }

    public void setOpenRegistration(String openRegistration) {
        this.openRegistration = openRegistration;
    }

    public String getEndRegistration() {
        return endRegistration;
    }

    public void setEndRegistration(String endRegistration) {
        this.endRegistration = endRegistration;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }

    public String getOrganiserEmail() {
        return organiserEmail;
    }

    public void setOrganiserEmail(String organiserEmail) {
        this.organiserEmail = organiserEmail;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public Float getOverallRating() {
        int rating = 0;
        if(!review.isEmpty()) {
            for (int i = 0; !review.isEmpty(); i++) {
                rating += Integer.parseInt(review.get(i).getRating().toString());
            }
            rating /= review.size();
        }
        return Float.valueOf(String.valueOf(rating));
    }

    public void setOverallRating(Float overallRating) {
        this.overallRating = overallRating;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review.add(review);
    }
}
