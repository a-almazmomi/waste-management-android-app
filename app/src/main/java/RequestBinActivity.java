package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestBinActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton backButton;
    private EditText editTextDescription;
    private EditText editTextLocation;
    private EditText editTextUrgency;
    private EditText editTextContact;
    private Button buttonSubmit;
    private Spinner spinnerSource;
    private Spinner spinnerType;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestbin);

        // Initialize UI components
        backButton = findViewById(R.id.backButton);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextUrgency = findViewById(R.id.editTextUrgency);
        editTextContact = findViewById(R.id.editTextContact);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        spinnerSource = findViewById(R.id.spinnerSource);
        spinnerType = findViewById(R.id.spinnerType);

        // Set up the spinner for type of garbage
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.garbage_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        // Set up the spinner for source of garbage
        ArrayAdapter<CharSequence> sourceAdapter = ArrayAdapter.createFromResource(this,
                R.array.garbage_sources, android.R.layout.simple_spinner_item);
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSource.setAdapter(sourceAdapter);

        // Set up the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("request");

        // Submit button listener
        buttonSubmit.setOnClickListener(v -> submitRequest());

        // Back button listener
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(RequestBinActivity.this, HomePage.class);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Enable zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Center the map on a neighborhood in Jeddah
        LatLng jeddahNeighborhood = new LatLng(21.543333, 39.172778);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jeddahNeighborhood, 15));

        // Add markers for bins
        addBinMarker(new LatLng(21.543500, 39.172300), "Bin 1", "Full", BitmapDescriptorFactory.HUE_RED);
    }

    private void addBinMarker(LatLng location, String title, String snippet, float hueColor) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(hueColor)));
        if (marker != null) {
            marker.showInfoWindow();
        }
    }

    private void submitRequest() {
        String description = editTextDescription.getText().toString();
        String location = editTextLocation.getText().toString();
        String type = spinnerType.getSelectedItem().toString();
        String urgency = editTextUrgency.getText().toString();
        String contact = editTextContact.getText().toString();
        String source = spinnerSource.getSelectedItem().toString();

        if (description.isEmpty() || location.isEmpty() || urgency.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
        } else {
            // Create a new request object
            String requestId = databaseReference.push().getKey(); // Generate a unique key for the request
            Request request = new Request(requestId, description, location, type, urgency, contact, source);

            // Save the request to Firebase
            databaseReference.child(requestId).setValue(request)
                    .addOnSuccessListener(aVoid -> {
                        Intent intent = new Intent(RequestBinActivity.this, HomePage.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(this, "The order has been submitted successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to submit order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void clearFields() {
        editTextDescription.setText("");
        editTextLocation.setText("");
        editTextUrgency.setText("");
        editTextContact.setText("");
        spinnerType.setSelection(0);
        spinnerSource.setSelection(0);
    }
}