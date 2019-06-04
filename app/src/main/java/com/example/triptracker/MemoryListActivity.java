package com.example.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
                    count += 1;
                } else if (count == 3) {
                    String description = lines;
                    exampleList.add(new ExampleItem(R.drawable.pic5, itemName, date, description));
                    count = 1;
                }

            }
            return exampleList;

        } catch (IOException e){
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
        final ArrayList<ExampleItem> exampleList = createMemories();




        if (createMemories() != null){

            int listSize = exampleList.size();


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
                    startActivity(intent);
                }
            });
        }





    }
}
