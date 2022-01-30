package com.mobiddiction.fishsmart;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiddiction.fishsmart.CatchLog.CatchlogActivity;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.Gallery.GalleryActivity;
import com.mobiddiction.fishsmart.Home.HomeActivity;
import com.mobiddiction.fishsmart.Maps.MapboxActivity;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.NewSpecies.NewSpeciesActivity;
import com.mobiddiction.fishsmart.Rules.RulesActivity;
import com.mobiddiction.fishsmart.util.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Archa on 16/07/2015.
 */

public class MainFrameActivity extends ActivityGroup {

    private TabHost mTabHost;
    private ToggleListener listener = null;

    private RelativeLayout custom_menu;
    private LinearLayout moving_panel;
    public TextView nav_title, hometext, mapstext, speciestext, rulestext, moretext, gallerytext;
    public TextView newstext, contacttext, licensingtext;

    //    GoogleApiClient googleApiClient = null;
    private ImageButton home;
    private ImageButton maps, species, rules, more, gallery, contact, news, license;
    public static int lastSelectedTab;
    CircleImageView imgUser;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //((MyApplication) getApplication()).getTrackingManager().trackScreen(ScreenEnum.HOME, null);
        FirebaseAnalytics.getInstance(getApplicationContext()).setCurrentScreen(this,ScreenEnum.HOME, null);

        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this.getLocalActivityManager());

        listener = new ToggleListener();
        custom_menu = findViewById(R.id.custom_menu);

        // start change font
        newstext = findViewById(R.id.newstext);
        contacttext = findViewById(R.id.contacttext);
        licensingtext = findViewById(R.id.licensingtext);
        gallerytext = findViewById(R.id.gallerytext);
        nav_title = findViewById(R.id.nav_title);
        hometext = findViewById(R.id.hometext);
        mapstext = findViewById(R.id.mapstext);
        speciestext = findViewById(R.id.speciestext);
        rulestext = findViewById(R.id.rulestext);
        moretext = findViewById(R.id.moretext);

        // end change font

        custom_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });

        home = findViewById(R.id.home);
        maps = findViewById(R.id.maps);
        species = findViewById(R.id.species);
        rules = findViewById(R.id.rules);
        more = findViewById(R.id.more);
        gallery = findViewById(R.id.gallery);
        contact = findViewById(R.id.contact);
        news = findViewById(R.id.news);
        license = findViewById(R.id.licensing);
        imgUser = findViewById(R.id.imgUser);

        home.setSelected(true);

        moving_panel = findViewById(R.id.moving_panel);
        setOnClickListener(custom_menu);
        initializeTabs();

        SharedPreferences pref = getSharedPreferences("Fishsmart", 0);
        SharedPreferences.Editor edit = pref.edit();

        if (listener != null) {
            registerReceiver(listener, new IntentFilter("com.fishsmart.toggle"));
            registerReceiver(listener, new IntentFilter("com.fishsmart.gallery"));
        }

        if (!isNetworkConnected()) {

            /**
             * if not connected, user go to species
             */
            if (ModelManager.getInstance() != null && ModelManager.getInstance().getLoginDetails() != null) {
                if (ModelManager.getInstance().getLoginResponse().getUserId() != null &&
                        !ModelManager.getInstance().getLoginResponse().getUserId().equals("")) {
                    imgUser.setVisibility(View.VISIBLE);
//                    this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ModelManager.getInstance().getLoginDetails();
//                            if (userBean.image.size() > 0) {
//                                Glide.with(MainFrameActivity.this)
//                                        .load(userBean.image.get(0).url)
//                                        .apply(new RequestOptions().dontAnimate())
//                                        .into(imgUser);
//                            }
//                        }
//                    });
                }
            } else {
                imgUser.setVisibility(View.GONE);
            }
        } else {
            imgUser.setVisibility(View.GONE);
        }

//        mTabHost.setCurrentTab(2);
//        nav_title.setText(Html.fromHtml("SPECIES"));
//        home.setSelected(false);
//        maps.setSelected(false);
//        species.setSelected(true);
//        rules.setSelected(false);
//        more.setSelected(false);
//        gallery.setSelected(false);
//        toggleMenu();
//        custom_menu.setVisibility(View.GONE);
    }


    public boolean isNetworkConnected() {
        return ConnectionHelper.isConnectedOrConnecting(this);
    }

    public void setOnClickListener(View container) {
        container.findViewById(R.id.home).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.maps).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.species).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.rules).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.more).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.gallery).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.licensing).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.contact).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.news).setOnClickListener(menuButtonListener);
    }

    View.OnClickListener menuButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.home:
                    lastSelectedTab = 0;
                    mTabHost.setCurrentTab(0);

                    nav_title.setText(Html.fromHtml("FISHSMART NSW"));

                    home.setSelected(true);
                    maps.setSelected(false);
                    species.setSelected(false);
                    rules.setSelected(false);
                    more.setSelected(false);
                    gallery.setSelected(false);
                    license.setSelected(false);
                    contact.setSelected(false);
                    news.setSelected(false);

                    toggleMenu();
                    break;

                case R.id.maps:
                    lastSelectedTab = 1;
                    mTabHost.setCurrentTab(1);

                    nav_title.setText("MAPS");
                    home.setSelected(false);
                    maps.setSelected(true);
                    species.setSelected(false);
                    rules.setSelected(false);
                    more.setSelected(false);
                    gallery.setSelected(false);
                    license.setSelected(false);
                    contact.setSelected(false);
                    news.setSelected(false);

                    toggleMenu();
                    break;

                case R.id.species:
                    lastSelectedTab = 2;
                    mTabHost.setCurrentTab(2);

                    nav_title.setText(Html.fromHtml("SPECIES"));
                    home.setSelected(false);
                    maps.setSelected(false);
                    species.setSelected(true);
                    rules.setSelected(false);
                    more.setSelected(false);
                    gallery.setSelected(false);
                    license.setSelected(false);
                    contact.setSelected(false);
                    news.setSelected(false);

                    toggleMenu();
                    break;

                case R.id.rules:
                    lastSelectedTab = 3;
                    mTabHost.setCurrentTab(3);

                    nav_title.setText("RULES AND INFORMATION");
                    home.setSelected(false);
                    maps.setSelected(false);
                    species.setSelected(false);
                    rules.setSelected(true);
                    more.setSelected(false);
                    gallery.setSelected(false);
                    license.setSelected(false);
                    contact.setSelected(false);
                    news.setSelected(false);

                    toggleMenu();
                    break;

                case R.id.more:
                    lastSelectedTab = 4;
                    mTabHost.setCurrentTab(4);

                    nav_title.setText("CATCH LOG");

                    home.setSelected(false);
                    maps.setSelected(false);
                    species.setSelected(false);
                    rules.setSelected(false);
                    more.setSelected(true);
                    gallery.setSelected(false);
                    license.setSelected(false);
                    contact.setSelected(false);
                    news.setSelected(false);

                    toggleMenu();
                    break;

                case R.id.gallery:
                    lastSelectedTab = 5;
                    mTabHost.setCurrentTab(5);

                    nav_title.setText("GALLERY");

                    home.setSelected(false);
                    maps.setSelected(false);
                    species.setSelected(false);
                    rules.setSelected(false);
                    more.setSelected(false);
                    gallery.setSelected(true);
                    license.setSelected(false);
                    contact.setSelected(false);
                    news.setSelected(false);

                    toggleMenu();
                    break;
                case R.id.news:
                    lastSelectedTab = 6;
                    mTabHost.setCurrentTab(6);
                    nav_title.setText("NEWS");
                    home.setSelected(false);
                    maps.setSelected(false);
                    species.setSelected(false);
                    rules.setSelected(false);
                    more.setSelected(false);
                    gallery.setSelected(false);
                    license.setSelected(false);
                    contact.setSelected(false);
                    news.setSelected(true);

                    toggleMenu();
                    break;
                case R.id.contact:
                    lastSelectedTab = 7;
                    mTabHost.setCurrentTab(7);

                    nav_title.setText("Contacts");

                    home.setSelected(false);
                    maps.setSelected(false);
                    species.setSelected(false);
                    rules.setSelected(false);
                    more.setSelected(false);
                    gallery.setSelected(false);
                    license.setSelected(false);
                    contact.setSelected(true);
                    news.setSelected(false);

                    toggleMenu();
                    break;
                case R.id.licensing:
                    lastSelectedTab = 8;
                    mTabHost.setCurrentTab(8);
                    nav_title.setText(getResources().getString(R.string.label_licensing));
                    home.setSelected(false);
                    maps.setSelected(false);
                    species.setSelected(false);
                    rules.setSelected(false);
                    more.setSelected(false);
                    gallery.setSelected(false);
                    license.setSelected(true);
                    contact.setSelected(false);
                    news.setSelected(false);

                    toggleMenu();
                    break;

            }
        }
    };

    public void InvokeGallery() {
        mTabHost.setCurrentTab(5);
        nav_title.setText("GALLERY");
        home.setSelected(false);
        maps.setSelected(false);
        species.setSelected(false);
        rules.setSelected(false);
        more.setSelected(false);
        license.setSelected(false);
        contact.setSelected(false);
        news.setSelected(false);
        gallery.setSelected(true);
    }

    public void closeMenu(View vw) {
        toggleMenu();
    }

    public static boolean inAnimate;

    public void toggleMenu() {

        if (inAnimate)
            return;

        inAnimate = true;

        if (custom_menu.getVisibility() == View.GONE) {
            moving_panel.setTranslationY(Config.dpToPx(-250, MainFrameActivity.this));
            custom_menu.setVisibility(View.VISIBLE);

            moving_panel.animate().translationY(Config.dpToPx(50, MainFrameActivity.this)).setDuration(500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                    //  Toast.makeText(getBaseContext(), "toggleQuestion", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    inAnimate = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });

        } else {
            moving_panel.animate().translationY(Config.dpToPx(-200, MainFrameActivity.this)).setDuration(500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    custom_menu.setVisibility(View.GONE);
                    inAnimate = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });

        }
    }

    public void initializeTabs() {
        // Setup your tab icons and content views.. Nothing special in this..
        // Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        // Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
        Intent intent = new Intent().setClass(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        TabHost.TabSpec spec = mTabHost.newTabSpec(Config.HOME);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.HOME));
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, MapboxActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        spec = mTabHost.newTabSpec(Config.MAPS);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.MAPS));
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, NewSpeciesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        spec = mTabHost.newTabSpec(Config.SPEICES);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.SPEICES));
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, RulesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        spec = mTabHost.newTabSpec(Config.RULES);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.RULES));
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, CatchlogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        spec = mTabHost.newTabSpec(Config.MORE);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.MORE));
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, GalleryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        spec = mTabHost.newTabSpec(Config.GALLERY);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.GALLERY));
        mTabHost.addTab(spec);

        /*
        news, contact, licence
         */
        intent = new Intent().setClass(this, NewsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        spec = mTabHost.newTabSpec(Config.NEWS);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.NEWS));
        mTabHost.addTab(spec);


        intent = new Intent().setClass(this, ContactActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        spec = mTabHost.newTabSpec(Config.NEWS);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.NEWS));
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, LicenseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        spec = mTabHost.newTabSpec(Config.NEWS);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.NEWS));
        mTabHost.addTab(spec);
    }

    private View createTabView(final int id, String title) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);

        ImageView imageView = view.findViewById(R.id.tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(id));

        TextView titleView = view.findViewById(R.id.tab_title);
        titleView.setText(title);

        return view;
    }

    protected class ToggleListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.fishsmart.toggle")) {

                toggleMenu();
            } else if (intent.getAction().equals("com.fishsmart.gallery")) {

                InvokeGallery();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (listener != null) {
            registerReceiver(listener, new IntentFilter("com.fishsmart.toggle"));
            registerReceiver(listener, new IntentFilter("com.fishsmart.gallery"));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Utils.freeMemory();
    }
}