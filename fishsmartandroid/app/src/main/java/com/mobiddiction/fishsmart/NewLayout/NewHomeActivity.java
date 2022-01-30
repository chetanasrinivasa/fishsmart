package com.mobiddiction.fishsmart.NewLayout;

/**
 * Created by AI on 21/06/2017.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.Home.HorizontalListView;
import com.mobiddiction.fishsmart.NewSpecies.NewSpeciesActivity;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.Species.SearchAdapter;
import com.mobiddiction.fishsmart.Species.SearchModel;
import com.mobiddiction.fishsmart.Species.SpeciesModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Archa on 20/04/2016.
 */
public class NewHomeActivity extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Boolean isNetwork = true;

    private SearchView searchView;
    private MenuItem searchMenuItem;
    ArrayList<SearchModel> SearchList = new ArrayList<>();
    ListView listviewsearch;
    SearchAdapter adapter;
    ArrayList<SpeciesModel> speciesList = new ArrayList<SpeciesModel>();
    public Handler handler = new Handler();
    PagerAdapter pageradapter;
    ViewPager viewpager;
    Button hiddenButton;
    HorizontalListView specieslistview;
    LinearLayout whetherlayout, gallerylayout;

    TextView loctitle,locdesc, temptext, highlowtext, textopengallery, latestphototext, text5, textlabel;
    TextView tidetext1, tidetext2, tidetext3, tidetext4, tidetext5, tidedata1, tidedata2, tidedata3, tidedata4, tidedata5;
    LinearLayout weblayout, linear6, linear7, linear8, linear9, linear10;
    RelativeLayout setting;
    ScrollView scrollview;
    public Dialog dialog = null;
    public SharedPreferences pref;
    GoogleApiClient googleApiClient = null;
    public ProgressBar progress_bar;


    @SuppressLint("MissingSuperCall")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_home, container, false);
//        super.onCreate(savedInstanceState, R.layout.activity_home, false);
//        BaseTabActivity.HomeTextChanger("FISHSMART NSW");
        isNetwork = checkConnectivity();
        Log.d("TESTTTT" , " on create home");
//        new SpeciesAsync(NewHomeActivity.this, speciesList).execute("/api/featuredSpecies");
        pref = getActivity().getSharedPreferences("fishsmart", 0);
//cs: remove me
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(10000);
//        mLocationRequest.setFastestInterval(5000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        setting = view.findViewById(R.id.setting);
        hiddenButton = view.findViewById(R.id.hiddenButton);
        hiddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertOpenSetting();
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            setting.setVisibility(View.VISIBLE);
            checkGPS();
        }

        if(!pref.getBoolean("vergincallhome", false))
        {
            try {

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable(){

                    public void run() {

                        LayoutInflater inflater = getLayoutInflater(savedInstanceState);
                        final View dialoglayout = inflater.inflate(R.layout.first_popup, null);
                        YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                        TextView title = dialoglayout.findViewById(R.id.title);
                        TextView desc = dialoglayout.findViewById(R.id.desc);
                        Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);

                        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        thanksbtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        title.setText("Home");
                        desc.setText("Check out weather and tide info here, see our featured fish species or search for a fish.");

                        dialog = new Dialog(getActivity());

                        dialog.setCanceledOnTouchOutside(false);

                        thanksbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putBoolean("vergincallhome", true);
                                edit.commit();
                            }
                        });

                        int divierId = dialog.getContext().getResources()
                                .getIdentifier("android:id/titleDivider", null, null);
                        View divider = dialog.findViewById(divierId);
                        if(divider != null)
                            divider.setVisibility(View.INVISIBLE);

                        dialog.setContentView(dialoglayout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.show();

                    }
                });


            }catch (Exception ix)
            {

            }
        }

        progress_bar = view.findViewById(R.id.progress_bar);
        latestphototext = view.findViewById(R.id.latestphototext);
        latestphototext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

        text5 = view.findViewById(R.id.text5);
        text5.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

        textlabel = view.findViewById(R.id.textlabel);
        textlabel.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

        tidetext1 = view.findViewById(R.id.tidetext1);
        tidetext1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        tidetext2 = view.findViewById(R.id.tidetext2);
        tidetext2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        tidetext3 = view.findViewById(R.id.tidetext3);
        tidetext3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        tidetext4 = view.findViewById(R.id.tidetext4);
        tidetext4.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        tidetext5 = view.findViewById(R.id.tidetext5);
        tidetext5.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        tidedata1 = view.findViewById(R.id.tidedata1);
        tidedata1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        tidedata2 = view.findViewById(R.id.tidedata2);
        tidedata2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        tidedata3 = view.findViewById(R.id.tidedata3);
        tidedata3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        tidedata4 = view.findViewById(R.id.tidedata4);
        tidedata4.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        tidedata5 = view.findViewById(R.id.tidedata5);
        tidedata5.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

        textopengallery = view.findViewById(R.id.textopengallery);
        textopengallery.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        loctitle = view.findViewById(R.id.loctitle);
        loctitle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));
        locdesc = view.findViewById(R.id.locdesc);
        locdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        temptext = view.findViewById(R.id.temptext);
        temptext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));
        highlowtext = view.findViewById(R.id.highlowtext);
        highlowtext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

        linear6 = view.findViewById(R.id.linear6);
        linear7 = view.findViewById(R.id.linear7);
        linear8 = view.findViewById(R.id.linear8);
        linear9 = view.findViewById(R.id.linear9);
        linear10 = view.findViewById(R.id.linear10);

        scrollview = view.findViewById(R.id.scrollview);
        specieslistview  = view.findViewById(R.id.hlist);

        specieslistview.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);

                        break;
                }

                v.onTouchEvent(event);
                return true;
            }
        });

        //   viewpager = (ViewPager) findViewById(R.id.viewPager);

//        final ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();

//        actionBar.setDisplayShowHomeEnabled(false);
//
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_header));
//
//        if (savedInstanceState == null) {
//            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, new GalleryActivity.GalleryFragment()).commit();
//        }
//
//        View mActionBarView = getLayoutInflater(savedInstanceState).inflate(R.layout.custom_actionbar, null);
//        actionBar.setCustomView(mActionBarView);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

//        TextView nav_title = (TextView) mActionBarView.findViewById(R.id.nav_title);
//
//        nav_title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
//        nav_title.setText(Html.fromHtml("FISHSMART NSW"));
//
//        listviewsearch = (ListView) mActionBarView.findViewById(R.id.listviewsearch);
//        whetherlayout = (LinearLayout) mActionBarView.findViewById(R.id.whetherlayout);
//        gallerylayout = (LinearLayout) mActionBarView.findViewById(R.id.gallerylayout);

//        whetherlayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getActivity(), WeatherActivity.class);
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);
//            }
//        });
//
//        gallerylayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent();
//                i.setAction("com.fishsmart.gallery");
//                getActivity().sendBroadcast(i);
//            }
//        });

//        adapter = new SearchAdapter(getActivity(), R.layout.searchsuggestionitem, SearchList);
//        listviewsearch.setAdapter(adapter);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            new WeatherAsync(NewHomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
            setLocationPermissionlayout();
        }
        else
        {
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            final Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                checkGPS();
            } else {
                if(location != null)
                {
                    Config.latitude = location.getLatitude();
                    Config.longitude = location.getLongitude();
//                    new WeatherAsync(com.mobiddiction.fishsmart.Home.NewHomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
                }
            }
        }
        return view;
    }

    public void receiveItems() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                adapter.notifyDataSetChanged();

            }
        }, 500);
    }

    public void receiveSpeciesItems()
    {
        specieslistview.setOnItemClickListener(null);
        specieslistview.setAdapter(mAdapter);

        //  pageradapter = new ViewPagerAdapter(NewHomeActivity.this, speciesList);
        //   viewpager.setAdapter(pageradapter);


    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        Log.d("TESTTTT" , " on resume home");
//        setLocationPermissionlayout();
//
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            googleApiClient = new GoogleApiClient.Builder(this)
//                    .addApi(LocationServices.API)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .build();
//
//            googleApiClient.connect();
//        }
//        ((MyApplication) getApplication()).getTrackingManager().trackScreen(ScreenEnum.WEATHER_DETAILS, null);
//        new WeatherAsync(NewHomeActivity.this, SearchList).execute("/api/v1/location"); // api for location search
//
//        if (ActivityCompat.checkSelfPermission(NewHomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NewHomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            new WeatherAsync(NewHomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
//        }
//        else
//        {
//            final Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//            if(location != null) {
//                Log.d("TESTTTT" , " location : " + "lat=" + location.getLatitude() + " , lon=" + location.getLongitude());
//                new WeatherAsync(NewHomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
//            }
//            else{
//                Log.d("TESTTTT" , " location null");
//                new WeatherAsync(NewHomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
//            }
//        }
//    }

    public void receiveWeatherItems()
    {
        for(int i=0; i<Config.weatherList.size(); i++)
        {

            loctitle.setText(Config.weatherList.get(i).getDescription());
            String[] tempsplit = Config.weatherList.get(i).getTemperature().split("C");
            String[] maxtemp = Config.weatherList.get(i).getMaximumTemperature().split("C");
            String[] mintemp = Config.weatherList.get(i).getMinimumTemperature().split("C");

            temptext.setText("Current Temperature: "+tempsplit[0]+"°");
            highlowtext.setText("H: "+maxtemp[0]+"°  L: "+mintemp[0]+"°");

            for(int j=0; j<Config.weatherList.get(i).getForecastPeriod().size(); j++)
            {
                String[] splitdate = Config.weatherList.get(i).getForecastPeriod().get(j).getStartTimeLocal().split("T");

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();

                if(dateFormat.format(date).equals(splitdate[0]))
                {
                    locdesc.setText(Config.weatherList.get(i).getForecastPeriod().get(j).getForecast());
                }
            }

            for(int l=0; l<Config.weatherList.get(i).getTideData().size(); l++)
            {
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                DateFormat outputFormat = new SimpleDateFormat("hh:mm a");

                try {

                    String[] value = Config.weatherList.get(i).getTideData().get(l).getValue().split(" ");

                    if(Config.weatherList.get(i).getTideData().get(l).getSequence().equals("1"))
                    {
                        linear6.setVisibility(View.VISIBLE);
                        if(Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low"))
                        {
                            tidetext1.setText("Low tide");
                            tidedata1.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                        else
                        {
                            tidetext1.setText("High tide");
                            tidedata1.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if(Config.weatherList.get(i).getTideData().get(l).getSequence().equals("2"))
                    {
                        linear7.setVisibility(View.VISIBLE);
                        if(Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low"))
                        {
                            tidetext2.setText("Low tide");
                            tidedata2.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                        else
                        {
                            tidetext2.setText("High tide");
                            tidedata2.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if(Config.weatherList.get(i).getTideData().get(l).getSequence().equals("3"))
                    {
                        linear8.setVisibility(View.VISIBLE);
                        if(Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low"))
                        {
                            tidetext3.setText("Low tide");
                            tidedata3.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                        else
                        {
                            tidetext3.setText("High tide");
                            tidedata3.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if(Config.weatherList.get(i).getTideData().get(l).getSequence().equals("4"))
                    {
                        linear9.setVisibility(View.VISIBLE);
                        if(Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low"))
                        {
                            tidetext4.setText("Low tide");
                            tidedata4.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                        else
                        {
                            tidetext4.setText("High tide");
                            tidedata4.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if(Config.weatherList.get(i).getTideData().get(l).getSequence().equals("5"))
                    {
                        linear10.setVisibility(View.VISIBLE);
                        if(Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low"))
                        {
                            tidetext5.setText("Low tide");
                            tidedata5.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                        else
                        {
                            tidetext5.setText("High tide");
                            tidedata5.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchMenuItem = menu.findItem(R.id.search);
//        searchView = (SearchView) searchMenuItem.getActionView();
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setQueryHint("Search for fish...");
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                SearchList.clear();
//
//                new HomeSearchAsync(NewHomeActivity.this, SearchList).execute("/api/search?keyword=" + newText);
//                if (newText.length() > 2) {
//
//                    listviewsearch.setVisibility(View.VISIBLE);
//                } else {
//                    listviewsearch.setVisibility(View.GONE);
//                }
//
//                return false;
//            }
//
//        });
//
//        return true;
//    }

    private BaseAdapter mAdapter = new BaseAdapter() {

        private TextView exclaimtext, description, sizelimittext, fishsubname, rulesapply;
        private LinearLayout exclamlayout, mainlayout, linklayout;
        private ImageView icon;
        private Button detailbtn;

        @Override
        public int getCount() {
            return speciesList.size();

        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homespecies_item, null);

            icon = v.findViewById(R.id.fishimg);

            exclaimtext = v.findViewById(R.id.exclaimtext);
            exclaimtext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            exclamlayout = v.findViewById(R.id.exclamlayout);
            mainlayout = v.findViewById(R.id.mainlayout);
            linklayout = v.findViewById(R.id.linklayout);

            description = v.findViewById(R.id.fishname);
            description.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            sizelimittext = v.findViewById(R.id.sizelimittext);
            sizelimittext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            fishsubname = v.findViewById(R.id.fishsubname);
            fishsubname.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            rulesapply = v.findViewById(R.id.rulesapply);
            rulesapply.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            detailbtn = v.findViewById(R.id.detailbtn);

            final SpeciesModel item = speciesList.get(position);

            Glide.with(getActivity().getBaseContext()).load(item.getThumbnail()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_grey)).into(icon);

            exclaimtext.setText(item.getGroupName());
            description.setText(item.getTitle());
            sizelimittext.setText("Size Limit: " + item.getSizeLimit());
            fishsubname.setText(item.getDescription());

            if (item.getBagLimitForCardView().equals(""))
            {
                rulesapply.setText("Rules Apply");
                exclamlayout.setVisibility(View.VISIBLE);
            }
            else
            {
                if(!item.getGrouped()) {

                    exclamlayout.setVisibility(View.INVISIBLE);
                } else {
                    exclamlayout.setVisibility(View.INVISIBLE);
                }

                if (item.getBagLimitForCardView().equals("null")) {
                    rulesapply.setText("Bag Limit: N/A");
                } else {
                    rulesapply.setText("Bag Limit: "+ item.getBagLimitForCardView());
                }
            }

            if (item.getGroupName().equals(""))
            {
                exclamlayout.setVisibility(View.INVISIBLE);
            }
            else
            {
                if(!item.getGrouped())
                    exclamlayout.setVisibility(View.INVISIBLE);
                else
                    exclamlayout.setVisibility(View.VISIBLE);
            }

            detailbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), NewSpeciesActivity.class);
                    intent.putExtra("fishid", item.getId());

                    startActivityForResult(intent, 10);
                    getActivity().overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);
                }
            });

            return v;
        }
    };

    public Boolean checkConnectivity()
    {
//        if(ConnectionHelper.isConnectedOrConnecting(getActivity().getApplicationContext())) {
//            BaseTabActivity.hideErrorsBar(true);
//            return true;
//        }else {
//            BaseTabActivity.hideErrorsBar(false);
//            return false;
//        }
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    public void checkGPS() {
//cs: TODO: find eqvt of check gps in mapbox and implement here
//cs: remove me
//        if (googleApiClient == null) {
//            googleApiClient = new GoogleApiClient.Builder(getActivity())
//                    .addApi(LocationServices.API)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this).build();
//            googleApiClient.connect();
//            LocationRequest locationRequest = LocationRequest.create();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(30 * 1000);
//            locationRequest.setFastestInterval(5 * 1000);
//            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//            builder.setAlwaysShow(true);
//            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
//            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//                @Override
//                public void onResult(LocationSettingsResult result) {
//                    final Status status = result.getStatus();
//                    final LocationSettingsStates state = result.getLocationSettingsStates();
//                    switch (status.getStatusCode()) {
//                        case LocationSettingsStatusCodes.SUCCESS:
//                            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                setLocationPermissionlayout();
////                                new WeatherAsync(com.mobiddiction.fishsmart.Home.NewHomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
//                            }
//                            else
//                            {
//                                LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//                                final Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
////                                new WeatherAsync(com.mobiddiction.fishsmart.Home.NewHomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
//                            }
//                            break;
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                            try {
//                                status.startResolutionForResult(getActivity(), 1000);
//                            } catch (IntentSender.SendIntentException e) {
//                            }
//                            break;
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//
//                            break;
//                    }
//                }
//            });
//        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 1000:
//                switch (resultCode) {
//                    case NewHomeActivity.RESULT_OK:
//                        new CountDownTimer(3000,10000) {
//                            @Override
//                            public void onTick(long millisUntilFinished) {
//                            }
//                            @Override
//                            public void onFinish() {
//                                try {
//                                    statusCheck();
//
//                                } catch (Exception e){
//
//                                }
//                            }
//                        }.start();
//                        break;
//                    case NewHomeActivity.RESULT_CANCELED:
//                        checkGPS();
//                        break;
//                    case 1:
//                        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                        final Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                        new WeatherAsync(NewHomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
//                        break;
//                }
//                break;
//        }
//    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else{
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            final Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            new WeatherAsync(com.mobiddiction.fishsmart.Home.NewHomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),1);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public void setLocationPermissionlayout()
    {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            setting.setVisibility(View.VISIBLE);
        } else{
            setting.setVisibility(View.GONE);
        }
    }


    private void alertOpenSetting() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Location, Storage and Telephone permissions are required to use this app.");
        alertDialogBuilder.setMessage("Please enable these permissions in Permissions under app settings.");
        alertDialogBuilder.setNegativeButton("Go to setting", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                goToSettings();
            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }


    private void goToSettings() {
        Intent myAppSettings = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getActivity().getApplication().getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myAppSettings, 2000);
    }

}