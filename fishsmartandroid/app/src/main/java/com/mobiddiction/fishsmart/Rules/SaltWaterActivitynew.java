package com.mobiddiction.fishsmart.Rules;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.R;

/**
 * Created by Archa on 17/05/2016.
 */
public class SaltWaterActivitynew extends BaseTabActivity implements androidx.appcompat.app.ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    static ViewPager mViewPager;
    TrapFragment trapfragment;
    TabLayout tab;
    ImageButton btn_slide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState/*, R.layout.activity_saltwater_new, false*/);
        setContentView(R.layout.activity_saltwater_new);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_header));

        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        btn_slide = mActionBarView.findViewById(R.id.btn_slide);
        btn_slide.setVisibility(View.GONE);
        ImageView notificationBtn = mActionBarView.findViewById(R.id.imgNotification);
        notificationBtn.setVisibility(View.GONE);
        ImageView backbtn = mActionBarView.findViewById(R.id.btnBack);
        backbtn.setVisibility(View.VISIBLE);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView nav_title = mActionBarView.findViewById(R.id.nav_title);
        nav_title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        nav_title.setText("SALTWATER RULES");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        tab = findViewById(R.id.tab);
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setBackgroundColor(Color.WHITE);




        tab.addTab(tab.newTab().setText("Summary"));
        tab.addTab(tab.newTab().setText("Gear Rules"));

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
//        ActionBar.Tab summarytab = getSupportActionBar().newTab().setText("Summary").setTabListener(this);
//        ActionBar.Tab traptab = getSupportActionBar().newTab().setText("Gear Rules").setTabListener(this);

//        Typeface typeface = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
//
//        ViewGroup vg = (ViewGroup) tab.getChildAt(0);
//        int tabsCount = vg.getChildCount();
//        for (int j = 0; j < tabsCount; j++) {
//            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
//            int tabChildsCount = vgTab.getChildCount();
//            for (int i = 0; i < tabChildsCount; i++) {
//                View tabViewChild = vgTab.getChildAt(i);
//                if (tabViewChild instanceof TextView) {
//                    ((TextView) tabViewChild).setTypeface(typeface, Typeface.NORMAL);
//                }
//            }
//        }
//        getSupportActionBar().addTab(summarytab);
//        getSupportActionBar().addTab(traptab);
//
//        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//
//                getSupportActionBar().setSelectedNavigationItem(position);
//            }
//
//        });
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

        trapfragment = new TrapFragment();
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
                    return new SummaryFragment();
                case 1:
                    return trapfragment;
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

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public static class SummaryFragment extends Fragment{

        TextView summary, gang, must, listtext, tapontext, geartype, text1, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11, text12, text13, text14, text15, text16, text17, text18, text19, text20, paratext;

        ImageButton backbtn;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_saltwater_summary, container, false);

            summary = v.findViewById(R.id.summary);
            summary.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            gang = v.findViewById(R.id.gang);
            gang.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            must = v.findViewById(R.id.must);
            Typeface face19 = Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf");
            must.setTypeface(face19);

            paratext = v.findViewById(R.id.paratext);
            paratext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            geartype = v.findViewById(R.id.geartype);
            geartype.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            listtext = v.findViewById(R.id.listtext);
            listtext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            tapontext = v.findViewById(R.id.tapontext);
            tapontext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text1 = v.findViewById(R.id.text1);
            text1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text2 = v.findViewById(R.id.text2);
            text2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text3 = v.findViewById(R.id.text3);
            text3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text4 = v.findViewById(R.id.text4);
            text4.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text5 = v.findViewById(R.id.text5);
            text5.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text6 = v.findViewById(R.id.text6);
            text6.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text7 = v.findViewById(R.id.text7);
            text7.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text8 = v.findViewById(R.id.text8);
            text8.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text9 = v.findViewById(R.id.text9);
            text9.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text10 = v.findViewById(R.id.text10);
            text10.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text11 = v.findViewById(R.id.text11);
            text11.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text12 = v.findViewById(R.id.text12);
            text12.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text13 = v.findViewById(R.id.text13);
            text13.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text14 = v.findViewById(R.id.text14);
            text14.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text15 = v.findViewById(R.id.text15);
            text15.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text16 = v.findViewById(R.id.text16);
            text16.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text17 = v.findViewById(R.id.text17);
            text17.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text18 = v.findViewById(R.id.text18);
            text18.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text19 = v.findViewById(R.id.text19);
            text19.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text20 = v.findViewById(R.id.text20);
            text20.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));


            listtext.setText(Html.fromHtml("<br>&#8226;   Recreational catch cannot be sold.<br><br>" +
                    "&#8226;   Cast nets not permitted for use in NSW.<br><br>" +
                    "&#8226;   Interference with commercial fishing gear, oyster leases or other gear is not permitted.<br><br>" +
                    "&#8226;   Spare lines should not be rigged and be properly stowed.<br><br>" +
                    "&#8226;   Jagging fish not permitted.<br><br>" +
                    "&#8226;   Pipis can only be taken for use as bait and cannot be taken more than 50m from the high tide water mark.<br><br>" +
                    "&#8226;   Lobsters and crabs with eggs must be released.<br><br>" +
                    "&#8226;   You must not alter a fish subject to a size limit other than by gutting, gilling and scaling until you are well away from the water. This rule does not apply at areas normally used for cleaning fish such as at  fish cleaning tables,  if the fish are for immediate consumption,  immediate use as bait or for fish that don’t have a legal length.<br>"));

            tapontext.setText(Html.fromHtml("Tap on the <font color=#df8871>trap titles</font> below to see more information and the rules that apply to them."));

            //  backbtn.setOnClickListener(listener);
            text7.setOnClickListener(listener);
            text9.setOnClickListener(listener);
            text11.setOnClickListener(listener);
            text13.setOnClickListener(listener);
            text15.setOnClickListener(listener);
            text17.setOnClickListener(listener);
            text19.setOnClickListener(listener);
            return v;
        }

        View.OnClickListener listener = new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.text7) {

                    ((SaltWaterActivitynew)getActivity()).trapfragment.scrollValue = 1;
                    mViewPager.setCurrentItem(1);

                }else if (v.getId() == R.id.text9) {

                    ((SaltWaterActivitynew)getActivity()).trapfragment.scrollValue = 2;
                    mViewPager.setCurrentItem(1);

                }else if (v.getId() == R.id.text11) {

                    ((SaltWaterActivitynew)getActivity()).trapfragment.scrollValue = 3;
                    mViewPager.setCurrentItem(1);

                }else if (v.getId() == R.id.text13) {

                    ((SaltWaterActivitynew)getActivity()).trapfragment.scrollValue = 4;
                    mViewPager.setCurrentItem(1);

                }else if (v.getId() == R.id.text15) {

                    ((SaltWaterActivitynew)getActivity()).trapfragment.scrollValue = 5;
                    mViewPager.setCurrentItem(1);

                }else if (v.getId() == R.id.text17) {

                    ((SaltWaterActivitynew)getActivity()).trapfragment.scrollValue = 6;
                    mViewPager.setCurrentItem(1);

                }else if (v.getId() == R.id.text19) {

                    ((SaltWaterActivitynew)getActivity()).trapfragment.scrollValue = 7;
                    mViewPager.setCurrentItem(1);

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

    }

    public static class TrapFragment extends Fragment{

        int scrollValue;
        TextView rulestext1, rulestext2, rulestext3, rulestext4, rulestext5, rulestext6, rulestext7, rulestext8, baittrap, hoopnet, crabtrap, spanner, hand, scissor, dip, text21, text22, text23, text24, text25, text26, text27, text28;
        ScrollView trapview;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_saltwater_traprules, container, false);

            trapview = v.findViewById(R.id.traprulesscrollview);

            dip = v.findViewById(R.id.dip);
            dip.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            scissor = v.findViewById(R.id.scissor);
            scissor.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            hand = v.findViewById(R.id.hand);
            hand.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            spanner = v.findViewById(R.id.spanner);
            spanner.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            crabtrap = v.findViewById(R.id.crabtrap);
            crabtrap.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            baittrap = v.findViewById(R.id.baittrap);
            baittrap.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            hoopnet = v.findViewById(R.id.hoopnet);
            hoopnet.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            rulestext1 = v.findViewById(R.id.ruleslisttext1);
            rulestext1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            rulestext2 = v.findViewById(R.id.ruleslisttext2);
            rulestext2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            rulestext3 = v.findViewById(R.id.ruleslisttext3);
            rulestext3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            rulestext4 = v.findViewById(R.id.ruleslisttext4);
            rulestext4.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            rulestext5 = v.findViewById(R.id.ruleslisttext5);
            rulestext5.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            rulestext6 = v.findViewById(R.id.ruleslisttext6);
            rulestext6.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            rulestext7 = v.findViewById(R.id.ruleslisttext7);
            rulestext7.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            rulestext8 = v.findViewById(R.id.ruleslisttext8);
            rulestext8.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text21 = v.findViewById(R.id.text21);
            text21.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text22 = v.findViewById(R.id.text22);
            text22.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text23 = v.findViewById(R.id.text23);
            text23.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text24 = v.findViewById(R.id.text24);
            text24.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text25 = v.findViewById(R.id.text25);
            text25.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text26 = v.findViewById(R.id.text26);
            text26.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text27 = v.findViewById(R.id.text27);
            text27.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text28 = v.findViewById(R.id.text28);
            text28.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            // trap
            rulestext1.setText(Html.fromHtml("<br>&#8226;   You can only use or have in possession one trap at any one time.<br><br>" +
                    "&#8226;   The maximum dimensions of a bait trap are 450mm length x 350mm diameter with entrance funnel no larger than 60mm in diameter.<br><br>" +
                    "&#8226;   The trap must have a tag attached to a part of the trap which is at or above water level with dimensions not less than 80mm x 45mm,with BT, initial and surname, year of birth (YOB) and postcode of the person who sets, uses or lifts the trap, all letters to be a minimum of 15mm and in a colour contrasting to the tag.<br>"));

            rulestext2.setText(Html.fromHtml("<br>&#8226;   Not more than 4 nets are to be used (or in possession) by any one person at any one time.<br><br>" +
                    "&#8226;   1 or 2 hoops per net (no rigid frame between them).<br><br>" +
                    "&#8226;   Diameter of hoops should be no greater than 1.25 metres.<br><br>" +
                    "&#8226;   Mesh size cannot be less than 13mm measured across the diagonal.<br><br>" +
                    "&#8226;   Drop (length of net) should be no more than 1 metre.<br><br>" +
                    "&#8226;   A float/ buoy to be labelled with HN, initial and surname, year of birth (YOB) and postcode of the person who sets, uses or lifts the fishing gear. The minimum height of the float being at least 50mm above the water with all letters to be a minimum of 15mm and in a colour contrasting to the buoy.<br><br>" +
                    "&#8226;   The float/ buoy must measure not less than 100mm in all dimensions.<br><br>" +
                    "&#8226;   Hoop nets or lift nets must not have any rope floating on the surface of the water.<br><br>" +
                    "&#8226;   The net must be dropped and raised vertically through the water by hand.<br><br>" +
                    "&#8226;   The net must not be used in ocean waters.<br><br>" +
                    "&#8226;   Any rock lobsters or fin-fish (which are subject to a size limit) must be immediately returned to the water unharmed if caught.<br><br>" +
                    "&#8226;   Do not set gear in areas of high boat traffic or navigation channels.<br>"));

            rulestext3.setText(Html.fromHtml("<br>&#8226;   Not more than 2 traps to be used (or in possession) by any person at any one time.<br><br>" +
                    "&#8226;   Maximum dimensions - 1.2 metres (length) x 1 metre (width) x 0.5 metre (depth) or has a diameter not exceeding 1.6 metres at the top or bottom.<br><br>" +
                    "&#8226;   Minimum mesh size - 50mm.<br><br>" +
                    "&#8226;   No more than 4 entrances (none of which are on the top of the trap).<br><br>" +
                    "&#8226;   A float/ buoy to be labelled with CT, initial and surname, year of birth (YOB) and postcode of the person who sets, uses or lifts the fishing gear. The minimum height of the float being at least 50mm above the water with all letters to be a minimum of 15mm and in a colour contrasting to the buoy. There must also be a 50 gram weight attached to the float/buoy line so that no line is floating on the surface of the water.<br><br>" +
                    "&#8226;   The float/ buoy must measure not less than 100mm in all dimensions.<br><br>" +
                    "&#8226;   Must not be made of entanglement material.<br><br>" +
                    "&#8226;   Must not be set to impede the free passage of fish.<br><br>" +
                    "&#8226;   Any fish caught (other than crabs) must be returned to the water.<br><br>" +
                    "&#8226;   Do not set gear in areas of high boat traffic or navigation channels.<br>"));

            rulestext4.setText(Html.fromHtml("<br>&#8226;   Not more than one trap is to be used (or in possession) by any person at any time.<br><br>" +
                    "&#8226;   The base or floor of the trap may be either rectangular or circular.<br><br>" +
                    "&#8226;   Rectangular not exceeding 1.2 metres by 1.2 metres.<br><br>" +
                    "&#8226;   Circular not exceeding 1.2 metres in diameter.<br><br>" +
                    "&#8226;   A float/buoy to be labelled with LT, initial and surname, year of birth (YOB) and postcode of the person who sets, uses or lifts the fishing gear. The float being at least 100 mm in diameter and 50 mm above the water with all letters to be a minimum of 15 mm and in a colour contrasting to the buoy. There must also be a 50 gram weight attached no less than one metre below the buoy so that no rope/line is floating on the surface of the water.<br><br>" +
                    "&#8226;   Escape gaps are required. Either: One gap not less than 57 mm high x 500 mm wide; or two gaps not less than 57 mm wide x 250 mm wide; or three gaps not less than 57 mm high x 200 mm wide so that no part of any escape gap is more than 12 cm above the floor of the trap.<br><br>" +
                    "&#8226;   Lobster traps must not be used in inland waters or any waters more than 10 m deep (contour).<br><br>" +
                    "&#8226;   Only Rocklobsters can be taken with this trap.<br><br>" +
                    "&#8226;   Do not set gear in areas of high boat traffic or navigation channels.<br>"));

            rulestext5.setText(Html.fromHtml("<br>&#8226;   Not more than 1 spanner crab net can be used by a person at any one time.<br><br>" +
                    "&#8226;   Net must be attached to a rigid frame not exceeding 1.6 metres in length and 1 metre in width.<br><br>" +
                    "&#8226;   Net must not be capable of extending more than 0.1 metre beneath the frame when the frame is suspended in a horizontal position.<br><br>" +
                    "&#8226;   The net must be lowered and raised through the water only by hand.<br><br>" +
                    "&#8226;   Only to be used for taking of spanner crabs.<br><br>" +
                    "&#8226;   May only be used in ocean waters north of Korogoro Point (Hat Head).<br><br>" +
                    "&#8226;   A float/ buoy to be labelled with SN, initial and surname, year of birth (YOB) and postcode of the person who sets, uses or lifts the fishing gear. The minimum height of the float being at least 50mm above the water with all letters to be a minimum of 15mm and in a colour contrasting to the buoy. There must also be a 50 gram weight attached to the float/buoy line so that no line is floating on the surface of the water.<br><br>" +
                    "&#8226;   The float/ buoy must measure not less than 100mm in all dimensions.<br>"));

            rulestext6.setText(Html.fromHtml("<br>&#8226;   Maximum length of 6 metres.<br><br>" +
                    "&#8226;   Mesh size between 30mm-36mm measured across the diagonal.<br><br>" +
                    "&#8226;   Must not be staked or set, or joined or placed with any other net.<br><br>" +
                    "&#8226;   Must be continuously and manually pulled through the water and not used as a stationary net.<br><br>" +
                    "&#8226;   Hauling lines of up to a maximum of 2 metres in length may be used.<br><br>" +
                    "&#8226;   Any fish caught which are subject to a size limit must be immediately returned to the water unharmed.<br>"));

            rulestext7.setText(Html.fromHtml("<br>&#8226;   Must be attached to a scissor-type frame<br><br>" +
                    "&#8226;   Length of the lead or bottom line between the ends of poles must be no longer than 2.75 metres.<br><br>" +
                    "&#8226;   Mesh size between 30mm-36mm measured across the diagonal.<br><br>" +
                    "&#8226;   Must not be staked or set, or joined or placed with any other net.<br><br>" +
                    "&#8226;   Must be continuously and manually propelled and not used as a stationary net.<br><br>" +
                    "&#8226;   Must be operated by one person only.<br><br>" +
                    "&#8226;   Only one net per person is permitted at any time.<br><br>" +
                    "&#8226;   Any fish caught which are subject to a size limit must be immediately returned to the water unharmed.<br>"));

            rulestext8.setText(Html.fromHtml("<br>&#8226;   Maximum diameter of hoop or ring 0.6 metres.<br><br>" +
                    "&#8226;   Minimum mesh size 20mm measured across the diagonal.<br><br>" +
                    "&#8226;   Drop (length of net) no more than 1.25 metres.<br><br>" +
                    "&#8226;   Must be used by hand and not staked or set.<br><br>" +
                    "&#8226;   Must not be joined or placed with any other net.<br><br>" +
                    "&#8226;   Only one net per person at any time.<br><br>" +
                    "&#8226;   Other fish caught while fishing for prawns may be kept, however, bag and size limits apply.<br>"));

            // end trap


            return v;
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
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {

                if(scrollValue == 1)
                {
                    trapview.scrollTo(0, baittrap.getTop());
                }
                else if(scrollValue == 2)
                {
                    trapview.scrollTo(0, hoopnet.getTop());
                }
                else if(scrollValue == 3)
                {
                    trapview.scrollTo(0, crabtrap.getTop());

                }
                else if(scrollValue == 4)
                {
                    trapview.scrollTo(0, spanner.getTop());
                }
                else if(scrollValue == 5)
                {
                    trapview.scrollTo(0, hand.getTop());
                }
                else if(scrollValue == 6)
                {
                    trapview.scrollTo(0, scissor.getTop());
                }
                else if(scrollValue == 7)
                {
                    trapview.scrollTo(0, dip.getTop());
                }

            }

        }

    }
}