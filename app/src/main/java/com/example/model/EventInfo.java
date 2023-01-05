package com.example.model;

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
    private String organiserId;
    private String eventDate;
    private String eventVenue;
    private String eventId;

    public EventInfo(){

    }

    public EventInfo(String eventImage, String eventName, String openRegistration, String endRegistration, String eventDetail, String organiserId,
                     String date, String venue){
        this.mImageUrl = eventImage;
        this.eventName = eventName;
        this.openRegistration = openRegistration;
        this.endRegistration = endRegistration;
        this.eventDetail = eventDetail;
        this.organiserId = organiserId;
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

    public String getOrganiserId() {
        return organiserId;
    }

    public void setOrganiserId(String organiserId) {
        this.organiserId = organiserId;
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
