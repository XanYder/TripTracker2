package com.example.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    int memories = 0;

    public String getTextFromFile(int index){
        List memNames = new ArrayList();
        memories = 0;
        try {
            FileInputStream fis = openFileInput("memories.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);


            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String lines;
            int count = 1;
            while ((lines = bufferedReader.readLine()) != null){
                if (count == 1) {
                    memNames.add(lines + "\n");
                    memories += 1;
                    count += 1;
                }
                else{
                    count = 1;
                }
            }
            Collections.reverse(memNames);
            return String.valueOf(memNames.get(index));

        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView memory1 = findViewById(R.id.memory1);
        TextView memory2 = findViewById(R.id.memory2);
        TextView memory3 = findViewById(R.id.memory3);

        getTextFromFile(0);


        TextView[] memoryArray = {memory1,memory2,memory3};

        for (int x = 0; x< 3 && x < memories; x++){
            memoryArray[x].setText(getTextFromFile(x));
        }

        textView = findViewById(R.id.your_memories);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemoryListActivity.class);
                startActivity(intent);
            }
        });

        textView = findViewById(R.id.map);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });



        mapButton();
        listButton();


    }

    private void mapButton() {
        ImageButton mapButton = (ImageButton) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });
    }

    private void listButton() {
        ImageButton listButton = (ImageButton) findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MemoryListActivity.class));
            }
        });
    }

}
