package com.example.triptracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    int checked = 0;
    int memories = 0;
    List memNames = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checked = getIntent().getIntExtra("checked", 0);
        memories = getIntent().getIntExtra("memoriesAmount", 0);
        memNames = getIntent().getStringArrayListExtra("memNames");

        TextView memory1 = (TextView)findViewById(R.id.memory1);
        TextView memory2 = (TextView)findViewById(R.id.memory2) ;
        TextView memory3 = (TextView)findViewById(R.id.memory3) ;


        //Toast.makeText(getApplication().getBaseContext(), (String)String.valueOf(memories),
               // Toast.LENGTH_LONG).show();


        TextView[] memoryArray = {memory1,memory2,memory3};

        for (int x = 0; x< 3 && x < memories; x++){
            memoryArray[x].setText("Hi");
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
