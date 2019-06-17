package com.example.triptracker;

import android.util.Log;

import java.util.ArrayList;

public class ExampleItem {
    private int mImageResource;
    private String mText1;
    private  String mText2;
    private String mDescription;
    private String mLocation;
    private ArrayList<String> mImages, mVideos, mImagesURI, mVideosURI;

    public ExampleItem(int imageResource, String text1, String text2, String description, String location, ArrayList<String> images, ArrayList<String> videos, ArrayList<String> imagesURI, ArrayList<String> videosURI) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mDescription = description;
        mLocation = location;
        mImages = images;
        mVideos = videos;
        mImagesURI = imagesURI;
        mVideosURI = videosURI;
    }

    public String getDiscription() {
        if (mDescription.equals("")) {
            return mDescription;
        } else {
            return mDescription.substring(5);
        }
    }

    public String getmText2() {
        return mText2;
    }

    public ArrayList<String> getmImages() {
        return mImages;
    }

    public ArrayList<String> getmImagesURI() {
        return mImagesURI;
    }

    public ArrayList<String> getmVideos() {
        return mVideos;
    }

    public ArrayList<String> getmVideosURI() {
        return mVideosURI;
    }

    public String getLocation() {
        if (mLocation.equals("")) {
            return mLocation;
        } else {
            return mLocation.substring(5);
        }

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
