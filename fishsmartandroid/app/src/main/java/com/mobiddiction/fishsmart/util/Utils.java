package com.mobiddiction.fishsmart.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;

public class Utils {
    /**
     * System Upgrade for Database.
     */
    public static void systemUpgrade(Context context) {
        DBHelper dbHelper = null;
        int level = 0;
        try {
            dbHelper = new DBHelper(context);
            level = Integer.parseInt(Pref.getValue(context, Config.LEVEL, "0"));

            if (level == 0) {
                dbHelper.upgrade(level);
                level++;
            }
            Pref.setValue(context, Config.LEVEL, level + "");
        } catch (Exception e) {
            Log.error(":: systemUpgrade() ::", e);
        } finally {
            if (dbHelper != null)
                dbHelper.close();
            dbHelper = null;
            level = 0;
        }
    }

    /*
     * Function free the memory.
     * */
    public static void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    /**
     * Check Connectivity of network.
     */
    public static boolean isOnline(Context context) {
        try {
            if (context == null)
                return false;

            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (cm != null) {
                if (cm.getActiveNetworkInfo() != null) {
                    return cm.getActiveNetworkInfo().isConnected();
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.error("Exception", e);
            return false;
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString().replace("And", "and").replace("-sea", "-Sea")
                .replace("Cockles", "Cockles,").replace("Sole", "Soles");
    }
}