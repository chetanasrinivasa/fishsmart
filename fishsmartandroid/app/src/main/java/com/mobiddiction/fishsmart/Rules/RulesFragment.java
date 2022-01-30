package com.mobiddiction.fishsmart.Rules;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiddiction.fishsmart.R;

import java.util.ArrayList;

public class RulesFragment extends Fragment {

    Button explorebtn, savedbtn;
    RelativeLayout saltwaterbtn, freshwaterbtn;
    private OnFragmentInteractionListener mListener;
    AppCompatActivity mActivity;
    TextView rules, water, trout, spear, local, regional, saltwaterbtntext, freshwaterbtntext;

    LinearLayout waterdynamiclayout, troutdynamiclayout, speardynamiclayout, localdynamiclayout, regionaldynamiclayout;
    RelativeLayout waterlayout, troutlayout, spearlayout, locallayout, regionallayout;

    ProgressBar progress_bar;

    ArrayList<RulesModel> rulesList = new ArrayList<RulesModel>();

    public static RulesFragment newInstance(Context ctx) {
        RulesFragment fragment = new RulesFragment();
       // Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
      //  args.putString(ARG_PARAM2, param2);
     //   fragment.setArguments(args);
        return fragment;
    }

    public RulesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        //    mParam1 = getArguments().getString(ARG_PARAM1);
        //    mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rules, container, false);

        explorebtn = v.findViewById(R.id.explorebtn);
        savedbtn = v.findViewById(R.id.savedbtn);
        saltwaterbtn = v.findViewById(R.id.saltwaterbtn);
        freshwaterbtn = v.findViewById(R.id.freshwaterbtn);

        waterdynamiclayout = v.findViewById(R.id.waterdynamiclayout);
        troutdynamiclayout = v.findViewById(R.id.troutdynamiclayout);
        speardynamiclayout = v.findViewById(R.id.speardynamiclayout);
        localdynamiclayout = v.findViewById(R.id.localdynamiclayout);
        regionaldynamiclayout = v.findViewById(R.id.regionaldynamiclayout);

        waterlayout = v.findViewById(R.id.waterlayout);
        troutlayout = v.findViewById(R.id.troutlayout);
        spearlayout = v.findViewById(R.id.spearlayout);
        locallayout = v.findViewById(R.id.locallayout);
        regionallayout = v.findViewById(R.id.regionallayout);

        progress_bar = v.findViewById(R.id.progress_bar);

     //   new RulesAsync(RulesFragment.this, rulesList).execute("/api/fishingGuide");

        // start change font

        rules = v.findViewById(R.id.rules);
        Typeface face1 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Bold.otf");
        rules.setTypeface(face1);

        water = v.findViewById(R.id.water);
        Typeface face2 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Bold.otf");
        water.setTypeface(face2);

        trout = v.findViewById(R.id.trout);
        Typeface face3 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Bold.otf");
        trout.setTypeface(face3);

        spear = v.findViewById(R.id.spear);
        Typeface face4 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Bold.otf");
        spear.setTypeface(face4);

        local = v.findViewById(R.id.local);
        Typeface face5 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Bold.otf");
        local.setTypeface(face5);

        regional = v.findViewById(R.id.regional);
        Typeface face6 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Bold.otf");
        regional.setTypeface(face6);

        saltwaterbtntext = v.findViewById(R.id.saltwaterbtntext);
        Typeface face7 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Regular.otf");
        saltwaterbtntext.setTypeface(face7);

        freshwaterbtntext = v.findViewById(R.id.freshwaterbtntext);
        Typeface face8 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Regular.otf");
        freshwaterbtntext.setTypeface(face8);

        Typeface face9 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Regular.otf");
        explorebtn.setTypeface(face9);

        Typeface face10 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Regular.otf");
        savedbtn.setTypeface(face10);

        // end change font

        explorebtn.setSelected(true);

        explorebtn.setOnClickListener(listener);
        savedbtn.setOnClickListener(listener);
        saltwaterbtn.setOnClickListener(listener);
        freshwaterbtn.setOnClickListener(listener);

        return v;
    }

    View.OnClickListener listener = new View.OnClickListener(){


        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.explorebtn)
            {
                explorebtn.setSelected(true);
                savedbtn.setSelected(false);
                explorebtn.setTextColor(Color.parseColor("#df8871"));
                savedbtn.setTextColor(Color.parseColor("#9b9b9b"));

            }else if (v.getId() == R.id.savedbtn)
            {
                explorebtn.setSelected(false);
                savedbtn.setSelected(true);
                explorebtn.setTextColor(Color.parseColor("#9b9b9b"));
                savedbtn.setTextColor(Color.parseColor("#df8871"));

            }else if (v.getId() == R.id.saltwaterbtn)
            {
                ((RulesActivity) mActivity).DisplaySaltWater();

            }else if (v.getId() == R.id.freshwaterbtn)
            {
                ((RulesActivity) mActivity).DisplayFreshWater();
            }

        }
    };

    public void RulesListDownloaded() {

        for( int i=0; i<rulesList.size(); i++)
        {
          //  Log.d("rulesList", rulesList.get(i).getTitle());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        //    mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}