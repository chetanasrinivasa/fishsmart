package com.mobiddiction.fishsmart.notification;

import android.annotation.SuppressLint;
import android.os.Build;

import com.mobiddiction.fishsmart.dao.NEWSpeciesData;

import java.text.SimpleDateFormat;
import java.util.List;

public class AppConstant {

    public List<NEWSpeciesData> featuredSpecies = null;

    public List<NEWSpeciesData> getFeaturedSpecies(){
        return this.featuredSpecies;
    }

    public void setFeaturedSpecies(List<NEWSpeciesData> mList){
        this.featuredSpecies = mList;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    @SuppressLint("NewApi")
    static public String Epoch2DateStringtime(String formatString) {
        if(formatString != null) {


//            ZonedDateTime dateTime = Instant.ofEpochMilli(Long.parseLong(formatString)).atZone(ZoneId.of("Asia/Jakarta"));
//            String formatted = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            long itemLong = Long.parseLong(formatString) / 1000;
            java.util.Date d = new java.util.Date(itemLong * 1000L);
//            String itemDateStr = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(d);
            String itemDateStr = new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(d);
            return itemDateStr;
        }else{
            return "--";
        }
    }
}