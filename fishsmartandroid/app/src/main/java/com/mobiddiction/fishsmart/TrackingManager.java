package com.mobiddiction.fishsmart;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mobiddiction.fishsmart.Configuration.Config;

/**
 * Created by andrewi on 11/10/2016.
 */

public class TrackingManager {

    private static final String TAG = TrackingManager.class.getSimpleName();
    private static final String CATEGORY_UI = "UI";
    private Context context;
    private Tracker analyticsTracker;

    public TrackingManager(Context context){
        this.context = context;
    }

    public void trackScreen(String screenName, String label){
        Tracker tracker = getAnalyticsTracker();
        if (tracker == null){
            Log.d(TAG, "Tracker was null, ignoring");
            return;
        }

        Log.d(TAG, "Tracking screen: " + screenName);
        String completeNAME = screenName;

        if(label != null) {
            completeNAME += " : " + label;
        }

        tracker.setScreenName(completeNAME);

        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private Tracker getAnalyticsTracker() {

        if (analyticsTracker == null){

            String trackingId = Config.getStringValueFromPreferences(context, Config.GA_TRACKING_ID_PREFERENCE);

            if (trackingId == null){
                // Get default
                trackingId = context.getString(R.string.default_ga_tracking_id);
            }
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);

            analyticsTracker = analytics.newTracker(trackingId);
        }

        return analyticsTracker;
    }

    // We assume they're all UI events
    public void trackEvent(String action, String label){
        Tracker tracker = getAnalyticsTracker();
        if (tracker == null){
            Log.d(TAG, "Tracker was null, ignoring");
            return;
        }

        Log.d(TAG, "Tracking action: " + action + ", label: " + label);
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(CATEGORY_UI)
                .setAction(action)
                .setLabel(label)
                .build());
    }

}