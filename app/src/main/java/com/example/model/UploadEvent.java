package com.example.model;

public class UploadEvent {
    // this class is use for upload event purpose

    //String Info
    private String organiserId;
    private String eventName;
    private String openRegistration;
    private String endRegistration;
    private String eventDetail;
    private String mImageUrl;
    private String eventDate;
    private String eventVenue;
    private String status;
    public UploadEvent(){

    }

    public UploadEvent(String mImageUrl, String name, String openRegistration, String endRegistration, String eventDetail, String organiserId, String date, String venue, String status ){
        this.organiserId = organiserId;
        this.eventName = name.trim();
        this.openRegistration = openRegistration.trim();
        this.endRegistration = endRegistration.trim();
        this.eventDetail = eventDetail.trim();
        this.eventDate = date.trim();
        this.eventVenue = venue.trim();
        this.mImageUrl = mImageUrl;
        this.status = status;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrganiserId() {
        return organiserId;
    }

    public void setOrganiserId(String organiserId) {
        this.organiserId = organiserId;
    }
}
