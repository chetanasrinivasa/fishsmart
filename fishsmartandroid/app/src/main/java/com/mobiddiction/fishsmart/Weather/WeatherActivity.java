package com.mobiddiction.fishsmart.Weather;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.MyApplication;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.ScreenEnum;
import com.mobiddiction.fishsmart.WebviewActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Archa on 24/05/2016.
 */
public class WeatherActivity extends AppCompatActivity {

    TextView loctitle, locdesc, temptext, highlowtext, humiditytext, sunrisetext, windtext, sunsettext, texttide;
    TextView textbarometric, text5, text6, text7, stpretext, stalttext, seapretext, textmoon, text8, text9, nowtext, daylentext, textseemore, weatherheading, textcallwebview;
    TextView tidetext1, tidetext2, tidetext3, tidetext4, tidetext5, tidedata1, tidedata2, tidedata3, tidedata4, tidedata5;
    LinearLayout weblayout, linear6, linear7, linear8, linear9, linear10;
    ImageButton searchbtn, backbtn, infobtn;
    ListView locationlist;
    LocSearchAdapter adapter;
    ArrayList<LocSearchModel> SearchList = new ArrayList<LocSearchModel>();
    String lat = "";
    String lon = "";
    public Dialog dialog = null;
    private boolean isSearchCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whether);

        //BaseTabActivity.HomeTextChanger("WEATHER DETAILS", this);

        //((MyApplication) getApplication()).getTrackingManager().trackScreen(ScreenEnum.WEATHER_DETAILS, null);
        FirebaseAnalytics.getInstance(getApplicationContext()).setCurrentScreen(this,ScreenEnum.WEATHER_DETAILS, null);


        new LocSearchAsync(WeatherActivity.this, SearchList).execute("/api/v1/station"); // api for location search


        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("lon");

        if (lat != "" && lon != "") {
            new LocWeatherAsync(WeatherActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + lat + "&lon=" + lon);
        } else if (ActivityCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            new LocWeatherAsync(WeatherActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
        } else {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            final Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                new LocWeatherAsync(WeatherActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
            } else {
                new LocWeatherAsync(WeatherActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
            }
        }
        textcallwebview = findViewById(R.id.textcallwebview);
        textcallwebview.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        textcallwebview.setText(Html.fromHtml("For detailed tidal information and tidal variation at your location :    <font color=\"#EA5A23\"><b>go here</b></font>"));

        tidetext1 = findViewById(R.id.tidetext1);
        tidetext1.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        tidetext2 = findViewById(R.id.tidetext2);
        tidetext2.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        tidetext3 = findViewById(R.id.tidetext3);
        tidetext3.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        tidetext4 = findViewById(R.id.tidetext4);
        tidetext4.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        tidetext5 = findViewById(R.id.tidetext5);
        tidetext5.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        tidedata1 = findViewById(R.id.tidedata1);
        tidedata1.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        tidedata2 = findViewById(R.id.tidedata2);
        tidedata2.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        tidedata3 = findViewById(R.id.tidedata3);
        tidedata3.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        tidedata4 = findViewById(R.id.tidedata4);
        tidedata4.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        tidedata5 = findViewById(R.id.tidedata5);
        tidedata5.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        loctitle = findViewById(R.id.loctitle);
        loctitle.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Bold.otf"));
        locdesc = findViewById(R.id.locdesc);
        locdesc.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        temptext = findViewById(R.id.temptext);
        temptext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Bold.otf"));
        highlowtext = findViewById(R.id.highlowtext);
        highlowtext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        humiditytext = findViewById(R.id.humiditytext);
        humiditytext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        sunrisetext = findViewById(R.id.sunrisetext);
        sunrisetext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        windtext = findViewById(R.id.windtext);
        windtext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        sunsettext = findViewById(R.id.sunsettext);
        sunsettext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        texttide = findViewById(R.id.texttide);
        texttide.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Bold.otf"));

        textbarometric = findViewById(R.id.textbarometric);
        textbarometric.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Bold.otf"));

        text5 = findViewById(R.id.text5);
        text5.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        text6 = findViewById(R.id.text6);
        text6.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        text7 = findViewById(R.id.text7);
        text7.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        stpretext = findViewById(R.id.stpretext);
        stpretext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        stalttext = findViewById(R.id.stalttext);
        stalttext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        seapretext = findViewById(R.id.seapretext);
        seapretext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        textmoon = findViewById(R.id.textmoon);
        textmoon.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Bold.otf"));
        text8 = findViewById(R.id.text8);
        text8.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        text9 = findViewById(R.id.text9);
        text9.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        nowtext = findViewById(R.id.nowtext);
        nowtext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        daylentext = findViewById(R.id.daylentext);
        daylentext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        textseemore = findViewById(R.id.textseemore);
        textseemore.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        weatherheading = findViewById(R.id.weatherheading);
        weatherheading.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

        weblayout = findViewById(R.id.weblayout);
        searchbtn = findViewById(R.id.searchbtn);
        backbtn = findViewById(R.id.backbtn);
        infobtn = findViewById(R.id.infobtn);

        linear6 = findViewById(R.id.linear6);
        linear7 = findViewById(R.id.linear7);
        linear8 = findViewById(R.id.linear8);
        linear9 = findViewById(R.id.linear9);
        linear10 = findViewById(R.id.linear10);

        locationlist = findViewById(R.id.locationlist);

        weblayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WeatherActivity.this, WebviewActivity.class);
                intent.putExtra("fs", "http://bom.gov.au");
                intent.putExtra("share", false);
                startActivity(intent);

            }
        });

        textcallwebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WeatherActivity.this, WebviewActivity.class);
                intent.putExtra("fs", "http://bit.ly/1U7tDIj");
                intent.putExtra("share", true);
                startActivity(intent);

            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSearchCall = true;
                weatherheading.setText("Select a location");
                locationlist.setVisibility(View.VISIBLE);
                // backbtn.setVisibility(View.VISIBLE);
                infobtn.setVisibility(View.GONE);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSearchCall) {
                    weatherheading.setText("WEATHER DETAILS");
                    locationlist.setVisibility(View.GONE);
                    // backbtn.setVisibility(View.GONE);
                    infobtn.setVisibility(View.GONE);
                    isSearchCall = false;
                } else {
                    finish();
                }
            }
        });

        adapter = new LocSearchAdapter(this, R.layout.searchsuggestionitem, SearchList);
        locationlist.setAdapter(adapter);


        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {

                        public void run() {

                            LayoutInflater inflater = getLayoutInflater();
                            final View dialoglayout = inflater.inflate(R.layout.first_popup, null);
                            YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                            TextView title = dialoglayout.findViewById(R.id.title);
                            TextView desc = dialoglayout.findViewById(R.id.desc);
                            Button thanksbtn = dialoglayout.findViewById(R.id.thanksbtn);

                            title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                            desc.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                            thanksbtn.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

                            title.setText("Disclaimer");
                            desc.setText("This mobile app is a guide only, it does not replace the Fisheries Management Act 1994 or other acts and statutory rules applying to, or affecting recreational fishing. It cannot be used as a defence in a court of law.");

                            dialog = new Dialog(WeatherActivity.this);

                            dialog.setCanceledOnTouchOutside(false);

                            thanksbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    dialog.dismiss();
                                }
                            });

                            int divierId = dialog.getContext().getResources()
                                    .getIdentifier("android:id/titleDivider", null, null);
                            View divider = dialog.findViewById(divierId);
                            if (divider != null)
                                divider.setVisibility(View.INVISIBLE);

                            dialog.setContentView(dialoglayout);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.show();

                        }
                    });


                } catch (Exception ix) {

                }
            }
        });

    }

    static public Date LocalStringToDate(String stringToConvert) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date returnDate = null;
        try {
            returnDate = dateFormat.parse(stringToConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnDate;
    }

    public void receiveItems() {
        //2017-07-19T07:40:00Z

        DateFormat dateFormatTo = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
        dateFormatTo.setTimeZone(TimeZone.getDefault());
        if(Config.weatherList.size() > 0 ) {
            //Toast.makeText(this, "Weather Last Updated : " + dateFormatTo.format(LocalStringToDate(Config.weatherList.get(0).getUtcTime())), Toast.LENGTH_SHORT).show();
        }

        for(int i=0; i< Config.weatherList.size(); i++)
        {

            loctitle.setText(Config.weatherList.get(i).getDescription());
            stpretext.setText(Config.weatherList.get(i).getStationPressure());
            stalttext.setText(Config.weatherList.get(i).getStationAltitude());
            seapretext.setText(Config.weatherList.get(i).getSeaLevelPressure());

            if(Config.weatherList.get(i).getMoonPhase().equals("firstquarter"))
            {
                nowtext.setText("First Quarter");
            }
            else if(Config.weatherList.get(i).getMoonPhase().equals("secondquarter"))
            {
                nowtext.setText("Second Quarter");
            }
            else if(Config.weatherList.get(i).getMoonPhase().equals("thirdquarter"))
            {
                nowtext.setText("Third Quarter");
            }
            else if(Config.weatherList.get(i).getMoonPhase().equals("waxingcrescent"))
            {
                nowtext.setText("Waxing Crescent");
            }
            else if(Config.weatherList.get(i).getMoonPhase().equals("waningcrescent"))
            {
                nowtext.setText("Waning Crescent");
            }
            else if(Config.weatherList.get(i).getMoonPhase().equals("waxinggibbous"))
            {
                nowtext.setText("Waxing Gibbous");
            }
            else if(Config.weatherList.get(i).getMoonPhase().equals("waninggibbous"))
            {
                nowtext.setText("Waning Gibbous");
            }
            else if(Config.weatherList.get(i).getMoonPhase().equals("fullmoon"))
            {
                nowtext.setText("Full Moon");
            }
            else if(Config.weatherList.get(i).getMoonPhase().equals("newmoon"))
            {
                nowtext.setText("New Moon");
            }

            daylentext.setText(Config.weatherList.get(i).getDayLength()+" hours");

            String[] tempsplit = Config.weatherList.get(i).getTemperature().split("C");
            String[] maxtemp = Config.weatherList.get(i).getMaximumTemperature().split("C");
            String[] mintemp = Config.weatherList.get(i).getMinimumTemperature().split("C");

            humiditytext.setText("Humidity: "+Config.weatherList.get(i).getHumidity());
            windtext.setText("Wind: "+Config.weatherList.get(i).getWindSpeed());

            temptext.setText("Current Temperature: "+tempsplit[0]+"°");
            highlowtext.setText("H: "+maxtemp[0]+"°  L: "+mintemp[0]+"°");

            for(int j=0; j<Config.weatherList.get(i).getForecastPeriod().size(); j++)
            {
                String[] splitdate = Config.weatherList.get(i).getForecastPeriod().get(j).getStartTimeLocal().split("T");

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();

                if(dateFormat.format(date).equals(splitdate[0]))
                {
                    locdesc.setText(Config.weatherList.get(i).getForecastPeriod().get(j).getForecast());
                }
            }

            for(int k=0; k<Config.weatherList.get(i).getAstronomyTime().size(); k++)
            {
                if(Config.weatherList.get(i).getAstronomyTime().get(k).getAstronomyType().equals("RISE"))
                {
                    String hour = Config.weatherList.get(i).getAstronomyTime().get(k).getHour();
                    String mins = Config.weatherList.get(i).getAstronomyTime().get(k).getMinute();
                    if(hour.length() == 1){
                        hour = "0"+Config.weatherList.get(i).getAstronomyTime().get(k).getHour();
                    }

                    if(mins.length() == 1){
                        mins = "0"+Config.weatherList.get(i).getAstronomyTime().get(k).getMinute();
                    }
                    sunrisetext.setText("Sunrise: "+hour+":"+mins+"am");
                }
                if(Config.weatherList.get(i).getAstronomyTime().get(k).getAstronomyType().equals("SET"))
                {

                    int hourString = (Integer.parseInt(Config.weatherList.get(i).getAstronomyTime().get(k).getHour())-12);
                    String hour = hourString+"";
                    String mins = Config.weatherList.get(i).getAstronomyTime().get(k).getMinute();
                    if(hour.length() == 1){
                        hour = "0"+hourString+"";
                    }

                    if(mins.length() == 1){
                        mins = "0"+Config.weatherList.get(i).getAstronomyTime().get(k).getMinute();
                    }

                    sunsettext.setText("Sunset: "+hour+":"+mins+"pm");
                }

            }
            for(int l=0; l<Config.weatherList.get(i).getTideData().size(); l++)
            {

                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                DateFormat outputFormat = new SimpleDateFormat("hh:mm a");

                try {

                    String[] value = Config.weatherList.get(i).getTideData().get(l).getValue().split(" ");

                    if(Config.weatherList.get(i).getTideData().get(l).getSequence().equals("1"))
                    {
                        linear6.setVisibility(View.VISIBLE);
                        if(Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low"))
                        {
                            tidetext1.setText("Low tide");
                            tidedata1.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                        else
                        {
                            tidetext1.setText("High tide");
                            tidedata1.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if(Config.weatherList.get(i).getTideData().get(l).getSequence().equals("2"))
                    {
                        linear7.setVisibility(View.VISIBLE);
                        if(Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low"))
                        {
                            tidetext2.setText("Low tide");
                            tidedata2.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                        else
                        {
                            tidetext2.setText("High tide");
                            tidedata2.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if(Config.weatherList.get(i).getTideData().get(l).getSequence().equals("3"))
                    {
                        linear8.setVisibility(View.VISIBLE);
                        if(Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low"))
                        {
                            tidetext3.setText("Low tide");
                            tidedata3.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                        else
                        {
                            tidetext3.setText("High tide");
                            tidedata3.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if(Config.weatherList.get(i).getTideData().get(l).getSequence().equals("4"))
                    {
                        linear9.setVisibility(View.VISIBLE);
                        if(Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low"))
                        {
                            tidetext4.setText("Low tide");
                            tidedata4.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                        else
                        {
                            tidetext4.setText("High tide");
                            tidedata4.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }
                    if(Config.weatherList.get(i).getTideData().get(l).getSequence().equals("5"))
                    {
                        linear10.setVisibility(View.VISIBLE);
                        if(Config.weatherList.get(i).getTideData().get(l).getInstance().equals("low"))
                        {
                            tidetext5.setText("Low tide");
                            tidedata5.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                        else
                        {
                            tidetext5.setText("High tide");
                            tidedata5.setText(value[0] + " m - " + outputFormat.format(inputFormat.parse(Config.weatherList.get(i).getTideData().get(l).getLocalTime())));
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1000:
                switch (resultCode) {
                    case 1:

                        if (ActivityCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            new LocWeatherAsync(WeatherActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
                        } else {
                            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            final Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                            if (location != null) {
                                new LocWeatherAsync(WeatherActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
                            } else {
                                new LocWeatherAsync(WeatherActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
                            }
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();


        if (lat != "" && lon != "") {
            new LocWeatherAsync(WeatherActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + lat + "&lon=" + lon);

        } else if (ActivityCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            new LocWeatherAsync(WeatherActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
        } else {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            final Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                new LocWeatherAsync(WeatherActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude());
            } else {
                new LocWeatherAsync(WeatherActivity.this, Config.weatherList).execute("/api/v1/weather?lat=" + "-33.866906" + "&lon=" + "151.208896");
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isSearchCall) {
            weatherheading.setText("WEATHER DETAILS");
            locationlist.setVisibility(View.GONE);
            infobtn.setVisibility(View.GONE);
            isSearchCall = false;
        } else {
            finish();
        }
    }
}