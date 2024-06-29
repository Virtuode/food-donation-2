package com.example.codinger.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.codinger.R;

public class ChatFragment extends Fragment {

    private Button btnDonateNow, btnSelfDonate, btnHelpDonate;
    private boolean isExpanded = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        btnDonateNow = view.findViewById(R.id.btnDonateNow);
        btnSelfDonate = view.findViewById(R.id.btnSelfDonate);
        btnHelpDonate = view.findViewById(R.id.btnHelpDonate);

        btnDonateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    collapseButtons();
                } else {
                    expandButtons();
                }
                isExpanded = !isExpanded;
            }
        });

        return view;
    }

    private void expandButtons() {
        btnSelfDonate.setVisibility(View.VISIBLE);
        btnHelpDonate.setVisibility(View.VISIBLE);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(btnSelfDonate, "translationY", btnDonateNow.getHeight(), 0f);
        animator1.setDuration(300);
        animator1.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(btnHelpDonate, "translationY", btnDonateNow.getHeight(), 0f);
        animator2.setDuration(300);
        animator2.start();
    }

    private void collapseButtons() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(btnSelfDonate, "translationY", 0f, btnDonateNow.getHeight());
        animator1.setDuration(300);
        animator1.start();
        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                btnSelfDonate.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(btnHelpDonate, "translationY", 0f, btnDonateNow.getHeight());
        animator2.setDuration(300);
        animator2.start();
        animator2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {

                btnHelpDonate.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }
}