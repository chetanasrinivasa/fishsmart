package com.mobiddiction.fishsmart.Rules;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.DB.FishSmartDBHelper;
import com.mobiddiction.fishsmart.DB.RuleSaveEntry;
import com.mobiddiction.fishsmart.MyApplication;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Network.ResponseConfig;
import com.mobiddiction.fishsmart.Network.ResponseListner;
import com.mobiddiction.fishsmart.Profile.ProfileActivity;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.ScreenEnum;
import com.mobiddiction.fishsmart.bean.UserBean;
import com.mobiddiction.fishsmart.dao.NewRulesGuide;
import com.mobiddiction.fishsmart.notification.NotificationActivity;
import com.mobiddiction.fishsmart.util.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Archa on 10/05/2016.
 */
public class RulesActivity extends BaseTabActivity implements androidx.appcompat.app.ActionBar.TabListener {

    Handler handler = new Handler();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private static CircleImageView imgUser;

    TabLayout tab;
    public Dialog dialog = null;
    public SharedPreferences pref;
    public static boolean layoutWaterFlag = false;
    public static boolean layoutTroutFlag = false;
    public static boolean layoutSpearFlag = false;
    public static boolean layoutLocalFlag = false;
    public static boolean layoutRegionalFlag = false;

    public static ArrayList<SaveModel> SaveList = new ArrayList<SaveModel>();
    private ImageView imgNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState/*, R.layout.activity_species_new, false*/);
        setContentView(R.layout.activity_species_new);

        HomeTextChanger("RULES AND INFORMATION", this);
        pref = getSharedPreferences("fishsmart", 0);

        if(!pref.getBoolean("vergincallrules", false))
        {
            try {

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable(){

                    public void run() {

                        LayoutInflater inflater = getLayoutInflater();
                        final View dialoglayout = inflater.inflate(R.layout.first_popup, null);
                        YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                        TextView title = dialoglayout.findViewById(R.id.title);
                        TextView desc = dialoglayout.findViewById(R.id.desc);
                        Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);

                        title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                        desc.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                        thanksbtn.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

                        title.setText("Rules");
                        desc.setText("Check out important fishing rules and information on permitted fishing gear.");

                        dialog = new Dialog(RulesActivity.this);

                        dialog.setCanceledOnTouchOutside(false);

                        thanksbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putBoolean("vergincallrules", true);
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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setBackgroundColor(Color.WHITE);



        tab = findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("Explore"));
        tab.addTab(tab.newTab().setText("Favourite"));

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_header));

        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        imgNotification = mActionBarView.findViewById(R.id.imgNotification);
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RulesActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        TextView nav_title = mActionBarView.findViewById(R.id.nav_title);
        nav_title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        nav_title.setText(Html.fromHtml("RULES AND INFORMATION"));

        imgUser = mActionBarView.findViewById(R.id.imgUser);

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RulesActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });


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
//        getSupportActionBar().setHomeButtonEnabled(false);
//        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        ActionBar.Tab alerttab = getSupportActionBar().newTab().setText("Explore").setTabListener(this);
//        ActionBar.Tab enclosurestab = getSupportActionBar().newTab().setText("Favourite").setTabListener(this);
//
//        getSupportActionBar().addTab(alerttab);
//        getSupportActionBar().addTab(enclosurestab);
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

        if (Utils.isOnline(this) && ModelManager.getInstance().getLoginResponse() != null) {
            NetworkManager.getInstance().getUserProfile(ModelManager.getInstance().getLoginResponse().getAuthorization(), "application/json",
                    ModelManager.getInstance().getLoginResponse().getUserId(), responseListner);
        }
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        final UserBean userBean = (UserBean) obj;
        if (userBean.image.size() > 0) {
            RulesActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Glide.with(RulesActivity.this)
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

    public void DisplaySaltWater()
    {
        Intent intent = new Intent(RulesActivity.this, SaltWaterActivitynew.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.animator.slide_in_bottom, R.animator.slide_out_up);

    }
    public void DisplayFreshWater()
    {
        Intent intent = new Intent(RulesActivity.this, FreshWaterActivitynew.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.animator.slide_in_bottom, R.animator.slide_out_up);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

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
                    return new ExploreFragment();
                case 1:
                    return new SavedFragment();
            }

            return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
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

    public static class ExploreFragment extends Fragment{

        RelativeLayout saltwaterbtn, freshwaterbtn;
        TextView rules, water, trout, spear, local, regional, saltwaterbtntext, freshwaterbtntext;
        LinearLayout waterdynamiclayout, troutdynamiclayout, speardynamiclayout, localdynamiclayout, regionaldynamiclayout, mainlinearlayout;
        RelativeLayout waterlayout, troutlayout, spearlayout, locallayout, regionallayout;

        TextView filterguidetext, salttext, trouttext, speartext, safetext, regionaltext;
        RelativeLayout layoutsalt, layouttrout, layoutspear, layoutsafe, layoutregional;
        RelativeLayout dropdownbtn;
        LinearLayout dropdownlayout;
        Boolean dropdownFlag = false;
        private View view;
        public Handler handler = new Handler();

        public ProgressBar progress_bar;

        List<NewRulesGuide> rulesList = new ArrayList<NewRulesGuide>();
        public Dialog dialog = null;

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if(view == null)
                view = inflater.inflate(R.layout.fragment_rulesexplore, container, false);
           // ((MyApplication) getActivity().getApplication()).getTrackingManager().trackScreen(ScreenEnum.EXPLORE_GUIDES, null);
            FirebaseAnalytics.getInstance(getActivity().getApplicationContext()).setCurrentScreen(getActivity(),ScreenEnum.EXPLORE_GUIDES, null);

            saltwaterbtn = view.findViewById(R.id.saltwaterbtn);
            freshwaterbtn = view.findViewById(R.id.freshwaterbtn);

            waterdynamiclayout = view.findViewById(R.id.waterdynamiclayout);
            troutdynamiclayout = view.findViewById(R.id.troutdynamiclayout);
            speardynamiclayout = view.findViewById(R.id.speardynamiclayout);
            localdynamiclayout = view.findViewById(R.id.localdynamiclayout);
            regionaldynamiclayout = view.findViewById(R.id.regionaldynamiclayout);
            mainlinearlayout = view.findViewById(R.id.mainlinearlayout);

            waterlayout = view.findViewById(R.id.waterlayout);
            troutlayout = view.findViewById(R.id.troutlayout);
            spearlayout = view.findViewById(R.id.spearlayout);
            locallayout = view.findViewById(R.id.locallayout);
            regionallayout = view.findViewById(R.id.regionallayout);

            layoutsalt = view.findViewById(R.id.layoutsalt);
            layouttrout = view.findViewById(R.id.layouttrout);
            layoutspear = view.findViewById(R.id.layoutspear);
            layoutsafe = view.findViewById(R.id.layoutsafe);
            layoutregional = view.findViewById(R.id.layoutregional);

            progress_bar = view.findViewById(R.id.progress_bar);

            NetworkManager.getInstance().getGuide();
//            new RulesAsync(ExploreFragment.this, rulesList).execute("/api/fishingGuide");

            // start change font

            rules = view.findViewById(R.id.rules);
            Typeface face1 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            rules.setTypeface(face1);

            water = view.findViewById(R.id.water);
            Typeface face2 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            water.setTypeface(face2);

            trout = view.findViewById(R.id.trout);
            Typeface face3 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            trout.setTypeface(face3);

            spear = view.findViewById(R.id.spear);
            Typeface face4 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            spear.setTypeface(face4);

            local = view.findViewById(R.id.local);
            Typeface face5 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            local.setTypeface(face5);

            regional = view.findViewById(R.id.regional);
            Typeface face6 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            regional.setTypeface(face6);

            saltwaterbtntext = view.findViewById(R.id.saltwaterbtntext);
            Typeface face7 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf");
            saltwaterbtntext.setTypeface(face7);

            freshwaterbtntext = view.findViewById(R.id.freshwaterbtntext);
            Typeface face8 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf");
            freshwaterbtntext.setTypeface(face8);

            filterguidetext = view.findViewById(R.id.filterguidetext);
            filterguidetext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            salttext = view.findViewById(R.id.salttext);
            salttext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            trouttext = view.findViewById(R.id.trouttext);
            trouttext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            speartext = view.findViewById(R.id.speartext);
            speartext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            safetext = view.findViewById(R.id.safetext);
            safetext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            regionaltext = view.findViewById(R.id.regionaltext);
            regionaltext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            // end change font

            dropdownbtn = view.findViewById(R.id.dropdownbtn);
            dropdownlayout = view.findViewById(R.id.dropdownlayout);

            dropdownbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!dropdownFlag)
                    {
                        if(!filterguidetext.getText().equals("Filter guides by..."))
                        {
                            filterguidetext.setText("Filter guides by...");
                            waterlayout.setVisibility(View.VISIBLE);
                            troutlayout.setVisibility(View.VISIBLE);
                            spearlayout.setVisibility(View.VISIBLE);
                            locallayout.setVisibility(View.VISIBLE);
                            regionallayout.setVisibility(View.VISIBLE);
                            dropdownlayout.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            dropdownlayout.setVisibility(View.VISIBLE);
                            dropdownFlag = true;
                        }
                    }
                    else
                    {
                        dropdownlayout.setVisibility(View.GONE);
                        dropdownFlag = false;
                    }
                }
            });

            saltwaterbtn.setOnClickListener(listener);
            freshwaterbtn.setOnClickListener(listener);
            layoutsalt.setOnClickListener(listener);
            layouttrout.setOnClickListener(listener);
            layoutspear.setOnClickListener(listener);
            layoutsafe.setOnClickListener(listener);
            layoutregional.setOnClickListener(listener);
            mainlinearlayout.setOnClickListener(listener);
            rules.setOnClickListener(listener);

            return view;
        }

        View.OnClickListener listener = new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.saltwaterbtn)
                {
                    ((RulesActivity)getActivity()).DisplaySaltWater();

                }else if (v.getId() == R.id.freshwaterbtn)
                {
                    ((RulesActivity)getActivity()).DisplayFreshWater();

                }else if (v.getId() == R.id.layoutsalt)
                {
                    waterlayout.setVisibility(View.VISIBLE);
                    troutlayout.setVisibility(View.GONE);
                    spearlayout.setVisibility(View.GONE);
                    locallayout.setVisibility(View.GONE);
                    regionallayout.setVisibility(View.GONE);
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                    filterguidetext.setText("Salt and Freshwater Guides");


                }else if (v.getId() == R.id.layouttrout)
                {
                    waterlayout.setVisibility(View.GONE);
                    troutlayout.setVisibility(View.VISIBLE);
                    spearlayout.setVisibility(View.GONE);
                    locallayout.setVisibility(View.GONE);
                    regionallayout.setVisibility(View.GONE);
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                    filterguidetext.setText("Trout Fishing Guides");

                }else if (v.getId() == R.id.layoutspear)
                {
                    waterlayout.setVisibility(View.GONE);
                    troutlayout.setVisibility(View.GONE);
                    spearlayout.setVisibility(View.VISIBLE);
                    locallayout.setVisibility(View.GONE);
                    regionallayout.setVisibility(View.GONE);
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                    filterguidetext.setText("Spear Fishing Guides");

                }else if (v.getId() == R.id.layoutsafe)
                {
                    waterlayout.setVisibility(View.GONE);
                    troutlayout.setVisibility(View.GONE);
                    spearlayout.setVisibility(View.GONE);
                    locallayout.setVisibility(View.VISIBLE);
                    regionallayout.setVisibility(View.GONE);
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                    filterguidetext.setText("Safe Fishing Guides");

                }else if (v.getId() == R.id.layoutregional)
                {
                    waterlayout.setVisibility(View.GONE);
                    troutlayout.setVisibility(View.GONE);
                    spearlayout.setVisibility(View.GONE);
                    locallayout.setVisibility(View.GONE);
                    regionallayout.setVisibility(View.VISIBLE);
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                    filterguidetext.setText("Regional Fishing Guides");
                }
                else if (v.getId() == R.id.mainlinearlayout)
                {
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                }
                else if (v.getId() == R.id.rules)
                {
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                }
            }
        };

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        }



        @Override
        public void onResume() {
            super.onResume();
            EventBus.getDefault().register(this);

            if (ModelManager.getInstance() != null && ModelManager.getInstance().getLoginDetails() != null) {
                if (ModelManager.getInstance().getLoginResponse().getUserId() != null &&
                        !ModelManager.getInstance().getLoginResponse().getUserId().equals("")) {
                    imgUser.setVisibility(View.VISIBLE);
                }
            } else {
                imgUser.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPause() {
            super.onPause();
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
            if(event == BasicEvent.GUIDE_LOADED_SUCCESS){
                RulesListDownloaded();
            }
            if(event == BasicEvent.GUIDE_LOADED_FAILED){

            }
        }



        public void RulesListDownloaded() {

            rulesList =  ModelManager.getInstance().getNewRulesGuide();

            for(int i = 0; i< rulesList.size(); i++)
            {
                final String fishguidetype = rulesList.get(i).getFishingGuideType().replaceAll("\\s+","");
                if(rulesList.get(i).getFishingGuideType().replaceAll("\\s+","").equals("SALTWATERANDFRESHWATER"))
                {
                    for(int j=0; j<ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).size(); j++)
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulesexplore_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle());
                        rulesdesc.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String pdfurl = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getPdfUrl();

                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", pdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start fish rule saving

                        final String finalTypeid = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId();
                        final String finalDesc = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription();
                        final String finalTitle = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle();

                        savebtn.setSelected(false);
                        savebtn.setChecked(false);

                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        String[] columes = {

                                RuleSaveEntry.ID,
                                RuleSaveEntry.FISHTYPEID,
                                RuleSaveEntry.TITLE,
                                RuleSaveEntry.TYPE,
                                RuleSaveEntry.DESCRIPTION,
                                RuleSaveEntry.PDFURL
                        };

                        Cursor cursor = db.query(RuleSaveEntry.TABLE_NAME, columes, null, null, null, null  , "_id");

                        while (cursor.moveToNext()) {

                            int indexOfTypeId = cursor.getColumnIndex(RuleSaveEntry.FISHTYPEID);

                            if(cursor.getString(indexOfTypeId).equals(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId()))
                            {
                                savebtn.setSelected(true);
                                savebtn.setChecked(true);
                            }
                        }

                        cursor.close();

                        if (db != null && db.isOpen())
                            db.close();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(savebtn.isChecked())
                                {
                                    savebtn.setSelected(true);

                                    try {

                                        Handler h = new Handler(Looper.getMainLooper());
                                        h.post(new Runnable(){

                                            public void run() {

                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                final View dialoglayout = inflater.inflate(R.layout.save_rules_popup, null);
                                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                                dialog = new Dialog(getActivity());

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

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            dialog.dismiss();

                                        }
                                    }, 1500);

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(RuleSaveEntry.FISHTYPEID, finalTypeid);
                                        values.put(RuleSaveEntry.TYPE, fishguidetype);
                                        values.put(RuleSaveEntry.TITLE, finalTitle);
                                        values.put(RuleSaveEntry.DESCRIPTION, finalDesc);
                                        values.put(RuleSaveEntry.PDFURL, pdfurl);

                                        db.insert(RuleSaveEntry.TABLE_NAME, null, values);
                                        Log.d("INSERTED", "ID : " + finalTypeid);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                }
                                else
                                {
                                    savebtn.setSelected(false);

                                    FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                                    db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                    Log.d("DELETED", "ID : " + finalTypeid);
                                }
                            }
                        });

                        // end fish rule saving

                        waterdynamiclayout.addView(child);
                    }
                }

                if(rulesList.get(i).getFishingGuideType().replaceAll("\\s+","").equals("TROUTFISHING"))
                {
                    for(int j=0; j<ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).size(); j++)
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulesexplore_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle());
                        rulesdesc.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String pdfurl = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getPdfUrl();

                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", pdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start fish rule saving

                        final String finalTypeid = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId();
                        final String finalDesc = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription();
                        final String finalTitle = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle();

                        savebtn.setSelected(false);
                        savebtn.setChecked(false);

                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        String[] columes = {

                                RuleSaveEntry.ID,
                                RuleSaveEntry.FISHTYPEID,
                                RuleSaveEntry.TITLE,
                                RuleSaveEntry.TYPE,
                                RuleSaveEntry.DESCRIPTION,
                                RuleSaveEntry.PDFURL
                        };

                        Cursor cursor = db.query(RuleSaveEntry.TABLE_NAME, columes, null, null, null, null  , "_id");

                        while (cursor.moveToNext()) {

                            int indexOfTypeId = cursor.getColumnIndex(RuleSaveEntry.FISHTYPEID);

                            if(cursor.getString(indexOfTypeId).equals(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId()))
                            {
                                savebtn.setSelected(true);
                                savebtn.setChecked(true);
                            }
                        }

                        cursor.close();

                        if (db != null && db.isOpen())
                            db.close();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(savebtn.isChecked())
                                {
                                    savebtn.setSelected(true);

                                    // start popup dialog

                                    try {

                                        Handler h = new Handler(Looper.getMainLooper());
                                        h.post(new Runnable(){

                                            public void run() {

                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                final View dialoglayout = inflater.inflate(R.layout.save_rules_popup, null);
                                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                                dialog = new Dialog(getActivity());

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

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            dialog.dismiss();

                                        }
                                    }, 1500);

                                    // end popup dialog

                                    // save data

                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(RuleSaveEntry.FISHTYPEID, finalTypeid);
                                        values.put(RuleSaveEntry.TYPE, fishguidetype);
                                        values.put(RuleSaveEntry.TITLE, finalTitle);
                                        values.put(RuleSaveEntry.DESCRIPTION, finalDesc);
                                        values.put(RuleSaveEntry.PDFURL, pdfurl);

                                        db.insert(RuleSaveEntry.TABLE_NAME, null, values);
                                        Log.d("INSERTED", "ID : " + finalTypeid);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                }
                                else
                                {
                                    savebtn.setSelected(false);

                                    FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                                    db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                    Log.d("DELETED", "ID : " + finalTypeid);
                                }
                            }
                        });

                        // end fish rule saving

                        troutdynamiclayout.addView(child);
                    }
                }

                if(rulesList.get(i).getFishingGuideType().replaceAll("\\s+","").equals("SPEARFISHING"))
                {
                    for(int j=0; j<ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).size(); j++)
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulesexplore_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle());
                        rulesdesc.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String pdfurl = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getPdfUrl();

                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", pdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start fish rule saving

                        final String finalTypeid = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId();
                        final String finalDesc = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription();
                        final String finalTitle = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle();

                        savebtn.setSelected(false);
                        savebtn.setChecked(false);

                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        String[] columes = {

                                RuleSaveEntry.ID,
                                RuleSaveEntry.FISHTYPEID,
                                RuleSaveEntry.TITLE,
                                RuleSaveEntry.TYPE,
                                RuleSaveEntry.DESCRIPTION,
                                RuleSaveEntry.PDFURL
                        };

                        Cursor cursor = db.query(RuleSaveEntry.TABLE_NAME, columes, null, null, null, null  , "_id");

                        while (cursor.moveToNext()) {

                            int indexOfTypeId = cursor.getColumnIndex(RuleSaveEntry.FISHTYPEID);

                            if(cursor.getString(indexOfTypeId).equals(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId()))
                            {
                                savebtn.setSelected(true);
                                savebtn.setChecked(true);
                            }
                        }

                        cursor.close();

                        if (db != null && db.isOpen())
                            db.close();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (savebtn.isChecked()) {
                                    savebtn.setSelected(true);

                                    // start popup dialog

                                    try {

                                        Handler h = new Handler(Looper.getMainLooper());
                                        h.post(new Runnable(){

                                            public void run() {

                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                final View dialoglayout = inflater.inflate(R.layout.save_rules_popup, null);
                                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                                dialog = new Dialog(getActivity());

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

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            dialog.dismiss();

                                        }
                                    }, 1500);

                                    // end popup dialog

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(RuleSaveEntry.FISHTYPEID, finalTypeid);
                                        values.put(RuleSaveEntry.TYPE, fishguidetype);
                                        values.put(RuleSaveEntry.TITLE, finalTitle);
                                        values.put(RuleSaveEntry.DESCRIPTION, finalDesc);
                                        values.put(RuleSaveEntry.PDFURL, pdfurl);

                                        db.insert(RuleSaveEntry.TABLE_NAME, null, values);
                                        Log.d("INSERTED", "ID : " + finalTypeid);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                } else {
                                    savebtn.setSelected(false);

                                    FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                                    db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                    Log.d("DELETED", "ID : " + finalTypeid);
                                }
                            }
                        });

                        // end fish rule saving

                        speardynamiclayout.addView(child);

                    }

                }

                if(rulesList.get(i).getFishingGuideType().replaceAll("\\s+","").equals("SAFEFISHING"))
                {
                    for(int j=0; j<ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).size(); j++)
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulesexplore_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle());
                        rulesdesc.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String pdfurl = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getPdfUrl();

                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", pdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start fish rule saving

                        final String finalTypeid = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId();
                        final String finalDesc = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription();
                        final String finalTitle = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle();

                        savebtn.setSelected(false);
                        savebtn.setChecked(false);

                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        String[] columes = {

                                RuleSaveEntry.ID,
                                RuleSaveEntry.FISHTYPEID,
                                RuleSaveEntry.TITLE,
                                RuleSaveEntry.TYPE,
                                RuleSaveEntry.DESCRIPTION,
                                RuleSaveEntry.PDFURL
                        };

                        Cursor cursor = db.query(RuleSaveEntry.TABLE_NAME, columes, null, null, null, null  , "_id");

                        while (cursor.moveToNext()) {

                            int indexOfTypeId = cursor.getColumnIndex(RuleSaveEntry.FISHTYPEID);

                            if(cursor.getString(indexOfTypeId).equals(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId()))
                            {
                                savebtn.setSelected(true);
                                savebtn.setChecked(true);
                            }
                        }

                        cursor.close();

                        if (db != null && db.isOpen())
                            db.close();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (savebtn.isChecked()) {
                                    savebtn.setSelected(true);

                                    // start popup dialog

                                    try {

                                        Handler h = new Handler(Looper.getMainLooper());
                                        h.post(new Runnable(){

                                            public void run() {

                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                final View dialoglayout = inflater.inflate(R.layout.save_rules_popup, null);
                                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                                dialog = new Dialog(getActivity());

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

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            dialog.dismiss();

                                        }
                                    }, 1500);

                                    // end popup dialog

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(RuleSaveEntry.FISHTYPEID, finalTypeid);
                                        values.put(RuleSaveEntry.TYPE, fishguidetype);
                                        values.put(RuleSaveEntry.TITLE, finalTitle);
                                        values.put(RuleSaveEntry.DESCRIPTION, finalDesc);
                                        values.put(RuleSaveEntry.PDFURL, pdfurl);

                                        db.insert(RuleSaveEntry.TABLE_NAME, null, values);
                                        Log.d("INSERTED", "ID : " + finalTypeid);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                } else {
                                    savebtn.setSelected(false);

                                    FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                                    db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                    Log.d("DELETED", "ID : " + finalTypeid);
                                }
                            }
                        });

                        // end fish rule saving

                        localdynamiclayout.addView(child);
                    }
                }

                if(rulesList.get(i).getFishingGuideType().replaceAll("\\s+","").equals("REGIONALFISHING"))
                {
                    for(int j=0; j<ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).size(); j++)
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulesexplore_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle());
                        rulesdesc.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String pdfurl = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getPdfUrl();

                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Activity activity = getActivity();
                                if (activity != null && isAdded()){
                                    Intent intent = new Intent(getActivity(), PdfActivity.class);
                                    intent.putExtra("fs", pdfurl);
                                    intent.putExtra("share", true);
                                    startActivity(intent);
                                }

                            }
                        });

                        // start fish rule saving

                        final String finalTypeid = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId();
                        final String finalDesc = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription();
                        final String finalTitle = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle();

                        savebtn.setSelected(false);
                        savebtn.setChecked(false);

                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        String[] columes = {

                                RuleSaveEntry.ID,
                                RuleSaveEntry.FISHTYPEID,
                                RuleSaveEntry.TITLE,
                                RuleSaveEntry.TYPE,
                                RuleSaveEntry.DESCRIPTION,
                                RuleSaveEntry.PDFURL
                        };

                        Cursor cursor = db.query(RuleSaveEntry.TABLE_NAME, columes, null, null, null, null  , "_id");

                        while (cursor.moveToNext()) {

                            int indexOfTypeId = cursor.getColumnIndex(RuleSaveEntry.FISHTYPEID);

                            if(cursor.getString(indexOfTypeId).equals(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId()))
                            {
                                savebtn.setSelected(true);
                                savebtn.setChecked(true);
                            }
                        }

                        cursor.close();

                        if (db != null && db.isOpen())
                            db.close();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (savebtn.isChecked()) {
                                    savebtn.setSelected(true);

                                    // start popup dialog

                                    try {

                                        Handler h = new Handler(Looper.getMainLooper());
                                        h.post(new Runnable(){

                                            public void run() {

                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                final View dialoglayout = inflater.inflate(R.layout.save_rules_popup, null);
                                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                                dialog = new Dialog(getActivity());

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

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            dialog.dismiss();

                                        }
                                    }, 1500);

                                    // end popup dialog

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(RuleSaveEntry.FISHTYPEID, finalTypeid);
                                        values.put(RuleSaveEntry.TYPE, fishguidetype);
                                        values.put(RuleSaveEntry.TITLE, finalTitle);
                                        values.put(RuleSaveEntry.DESCRIPTION, finalDesc);
                                        values.put(RuleSaveEntry.PDFURL, pdfurl);

                                        db.insert(RuleSaveEntry.TABLE_NAME, null, values);
                                        Log.d("INSERTED", "ID : " + finalTypeid);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                } else {
                                    savebtn.setSelected(false);

                                    FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                                    db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                    Log.d("DELETED", "ID : " + finalTypeid);
                                }
                            }
                        });

                        // end fish rule saving

                        regionaldynamiclayout.addView(child);
                    }
                }
            }
        }


        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {

                if(view != null)
                {
                    if( waterdynamiclayout != null && troutdynamiclayout != null && speardynamiclayout != null && localdynamiclayout != null && regionaldynamiclayout != null )
                    {
                        if(layoutWaterFlag)
                        {
                            layoutWaterFlag = false;
                            waterdynamiclayout.removeAllViews();
                            DrawWaterLayout();
                        }
                        if(layoutTroutFlag)
                        {
                            layoutTroutFlag = false;
                            troutdynamiclayout.removeAllViews();
                            DrawTroutLayout();
                        }

                        if(layoutSpearFlag)
                        {
                            layoutSpearFlag = false;
                            speardynamiclayout.removeAllViews();
                            DrawSpearLayout();
                        }

                        if(layoutLocalFlag)
                        {
                            layoutLocalFlag = false;
                            localdynamiclayout.removeAllViews();
                            DrawLocalLayout();
                        }

                        if(layoutRegionalFlag)
                        {
                            layoutRegionalFlag = false;
                            regionaldynamiclayout.removeAllViews();
                            DrawRegionalLayout();
                        }
                    }
                }
            }
        }

        public void DrawWaterLayout()
        {
            for(int i=0;i<rulesList.size();i++) {
                final String fishguidetype = rulesList.get(i).getFishingGuideType().replaceAll("\\s+","");

                if(rulesList.get(i).getFishingGuideType().replaceAll("\\s+","").equals("SALTWATERANDFRESHWATER"))
                {
                    for(int j=0; j<ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).size(); j++)
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulesexplore_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle());
                        rulesdesc.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String pdfurl = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getPdfUrl();

                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", pdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start fish rule saving

                        final String finalTypeid = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId();
                        final String finalDesc = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription();
                        final String finalTitle = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle();

                        savebtn.setSelected(false);
                        savebtn.setChecked(false);

                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        String[] columes = {

                                RuleSaveEntry.ID,
                                RuleSaveEntry.FISHTYPEID,
                                RuleSaveEntry.TITLE,
                                RuleSaveEntry.TYPE,
                                RuleSaveEntry.DESCRIPTION,
                                RuleSaveEntry.PDFURL
                        };

                        Cursor cursor = db.query(RuleSaveEntry.TABLE_NAME, columes, null, null, null, null  , "_id");

                        while (cursor.moveToNext()) {

                            int indexOfTypeId = cursor.getColumnIndex(RuleSaveEntry.FISHTYPEID);

                            if(cursor.getString(indexOfTypeId).equals(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId()))
                            {
                                savebtn.setSelected(true);
                                savebtn.setChecked(true);
                            }
                        }

                        cursor.close();

                        if (db != null && db.isOpen())
                            db.close();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(savebtn.isChecked())
                                {
                                    savebtn.setSelected(true);

                                    try {

                                        Handler h = new Handler(Looper.getMainLooper());
                                        h.post(new Runnable(){

                                            public void run() {

                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                final View dialoglayout = inflater.inflate(R.layout.save_rules_popup, null);
                                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                                dialog = new Dialog(getActivity());

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

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            dialog.dismiss();

                                        }
                                    }, 1500);

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(RuleSaveEntry.FISHTYPEID, finalTypeid);
                                        values.put(RuleSaveEntry.TYPE, fishguidetype);
                                        values.put(RuleSaveEntry.TITLE, finalTitle);
                                        values.put(RuleSaveEntry.DESCRIPTION, finalDesc);
                                        values.put(RuleSaveEntry.PDFURL, pdfurl);

                                        db.insert(RuleSaveEntry.TABLE_NAME, null, values);
                                        Log.d("INSERTED", "ID : " + finalTypeid);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                }
                                else
                                {
                                    savebtn.setSelected(false);

                                    FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                                    db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                    Log.d("DELETED", "ID : " + finalTypeid);
                                }
                            }
                        });

                        // end fish rule saving

                        waterdynamiclayout.addView(child);
                    }
                }
            }
        }

        public void DrawTroutLayout()
        {
            for(int i=0;i<rulesList.size();i++) {
                final String fishguidetype = rulesList.get(i).getFishingGuideType().replaceAll("\\s+","");

                if(rulesList.get(i).getFishingGuideType().replaceAll("\\s+","").equals("TROUTFISHING"))
                {
                    for(int j=0; j<ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).size(); j++)
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulesexplore_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle());
                        rulesdesc.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String pdfurl = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getPdfUrl();

                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", pdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start fish rule saving

                        final String finalTypeid = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId();
                        final String finalDesc = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription();
                        final String finalTitle = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle();

                        savebtn.setSelected(false);
                        savebtn.setChecked(false);

                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        String[] columes = {

                                RuleSaveEntry.ID,
                                RuleSaveEntry.FISHTYPEID,
                                RuleSaveEntry.TITLE,
                                RuleSaveEntry.TYPE,
                                RuleSaveEntry.DESCRIPTION,
                                RuleSaveEntry.PDFURL
                        };

                        Cursor cursor = db.query(RuleSaveEntry.TABLE_NAME, columes, null, null, null, null  , "_id");

                        while (cursor.moveToNext()) {

                            int indexOfTypeId = cursor.getColumnIndex(RuleSaveEntry.FISHTYPEID);

                            if(cursor.getString(indexOfTypeId).equals(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId()))
                            {
                                savebtn.setSelected(true);
                                savebtn.setChecked(true);
                            }
                        }

                        cursor.close();

                        if (db != null && db.isOpen())
                            db.close();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(savebtn.isChecked())
                                {
                                    savebtn.setSelected(true);

                                    // start popup dialog

                                    try {

                                        Handler h = new Handler(Looper.getMainLooper());
                                        h.post(new Runnable(){

                                            public void run() {

                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                final View dialoglayout = inflater.inflate(R.layout.save_rules_popup, null);
                                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                                dialog = new Dialog(getActivity());

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

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            dialog.dismiss();

                                        }
                                    }, 1500);

                                    // end popup dialog

                                    // save data

                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(RuleSaveEntry.FISHTYPEID, finalTypeid);
                                        values.put(RuleSaveEntry.TYPE, fishguidetype);
                                        values.put(RuleSaveEntry.TITLE, finalTitle);
                                        values.put(RuleSaveEntry.DESCRIPTION, finalDesc);
                                        values.put(RuleSaveEntry.PDFURL, pdfurl);

                                        db.insert(RuleSaveEntry.TABLE_NAME, null, values);
                                        Log.d("INSERTED", "ID : " + finalTypeid);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                }
                                else
                                {
                                    savebtn.setSelected(false);

                                    FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                                    db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                    Log.d("DELETED", "ID : " + finalTypeid);
                                }
                            }
                        });

                        // end fish rule saving

                        troutdynamiclayout.addView(child);
                    }
                }
            }
        }

        public void DrawSpearLayout()
        {
            for(int i=0;i<rulesList.size();i++) {
                final String fishguidetype = rulesList.get(i).getFishingGuideType().replaceAll("\\s+","");

                if(rulesList.get(i).getFishingGuideType().replaceAll("\\s+","").equals("SPEARFISHING"))
                {
                    for(int j=0; j<ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).size(); j++)
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulesexplore_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle());
                        rulesdesc.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String pdfurl = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getPdfUrl();

                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", pdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start fish rule saving

                        final String finalTypeid = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId();
                        final String finalDesc = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription();
                        final String finalTitle = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle();

                        savebtn.setSelected(false);
                        savebtn.setChecked(false);

                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        String[] columes = {

                                RuleSaveEntry.ID,
                                RuleSaveEntry.FISHTYPEID,
                                RuleSaveEntry.TITLE,
                                RuleSaveEntry.TYPE,
                                RuleSaveEntry.DESCRIPTION,
                                RuleSaveEntry.PDFURL
                        };

                        Cursor cursor = db.query(RuleSaveEntry.TABLE_NAME, columes, null, null, null, null  , "_id");

                        while (cursor.moveToNext()) {

                            int indexOfTypeId = cursor.getColumnIndex(RuleSaveEntry.FISHTYPEID);

                            if(cursor.getString(indexOfTypeId).equals(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId()))
                            {
                                savebtn.setSelected(true);
                                savebtn.setChecked(true);
                            }
                        }

                        cursor.close();

                        if (db != null && db.isOpen())
                            db.close();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (savebtn.isChecked()) {
                                    savebtn.setSelected(true);

                                    // start popup dialog

                                    try {

                                        Handler h = new Handler(Looper.getMainLooper());
                                        h.post(new Runnable(){

                                            public void run() {

                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                final View dialoglayout = inflater.inflate(R.layout.save_rules_popup, null);
                                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                                dialog = new Dialog(getActivity());

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

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            dialog.dismiss();

                                        }
                                    }, 1500);

                                    // end popup dialog

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(RuleSaveEntry.FISHTYPEID, finalTypeid);
                                        values.put(RuleSaveEntry.TYPE, fishguidetype);
                                        values.put(RuleSaveEntry.TITLE, finalTitle);
                                        values.put(RuleSaveEntry.DESCRIPTION, finalDesc);
                                        values.put(RuleSaveEntry.PDFURL, pdfurl);

                                        db.insert(RuleSaveEntry.TABLE_NAME, null, values);
                                        Log.d("INSERTED", "ID : " + finalTypeid);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                } else {
                                    savebtn.setSelected(false);

                                    FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                                    db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                    Log.d("DELETED", "ID : " + finalTypeid);
                                }
                            }
                        });

                        // end fish rule saving

                        speardynamiclayout.addView(child);

                    }
                }
            }
        }

        public void DrawLocalLayout()
        {
            for(int i=0;i<rulesList.size();i++) {
                final String fishguidetype = rulesList.get(i).getFishingGuideType().replaceAll("\\s+","");

                if(rulesList.get(i).getFishingGuideType().replaceAll("\\s+","").equals("SAFEFISHING"))
                {
                    for(int j=0; j<ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).size(); j++)
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulesexplore_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle());
                        rulesdesc.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String pdfurl = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getPdfUrl();

                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", pdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start fish rule saving

                        final String finalTypeid = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId();
                        final String finalDesc = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription();
                        final String finalTitle = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle();

                        savebtn.setSelected(false);
                        savebtn.setChecked(false);

                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        String[] columes = {

                                RuleSaveEntry.ID,
                                RuleSaveEntry.FISHTYPEID,
                                RuleSaveEntry.TITLE,
                                RuleSaveEntry.TYPE,
                                RuleSaveEntry.DESCRIPTION,
                                RuleSaveEntry.PDFURL
                        };

                        Cursor cursor = db.query(RuleSaveEntry.TABLE_NAME, columes, null, null, null, null  , "_id");

                        while (cursor.moveToNext()) {

                            int indexOfTypeId = cursor.getColumnIndex(RuleSaveEntry.FISHTYPEID);

                            if(cursor.getString(indexOfTypeId).equals(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId()))
                            {
                                savebtn.setSelected(true);
                                savebtn.setChecked(true);
                            }
                        }

                        cursor.close();

                        if (db != null && db.isOpen())
                            db.close();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (savebtn.isChecked()) {
                                    savebtn.setSelected(true);

                                    // start popup dialog

                                    try {

                                        Handler h = new Handler(Looper.getMainLooper());
                                        h.post(new Runnable(){

                                            public void run() {

                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                final View dialoglayout = inflater.inflate(R.layout.save_rules_popup, null);
                                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                                dialog = new Dialog(getActivity());

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

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            dialog.dismiss();

                                        }
                                    }, 1500);

                                    // end popup dialog

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(RuleSaveEntry.FISHTYPEID, finalTypeid);
                                        values.put(RuleSaveEntry.TYPE, fishguidetype);
                                        values.put(RuleSaveEntry.TITLE, finalTitle);
                                        values.put(RuleSaveEntry.DESCRIPTION, finalDesc);
                                        values.put(RuleSaveEntry.PDFURL, pdfurl);

                                        db.insert(RuleSaveEntry.TABLE_NAME, null, values);
                                        Log.d("INSERTED", "ID : " + finalTypeid);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                } else {
                                    savebtn.setSelected(false);

                                    FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                                    db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                    Log.d("DELETED", "ID : " + finalTypeid);
                                }
                            }
                        });

                        // end fish rule saving

                        localdynamiclayout.addView(child);
                    }
                }
            }
        }

        public void DrawRegionalLayout()
        {
            for(int i=0;i<rulesList.size();i++)
            {
                final String fishguidetype = rulesList.get(i).getFishingGuideType().replaceAll("\\s+","");

                if(rulesList.get(i).getFishingGuideType().replaceAll("\\s+","").equals("REGIONALFISHING"))
                {
                    for(int j=0; j<ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).size(); j++)
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulesexplore_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle());
                        rulesdesc.setText(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String pdfurl = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getPdfUrl();

                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", pdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start fish rule saving

                        final String finalTypeid = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId();
                        final String finalDesc = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getDescription();
                        final String finalTitle = ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getTitle();

                        savebtn.setSelected(false);
                        savebtn.setChecked(false);

                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        String[] columes = {

                                RuleSaveEntry.ID,
                                RuleSaveEntry.FISHTYPEID,
                                RuleSaveEntry.TITLE,
                                RuleSaveEntry.TYPE,
                                RuleSaveEntry.DESCRIPTION,
                                RuleSaveEntry.PDFURL
                        };

                        Cursor cursor = db.query(RuleSaveEntry.TABLE_NAME, columes, null, null, null, null  , "_id");

                        while (cursor.moveToNext()) {

                            int indexOfTypeId = cursor.getColumnIndex(RuleSaveEntry.FISHTYPEID);

                            if(cursor.getString(indexOfTypeId).equals(ModelManager.getInstance().getNewListTypeModelbyid(rulesList.get(i).getId()).get(j).getId()))
                            {
                                savebtn.setSelected(true);
                                savebtn.setChecked(true);
                            }
                        }

                        cursor.close();

                        if (db != null && db.isOpen())
                            db.close();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (savebtn.isChecked()) {
                                    savebtn.setSelected(true);

                                    // start popup dialog

                                    try {

                                        Handler h = new Handler(Looper.getMainLooper());
                                        h.post(new Runnable(){

                                            public void run() {

                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                final View dialoglayout = inflater.inflate(R.layout.save_rules_popup, null);
                                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                                dialog = new Dialog(getActivity());

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

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            dialog.dismiss();

                                        }
                                    }, 1500);

                                    // end popup dialog

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(RuleSaveEntry.FISHTYPEID, finalTypeid);
                                        values.put(RuleSaveEntry.TYPE, fishguidetype);
                                        values.put(RuleSaveEntry.TITLE, finalTitle);
                                        values.put(RuleSaveEntry.DESCRIPTION, finalDesc);
                                        values.put(RuleSaveEntry.PDFURL, pdfurl);

                                        db.insert(RuleSaveEntry.TABLE_NAME, null, values);
                                        Log.d("INSERTED", "ID : " + finalTypeid);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                } else {
                                    savebtn.setSelected(false);

                                    FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                                    db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                    Log.d("DELETED", "ID : " + finalTypeid);
                                }
                            }
                        });

                        // end fish rule saving
                        regionaldynamiclayout.addView(child);
                    }
                }

            }
        }
    }

    public static class SavedFragment extends Fragment {

        RelativeLayout saltwaterbtn, freshwaterbtn;
        TextView rules, water, trout, spear, local, regional, saltwaterbtntext, freshwaterbtntext;
        LinearLayout waterdynamiclayout, troutdynamiclayout, speardynamiclayout, localdynamiclayout, regionaldynamiclayout, mainlinearlayout;
        RelativeLayout waterlayout, troutlayout, spearlayout, locallayout, regionallayout;

        TextView filterguidetext, salttext, trouttext, speartext, safetext, regionaltext, textvergin1, textvergin2;
        RelativeLayout layoutsalt, layouttrout, layoutspear, layoutsafe, layoutregional, verginlayout;
        RelativeLayout dropdownbtn;
        LinearLayout dropdownlayout;
        Boolean dropdownFlag = false;
        Boolean waterflag = false, troutflag = false, spearflag = false, localflag = false, regionalflag = false;
        private View view;

        public ProgressBar progress_bar;

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if(view == null)

                view = inflater.inflate(R.layout.fragment_rulessave, container, false);
            //((MyApplication) getActivity().getApplication()).getTrackingManager().trackScreen(ScreenEnum.SAVED_GUIDES, null);
            FirebaseAnalytics.getInstance(getActivity().getApplicationContext()).setCurrentScreen(getActivity(),ScreenEnum.SAVED_GUIDES, null);
            saltwaterbtn = view.findViewById(R.id.saltwaterbtn);
            freshwaterbtn = view.findViewById(R.id.freshwaterbtn);

            waterdynamiclayout = view.findViewById(R.id.waterdynamiclayout);
            troutdynamiclayout = view.findViewById(R.id.troutdynamiclayout);
            speardynamiclayout = view.findViewById(R.id.speardynamiclayout);
            localdynamiclayout = view.findViewById(R.id.localdynamiclayout);
            regionaldynamiclayout = view.findViewById(R.id.regionaldynamiclayout);
            mainlinearlayout = view.findViewById(R.id.mainlinearlayout);

            waterlayout = view.findViewById(R.id.waterlayout);
            troutlayout = view.findViewById(R.id.troutlayout);
            spearlayout = view.findViewById(R.id.spearlayout);
            locallayout = view.findViewById(R.id.locallayout);
            regionallayout = view.findViewById(R.id.regionallayout);

            layoutsalt = view.findViewById(R.id.layoutsalt);
            layouttrout = view.findViewById(R.id.layouttrout);
            layoutspear = view.findViewById(R.id.layoutspear);
            layoutsafe = view.findViewById(R.id.layoutsafe);
            layoutregional = view.findViewById(R.id.layoutregional);
            verginlayout = view.findViewById(R.id.verginlayout);

            progress_bar = view.findViewById(R.id.progress_bar);

            // start change font

            textvergin1 = view.findViewById(R.id.textvergin1);
            textvergin1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            textvergin2 = view.findViewById(R.id.textvergin2);
            textvergin2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            rules = view.findViewById(R.id.rules);
            Typeface face1 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            rules.setTypeface(face1);

            water = view.findViewById(R.id.water);
            Typeface face2 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            water.setTypeface(face2);

            trout = view.findViewById(R.id.trout);
            Typeface face3 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            trout.setTypeface(face3);

            spear = view.findViewById(R.id.spear);
            Typeface face4 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            spear.setTypeface(face4);

            local = view.findViewById(R.id.local);
            Typeface face5 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            local.setTypeface(face5);

            regional = view.findViewById(R.id.regional);
            Typeface face6 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf");
            regional.setTypeface(face6);

            saltwaterbtntext = view.findViewById(R.id.saltwaterbtntext);
            Typeface face7 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf");
            saltwaterbtntext.setTypeface(face7);

            freshwaterbtntext = view.findViewById(R.id.freshwaterbtntext);
            Typeface face8 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf");
            freshwaterbtntext.setTypeface(face8);

            filterguidetext = view.findViewById(R.id.filterguidetext);
            filterguidetext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            salttext = view.findViewById(R.id.salttext);
            salttext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            trouttext = view.findViewById(R.id.trouttext);
            trouttext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            speartext = view.findViewById(R.id.speartext);
            speartext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            safetext = view.findViewById(R.id.safetext);
            safetext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            regionaltext = view.findViewById(R.id.regionaltext);
            regionaltext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            // end change font

            dropdownbtn = view.findViewById(R.id.dropdownbtn);
            dropdownlayout = view.findViewById(R.id.dropdownlayout);

            dropdownbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!dropdownFlag)
                    {
                        if(!filterguidetext.getText().equals("Filter guides by..."))
                        {
                            filterguidetext.setText("Filter guides by...");

                            if(!waterflag && !troutflag && !spearflag && !localflag && !regionalflag)
                            {
                                waterlayout.setVisibility(View.GONE);
                                troutlayout.setVisibility(View.GONE);
                                spearlayout.setVisibility(View.GONE);
                                locallayout.setVisibility(View.GONE);
                                regionallayout.setVisibility(View.GONE);
                            }
                            else
                            {
                                waterlayout.setVisibility(View.VISIBLE);
                                troutlayout.setVisibility(View.VISIBLE);
                                spearlayout.setVisibility(View.VISIBLE);
                                locallayout.setVisibility(View.VISIBLE);
                                regionallayout.setVisibility(View.VISIBLE);
                            }

                            dropdownlayout.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            dropdownlayout.setVisibility(View.VISIBLE);
                            dropdownFlag = true;
                        }
                    }
                    else
                    {
                        dropdownlayout.setVisibility(View.GONE);
                        dropdownFlag = false;
                    }
                }
            });

            saltwaterbtn.setOnClickListener(listener);
            freshwaterbtn.setOnClickListener(listener);
            layoutsalt.setOnClickListener(listener);
            layouttrout.setOnClickListener(listener);
            layoutspear.setOnClickListener(listener);
            layoutsafe.setOnClickListener(listener);
            layoutregional.setOnClickListener(listener);
            mainlinearlayout.setOnClickListener(listener);
            rules.setOnClickListener(listener);

            return view;
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {

                if(view != null)
                {
                    update();
                }
            }
        }

        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.saltwaterbtn)
                {
                    ((RulesActivity)getActivity()).DisplaySaltWater();

                }else if (v.getId() == R.id.freshwaterbtn)
                {
                    ((RulesActivity)getActivity()).DisplayFreshWater();

                }else if (v.getId() == R.id.layoutsalt)
                {
                    if(waterflag == true)
                        waterlayout.setVisibility(View.VISIBLE);
                    else
                        waterlayout.setVisibility(View.GONE);

                    troutlayout.setVisibility(View.GONE);
                    spearlayout.setVisibility(View.GONE);
                    locallayout.setVisibility(View.GONE);
                    regionallayout.setVisibility(View.GONE);
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                    filterguidetext.setText("Salt and Freshwater Guides");


                }else if (v.getId() == R.id.layouttrout)
                {
                    waterlayout.setVisibility(View.GONE);

                    if(troutflag == true)
                        troutlayout.setVisibility(View.VISIBLE);
                    else
                        troutlayout.setVisibility(View.GONE);

                    spearlayout.setVisibility(View.GONE);
                    locallayout.setVisibility(View.GONE);
                    regionallayout.setVisibility(View.GONE);
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                    filterguidetext.setText("Trout Fishing Guides");

                }else if (v.getId() == R.id.layoutspear)
                {
                    waterlayout.setVisibility(View.GONE);
                    troutlayout.setVisibility(View.GONE);

                    if(spearflag == true)
                        spearlayout.setVisibility(View.VISIBLE);
                    else
                        spearlayout.setVisibility(View.GONE);

                    locallayout.setVisibility(View.GONE);
                    regionallayout.setVisibility(View.GONE);
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                    filterguidetext.setText("Spear Fishing Guides");

                }else if (v.getId() == R.id.layoutsafe)
                {
                    waterlayout.setVisibility(View.GONE);
                    troutlayout.setVisibility(View.GONE);
                    spearlayout.setVisibility(View.GONE);

                    if(localflag == true)
                        locallayout.setVisibility(View.VISIBLE);
                    else
                        locallayout.setVisibility(View.GONE);

                    regionallayout.setVisibility(View.GONE);
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                    filterguidetext.setText("Safe Fishing Guides");

                }else if (v.getId() == R.id.layoutregional)
                {
                    waterlayout.setVisibility(View.GONE);
                    troutlayout.setVisibility(View.GONE);
                    spearlayout.setVisibility(View.GONE);
                    locallayout.setVisibility(View.GONE);

                    if(regionalflag == true)
                        regionallayout.setVisibility(View.VISIBLE);
                    else
                        regionallayout.setVisibility(View.GONE);

                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                    filterguidetext.setText("Regional Fishing Guides");

                }else if (v.getId() == R.id.mainlinearlayout)
                {
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                }
                else if (v.getId() == R.id.rules)
                {
                    dropdownlayout.setVisibility(View.GONE);
                    dropdownFlag = false;
                }
            }
        };

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        }

        @Override
        public void onResume() {
            super.onResume();

        }

        public void update() {

            // start save

            waterdynamiclayout.removeAllViews();
            troutdynamiclayout.removeAllViews();
            speardynamiclayout.removeAllViews();
            localdynamiclayout.removeAllViews();
            regionaldynamiclayout.removeAllViews();
            SaveList.clear();

            FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            String[] columes = {

                    RuleSaveEntry.ID,
                    RuleSaveEntry.FISHTYPEID,
                    RuleSaveEntry.TITLE,
                    RuleSaveEntry.TYPE,
                    RuleSaveEntry.DESCRIPTION,
                    RuleSaveEntry.PDFURL
            };

            Cursor cursor = db.query(RuleSaveEntry.TABLE_NAME, columes, null, null, null, null  , "_id");

            while (cursor.moveToNext()) {

                SaveModel savemodel = new SaveModel();

                int indexOfTypeId = cursor.getColumnIndex(RuleSaveEntry.FISHTYPEID);
                savemodel.setId(cursor.getString(indexOfTypeId));

                int indexOfType = cursor.getColumnIndex(RuleSaveEntry.TYPE);
                savemodel.setType(cursor.getString(indexOfType));


                int indexOfTitle = cursor.getColumnIndex(RuleSaveEntry.TITLE);
                savemodel.setTitle(cursor.getString(indexOfTitle));

                int indexOfDesc = cursor.getColumnIndex(RuleSaveEntry.DESCRIPTION);
                savemodel.setDesc(cursor.getString(indexOfDesc));

                int indexOfUrl = cursor.getColumnIndex(RuleSaveEntry.PDFURL);
                savemodel.setPdfurl(cursor.getString(indexOfUrl));

                SaveList.add(savemodel);
            }

            cursor.close();

            if (db != null && db.isOpen())
                db.close();

            if(SaveList != null && SaveList.size() != 0)
            {
                verginlayout.setVisibility(View.GONE);

                for(int i=0; i<SaveList.size(); i++)
                {
                    if(SaveList.get(i).getType().equals("SALTWATERANDFRESHWATER"))
                    {
                        waterflag = true;
                        waterlayout.setVisibility(View.VISIBLE);
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulessave_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(SaveList.get(i).getTitle());
                        rulesdesc.setText(SaveList.get(i).getDesc());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String finalPdfurl = SaveList.get(i).getPdfurl();
                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", finalPdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start saving

                        final String finalTypeid = SaveList.get(i).getId();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (savebtn.isChecked()) {
                                    savebtn.setSelected(true);

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                        Log.d("DELETED", "ID : " + finalTypeid);

                                        layoutWaterFlag = true;

                                        setUserVisibleHint(true);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                } else {
                                    savebtn.setSelected(false);

                                }

                            }
                        });

                        // end saving

                        waterdynamiclayout.addView(child);
                    }

                    if(SaveList.get(i).getType().equals("TROUTFISHING"))
                    {
                        troutflag = true;
                        troutlayout.setVisibility(View.VISIBLE);
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulessave_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(SaveList.get(i).getTitle());
                        rulesdesc.setText(SaveList.get(i).getDesc());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(!dropdownFlag[0])
                                {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                }
                                else
                                {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });


                        final String finalPdfurl = SaveList.get(i).getPdfurl();
                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", finalPdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start saving

                        final String finalTypeid = SaveList.get(i).getId();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (savebtn.isChecked()) {
                                    savebtn.setSelected(true);

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                        Log.d("DELETED", "ID : " + finalTypeid);

                                        layoutTroutFlag = true;

                                        setUserVisibleHint(true);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                } else {
                                    savebtn.setSelected(false);

                                }

                            }
                        });

                        // end saving

                        troutdynamiclayout.addView(child);

                    }

                    if(SaveList.get(i).getType().equals("SPEARFISHING"))
                    {
                        spearflag = true;
                        spearlayout.setVisibility(View.VISIBLE);
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulessave_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(SaveList.get(i).getTitle());
                        rulesdesc.setText(SaveList.get(i).getDesc());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String finalPdfurl = SaveList.get(i).getPdfurl();
                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", finalPdfurl);
                                startActivity(intent);

                            }
                        });

                        // start saving

                        final String finalTypeid = SaveList.get(i).getId();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (savebtn.isChecked()) {
                                    savebtn.setSelected(true);

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                        Log.d("DELETED", "ID : " + finalTypeid);

                                        layoutSpearFlag = true;

                                        setUserVisibleHint(true);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                } else {
                                    savebtn.setSelected(false);

                                }

                            }
                        });

                        // end saving

                        speardynamiclayout.addView(child);
                    }
                    if(SaveList.get(i).getType().equals("SAFEFISHING")) {

                        localflag = true;
                        locallayout.setVisibility(View.VISIBLE);
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulessave_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(SaveList.get(i).getTitle());
                        rulesdesc.setText(SaveList.get(i).getDesc());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String finalPdfurl = SaveList.get(i).getPdfurl();
                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", finalPdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start saving

                        final String finalTypeid = SaveList.get(i).getId();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (savebtn.isChecked()) {
                                    savebtn.setSelected(true);

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                        Log.d("DELETED", "ID : " + finalTypeid);

                                        layoutLocalFlag = true;

                                        setUserVisibleHint(true);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                } else {
                                    savebtn.setSelected(false);

                                }
                            }
                        });

                        // end saving

                        localdynamiclayout.addView(child);

                    }

                    if(SaveList.get(i).getType().equals("REGIONALFISHING")) {

                        regionalflag = true;
                        regionallayout.setVisibility(View.VISIBLE);
                        View child = getActivity().getLayoutInflater().inflate(R.layout.rulessave_item, null);

                        TextView rulename = child.findViewById(R.id.rulename);
                        TextView rulesdesc = child.findViewById(R.id.rulesdesc);
                        final ToggleButton savebtn = child.findViewById(R.id.savebtn);
                        Button viewguidebtn = child.findViewById(R.id.viewguidebtn);
                        final ImageView arrow = child.findViewById(R.id.arrow);

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final Boolean[] dropdownFlag = {false};

                        rulename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        rulesdesc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        viewguidebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        rulename.setText(SaveList.get(i).getTitle());
                        rulesdesc.setText(SaveList.get(i).getDesc());

                        mainlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (!dropdownFlag[0]) {
                                    arrow.setBackgroundResource(R.drawable.uparrow);
                                    dropdownlayout.setVisibility(View.VISIBLE);
                                    dropdownFlag[0] = true;
                                } else {
                                    arrow.setBackgroundResource(R.drawable.downarrow);
                                    dropdownlayout.setVisibility(View.GONE);
                                    dropdownFlag[0] = false;
                                }

                            }
                        });

                        final String finalPdfurl = SaveList.get(i).getPdfurl();
                        viewguidebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PdfActivity.class);
                                intent.putExtra("fs", finalPdfurl);
                                intent.putExtra("share", true);
                                startActivity(intent);

                            }
                        });

                        // start saving

                        final String finalTypeid = SaveList.get(i).getId();

                        savebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (savebtn.isChecked()) {
                                    savebtn.setSelected(true);

                                    // save data
                                    try {

                                        FishSmartDBHelper dbHelper = new FishSmartDBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        db.delete(RuleSaveEntry.TABLE_NAME, RuleSaveEntry.FISHTYPEID + "=" + finalTypeid, null);
                                        Log.d("DELETED", "ID : " + finalTypeid);

                                        layoutRegionalFlag = true;

                                        setUserVisibleHint(true);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // end save data
                                } else {
                                    savebtn.setSelected(false);

                                }
                            }
                        });

                        // end saving

                        regionaldynamiclayout.addView(child);
                    }
                }
            }
            else
            {
                verginlayout.setVisibility(View.VISIBLE);

                waterlayout.setVisibility(View.GONE);
                troutlayout.setVisibility(View.GONE);
                spearlayout.setVisibility(View.GONE);
                locallayout.setVisibility(View.GONE);
                regionallayout.setVisibility(View.GONE);
                dropdownlayout.setVisibility(View.GONE);

                waterflag = false;
                troutflag = false;
                spearflag = false;
                localflag = false;
                regionalflag = false;
            }

            // end save1

        }
    }

    private void setUserData(final UserBean userBean) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (userBean.image.size() > 0) {
                    Picasso.get()
                            .load(userBean.image.get(0).url)
                            .into(imgUser);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}