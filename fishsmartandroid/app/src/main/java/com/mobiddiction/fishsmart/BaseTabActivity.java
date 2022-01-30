package com.mobiddiction.fishsmart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.NewSpecies.NewSpeciesActivity;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Archa on 16/07/2015.
 */
public abstract class BaseTabActivity extends AppCompatActivity {

    private static final String TAG = "BaseTabActivity";
    ImageButton btn_slide;
    ImageButton backbtn;
    ImageView btnBack;
    TextView nav_title;
    static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private boolean isPaused;
    private RelativeLayout errorsBar;

    @Override
    protected void onCreate(Bundle savedInstanceState/*, int layout, boolean isTabs*/) {
        super.onCreate(savedInstanceState);

//        setContentView(layout);
//        final ActionBar actionBar = getSupportActionBar();
//        if (isTabs)
//            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


//        this.getSupportActionBar().setDisplayShowHomeEnabled(false);

        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        btn_slide = mActionBarView.findViewById(R.id.btn_slide);
        backbtn = mActionBarView.findViewById(R.id.backbtn);
        btnBack = mActionBarView.findViewById(R.id.btnBack);

        nav_title = mActionBarView.findViewById(R.id.nav_title);
        Typeface face1 = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        nav_title.setTypeface(face1);

//        this.getSupportActionBar().setCustomView(mActionBarView);
//        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        IntentFilter filter = new IntentFilter(CONNECTIVITY_CHANGE_ACTION);
        this.registerReceiver(mChangeConnectionReceiver, filter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFrameActivity.inAnimate = true;
                //MainFrameActivity.home.performClick();
                MainFrameActivity.inAnimate = false;
            }
        });
    }

    private final BroadcastReceiver mChangeConnectionReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (CONNECTIVITY_CHANGE_ACTION.equals(action) && !isPaused) {
                //check internet connection
                if (!ConnectionHelper.isConnectedOrConnecting(getApplicationContext())) {
                    if (context != null) {
                        boolean show = false;
                        if (ConnectionHelper.lastNoConnectionTs == -1) {//first time
                            show = true;
                            ConnectionHelper.lastNoConnectionTs = System.currentTimeMillis();
                        } else {
                            if (System.currentTimeMillis() - ConnectionHelper.lastNoConnectionTs > 1000) {
                                show = true;
                                ConnectionHelper.lastNoConnectionTs = System.currentTimeMillis();
                            }
                        }

                        if (show && ConnectionHelper.isOnline) {
                            hideErrorsBar(false);
                            ConnectionHelper.isOnline = false;
                        }
                    }
                } else {
                    hideErrorsBar(true);
                    ConnectionHelper.isOnline = true;
                }
            }
        }
    };

    public void hideErrorsBar(boolean hide) {
        try {
            //try to find alert bar view
            View errorBar = findViewById(R.id.toolbar_sub_error_bar);
            if (errorBar != null) {
                //TODO some animation here?
                if (hide) {
                    EventBus.getDefault().post(BasicEvent.ONLINE);
//                    errorBar.setVisibility(View.GONE);
                } else {
                    EventBus.getDefault().post(BasicEvent.OFFLINE);
//                    errorBar.setVisibility(View.VISIBLE);

                    // Need the activity rootview
                    // Use "android.R.id.content"
                    // Can also use "getWindow().getDecorView().findViewById(android.R.id.content)"
                    Snackbar.make(findViewById(android.R.id.content), R.string.offine_message,
                            Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
        }
    }


    public void toggleMenu(View vw) {
        Intent i = new Intent();
        i.setAction("com.fishsmart.toggle");
        sendBroadcast(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //  setUpMap();
        isPaused = false;
        if (findViewById(R.id.toolbar_sub_error_bar) != null) {
            errorsBar = findViewById(R.id.toolbar_sub_error_bar);
//            errorsBar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    hideErrorsBar(true);
//                }
//            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mChangeConnectionReceiver != null) {
            try {
                this.unregisterReceiver(mChangeConnectionReceiver);
            } catch (Exception e) {

            }
        }
    }

    public void HomeTextChanger(String text, Context c) {
        nav_title.setText(Html.fromHtml(text));
        if (text.equals("GALLERY")) {
            btnBack.setVisibility(View.VISIBLE);
            btn_slide.setVisibility(View.GONE);
        } else {
            btn_slide.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.GONE);
        }
    }

    public Context getContext(){
        return BaseTabActivity.this;
    }
}