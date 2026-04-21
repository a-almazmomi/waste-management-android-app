package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private TextView firstNameValue;
    private TextView lastNameValue;
    private TextView sexValue;
    private TextView emailValue;
    private Button logOutButton;
    private FirebaseAuth mAuth; // FirebaseAuth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Reference the correct layout file

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        firstNameValue = findViewById(R.id.firstNameValue);
        lastNameValue = findViewById(R.id.lastNameValue);
        sexValue = findViewById(R.id.sexValue);
        emailValue = findViewById(R.id.emailValue);
        logOutButton = findViewById(R.id.logOutButton);

        // Log Out button functionality
        logOutButton.setOnClickListener(v -> logOut());
    }

    /**
     * Logs the user out using FirebaseAuth and navigates to the LoginActivity.
     */
    private void logOut() {
        // Sign out the user with FirebaseAuth
        mAuth.signOut();

        // Display a toast message confirming the log-out
        Toast.makeText(this, "You have been logged out.", Toast.LENGTH_SHORT).show();

        // Redirect to LoginActivity
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
        startActivity(intent);
        finish(); // Finish the current activity
    }
}


