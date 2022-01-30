package com.mobiddiction.fishsmart.Species;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by Archa on 10/05/2016.
 */
public class SpeciesActivity extends BaseTabActivity implements androidx.appcompat.app.ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private SearchView searchView;

    TabLayout tab;
    private MenuItem searchMenuItem;
    ArrayList<SearchModel> SearchList = new ArrayList<>();
    ListView listviewsearch;
    SearchAdapter adapter;

    public static Handler handler = new Handler();
    public static SharedPreferences pref;
    SpeciesDownloader speciesDownloader = SpeciesDownloader.getInstance();
    public Handler mHandler = new Handler();

    static ArrayList<SpeciesModel> allSpecies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState/*, R.layout.activity_species_new, false*/);
        setContentView(R.layout.activity_species_new);
        pref = getSharedPreferences("fishsmart", 0);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_header));

        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        TextView nav_title = mActionBarView.findViewById(R.id.nav_title);
        nav_title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        nav_title.setText(Html.fromHtml("SPECIES"));
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setBackgroundColor(Color.WHITE);




        tab = findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("Saltwater"));
        tab.addTab(tab.newTab().setText("Freshwater"));


        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        final ActionBar.Tab savedSpecies = getSupportActionBar().newTab().setText("Saved Species").setTabListener(this);
        ActionBar.Tab alerttab = getSupportActionBar().newTab().setText("Saltwater").setTabListener(this);
        ActionBar.Tab enclosurestab = getSupportActionBar().newTab().setText("Freshwater").setTabListener(this);

        getSupportActionBar().addTab(savedSpecies);
        getSupportActionBar().addTab(alerttab);
        getSupportActionBar().addTab(enclosurestab);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                getSupportActionBar().setSelectedNavigationItem(position);

                //If Saved tab selected
                if (position == savedSpecies.getPosition()) {
                    mSectionsPagerAdapter.savedSpeciesFragment.loadSaved();
                }
            }

        });

        listviewsearch = findViewById(R.id.listviewsearch);

        adapter = new SearchAdapter(this, R.layout.searchsuggestionitem, SearchList);
        listviewsearch.setAdapter(adapter);
        mViewPager.setCurrentItem(1);
        speciesDownloader.downloadFreshWaterSpecies(getApplicationContext());
    }

    public static void addAllToVariable(ArrayList<SpeciesModel> allFish) {
        allSpecies.addAll(allFish);
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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search for fish...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //  Log.d("search query", query);

                // called after click on search icon

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                SearchList.clear();

//                new SearchAsync(SpeciesActivity.this, SearchList).execute("/api/search?keyword="+newText);
                if (newText.length() > 3)
                {
                    listviewsearch.setVisibility(View.VISIBLE);
                    mViewPager.setVisibility(View.GONE);
                }
                else
                {
                    listviewsearch.setVisibility(View.GONE);
                    mViewPager.setVisibility(View.VISIBLE);
                }

                return false;
            }

        });

        return true;
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SavedSpeciesFragment savedSpeciesFragment;
        public FreshwaterFragment freshWaterFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position)
            {
                case 0:
                    savedSpeciesFragment = new SavedSpeciesFragment();
                    return savedSpeciesFragment;
                case 1:
                    return new SaltWaterFragment();
                case 2:
                    freshWaterFragment = new FreshwaterFragment();
                    return freshWaterFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        mViewPager.setCurrentItem(tab.getPosition());
      //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0089a7")));

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public static class SaltWaterFragment extends Fragment{

        ArrayList<SpeciesModel> speciesListSalt = new ArrayList<SpeciesModel>();
        ArrayList<SpeciesModel> speciesListSaltTemp = new ArrayList<SpeciesModel>();
        RecyclerView listviewSalt;
        SpeciesSaltAdapter adapterSalt;
        TextView whattext;
        ProgressBar progress_bar;

        public Dialog dialog = null;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_saltwater, container, false);

        //    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

            listviewSalt = view.findViewById(R.id.listviewSalt);
            whattext = view.findViewById(R.id.what);
            whattext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            progress_bar = view.findViewById(R.id.progress_bar);

            listviewSalt.setLayoutManager(new LinearLayoutManager(getActivity()));
            listviewSalt.setHasFixedSize(true);

            adapterSalt = new SpeciesSaltAdapter(getActivity(), speciesListSalt);
            listviewSalt.setAdapter(adapterSalt);
            loadSaltWaterFishFromDB();
            new SaltWaterAsync(SaltWaterFragment.this, speciesListSaltTemp).execute("/api/speciesData?speciesType=SALTWATER");

            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {

                if(!pref.getBoolean("vergincallsaltwater", false))
                {
                    try {

                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable(){

                            public void run() {

                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View dialoglayout = inflater.inflate(R.layout.first_popup, null);
                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                TextView title = dialoglayout.findViewById(R.id.title);
                                TextView desc = dialoglayout.findViewById(R.id.desc);
                                Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);

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
            }
        }

        @Override
        public void onResume() {
            super.onResume();

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EventBus.getDefault().register(this);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            EventBus.getDefault().unregister(this);
        }

        @Subscribe
        public void onEventBus(SpeciesModel item) {
            Log.d("LOG", "EventBus for " + item.getTitle());
//
//            for (int i = 0; i < listviewSalt.getChildCount(); i++) {
//                View child = listviewSalt.getChildAt(i);
//                TextView fishname = (TextView) child.findViewById(R.id.fishname);
//                if (fishname.getText().toString().equals(item.getTitle())) {
//
//                    Button savespeciesbtn = (Button) child.findViewById(R.id.savespeciesbtn);
//                    savespeciesbtn.setSelected(false);
//                    return;
//                }
//            }
            adapterSalt.notifyDataSetChanged();
        }

        public void receiveItems() {
            for (SpeciesModel aModel: speciesListSaltTemp) {
                SpeciesDownloader.addFishIntoDB(aModel, getContext());
            }
            // Get the items from the db
            loadSaltWaterFishFromDB();
        }

        private void loadSaltWaterFishFromDB() {
            speciesListSalt.addAll(SpeciesDownloader.getAllSaltWaterSpecies());
            addAllToVariable(speciesListSalt);
            adapterSalt.notifyDataSetChanged();
        }

    }

    public static class FreshwaterFragment extends Fragment{

        ArrayList<SpeciesModel> speciesListFresh = new ArrayList<SpeciesModel>();
        ArrayList<SpeciesModel> speciesListFreshTemp = new ArrayList<SpeciesModel>(); // We get this back from the api
        RecyclerView listviewFresh;
        SpeciesFreshAdapter adapterFresh;
        TextView whattext;
        ProgressBar progress_bar;
        public Dialog dialog = null;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_freshwater, container, false);

            listviewFresh = view.findViewById(R.id.listviewFresh);
            whattext = view.findViewById(R.id.what);
            whattext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            progress_bar = view.findViewById(R.id.progress_bar);

            listviewFresh.setLayoutManager(new LinearLayoutManager(getActivity()));
            listviewFresh.setHasFixedSize(true);


            adapterFresh = new SpeciesFreshAdapter(getActivity(), speciesListFresh);
            listviewFresh.setAdapter(adapterFresh);
            loadFreshWaterFishFromDB();
            new FreshWaterAsync(FreshwaterFragment.this, speciesListFreshTemp).execute("/api/speciesData?speciesType=FRESHWATER");

            return view;
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {

                if(!pref.getBoolean("vergincallfreshwater", false))
                {
                    try {

                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable(){

                            public void run() {

                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View dialoglayout = inflater.inflate(R.layout.first_popup, null);
                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                TextView title = dialoglayout.findViewById(R.id.title);
                                TextView desc = dialoglayout.findViewById(R.id.desc);
                                Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);

                                title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                                desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                                thanksbtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                                title.setText("Freshwater");
                                desc.setText("Search for freshwater fish species here and find out about their fishing rules.");

                                dialog = new Dialog(getActivity());

                                dialog.setCanceledOnTouchOutside(false);

                                thanksbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialog.dismiss();
                                        SharedPreferences.Editor edit = pref.edit();
                                        edit.putBoolean("vergincallfreshwater", true);
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
            }
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        }

        @Override
        public void onResume() {
            super.onResume();

        }


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EventBus.getDefault().register(this);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            EventBus.getDefault().unregister(this);
        }

        @Subscribe
        public void onEventBus(SpeciesModel item) {
            Log.d("LOG", "EventBus for " + item.getTitle());

//            for (int i = 0; i < listviewFresh.getChildCount(); i++) {
//                View child = listviewFresh.getChildAt(i);
//                TextView fishname = (TextView) child.findViewById(R.id.fishname);
//                if (fishname.getText().toString().equals(item.getTitle())) {
//
//                    Button savespeciesbtn = (Button) child.findViewById(R.id.savespeciesbtn);
//                    savespeciesbtn.setSelected(false);
//                    return;
//                }
//            }
            adapterFresh.notifyDataSetChanged();
        }

        public void receiveItems() {
            // What does this do?
            for (SpeciesModel aModel: speciesListFreshTemp) {
                SpeciesDownloader.addFishIntoDB(aModel, getContext());
            }

            // Get the items from the db
            loadFreshWaterFishFromDB();
        }

        private void loadFreshWaterFishFromDB() {
            speciesListFresh.addAll(SpeciesDownloader.getAllFreshWaterSpecies());
            addAllToVariable(speciesListFresh);
            adapterFresh.notifyDataSetChanged();
        }
    }

    public static class SavedSpeciesFragment extends Fragment{

        ArrayList<SpeciesModel> speciesListSaved = new ArrayList<SpeciesModel>();
        RecyclerView listviewSaved;
        SpeciesSavedAdapter adapterSaved;
        TextView whattext;
        ProgressBar progress_bar;
        LinearLayout noSavedBackgroung;
        Button downloadAllButton;
        RelativeLayout downloadWrapper, progressWrapper;
        ProgressBar downloadProgressBar;
        Integer totalToDownload = 0, downloadProgress = 0;
        Context mContext;
        public Handler mHandler = new Handler();

        public Dialog dialog = null;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_saved_species, container, false);

            mContext = getContext();

            //    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

            downloadAllButton = view.findViewById(R.id.downloadAllButton);

            downloadAllButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Save all to realm here
                    downloadWrapper.setVisibility(View.INVISIBLE);
                    progressWrapper.setVisibility(View.VISIBLE);
//                    downloadProgressBar.setProgress(0);
                    NetworkManager.getInstance().getAllSpecies(getActivity(),mHandler);

                    // Get all species
                    ArrayList<SpeciesModel> allS = SpeciesDownloader.getAllSpecies();
                    downloadProgressBar.setMax(allS.size());
                    downloadProgressBar.setProgress(0);
                    totalToDownload = allS.size();
                    for (SpeciesModel species : allS) {
                        SpeciesDownloader.saveFish(species.getId());
                    }

                    downloadNextSpecies();
                }
            });

            noSavedBackgroung = view.findViewById(R.id.noSavedBackground);
            listviewSaved = view.findViewById(R.id.listviewSaved);
            whattext = view.findViewById(R.id.what);
            whattext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            downloadWrapper = view.findViewById(R.id.downloadWrapper);
            progressWrapper = view.findViewById(R.id.progressWrapper);
            downloadProgressBar = view.findViewById(R.id.downloadProgressBar);
            progressWrapper.setVisibility(View.INVISIBLE);

            listviewSaved.setLayoutManager(new LinearLayoutManager(getActivity()));
            listviewSaved.setHasFixedSize(true);

            adapterSaved = new SpeciesSavedAdapter(getActivity(), SavedSpeciesFragment.this, speciesListSaved);
            listviewSaved.setAdapter(adapterSaved);

//            loadSaved();

            return view;
        }

        private void downloadNextSpecies() {
            // Get the id
            Integer indexToDownload = allSpecies.size()-totalToDownload;
            SpeciesModel model = allSpecies.get(indexToDownload);

            // Check if we actually have it right?
            SpeciesDetailModel aDetail = SpeciesDownloader.getSpeciesDetails(model.getId());
            if (aDetail != null) {
                // We already have one
                downloadDone("0");
            } else {
                 ArrayList<SpeciesDetailModel> speciesdetailList = new ArrayList<SpeciesDetailModel>();
                 new SpeciesDetailAsync(SavedSpeciesFragment.this, speciesdetailList).execute("/api/species?Id=" + model.id);
            }
        }

        public void downloadDone(String id) {
            totalToDownload--;
            downloadProgressBar.setProgress(++downloadProgress);
            if (totalToDownload == 0) {
                // Done, stop the progress indicator etc
                progressWrapper.setVisibility(View.INVISIBLE);
                loadSaved();
            } else {
                downloadNextSpecies();
            }
        }


        public void loadSaved() {
            speciesListSaved.clear();
            speciesListSaved.addAll(SpeciesDownloader.getSavedSpecies());
            receiveItems();
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {

                if(!pref.getBoolean("vergincallsavedSpecies", false))
                {
                    try {

                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable(){

                            public void run() {

                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View dialoglayout = inflater.inflate(R.layout.first_popup, null);
                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                TextView title = dialoglayout.findViewById(R.id.title);
                                TextView desc = dialoglayout.findViewById(R.id.desc);
                                Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);

                                title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                                desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                                thanksbtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                                title.setText("Save Species Offline");
                                desc.setText("You now have the ability to download species offline." +
                                        System.getProperty("line.separator") +
                                        System.getProperty("line.separator") +
                                        "To download a species, tap the heart icon on the species listing card or download all species from the 'Saved Species' tab.");

                                dialog = new Dialog(getActivity());

                                dialog.setCanceledOnTouchOutside(false);

                                thanksbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialog.dismiss();
                                        SharedPreferences.Editor edit = pref.edit();
                                        edit.putBoolean("vergincallsavedSpecies", true);
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
            }
        }


        @Override
        public void onResume() {
            super.onResume();
            org.greenrobot.eventbus.EventBus.getDefault().register(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
            Utils.freeMemory();
        }

        @Override
        public void onStop() {
            super.onStop();
            org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
        }

        @Subscribe
        public void onEventMainThread(BasicEvent event) {
            if (event == BasicEvent.SPECIES_DOWNLOADED) {

                Toast.makeText(getActivity(),"FINISHED FETCH ALL DATA getFreshWater : "+ ModelManager.getInstance().getFreshWater().size() +
                        "\n" +"getSaltWater : "+ ModelManager.getInstance().getSaltWater().size() , Toast.LENGTH_SHORT).show();
            }
        }

        public void receiveItems() {

            adapterSaved.notifyDataSetChanged();

            if (speciesListSaved.size() > 0) {
                noSavedBackgroung.setVisibility(View.GONE);
                listviewSaved.setVisibility(View.VISIBLE);
            }
            else {
                noSavedBackgroung.setVisibility(View.VISIBLE);
                listviewSaved.setVisibility(View.GONE);
            }
        }


    }
}