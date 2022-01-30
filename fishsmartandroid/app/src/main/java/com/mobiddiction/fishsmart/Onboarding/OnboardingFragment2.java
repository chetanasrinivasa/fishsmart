package com.mobiddiction.fishsmart.Onboarding;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mobiddiction.fishsmart.R;

@SuppressLint("ValidFragment")
public class OnboardingFragment2 extends Fragment {
    OnboardingActivity onboarding;
    public OnboardingFragment2(OnboardingActivity onboarding) {
        // Required empty public constructor
        this.onboarding = onboarding;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_onboarding_fragment2, container, false);
        return view;
    }

}
