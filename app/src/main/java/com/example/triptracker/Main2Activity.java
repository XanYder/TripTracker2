package com.example.triptracker;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.google.android.libraries.places.api.Places;
//import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
    private boolean addedTitle = false;

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
    public String apiKey = "AIzaSyD5pLtC8lv397OU31Tas86utbmeQAl1jl8";
    private FusedLocationProviderClient fusedLocationClient;
    private EditText mSearchText;
    private static final String TAG = "MyActivity";

    private void init(){
        Log.d(TAG, "init: initializing");
        EditText location = findViewById(R.id.location);
        location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    //execute our method for searching
                    if (!geoLocate().equals("No")){
                        EditText location = findViewById(R.id.location);
                        location.setText(geoLocate());
                    }
                    else{
                        Toast.makeText(Main2Activity.this,"Could not find that adress, please try again.", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (!geoLocate().equals("No")){
                        EditText location = findViewById(R.id.location);
                        location.setText(geoLocate());
                    }
                    else{
                        Toast.makeText(Main2Activity.this,"Could not find that adress, please try again.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Places.initialize(getApplicationContext(), apiKey);
        //PlacesClient placesClient = Places.createClient(this);
        final Geocoder geocoder = new Geocoder(Main2Activity.this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                try {
                    List<Address> current = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address address = current.get(0);
                    mSearchText = findViewById(R.id.location);
                    mSearchText.setText(address.getAddressLine(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final TextView autoDate = findViewById(R.id.auto_date);
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        autoDate.setText(date);


        Button cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText title = findViewById(R.id.theTitle);
                String text = title.getText().toString();
                setTextinFile("title" + text + "\n");
                addedTitle = true;
                startActivity(new Intent(Main2Activity.this, CameraActivity.class));

            }
        });

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
                TextInputEditText title = findViewById(R.id.theTitle);
                String text = title.getText().toString();
                TextInputEditText location = findViewById(R.id.location);
                //Button test = findViewById(R.id.cameraButton);
                if (!TextUtils.isEmpty(location.getText()) && !TextUtils.isEmpty(title.getText()) && !geoLocate().equals("No")) {

                    if (addedTitle == false) {
                        setTextinFile("title" + text + "\n");
                    }
                    Switch dateSwitch = findViewById(R.id.date_switch);
                    if (dateSwitch.isChecked()) {
                        String text2 = autoDate.getText().toString();
                        setTextinFile("date" + text2 + "\n");
                    } else {
                        TextInputEditText date = findViewById(R.id.custom_date_text);
                        String text2 = date.getText().toString();
                        setTextinFile("date" + text2 + "\n");
                    }

                    EditText description = findViewById(R.id.description);
                    setTextinFile("description" + description.getText().toString() + "\n");
                    setTextinFile("location" + location.getText().toString() + "\n");


                    Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Main2Activity.this, "Please enter at least a title and a valid adress", Toast.LENGTH_LONG).show();
                }
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
        homeButton();
        init();
    }

    private String geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");
        EditText location = findViewById(R.id.location);
        String searchString = location.getText().toString();

        Geocoder geocoder = new Geocoder(Main2Activity.this);
        List<Address> list;
        try{
            list= geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
            return "No";
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            return String.valueOf(list.get(0).getAddressLine(0));
        }
        return "No";
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

    private void homeButton() {
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }
        });

    }


}
