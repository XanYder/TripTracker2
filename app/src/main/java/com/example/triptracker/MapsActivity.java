package com.example.triptracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
//import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
    private LatLng HRO = new LatLng(51.91732977623568, 4.4843445754744655);

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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HRO, 5));
        if(getIntent().getStringExtra("location") != null) {
            try {
                Geocoder geode = new Geocoder(this, Locale.getDefault());
                List<Address> list = geode.getFromLocationName(getIntent().getStringExtra("location"), 1);
                Address link = list.get(0);
                double lat = link.getLatitude();
                double lng = link.getLongitude();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 5));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream fis = openFileInput("memories.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String lines;
            String itemName = "";
            String date = "";
            String description = "";
            String location = "";
            ArrayList<String> images = new ArrayList<>();
            ArrayList<String> videos = new ArrayList<>();
            ArrayList<String> imageURI = new ArrayList<>();
            ArrayList<String> videosURI = new ArrayList<>();
            Integer counter = 0;

            while ((lines = bufferedReader.readLine()) != null) {
                String identify = lines.substring(0, 4);

                switch (identify) {
                    case "titl":
                        if (!itemName.equals("")) {
                            memories.add(new ExampleItem(R.drawable.pic5, itemName, date, description, location, images, videos, imageURI, videosURI));
                        }
                        date = "";
                        description = "";
                        location = "";
                        itemName = lines.substring(5);
                        images = new ArrayList<>();
                        videos = new ArrayList<>();
                        imageURI = new ArrayList<>();
                        videosURI = new ArrayList<>();
                        break;
                    case "date":
                        date = lines.substring(4);
                        break;
                    case "desc":
                        description = lines.substring(6);
                        break;
                    case "loca":
                        location = lines.substring(3);
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
                            counter++;
                            //Toast.makeText(this, String.valueOf(marker.getPosition()), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "vidu":
                        videosURI.add(lines.substring(6));
                        break;
                    case "impa":
                        images.add(lines.substring(6));
                        break;
                }

            }
            memories.add(new ExampleItem(R.drawable.pic5, itemName, date, description, location, images, videos, imageURI, videosURI));
            //return memories;


        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
        // Check whether this app could get location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Location Permission is needed in order to access and show your location", Toast.LENGTH_LONG).show();
            }
        }else {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int REQUEST_ACCESS_FINE_LOCATION, String[] permissions, int[] grantResults) {
        if (REQUEST_ACCESS_FINE_LOCATION == REQUEST_ACCESS_FINE_LOCATION) {
            //received permission result for location permission
            //check if only the required permission has been granted
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        // Retrieve the data from the marker.
        int id = (int) marker.getTag();

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
