package com.mobiddiction.fishsmart.Species;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.dao.AllSpecies;
import com.mobiddiction.fishsmart.dao.AllSpeciesGroup;
import com.mobiddiction.fishsmart.dao.AllSpeciesfishFacts;
import com.mobiddiction.fishsmart.dao.AllSpeciesrules;
import com.mobiddiction.fishsmart.dao.FreshWaterNewData;
import com.mobiddiction.fishsmart.dao.FreshWaterfishFacts;
import com.mobiddiction.fishsmart.dao.FreshWaterfishGroup;
import com.mobiddiction.fishsmart.dao.FreshWaterrules;
import com.mobiddiction.fishsmart.dao.SaltWaterNewData;
import com.mobiddiction.fishsmart.dao.SaltWaterfishFacts;
import com.mobiddiction.fishsmart.dao.SaltWaterfishGroup;
import com.mobiddiction.fishsmart.dao.SaltWaterrules;
import com.mobiddiction.fishsmart.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Archa on 3/05/2016.
 */
public class SpeciesDetailActivity extends AppCompatActivity {

    TextView fishsubname, fishfact, text1, text2, text3, text4, text5, text6, text7, abouttext, sizetext, chartext, confustext, legaltext, baglimittext, possessiontext, rules, fishheading, exclamtext, groupnametext, grouptitletext, groupdesctext;
    ProgressBar progress_bar;
    ArrayList<SpeciesDetailModel> speciesdetailList = new ArrayList<SpeciesDetailModel>();
    SpeciesDetailModel species;
    SpeciesModel model;
    ImageView icon, icons;
    LinearLayout exclamlayout, fishgrouplayout;
    private ToggleButton savespeciesbtn;
    Boolean ISSALTFISH;
    Boolean isSaved;
    String fishId;
    SpeciesDownloader speciesDownloader = SpeciesDownloader.getInstance();
    private ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speciesdetail);

        fishId = getIntent().getStringExtra("fishid");
        ISSALTFISH = getIntent().getBooleanExtra("ISSALTFISH", false);
        isSaved = getIntent().getBooleanExtra("isSaved", false);

        Log.d("TESTTT", " item.getId() :  " + fishId);
        Log.d("TESTTT", " item.getId() :  " + ISSALTFISH);

        progress_bar = findViewById(R.id.progress_bar);


        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        savespeciesbtn = findViewById(R.id.savespeciesbtn);
        icon = findViewById(R.id.fishimg);
        exclamlayout = findViewById(R.id.exclamlayout);
        fishgrouplayout = findViewById(R.id.fishgrouplayout);

        fishsubname = findViewById(R.id.fishsubname);
        Typeface face1 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        fishsubname.setTypeface(face1);

        fishfact = findViewById(R.id.fishfact);
        Typeface face2 = Typeface.createFromAsset(getAssets(), "Bariol_Bold.otf");
        fishfact.setTypeface(face2);

        text1 = findViewById(R.id.text1);
        Typeface face3 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text1.setTypeface(face3);

        text2 = findViewById(R.id.text2);
        Typeface face4 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text2.setTypeface(face4);

        text3 = findViewById(R.id.text3);
        Typeface face5 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text3.setTypeface(face5);

        text4 = findViewById(R.id.text4);
        Typeface face6 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text4.setTypeface(face6);

        text5 = findViewById(R.id.text5);
        Typeface face7 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text5.setTypeface(face7);

        text6 = findViewById(R.id.text6);
        Typeface face8 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text6.setTypeface(face8);

        text7 = findViewById(R.id.text7);
        Typeface face9 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        text7.setTypeface(face9);

        abouttext = findViewById(R.id.abouttext);
        Typeface face10 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        abouttext.setTypeface(face10);

        sizetext = findViewById(R.id.sizetext);
        Typeface face11 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        sizetext.setTypeface(face11);

        chartext = findViewById(R.id.chartext);
        Typeface face12 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        chartext.setTypeface(face12);

        confustext = findViewById(R.id.confustext);
        Typeface face13 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        confustext.setTypeface(face13);

        legaltext = findViewById(R.id.legaltext);
        Typeface face14 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        legaltext.setTypeface(face14);

        baglimittext = findViewById(R.id.baglimittext);
        Typeface face15 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        baglimittext.setTypeface(face15);

        possessiontext = findViewById(R.id.possessiontext);
        Typeface face16 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        possessiontext.setTypeface(face16);

        rules = findViewById(R.id.rules);
        Typeface face17 = Typeface.createFromAsset(getAssets(), "Bariol_Bold.otf");
        rules.setTypeface(face17);

        fishheading = findViewById(R.id.fishheading);
        fishheading.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        exclamtext = findViewById(R.id.exclaimtext);
        exclamtext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Bold.otf"));

        groupnametext = findViewById(R.id.groupnametext);
        groupnametext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Bold.otf"));

        grouptitletext = findViewById(R.id.grouptitletext);
        grouptitletext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        groupdesctext = findViewById(R.id.groupdesctext);
        groupdesctext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        icons = findViewById(R.id.icons);
        DisplayDetail(fishId);
//
//        if(getIntent().getBooleanExtra("fromsearchoffline",false)){
//            DisplayDetail(getIntent().getStringExtra("fishid"));
//        }else {
//
//            if (isNetworkConnected()) {
//
//                Log.d("TESTTT", " item.getId() :isNetworkConnected  ");
//                SpeciesDetailModel existingObject = speciesDownloader.getSpeciesDetails(fishId);
//                model = speciesDownloader.getSpecies(fishId);
//
//                if (existingObject != null) {
//                    species = existingObject;
//                    // Load the ui
//                    DisplayDetail("0");
//                }
//
////                new SpeciesDetailAsync(SpeciesDetailActivity.this, speciesdetailList).execute("/api/species?Id=" + fishId);
//                updateHeartIcon();
//
//                savespeciesbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // TODO: Update downloader
//                        if (speciesDownloader.isFishSaved(fishId)) {
//                            speciesDownloader.unsaveFish(fishId);
//                        } else {
//                            speciesDownloader.saveFish(fishId);
//                        }
//                        updateHeartIcon();
//                    }
//                });
//            } else {
//
//                Log.d("TESTTT", " item.getId() : ! isNetworkConnected  ");
//                DisplayDetail("0");
//            }
//        }
    }

    private void updateHeartIcon() {
        savespeciesbtn.setSelected(SpeciesDownloader.isFishSaved(fishId));
    }


    public void DisplayDetail(String id) {

        String subHeader = "-";
        if (getIntent().getBooleanExtra("fromsearchoffline", false)) {

            AllSpecies allSpecies = ModelManager.getInstance().getSpeciesByid(id);

            /*subHeader = allSpecies.getSubHeader();
            if (subHeader.equals("null")) {
                fishsubname.setText("");
            } else {
                fishsubname.setText(subHeader);
            }*/

            if (allSpecies.getThumbnailImage() != null && !allSpecies.getThumbnailImage().equals("")) {
                Picasso.get()
                        .load(allSpecies.getThumbnailImage())
                        .placeholder(R.drawable.fishsmart_logo)
                        .into(icon);
            }else{
                Picasso.get()
                        .load(R.drawable.fishsmart_logo)
                        .into(icon);
            }

            /*Glide.with(this)
                    .load(allSpecies.getThumbnailImage())
                    .apply(new RequestOptions().placeholder(R.drawable.fishsmart_logo).error(R.drawable.fishsmart_logo))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            final ShapeDrawable background = new ShapeDrawable();
                            background.getPaint().setColor(Color.parseColor("#ffffff"));
                            final Drawable[] layers = {background, resource};
                            icon.setImageDrawable(new LayerDrawable(layers));
                        }
                    });*/


            if (allSpecies.getTitle().length() > 20) {
                fishheading.setText(allSpecies.getTitle().substring(0, 20).concat("..."));
            }else{
                fishheading.setText(allSpecies.getTitle());
            }
            /**
             * RULES
             *
             */

            AllSpeciesrules freshWaterrules = ModelManager.getInstance().getFreshWaterRulesOffline(fishId);
            legaltext.setText(freshWaterrules.getLegalSize());
            baglimittext.setText(freshWaterrules.getRuleBagLimit());
            possessiontext.setText(freshWaterrules.getPossession());


            /**
             * Fish Facts
             *
             */


            AllSpeciesfishFacts freshWaterfishFacts = ModelManager.getInstance().getFreshWaterFishFactOffline(fishId);
            if (freshWaterfishFacts.getAbout() != null && !freshWaterfishFacts.getAbout().equalsIgnoreCase("")) {
                abouttext.setText(Html.fromHtml(freshWaterfishFacts.getAbout()));
            }


            if (freshWaterfishFacts.getSize() != null && !freshWaterfishFacts.getSize().equalsIgnoreCase("")) {
                sizetext.setText(Html.fromHtml(freshWaterfishFacts.getSize()));
            }

            if (freshWaterfishFacts.getCharacteristics() != null && !freshWaterfishFacts.getCharacteristics().equalsIgnoreCase("")) {
                chartext.setText(Html.fromHtml(freshWaterfishFacts.getCharacteristics()));
            }

            if (freshWaterfishFacts.getConfusingSpecies() != null && !freshWaterfishFacts.getConfusingSpecies().equalsIgnoreCase("")) {
                confustext.setText(freshWaterfishFacts.getConfusingSpecies());
            }


            /**
             * Fish Group
             *
             */
            AllSpeciesGroup freshWaterfishGroup = ModelManager.getInstance().getFreshWaterGroupFishByIDOffline(fishId);
            exclamtext.setText(Utils.toTitleCase(freshWaterfishGroup.getGroupName().toLowerCase()));
            //FIXME replace groupname with group type
            if (freshWaterfishGroup.getGroupType().equals("")) {
                fishgrouplayout.setVisibility(View.GONE);
                exclamlayout.setVisibility(View.GONE);
                groupnametext.setVisibility(View.GONE);
            } else {
                if ((freshWaterfishGroup.getGroupDescription()==null || freshWaterfishGroup.getGroupDescription().equals("")) && freshWaterfishGroup.getGroupTitle().equals("")) {
                    fishgrouplayout.setVisibility(View.GONE);
                    if (freshWaterfishGroup.getGroupType().equals(""))
                        exclamlayout.setVisibility(View.GONE);
                    else
                        exclamlayout.setVisibility(View.VISIBLE);
                    groupnametext.setVisibility(View.GONE);
                } else {
                    fishgrouplayout.setVisibility(View.VISIBLE);
                    exclamlayout.setVisibility(View.VISIBLE);
                    groupnametext.setVisibility(View.VISIBLE);
                }
            }

            if (allSpecies.getGrouped() != null && allSpecies.getGrouped() == true) {
                grouptitletext.setText(freshWaterfishGroup.getGroupTitle());
                groupnametext.setText(freshWaterfishGroup.getGroupType());
                groupdesctext.setText(freshWaterfishGroup.getGroupDescription());
            } else {
                exclamlayout.setVisibility(View.GONE);
                exclamtext.setVisibility(View.GONE);
                fishgrouplayout.setVisibility(View.GONE);
                groupnametext.setVisibility(View.GONE);
                icons.setVisibility(View.GONE);
            }


        } else {

//            if (ModelManager.getInstance().getNewSpeciesData() != null) {
//                try {
//                    NEWSpeciesData newSpeciesData = ModelManager.getInstance().getNewSpeciesData();
////                    String date = newSpeciesData.getDateModified();
//
//                    //Tue Oct 11 04:39:50 AEDT 2016
//                    //EEE MMM dd  HH:mm:ss z yyyy"
//                    SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
//                    Date testDate = null;
//                    try {
////                        testDate = sdf.parse(date);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                    String newFormat = formatter.format(testDate);
//
//                    Log.d("TESTTT", " newFormat : " + newFormat);
////                Toast.makeText(this, "Species Last Updated : " + newFormat, Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    Log.d("TESTTT", " exception : " + e.getMessage().toString());
//                }
//            }
            if (ISSALTFISH) {
                try {
                    SaltWaterNewData saltWaterNewData = ModelManager.getInstance().getSaltWaterSpeciesByID(fishId);
                    Log.v("L", "Species is not nil");
                    progress_bar.setVisibility(View.GONE);
                    savespeciesbtn.setChecked(isSaved);

                    if (saltWaterNewData.getTitle().length() > 20) {
                        fishheading.setText(saltWaterNewData.getTitle().substring(0, 20).concat("..."));
                    }else{
                        fishheading.setText(saltWaterNewData.getTitle());
                    }

                    if (saltWaterNewData.getSubHeader().equals("null")) {
                        fishsubname.setText("");
                    } else {
                        fishsubname.setText(saltWaterNewData.getSubHeader());
                    }

                    if (saltWaterNewData.getThumbnailImage() != null && !saltWaterNewData.getThumbnailImage().equals("")) {
                        Picasso.get()
                                .load(saltWaterNewData.getThumbnailImage())
                                .placeholder(R.drawable.fishsmart_logo)
                                .into(icon);
                    }else{
                        Picasso.get()
                                .load(R.drawable.fishsmart_logo)
                                .into(icon);
                    }

                /*Glide.with(this)
                        .load(saltWaterNewData.getThumbnailImage())
                        .apply(new RequestOptions().placeholder(R.drawable.fishsmart_logo).error(R.drawable.fishsmart_logo))
                        .into(new SimpleTarget<Drawable>() {

                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                final ShapeDrawable background = new ShapeDrawable();
                                background.getPaint().setColor(Color.parseColor("#ffffff"));
                                final Drawable[] layers = {background, resource};
                                icon.setImageDrawable(new LayerDrawable(layers));
                            }
                        });*/


                    /**
                     * RULES
                     *
                     */

                    SaltWaterrules saltWaterrules = ModelManager.getInstance().getSaltWaterRules(fishId);
                    legaltext.setText(saltWaterrules.getLegalSize());
                    baglimittext.setText(saltWaterrules.getRuleBagLimit());
                    possessiontext.setText(saltWaterrules.getPossession());


                    /**
                     * Fish Facts
                     *
                     */

                    SaltWaterfishFacts saltWaterfishFacts = ModelManager.getInstance().getSaltWaterFishFact(fishId);
                    abouttext.setText(Html.fromHtml(saltWaterfishFacts.getAbout()));
                    sizetext.setText(Html.fromHtml(saltWaterfishFacts.getSize()));
                    chartext.setText(Html.fromHtml(saltWaterfishFacts.getCharacteristics()));
                    confustext.setText(saltWaterfishFacts.getConfusingSpecies());


                    /**
                     * Fish Group
                     *
                     */
                    SaltWaterfishGroup saltWaterfishGroup = ModelManager.getInstance().getSaltWaterGroupFishByID(fishId);
                    exclamtext.setText(Utils.toTitleCase(saltWaterfishGroup.getGroupName().toLowerCase()));
                    if (saltWaterfishGroup.getGroupName()==null || saltWaterfishGroup.getGroupName().equals("")) {
                        fishgrouplayout.setVisibility(View.GONE);
                        exclamlayout.setVisibility(View.GONE);
                        groupnametext.setVisibility(View.GONE);
                    } else {
                        if (saltWaterfishGroup.getGroupDescription() != null && saltWaterfishGroup.getGroupDescription().equals("") && saltWaterfishGroup.getGroupTitle().equals("")) {
                            fishgrouplayout.setVisibility(View.GONE);
                            if (saltWaterfishGroup.getGroupName().equals(""))
                                exclamlayout.setVisibility(View.GONE);
                            else
                                exclamlayout.setVisibility(View.VISIBLE);
                            groupnametext.setVisibility(View.GONE);
                        } else {
                            fishgrouplayout.setVisibility(View.VISIBLE);
                            exclamlayout.setVisibility(View.VISIBLE);
                            groupnametext.setVisibility(View.VISIBLE);
                        }
                    }

                    if (saltWaterNewData.getGrouped() != null && saltWaterNewData.getGrouped() == true) {
                        grouptitletext.setText(saltWaterfishGroup.getGroupTitle());
                        groupnametext.setText(saltWaterfishGroup.getGroupName());
                        groupdesctext.setText(saltWaterfishGroup.getGroupDescription());
                    } else {
                        exclamlayout.setVisibility(View.GONE);
                        exclamtext.setVisibility(View.GONE);
                        fishgrouplayout.setVisibility(View.GONE);
                        groupnametext.setVisibility(View.GONE);
                        icons.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (!ISSALTFISH) {
                try {
                    FreshWaterNewData freshWaterNewData = ModelManager.getInstance().getFreshWaterSpeciesByID(fishId);
                    Log.v("L", "Species is not nil");
                    progress_bar.setVisibility(View.GONE);
                    savespeciesbtn.setChecked(isSaved);

                    if (freshWaterNewData.getTitle().length() > 12) {
                        fishheading.setText(freshWaterNewData.getTitle().substring(0, 12).concat(" ..."));
                    }else{
                        fishheading.setText(freshWaterNewData.getTitle());
                    }

                    if (freshWaterNewData.getSubHeader().equals("null")) {
                        fishsubname.setText("");
                    } else {
                        fishsubname.setText(freshWaterNewData.getSubHeader());
                    }

                    if (freshWaterNewData.getThumbnailImage() != null && !freshWaterNewData.getThumbnailImage().equals("")) {
                        Picasso.get()
                                .load(freshWaterNewData.getThumbnailImage())
                                .placeholder(R.drawable.fishsmart_logo)
                                .into(icon);
                    }else{
                        Picasso.get()
                                .load(R.drawable.fishsmart_logo)
                                .into(icon);
                    }

                /*Glide.with(this)
                        .load(freshWaterNewData.getThumbnailImage())
                        .apply(new RequestOptions().placeholder(R.drawable.fishsmart_logo).error(R.drawable.fishsmart_logo))
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                final ShapeDrawable background = new ShapeDrawable();
                                background.getPaint().setColor(Color.parseColor("#ffffff"));
                                final Drawable[] layers = {background, resource};
                                icon.setImageDrawable(new LayerDrawable(layers));
                            }
                        });*/
                    /**
                     * RULES
                     *
                     */

                    FreshWaterrules freshWaterrules = ModelManager.getInstance().getFreshWaterRules(fishId);
                    legaltext.setText(freshWaterrules.getLegalSize());
                    baglimittext.setText(freshWaterrules.getRuleBagLimit());
                    possessiontext.setText(freshWaterrules.getPossession());


                    /**
                     * Fish Facts
                     *
                     */

                    FreshWaterfishFacts freshWaterfishFacts = ModelManager.getInstance().getFreshWaterFishFact(fishId);
                    abouttext.setText(Html.fromHtml(freshWaterfishFacts.getAbout()));
                    sizetext.setText(Html.fromHtml(freshWaterfishFacts.getSize()));
                    chartext.setText(Html.fromHtml(freshWaterfishFacts.getCharacteristics()));
                    confustext.setText(freshWaterfishFacts.getConfusingSpecies());


                    /**
                     * Fish Group
                     *
                     */
                    FreshWaterfishGroup freshWaterfishGroup = ModelManager.getInstance().getFreshWaterGroupFishByID(fishId);
                    exclamtext.setText(Utils.toTitleCase(freshWaterfishGroup.getGroupName().toLowerCase()));
                    if (freshWaterfishGroup.getGroupName() !=null && freshWaterfishGroup.getGroupName().equals("")) {
                        fishgrouplayout.setVisibility(View.GONE);
                        exclamlayout.setVisibility(View.GONE);
                        groupnametext.setVisibility(View.GONE);
                    } else {
                        if (freshWaterfishGroup.getGroupDescription()==null || freshWaterfishGroup.getGroupDescription().trim().equals("") && freshWaterfishGroup.getGroupTitle().equals("")) {
                            fishgrouplayout.setVisibility(View.GONE);
                            if (freshWaterfishGroup.getGroupName() ==null || freshWaterfishGroup.getGroupName().equals(""))
                                exclamlayout.setVisibility(View.GONE);
                            else
                                exclamlayout.setVisibility(View.VISIBLE);
                            groupnametext.setVisibility(View.GONE);
                        } else {
                            fishgrouplayout.setVisibility(View.VISIBLE);
                            exclamlayout.setVisibility(View.VISIBLE);
                            groupnametext.setVisibility(View.VISIBLE);
                        }
                    }

                    if (freshWaterNewData.getGrouped() != null && freshWaterNewData.getGrouped() == true) {
                        grouptitletext.setText(freshWaterfishGroup.getGroupTitle());
                        groupnametext.setText(freshWaterfishGroup.getGroupName());
                        groupdesctext.setText(freshWaterfishGroup.getGroupDescription());
                    } else {
                        exclamlayout.setVisibility(View.GONE);
                        exclamtext.setVisibility(View.GONE);
                        fishgrouplayout.setVisibility(View.GONE);
                        groupnametext.setVisibility(View.GONE);
                        icons.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.freeMemory();
    }
}