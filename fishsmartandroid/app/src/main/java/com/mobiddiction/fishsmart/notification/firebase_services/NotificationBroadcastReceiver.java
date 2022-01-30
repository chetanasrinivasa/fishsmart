package com.mobiddiction.fishsmart.notification.firebase_services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.legacy.content.WakefulBroadcastReceiver;

public class NotificationBroadcastReceiver extends WakefulBroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        WakefulBroadcastReceiver.startWakefulService(context, intent.setComponent(new ComponentName(context.getPackageName(), TokenService.class.getName())));
        setResultCode(Activity.RESULT_OK);

        SharedPreferences.Editor finishedImageUpload = context.getSharedPreferences("PUSH_NOTIFICATION", Context.MODE_PRIVATE).edit();
        finishedImageUpload.putBoolean("PUSH_NOTIFICATION", true);
        finishedImageUpload.commit();
    }


}