package com.example.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
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

        TextView location = findViewById(R.id.textView3);
        location.setText(getIntent().getStringExtra("location"));

        ImageView button = (ImageView) findViewById(R.id.cameraButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = getIntent().getStringExtra("description");
                String shareSub = getIntent().getStringExtra("title");
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share using"));
            }
        });

    }


}

