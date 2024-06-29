package com.example.codinger.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.codinger.R;
import com.example.codinger.activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificationBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private FirebaseAuth mAuth;
    private TextView verificationStatusTextView;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verification_bottom_sheet_dialog, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        verificationStatusTextView = view.findViewById(R.id.verificationStatusTextView);






        handler = new Handler(Looper.getMainLooper());
        startCheckingEmailVerification();

        return view;

    }

    private void startCheckingEmailVerification() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    checkEmailVerification(user);
                }
                handler.postDelayed(this, 5000);  // Check every 5 seconds
            }
        }, 5000);
    }

    private void checkEmailVerification(FirebaseUser user) {
        user.reload().addOnCompleteListener(task -> {
            if (task.isSuccessful() && user.isEmailVerified()) {
                verificationStatusTextView.setText("Email Verified");
                verificationStatusTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
                verificationStatusTextView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom_background_verified));
                handler.removeCallbacksAndMessages(null); // Stop checking
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    dismiss();
                    navigateToMainPage();
                }, 2000);
            } else {
                verificationStatusTextView.setText("Email Not Verified");
                verificationStatusTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.hint_color));
                verificationStatusTextView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom_background));
            }
        });
    }

    private void navigateToMainPage() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
