package com.example.triptracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    public ArrayList<ExampleItem> reverse(ArrayList<ExampleItem> list) {
        if (list.size() > 1) {
            ExampleItem value = list.remove(0);
            reverse(list);
            list.add(value);
        }
        return list;
    }

    public String getTextFromFile(int index){
        List<String> memNames = new ArrayList<>();
        memories = 0;
        try {
            FileInputStream fis = openFileInput("memories.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);


            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String lines;
            while ((lines = bufferedReader.readLine()) != null){
                String identify = lines.substring(0, 4);
                Log.d("identify", identify);
                if (identify.equals("titl")) {

                    memNames.add(lines.substring(5));
                    memories += 1;
                }
            }
            Collections.reverse(memNames);
            try {
                return String.valueOf(memNames.get(index));
            } catch (Exception e) {
                return "Nothing";
            }

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
            String itemName = "";
            String date = "";
            String description = "";
            String location = "";
            ArrayList<String> images = new ArrayList<String>();
            ArrayList<String> videos = new ArrayList<String>();
            ArrayList<String> imageURI = new ArrayList<String>();
            ArrayList<String> videosURI = new ArrayList<String>();

            while ((lines = bufferedReader.readLine()) != null) {
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
                }


            }
            exampleList.add(new ExampleItem(R.drawable.pic5, itemName, date, description, location, images, videos, imageURI, videosURI));
            return reverse(exampleList);

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


        //.makeText(this, String.valueOf(amount), Toast.LENGTH_SHORT).show();

        TextView[] memoryArray = {memory1,memory2,memory3};

        for (int x = 0; x< 3 && x < memories; x++){
            memoryArray[x].setText(getTextFromFile(x));
        }

        if (amount > 0) {
            ImageView memoryPic1 = findViewById(R.id.imageView3);
            if (exampleList.get(0).getmImages().size() > 0) {
                Bitmap bmImg = BitmapFactory.decodeFile(exampleList.get(0).getmImages().get(0));
                memoryPic1.setImageBitmap(bmImg);
            } else if (exampleList.get(0).getmImagesURI().size() > 0) {
                Uri myUri = Uri.parse(exampleList.get(0).getmImagesURI().get(0));
                memoryPic1.setImageURI(myUri);

            } else {
                memoryPic1.setImageResource(R.drawable.circle);
            }


            final int click1 = exampleList.size() - 1;

            //Toast.makeText(this, String.valueOf(exampleList.size()), Toast.LENGTH_SHORT).show();
            ImageView one = findViewById(R.id.imageView3);
            one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MemoryActivity.class);
                    intent.putExtra("title", String.valueOf(exampleList.get(click1).getText1()));
                    intent.putExtra("description", String.valueOf(exampleList.get(click1).getDiscription()));
                    intent.putExtra("location", String.valueOf(exampleList.get(click1).getLocation()));
                    startActivity(intent);
                }
            });
            if (amount > 1) {

                ImageView memoryPic2 = findViewById(R.id.imageView8);
                if (exampleList.get(1).getmImages().size() > 0) {
                    Bitmap bmImg = BitmapFactory.decodeFile(exampleList.get(1).getmImages().get(0));
                    memoryPic2.setImageBitmap(bmImg);
                } else if (exampleList.get(1).getmImagesURI().size() > 0) {
                    Uri myUri = Uri.parse(exampleList.get(1).getmImagesURI().get(0));
                    memoryPic2.setImageURI(myUri);


                } else {
                    memoryPic2.setImageResource(R.drawable.circle);
                }
                final int click2 = exampleList.size() - 2;
                ImageView two = findViewById(R.id.imageView8);
                two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MemoryActivity.class);
                        intent.putExtra("title", String.valueOf(exampleList.get(click2).getText1()));
                        intent.putExtra("description", String.valueOf(exampleList.get(click2).getDiscription()));
                        intent.putExtra("location", String.valueOf(exampleList.get(click2).getLocation()));

                        startActivity(intent);
                    }
                });
                if (amount > 2) {

                    ImageView memoryPic3 = findViewById(R.id.imageView9);
                    if (exampleList.get(2).getmImages().size() > 0) {
                        Bitmap bmImg = BitmapFactory.decodeFile(exampleList.get(2).getmImages().get(0));
                        memoryPic3.setImageBitmap(bmImg);
                    } else if (exampleList.get(2).getmImagesURI().size() > 0) {
                        Uri myUri = Uri.parse(exampleList.get(2).getmImagesURI().get(0));
                        memoryPic3.setImageURI(myUri);


                    } else {
                        memoryPic3.setImageResource(R.drawable.circle);
                    }
                    final int click3 = exampleList.size() - 3;
                    ImageView three = findViewById(R.id.imageView9);
                    three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, MemoryActivity.class);
                            intent.putExtra("title", String.valueOf(exampleList.get(click3).getText1()));
                            intent.putExtra("description", String.valueOf(exampleList.get(click3).getDiscription()));
                            intent.putExtra("location", String.valueOf(exampleList.get(click3).getLocation()));

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
