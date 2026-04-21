package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton homeButton, locationButton, qrButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map); // Link to the XML layout

        // Obtain the SupportMapFragment and get notified when the map is ready to be used
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Initialize buttons
        homeButton = findViewById(R.id.homeButton);
        locationButton = findViewById(R.id.locationButton);
        qrButton = findViewById(R.id.qrButton);

        // Home button functionality
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, HomePage.class);
            startActivity(intent);
        });

        // Location button functionality (Navigate to CameraActivity)
        locationButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, CameraActivity.class);
            startActivity(intent);
        });

        // QR button functionality (optional, if needed)
        qrButton.setOnClickListener(v -> {
            // Add specific functionality for the QR button here
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Enable zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Center the map on a default location (Jeddah in this example)
        LatLng defaultLocation = new LatLng(21.543333, 39.172778);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15)); // Adjust zoom level as required

        // Retrieve data passed from CameraActivity
        Intent intent = getIntent();
        if (intent != null) {
            String district = intent.getStringExtra("district");
            String wasteId = intent.getStringExtra("wasteId");
            String status = intent.getStringExtra("status");

            // Add marker dynamically based on received data
            addDynamicMarker(district, wasteId, status);
        }
    }

    // Helper method to add a dynamic marker based on input
    private void addDynamicMarker(String district, String wasteId, String status) {
        // Determine the location (LatLng) based on the district
        LatLng districtLocation = getDistrictCoordinates(district);

        // Determine marker color based on waste status
        float markerColor;
        switch (status) {
            case "Full":
                markerColor = BitmapDescriptorFactory.HUE_RED;
                break;
            case "Half-Full":
                markerColor = BitmapDescriptorFactory.HUE_ORANGE;
                break;
            case "Empty":
                markerColor = BitmapDescriptorFactory.HUE_GREEN;
                break;
            default:
                markerColor = BitmapDescriptorFactory.HUE_BLUE; // Default color
        }

        // Add the marker to the map
        mMap.addMarker(new MarkerOptions()
                        .position(districtLocation)
                        .title("Waste ID: " + wasteId)
                        .snippet("Status: " + status)
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColor)))
                .showInfoWindow(); // Show info window by default
    }

    // Method to map district names to LatLng coordinates
    private LatLng getDistrictCoordinates(String district) {
        switch (district) {
            case "District 1":
                return new LatLng(21.543500, 39.172300);
            case "District 2":
                return new LatLng(21.543700, 39.172500);
            case "District 3":
                return new LatLng(21.543900, 39.172700);
            default:
                return new LatLng(21.543333, 39.172778); // Default location
        }
    }
}





