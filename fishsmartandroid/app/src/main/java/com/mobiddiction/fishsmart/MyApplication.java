package com.mobiddiction.fishsmart;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.picasso.Picasso;
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

        trackingManager = new TrackingManager(this);
        context = this;
        Realm.init(this);


        Picasso.Builder builder = new Picasso.Builder(this);
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(false);
        Picasso.setSingletonInstance(built);

    }

    public TrackingManager getTrackingManager() {
        return trackingManager;
    }
    public static Context getRestApplication() {
        return context;
    }
}