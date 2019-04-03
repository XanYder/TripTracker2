package com.example.triptracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    int checked = 0;
    int memories = 0;

    public String getTextFromFile(int index){
        String text = "";
        List memNames = new ArrayList();
        memories = 0;
        try {
            FileInputStream fis = openFileInput("test.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);


            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String lines;
            while ((lines = bufferedReader.readLine()) != null){
                memNames.add(lines+"\n");
                memories += 1;
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
        checked = getIntent().getIntExtra("checked", 0);
        memories = getIntent().getIntExtra("memoriesAmount", 0);

        TextView memory1 = (TextView)findViewById(R.id.memory1);
        TextView memory2 = (TextView)findViewById(R.id.memory2) ;
        TextView memory3 = (TextView)findViewById(R.id.memory3) ;

        getTextFromFile(0);


        TextView[] memoryArray = {memory1,memory2,memory3};

        for (int x = 0; x< 3 && x < memories; x++){
            memoryArray[x].setText(getTextFromFile(x));
        }

        textView = (TextView)findViewById(R.id.your_memories);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("checked", checked );
                intent.putExtra("memoriesAmount", memories );
                ArrayList<String> memNames = getIntent().getStringArrayListExtra("memNames");
                startActivity(intent);
            }
        });



    }
}
