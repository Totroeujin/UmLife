package com.example.model;

public class UploadEvent {
    // this class is use for upload event purpose

    //String Info
    private String uuid;
    private String eventName;
    private String openRegistration;
    private String endRegistration;
    private String eventDetail;
    private String organiserEmail;
    private String mImageUrl;
    public UploadEvent(){

    }

    public UploadEvent(String mImageUrl, String name, String openRegistration, String endRegistration, String eventDetail, String organiserEmail, String organiserUuid ){
        this.uuid = organiserUuid;
        this.eventName = name.trim();
        this.mImageUrl = mImageUrl;
        this.openRegistration = openRegistration.trim();
        this.endRegistration = endRegistration.trim();
        this.eventDetail = eventDetail.trim();
        this.organiserEmail = organiserEmail.trim();
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

    public String getOrganiserEmail() {
        return organiserEmail;
    }

    public void setOrganiserEmail(String organiserEmail) {
        this.organiserEmail = organiserEmail;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
