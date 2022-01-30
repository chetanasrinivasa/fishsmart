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
public class FreshWaterActivitynew extends BaseTabActivity implements androidx.appcompat.app.ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    static ViewPager mViewPager;
    TrapFragment trapfragment;
    TabLayout tab;
    ImageButton btn_slide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState/*, R.layout.activity_freshwater_new, false*/);
        setContentView(R.layout.activity_freshwater_new);

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
        nav_title.setText("FRESHWATER RULES");

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
//        ActionBar.Tab summarytab = getSupportActionBar().newTab().setText("Summary").setTabListener(this);
//        ActionBar.Tab traptab = getSupportActionBar().newTab().setText("Gear Rules").setTabListener(this);
//
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

        TextView listtext, fishingrules, geartype, text1, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11, text12, text13, text14, text15, text16, text17, text18, text19, text20, text21, text22;
        TextView tapontext, summary;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_freshwater_summary, container, false);

            summary = v.findViewById(R.id.summary);
            summary.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            fishingrules = v.findViewById(R.id.fishingrules);
            fishingrules.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

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

            text21 = v.findViewById(R.id.text21);
            text21.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            text22 = v.findViewById(R.id.text22);
            text22.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

            listtext.setText(Html.fromHtml("<br>&#8226;   Recreational catch cannot be sold.<br><br>" +
                    "&#8226;   Rods and handlines must be attended and be within 50m and in your line of sight.<br><br>" +
                    "&#8226;   Set lines, drop lines or jagging are not permitted.<br><br>" +
                    "&#8226;   Spare lines should not be rigged and be properly stowed.<br><br>" +
                    "&#8226;   Fish traps, gill nets, drum nets, crab nets etc. not permitted.<br><br>" +
                    "&#8226;   Gaffs are not permitted.<br><br>" +
                    "&#8226;   Frogs, live finfish, non-native fish, live birds and mammals and trout and salmon roe (eggs) cannot be used as bait. This includes non-native fish or parts thereof whether alive or dead, other than dead carp.<br><br>" +
                    "&#8226;   You must not alter a fish subject to a size limit other than by gutting, gilling and scaling until you are well away from the water. This rule does not apply at areas normally used for cleaning fish such as at fish cleaning tables, if the fish are for immediate consumption, immediate use as bait or for fish that don’t have a legal length ."));

            // start trap

            tapontext.setText(Html.fromHtml("Tap on the <font color=#df8871>trap titles</font> below to see more information and the rules that apply to them."));

          //  backbtn.setOnClickListener(listener);
            text15.setOnClickListener(listener);
            text17.setOnClickListener(listener);
            text19.setOnClickListener(listener);
            text21.setOnClickListener(listener);

            return v;
        }

        View.OnClickListener listener = new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.backbtn) {

                    getActivity().onBackPressed();

                }else if (v.getId() == R.id.text15) {

                    ((FreshWaterActivitynew)getActivity()).trapfragment.scrollValue = 1;
                    mViewPager.setCurrentItem(1);


                }else if (v.getId() == R.id.text17) {

                    ((FreshWaterActivitynew)getActivity()).trapfragment.scrollValue = 2;
                    mViewPager.setCurrentItem(1);


                }else if (v.getId() == R.id.text19) {

                    ((FreshWaterActivitynew)getActivity()).trapfragment.scrollValue = 3;
                    mViewPager.setCurrentItem(1);

                }else if (v.getId() == R.id.text21) {

                    ((FreshWaterActivitynew)getActivity()).trapfragment.scrollValue = 4;
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
        TextView rulestext1, rulestext2, rulestext3, rulestext4, rulestext5, hoop, shrimp, hand, yabby;
        ScrollView trapview;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_freshwater_traprules, container, false);

            trapview = v.findViewById(R.id.traprulesscrollview);

            hoop = v.findViewById(R.id.hoop);
            hoop.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            shrimp = v.findViewById(R.id.shrimp);
            shrimp.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            hand = v.findViewById(R.id.hand);
            hand.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));

            yabby = v.findViewById(R.id.yabby);
            yabby.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));


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

            rulestext1.setText(Html.fromHtml("<br>&#8226;   Maximum dimensions: Net attached with not more than 2 hoops, rings or frames attached to the net, but not attached to each other by any rigid frame. Drop of net (inclusive of hoops, rings or frames) not more than 1m. Greatest diameter or diagonal of the hoops, rings or frames not exceeding 1.25m.<br><br>" +
                    "&#8226;   Mesh size: Mesh not less than 13mm.<br><br>" +
                    "&#8226;   Maximum number: Unless otherwise specified, five nets may be set, used or in your possession.<br><br>" +
                    "&#8226;   Identification: The buoy must be positioned above the water with the letters HN, initial and surname, year of birth and postcode, 15mm in height, clearly visible and in a contrasting colour to the buoy. Any rope attached to the buoy must not be floating on the surface of the water.<br><br>" +
                    "&#8226;   Maximum set time: Cannot be left set for more than 24 hours.<br><br>" +
                    "&#8226;   Waters: May be used in inland waters to take Crayfish and Yabbies except closed waters and trout waters (other than using up to 5 hoop or lift nets to take Yabbies in Googong Dam and in Lakes Lyell, Wallace, Eucumbene or Jindabyne).<br><br>" +
                    "&#8226;   The net is used only as a hand implement and only by the method of lowering into the water and then drawing the net vertically to the surface."));

            rulestext2.setText(Html.fromHtml("<br>&#8226;   Maximum dimensions: 0.6m x 0.5m x 0.5m.<br><br>" +
                    "&#8226;   Mesh size: 13mm maximum measured across the diagonal.<br><br>" +
                    "&#8226;   Entrance funnels: Inner hole maximum width of 35mm.<br><br>" +
                    "&#8226;   Maximum number: One (1) trap may be set, used and in your possession.<br><br>" +
                    "&#8226;   Identification: Must have a tag with dimensions of at least 80mm by 45mm attached to the trap at or above the water level which clearly displays the letters ST, initial and surname, year of birth and postcode, 15mm in height, clearly visible and in a contrasting colour.<br><br>" +
                    "&#8226;   Maximum set time: Cannot be left set for more than 24 hours.<br><br>" +
                    "&#8226;   Waters: May be used in inland waters to take freshwater Shrimp and Yabbies, except closed areas and trout waters."));

            rulestext3.setText(Html.fromHtml("<br>&#8226;   Maximum dimension: Up to 6m in length measured along the headline.<br><br>" +
                    "&#8226;   Mesh size: 40mm maximum measured across the diagonal.<br><br>" +
                    "&#8226;   Maximum number of users: Propelled by one (1) person. One (1) other person may assist in the operation of the net. May be used with or without hauling lines or poles.<br><br>" +
                    "&#8226;   Maximum set time: Zero. Must be continuously and manually propelled. Not to be set, staked or joined with any other net.<br><br>" +
                    "&#8226;   Waters: May only be used to take Yabbies in inland waters, being ground tanks, bore drains or lagoons. Not permitted in any river or public dam, or in closed areas or trout waters.<br><br>" +
                    "&#8226;   Must be used as a hand implement only."));

            rulestext4.setText(Html.fromHtml("<br>&#8226;   Maximum dimensions: 1m length x 0.6m width x 0.3m depth.<br><br>" +
                    "&#8226;   Netting or mesh size: 13mm minimum measured across the stretched diagonal from knot to knot. Rigid mesh such as metal or hard plastic is prohibited.<br><br>" +
                    "&#8226;   Entrance funnels: A bycatch reduction device must be fitted to all entrance funnels. The device must consist of a rigid ring with a maximum internal diameter of 90mm permanently affixed to entry funnels at some point along their length, so as to restrict the entry funnels to a maximum opening of 90mm measured in any direction. This is to protect platypus, turtles and birds from entering the trap in search of food and getting caught in the trap.<br><br>" +
                    "&#8226;   Maximum number: Unless otherwise specified, up to five traps may be set, used or in your possession.<br><br>" +
                    "&#8226;   Maximum set time: Cannot be left set for more than 24 hours.<br><br>" +
                    "&#8226;   Identification: Position of the trap located by a buoy or tag. If the trap is identified by a buoy, the buoy must be positioned above the trap and measure not less than 100mm in all dimensions and must be 50mm above the water. Any rope attached to the buoy must not be floating on the surface of the water. If the trap is identified by a tag, the tag must be attached to the trap at or above the water level and have dimensions of at least 80mm x 45mm. Identification tags and buoys must clearly display the letters YT, initial and surname, year of birth and postcode of the person using the trap, 15mm in height, clearly visible and in a contrasting colour.<br>"));

            rulestext5.setText(Html.fromHtml("Waters: May be used in inland waters to take Yabbies and Freshwater Shrimp, except trout waters and closed waters, plus the following areas where platypus are found:<br><br> 1.   Waters east of the Newell Highway (except private farm dams).<br><br>" +
                    " 2.   The Murray River from the Newell Highway at Tocumwal downstream to the Echuca Road Bridge.<br><br>" +
                    " 3.   The Edward River from the Murray River at Picnic Point downstream to Stevens Weir.<br><br>" +
                    " 4.   The Murrumbidgee River from Narrandera downstream to the Darlington Point Road Bridge<br>"));


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
                    trapview.scrollTo(0, hoop.getTop());
                }
                else if(scrollValue == 2)
                {
                    trapview.scrollTo(0, shrimp.getTop());
                }
                else if(scrollValue == 3)
                {
                    trapview.scrollTo(0, hand.getTop());
                }
                else if(scrollValue == 4)
                {
                    trapview.scrollTo(0, yabby.getTop());
                }
            }
        }

    }
}