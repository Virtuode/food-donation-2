package com.example.codinger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codinger.R;
import com.example.codinger.fragments.VerificationBottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private TextView signInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.buttonRegister);
        signInTextView = findViewById(R.id.signInTextView);

        // Set onClick listeners
        signUpButton.setOnClickListener(v -> signUpWithEmail());
        signInTextView.setOnClickListener(v -> navigateToSignIn());
    }

    private void signUpWithEmail() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isPasswordStrong(password)) {
            Toast.makeText(this, "Password must be at least 8 characters long and include uppercase, lowercase, number, and special character.", Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign up success
                        FirebaseUser user = mAuth.getCurrentUser();
                        sendEmailVerification();
                        updateUI(user);
                    } else {
                        // If sign up fails, display a message to the user.
                        Exception exception = task.getException();
                        if (exception != null) {
                            Log.e(TAG, "Authentication failed: " + exception.getMessage(), exception);
                            Toast.makeText(SignupActivity.this, "Authentication Failed: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignupActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                        updateUI(null);
                    }
                });
    }

    private boolean isPasswordStrong(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        return password.matches(passwordPattern);
    }


    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            showVerificationBottomSheet();
                        } else {
                            Toast.makeText(SignupActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void showVerificationBottomSheet() {
        VerificationBottomSheetDialogFragment bottomSheet = new VerificationBottomSheetDialogFragment();
        bottomSheet.setCancelable(false);
        bottomSheet.show(getSupportFragmentManager(), "VerificationBottomSheet");
    }




    private void navigateToSignIn() {
        // Navigate to the sign-in screen

            // Navigate to the sign-in screen
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Navigate to next activity or update UI with user info
            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show();
            // Navigate to the next activity, e.g., MainActivity
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Clear input fields or show error message
            emailEditText.setText("");
            passwordEditText.setText("");
            confirmPasswordEditText.setText("");
        }
    }
}
