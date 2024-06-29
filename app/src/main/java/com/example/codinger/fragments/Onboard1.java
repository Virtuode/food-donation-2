package com.example.codinger.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.codinger.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Onboard1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Onboard1 extends Fragment {


        private static final String ARG_TITLE = "title";
        private static final String ARG_DESCRIPTION = "description";

        private String title;
        private String description;

        public Onboard1() {
            // Required empty public constructor
        }

        public static Onboard1 newInstance(String title, String description) {
            Onboard1 fragment = new Onboard1();
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
            View view = inflater.inflate(R.layout.fragment_onboard1, container, false);
            TextView titleTextView = view.findViewById(R.id.onboardingTitle);
            TextView descriptionTextView = view.findViewById(R.id.onboardingDescription);
            titleTextView.setText(title);
            descriptionTextView.setText(description);
            return view;
        }
}
