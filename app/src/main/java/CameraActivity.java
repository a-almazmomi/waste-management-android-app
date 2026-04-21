package com.example.project;

import static android.os.Build.VERSION_CODES.R;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class CameraActivity extends AppCompatActivity {
    private ImageView imgCapture;
    private ImageButton buttonTakePicture;
    private ImageButton backButton;
    private static final int IMAGE_CAPTURE_CODE = 1;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private DatabaseReference databaseReference;
    private EditText descriptionInput;
    private Spinner districtSpinner;
    private Spinner statusSpinner; // Spinner for manual waste status selection
    private static final Pattern WASTE_ID_PATTERN = Pattern.compile("JED-12-345"); // Regex pattern

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.activity_camera);

        buttonTakePicture = findViewById(R.id.snap_picture_button);
        imgCapture = findViewById(R.id.waste_location_map);
        backButton = findViewById(R.id.back_button);
        descriptionInput = findViewById(R.id.description_input); // Get reference to the input field
        districtSpinner = findViewById(R.id.district_spinner); // Get reference to the district spinner
        statusSpinner = findViewById(R.id.status_spinner); // Get reference to the status spinner

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("waste_reports");

        // Check for camera permission at runtime
        checkCameraPermission();

        // Add a TextWatcher for real-time validation feedback
        descriptionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action required
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateWasteIdInput(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action required
            }
        });

        // Set up button to capture picture
        buttonTakePicture.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
        });

        // Submit button to save data to Firebase and navigate to MapActivity
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            String wasteId = descriptionInput.getText().toString();
            String district = districtSpinner.getSelectedItem().toString();
            String wasteStatus = statusSpinner.getSelectedItem().toString(); // Get the status from Spinner

            if (!WASTE_ID_PATTERN.matcher(wasteId).matches()) {
                Toast.makeText(CameraActivity.this, "Invalid Waste ID format. Please use JED-XX-XXX.", Toast.LENGTH_SHORT).show();
            } else {
                // Create a new report object
                String reportId = databaseReference.push().getKey();
                WasteReport report = new WasteReport(reportId, wasteId, district);

                // Save the report to Firebase
                databaseReference.child(reportId).setValue(report)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(CameraActivity.this, "Your report submitted successfully", Toast.LENGTH_LONG).show();

                            // Navigate to MapActivity with the submitted data
                            Intent intent = new Intent(CameraActivity.this, MapActivity.class);
                            intent.putExtra("district", district);
                            intent.putExtra("wasteId", wasteId);
                            intent.putExtra("status", wasteStatus); // Pass status
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(CameraActivity.this, "Failed to submit report: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // Set up back button to navigate to HomePage
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CameraActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        });
    }

    // Method to validate waste ID input in real-time
    private void validateWasteIdInput(String input) {
        if (!WASTE_ID_PATTERN.matcher(input).matches()) {
            descriptionInput.setError("Invalid format! Use JED-XX-XXX.");
        }
    }

    // Method to check and request camera permission
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            if (bp != null) {
                imgCapture.setImageBitmap(bp);
                Toast.makeText(CameraActivity.this, "Picture captured successfully", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}



