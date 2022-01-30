package com.mobiddiction.fishsmart.Species;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiddiction.fishsmart.MyApplication;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.ScreenEnum;

import java.util.ArrayList;

public class SpeciesFragment extends Fragment {

    Button saltwaterbtn, freshwaterbtn, savedspeciesbtn;
    AppCompatActivity mActivity;
    ArrayList<SpeciesModel> speciesListSalt = new ArrayList<SpeciesModel>();
    ArrayList<SpeciesModel> speciesListFresh = new ArrayList<SpeciesModel>();
    ArrayList<SpeciesModel> speciesListSaved = new ArrayList<SpeciesModel>();
    RecyclerView listviewSalt, listviewFresh, listviewSaved;
    SpeciesSaltAdapter adapterSalt;
    SpeciesFreshAdapter adapterFresh;
    SpeciesSavedAdapter adapterSaved;
    ProgressBar progress_bar;
    TextView whattext;
  //  public RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public static SpeciesFragment newInstance(Context ctx) {
        SpeciesFragment fragment = new SpeciesFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public SpeciesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
         //   mParam1 = getArguments().getString(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_species, container, false);

        listviewSalt = v.findViewById(R.id.listviewSalt);
        listviewFresh = v.findViewById(R.id.listviewFresh);
        listviewSaved = v.findViewById(R.id.listviewSaved);

        listviewSalt.setLayoutManager(new LinearLayoutManager(mActivity));
        listviewSalt.setHasFixedSize(true);
        listviewFresh.setLayoutManager(new LinearLayoutManager(mActivity));
        listviewFresh.setHasFixedSize(true);
        listviewSaved.setLayoutManager(new LinearLayoutManager(mActivity));
        listviewSaved.setHasFixedSize(true);

        saltwaterbtn = v.findViewById(R.id.saltwaterbtn);
        freshwaterbtn = v.findViewById(R.id.freshwaterbtn);
        savedspeciesbtn = v.findViewById(R.id.savespeciesbtn);
        whattext = v.findViewById(R.id.what);

        Typeface face0 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Regular.otf");
        savedspeciesbtn.setTypeface(face0);
        Typeface face1 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Regular.otf");
        saltwaterbtn.setTypeface(face1);
        Typeface face2 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Regular.otf");
        freshwaterbtn.setTypeface(face2);
        Typeface face3 = Typeface.createFromAsset(mActivity.getAssets(), "Bariol_Regular.otf");
        whattext.setTypeface(face3);

        progress_bar = v.findViewById(R.id.progress_bar);

        adapterFresh = new SpeciesFreshAdapter(mActivity, speciesListFresh);

        listviewFresh.setAdapter(adapterFresh);
        adapterSalt = new SpeciesSaltAdapter(mActivity, speciesListSalt);

        listviewSalt.setAdapter(adapterSalt);
        adapterSaved = new SpeciesSavedAdapter(mActivity, null, speciesListSaved);

        listviewSaved.setAdapter(adapterSaved);

        listviewFresh.setVisibility(View.GONE);
        listviewSalt.setVisibility(View.GONE);
        listviewSaved.setVisibility(View.VISIBLE);

        savedspeciesbtn.setSelected(true);
      //  new SaltWaterAsync(SpeciesFragment.this, speciesListSalt).execute("/api/speciesData?speciesType=SALTWATER");
     //   new SaltWaterAsync(SpeciesFragment.this, speciesListFresh).execute("/api/speciesData?speciesType=FRESHWATER");

        savedspeciesbtn.setOnClickListener(listener);
        saltwaterbtn.setOnClickListener(listener);
        freshwaterbtn.setOnClickListener(listener);

        return v;
    }

    View.OnClickListener listener = new View.OnClickListener(){


        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.saltwaterbtn) {

                listviewSaved.setVisibility(View.GONE);
                listviewFresh.setVisibility(View.GONE);
                listviewSalt.setVisibility(View.VISIBLE);
                //((MyApplication) getActivity().getApplication()).getTrackingManager().trackScreen(ScreenEnum.SALTWATER_SPECIES, null);
                FirebaseAnalytics.getInstance(getActivity().getApplicationContext()).setCurrentScreen(getActivity(),ScreenEnum.SALTWATER_SPECIES, null);
                saltwaterbtn.setSelected(true);
                freshwaterbtn.setSelected(false);
                savedspeciesbtn.setSelected(false);
                saltwaterbtn.setTextColor(Color.parseColor("#df8871"));
                freshwaterbtn.setTextColor(Color.parseColor("#9b9b9b"));
                savedspeciesbtn.setTextColor(Color.parseColor("#9b9b9b"));

             //   new SpeciesAsync(SpeciesFragment.this, speciesListSalt).execute("/api/speciesData?speciesType=SALTWATER");
             //   adapter = new SpeciesAdapter(mActivity, speciesListSalt);
             //   listviewSalt.setAdapter(adapter);
              //  adapterSalt.notifyDataSetChanged();

            }else if (v.getId() == R.id.freshwaterbtn) {

                listviewSaved.setVisibility(View.GONE);
                listviewSalt.setVisibility(View.GONE);
                listviewFresh.setVisibility(View.VISIBLE);
                //((MyApplication) getActivity().getApplication()).getTrackingManager().trackScreen(ScreenEnum.FRESHWATER_SPECIES, null);
                FirebaseAnalytics.getInstance(getActivity().getApplicationContext()).setCurrentScreen(getActivity(),ScreenEnum.FRESHWATER_SPECIES, null);
                saltwaterbtn.setSelected(false);
                freshwaterbtn.setSelected(true);
                savedspeciesbtn.setSelected(false);
                saltwaterbtn.setTextColor(Color.parseColor("#9b9b9b"));
                freshwaterbtn.setTextColor(Color.parseColor("#df8871"));
                savedspeciesbtn.setTextColor(Color.parseColor("#9b9b9b"));

            //    new SpeciesAsync(SpeciesFragment.this, speciesListFresh).execute("/api/speciesData?speciesType=FRESHWATER");
              //  adapter = new SpeciesAdapter(mActivity, speciesListFresh);
              //  listviewFresh.setAdapter(adapter);
              //  adapterFresh.notifyDataSetChanged();

            }else if (v.getId() == R.id.savespeciesbtn)
            {
                listviewSaved.setVisibility(View.VISIBLE);
                listviewSalt.setVisibility(View.GONE);
                listviewFresh.setVisibility(View.GONE);
                //((MyApplication) getActivity().getApplication()).getTrackingManager().trackScreen(ScreenEnum.SAVED_SPECIES, null);
                FirebaseAnalytics.getInstance(getActivity().getApplicationContext()).setCurrentScreen(getActivity(),ScreenEnum.SAVED_SPECIES, null);
                saltwaterbtn.setSelected(false);
                freshwaterbtn.setSelected(false);
                savedspeciesbtn.setSelected(true);
                saltwaterbtn.setTextColor(Color.parseColor("#9b9b9b"));
                freshwaterbtn.setTextColor(Color.parseColor("#9b9b9b"));
                savedspeciesbtn.setTextColor(Color.parseColor("#df8871"));

            }else if (v.getId() == R.id.freshwaterbtn)
            {
              //  ((RulesActivity) mActivity).DisplayFreshWater();
            }

        }
    };

    public void SpeciesListDownloaded() {

        adapterSalt.notifyDataSetChanged();
        adapterFresh.notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
         //   mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
         //   mListener = (OnFragmentInteractionListener) mActivity;
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