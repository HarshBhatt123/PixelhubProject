package com.example.pixelhubproject;

public class Item {

    private String mImageUrl;
    private String mCreator;
    private String mDownload;


    public Item(String imageUrl, String creator,String download) {
        mImageUrl = imageUrl;
        mCreator = creator;
        mDownload = download;

    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public String getDownload() {
        return mDownload;
    }
    public String getCreator() {
        return mCreator;
    }
}
