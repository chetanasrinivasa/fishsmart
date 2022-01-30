package com.mobiddiction.fishsmart.Onboarding;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.dao.Onboarding;
import com.squareup.picasso.Picasso;

@SuppressLint("ValidFragment")
public class OnboardingFragment1 extends Fragment {

    OnboardingActivity onboardingActivity;
    Onboarding onboarding;
    FrameLayout frameLayout;
    ImageView background_image;
    ImageView icon_header;
    TextView title,caption;

    public OnboardingFragment1(OnboardingActivity onboardingActivity, Onboarding onboarding) {
        // Required empty public constructor
        this.onboardingActivity = onboardingActivity;
        this.onboarding = onboarding;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_onboarding_fragment1, container, false);
        frameLayout = view.findViewById(R.id.frameLayout);
        background_image = view.findViewById(R.id.background_image);
        icon_header = view.findViewById(R.id.icon_header);
        title = view.findViewById(R.id.title);
        caption = view.findViewById(R.id.caption);

        title.setText(onboarding.getHeadline());
        caption.setText(onboarding.getShortDescription());
        if (onboarding.getImageURL() != null && !onboarding.getImageURL().equals("")) {
            Picasso.get().load(onboarding.getImageURL()).into(icon_header);
        }
        if(onboarding.getBackgroundImageURL() != null && !onboarding.getBackgroundImageURL().equals("")){
            Picasso.get().load(onboarding.getBackgroundImageURL()).into(background_image);
        } else {
            if (onboarding.getBackgroundColor() != null && !onboarding.getBackgroundColor().equals("")){
                frameLayout.setBackgroundColor(Color.parseColor(onboarding.getBackgroundColor()));
            }
        }

        return view;
    }

}
