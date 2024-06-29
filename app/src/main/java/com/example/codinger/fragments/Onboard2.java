package com.example.codinger.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.codinger.R;


public class Onboard2 extends Fragment {


    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";

    private String title;
    private String description;

    public Onboard2() {
        // Required empty public constructor
    }

    public static Onboard2 newInstance(String title, String description) {
        Onboard2 fragment = new Onboard2();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            description = getArguments().getString(ARG_DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboard2, container, false);
        TextView titleTextView = view.findViewById(R.id.onboardingTitle);
        TextView descriptionTextView = view.findViewById(R.id.onboardingDescription);
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        return view;
    }
}
