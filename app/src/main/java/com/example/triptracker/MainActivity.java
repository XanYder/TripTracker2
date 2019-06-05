package com.example.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
                } else if (count == 2) {
                    count += 1;
                } else if (count > 2) {
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

    public int amount = 0;

    public ArrayList<ExampleItem> createMemoryList() {
        ArrayList<ExampleItem> exampleList = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("memories.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String lines;
            int count = 1;
            String itemName = "";
            String date = "";
            while ((lines = bufferedReader.readLine()) != null) {
                if (count == 1) {
                    itemName = lines;
                    count += 1;
                    amount += 1;
                } else if (count == 2) {
                    date = lines;
                    count += 1;
                } else if (count == 3) {
                    String description = lines;
                    exampleList.add(new ExampleItem(R.drawable.pic5, itemName, date, description));
                    count = 1;
                }
            }
            return exampleList;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<ExampleItem> exampleList = createMemoryList();
        TextView memory1 = findViewById(R.id.memory1);
        TextView memory2 = findViewById(R.id.memory2);
        TextView memory3 = findViewById(R.id.memory3);

        getTextFromFile(0);


        TextView[] memoryArray = {memory1,memory2,memory3};

        for (int x = 0; x< 3 && x < memories; x++){
            memoryArray[x].setText(getTextFromFile(x));
        }

        if (amount > 0) {
            final int click1 = exampleList.size() - 1;
            ImageView one = findViewById(R.id.imageView3);
            one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MemoryActivity.class);
                    intent.putExtra("title", String.valueOf(exampleList.get(click1).getText1()));
                    intent.putExtra("description", String.valueOf(exampleList.get(click1).getDiscription()));
                    startActivity(intent);
                }
            });
            if (amount > 1) {
                final int click2 = exampleList.size() - 2;
                ImageView two = findViewById(R.id.imageView8);
                two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MemoryActivity.class);
                        intent.putExtra("title", String.valueOf(exampleList.get(click2).getText1()));
                        intent.putExtra("description", String.valueOf(exampleList.get(click2).getDiscription()));
                        startActivity(intent);
                    }
                });
                if (amount > 2) {
                    final int click3 = exampleList.size() - 3;
                    ImageView three = findViewById(R.id.imageView9);
                    three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, MemoryActivity.class);
                            intent.putExtra("title", String.valueOf(exampleList.get(click3).getText1()));
                            intent.putExtra("description", String.valueOf(exampleList.get(click3).getDiscription()));
                            startActivity(intent);
                        }
                    });
                }
            }
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
        createButton();


    }

    private void mapButton() {
        ImageButton mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });
    }

    private void createButton() {
        ImageButton createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
    }

}
