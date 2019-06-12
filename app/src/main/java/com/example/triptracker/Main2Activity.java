package com.example.triptracker;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private FusedLocationProviderClient fusedLocationClient;
    private EditText mSearchText;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchText = findViewById(R.id.location);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        /*fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                Toast.makeText(Main2Activity.this, location.toString(),
                        Toast.LENGTH_LONG).show();
                if (location != null) {
                    Toast.makeText(Main2Activity.this, "location doesn't equal null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final TextView autoDate = findViewById(R.id.auto_date);
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        autoDate.setText(date);

        final Switch dateSwitch = findViewById(R.id.date_switch);
        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch dateSwitch = findViewById(R.id.date_switch);
                TextView autoDate = findViewById(R.id.auto_date);
                final TextInputLayout customDate = findViewById(R.id.date_layout);
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

                Switch dateSwitch = findViewById(R.id.date_switch);
                if (dateSwitch.isChecked()){
                    String text2 = autoDate.getText().toString();
                    setTextinFile(text2 + "\n");
                }
                else{
                    TextInputEditText date = findViewById(R.id.custom_date_text);
                    String text2 = date.getText().toString();
                    setTextinFile(text2 + "\n");
                }

                EditText description = findViewById(R.id.description);
                setTextinFile(description.getText().toString() + "\n");

                TextInputEditText location = findViewById(R.id.location);
                setTextinFile(location.getText().toString() + "\n");


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


        mapButton();
        listButton();
        homeButton();
        cameraButton();
        //init();
    }

    private void init(){
        Log.d(TAG, "init: initializing");
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    //execute our method for searching
                    geoLocate();
                }
                return false;
            }
        });
    }
    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");
        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(Main2Activity.this);
        List<Address> list = new ArrayList<>();
        try{
            list= geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }
        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private void mapButton() {
        ImageButton mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MapsActivity.class));
            }
        });
    }

    private void listButton() {
        ImageButton listButton = findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MemoryListActivity.class));
            }
        });
    }

    private void homeButton() {
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }
        });

    }

    private void cameraButton() {
        Button cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, CameraActivity.class));
            }
        });

    }
}
