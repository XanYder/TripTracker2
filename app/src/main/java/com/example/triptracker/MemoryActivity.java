package com.example.triptracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
//import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
//import android.widget.MediaController;
import android.widget.TextView;
//import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class MemoryActivity extends AppCompatActivity {
    private ArrayList<String> mImages, mVideos, mImagesURI, mVideosURI;
    private ArrayList<ImageView> allImages = new ArrayList<>();
    private ArrayList<VideoView> allVideos = new ArrayList<>();

    public void setTextinFile(ArrayList<String> texts) {
        FileOutputStream outputStream;

        try {
            deleteFile("memories.txt");
            outputStream = openFileOutput("memories.txt", Context.MODE_APPEND);

            for (String item : texts) {
                item = item + "\n";
                outputStream.write(item.getBytes());
            }
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeLine() {
        ArrayList<String> allText = new ArrayList<>();
        ArrayList<String> remove = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("memories.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String lines = "";


            TextView title = findViewById(R.id.theTitle);

            boolean found = false;

            while (lines != null) {
                lines = bufferedReader.readLine();
                allText.add(lines);

            }
            Iterator<String> iter = allText.iterator();
            while (iter.hasNext()) {
                String line = iter.next();

                if (line != null) {
                    if (line.substring(0, 3).equals("tit") == false) {
                        iter.remove();
                        Log.d("test", iter.next());

                    } else {
                        found = false;
                    }


                    if (line.substring(0, 3).equals("tit")) {
                        iter.remove();
                        Log.d("test", iter.next());
                        found = true;

                    }
                }

            }

            //allText.removeAll(remove);


            if (allText.size() > 0) {
                setTextinFile(allText);
            } else {

                deleteFile("memories.txt");
            }

        } catch (Exception e) {
            Log.d("error1", e.toString());
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

        final TextView location = findViewById(R.id.location);
        location.setText(getIntent().getStringExtra("location"));
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoryActivity.this, MapsActivity.class);
                intent.putExtra("location", String.valueOf(location.getText()));
                startActivity(intent);
            }
        });

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
            fake.setVideoURI(myUri);

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
                removeLine();
                startActivity(new Intent(MemoryActivity.this, MemoryListActivity.class));
            }
        });
    }

}
