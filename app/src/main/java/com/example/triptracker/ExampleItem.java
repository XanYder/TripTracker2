package com.example.triptracker;

public class ExampleItem {
    private int mImageResource;
    private String mText1;
    private  String mText2;
    private String mDescription;

    public ExampleItem(int imageResource, String text1, String text2, String description) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mDescription = description;
    }

    public String getDiscription() {
        return mDescription;
    }

    public int getImageResource(){
        return mImageResource;
    }
    public String getText1(){
        return mText1;
    }
    public String getText2(){
        return mText2;
    }

}
