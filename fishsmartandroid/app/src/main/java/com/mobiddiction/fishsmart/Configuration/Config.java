package com.mobiddiction.fishsmart.Configuration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.mobiddiction.fishsmart.Weather.WeatherModel;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Archa on 20/04/2016.
 */
public class Config {

    public static final Boolean isStaging = true;
    static public String CERT_STRING = "";
    // public final static String HOST = "http://driver-demo.mobiconnect.io:8080";
    // public final static String HOST = "http://test.mobiconnect.io:8080";
    // test.mobiconnect.io
    // public final static String HOST = "http://test.mobiconnect.io:8080";
    // public final static String HOST = "http://fishsmart-dev.mobiddiction.net:8080";
    public final static String HOST = "https://fishsmartv2.mobiddiction.net/api/"; //!!!PRODUCTION!!!
    // public final static String HOST = "https://fishsmartv2api.mobiddiction.net:8443/api/"; //!!!PRODUCTION!!!
    public final static String HOST_OLD = "https://fishsmart.mobiddiction.net.au";

    public static final String HOME = "Home";
    public static final String MAPS = "Maps";
    public static final String SPEICES = "Speices";
    public static final String RULES = "Rules";
    public static final String MORE = "More";
    public static final String GALLERY = "Gallery";
    public static final String NEWS = "News";
    public static final String SHARED_PREFERENCES_NAME = "Apps";
    public static final String GA_TRACKING_ID_PREFERENCE = "ga_id";
    public static double latitude = 0.0;
    public static double longitude = 0.0;

    public static ArrayList<WeatherModel> weatherList = new ArrayList<WeatherModel>();

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static String getStringValueFromPreferences(Context context, String preferenceName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
        return sharedPreferences.getString(preferenceName, null);
    }

    public static byte[] bitmap2bytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap bytes2Bitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


    static public String CheckStringIsNull(String text) {
        String newString = "";
        if (text != null || !text.equalsIgnoreCase("")) {
            newString = text;
        } else {
            newString = "";
        }
        return newString;
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        /**
         * REGEX WITH MINIMUM 6 CHARACTERS AND 1 CAPITAL
         */
//        final String PASSWORD_PATTERN = "^(?=.*[A-Z]).{6,30}$";
//        final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$";


        /**
         * REGEX WITH 1 CAPITAL 1 NUMBER AND 1 UNIX SYMBOL SUCH AS "~!@#$%*()`^&+="
         */
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%*()`^&+=])(?=\\S+$).{4,}$";

        /**
         * REGEX WITH 1 CAPITAL 1 NUMBER
         */
        final String PASSWORD_PATTERN = "^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    @SuppressLint("NewApi")
    static public String Epoch2DateStringtime(String formatString) {
        if (formatString != null) {


//            ZonedDateTime dateTime = Instant.ofEpochMilli(Long.parseLong(formatString)).atZone(ZoneId.of("Asia/Jakarta"));
//            String formatted = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            long itemLong = Long.parseLong(formatString) / 1000;
            java.util.Date d = new java.util.Date(itemLong * 1000L);
//            String itemDateStr = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(d);
            String itemDateStr = new SimpleDateFormat("dd/MM/yyyy, hh:mm a").format(d);
            return itemDateStr;
        } else {
            return "--";
        }
    }

    public static boolean isEmailValid(String email) {
        return !(email == null || TextUtils.isEmpty(email)) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
