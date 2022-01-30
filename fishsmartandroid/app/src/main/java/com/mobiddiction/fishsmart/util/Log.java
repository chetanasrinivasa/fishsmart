package com.mobiddiction.fishsmart.util;

public class Log {
    /* Logging and Console */
    public static boolean DO_SOP = true;

    public static void print(String mesg) {
        if (Log.DO_SOP) {
            android.util.Log.v(Config.TAG, mesg);
        }
    }

    public static void print(String title, String mesg) {
        if (Log.DO_SOP) {
            android.util.Log.v(title, mesg);
        }
    }

    public static void error(String title, Exception e) {
        if (Log.DO_SOP) {
            Log.print("============:::" + title + ":::================");
            android.util.Log.e(Config.TAG, title, e);
            Log.print("===============================================");
        }
    }
}