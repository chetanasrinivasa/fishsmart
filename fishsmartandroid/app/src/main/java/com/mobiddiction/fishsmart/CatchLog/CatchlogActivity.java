package com.mobiddiction.fishsmart.CatchLog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.appcompat.app.ActionBar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.CustomAlert.AlertDialog;
import com.mobiddiction.fishsmart.CustomAlert.OnItemClickListener;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Network.ResponseConfig;
import com.mobiddiction.fishsmart.Network.ResponseListner;
import com.mobiddiction.fishsmart.Profile.ProfileActivity;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.SignIn.SignInActivity;
import com.mobiddiction.fishsmart.SignUp.SignUpActivity;
import com.mobiddiction.fishsmart.bean.CatchLogBean;
import com.mobiddiction.fishsmart.bean.CatchLogImageBean;
import com.mobiddiction.fishsmart.bean.CatchLogLocationBean;
import com.mobiddiction.fishsmart.bean.CatchLogSpeciesCaughtBean;
import com.mobiddiction.fishsmart.bean.CatchLogSpeciesCaughtMeasureBean;
import com.mobiddiction.fishsmart.bean.UserBean;
import com.mobiddiction.fishsmart.bll.CatchLogBll;
import com.mobiddiction.fishsmart.notification.NotificationActivity;
import com.mobiddiction.fishsmart.util.Log;
import com.mobiddiction.fishsmart.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Krunal on 27/11/2018.
 */

public class CatchlogActivity extends BaseTabActivity implements View.OnClickListener {
    public static SharedPreferences pref;
    private CircleImageView imgUser;
    private ImageView imgNotification;

    // 3 Tab variable declaration
    private LinearLayout llCatches;
    private TextView txtTotalCatches;
    private TextView txtTitleCatches;

    private LinearLayout llSpecies;
    private TextView txtTotalSpecies;
    private TextView txtTitleSpecies;

    private LinearLayout llWhere;
    private ImageView imgWhere;
    private TextView txtTitleWhere;

    private ImageView imgCatlogAdd;
    private FragmentManager fragmentManager;
    private ArrayList<CatchLogBean> allCatchLogList;
    private RelativeLayout relProgressBar;

    public static ArrayList<CatchLogLocationBean> catchlogAllLocationList;
    private ArrayList<CatchLogLocationBean> locationList;
    private ArrayList<ArrayList<CatchLogSpeciesCaughtBean>> allWaterList;

    // User not logged in
    private RelativeLayout relNoUserLogin;
    private FrameLayout frameLayout;
    private LinearLayout llTab;
    private TextView txtMessage;
    private LinearLayout llLogin;
    private TextView txtRegister;
    private TextView txtLogin;
    private boolean isUserloggedIn = false;
    public static ArrayList<ArrayList<CatchLogSpeciesCaughtBean>> allCatchWaterList;
    private int clickRecordPos;
    private final long timestamp = System.currentTimeMillis();
    private CatchLogWhereFragment catchLogWhereFragment;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState/*, R.layout.activity_catchlog, false*/);
        setContentView(R.layout.activity_catchlog);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        HomeTextChanger("Catch log", this);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_header));
        pref = getSharedPreferences("fishsmart", 0);
        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        imgNotification = mActionBarView.findViewById(R.id.imgNotification);
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatchlogActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        TextView nav_title = mActionBarView.findViewById(R.id.nav_title);
        nav_title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        nav_title.setText(Html.fromHtml("CATCH LOG"));
        imgUser = mActionBarView.findViewById(R.id.imgUser);
        imgUser.setVisibility(View.VISIBLE);

        relProgressBar = findViewById(R.id.relProgressBar);
        llCatches = findViewById(R.id.llCatches);
        txtTotalCatches = findViewById(R.id.txtTotalCatches);
        txtTitleCatches = findViewById(R.id.txtTitleCatches);

        llSpecies = findViewById(R.id.llSpecies);
        txtTotalSpecies = findViewById(R.id.txtTotalSpecies);
        txtTitleSpecies = findViewById(R.id.txtTitleSpecies);

        llWhere = findViewById(R.id.llWhere);
        imgWhere = findViewById(R.id.imgWhere);
        txtTitleWhere = findViewById(R.id.txtTitleWhere);

        imgCatlogAdd = findViewById(R.id.imgCatlogAdd);

        relNoUserLogin = findViewById(R.id.relNoUserLogin);
        frameLayout = findViewById(R.id.frameLayout);
        llTab = findViewById(R.id.llTabs);
        txtMessage = findViewById(R.id.txtMessage);
        llLogin = findViewById(R.id.llLogin);
        txtRegister = findViewById(R.id.txtRegister);
        txtLogin = findViewById(R.id.txtLogin);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        txtTotalCatches.setTypeface(typeface);
        txtTitleCatches.setTypeface(typeface);

        txtTotalSpecies.setTypeface(typeface);
        txtTitleSpecies.setTypeface(typeface);

        txtTitleWhere.setTypeface(typeface);

        txtMessage.setTypeface(typeface);
        txtRegister.setTypeface(typeface);
        txtLogin.setTypeface(typeface);

        fragmentManager = getSupportFragmentManager();

        imgUser.setOnClickListener(this);
        llCatches.setOnClickListener(this);
        llSpecies.setOnClickListener(this);
        llWhere.setOnClickListener(this);
        imgCatlogAdd.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
    }

    ResponseListner responseListner = new ResponseListner() {
        @Override
        public void onResponse(String tag, int result, Object obj) {
            if (result == ResponseConfig.API_SUCCESS) {
                if (tag.equals(ResponseConfig.TAG_GET_CATCH_SPECIES)) {
                    allCatchWaterList = (ArrayList<ArrayList<CatchLogSpeciesCaughtBean>>) obj;
                    Collections.sort(allCatchWaterList.get(0), new ObjectComparator());
                    Collections.sort(allCatchWaterList.get(1), new ObjectComparator());
                    getCatchLogListAPI();
                } else if (tag.equals(ResponseConfig.TAG_GET_CATCHLOG)) {
                    allCatchLogList = (ArrayList<CatchLogBean>) obj;
                    // Get Offline Data
                    if(allCatchLogList != null) {
                        allCatchLogList.addAll(generateLocalDataFromDB());
                        Collections.sort(allCatchLogList, byDate);
                    }

                    for (int i = 0; i < allCatchLogList.size(); i++) {
                        for (int j = 0; j < allCatchLogList.get(i).speciesCaughtList.size(); j++) {

                            //FIXME spacies value must be a valid integer
                            int id = -1;
                            try {
                                id = Integer.parseInt(allCatchLogList.get(i).speciesCaughtList.get(j).species);
                            } catch (NumberFormatException e) {

                            }
                            for (int k = 0; k < allCatchWaterList.size(); k++) {
                                for (int l = 0; l < allCatchWaterList.get(k).size(); l++) {
                                    if (allCatchWaterList.get(k).get(l).id == id) {
                                        allCatchLogList.get(i).speciesCaughtList.get(j).isSpeciesName = true;
                                        allCatchLogList.get(i).speciesCaughtList.get(j).speciesName = allCatchWaterList.get(k).get(l).species;
                                    }
                                }
                            }
                        }
                    }

                    if (allCatchLogList.size() > 0) {
                        CatchlogActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                relNoUserLogin.setVisibility(View.GONE);
                                frameLayout.setVisibility(View.VISIBLE);
                                llLogin.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        CatchlogActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                relNoUserLogin.setVisibility(View.VISIBLE);
                                frameLayout.setVisibility(View.INVISIBLE);
                                llLogin.setVisibility(View.GONE);
                            }
                        });
                    }
                    getUserProfile();
                    generateAllTabData();
                } else if (tag.equals(ResponseConfig.TAG_GET_CATCHLOG_IMAGE)) {
                    ArrayList<CatchLogImageBean> imageList = (ArrayList<CatchLogImageBean>) obj;
                    CatchLogBean catchLogBean = allCatchLogList.get(clickRecordPos);
                    catchLogBean.catchLogImageList = imageList;
                    Intent intent = new Intent(CatchlogActivity.this, AddCatchlogActivity.class);
                    intent.putExtra("CatchLogData", catchLogBean);
                    intent.putExtra("isRecordExist", 1);
                    startActivity(intent);
                } else if (tag.equals(ResponseConfig.TAG_GET_USER_PROFILE)) {
                    final UserBean userBean = (UserBean) obj;
                    if (userBean.image.size() > 0) {
                        CatchlogActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                Glide.with(CatchlogActivity.this)
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

//                                Glide.with(CatchlogActivity.this).load(userBean.image.get(0).url)
//                                        .apply(new RequestOptions().dontAnimate().centerCrop())
//                                        .asBitmap()
//                                        into(new BitmapImageViewTarget(imgUser) {
//                                    @Override
//                                    protected void setResource(Bitmap resource) {
//                                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
//                                        circularBitmapDrawable.setCircular(true);
//                                        imgUser.setImageDrawable(circularBitmapDrawable);
//                                    }
//                                });
                            }
                        });
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "API SUCCESS " + result);
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, tag);
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Catchlog screen");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            } else {
                // Failure message
                Log.print("=========== API CALL FAIL ============");
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "API FAIL " + result);
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, tag);
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Catchlog screen");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            }

            CatchlogActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    relProgressBar.setVisibility(View.GONE);
                }
            });
        }
    };

    private ArrayList<CatchLogBean> generateLocalDataFromDB() {
        CatchLogBll catchLogBll = new CatchLogBll(CatchlogActivity.this);
        ArrayList<CatchLogBean> getCatchLogList = catchLogBll.getCatchLogList();
        ArrayList<CatchLogBean> myDbList = new ArrayList<>();
        try {
            JSONObject obj = null;
            JSONArray jsonArrays = null;
            CatchLogBean catchBean;
            for (CatchLogBean catchLogBean : getCatchLogList) {
                catchBean = new CatchLogBean();
                obj = new JSONObject(convertJSONString(catchLogBean.myJson.substring(catchLogBean.myJson.indexOf("{"), catchLogBean.myJson.lastIndexOf("}") + 1)));
                catchBean.id = obj.getInt("id");
                catchBean.userId = obj.getInt("userId");
                catchBean.startDate = obj.getString("startDate");
                catchBean.endDate = obj.getString("endDate");
                catchBean.createdDate = obj.getString("createdDate");
                catchBean.lastModifiedDate = obj.getString("lastModifiedDate");
                catchBean.imagePath = obj.getString("imagePath");
                catchBean.comment = obj.getString("comment");


                catchBean.hasLicense = obj.getInt("hasLicense") == 1;

                catchBean.fromBoat = obj.getInt("fromBoat") == 1;

                JSONObject locationJson = obj.getJSONObject("location");
                catchBean.location.lat = locationJson.getDouble("lat");
                catchBean.location.lon = locationJson.getDouble("lon");
                catchBean.location.name = locationJson.getString("name");
                catchBean.location.createdDate = obj.getString("createdDate");
                catchBean.location.imageUrl = catchBean.imagePath;
                catchBean.location.lastModifiedDate = obj.getString("lastModifiedDate");

                // speciesCaughtList
                jsonArrays = obj.getJSONArray("speciesCaughtList");
                JSONObject speciesObjec;

                JSONArray measurementsArray;
                JSONObject measurementObj;
                String[] methodArray;
                for (int i = 0; i < jsonArrays.length(); i++) {
                    speciesObjec = jsonArrays.getJSONObject(i);
                    CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean = new CatchLogSpeciesCaughtBean();
                    catchLogSpeciesCaughtBean.species = speciesObjec.getString("species");
                    catchLogSpeciesCaughtBean.method = speciesObjec.getString("method");
                    catchLogSpeciesCaughtBean.kept = speciesObjec.getInt("kept");
                    catchLogSpeciesCaughtBean.released = speciesObjec.getInt("released");

                    if (catchLogSpeciesCaughtBean.method.contains(",")) {
                        methodArray = catchLogSpeciesCaughtBean.method.split(",");
                        if (methodArray[0].trim().equals("31291")) {
                            catchLogSpeciesCaughtBean.isBaitSelcted = true;
                            catchLogSpeciesCaughtBean.methodIds.put("Bait", "31291");
                        } else {
                            catchLogSpeciesCaughtBean.isLureSelcted = true;
                            catchLogSpeciesCaughtBean.methodIds.put("Lure", "31960");
                        }

                        if (methodArray[1].trim().equals("31960")) {
                            catchLogSpeciesCaughtBean.isLureSelcted = true;
                            catchLogSpeciesCaughtBean.methodIds.put("Lure", "31960");
                        } else {
                            catchLogSpeciesCaughtBean.isBaitSelcted = true;
                            catchLogSpeciesCaughtBean.methodIds.put("Bait", "31291");
                        }
                    } else {
                        if (catchLogSpeciesCaughtBean.method.trim().equals("31291")) {
                            catchLogSpeciesCaughtBean.isBaitSelcted = true;
                            catchLogSpeciesCaughtBean.methodIds.put("Bait", "31291");
                        }
                        if (catchLogSpeciesCaughtBean.method.trim().equals("31960")) {
                            catchLogSpeciesCaughtBean.isLureSelcted = true;
                            catchLogSpeciesCaughtBean.methodIds.put("Lure", "31960");
                        }
                    }

                    measurementsArray = speciesObjec.getJSONArray("measurements");
                    for (int j = 0; j < measurementsArray.length(); j++) {
                        measurementObj = measurementsArray.getJSONObject(j);
                        CatchLogSpeciesCaughtMeasureBean measureBean = new CatchLogSpeciesCaughtMeasureBean();
                        measureBean.weight = measurementObj.getDouble("weight");
                        measureBean.length = measurementObj.getDouble("length");
                        measureBean.isKept = measurementObj.getBoolean("kept");
                        catchLogSpeciesCaughtBean.measurements.add(measureBean);
                    }
                    catchBean.speciesCaughtList.add(catchLogSpeciesCaughtBean);
                }
                JSONArray photoArray = obj.getJSONArray("imageCaptureList");
                JSONObject photoObject;
                for (int i = 0; i < photoArray.length(); i++) {
                    photoObject = photoArray.getJSONObject(i);
                    CatchLogImageBean catchLogImageBean = new CatchLogImageBean();
                    catchLogImageBean.url = photoObject.getString("imageloc");
                    catchLogImageBean.photoTitle = photoObject.getString("title");
                    catchLogImageBean.photoComment = photoObject.getString("comment");
                    catchBean.catchLogImageList.add(catchLogImageBean);
                }
                catchBean.privacy = obj.getInt("privacy");
                myDbList.add(catchBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myDbList;
    }

    public static String convertJSONString(String data) {
        data = data.replace("\\", "");
        return data;
    }

    private void generateAllTabData() {
        generateLocationList(allCatchLogList);
        generateSpeciesList(allCatchLogList);
        CatchlogActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                llCatches.performClick();
            }
        });
    }

    // Tab 1 Generate List
    private void generateLocationList(ArrayList<CatchLogBean> catchLogList) {
        locationList = new ArrayList<>();
        int size = catchLogList.size();
        CatchLogBean catchLogBean;
        for (int i = 0; i < size; i++) {
            catchLogBean = catchLogList.get(i);
            catchLogBean.location.imageUrl = catchLogBean.imagePath;
            locationList.add(catchLogBean.location);
        }
        CatchlogActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                catchlogAllLocationList = locationList;
                txtTotalCatches.setText(String.valueOf(locationList.size()));
            }
        });
    }

    // Tab 2 Generate List
    private void generateSpeciesList(ArrayList<CatchLogBean> catchLogList) {
        allWaterList = new ArrayList<>();
        int size = catchLogList.size();
        CatchLogBean catchLogBean;

        ArrayList<CatchLogSpeciesCaughtBean> allSpeciesCaughtList = new ArrayList<>();
        ArrayList<CatchLogSpeciesCaughtBean> saltWaterList = new ArrayList<>();
        ArrayList<CatchLogSpeciesCaughtBean> freshWaterList = new ArrayList<>();

        // CHECK THE LIST
        for (int i = 0; i < size; i++) {
            catchLogBean = catchLogList.get(i);
            for (int j = 0; j < catchLogBean.speciesCaughtList.size(); j++) {
                allSpeciesCaughtList.add(catchLogBean.speciesCaughtList.get(j));
            }
        }

        ArrayList<String> dummyList = new ArrayList<>();
        // Generate SaltWater Species List
        CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean1;
        for (int i = 0; i < allSpeciesCaughtList.size(); i++) {
            catchLogSpeciesCaughtBean1 = allSpeciesCaughtList.get(i);
            for (int j = 0; j < CatchlogActivity.allCatchWaterList.get(0).size(); j++) {
                CatchLogSpeciesCaughtBean csb = CatchlogActivity.allCatchWaterList.get(0).get(j);
                if (dummyList.contains(catchLogSpeciesCaughtBean1.species)) {
                    // Do nothing
                } else {
                    if (String.valueOf(csb.id).equals(catchLogSpeciesCaughtBean1.species)) {
                        saltWaterList.add(csb);
                        dummyList.add(catchLogSpeciesCaughtBean1.species);
                    }
                }
            }
        }

        // Generate FreshWater Species List
        for (int i = 0; i < allSpeciesCaughtList.size(); i++) {
            catchLogSpeciesCaughtBean1 = allSpeciesCaughtList.get(i);
            for (int j = 0; j < CatchlogActivity.allCatchWaterList.get(1).size(); j++) {
                CatchLogSpeciesCaughtBean csb = CatchlogActivity.allCatchWaterList.get(1).get(j);
                if (dummyList.contains(catchLogSpeciesCaughtBean1.species)) {
                    // Do nothing
                } else {
                    if (String.valueOf(csb.id).equals(catchLogSpeciesCaughtBean1.species)) {
                        freshWaterList.add(csb);
                        dummyList.add(catchLogSpeciesCaughtBean1.species);
                    }
                }
            }
        }

        allWaterList.add(saltWaterList);
        allWaterList.add(freshWaterList);

        final int totalSpecies = saltWaterList.size() + freshWaterList.size();
        CatchlogActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtTotalSpecies.setText("" + totalSpecies);
            }
        });
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imgUser:
                intent = new Intent(CatchlogActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.llCatches:
                if (isUserloggedIn) {
                    actionOnTabSelection(1);
                } else {
                    AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Alert", "Please create and account to maintain your catch log.",
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {

                                }
                            });
                }
                break;
            case R.id.llSpecies:
                if (isUserloggedIn) {
                    actionOnTabSelection(2);
                    if (allCatchLogList != null && allCatchLogList.size() > 0) {
                        generateSpeciesList(allCatchLogList);
                    }
                } else {
                    AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Alert", "Please create and account to maintain your catch log.",
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {

                                }
                            });
                }
                break;
            case R.id.llWhere:
                if (isUserloggedIn) {
                    actionOnTabSelection(3);
                } else {
                    AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Alert", "Please create and account to maintain your catch log.",
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {

                                }
                            });
                }
                break;
            case R.id.imgCatlogAdd:
                if (isUserloggedIn) {
                    intent = new Intent(CatchlogActivity.this, AddCatchlogActivity.class);
                    intent.putExtra("isRecordExist", 0);
                    startActivity(intent);
                } else {
                    AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Alert", "Please create and account to maintain your catch log.",
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {

                                }
                            });
                }
                break;
            case R.id.txtRegister:
                intent = new Intent(CatchlogActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.txtLogin:
                intent = new Intent(CatchlogActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.llRow:
                clickRecordPos = (int) view.getTag();
                if (allCatchLogList.get(clickRecordPos).privacy == 0) {
                    intent = new Intent(CatchlogActivity.this, AddCatchlogActivity.class);
                    intent.putExtra("CatchLogData", allCatchLogList.get(clickRecordPos));
                    intent.putExtra("isRecordExist", 1);
                    startActivity(intent);
                } else {
                    getCatchImageList(String.valueOf(allCatchLogList.get(clickRecordPos).id));
                }
                break;
        }
    }

    private void actionOnTabSelection(int whichTabSelected) {
        if (whichTabSelected == 1) {
            llCatches.setBackgroundColor(getResources().getColor(R.color.catlog_tab_selected_dark_color));
            txtTotalCatches.setTextColor(getResources().getColor(R.color.white));
            txtTitleCatches.setTextColor(getResources().getColor(R.color.white));

            llSpecies.setBackgroundColor(getResources().getColor(R.color.white));
            txtTotalSpecies.setTextColor(getResources().getColor(R.color.blacktext));
            txtTitleSpecies.setTextColor(getResources().getColor(R.color.blacktext));

            llWhere.setBackgroundColor(getResources().getColor(R.color.white));
            txtTitleWhere.setTextColor(getResources().getColor(R.color.blacktext));
            imgWhere.setImageResource(R.drawable.maps_dark_unselected);

            if (isUserloggedIn) {
                if (allCatchLogList != null && allCatchLogList.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("catchesList", locationList);
                    bundle.putSerializable("CatchLogData", allCatchLogList);
                    CatchLogCatchesFragment catchLogCatchesFragment = new CatchLogCatchesFragment();
                    catchLogCatchesFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, catchLogCatchesFragment).commitAllowingStateLoss();
                    relNoUserLogin.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                }
            } else {
                txtTotalCatches.setText("0");
                txtTotalSpecies.setText("0");
                relProgressBar.setVisibility(View.GONE);
                relNoUserLogin.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.INVISIBLE);
                llLogin.setVisibility(View.VISIBLE);
            }
        } else if (whichTabSelected == 2) {
            llCatches.setBackgroundColor(getResources().getColor(R.color.white));
            txtTotalCatches.setTextColor(getResources().getColor(R.color.blacktext));
            txtTitleCatches.setTextColor(getResources().getColor(R.color.blacktext));

            llSpecies.setBackgroundColor(getResources().getColor(R.color.catlog_tab_selected_dark_color));
            txtTotalSpecies.setTextColor(getResources().getColor(R.color.white));
            txtTitleSpecies.setTextColor(getResources().getColor(R.color.white));

            llWhere.setBackgroundColor(getResources().getColor(R.color.white));
            txtTitleWhere.setTextColor(getResources().getColor(R.color.blacktext));
            imgWhere.setImageResource(R.drawable.maps_dark_unselected);

            if (isUserloggedIn) {
                if (allCatchLogList != null && allCatchLogList.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("allWaterList", allWaterList);
                    CatchLogSpeciesFragment catchLogSpeciesFragment = new CatchLogSpeciesFragment();
                    catchLogSpeciesFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, catchLogSpeciesFragment).commit();
                    relNoUserLogin.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                }
            } else {
                txtTotalCatches.setText("0");
                txtTotalSpecies.setText("0");
                relProgressBar.setVisibility(View.GONE);
                relNoUserLogin.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.INVISIBLE);
                llLogin.setVisibility(View.VISIBLE);
            }
        } else if (whichTabSelected == 3) {
            llCatches.setBackgroundColor(getResources().getColor(R.color.white));
            txtTotalCatches.setTextColor(getResources().getColor(R.color.blacktext));
            txtTitleCatches.setTextColor(getResources().getColor(R.color.blacktext));

            llSpecies.setBackgroundColor(getResources().getColor(R.color.white));
            txtTotalSpecies.setTextColor(getResources().getColor(R.color.blacktext));
            txtTitleSpecies.setTextColor(getResources().getColor(R.color.blacktext));

            llWhere.setBackgroundColor(getResources().getColor(R.color.catlog_tab_selected_dark_color));
            txtTitleWhere.setTextColor(getResources().getColor(R.color.white));
            imgWhere.setImageResource(R.drawable.maps_white_selected);

            if (isUserloggedIn) {
                if (allCatchLogList != null && allCatchLogList.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("catchesList", locationList);
                    catchLogWhereFragment = new CatchLogWhereFragment(allCatchLogList);
                    catchLogWhereFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, catchLogWhereFragment).commit();
                    relNoUserLogin.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                }
            } else {
                txtTotalCatches.setText("0");
                txtTotalSpecies.setText("0");
                relProgressBar.setVisibility(View.GONE);
                relNoUserLogin.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.INVISIBLE);
                llLogin.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        if (ModelManager.getInstance() != null && ModelManager.getInstance().getLoginDetails() != null) {
            if (ModelManager.getInstance().getLoginResponse().getUserId() != null &&
                    !ModelManager.getInstance().getLoginResponse().getUserId().equals("")) {
                isUserloggedIn = true;
                if (isNetworkConnected()) {
                    relProgressBar.setVisibility(View.VISIBLE);
                    NetworkManager.getInstance().getCatchSpecies(responseListner);
                } else {
                    relProgressBar.setVisibility(View.GONE);
                    AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {

                                }
                            });
                }
                imgUser.setVisibility(View.VISIBLE);
                relNoUserLogin.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
            }
        } else {
            isUserloggedIn = false;
            relProgressBar.setVisibility(View.GONE);
            relNoUserLogin.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.INVISIBLE);
            llLogin.setVisibility(View.VISIBLE);
            imgUser.setVisibility(View.GONE);
            txtTotalCatches.setText("0");
            txtTotalSpecies.setText("0");
            actionOnTabSelection(1);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for(Fragment f : getSupportFragmentManager().getFragments()) {
            if(f != null && catchLogWhereFragment != null && f == catchLogWhereFragment) {
                getSupportFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
            }
        }
        EventBus.getDefault().unregister(this);
        Utils.freeMemory();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(BasicEvent event) {

    }

    private void getUserProfile() {
        if (Utils.isOnline(CatchlogActivity.this)) {
            NetworkManager.getInstance().getUserProfile(ModelManager.getInstance().getLoginResponse().getAuthorization(), "application/json",
                    ModelManager.getInstance().getLoginResponse().getUserId(), responseListner);
        } else {
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                        }
                    });
        }
    }

    private void getCatchLogListAPI() {
        if (isNetworkConnected()) {
            NetworkManager.getInstance().getCatchLogList(ModelManager.getInstance().getLoginResponse().getAuthorization(),
                    ModelManager.getInstance().getLoginResponse().getUserId(), responseListner);
        } else {
            relProgressBar.setVisibility(View.GONE);
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
    }

    private void getCatchImageList(String catchId) {
        if (isNetworkConnected()) {
            relProgressBar.setVisibility(View.VISIBLE);
            NetworkManager.getInstance().getCatchLogImageList(ModelManager.getInstance().getLoginResponse().getAuthorization(),
                    catchId, responseListner);
        } else {
            relProgressBar.setVisibility(View.GONE);
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
    }

    final Comparator<CatchLogBean> byDate = new Comparator<CatchLogBean>() {
        @Override
        public int compare(CatchLogBean catchLogBean, CatchLogBean t1) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = sdf.parse(millisToDate(Long.parseLong(catchLogBean.createdDate), "dd/MM/yyyy hh:mm a"));
                d2 = sdf.parse(millisToDate(Long.parseLong(t1.createdDate), "dd/MM/yyyy hh:mm a"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return (d1.getTime() > d2.getTime() ? -1 : 1);     //descending
            //return (d1.getTime() > d2.getTime() ? 1 : -1);
        }
    };

    private String millisToDate(long millis, String format) {
        return new SimpleDateFormat(format).format(new Date(millis));
    }

    public class ObjectComparator implements Comparator<CatchLogSpeciesCaughtBean> {
        @Override
        public int compare(CatchLogSpeciesCaughtBean o1, CatchLogSpeciesCaughtBean o2) {
            return o1.species.compareTo(o2.species);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}