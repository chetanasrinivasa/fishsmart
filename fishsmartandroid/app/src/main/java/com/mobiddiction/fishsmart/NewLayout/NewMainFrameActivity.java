package com.mobiddiction.fishsmart.NewLayout;

/**
 * Created by AI on 21/06/2017.
 */

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.Gallery.GalleryActivity;
import com.mobiddiction.fishsmart.Maps.MapboxActivity;
import com.mobiddiction.fishsmart.More.MoreActivity;
import com.mobiddiction.fishsmart.NewSpecies.NewSpeciesActivity;
import com.mobiddiction.fishsmart.NewsActivity;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.Rules.RulesActivity;
import com.mobiddiction.fishsmart.ScreenEnum;
/**
 * Created by Archa on 16/07/2015.
 */

public class NewMainFrameActivity extends AppCompatActivity {


    TabLayout tab;
    ViewPager pager;
    private TabHost mTabHost;
    private NewMainFrameActivity.ToggleListener listener = null;
    private Boolean listenerIsRegistered = false;

    private RelativeLayout custom_menu;
    private LinearLayout moving_panel;
    public TextView nav_title, hometext, mapstext, speciestext, rulestext, moretext;
    public TextView newstext, contacttext, licensingtext;
    GoogleApiClient googleApiClient = null;
    ImageButton home, maps, species, rules, more, gallery,contact,news,license;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //((MyApplication) getApplication()).getTrackingManager().trackScreen(ScreenEnum.HOME, null);
        FirebaseAnalytics.getInstance(getApplicationContext()).setCurrentScreen(this,ScreenEnum.HOME, null);

        mTabHost = findViewById(android.R.id.tabhost);
//        mTabHost.setup(this.getLocalActivityManager());

        listener = new NewMainFrameActivity.ToggleListener();
        custom_menu = findViewById(R.id.custom_menu);

        // start change font

        custom_menu = findViewById(R.id.custom_menu);


        // start change font
        newstext = findViewById(R.id.newstext);
        Typeface face11 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        newstext.setTypeface(face11);

        contacttext = findViewById(R.id.contacttext);
        Typeface face12 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        contacttext.setTypeface(face11);


        licensingtext = findViewById(R.id.licensingtext);
        Typeface face13 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        licensingtext.setTypeface(face11);

        nav_title = findViewById(R.id.nav_title);
        Typeface face1 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        nav_title.setTypeface(face1);

        hometext = findViewById(R.id.hometext);
        Typeface face2 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        hometext.setTypeface(face2);

        mapstext = findViewById(R.id.mapstext);
        Typeface face3 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        mapstext.setTypeface(face3);

        speciestext = findViewById(R.id.speciestext);
        Typeface face4 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        speciestext.setTypeface(face4);

        rulestext = findViewById(R.id.rulestext);
        Typeface face5 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        rulestext.setTypeface(face5);

        moretext = findViewById(R.id.moretext);
        Typeface face6 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        moretext.setTypeface(face6);

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

        home.setSelected(true);

        moving_panel = findViewById(R.id.moving_panel);
        setOnClickListener(custom_menu);
//        initializeTabs();

        // mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        SharedPreferences pref = getSharedPreferences("Fishsmart", 0);
        SharedPreferences.Editor edit = pref.edit();

        registerReceiver(listener, new IntentFilter("com.fishsmart.toggle"));
        registerReceiver(listener, new IntentFilter("com.fishsmart.gallery"));


        tab = findViewById(R.id.tab);
        pager = findViewById(R.id.pager);

        tab.addTab(tab.newTab().setText("Home").setIcon(R.drawable.home_selector));
        tab.addTab(tab.newTab().setText("Maps").setIcon(R.drawable.maps_selector));
        tab.addTab(tab.newTab().setText("Species").setIcon(R.drawable.species_selector));
        tab.addTab(tab.newTab().setText("Rules").setIcon(R.drawable.rules_selector));
        tab.addTab(tab.newTab().setText("Gallery").setIcon(R.drawable.gallery_selector));
        tab.addTab(tab.newTab().setText("More").setIcon(R.drawable.more_selector));
        tab.addTab(tab.newTab().setText("news").setIcon(R.drawable.more_selector));
        tab.addTab(tab.newTab().setText("contact").setIcon(R.drawable.more_selector));
        tab.addTab(tab.newTab().setText("license").setIcon(R.drawable.more_selector));
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(mSectionsPagerAdapter);
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));


    }

    public void setOnClickListener(View container) {
        container.findViewById(R.id.home).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.maps).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.species).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.rules).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.more).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.gallery).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.news).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.contact).setOnClickListener(menuButtonListener);
        container.findViewById(R.id.licensing).setOnClickListener(menuButtonListener);
    }

    View.OnClickListener menuButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.home:
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
                    mTabHost.setCurrentTab(4);

                    nav_title.setText("NEWS AND CONTACTS");

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
                    mTabHost.setCurrentTab(6);
                    nav_title.setText("News");
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
                    mTabHost.setCurrentTab(7);

                    nav_title.setText("Contact");

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
                    mTabHost.setCurrentTab(8);
                    nav_title.setText("Licensing");
                    home.setSelected(false);
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

            }
        }
    };

    public void InvokeGallery()
    {
        mTabHost.setCurrentTab(5);

        nav_title.setText("GALLERY");

        home.setSelected(false);
        maps.setSelected(false);
        species.setSelected(false);
        rules.setSelected(false);
        more.setSelected(false);
        gallery.setSelected(true);
        contact.setSelected(false);
        news.setSelected(false);
        gallery.setSelected(true);
    }

    public void closeMenu(View vw)
    {
        toggleMenu();
    }

    boolean inAnimate;

    public void toggleMenu ()
    {

        if (inAnimate)
            return;


        inAnimate = true;

        if (custom_menu.getVisibility() == View.GONE)
        {
            moving_panel.setTranslationY(Config.dpToPx(-250, NewMainFrameActivity.this));
            custom_menu.setVisibility(View.VISIBLE);

            moving_panel.animate().translationY(Config.dpToPx(50, NewMainFrameActivity.this)).setDuration(500).setListener(new Animator.AnimatorListener() {
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

        }else
        {
            moving_panel.animate().translationY(Config.dpToPx(-200, NewMainFrameActivity.this)).setDuration(500).setListener(new Animator.AnimatorListener() {
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

        Intent intent = new Intent().setClass(this, MoreActivity.class);
        TabHost.TabSpec spec = mTabHost.newTabSpec(Config.HOME);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.HOME));
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, MapboxActivity.class);
        spec = mTabHost.newTabSpec(Config.MAPS);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.MAPS));
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, NewSpeciesActivity.class);
        spec = mTabHost.newTabSpec(Config.SPEICES);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.SPEICES));
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, RulesActivity.class);
        spec = mTabHost.newTabSpec(Config.RULES);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.RULES));
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, MoreActivity.class);
        spec = mTabHost.newTabSpec(Config.MORE);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.MORE));
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, GalleryActivity.class);
        spec = mTabHost.newTabSpec(Config.GALLERY);
        spec.setContent(intent);
        spec.setIndicator(createTabView(R.mipmap.ic_launcher, Config.GALLERY));
        mTabHost.addTab(spec);


        /*
        news, contact, licence
         */
        intent = new Intent().setClass(this, NewsActivity.class);
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
            }
            else if (intent.getAction().equals("com.fishsmart.gallery")) {

                InvokeGallery();
            }
        }
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    return new NewHomeActivity();
                case 1:
                    return new NewHomeActivity();
                case 2:
                    return new NewHomeActivity();
                case 3:
                    return new NewHomeActivity();
                case 4:
                    return new NewHomeActivity();
                case 5:
                    return new NewHomeActivity();
                case 6:
                    return new NewHomeActivity();
                case 7:
                    return new NewHomeActivity();
                case 8:
                    return new NewHomeActivity();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 9;
        }
    }
}