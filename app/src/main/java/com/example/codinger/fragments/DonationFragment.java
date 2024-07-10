package com.example.codinger.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.codinger.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonationFragment extends Fragment {

    private TextView descriptionText, addressText, quantityText;
    private SeekBar quantitySeekBar;
    private ImageView editIcon;
    private DatabaseReference databaseReference;

    public DonationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation, container, false);

        // Initialize views
        descriptionText = view.findViewById(R.id.description_text);
        addressText = view.findViewById(R.id .address_text);
        quantityText = view.findViewById(R.id.quantity_text);
        quantitySeekBar = view.findViewById(R.id.customSeekBar);
        editIcon = view.findViewById(R.id.edit_icon);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("donation");

        // Retrieve data from Firebase and set to views
        loadDataFromFirebase();

        // SeekBar change listener
        quantitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) { // Only update if it's a user-initiated change
                    quantityText.setText(progress + " meal");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

        // Edit icon click listener
        editIcon.setOnClickListener(v -> {
            // Open a dialog to edit the quantity
            showEditDialog();
        });

        return view;
    }

    private void loadDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get data from Firebase and set to views
                String description = dataSnapshot.child("description").getValue(String.class);
                String address = dataSnapshot.child("address").getValue(String.class);
                int quantity = dataSnapshot.child("quantity").getValue(Integer.class);

                descriptionText.setText(description);
                addressText.setText(address);
                // Update SeekBar progress only if it's a user-initiated change
                if (!quantitySeekBar.isPressed()) {
                    quantitySeekBar.setProgress(quantity);
                }
                quantityText.setText(quantity + " meal");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
                Log.e("DonationDetailsFragment", "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void showEditDialog() {
        // Implement a dialog to edit the quantity
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Quantity");

        // Set up the input
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            int quantity = Integer.parseInt(input.getText().toString());
            quantitySeekBar.setProgress(quantity); // Update SeekBar
            databaseReference.child("quantity").setValue(quantity); // Update database
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}