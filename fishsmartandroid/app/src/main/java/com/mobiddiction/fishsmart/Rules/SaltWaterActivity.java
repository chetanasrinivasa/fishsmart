package com.mobiddiction.fishsmart.Rules;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiddiction.fishsmart.MyApplication;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.ScreenEnum;

/**
 * Created by Archa on 28/04/2016.
 */
public class SaltWaterActivity extends AppCompatActivity {

    Button summarybtn, trapbtn;
    TextView listtext, tapontext, geartype, text1, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11, text12, text13, text14, text15, text16, text17, text18, text19, text20, text21, text22, text23, text24, text25, text26, text27, text28, gang, must, paratext;
    TextView rulestext1, rulestext2, rulestext3, rulestext4, rulestext5, rulestext6, rulestext7, rulestext8, baittrap, hoopnet, crabtrap, spanner, hand, scissor, dip;
    ScrollView summaryview, trapview;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saltwater);

        summarybtn = findViewById(R.id.summarybtn);
        trapbtn = findViewById(R.id.trapbtn);
        summaryview = findViewById(R.id.summaryscrollview);
        trapview = findViewById(R.id.traprulesscrollview);
        backbtn = findViewById(R.id.backbtn);

        summarybtn.setSelected(true);

        // start change font

        dip = findViewById(R.id.dip);
        dip.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        scissor = findViewById(R.id.scissor);
        scissor.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        hand = findViewById(R.id.hand);
        hand.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        spanner = findViewById(R.id.spanner);
        spanner.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        crabtrap = findViewById(R.id.crabtrap);
        crabtrap.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        baittrap = findViewById(R.id.baittrap);
        baittrap.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        hoopnet = findViewById(R.id.hoopnet);
        hoopnet.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        paratext = findViewById(R.id.paratext);
        paratext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        listtext = findViewById(R.id.listtext);
        Typeface face1 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        listtext.setTypeface(face1);

        tapontext = findViewById(R.id.tapontext);
        Typeface face2 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        tapontext.setTypeface(face2);

        geartype = findViewById(R.id.geartype);
        Typeface face3 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        geartype.setTypeface(face3);

        text1 = findViewById(R.id.text1);
        Typeface face4 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text1.setTypeface(face4);

        text2 = findViewById(R.id.text2);
        Typeface face5 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text2.setTypeface(face5);

        text3 = findViewById(R.id.text3);
        Typeface face6 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text3.setTypeface(face6);

        text4 = findViewById(R.id.text4);
        Typeface face7 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text4.setTypeface(face7);

        text5 = findViewById(R.id.text5);
        Typeface face8 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text5.setTypeface(face8);

        text6 = findViewById(R.id.text6);
        Typeface face9 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text6.setTypeface(face9);

        text7 = findViewById(R.id.text7);
        text7.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text8 = findViewById(R.id.text8);
        text8.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text9 = findViewById(R.id.text9);
        text9.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text10 = findViewById(R.id.text10);
        text10.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text11 = findViewById(R.id.text11);
        text11.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text12 = findViewById(R.id.text12);
        text12.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text13 = findViewById(R.id.text13);
        text13.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text14 = findViewById(R.id.text14);
        text14.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text15 = findViewById(R.id.text15);
        text15.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text16 = findViewById(R.id.text16);
        text16.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text17 = findViewById(R.id.text17);
        text17.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text18 = findViewById(R.id.text18);
        text18.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text19 = findViewById(R.id.text19);
        text19.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text20 = findViewById(R.id.text20);
        text20.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text21 = findViewById(R.id.text21);
        text21.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text22 = findViewById(R.id.text22);
        text22.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text23 = findViewById(R.id.text23);
        text23.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text24 = findViewById(R.id.text24);
        text24.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text25 = findViewById(R.id.text25);
        text25.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text26 = findViewById(R.id.text26);
        text26.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text27 = findViewById(R.id.text27);
        text27.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        text28 = findViewById(R.id.text28);
        text28.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        Typeface face10 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        summarybtn.setTypeface(face10);

        Typeface face11 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        trapbtn.setTypeface(face11);

        text7 = findViewById(R.id.text7);
        Typeface face12 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text7.setTypeface(face12);

        text8 = findViewById(R.id.text8);
        Typeface face13 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text8.setTypeface(face13);

        text9 = findViewById(R.id.text9);
        Typeface face14 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text9.setTypeface(face14);

        text10 = findViewById(R.id.text10);
        Typeface face15 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text10.setTypeface(face15);

        text11 = findViewById(R.id.text11);
        Typeface face16 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text11.setTypeface(face16);

        text12 = findViewById(R.id.text12);
        Typeface face17 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text12.setTypeface(face17);

        gang = findViewById(R.id.gang);
        Typeface face18 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        gang.setTypeface(face18);

        must = findViewById(R.id.must);
        Typeface face19 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        must.setTypeface(face19);

        rulestext1 = findViewById(R.id.ruleslisttext1);
        rulestext1.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        rulestext2 = findViewById(R.id.ruleslisttext2);
        rulestext2.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        rulestext3 = findViewById(R.id.ruleslisttext3);
        rulestext3.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        rulestext4 = findViewById(R.id.ruleslisttext4);
        rulestext4.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        rulestext5 = findViewById(R.id.ruleslisttext5);
        rulestext5.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        rulestext6 = findViewById(R.id.ruleslisttext6);
        rulestext6.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        rulestext7 = findViewById(R.id.ruleslisttext7);
        rulestext7.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        rulestext8 = findViewById(R.id.ruleslisttext8);
        rulestext8.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        // end change font

        listtext.setText(Html.fromHtml("<br>&#8226;   Recreational catch cannot be sold.<br><br>" +
                "&#8226;   Cast nets not permitted for use in NSW.<br><br>" +
                "&#8226;   Interference with commercial fishing gear, oyster leases or other gear is not permitted.<br><br>" +
                "&#8226;   Spare lines should not be rigged and be properly stowed.<br><br>" +
                "&#8226;   Jagging fish not permitted.<br><br>" +
                "&#8226;   Pipis can only be taken for use as bait and cannot be taken more than 50m from the high tide water mark.<br><br>" +
                "&#8226;   Lobsters and crabs with eggs must be released.<br><br>" +
                "&#8226;   You must not alter a fish subject to a size limit other than by gutting, gilling and scaling until you are well away from the water. This rule does not apply at areas normally used for cleaning fish such as at  fish cleaning tables,  if the fish are for immediate consumption,  immediate use as bait or for fish that don’t have a legal length.<br>"));

        tapontext.setText(Html.fromHtml("Tap on the <font color=#df8871>trap titles</font> below to see more information and the rules that apply to them."));

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

        summarybtn.setOnClickListener(listener);
        trapbtn.setOnClickListener(listener);
        backbtn.setOnClickListener(listener);
        text7.setOnClickListener(listener);
        text9.setOnClickListener(listener);
        text11.setOnClickListener(listener);
        text13.setOnClickListener(listener);
        text15.setOnClickListener(listener);
        text17.setOnClickListener(listener);
        text19.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener(){


        @Override
        public void onClick(View v) {

        if (v.getId() == R.id.summarybtn)
        {
            summarybtn.setSelected(true);
            trapbtn.setSelected(false);
            summarybtn.setTextColor(Color.parseColor("#df8871"));
            trapbtn.setTextColor(Color.parseColor("#9b9b9b"));
            //((MyApplication) getApplication()).getTrackingManager().trackScreen(ScreenEnum.SALTWATER_SUMMARY, null);
            FirebaseAnalytics.getInstance(getApplicationContext()).setCurrentScreen(getParent(),ScreenEnum.SALTWATER_SUMMARY, null);
            summaryview.setVisibility(View.VISIBLE);
            trapview.setVisibility(View.GONE);

        }else if (v.getId() == R.id.trapbtn) {
            //((MyApplication) getApplication()).getTrackingManager().trackScreen(ScreenEnum.SALTWATER_TRAP_RULES, null);
            FirebaseAnalytics.getInstance(getApplicationContext()).setCurrentScreen(getParent(),ScreenEnum.SALTWATER_TRAP_RULES, null);
            summarybtn.setSelected(false);
            trapbtn.setSelected(true);
            summarybtn.setTextColor(Color.parseColor("#9b9b9b"));
            trapbtn.setTextColor(Color.parseColor("#df8871"));

            summaryview.setVisibility(View.GONE);
            trapview.setVisibility(View.VISIBLE);

        }else if (v.getId() == R.id.backbtn) {

           onBackPressed();

        }
        else if (v.getId() == R.id.text7) {

            summarybtn.setSelected(false);
            trapbtn.setSelected(true);
            summarybtn.setTextColor(Color.parseColor("#9b9b9b"));
            trapbtn.setTextColor(Color.parseColor("#df8871"));
            summaryview.setVisibility(View.GONE);
            trapview.setVisibility(View.VISIBLE);
            trapview.scrollTo(0, baittrap.getTop());

        }else if (v.getId() == R.id.text9) {

            summarybtn.setSelected(false);
            trapbtn.setSelected(true);
            summarybtn.setTextColor(Color.parseColor("#9b9b9b"));
            trapbtn.setTextColor(Color.parseColor("#df8871"));
            summaryview.setVisibility(View.GONE);
            trapview.setVisibility(View.VISIBLE);
            trapview.scrollTo(0, hoopnet.getTop());

        }else if (v.getId() == R.id.text11) {

            summarybtn.setSelected(false);
            trapbtn.setSelected(true);
            summarybtn.setTextColor(Color.parseColor("#9b9b9b"));
            trapbtn.setTextColor(Color.parseColor("#df8871"));
            summaryview.setVisibility(View.GONE);
            trapview.setVisibility(View.VISIBLE);
            trapview.scrollTo(0, crabtrap.getTop());

        }else if (v.getId() == R.id.text13) {

            summarybtn.setSelected(false);
            trapbtn.setSelected(true);
            summarybtn.setTextColor(Color.parseColor("#9b9b9b"));
            trapbtn.setTextColor(Color.parseColor("#df8871"));
            summaryview.setVisibility(View.GONE);
            trapview.setVisibility(View.VISIBLE);
            trapview.scrollTo(0, spanner.getTop());

        }else if (v.getId() == R.id.text15) {

            summarybtn.setSelected(false);
            trapbtn.setSelected(true);
            summarybtn.setTextColor(Color.parseColor("#9b9b9b"));
            trapbtn.setTextColor(Color.parseColor("#df8871"));
            summaryview.setVisibility(View.GONE);
            trapview.setVisibility(View.VISIBLE);
            trapview.scrollTo(hand.getTop(), hand.getTop());

        }else if (v.getId() == R.id.text17) {

            summarybtn.setSelected(false);
            trapbtn.setSelected(true);
            summarybtn.setTextColor(Color.parseColor("#9b9b9b"));
            trapbtn.setTextColor(Color.parseColor("#df8871"));
            summaryview.setVisibility(View.GONE);
            trapview.setVisibility(View.VISIBLE);
            trapview.scrollTo(scissor.getTop(), scissor.getTop());

        }else if (v.getId() == R.id.text19) {

            summarybtn.setSelected(false);
            trapbtn.setSelected(true);
            summarybtn.setTextColor(Color.parseColor("#9b9b9b"));
            trapbtn.setTextColor(Color.parseColor("#df8871"));
            summaryview.setVisibility(View.GONE);
            trapview.setVisibility(View.VISIBLE);
            trapview.scrollTo(dip.getTop(), dip.getTop());

        }

        }
    };
}