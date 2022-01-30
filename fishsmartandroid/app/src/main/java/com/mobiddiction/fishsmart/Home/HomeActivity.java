package com.mobiddiction.fishsmart.Home;

import android.Manifest;
import android.app.Dialog;
import android.app.SearchManager;
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
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.ConnectionHelper;
import com.mobiddiction.fishsmart.CustomAlert.OnItemClickListener;
import com.mobiddiction.fishsmart.EmptyBodyRequest;
import com.mobiddiction.fishsmart.Gallery.GalleryFragment;
import com.mobiddiction.fishsmart.MyApplication;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Network.ResponseConfig;
import com.mobiddiction.fishsmart.Network.ResponseListner;
import com.mobiddiction.fishsmart.Profile.ProfileActivity;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.ScreenEnum;
import com.mobiddiction.fishsmart.Species.FeaturedSpeciesadapter;
import com.mobiddiction.fishsmart.Species.SearchAdapter;
import com.mobiddiction.fishsmart.Species.SearchModel;
import com.mobiddiction.fishsmart.Species.SpeciesDetailActivity;
import com.mobiddiction.fishsmart.Species.SpeciesModel;
import com.mobiddiction.fishsmart.Weather.WeatherActivity;
import com.mobiddiction.fishsmart.Weather.WeatherAsync;
import com.mobiddiction.fishsmart.bean.UserBean;
import com.mobiddiction.fishsmart.dao.AllSpecies;
import com.mobiddiction.fishsmart.dao.DeviceData;
import com.mobiddiction.fishsmart.dao.NEWSpeciesData;
import com.mobiddiction.fishsmart.dao.Notification;
import com.mobiddiction.fishsmart.notification.AppConstant;
import com.mobiddiction.fishsmart.notification.NotificationActivity;
import com.mobiddiction.fishsmart.notification.PostDeviceRegistration;
import com.mobiddiction.fishsmart.util.Utils;
import com.mobiddiction.fishsmart.util.loadAllSpeciesData;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Archa on 20/04/2016.
 */
public class HomeActivity extends BaseTabActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "HomeActivity";
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
    RecyclerView specieslistview;
    LinearLayout whetherlayout, gallerylayout;
    TextView loctitle, locdesc, temptext, highlowtext, textopengallery, latestphototext, text5, textlabel;
    TextView tidetext1, tidetext2, tidetext3, tidetext4, tidetext5, tidedata1, tidedata2, tidedata3, tidedata4, tidedata5;
    LinearLayout weblayout, linear6, linear7, linear8, linear9, linear10;
    RelativeLayout setting;
    ScrollView scrollview;
    public Dialog dialog = null;
    public SharedPreferences pref;
    GoogleApiClient googleApiClient = null;
    public ProgressBar progress_bar;
    Bundle savedInstanceState;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private CircleImageView imgUser;
    private ImageView imgNotification;
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    public Handler mHandler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState/*, R.layout.activity_home, false*/);
        setContentView(R.layout.activity_home);
        this.savedInstanceState = savedInstanceState;
        HomeTextChanger("FISHSMART NSW", this);
        isNetwork = checkConnectivity();

        Log.d(TAG, " onCreate");

        pref = getSharedPreferences("fishsmart", 0);
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        setting = findViewById(R.id.setting);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        hiddenButton = findViewById(R.id.hiddenButton);
        hiddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertOpenSetting();
            }
        });

        checkGPS();

        progress_bar = findViewById(R.id.progress_bar);
        latestphototext = findViewById(R.id.latestphototext);
        text5 = findViewById(R.id.text5);
        textlabel = findViewById(R.id.textlabel);
        tidetext1 = findViewById(R.id.tidetext1);
        tidetext2 = findViewById(R.id.tidetext2);
        tidetext3 = findViewById(R.id.tidetext3);
        tidetext4 = findViewById(R.id.tidetext4);
        tidetext5 = findViewById(R.id.tidetext5);
        tidedata1 = findViewById(R.id.tidedata1);
        tidedata2 = findViewById(R.id.tidedata2);
        tidedata3 = findViewById(R.id.tidedata3);
        tidedata4 = findViewById(R.id.tidedata4);
        tidedata5 = findViewById(R.id.tidedata5);

        textopengallery = findViewById(R.id.textopengallery);
        loctitle = findViewById(R.id.loctitle);
        locdesc = findViewById(R.id.locdesc);
        temptext = findViewById(R.id.temptext);
        highlowtext = findViewById(R.id.highlowtext);

        linear6 = findViewById(R.id.linear6);
        linear7 = findViewById(R.id.linear7);
        linear8 = findViewById(R.id.linear8);
        linear9 = findViewById(R.id.linear9);
        linear10 = findViewById(R.id.linear10);

        scrollview = findViewById(R.id.scrollview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        specieslistview = findViewById(R.id.hlist);
        ViewCompat.setNestedScrollingEnabled(specieslistview, false);
        // specieslistview.setNestedScrollingEnabled(false);
        specieslistview.setLayoutManager(layoutManager);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(false);

        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_header));

        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        imgUser = mActionBarView.findViewById(R.id.imgUser);
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        imgNotification = mActionBarView.findViewById(R.id.imgNotification);
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        });

        TextView nav_title = mActionBarView.findViewById(R.id.nav_title);

        nav_title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        nav_title.setText(Html.fromHtml("FISHSMART NSW"));

        listviewsearch = findViewById(R.id.listviewsearch);
        whetherlayout = findViewById(R.id.whetherlayout);
        gallerylayout = findViewById(R.id.gallerylayout);

        whetherlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, WeatherActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);
            }
        });

        gallerylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("com.fishsmart.gallery");
                sendBroadcast(i);
            }
        });


        // new SpeciesAsync(HomeActivity.this, speciesList).execute("/api/featuredSpecies");
        adapter = new SearchAdapter(this, R.layout.searchsuggestionitem, SearchList);
        listviewsearch.setAdapter(adapter);

        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // Refresh location data
                        setLocationData();
                        Log.d(TAG,"mySwipeRefreshLayout onRefreshed");
                        if(isNetworkConnected())
                            NetworkManager.getInstance().getAllSpecies(HomeActivity.this,mHandler);

                        Log.d(TAG , "mySwipeRefreshLayout replacing galleryFragment");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new GalleryFragment().newInstance(true)).commitAllowingStateLoss();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        if (isNetworkConnected()) {
            getNotificationList();
            Log.d(TAG, " getNotificationList");
            // Update Device Data
            if (ModelManager.getInstance() != null && ModelManager.getInstance().getDeviceData() != null && ModelManager.getInstance().getDeviceData().size() > 0) {
                // Update Device Data
                DeviceData deviceData =  ModelManager.getInstance().getDeviceData().get(0);
                updateDevice(HomeActivity.this, deviceData.getUdid(), deviceData.getId());
                Log.d(TAG, " Update Device");
            }else {
                // Add Device Data
                registerDevice(HomeActivity.this);
                Log.d(TAG, " Add Device");
            }
        } else {
            com.mobiddiction.fishsmart.CustomAlert.AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            //
                        }
                    });
        }

        // Get location data
        setLocationData();
        if(isNetworkConnected()) {
            NetworkManager.getInstance().getAllSpecies(HomeActivity.this,mHandler);
        }else{
            loadAllSpeciesData mloadData = new loadAllSpeciesData(HomeActivity.this);
            mloadData.loadDataFromLocal(true, null,null,mHandler);
        }
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new GalleryFragment().newInstance(true)).commitAllowingStateLoss();
            }
        }, 2000);*/
    }

    public void receiveItems() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(ModelManager.getInstance().getLoginResponse() != null) {
            if (ModelManager.getInstance() != null && ModelManager.getInstance().getLoginDetails() != null) {
                if (ModelManager.getInstance().getLoginResponse().getUserId() != null &&
                        !ModelManager.getInstance().getLoginResponse().getUserId().equals("")) {
                    imgUser.setVisibility(View.VISIBLE);
                }
            } else {
                imgUser.setVisibility(View.GONE);
            }
        } else {
            imgUser.setVisibility(View.GONE);
        }

        if (Utils.isOnline(this) && ModelManager.getInstance().getLoginResponse() != null) {
            NetworkManager.getInstance().getUserProfile(ModelManager.getInstance().getLoginResponse().getAuthorization(),
            "application/json", ModelManager.getInstance().getLoginResponse().getUserId(), responseListner);
        }
    }

    static public Date LocalStringToDate(String stringToConvert) {

        Log.d(TAG, " date  : " + stringToConvert);
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

    public void receiveWeatherItems() {
        DateFormat dateFormatTo = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
        dateFormatTo.setTimeZone(TimeZone.getDefault());
        if(Config.weatherList != null && Config.weatherList.size() > 0) {
            Date d = LocalStringToDate(Config.weatherList.get(0).getUtcTime());
            //Toast.makeText(this, "Weather Last Updated : " +  dateFormatTo.format(d) , Toast.LENGTH_SHORT).show();
        }

        for(int i=0; i<Config.weatherList.size(); i++)
        {
            loctitle.setText(Config.weatherList.get(i).getDescription());

            String[] tempsplit = Config.weatherList.get(i).getTemperature().split("C");
            String[] maxtemp = Config.weatherList.get(i).getMaximumTemperature().split("C");
            String[] mintemp = Config.weatherList.get(i).getMinimumTemperature().split("C");

            temptext.setText("Current Temperature: " + tempsplit[0] + "°");
            highlowtext.setText("H: " + maxtemp[0] + "°  L: " + mintemp[0] + "°");

            for (int j = 0; j < Config.weatherList.get(i).getForecastPeriod().size(); j++) {
                String[] splitdate = Config.weatherList.get(i).getForecastPeriod().get(j).getStartTimeLocal().split("T");

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();

                if (dateFormat.format(date).equals(splitdate[0])) {
                    locdesc.setText(Config.weatherList.get(i).getForecastPeriod().get(j).getForecast());
                }
            }

            for (int l = 0; l < Config.weatherList.get(i).getTideData().size(); l++) {
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                DateFormat outputFormat = new SimpleDateFormat("hh:mm a");

                try {

                    String[] value = Config.weatherList.get(i).getTideData().get(l).getValue().split(" ");

                    if (Config.weatherList.get(i).getTideData().get(l).getSequence().equals("1")) {
                        linear6.setVisibility(View.VISIBLE);
                        if (Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low")) {
                            tidetext1.setText("Low tide");
                            tidedata1.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        } else {
                            tidetext1.setText("High tide");
                            tidedata1.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if (Config.weatherList.get(i).getTideData().get(l).getSequence().equals("2")) {
                        linear7.setVisibility(View.VISIBLE);
                        if (Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low")) {
                            tidetext2.setText("Low tide");
                            tidedata2.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        } else {
                            tidetext2.setText("High tide");
                            tidedata2.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if (Config.weatherList.get(i).getTideData().get(l).getSequence().equals("3")) {
                        linear8.setVisibility(View.VISIBLE);
                        if (Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low")) {
                            tidetext3.setText("Low tide");
                            tidedata3.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        } else {
                            tidetext3.setText("High tide");
                            tidedata3.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if (Config.weatherList.get(i).getTideData().get(l).getSequence().equals("4")) {
                        linear9.setVisibility(View.VISIBLE);
                        if (Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low")) {
                            tidetext4.setText("Low tide");
                            tidedata4.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        } else {
                            tidetext4.setText("High tide");
                            tidedata4.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if (Config.weatherList.get(i).getTideData().get(l).getSequence().equals("5")) {
                        linear10.setVisibility(View.VISIBLE);
                        if (Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low")) {
                            tidetext5.setText("Low tide");
                            tidedata5.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        } else {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchMenuItem.setVisible(false);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search for fish...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                SearchList.clear();

                if (newText.length() > 2) {

                    new HomeSearchAsync(HomeActivity.this, SearchList).execute("/api/search?keyword=" + newText);

                    listviewsearch.setVisibility(View.VISIBLE);
                } else {
                    listviewsearch.setVisibility(View.GONE);
                }

                return false;
            }
        });

        return true;
    }

//    private BaseAdapter mAdapter = new BaseAdapter() {
//
//        private TextView exclaimtext, description, sizelimittext, fishsubname, rulesapply;
//        private LinearLayout exclamlayout, mainlayout, linklayout;
//        private ImageView icon;
//        private Button detailbtn;
//
//        @Override
//        public int getCount() {
//            if (ModelManager.getInstance().getNewSpeciesDataFeatured() != null && ModelManager.getInstance().getNewSpeciesDataFeatured().size() > 0) {
//                return ModelManager.getInstance().getNewSpeciesDataFeatured().size();
//            } else {
//                return 0;
//            }
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homespecies_item, null);
//
//            icon = (ImageView) v.findViewById(R.id.fishimg);
//
//            exclaimtext = (TextView) v.findViewById(R.id.exclaimtext);
//            exclaimtext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
//
//            exclamlayout = (LinearLayout) v.findViewById(R.id.exclamlayout);
//            mainlayout = (LinearLayout) v.findViewById(R.id.mainlayout);
//            linklayout = (LinearLayout) v.findViewById(R.id.linklayout);
//
//            description = (TextView) v.findViewById(R.id.fishname);
//            description.setTypeface(Typeface.createFromAsset(getAssets(), "bariol_bold.otf"));
//
//            sizelimittext = (TextView) v.findViewById(R.id.sizelimittext);
//            sizelimittext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
//
//            fishsubname = (TextView) v.findViewById(R.id.fishsubname);
//            fishsubname.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
//
//            rulesapply = (TextView) v.findViewById(R.id.rulesapply);
//            rulesapply.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
//
//            detailbtn = (Button) v.findViewById(R.id.detailbtn);
//
//
//            List<NEWSpeciesData> newSpeciesDataListFeaturedTrue = ModelManager.getInstance().getNewSpeciesDataFeatured();
//
//
//            final AllSpecies item = ModelManager.getInstance().getSpeciesByid(newSpeciesDataListFeaturedTrue.get(position).getId() + "");
////            newSpeciesDataListFeaturedTrue.get(position);
//
////            final SpeciesModel item = speciesList.get(position);
//
////            Glide.with(getBaseContext()).load(item.getThumbnailImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(icon);
//
//            exclaimtext.setText(item.getTitle());
//            description.setText(item.getTitle());
//            sizelimittext.setText("Size Limit: " + item.getSizeLimit());
//            fishsubname.setText(item.getDescription());
//
//            if (item.getBagLimit().equals("")) {
//                rulesapply.setText("Rules Apply");
//                exclamlayout.setVisibility(View.VISIBLE);
//            } else {
//                if (item.getGrouped() != null && !item.getGrouped()) {
//
//                    exclamlayout.setVisibility(View.INVISIBLE);
//                } else {
//                    exclamlayout.setVisibility(View.INVISIBLE);
//                }
//
//                if (item.getBagLimit().equals("null")) {
//                    rulesapply.setText("Bag Limit: N/A");
//                } else {
//                    rulesapply.setText("Bag Limit: " + item.getBagLimit());
//                }
//            }
//
//            if (item.getTitle().equals("")) {
//                exclamlayout.setVisibility(View.INVISIBLE);
//            } else {
//                if (item.getGrouped() != null && !item.getGrouped())
//                    exclamlayout.setVisibility(View.INVISIBLE);
//                else
//                    exclamlayout.setVisibility(View.VISIBLE);
//            }
//
//            detailbtn.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                    Intent intent = new Intent(HomeActivity.this, SpeciesDetailActivity.class);
//                    intent.putExtra("fishid", item.getId());
//                    if (item.getSpeciesType().equalsIgnoreCase("SALTWATER")) {
//                        intent.putExtra("ISSALTFISH", true);
//                    } else {
//                        intent.putExtra("ISSALTFISH", false);
//                    }
//                    intent.putExtra("isSaved", false);
//                    startActivityForResult(intent, 10);
//                    overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);
////                    Intent intent = new Intent(HomeActivity.this, SpeciesDetailActivity.class);
////                    intent.putExtra("fishid", item.getId());
////
////                    startActivityForResult(intent, 10);
////                    overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);
//                }
//            });
//
//            return v;
//        }
//    };

    public Boolean checkConnectivity() {
        if (ConnectionHelper.isConnectedOrConnecting(getApplicationContext())) {
            hideErrorsBar(true);
            return true;
        } else {
//            hideErrorsBar(false);
            return false;
        }
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        final UserBean userBean = (UserBean) obj;
        if (userBean.image.size() > 0) {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Glide.with(HomeActivity.this)
                            .asBitmap()
                            .apply(new RequestOptions().dontAnimate().centerCrop())
                            .load(userBean.image.get(0).url)
                            .into(new BitmapImageViewTarget(imgUser) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    imgUser.setImageDrawable(circularBitmapDrawable);
                                }
                            });
                }
            });
        }
    }*/

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void checkGPS() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);
            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:

                            if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                setting.setVisibility(View.VISIBLE);
                                setLocationPermissionlayout();
                                if(ConnectionHelper.isConnected(HomeActivity.this)) {
                                    Log.d(TAG, "Location Success WeatherAsync");
                                    new WeatherAsync(HomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
                                }
                            } else {
                                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                final Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                if(location != null) {
                                    Config.latitude = location.getLatitude();
                                    Config.longitude = location.getLongitude();
                                    new WeatherAsync(HomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
                                }else{
                                    new WeatherAsync(HomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
                                }
                            }
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(HomeActivity.this, 1000);
                            } catch (IntentSender.SendIntentException e) {
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1000:
                switch (resultCode) {
                    case HomeActivity.RESULT_OK:
                        setLocationPermissionlayout();
                        new CountDownTimer(3000, 10000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                try {
                                    statusCheck();

                                } catch (Exception e) {

                                }
                            }
                        }.start();
                        break;
                    case HomeActivity.RESULT_CANCELED:
                        checkGPS();
                        break;
                    case 1:
                        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        final Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        Config.latitude = location.getLatitude();
                        Config.longitude = location.getLongitude();
                        new WeatherAsync(HomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
                        break;
                }
                break;

            case 2000:
                switch (resultCode) {
                    case HomeActivity.RESULT_OK:
                        setLocationPermissionlayout();
                        break;
                }
                break;
        }
    }

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
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            final Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            new WeatherAsync(HomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
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


    public void setLocationPermissionlayout() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            setting.setVisibility(View.VISIBLE);
        } else {
            setting.setVisibility(View.GONE);
        }
    }


    private void alertOpenSetting() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
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
        Intent myAppSettings = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getApplication().getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myAppSettings, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        Utils.freeMemory();
    }

    @Subscribe
    public void onEventMainThread(BasicEvent event) {

        if(event == BasicEvent.FEATURED_SUCCESS){

        }
        if (event == BasicEvent.SPECIES_DOWNLOADED) {

            List<NEWSpeciesData> listofFeatured = ModelManager.getInstance().getNewSpeciesDataFeatured();
            if( listofFeatured != null && listofFeatured.size() > 0){
                // Get existing featured species list
                setAdapter();

                if (!pref.getBoolean("vergincallhome", false)) {
                    try {

                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {

                            public void run() {

                                LayoutInflater inflater = getLayoutInflater();
                                final View dialoglayout = inflater.inflate(R.layout.first_popup, null);
                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                TextView title = dialoglayout.findViewById(R.id.title);
                                TextView desc = dialoglayout.findViewById(R.id.desc);
                                Button thanksbtn = dialoglayout.findViewById(R.id.thanksbtn);

                                title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                                desc.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                                thanksbtn.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

                                title.setText("Home");
                                desc.setText("Check out weather and tide info here, see our featured fish species or search for a fish.");

                                dialog = new Dialog(HomeActivity.this);
                                dialog.setCanceledOnTouchOutside(false);

                                thanksbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                SharedPreferences.Editor edit = pref.edit();
                                                edit.putBoolean("vergincallhome", true);
                                                edit.commit();
                                            }
                                        }, 5000);
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
        }
        if (event == BasicEvent.ONLINE) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.container, new GalleryFragment().newInstance(true)).commit();
            }
        }

        if (event == BasicEvent.OFFLINE) {

        }

        if(event == BasicEvent.GET_NOTIFICATION_SUCCESS){
            if(ModelManager.getInstance().getNotificationAlertUnRead().size() > 0){
                // Show unread notification alert
                Notification notification = ModelManager.getInstance().getNotificationAlertUnRead().get(0);

                String DeviceOs = "";
                if (ModelManager.getInstance().getNotificationDeviceData().size() > 0) {
                    if (ModelManager.getInstance().getNotificationDeviceData().get(0).getDeviceType() != null) {
                        DeviceOs = ModelManager.getInstance().getNotificationDeviceData().get(0).getDeviceType();
                    }
                }

                if (!DeviceOs.equalsIgnoreCase("ios")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.DialogTheme);
                            builder.setTitle(notification.getNotifTitle())
                                    .setMessage(notification.getNotifText())
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, final int id) {
                                            for(Notification notificationItem : ModelManager.getInstance().getNotificationAlertUnRead()){
                                                if(ModelManager.getInstance().getLoginResponse() != null) {
                                                    NetworkManager.getInstance().updateNotificationAlertRead(ModelManager.getInstance().getLoginResponse().getAuthorization(), EmptyBodyRequest.INSTANCE, notificationItem.getId());
                                                }
                                            }
                                        }
                                    });
                            final AlertDialog alert = builder.create();
                            alert.show();
                            Window window = alert.getWindow();
                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        }
                    });
                }
            }
        }

        if(event == BasicEvent.GET_NOTIFICATION_FAILED){
        }

        if(event == BasicEvent.NOTIFICATION_ADD_DEVICE){
            Log.d(TAG," NOTIFICATION_ADD_DEVICE");
        }

        if(event == BasicEvent.NOTIFICATION_UPDATE_DEVICE){
            Log.d(TAG, " NOTIFICATION_UPDATE_DEVICE");
        }
        if(event == BasicEvent.NOTIFICATION_DEVICE_NOT_FOUND){
            registerDevice(HomeActivity.this);
            Log.d(TAG, " NOTIFICATION_DEVICE_NOT_FOUND");
        }
    }


    private void setUserData(final UserBean userBean) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ( userBean != null ){
                    if( userBean.image != null ) {
                        if (userBean.image.size() > 0) {
                            Picasso.get()
                                    .load(userBean.image.get(0).url)
                                    .into(imgUser);
                        }
                    }
                }
            }
        });
    }

    private UserBean myUserBean;
    ResponseListner responseListner = new ResponseListner() {
        @Override
        public void onResponse(String tag, int result, Object obj) {
            if (result == ResponseConfig.API_SUCCESS) {
                if (tag.equals(ResponseConfig.TAG_GET_USER_PROFILE)) {
                    UserBean userBean = (UserBean) obj;
                    myUserBean = userBean;
                    setUserData(userBean);
                }

            } else {
                com.mobiddiction.fishsmart.util.Log.print("=========== API CALL FAIL ============");
            }
        }
    };


    private void getNotificationList() {
        if (ModelManager.getInstance() != null){
            if (ModelManager.getInstance().getLoginDetails() != null) {
                if (ModelManager.getInstance().getLoginResponse().getUserId() != null) {
                    if (!ModelManager.getInstance().getLoginResponse().getUserId().equals("")) {
                        try {
                            NetworkManager.getInstance().getNotification(ModelManager.getInstance().getLoginResponse().getAuthorization(), ModelManager.getInstance().getLoginResponse().getUserId() + "");
                            Log.d(TAG, "getNotification");
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            if (ModelManager.getInstance() != null){
                if (ModelManager.getInstance().getDeviceData() != null ) {
                    if (ModelManager.getInstance().getDeviceData().size() > 0) {
                        DeviceData deviceData = ModelManager.getInstance().getDeviceData().get(0);
                        String deviceId = deviceData.getId();
                        String authorization = "";
                        try {
                            if (ModelManager.getInstance().getLoginResponse() != null) {
                                authorization = ModelManager.getInstance().getLoginResponse().getAuthorization();
                                NetworkManager.getInstance().getNotificationByDeviceId(authorization, deviceId + "");
                                Log.d(TAG, "getNotificationByDeviceId");
                            } else {

                            }
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void setLocationData(){
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setLocationPermissionlayout();

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (googleApiClient == null) {
                googleApiClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();

                googleApiClient.connect();
            }
        }
        //((MyApplication) getApplication()).getTrackingManager().trackScreen(ScreenEnum.WEATHER_DETAILS, null);
        FirebaseAnalytics.getInstance(getApplicationContext()).setCurrentScreen(this,ScreenEnum.WEATHER_DETAILS, null);

        //Call to /api/location not needed anymore
        /*if (ConnectionHelper.isConnected(this)) {
            Log.d(TAG, "setLocationData WeatherAsync");
            new WeatherAsync(HomeActivity.this, SearchList).execute("/api/location"); // api for location search
        }*/

        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ConnectionHelper.isConnected(this)) {
                Log.d(TAG, "setLocationData checkSelfPermission WeatherAsync");
                new WeatherAsync(HomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
            }
        } else {
            final Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                new WeatherAsync(HomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
            } else {
                new WeatherAsync(HomeActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
            }
        }
    }

    public void registerDevice(Context context){

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( HomeActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String regId = instanceIdResult.getToken();
                String udid = id(HomeActivity.this);
                String deviceName = AppConstant.getDeviceName();
                String deviceOs = null;
                try {
                    deviceOs = Build.VERSION.BASE_OS;
                } catch (java.lang.NoSuchFieldError e) {
                    e.printStackTrace();
                    deviceOs = "ANDROID OS " + Build.VERSION.SDK_INT;
                }
                String deviceType = "ANDROID";
                String versionName = "";
                String baseURL = Config.HOST;
                boolean pushNotiifcation = true;
                boolean active = true;
                String userId = "0";
                String authorization = "";

                if(ModelManager.getInstance().getLoginResponse() != null) {
                    try {
                        userId = ModelManager.getInstance().getLoginResponse().getUserId() + "";
                        authorization = ModelManager.getInstance().getLoginResponse().getAuthorization();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    try {
                        versionName = context.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    PostDeviceRegistration deviceRegistration = new PostDeviceRegistration(udid, regId, deviceName, deviceOs, deviceType, versionName, baseURL,
                            pushNotiifcation, active, Integer.parseInt(userId), 1);
                    NetworkManager.getInstance().postDeviceRegistration(authorization, deviceRegistration);
                }
            }
        });
    }

    public void updateDevice(Context context, String udid, String deviceId){

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( HomeActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String regId = instanceIdResult.getToken();
                String deviceName = AppConstant.getDeviceName();
                String deviceOs = null;
                try {
                    deviceOs = Build.VERSION.BASE_OS;
                } catch (java.lang.NoSuchFieldError e) {
                    e.printStackTrace();
                    deviceOs = "ANDROID OS " + Build.VERSION.SDK_INT;
                }
                String deviceType = "ANDROID";
                String versionName = "";
                String userId = "0";
                String authorization = "";
                if(ModelManager.getInstance().getLoginResponse() != null) {
                    try {

                        userId = ModelManager.getInstance().getLoginResponse().getUserId() + "";
                        authorization = ModelManager.getInstance().getLoginResponse().getAuthorization();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    try {
                        versionName = context.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    PostDeviceRegistration deviceRegistration = new PostDeviceRegistration(udid, regId, deviceName, deviceOs, deviceType, versionName, Config.HOST,
                            true, true, Integer.parseInt(userId), 1);
                    NetworkManager.getInstance().updateDeviceRegistration(authorization,
                            deviceId, deviceRegistration);
                }
            }
        });
    }

    public boolean isNetworkConnected() {
        return ConnectionHelper.isConnected(this);
    }

    private void setAdapter(){
        List<NEWSpeciesData> sources = ModelManager.getInstance().getNewSpeciesDataFeatured();
        specieslistview.setAdapter(new FeaturedSpeciesadapter(HomeActivity.this, sources,
                new FeaturedSpeciesadapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(AllSpecies item) {
                        try {
                            Intent intent = new Intent(HomeActivity.this, SpeciesDetailActivity.class);
                            intent.putExtra("fishid", item.getId());
                            if (item.getSpeciesType().equalsIgnoreCase("SALTWATER")) {
                                intent.putExtra("ISSALTFISH", true);
                            } else {
                                intent.putExtra("ISSALTFISH", false);
                            }
                            intent.putExtra("isSaved", false);
                            HomeActivity.this.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public synchronized static String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }

}