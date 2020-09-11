package com.example.pixelhubproject;

public class Upload {

    private String mName;
    private String mImageUrl;
    public Upload() {
        //empty constructor needed for uploadd
    }
    public Upload(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "Anonymous";
        }
        mName = name;
        mImageUrl = imageUrl;
    }
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
