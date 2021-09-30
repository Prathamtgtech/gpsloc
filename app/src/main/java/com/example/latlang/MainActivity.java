package com.example.latlang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.latlang.Database.sqliteDatabase;
import com.example.latlang.Database.MapDatabase;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button getLoc;
    FusedLocationProviderClient fusedLocationProviderClient;
    List<Address> currentLocations;
    LocationManager locationManager;
    Location location;
    com.example.latlang.Database.MapDatabase mapDatabase;
    double Osm_lat, Osm_lang;
    double Gmap_lat,Gmap_lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get Location
        getLoc = findViewById(R.id.Get_loc);
        //Google Location Provider
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //sqlite
        mapDatabase = new MapDatabase(this);


        //Insert Data
        InsertData();
    }




    //Osm Map
    @SuppressLint("MissingPermission")
    private void getOsmCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                Osm_lat = location.getLatitude();
                Osm_lang =location.getLongitude();
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

    }

    //Google Map
    @SuppressLint("MissingPermission")
    private void getGoogleCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Location> task) {
                    Location currentLocation = task.getResult();
                    if (currentLocation != null) {
                        try {
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            currentLocations = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                            Gmap_lat = currentLocations.get(0).getLatitude();
                            Gmap_lang = currentLocations.get(0).getLongitude();
                            Log.d("Lat_lang",""+Gmap_lat+Gmap_lang);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    //Insert Data
    private void InsertData() {
        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGoogleCurrentLocation();
                getOsmCurrentLocation();
                double Diff_1,Diff_2;
                Log.d("Lat_langs",""+Gmap_lat+Gmap_lang);
                Diff_1=Gmap_lang-Gmap_lat;
                Diff_2=Osm_lang-Osm_lat;
                Boolean InsertData = mapDatabase.insertData("Lat :"+Gmap_lat+"Lang :"+Gmap_lang, Diff_1,"Lat :"+Osm_lat+"Lang :"+Osm_lang,Diff_2);
                if (InsertData == true) {
                    Toast.makeText(getApplicationContext(), "Details Added Sucessfully", Toast.LENGTH_LONG).show();
                    Log.d("LOCATION",""+Gmap_lat+Gmap_lang+ Osm_lat+ Osm_lang+Diff_1+Diff_2);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed To Added Details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}