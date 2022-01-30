package com.mobiddiction.fishsmart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.dao.NewLicenceData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@SuppressLint("ValidFragment")
public class ContactFragment extends Fragment {
    
    LinearLayout reportdynamiclayout, contactdynamiclayout, visitdynamiclayout;
    TextView report, contact, visit;
    public Dialog dialog = null;
    ProgressBar progress_bar;
    public static SharedPreferences pref;
    private boolean isFirstLoad;

    @SuppressLint("ValidFragment")
    public ContactFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create a SharedPreferences object
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        isFirstLoad = sharedPreferences.getBoolean(getResources().getString(R.string.is_first_time_load), true);

        View view = inflater.inflate(R.layout.fragment_morecontact, container, false);
        //((MyApplication) getActivity().getApplication()).getTrackingManager().trackScreen(ScreenEnum.CONTACT, null);
        FirebaseAnalytics.getInstance(getActivity()).setCurrentScreen(getActivity(),ScreenEnum.CONTACT, null);

        progress_bar = view.findViewById(R.id.progress_bar);
        reportdynamiclayout = view.findViewById(R.id.reportdynamiclayout);
        contactdynamiclayout = view.findViewById(R.id.contactdynamiclayout);
        visitdynamiclayout = view.findViewById(R.id.visitdynamiclayout);
        pref = getActivity().getSharedPreferences("fishsmart", 0);
        setDialog();
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

//      new MoreContactAsync(ContactFragment.this, MoreList).execute("/api/contactAndLicensing");
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

    private void setDialog() {
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
            for(NewLicenceData newLicenceData : ModelManager.getInstance().getNewLicenceREPORTData())
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

                if(newLicenceData.getPhone().equals("null"))
                {
                    phonebtn.setVisibility(View.GONE);
                }
                else
                {
                    phonebtn.setVisibility(View.VISIBLE);
                }

                if(newLicenceData.getUrl().equals("null"))
                {
                    onlinebtn.setVisibility(View.GONE);
                }
                else
                {
                    onlinebtn.setVisibility(View.VISIBLE);
                }

                final String phone = newLicenceData.getPhone();
                phonebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phone));
                        sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(sIntent);
                    }
                });

                final String online = newLicenceData.getUrl();
                onlinebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(), WebviewActivity.class);
                        intent.putExtra("fs", online);
                        intent.putExtra("share", true);
                        startActivity(intent);
                    }
                });

                title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                phonebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                onlinebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                title.setText(newLicenceData.getTitle());
                desc.setText(newLicenceData.getDescription());

                reportdynamiclayout.addView(child);
            }

            for(NewLicenceData newLicenceData : ModelManager.getInstance().getNewLicenceCONTACTUSData())
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

                if(newLicenceData.getPhone().equals("null"))
                {
                    phonebtn.setVisibility(View.GONE);
                }
                else
                {
                    phonebtn.setVisibility(View.VISIBLE);
                }

                if(newLicenceData.getUrl().equals("null"))
                {
                    onlinebtn.setVisibility(View.GONE);
                }
                else
                {
                    onlinebtn.setVisibility(View.VISIBLE);
                }

                final String phone = newLicenceData.getPhone();
                phonebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phone));
                        sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(sIntent);
                    }
                });

                final String online = newLicenceData.getUrl();
                onlinebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(), WebviewActivity.class);
                        intent.putExtra("fs", online);
                        intent.putExtra("share", true);
                        startActivity(intent);
                    }
                });

                title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                phonebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                onlinebtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                title.setText(newLicenceData.getTitle());
                desc.setText(newLicenceData.getDescription());

                contactdynamiclayout.addView(child);

            }

            for(NewLicenceData newLicenceData : ModelManager.getInstance().getNewLicenceVISITAFISHERIESOFFICEData())
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

                if(newLicenceData.getPhone().equals("null"))
                {
                    phonebtn.setVisibility(View.GONE);
                }
                else
                {
                    phonebtn.setVisibility(View.VISIBLE);
                }

                if(newLicenceData.getMobile().equals("null"))
                {
                    onlinebtn.setVisibility(View.GONE);
                }
                else
                {
                    onlinebtn.setVisibility(View.VISIBLE);
                }

                final String phone = newLicenceData.getPhone();
                phonebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phone));
                        sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(sIntent);
                    }
                });

                final String mobile = newLicenceData.getMobile();
                onlinebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ mobile));
                        sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(sIntent);
                    }
                });

                title.setText(newLicenceData.getTitle());
                desc.setText(newLicenceData.getDescription());

                visitdynamiclayout.addView(child);
            }
        }
    }
}

