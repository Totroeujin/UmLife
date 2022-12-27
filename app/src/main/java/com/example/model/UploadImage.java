package com.example.model;

public class UploadImage {
    private String mName;
    private String mImageUrl;

    public UploadImage(){

    }

    public UploadImage(String name, String mImageUrl){
        if(name.trim().equals("")){
            name = "No name";
        }
        this.mName = name;
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
