package com.example.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MemoryListActivity extends AppCompatActivity {

    public ArrayList<ExampleItem> createMemories(){
        ArrayList<ExampleItem> exampleList = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("memories.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String lines;

            int count = 1;
            String itemName = "";
            String date = "";
            while ((lines = bufferedReader.readLine()) != null){
                if (count == 1){
                    itemName = lines;
                    count += 1;
                }
                else if (count == 2) {
                    date = lines;
                    count = 1;
                    exampleList.add(new ExampleItem(R.drawable.pic5, itemName, date));

                }

            }
            return exampleList;

        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorylist);
        homeButton();
        listButton();
        mapButton();





        if (createMemories() != null){
            ArrayList<ExampleItem> exampleList = createMemories();
            int listSize = exampleList.size();


            mRecycleView = findViewById(R.id.recyclerView);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new ExampleAdapter(exampleList);

            mRecycleView.setLayoutManager(mLayoutManager);
            mRecycleView.setAdapter(mAdapter);
        }




    }

    private void homeButton() {
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void mapButton() {
        ImageButton mapButton = (ImageButton) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemoryListActivity.this, MapsActivity.class));
            }
        });
    }

    private void listButton() {
        ImageButton listButton = (ImageButton) findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemoryListActivity.this, MemoryListActivity.class));
            }
        });
    }
}
