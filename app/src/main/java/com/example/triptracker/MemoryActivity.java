package com.example.triptracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MemoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        TextView title = findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("title"));

        TextView description = findViewById(R.id.description);
        description.setText(getIntent().getStringExtra("description"));


    }


}

