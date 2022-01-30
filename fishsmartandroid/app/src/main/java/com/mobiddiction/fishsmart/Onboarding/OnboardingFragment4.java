package com.mobiddiction.fishsmart.Onboarding;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mobiddiction.fishsmart.MainFrameActivity;
import com.mobiddiction.fishsmart.Network.ResponseConfig;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.SignIn.SignInActivity;
import com.mobiddiction.fishsmart.SignUp.SignUpActivity;
import com.mobiddiction.fishsmart.dao.Onboarding;
import com.mobiddiction.fishsmart.util.Pref;
import com.squareup.picasso.Picasso;

@SuppressLint("ValidFragment")
public class OnboardingFragment4 extends Fragment {

    FrameLayout frameLayout;
    Button register, login, btnSkip;
    ImageView background_image;
    ImageView icon_header;
    TextView title, caption;

    Onboarding onboarding;

    public OnboardingFragment4(Onboarding onboarding) {
        // Required empty public constructor
        this.onboarding = onboarding;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_onboarding_fragment4, container, false);
        register = view.findViewById(R.id.register);
        login = view.findViewById(R.id.login);
        btnSkip = view.findViewById(R.id.btnSkip);

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SignUpActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SignInActivity.class));
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainFrameActivity.class));
            }
        });
        Pref.setValue(getActivity(), ResponseConfig.PREF_IS_LOAD_BOARD, 1);
        return view;
    }
}