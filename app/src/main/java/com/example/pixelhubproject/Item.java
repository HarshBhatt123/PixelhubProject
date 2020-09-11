package com.example.pixelhubproject;

public class Item {

    private String mImageUrl;
    private String mCreator;

    public Item(String imageUrl, String creator) {
        mImageUrl = imageUrl;
        mCreator = creator;

    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public String getCreator() {
        return mCreator;
    }
}
