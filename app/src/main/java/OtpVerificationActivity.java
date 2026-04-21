package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OtpVerificationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText otpInput;
    private Button verifyOtpButton;
    private TextView resendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        mAuth = FirebaseAuth.getInstance();

        otpInput = findViewById(R.id.otpInput);
        verifyOtpButton = findViewById(R.id.verifyButton);
        resendOtp = findViewById(R.id.resendCodeClickable);

        verifyOtpButton.setOnClickListener(view -> {
            String otp = otpInput.getText().toString();
            if (TextUtils.isEmpty(otp)) {
                otpInput.setError("Enter the OTP");
                return;
            }
            verifyEmailWithOtp(otp);
        });

        resendOtp.setOnClickListener(view -> {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                user.sendEmailVerification()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(OtpVerificationActivity.this, "OTP Resent", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void verifyEmailWithOtp(String otp) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            Toast.makeText(OtpVerificationActivity.this, "Email already verified", Toast.LENGTH_SHORT).show();
            // Proceed to MainActivity
            startActivity(new Intent(OtpVerificationActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(OtpVerificationActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
        }
    }
}

