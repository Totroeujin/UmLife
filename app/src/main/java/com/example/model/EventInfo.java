package com.example.model;

import android.net.Uri;

public class EventInfo {
    //Image Info
    private Uri eventImage;

    //String Info
    private String eventName;
    private String openRegistration;
    private String endRegistration;
    private String eventDetail;
    private String organiserEmail;

    public EventInfo(){

    }

    public EventInfo(Uri eventImage, String eventName, String openRegistration, String endRegistration, String eventDetail, String organiserEmail){
        this.eventImage = eventImage;
        this.eventName = eventName;
        this.openRegistration = openRegistration;
        this.endRegistration = endRegistration;
        this.eventDetail = eventDetail;
        this.organiserEmail = organiserEmail;
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

    public Uri getEventImage() {
        return eventImage;
    }

    public void setEventImage(Uri eventImage) {
        this.eventImage = eventImage;
    }
}
