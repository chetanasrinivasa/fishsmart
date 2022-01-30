package com.mobiddiction.fishsmart.More;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.MyApplication;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.ScreenEnum;
import com.mobiddiction.fishsmart.WebviewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by Archa on 10/05/2016.
 */
public class MoreActivity extends BaseTabActivity implements androidx.appcompat.app.ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    public static SharedPreferences pref;
    TabLayout tab;
    static ArrayList<MoreModel> MoreList = new ArrayList<>();
    private static boolean isFirstLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState/*, R.layout.activity_species_new, false*/);
        setContentView(R.layout.activity_species_new);

        HomeTextChanger("NEWS AND CONTACTS",this);

      //  new MoreAsync(MoreActivity.this, MoreList).execute("/api/contactAndLicensing");

        pref = getSharedPreferences("fishsmart", 0);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setBackgroundColor(Color.WHITE);


        tab = findViewById(R.id.tab);

        tab.addTab(tab.newTab().setText("News"));
        tab.addTab(tab.newTab().setText("Contact"));
        tab.addTab(tab.newTab().setText("Licensing"));

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
//        getSupportActionBar().setHomeButtonEnabled(false);
//        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        ActionBar.Tab newstab = getSupportActionBar().newTab().setText("News").setTabListener(this);
//        ActionBar.Tab contacttab = getSupportActionBar().newTab().setText("Contact").setTabListener(this);
//        ActionBar.Tab licensingtab = getSupportActionBar().newTab().setText("Licensing").setTabListener(this);
//
//        getSupportActionBar().addTab(newstab);
//        getSupportActionBar().addTab(contacttab);
//        getSupportActionBar().addTab(licensingtab);
//
//        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//
//                getSupportActionBar().setSelectedNavigationItem(position);
//                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0089a7")));
//            }
//
//        });

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
                    return new NewsFragment();
                case 1:
                    return new ContactFragment();
                case 2:
                    return new LicensingFragment();
            }

            return null;

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
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0089a7")));

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public static class NewsFragment extends Fragment{
        WebView webView;
        public Dialog dialog = null;
        static ArrayList<MoreModel> MoreList = new ArrayList<>();

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_news, container, false);
            //((MyApplication) getActivity().getApplication()).getTrackingManager().trackScreen(ScreenEnum.NEWS, null);
            FirebaseAnalytics.getInstance(getActivity().getApplicationContext()).setCurrentScreen(getActivity(),ScreenEnum.NEWS, null);
          //  new MoreAsync(NewsFragment.this, MoreList).execute("/api/contactAndLicensing");

            webView = view.findViewById(R.id.webView);

            webView.setWebViewClient(new MyWebViewClient());
            openURL("https://m.facebook.com/NSWDPIFisheries/");

            WebSettings set = webView.getSettings();
            set.setJavaScriptEnabled(true);

            return view;
        }

        private class MyWebViewClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        }

        private void openURL(String url) {
            webView.loadUrl(url);
            webView.requestFocus();
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {

                if(!pref.getBoolean("vergincallnews", false))
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

                                title.setText("News");
                                desc.setText("Check out our Facebook newsfeed Feed here.");

                                dialog = new Dialog(getActivity());

                                dialog.setCanceledOnTouchOutside(false);

                                thanksbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialog.dismiss();
                                        SharedPreferences.Editor edit = pref.edit();
                                        edit.putBoolean("vergincallnews", true);
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
    }

    public static class ContactFragment extends Fragment {
        LinearLayout reportdynamiclayout, contactdynamiclayout, visitdynamiclayout;
        TextView report, contact, visit;
        public Dialog dialog = null;
        ProgressBar progress_bar;

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Create a SharedPreferences object
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            isFirstLoad = sharedPreferences.getBoolean(getResources().getString(R.string.is_first_time_load), true);

            View view = inflater.inflate(R.layout.fragment_morecontact, container, false);
            //((MyApplication) getActivity().getApplication()).getTrackingManager().trackScreen(ScreenEnum.CONTACT, null);
            FirebaseAnalytics.getInstance(getActivity().getApplicationContext()).setCurrentScreen(getActivity(),ScreenEnum.CONTACT, null);

            progress_bar = view.findViewById(R.id.progress_bar);
            reportdynamiclayout = view.findViewById(R.id.reportdynamiclayout);
            contactdynamiclayout = view.findViewById(R.id.contactdynamiclayout);
            visitdynamiclayout = view.findViewById(R.id.visitdynamiclayout);

            report = view.findViewById(R.id.report);
            contact = view.findViewById(R.id.contact);
            visit = view.findViewById(R.id.visit);

            TextView disclaimerHeader = view.findViewById(R.id.disclaimer_header);
            TextView disclaimerTitle = view.findViewById(R.id.disclaimer_title);
            TextView disclaimerT1 = view.findViewById(R.id.desc1);
            TextView disclaimerT2 = view.findViewById(R.id.desc2);

            report.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));
            contact.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));
            visit.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));
            disclaimerHeader.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));
            disclaimerTitle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
            disclaimerT1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
            disclaimerT2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

//          new MoreContactAsync(ContactFragment.this, MoreList).execute("/api/contactAndLicensing");
            if (isFirstLoad) {
                isFirstLoad = false;
                NetworkManager.getInstance().getLicense();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getResources().getString(R.string.is_first_time_load), false);
                editor.apply();
            } else {
                receiveItems();
            }

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

                if(!pref.getBoolean("vergincallcontact", false))
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

                                title.setText("Contact");
                                desc.setText("This area gives you key contact information along with additional information on DPI.");

                                dialog = new Dialog(getActivity());

                                dialog.setCanceledOnTouchOutside(false);

                                thanksbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialog.dismiss();
                                        SharedPreferences.Editor edit = pref.edit();
                                        edit.putBoolean("vergincallcontact", true);
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
            if(event == BasicEvent.LICENCE_SUCCESS){
                receiveItems();
            }
        }

        public void receiveItems() {

            if(ModelManager.getInstance().getNewLicenceData() != null)
            {
                for(int j=0; j<ModelManager.getInstance().getNewLicenceData().size(); j++)
                {
                    if(ModelManager.getInstance().getNewLicenceData().get(j).getSubType().replaceAll("\\s+","").equals("REPORT"))
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.morecontact_item, null);

                        TextView title = child.findViewById(R.id.title);
                        TextView desc = child.findViewById(R.id.desc);
                        Button phonebtn = child.findViewById(R.id.phonebtn);
                        Button onlinebtn = child.findViewById(R.id.onlinebtn);
                        LinearLayout arrowlayout = child.findViewById(R.id.arrowlayout);
                        RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        dropdownlayout.setVisibility(View.VISIBLE);

                        arrowlayout.setVisibility(View.GONE);

                        if(ModelManager.getInstance().getNewLicenceData().get(j).getPhone().equals(""))
                        {
                            phonebtn.setVisibility(View.GONE);
                        }
                        else
                        {
                            phonebtn.setVisibility(View.VISIBLE);
                        }

                        if(ModelManager.getInstance().getNewLicenceData().get(j).getUrl().equals(""))
                        {
                            onlinebtn.setVisibility(View.GONE);
                        }
                        else
                        {
                            onlinebtn.setVisibility(View.VISIBLE);
                        }

                        final String phone = ModelManager.getInstance().getNewLicenceData().get(j).getPhone();
                        phonebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phone));
                                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(sIntent);
                            }
                        });

                        final String online = ModelManager.getInstance().getNewLicenceData().get(j).getUrl();
                        onlinebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                                intent.putExtra("fs", online);
                                intent.putExtra("share", false);
                                startActivity(intent);
                            }
                        });

                        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        phonebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        onlinebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        title.setText(ModelManager.getInstance().getNewLicenceData().get(j).getTitle());
                        desc.setText(ModelManager.getInstance().getNewLicenceData().get(j).getDescription());

                        reportdynamiclayout.addView(child);
                    }
                }

                for(int j=0; j<ModelManager.getInstance().getNewLicenceData().size(); j++)
                {
                    if(ModelManager.getInstance().getNewLicenceData().get(j).getSubType().replaceAll("\\s+","").equals("CONTACTUS"))
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.morecontact_item, null);

                        TextView title = child.findViewById(R.id.title);
                        TextView desc = child.findViewById(R.id.desc);
                        Button phonebtn = child.findViewById(R.id.phonebtn);
                        Button onlinebtn = child.findViewById(R.id.onlinebtn);
                        LinearLayout arrowlayout = child.findViewById(R.id.arrowlayout);
                        RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        dropdownlayout.setVisibility(View.VISIBLE);

                        arrowlayout.setVisibility(View.GONE);

                        if(ModelManager.getInstance().getNewLicenceData().get(j).getPhone().equals(""))
                        {
                            phonebtn.setVisibility(View.GONE);
                        }
                        else
                        {
                            phonebtn.setVisibility(View.VISIBLE);
                        }

                        if(ModelManager.getInstance().getNewLicenceData().get(j).getUrl().equals(""))
                        {
                            onlinebtn.setVisibility(View.GONE);
                        }
                        else
                        {
                            onlinebtn.setVisibility(View.VISIBLE);
                        }

                        final String phone = ModelManager.getInstance().getNewLicenceData().get(j).getPhone();
                        phonebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phone));
                                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(sIntent);
                            }
                        });

                        final String online = ModelManager.getInstance().getNewLicenceData().get(j).getUrl();
                        onlinebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                                intent.putExtra("fs", online);
                                intent.putExtra("share", false);
                                startActivity(intent);
                            }
                        });

                        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        phonebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        onlinebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        title.setText(ModelManager.getInstance().getNewLicenceData().get(j).getTitle());
                        desc.setText(ModelManager.getInstance().getNewLicenceData().get(j).getDescription());

                        contactdynamiclayout.addView(child);
                    }
                }

                for(int j=0; j<ModelManager.getInstance().getNewLicenceData().size(); j++)
                {
                    if(ModelManager.getInstance().getNewLicenceData().get(j).getSubType().replaceAll("\\s+","").equals("VISITAFISHERIESOFFICE"))
                    {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.morecontact_item, null);

                        TextView title = child.findViewById(R.id.title);
                        TextView desc = child.findViewById(R.id.desc);
                        Button phonebtn = child.findViewById(R.id.phonebtn);
                        Button onlinebtn = child.findViewById(R.id.onlinebtn);
                        onlinebtn.setText("Mobile");

                        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        phonebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        onlinebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        final RelativeLayout dropdownlayout = child.findViewById(R.id.dropdownlayout);
                        LinearLayout mainlayout = child.findViewById(R.id.mainlayout);
                        final ImageView arrow = child.findViewById(R.id.arrow);
                        final Boolean[] dropdownFlag = {false};

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

                        if(ModelManager.getInstance().getNewLicenceData().get(j).getPhone().equals(""))
                        {
                            phonebtn.setVisibility(View.GONE);
                        }
                        else
                        {
                            phonebtn.setVisibility(View.VISIBLE);
                        }

                        if(ModelManager.getInstance().getNewLicenceData().get(j).getMobile().equals(""))
                        {
                            onlinebtn.setVisibility(View.GONE);
                        }
                        else
                        {
                            onlinebtn.setVisibility(View.VISIBLE);
                        }

                        final String phone = ModelManager.getInstance().getNewLicenceData().get(j).getPhone();
                        phonebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phone));
                                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(sIntent);
                            }
                        });

                        final String mobile = ModelManager.getInstance().getNewLicenceData().get(j).getMobile();
                        onlinebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ mobile));
                                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(sIntent);
                            }
                        });

                        title.setText(ModelManager.getInstance().getNewLicenceData().get(j).getTitle());
                        desc.setText(ModelManager.getInstance().getNewLicenceData().get(j).getDescription());

                        visitdynamiclayout.addView(child);
                    }
                }
            }
        }
    }

    public static class LicensingFragment extends Fragment {

        TextView header, paytext, calltext, renewText;
        LinearLayout dynamiclayout, calllayout, paylayout, renewlayout;
        public Dialog dialog = null;
        TextView desc; // dynamiclayout desc

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_morelicnse, container, false);
            //((MyApplication) getActivity().getApplication()).getTrackingManager().trackScreen(ScreenEnum.LICENSING, null);
            FirebaseAnalytics.getInstance(getActivity().getApplicationContext()).setCurrentScreen(getActivity(),ScreenEnum.LICENSING, null);
            header = view.findViewById(R.id.header);
            paytext = view.findViewById(R.id.paytext);
            calltext = view.findViewById(R.id.calltext);
            renewText = view.findViewById(R.id.renewtext);
            dynamiclayout = view.findViewById(R.id.dynamiclayout);
            calllayout = view.findViewById(R.id.calllayout);
            paylayout = view.findViewById(R.id.paylayout);
            renewlayout = view.findViewById(R.id.renewlayout);

            header.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));
            paytext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
            calltext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
            renewText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            calllayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1300 369 365"));
                    sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(sIntent);
                }
            });

//            NetworkManager.getInstance().getLicense();
            //change for taking the user to browser instead of a webview inth app as done earlier
            paylayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /*Intent intent = new Intent(getActivity(), WebviewActivity.class);
                    intent.putExtra("fs", "https://www.onegov.nsw.gov.au//gls_portal/LicenceForm.mvc/NewApplication?formId=ecfa7925-86a8-411b-81e8-725d05491d2e&agencyID=23adb6eb-2c36-4169-b365-bf26ef2367f8");
                    intent.putExtra("share", false);
                    startActivity(intent);*/

                    //change for taking the user to browser instead of a webview inth app as done earlier
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getResources().getString(R.string.redirect_title))
                            .setMessage(getResources().getString(R.string.redirect_message))
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener (){

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setPositiveButton("Go ahead",new DialogInterface.OnClickListener (){

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent viewIntent =
                                            new Intent(Intent.ACTION_VIEW);
                                    viewIntent.setData(Uri.parse(getResources().getString(R.string.url_pay_online)));
                                    startActivity(viewIntent);
                                }
                            })
                            .setView(R.layout.layout_alertview)
                            .create()
                            .show();
                }
            });

            //change for taking the user to browser instead of a webview inth app as done earlier
            renewlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getResources().getString(R.string.redirect_title))
                            .setMessage(getResources().getString(R.string.redirect_message))
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener (){

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setPositiveButton("Go ahead",new DialogInterface.OnClickListener (){

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent viewIntent =
                                            new Intent(Intent.ACTION_VIEW);
                                    viewIntent.setData(Uri.parse(getResources().getString(R.string.url_renew_licence)));
                                    startActivity(viewIntent);
                                }
                            })
                            .setView(R.layout.layout_alertview)
                            .create()
                            .show();
                    /*Intent intent = new Intent(getActivity(), WebviewActivity.class);
                    intent.putExtra("fs", "https://www.onegov.nsw.gov.au/gls_portal/snsw/Renew");
                    intent.putExtra("share", false);
                    startActivity(intent);*/



                }
            });
            receivedItems();
//            receivedItems();

            return view;
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {

                if(desc != null && desc.getText().equals(""))
                {
                    Log.d("desc", "view is empty");
                    dynamiclayout.removeAllViews();
//                    receivedItems();
                }
                else
                {
                    Log.d("desc", "view is not empty");
                }

                if(!pref.getBoolean("vergincalllicense", false))
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

                                title.setText("Licensing");
                                desc.setText("You can pay the recreational fishing licence fee here, find out more about the licence fee and whether you may be exempt.");

                                dialog = new Dialog(getActivity());

                                dialog.setCanceledOnTouchOutside(false);

                                thanksbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialog.dismiss();
                                        SharedPreferences.Editor edit = pref.edit();
                                        edit.putBoolean("vergincalllicense", true);
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
            if(event == BasicEvent.LICENCE_SUCCESS){
                receivedItems();
            }
        }
        public void receivedItems()
        {
            for(int j = 0; j< ModelManager.getInstance().getNewLicenceNSWRECREATIONALLICENSEFEEData().size(); j++)
            {
                if(ModelManager.getInstance().getNewLicenceNSWRECREATIONALLICENSEFEEData().get(j).getSubType().replaceAll("\\s+","").equals("NSWRECREATIONALLICENSEFEE"))
                {
                    View child = getActivity().getLayoutInflater().inflate(R.layout.morelicense_item, null);

                    TextView title = child.findViewById(R.id.title);
                    desc = child.findViewById(R.id.desc);

                    title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                    desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                    title.setText(ModelManager.getInstance().getNewLicenceNSWRECREATIONALLICENSEFEEData().get(j).getTitle());
                    desc.setText(ModelManager.getInstance().getNewLicenceNSWRECREATIONALLICENSEFEEData().get(j).getDescription());

                    dynamiclayout.addView(child);
                }
            }
        }
    }
}