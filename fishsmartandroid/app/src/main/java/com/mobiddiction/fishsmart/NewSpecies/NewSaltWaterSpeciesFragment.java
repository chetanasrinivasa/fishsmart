package com.mobiddiction.fishsmart.NewSpecies;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.ConnectionHelper;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.util.loadAllSpeciesData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by AI on 16/06/2017.
 */

public class NewSaltWaterSpeciesFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView listviewSalt;
    NewSpeciesSaltAdapter adapterSalt;
    public static SharedPreferences pref;
    TextView whattext;
    ProgressBar progress_bar;
    public Dialog dialog = null;
    private static final String LOG_TAG = NewSaltWaterSpeciesFragment.class.getSimpleName();
    public Handler mHandler = new Handler();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saltwater, container, false);

        pref = getActivity().getSharedPreferences("fishsmart", 0);

        setDialog();

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        listviewSalt = view.findViewById(R.id.listviewSalt);
        whattext = view.findViewById(R.id.what);
        whattext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

        progress_bar = view.findViewById(R.id.progress_bar);

        listviewSalt.setLayoutManager(new LinearLayoutManager(getActivity()));
        listviewSalt.setHasFixedSize(true);

        if (ModelManager.getInstance().getSaltWater() != null && ModelManager.getInstance().getSaltWater().size() > 0) {
            adapterSalt = new NewSpeciesSaltAdapter(getActivity(), ModelManager.getInstance().getSaltWater());
            listviewSalt.setAdapter(adapterSalt);
        }

        progress_bar.setVisibility(View.GONE);
        setData();
        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        setData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        return view;
    }

    private void setDialog() {
        if (pref != null && !pref.getBoolean("vergincallsaltwater", false)) {
            try {

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {

                    public void run() {

                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        final View dialoglayout = inflater.inflate(R.layout.first_popup, null);
                        YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                        TextView title = dialoglayout.findViewById(R.id.title);
                        TextView desc = dialoglayout.findViewById(R.id.desc);
                        Button thanksbtn = dialoglayout.findViewById(R.id.thanksbtn);

                        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        thanksbtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        title.setText("Saltwater");
                        desc.setText("Search for saltwater fish species here and find out about their fishing rules.");

                        dialog = new Dialog(getActivity());

                        dialog.setCanceledOnTouchOutside(false);

                        thanksbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putBoolean("vergincallsaltwater", true);
                                edit.commit();
                            }
                        });

                        int divierId = dialog.getContext().getResources()
                                .getIdentifier("android:id/titleDivider", null, null);
                        View divider = dialog.findViewById(divierId);
                        if (divider != null)
                            divider.setVisibility(View.INVISIBLE);

                        dialog.setContentView(dialoglayout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.show();
                    }
                });
            } catch (Exception ix) {

            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    static public Date LocalStringToDate(String stringToConvert) {
//        "utcTime":"2017-07-19T07:40:00Z"

        Log.d("NewSaltWaterFrag", " date  : " + stringToConvert);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date returnDate = null;
        try {
            returnDate = dateFormat.parse(stringToConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnDate;
    }

    @Subscribe
    public void onEventMainThread(BasicEvent event) {
        if (event == BasicEvent.SPECIES_DOWNLOADED) {
            Log.d("NewSaltWaterFrag","SPECIES_DOWNLOADED");
            if (ModelManager.getInstance().getSaltWater() != null && ModelManager.getInstance().getSaltWater().size() > 0) {
                attachAdapters();
            }

            DateFormat dateFormatTo = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
            dateFormatTo.setTimeZone(TimeZone.getDefault());
            if(Config.weatherList != null && Config.weatherList.size() > 0) {
                Date d = LocalStringToDate(Config.weatherList.get(0).getUtcTime());
                Toast.makeText(getActivity(), "Species Last Updated : " +  dateFormatTo.format(d) , Toast.LENGTH_SHORT).show();
            }
        }else if(event == BasicEvent.SPECIES_ERROR){
            Log.d("NewSaltWaterFrag","SPECIES_ERROR");
            if (ModelManager.getInstance().getSaltWater() != null && ModelManager.getInstance().getSaltWater().size() > 0) {
                attachAdapters();
            }
        }
    }

    public boolean isNetworkConnected() {
        return ConnectionHelper.isConnected(getContext());
    }

    public void setData() {
        Log.d("NewSaltWaterFrag","setData");
        ModelManager mModel = ModelManager.getInstance();
        if (mModel != null) {//check if data already loaded in DB
            if (mModel.getSaltWater() != null && mModel.getSaltWater().size() > 0) {
                Log.d("NewSaltWaterFrag","setData exists");
                attachAdapters();
            }else{//if data does not exists
                Log.d("NewSaltWaterFrag","setData does NOTT exists");
                if (isNetworkConnected()) {//check for connection
                    Log.d("NewSaltWaterFrag","setData Connection");
                    NetworkManager.getInstance().getAllSpecies(getActivity(),mHandler);
                }else {//if no cconnection, load from local file & push to DB
                    Log.d("NewSaltWaterFrag","setData No Connection");
                    loadAllSpeciesData mloadData = new loadAllSpeciesData(getActivity());
                    mloadData.loadDataFromLocal(true, null,null,mHandler);
                }
                //after loading from the backend or local file
                //attach adapters
                if (mModel.getSaltWater() != null && mModel.getSaltWater().size() > 0) {
                    attachAdapters();
                }
            }
        }
    }

    private void attachAdapters(){
        adapterSalt = new NewSpeciesSaltAdapter(getActivity(), ModelManager.getInstance().getSaltWater());
        listviewSalt.setAdapter(adapterSalt);
        progress_bar.setVisibility(View.GONE);
    }
}