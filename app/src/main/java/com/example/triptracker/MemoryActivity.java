package com.example.triptracker;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class MemoryActivity extends AppCompatActivity {
    private ArrayList<String> mImages, mVideos, mImagesURI, mVideosURI;
    private ArrayList<ImageView> allImages = new ArrayList<ImageView>();
    private ArrayList<VideoView> allVideos = new ArrayList<VideoView>();

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        ViewPager viewPager = findViewById(R.id.imageSlider);

        TextView title = findViewById(R.id.theTitle);
        title.setText(getIntent().getStringExtra("title"));

        TextView description = findViewById(R.id.description);
        description.setText(getIntent().getStringExtra("description"));

        TextView location = findViewById(R.id.location);
        location.setText(getIntent().getStringExtra("location"));

        mImages = getIntent().getStringArrayListExtra("images");
        mVideos = getIntent().getStringArrayListExtra("videos");
        mImagesURI = getIntent().getStringArrayListExtra("imagesURI");
        mVideosURI = getIntent().getStringArrayListExtra("videosURI");


        ImageView button = findViewById(R.id.shareButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = getIntent().getStringExtra("description");
                //String shareSub = getIntent().getStringExtra("title");
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share using"));
            }
        });


        for (String item : mImages) {
            ImageView fake = new ImageView(this);
            Bitmap bmImg = BitmapFactory.decodeFile(item);
            fake.setImageBitmap(bmImg);
            allImages.add(fake);
        }

        for (String item : mImagesURI) {
            ImageView fake = new ImageView(this);
            Uri myUri = Uri.parse(item);
            fake.setImageURI(myUri);
            allImages.add(fake);
        }

        for (String item : mVideos) {
            VideoView fake = new VideoView(this);
            fake.setVideoPath(item);
            allVideos.add(fake);
        }

        for (String item : mVideosURI) {
            VideoView fake = new VideoView(this);
            Uri myUri = Uri.parse(item);
            fake.setVideoPath(myUri.getPath());
            allVideos.add(fake);
        }


        ImageAdapter adapter = new ImageAdapter(this, allImages, allVideos);
        viewPager.setAdapter(adapter);


        homeButton();
        mapButton();
        createButton();
        deleteButton();

    }

    private void homeButton() {
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void mapButton() {
        ImageButton mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemoryActivity.this, MapsActivity.class));
            }
        });
    }

    private void createButton() {
        ImageButton createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemoryActivity.this, Main2Activity.class));
            }
        });
    }

    private void deleteButton() {
        ImageButton deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemoryActivity.this, MemoryListActivity.class));
            }
        });
    }

}

