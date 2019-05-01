package com.example.triptracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Main2Activity extends AppCompatActivity {


    public void setTextinFile(String text){
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput("memories.txt", Context.MODE_APPEND);
            outputStream.write(text.getBytes());
            outputStream.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView autoDate = findViewById(R.id.auto_date);
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        autoDate.setText(date);

        final Switch dateSwitch = findViewById(R.id.date_switch);
        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch dateSwitch = findViewById(R.id.date_switch);
                TextView autoDate = findViewById(R.id.auto_date);
                final TextInputLayout customDate = findViewById(R.id.custom_date_text);
                if (dateSwitch.isChecked() == Boolean.TRUE) {
                    customDate.setVisibility(View.INVISIBLE);
                    autoDate.setVisibility(View.VISIBLE);


                } else {
                    customDate.setVisibility(View.VISIBLE);
                    autoDate.setVisibility(View.INVISIBLE);
                }
            }
        });


        Button createMemory = findViewById(R.id.createMemory);
        createMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText title = findViewById(R.id.title);
                String text = title.getText().toString();
                setTextinFile(text + "\n");
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteFile("memories.txt");
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
