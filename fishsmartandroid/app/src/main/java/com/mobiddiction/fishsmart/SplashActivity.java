package com.mobiddiction.fishsmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mobiddiction.fishsmart.CustomAlert.AlertDialog;
import com.mobiddiction.fishsmart.CustomAlert.OnItemClickListener;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Network.ResponseConfig;
import com.mobiddiction.fishsmart.Onboarding.OnboardingActivity;
import com.mobiddiction.fishsmart.Profile.ChangePasswordActivity;
import com.mobiddiction.fishsmart.SignIn.SignInActivity;
import com.mobiddiction.fishsmart.SignIn.SignInBody;
import com.mobiddiction.fishsmart.dao.FirstTimeLoad;
import com.mobiddiction.fishsmart.util.Pref;
import com.mobiddiction.fishsmart.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Archa on 20/04/2016.
 */
public class SplashActivity extends AppCompatActivity {

    GoogleApiClient mGoogleApiClient;
    Location currentLocation;
    private ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashImage = findViewById(R.id.splashImage);

        /*Glide.with(this)
                .load(R.drawable.splash)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(splashImage);*/

        Glide.with(this)
                .load(R.drawable.splash)
                /*.diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)*/
                .format(DecodeFormat.PREFER_RGB_565)
                .apply(new RequestOptions().centerInside())
                .into(splashImage);
        Utils.systemUpgrade(SplashActivity.this);
        SharedPreferences.Editor get_editor = getSharedPreferences("FIRST_TIME_LOAD", 0).edit();
        SharedPreferences get = getSharedPreferences("FIRST_TIME_LOAD", 0);


        /**
         * set count
         */
        if (get.getInt("COUNT", 0) == 0) {
            get_editor.putInt("COUNT", 1);
            get_editor.commit();
            ModelManager.getInstance().removeAllFirstTime();
            FirstTimeLoad firstTimeLoad = new FirstTimeLoad();
            firstTimeLoad.setIsFirstTimeLoad(true);
            ModelManager.getInstance().insertFirstTime(firstTimeLoad);
        } else {
            if (isNetworkConnected()) {
                ModelManager.getInstance().removeAllFirstTime();
                FirstTimeLoad firstTimeLoad = new FirstTimeLoad();
                firstTimeLoad.setIsFirstTimeLoad(false);
                ModelManager.getInstance().insertFirstTime(firstTimeLoad);
            }
        }

        if(Pref.getValue(SplashActivity.this, ResponseConfig.PREF_IS_LOAD_BOARD, 0) == 0) {
            NetworkManager.getInstance().getOnboarding();
        }

        /**
         * check first time on android
         */
//        SharedPreferences get = getSharedPreferences("FIRST_TIME_LOAD", 0);
//
//        if(ModelManager.getInstance().getFirstLoad() == null && ModelManager.getInstance().getFirstLoad().size() < 0) {
//            SharedPreferences.Editor get_editor = getSharedPreferences("FIRST_TIME_LOAD", 0).edit();
//            get_editor.putBoolean("FIRST_TIME_LOAD", true);
//            get_editor.commit();
//        }
//
//
//        if(get.getBoolean("FIRST_TIME_LOAD",false)) {
//            SharedPreferences.Editor get_editor = getSharedPreferences("FIRST_TIME_LOAD", 0).edit();
//            ModelManager.getInstance().removeAllFirstTime();
//            FirstTimeLoad firstTimeLoad = new FirstTimeLoad();
//            firstTimeLoad.setIsFirstTimeLoad(true);
//            ModelManager.getInstance().insertFirstTime(firstTimeLoad);
//            get_editor.putBoolean("FIRST_TIME_LOAD",false);
//            get_editor.commit();
//        } else {
//            ModelManager.getInstance().removeAllFirstTime();
//            FirstTimeLoad firstTimeLoad = new FirstTimeLoad();
//            firstTimeLoad.setIsFirstTimeLoad(false);
//            ModelManager.getInstance().insertFirstTime(firstTimeLoad);
//        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (ModelManager.getInstance() != null && ModelManager.getInstance().getLoginDetails() != null) {
                    NetworkManager.getInstance().doLogin(new SignInBody(ModelManager.getInstance().getLoginDetails().getEmail(), ModelManager.getInstance().getLoginDetails().getPassword()));
                } else {
                    if (Pref.getValue(SplashActivity.this, ResponseConfig.PREF_IS_LOAD_BOARD, 0) == 0 && ModelManager.getInstance().getOnboarding().size() > 0) {
                        startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainFrameActivity.class));
                        finish();
                    }
                }
            }
        }, 3000);

    }


    public boolean isNetworkConnected() {
        return ConnectionHelper.isConnectedOrConnecting(this);
    }


    @Override
    public void onResume() {
        super.onResume();

        Glide.with(this)
                .load(R.drawable.splash)
                /*.diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)*/
                .format(DecodeFormat.PREFER_RGB_565)
                .apply(new RequestOptions().centerInside())
                .into(splashImage);

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
        Glide.with(this).clear(splashImage);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(BasicEvent event) {

        if (event == BasicEvent.DO_LOGIN_SUCCESS) {
            if (Pref.getValue(SplashActivity.this, ResponseConfig.PREF_IS_LOAD_BOARD, 0) == 0 && ModelManager.getInstance().getOnboarding() != null && ModelManager.getInstance().getOnboarding().size() > 0) {
                startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
                finish();
            } else {
                if(ModelManager.getInstance().getLoginResponse().getChangePassword().equals("true")){
                    Intent intent = new Intent(this, ChangePasswordActivity.class);
                    intent.putExtra("NEED_CHANGE_PASS",true);
                    startActivity(intent);
                    finish();
                }else {
                    startActivity(new Intent(this, MainFrameActivity.class));
                    finish();
                }
            }
        }

        if (event == BasicEvent.DO_LOGIN_FAILED) {
            AlertDialog.showDialogWithAlertHeaderSingleButton(this,
                    getResources().getString(R.string.login_failed_title),
                    getResources().getString(R.string.login_failed_message),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            ModelManager.getInstance().removeLogindetail();
                            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                            finish();
                        }
                    });
        }


        if (event == BasicEvent.DO_LOGIN_401) {
            AlertDialog.showDialogWithAlertHeaderSingleButton(this,
                    "Activate your account",
                    "Please activate your account from the link sent to your email.",
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            ModelManager.getInstance().removeLogindetail();
                            if (ModelManager.getInstance().getOnboarding().size() > 0) {
                                startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
                                finish();
                            } else {
                                startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                                finish();
                            }
                        }
                    });
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}