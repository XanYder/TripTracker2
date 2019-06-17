package com.example.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
//import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MemoryListActivity extends AppCompatActivity {
    public Integer amount = 0;

    public ArrayList<ExampleItem> createMemoryList() {
        ArrayList<ExampleItem> exampleList = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("memories.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String lines;
            String itemName = "";
            String date = "";
            String description = "";
            String location = "";
            ArrayList<String> images = new ArrayList<String>();
            ArrayList<String> videos = new ArrayList<String>();
            ArrayList<String> imageURI = new ArrayList<String>();
            ArrayList<String> videosURI = new ArrayList<String>();

            try {
                lines = bufferedReader.readLine().replace("/", "//");
                lines = lines.replace("////", "//");
            } catch (Exception e) {
                lines = null;
            }
            while (lines != null) {

                String identify = lines.substring(0, 4);


                if (identify.equals("titl")) {
                    if (itemName.equals("") == false) {
                        exampleList.add(new ExampleItem(R.drawable.pic5, itemName, date, description, location, images, videos, imageURI, videosURI));
                    }
                    itemName = "";
                    date = "";
                    description = "";
                    location = "";
                    images = new ArrayList<String>();
                    videos = new ArrayList<String>();
                    imageURI = new ArrayList<String>();
                    videosURI = new ArrayList<String>();
                    amount += 1;

                    itemName = lines.substring(5);
                } else if (identify.equals("date")) {
                    date = lines.substring(4);
                } else if (identify.equals("desc")) {
                    description = lines.substring(6);
                } else if (identify.equals("loca")) {
                    location = lines.substring(3);
                } else if (identify.equals("vidu")) {
                    videosURI.add(lines.substring(6));
                } else if (identify.equals("impa")) {
                    images.add(lines.substring(6));
                } else if (identify.equals("imur")) {
                    imageURI.add(lines.substring(5));
                } else if (identify.equals("vipa")) {
                    videos.add(lines.substring(6));
                }

                try {
                    lines = bufferedReader.readLine().replace("/", "//");
                    lines = lines.replace("////", "//");
                } catch (Exception e) {
                    lines = null;
                }


            }
            exampleList.add(new ExampleItem(R.drawable.pic5, itemName, date, description, location, images, videos, imageURI, videosURI));
            return exampleList;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private RecyclerView mRecycleView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorylist);
        createButton();
        homeButton();
        mapButton();

        final ArrayList<ExampleItem> exampleList = createMemoryList();


        if (createMemoryList() != null) {

            //int listSize = exampleList.size();



            mRecycleView = findViewById(R.id.recyclerView);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new ExampleAdapter(exampleList);

            mRecycleView.setLayoutManager(mLayoutManager);
            mRecycleView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(MemoryListActivity.this, MemoryActivity.class);
                    intent.putExtra("title", String.valueOf(exampleList.get(position).getText1()));
                    intent.putExtra("description", String.valueOf(exampleList.get(position).getDiscription()));
                    intent.putExtra("location", String.valueOf(exampleList.get(position).getLocation()));
                    intent.putStringArrayListExtra("images", exampleList.get(position).getmImages());
                    intent.putStringArrayListExtra("videos", exampleList.get(position).getmVideos());
                    intent.putStringArrayListExtra("imagesURI", exampleList.get(position).getmImagesURI());
                    intent.putStringArrayListExtra("videosURI", exampleList.get(position).getmVideosURI());
                    intent.putExtra("date", exampleList.get(position).getmText2());

                    startActivity(intent);
                }
            });
        }
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
                startActivity(new Intent(MemoryListActivity.this, MapsActivity.class));
            }
        });
    }

    private void createButton() {
        ImageButton createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemoryListActivity.this, Main2Activity.class));
            }
        });
    }

}
