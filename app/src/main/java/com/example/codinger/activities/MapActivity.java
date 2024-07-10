package com.example.codinger.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.HandlerThread;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.codinger.R;
import com.google.android.gms.location.Priority;
import com.tomtom.sdk.location.GeoLocation;
import com.tomtom.sdk.location.OnLocationUpdateListener;
import com.tomtom.sdk.location.LocationProvider;
import com.tomtom.sdk.location.android.AndroidLocationProvider;

import com.tomtom.sdk.map.display.MapOptions;
import com.tomtom.sdk.map.display.TomTomMap;
import com.tomtom.sdk.map.display.ui.MapFragment;
import com.tomtom.sdk.map.display.ui.MapReadyCallback;
import com.tomtom.sdk.map.display.ui.MapView;

public class MapActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mapView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            initMap();
        }
    }

    private void initMap() {
        // Create MapOptions and MapFragment
        MapOptions mapOptions = new MapOptions("JvShheAVgqajf6D0tq8irLe2pLFL4DWX"); // Replace with your actual API key
        MapFragment mapFragment = MapFragment.Companion.newInstance(mapOptions);

        // Add MapFragment to the container
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map_container, mapFragment); // Make sure R.id.map_container is the correct ID of your container
        fragmentTransaction.commit();

        // Access the TomTomMap
        mapFragment.getMapAsync(new MapReadyCallback() {
            @Override
            public void onMapReady(@NonNull TomTomMap tomTomMap) {
                // Initialize the location provider
                HandlerThread locationHandlerThread = new HandlerThread("locationHandlerThread");
                locationHandlerThread.start();







                // Example listener for location updates
                OnLocationUpdateListener onLocationUpdateListener = new OnLocationUpdateListener() {
                    @Override
                    public void onLocationUpdate(@NonNull GeoLocation geoLocation) {
                        // Handle location updates here
                    }
                };


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMap();
            } else {
                Toast.makeText(this, "Location permission is required to show your current location on the map", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}