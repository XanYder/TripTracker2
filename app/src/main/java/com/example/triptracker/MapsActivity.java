package com.example.triptracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
//import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
//import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import android.content.Context;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import android.support.v4.content.ContextCompat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final int REQUEST_ACCESS_FINE_LOCATION = 0;
    private static final LatLng HRO = new LatLng(51.91732977623568,4.4843445754744655);

    public ArrayList<ExampleItem> memories = new ArrayList<>();

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        createButton();
        homeButton();
        //createMemory();
        mMap = map;
        //Move the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HRO, 10));
        // Add some markers to the map, and add a data object to each marker.
        try{
            FileInputStream fis = openFileInput("memories.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String lines;
            int count = 1;
            int counter = 0;
            String itemName = "";
            String date = "";
            String description = "";
            String location;
            while ((lines = bufferedReader.readLine()) != null){
                if (count == 1){
                    itemName = lines;
                    count += 1;
                }
                else if (count == 2) {
                    date = lines;
                    count += 1;
                } else if (count == 3) {
                    description = lines;
                    count += 1;
                } else if (count == 4) {
                    location = lines;
                    Geocoder gc = new Geocoder(this, Locale.getDefault());
                    if (Geocoder.isPresent()) {
                        List<Address> list = gc.getFromLocationName(location, 1);
                        Address address = list.get(0);
                        double lat = address.getLatitude();
                        double lng = address.getLongitude();
                        Marker marker = mMap.addMarker((new MarkerOptions()
                                .position(new LatLng(lat, lng))
                                .title(itemName)));
                        marker.setTag(counter);
                        //Toast.makeText(this, String.valueOf(marker.getPosition()), Toast.LENGTH_SHORT).show();
                    }

                    memories.add(new ExampleItem(R.drawable.pic5, itemName, date, description, location));
                    count = 1;
                    counter++;
                }

            }


        }catch (IOException e){
            e.printStackTrace();
        }
        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
        // Check whether this app could get location
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            /*
             * Enables the My Location layer if the fine location permission has been granted.
             */
            mMap.setMyLocationEnabled(true);
        }else{
            //Permission has not been granted
            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "Location Permission is needed in order to access and show your location", Toast.LENGTH_SHORT).show();
            }
        }
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);


    }

    @Override
    public void onRequestPermissionsResult(int REQUEST_ACCESS_FINE_LOCATION, String[] permissions, int[] grantResults){
        if (REQUEST_ACCESS_FINE_LOCATION == REQUEST_ACCESS_FINE_LOCATION){
            //received permission result for location permission

            //check if only the required permission has been granted
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Location permission has been granted, preview can be displayed
                mMap.setMyLocationEnabled(true);
            }else{
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        // Retrieve the data from the marker.
        Integer id = (Integer) marker.getTag();

        Intent intent = new Intent(this, MemoryActivity.class);
        intent.putExtra("title", String.valueOf(memories.get(id).getText1()));
        intent.putExtra("description", String.valueOf(memories.get(id).getDiscription()));
        intent.putExtra("location", String.valueOf(memories.get(id).getLocation()));
        startActivity(intent);

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;

    }

    private void createButton() {
        Button createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, Main2Activity.class));
            }
        });
    }

    private void homeButton() {
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, MainActivity.class));
            }
        });

    }
}
