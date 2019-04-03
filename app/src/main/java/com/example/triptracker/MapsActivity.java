package com.example.triptracker;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_ACCESS_FINE_LOCATION = 0;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Check whether this app could get location
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            /**
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

}
