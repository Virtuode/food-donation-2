package com.example.codinger.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;

import com.example.codinger.R;
import com.example.codinger.activities.MapActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PostFragment extends Fragment {

    private static final int PICK_IMAGE = 100;
    private ImageView imageView;
    private EditText descriptionEditText;
    private TextView locationTextView;
    private Uri imageUri;
    private String selectedLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);


        imageView = view.findViewById(R.id.postImageView);
        ImageButton editIcon = view.findViewById(R.id.editImageButton);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        ImageButton locationIcon = view.findViewById(R.id.locationButton);
        locationTextView = view.findViewById(R.id.locationTextView);
        Button saveButton = view.findViewById(R.id.saveButton);

        editIcon.setOnClickListener(v -> openGallery());
        locationIcon.setOnClickListener(v -> openMapActivity());
//        saveButton.setOnClickListener(v -> saveToFirebase());

        return view;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void openMapActivity() {
        Intent intent = new Intent(requireContext(), MapActivity.class);
        startActivity(intent);
    }

    public void setLocation(String location) {
        selectedLocation = location;
        locationTextView.setText(location);
    }

    private void saveToFirebase() {
        String description = descriptionEditText.getText().toString();
        if (imageUri != null && !description.isEmpty() && selectedLocation != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Postdonate");

            String postId = databaseReference.push().getKey();
            Map<String, Object> postMap = new HashMap<>();
            postMap.put("imageUri", imageUri.toString());
            postMap.put("description", description);
            postMap.put("location", selectedLocation);

            databaseReference.child(postId).setValue(postMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Post saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to save post", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}

