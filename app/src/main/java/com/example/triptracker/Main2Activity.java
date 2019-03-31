package com.example.triptracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    int checked = 0;
    int memories = 0;
    EditText memoryInput;

    List memNames = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Switch simpleSwitch = (Switch) findViewById(R.id.switch1);
        checked = getIntent().getIntExtra("checked", 0);
        memories = getIntent().getIntExtra("memoriesAmount", 0);
        //memNames = getIntent().getStringArrayListExtra("memNames");



        if (checked == 1){
            simpleSwitch.setChecked(true);
        }
        else{
            simpleSwitch.setChecked(false);
        }





        Button backButton = (Button) findViewById(R.id.button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class   );
                intent.putExtra("checked", checked );
                intent.putExtra("memoriesAmount", memories );
                intent.putStringArrayListExtra("memNames", (ArrayList<String>) memNames);


                startActivity(intent);
            }
        });


        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch simpleSwitch = (Switch) findViewById(R.id.switch1);
                if (simpleSwitch.isChecked() == Boolean.TRUE) {
                    simpleSwitch.setText("Hi");
                    checked = 1;
                }
                else {
                    simpleSwitch.setText("Not Checked");
                    checked = 0;
                }
            }





        });


        Button memoryButton = (Button)findViewById(R.id.create);
        memoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               memories += 1;
               memNames.add(memoryInput.getText());
               Collections.reverse(memNames);

            }
        });




    }
}
