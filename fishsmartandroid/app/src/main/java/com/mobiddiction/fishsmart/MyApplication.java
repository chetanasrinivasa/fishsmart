package com.mobiddiction.fishsmart;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.Crash;
import io.realm.Realm;

/**
 * Created by Archa on 25/02/2016.
 */
public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();
    //  private RequestQueue mRequestQueue;
    private TrackingManager trackingManager;
    private static Context context;
//    protected ModelManager modelManager;
//    private NetworkManager networkManager;
    private static boolean activityVisible;

    @Override
    public void onCreate() {

        super.onCreate();

        Crashlytics crash = new Crashlytics();

        Fabric.with(this, crash, new Crashlytics());
//        Fabric.with(this, new Crashlytics());
//        modelManager = ModelManager.getInstance();
//        networkManager = NetworkManager.getInstance();
        trackingManager = new TrackingManager(this);
        context = this;
        Realm.init(this);


        Picasso.Builder builder = new Picasso.Builder(this);
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(false);
        Picasso.setSingletonInstance(built);

        boolean fabricInitialized = Fabric.isInitialized();
        if (fabricInitialized) {
//            crash.log("Fabric initalised");
            Log.d(TAG, "k Fabric initialised");
        } else {
            Log.d(TAG, "k Fabric not initialised");
        }

    }

    public TrackingManager getTrackingManager() {
        return trackingManager;
    }
    public static Context getRestApplication() {
        return context;
    }
}