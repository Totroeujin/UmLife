package com.example.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class EventInfo implements Parcelable {
    // this class is use for store event after the data fetched

    //Image Info
    private String mImageUrl;

    //String Info
    private String eventName;
    private String openRegistration;
    private String endRegistration;
    private String eventDetail;
    private String organiserId;
    private String eventDate;
    private String eventVenue;
    private String eventId;
    private String status;
    private int participation = 0;

    public EventInfo(){

    }

    public EventInfo(String eventImage, String eventName, String openRegistration, String endRegistration, String eventDetail, String organiserEmail, String organiserUuid,
                     String date, String venue){
        this.mImageUrl = eventImage;
        this.eventName = eventName;
        this.openRegistration = openRegistration;
        this.endRegistration = endRegistration;
        this.eventDetail = eventDetail;
        this.organiserId = organiserId;
        this.eventDate = date;
        this.eventVenue = venue;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getParticipation() {
        return participation;
    }

    public void setParticipation(int participation) {
        this.participation = participation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mImageUrl);
        parcel.writeString(eventName);
        parcel.writeString(openRegistration);
        parcel.writeString(endRegistration);
        parcel.writeString(eventDetail);
        parcel.writeString(organiserId);
        parcel.writeString(eventDate);
        parcel.writeString(eventVenue);
        parcel.writeString(status);
        parcel.writeString(eventId);
        parcel.writeInt(participation);
    }

    public static final Parcelable.Creator<EventInfo> CREATOR = new Parcelable.Creator<EventInfo>() {
        public EventInfo createFromParcel(Parcel in) {
            return new EventInfo(in);
        }

        public EventInfo[] newArray(int size) {
            return new EventInfo[size];
        }
    };

    private EventInfo(Parcel in) {
        mImageUrl = in.readString();
        eventName = in.readString();
        openRegistration = in.readString();
        endRegistration = in.readString();
        eventDetail = in.readString();
        organiserId = in.readString();
        eventDate = in.readString();
        eventVenue = in.readString();
        status = in.readString();
        eventId = in.readString();
        participation = in.readInt();
    }
}
