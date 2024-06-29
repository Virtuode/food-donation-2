package com.example.codinger.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.codinger.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileChangeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private CircularImageView profileImageView;
    private FloatingActionButton fabEditImage;
    private TextView tvUsername, tvCancel, tvSave;
    private ImageView ivEditUsername;
    private EditText etNewUsername;
    private BottomSheetDialog bottomSheetDialog;
    private Uri imageUri;
    private ProgressBar progressBar;

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);

        profileImageView = findViewById(R.id.profile_image);
        fabEditImage = findViewById(R.id.fab_edit_image);
        tvUsername = findViewById(R.id.tv_username);
        ivEditUsername = findViewById(R.id.iv_edit_username);
        progressBar = findViewById(R.id.progress_bar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        storageReference = FirebaseStorage.getInstance().getReference("profile_pictures").child(user.getUid());

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        if (user != null) {
            loadUserProfile();
        }

        fabEditImage.setOnClickListener(v -> openFileChooser());

        ivEditUsername.setOnClickListener(v -> openBottomSheet());
    }

    private void loadUserProfile() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue(String.class);
                String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue(String.class);

                tvUsername.setText(username);
                if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                    Glide.with(ProfileChangeActivity.this)
                            .load(profileImageUrl)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(profileImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImageView.setImageBitmap(bitmap);
                uploadImageToFirebase(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebase(Bitmap bitmap) {
        if (imageUri != null) {
            progressBar.setVisibility(View.VISIBLE);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            byte[] data = baos.toByteArray();

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("image/jpeg")
                    .build();

            StorageReference fileReference = storageReference.child("profile.jpg");
            UploadTask uploadTask = fileReference.putBytes(data, metadata);

            uploadTask.addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                databaseReference.child("profileImageUrl").setValue(imageUrl);
                Glide.with(ProfileChangeActivity.this)
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(profileImageView);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileChangeActivity.this, "Profile image updated", Toast.LENGTH_SHORT).show();
            })).addOnFailureListener(e -> {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileChangeActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void openBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_edit_username,null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);

        etNewUsername = view.findViewById(R.id.et_new_username);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvSave = view.findViewById(R.id.tv_save);

        tvCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        tvSave.setOnClickListener(v -> {
            String newUsername = etNewUsername.getText().toString().trim();
            if (!newUsername.isEmpty()) {
                databaseReference.child("username").setValue(newUsername);
                tvUsername.setText(newUsername);
                bottomSheetDialog.dismiss();
            } else {
                etNewUsername.setError("Username cannot be empty");
            }
        });

        bottomSheetDialog.show();
    }
}