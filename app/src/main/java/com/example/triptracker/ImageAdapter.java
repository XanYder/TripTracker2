package com.example.triptracker;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<ImageView> mImageIds = new ArrayList<ImageView>();
    private ArrayList<VideoView> mVideoIds = new ArrayList<VideoView>();


    public ImageAdapter(Context context, ArrayList<ImageView> images, ArrayList<VideoView> videos) {
        mImageIds = images;
        mVideoIds = videos;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImageIds.size() + mVideoIds.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (position < mImageIds.size()) {
            ImageView imageView = new ImageView(mContext);
            imageView = mImageIds.get(position);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            container.addView(imageView, 0);
            return imageView;
        } else {
            VideoView videoView = new VideoView(mContext);
            videoView = mVideoIds.get(position - mImageIds.size());
            container.addView(videoView, 0);
            final VideoView theVideo = videoView;
            theVideo.start();
            theVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    theVideo.start();
                }
            });
            return theVideo;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        try {
            container.removeView((ImageView) object);
        } catch (Exception e) {
            container.removeView((VideoView) object);
        }

    }
}

