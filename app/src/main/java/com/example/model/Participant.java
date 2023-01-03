package com.example.model;

public class Participant {
    private String uuid;
    private String eventID;
    private String name;
    private String age;
    private String email;
    private String phoneNum;
    private String course;
    private String address;

    public Participant(){

    }

    public Participant(Participant participant){
        this.uuid = participant.uuid;
        this.eventID = participant.eventID;
        this.name = participant.name;
        this.age = participant.age;
        this.email = participant.email;
        this.phoneNum = participant.phoneNum;
        this.course = participant.course;
        this.address = participant.address;
    }

    public Participant(String uuid, String eventID, String name, String age, String email, String phoneNum, String course, String address){
        this.uuid = uuid;
        this.eventID = eventID;
        this.name = name.trim();
        this.age = age.trim();
        this.email = email.trim();
        this.phoneNum = phoneNum.trim();
        this.course = course.trim();
        this.address = address.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
