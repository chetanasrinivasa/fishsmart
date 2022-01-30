package com.mobiddiction.fishsmart.NewSpecies;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.Species.SearchAdapter;
import com.mobiddiction.fishsmart.Species.SearchAdapterOffline;
import com.mobiddiction.fishsmart.Species.SearchModel;
import com.mobiddiction.fishsmart.Species.SpeciesDownloader;
import com.mobiddiction.fishsmart.Species.SpeciesModel;
import com.mobiddiction.fishsmart.dao.AllSpecies;
import com.mobiddiction.fishsmart.notification.NotificationActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AI on 16/06/2017.
 */

public class NewSpeciesActivity extends BaseTabActivity implements androidx.appcompat.app.ActionBar.TabListener{


    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    ArrayList<SearchModel> SearchList = new ArrayList<>();
    ListView listviewsearch;
    SearchAdapter adapter;
    List<AllSpecies> allSpeciesListView;
    private ImageView imgNotification;

    TabLayout tab;
    public static Handler handler = new Handler();
    public static SharedPreferences pref;

    static ArrayList<SpeciesModel> allSpecies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState/*, com.mobiddiction.fishsmart.R.layout.activity_species_new, false*/);
        setContentView(com.mobiddiction.fishsmart.R.layout.activity_species_new);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_header));
        pref = getSharedPreferences("fishsmart", 0);
        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        imgNotification = mActionBarView.findViewById(R.id.imgNotification);
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewSpeciesActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        listviewsearch = findViewById(R.id.listviewsearch);
        TextView nav_title = mActionBarView.findViewById(R.id.nav_title);
        nav_title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        nav_title.setText(Html.fromHtml("SPECIES"));
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setBackgroundColor(Color.WHITE);


        if (isNetworkConnected()) {
            adapter = new SearchAdapter(this, R.layout.searchsuggestionitem, SearchList);
        }else{
            adapter = new SearchAdapter(this, R.layout.searchsuggestionitem, SearchList);
        }



        listviewsearch.setAdapter(adapter);



        tab = findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("Saltwater"));
        tab.addTab(tab.newTab().setText("Freshwater"));

        Typeface typeface = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");

        ViewGroup vg = (ViewGroup) tab.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(typeface, Typeface.NORMAL);
                }
            }
        }
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

    }

    public void receiveItems() {

        listviewsearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        mViewPager.setCurrentItem(tab.getPosition());
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0089a7")));
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public NewFreshWaterSpeciesFragment savedSpeciesFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    return new NewSaltWaterSpeciesFragment();
                case 1:
                    return new NewFreshWaterSpeciesFragment();
//                case 2:
//                    return new NewFreshWaterSpeciesFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
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

    @Subscribe
    public void onEventMainThread(BasicEvent event) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("Search for fish...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                SearchList.clear();

                // TODO: 11/09/2017 search offline
//                if (isNetworkConnected()) {
//
//
//                    Log.d("TESTT", "search log : " + newText);
//
//                    if (newText.length() > 2) {
//                        Log.d("TESTT", "search log : " + newText.length());
//                        new SearchAsync(NewSpeciesActivity.this, SearchList).execute("/api/search?keyword=" + newText);
//                        listviewsearch.setVisibility(View.VISIBLE);
//                        mViewPager.setVisibility(View.GONE);
//                    } else {
//                        listviewsearch.setVisibility(View.GONE);
//                        mViewPager.setVisibility(View.VISIBLE);
//                    }
//
//                } else {
                if (newText.length() > 2) {
                    Log.d("TESTT", "search log : " + newText.length());
//                        new SearchAsync(NewSpeciesActivity.this, SearchList).execute("/api/search?keyword=" + newText);

                    allSpeciesListView = ModelManager.getInstance().getSpeciesByString(newText);
                    Log.d("TESTT", "allSpeciesListView : " + allSpeciesListView.size());
                    for(AllSpecies allSpecies : allSpeciesListView ){
                        Log.d("TESTT", "allSpeciesListView title : " + allSpecies.getTitle());
                    }
                    listviewsearch.setVisibility(View.VISIBLE);
                    listviewsearch.setAdapter(new SearchAdapterOffline(NewSpeciesActivity.this,allSpeciesListView));
                    mViewPager.setVisibility(View.GONE);
                } else {
                    listviewsearch.setVisibility(View.GONE);
                    mViewPager.setVisibility(View.VISIBLE);
                }
//                }
                return false;
            }

        });

        return true;
    }
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
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
