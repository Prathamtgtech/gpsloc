package com.example.latlang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import com.example.latlang.ModelClass.MapModel;
import com.example.latlang.MyAdapeter.MapAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationEngineListener, PermissionsListener {
    Button getLoc;
    FusedLocationProviderClient fusedLocationProviderClient;
    List<Address> currentLocations;
    Location location;
    com.example.latlang.Database.MapDatabase mapDatabase;
    double G_lat, G_lang;
    double M_lat, M_lang;
    double G_diff, M_diff;
    RecyclerView recyclerView;
    MapAdapter mapAdapter;
    ArrayList<MapModel> mapModels = new ArrayList<>();
    MapModel mapModel;
    Cursor cursor;

    //MapBox
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private Location originlocation;

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
        recyclerView = findViewById(R.id.rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cursor = new MapDatabase(this).FetchCustData();
        //Insert Data
        InsertData();
        //DataView
        TableView();
    }

    //Insert Data
    private void InsertData() {
        getGoogleCurrentLocation();
        getMapboxLocation();
        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G_diff=M_lat-G_lat*M_lat-G_lat;
                M_diff=M_lang-G_lang*M_lang-G_lang;
                double Diff = Math.sqrt(G_diff + M_diff);
                Log.d("Lat_langs", "" + G_lat + G_lang);
                Log.d("mapLat_langs", "" + G_lat + G_lang);
                Boolean InsertData = mapDatabase.insertData(G_lat, G_lang, M_lat, M_lang, Diff);
                if (InsertData == true) {
                    Toast.makeText(getApplicationContext(), "Details Added Sucessfully", Toast.LENGTH_LONG).show();
                    Log.d("LOCATION", "" + G_lat + G_lang + M_lat + M_lat);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed To Added Details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //MapBox
    private void getMapboxLocation() {
        if (permissionsManager.areLocationPermissionsGranted(this)) {
            locationEngine();

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    //MapBox LocationEngine
    @SuppressLint("MissingPermission")
    private void locationEngine() {
        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();
        ;

        Location lastlocation = locationEngine.getLastLocation();
        if (lastlocation != null) {
            originlocation = lastlocation;
            M_lat = lastlocation.getLatitude();
            M_lang = lastlocation.getLongitude();
        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null){
            originlocation=location;
            M_lat = location.getLatitude();
            M_lang = location.getLongitude();
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted){
            getMapboxLocation();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode,permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

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
                            G_lat = currentLocations.get(0).getLatitude();
                            G_lang = currentLocations.get(0).getLongitude();

                            Log.d("Goog_lat", "" + G_lat + G_lang + G_diff);
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


    //View Data
    private void TableView() {
        while (cursor.moveToNext()) {
            mapModel = new MapModel();
            mapModel.setId(cursor.getInt(0));
            mapModel.setG_lat(cursor.getDouble(1));
            mapModel.setG_lang(cursor.getDouble(2));
            mapModel.setM_lat(cursor.getDouble(3));
            mapModel.setM_lang(cursor.getDouble(4));
            mapModel.setDiff(cursor.getDouble(5));
            mapModels.add(mapModel);
        }
        mapAdapter = new MapAdapter(mapModels, getApplicationContext());
        recyclerView.setAdapter(mapAdapter);
    }
}