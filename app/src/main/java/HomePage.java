package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    private ImageView profilePicture;
    private ImageView board;
    private ImageView buttonOpenCamera;
    private ImageButton mapPageBtn;
    private ImageButton infoButton;
    private Button requestBinButton; // Added Request Bin button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize UI components
        profilePicture = findViewById(R.id.profile_picture);
        board = findViewById(R.id.board);
        buttonOpenCamera = findViewById(R.id.button_open_camera);
        mapPageBtn = findViewById(R.id.mapPageBtn);
        infoButton = findViewById(R.id.info);
        requestBinButton = findViewById(R.id.request_bin_button); // Initialize Request Bin button

        // Navigate to ProfileActivity when profile picture is clicked
        profilePicture.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Open CameraActivity when camera button is clicked
        buttonOpenCamera.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, CameraActivity.class);
            startActivity(intent);
        });

        // Open MapActivity when map button is clicked
        mapPageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, MapActivity.class);
            startActivity(intent);
        });

        // Open InfoActivity when info button is clicked
        infoButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, InfoActivity.class);
            startActivity(intent);
        });

        // Open RequestBinActivity when Request Bin button is clicked
        requestBinButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, RequestBinActivity.class);
            startActivity(intent);
        });
    }
}

