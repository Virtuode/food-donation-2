package com.example.codinger.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.codinger.R;
import com.example.codinger.activities.DonationActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment implements ItemClickListener {

    CardView cardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        cardView = view.findViewById(R.id.cardview_click);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new instance of PostFragment
                PostFragment postFragment = new PostFragment();

                // Use FragmentManager to replace the current fragment with PostFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, postFragment);
                transaction.addToBackStack(null);  // Add to back stack to allow back navigation
                transaction.commit();
            }
        });


        List<SlideModel> imageList = new ArrayList<>();
        // Add images to the list
        SlideModel slide1 = new SlideModel("https://firebasestorage.googleapis.com/v0/b/codinger-e417c.appspot.com/o/image1.jpg?alt=media&token=ade9d7e6-2364-4db7-8338-fec3cfaa654a", "A beautiful sunset", ScaleTypes.CENTER_CROP);
        SlideModel slide2 = new SlideModel("https://firebasestorage.googleapis.com/v0/b/codinger-e417c.appspot.com/o/gettyimages-1252924066-612x612.jpg?alt=media&token=e2327c75-e6eb-4861-8157-e7c5cda596f2", "Mountain view", ScaleTypes.CENTER_CROP);
        SlideModel slide3 = new SlideModel("https://firebasestorage.googleapis.com/v0/b/codinger-e417c.appspot.com/o/image3.jpg?alt=media&token=3b7e3012-964b-4adf-af15-6ed2053fcaff", "City skyline", ScaleTypes.CENTER_CROP);

        imageList.add(slide1);
        imageList.add(slide2);
        imageList.add(slide3);

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP);
        imageSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE);
        imageSlider.startSliding(2000);

        // Set ItemClickListener
        imageSlider.setItemClickListener(this);
    }

    @Override
    public void onItemSelected(int position) {
        // Handle click event here
        switch (position) {
            case 0:
                // Action for position 0
                // Action for position 0
                try {
                    Intent intent = new Intent(getActivity(), DonationActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    // Display the exception message in a Toast
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                break;
            case 1:
                // Action for position 1
                Toast.makeText(getContext(), "Monday", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                // Action for position 2
                Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                // Action for position 3
                Toast.makeText(getContext(), "Wednesday", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                // Action for position 4
                Toast.makeText(getContext(), "Thursday", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                // Action for position 5
                Toast.makeText(getContext(), "Friday", Toast.LENGTH_SHORT).show();
                break;

            default:
                // Default action
                Toast.makeText(getContext(), "Invalid value", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void doubleClick(int i) {

    }
}