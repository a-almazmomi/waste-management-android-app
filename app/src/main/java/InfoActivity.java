package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoActivity extends AppCompatActivity {

    private VideoView videoView;
    private Button submitButton;
    private EditText editTextBorder;
    private ImageButton cameraButton, homeButton, mapButton;
    private RadioGroup radioGroupTime;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info);

        // Setup VideoView
        videoView = findViewById(R.id.video);
        submitButton = findViewById(R.id.submit_button);
        editTextBorder = findViewById(R.id.editTextBorder);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.waste_management);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Setup RadioGroup
        radioGroupTime = findViewById(R.id.radioGroupTime);
        radioGroupTime.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedTime = radioButton.getText().toString();
            Toast.makeText(InfoActivity.this, "Selected Time: " + selectedTime, Toast.LENGTH_SHORT).show();
        });

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("feedback");

        // Submit button to go back to HomePage
        submitButton.setOnClickListener(v -> {
            String feedback = editTextBorder.getText().toString();
            int selectedId = radioGroupTime.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            String selectedTime = (radioButton != null) ? radioButton.getText().toString() : "Not Selected";

            if (feedback.isEmpty()) {
                Toast.makeText(InfoActivity.this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
            } else {
                // Create a new feedback object
                String feedbackId = databaseReference.push().getKey();
                Feedback feedbackObject = new Feedback(feedbackId, feedback, selectedTime);

                // Save the feedback to Firebase
                databaseReference.child(feedbackId).setValue(feedbackObject)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(InfoActivity.this, "Thank you, your response has been recorded", Toast.LENGTH_LONG).show();
                            clearFields();
                            startActivity(new Intent(InfoActivity.this, HomePage.class));
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(InfoActivity.this, "Failed to submit feedback: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // Setup navigation buttons
        cameraButton = findViewById(R.id.button_open_camera);
        homeButton = findViewById(R.id.homeButton);
        mapButton = findViewById(R.id.mapPageBtn);

        // Navigate to CameraActivity
        cameraButton.setOnClickListener(v -> {
            Intent intent = new Intent(InfoActivity.this, CameraActivity.class);
            startActivity(intent);
        });

        // Navigate to HomePage
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(InfoActivity.this, HomePage.class);
            startActivity(intent);
        });

        // Navigate to MapActivity
        mapButton.setOnClickListener(v -> {
            Intent intent = new Intent(InfoActivity.this, MapActivity.class);
            startActivity(intent);
        });
    }

    private void clearFields() {
        editTextBorder.setText("");
        radioGroupTime.clearCheck(); // Clear the selected radio button
    }
}