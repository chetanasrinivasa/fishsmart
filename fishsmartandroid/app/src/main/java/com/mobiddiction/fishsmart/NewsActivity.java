package com.mobiddiction.fishsmart;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Network.ResponseConfig;
import com.mobiddiction.fishsmart.Network.ResponseListner;
import com.mobiddiction.fishsmart.Profile.ProfileActivity;
import com.mobiddiction.fishsmart.bean.UserBean;
import com.mobiddiction.fishsmart.notification.NotificationActivity;
import com.mobiddiction.fishsmart.util.Utils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Archa on 10/05/2016.
 */
public class NewsActivity extends BaseTabActivity {

    NewsFragment newsFragment;
    private CircleImageView imgUser;
    private ImageView imgNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState/*, R.layout.activity_news, false*/);
        setContentView(R.layout.activity_news);

        HomeTextChanger("NEWS", this);
        //((MyApplication) getApplication()).getTrackingManager().trackScreen(ScreenEnum.NEWS, null);
        FirebaseAnalytics.getInstance(getApplicationContext()).setCurrentScreen(this,ScreenEnum.NEWS, null);
        newsFragment = new NewsFragment();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_header));

        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        imgNotification = mActionBarView.findViewById(R.id.imgNotification);
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        imgUser = mActionBarView.findViewById(R.id.imgUser);

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        TextView nav_title = mActionBarView.findViewById(R.id.nav_title);
        nav_title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        nav_title.setText(Html.fromHtml("NEWS"));


        if (Utils.isOnline(this) && ModelManager.getInstance().getLoginResponse() != null) {
            NetworkManager.getInstance().getUserProfile(ModelManager.getInstance().getLoginResponse().getAuthorization(), "application/json",
                    ModelManager.getInstance().getLoginResponse().getUserId(), responseListner);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ModelManager.getInstance() != null && ModelManager.getInstance().getLoginDetails() != null) {
            if (ModelManager.getInstance().getLoginResponse().getUserId() != null &&
                    !ModelManager.getInstance().getLoginResponse().getUserId().equals("")) {
                imgUser.setVisibility(View.VISIBLE);
            }
        } else {
            imgUser.setVisibility(View.GONE);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewsFragment()).commit();
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


    /*@Override
    protected void onStart() {
        super.onStart();
        final UserBean userBean = (UserBean) obj;
        if (userBean.image.size() > 0) {
            NewsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Glide.with(NewsActivity.this)
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.freeMemory();
    }
}