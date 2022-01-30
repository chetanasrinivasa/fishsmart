package com.mobiddiction.fishsmart.Weather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.CustomAlert.AlertDialog;
import com.mobiddiction.fishsmart.CustomAlert.OnItemClickListener;
import com.mobiddiction.fishsmart.Home.HomeActivity;
import com.mobiddiction.fishsmart.Network.UploadPicAPI;
import com.mobiddiction.fishsmart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import android.content.Context;
import android.widget.Toast;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * Created by Archa on 24/05/2016.
 */
public class WeatherAsync extends AsyncTask<String, Void, Integer> {

    private WeakReference<HomeActivity> activity;
    ArrayList<WeatherModel> WhetherList = new ArrayList<WeatherModel>();  //safe
    ArrayList<forecastModel> ForcastList = new ArrayList<forecastModel>();  //safe
    ArrayList<tideModel> tideList = new ArrayList<tideModel>();  //safe
    ArrayList<astroModel> astroList = new ArrayList<astroModel>();  //safe

    public WeatherAsync(HomeActivity fragment, ArrayList whetherList) {
        activity = new WeakReference<>(fragment);
        this.WhetherList = whetherList;
    }

    @Override
    protected void onPreExecute() {
        HomeActivity context = activity.get();
        if (context != null) {
            context.progress_bar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Integer doInBackground(String... params) {

        Integer result = 0;

        try {

            URL url = new URL(Config.HOST_OLD + params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //  conn.setDoOutput(false);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.setRequestProperty("Accept", "application/json");
            int responseCode = conn.getResponseCode();

            Log.d("WeatherAsync", "whether responsecode " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                Log.d("LOG", "whether response : " + response);

                parseResult(response.toString());

                result = 1; // Successful
            }
            else
            {
                //  parseResult("");
                result = 0;
            }

        }catch(UnknownHostException e){
            Log.d("WeatherAsync" , " UnknownHostException e : " + e.getMessage());
           // connCheck();
        } catch (Exception e) {
            Log.d("WeatherAsync" , " Exception e : ");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(Integer result)
    {
        HomeActivity context = activity.get();
        if (context != null) {
            context.progress_bar.setVisibility(View.GONE);
            if (result == 1)
            {
                context.receiveWeatherItems();
            }
        }
    }

    private void parseResult(String response) {

        try {

            JSONObject json = new JSONObject(response);

//            if (json.has("success") && json.getString("success").equals("true")) {

            JSONObject posts = json.getJSONObject("data");

            for( int i=0; i<posts.length(); i++ )
            {
                WeatherModel list = new WeatherModel();

                JSONObject objectloc = posts.getJSONObject("location");

                for(int j=0; j<objectloc.length(); j++)
                {
                    list.setName(objectloc.isNull("name") ? "" : objectloc.getString("name"));
                    list.setLat(objectloc.isNull("lat") ? "" : objectloc.getString("lat"));
                    list.setLon(objectloc.isNull("lon") ? "" : objectloc.getString("lon"));

                    JSONObject objectastronomy = objectloc.getJSONObject("astronomy");

                    for(int k=0; k<objectastronomy.length(); k++)
                    {
                        try{
                            list.setDayLength(objectastronomy.isNull("dayLength") ? "" : objectastronomy.getString("dayLength"));
                            list.setMoonPhase(objectastronomy.isNull("moonPhase") ? "" : objectastronomy.getString("moonPhase"));
                            list.setDate(objectastronomy.isNull("date") ? "" : objectastronomy.getString("date"));

                            JSONArray objectastronomyTime = objectastronomy.getJSONArray("astronomyTime");

                            for(int l=0; l<objectastronomyTime.length(); l++)
                            {
                                astroModel astrolist = new astroModel();

                                astrolist.setAstronomyType(objectastronomyTime.getJSONObject(l).isNull("astronomyType") ? "" : objectastronomyTime.getJSONObject(l).getString("astronomyType"));
                                astrolist.setHour(objectastronomyTime.getJSONObject(l).isNull("hour") ? "" : objectastronomyTime.getJSONObject(l).getString("hour"));
                                astrolist.setMinute(objectastronomyTime.getJSONObject(l).isNull("minute") ? "" : objectastronomyTime.getJSONObject(l).getString("minute"));

                                astroList.add(astrolist);
                            }

                            list.setAstronomyTime(astroList);

                        }catch (Exception e){

                        }
                    }
                }

                JSONObject objectarea = posts.getJSONObject("area");

                for(int m=0; m<objectarea.length(); m++)
                {

                    try {
                        JSONArray objectforecastPeriod = objectarea.getJSONArray("forecastPeriod");

                        for (int n = 0; n < objectforecastPeriod.length(); n++) {
                            forecastModel forcastlist = new forecastModel();

                            forcastlist.setStartTimeLocal(objectforecastPeriod.getJSONObject(n).isNull("startTimeLocal") ? "" : objectforecastPeriod.getJSONObject(n).getString("startTimeLocal"));
                            forcastlist.setEndTimeLocal(objectforecastPeriod.getJSONObject(n).isNull("endTimeLocal") ? "" : objectforecastPeriod.getJSONObject(n).getString("endTimeLocal"));
                            forcastlist.setStartUtcLocal(objectforecastPeriod.getJSONObject(n).isNull("startUtcLocal") ? "" : objectforecastPeriod.getJSONObject(n).getString("startUtcLocal"));
                            forcastlist.setEndUtcLocal(objectforecastPeriod.getJSONObject(n).isNull("endUtcLocal") ? "" : objectforecastPeriod.getJSONObject(n).getString("endUtcLocal"));
                            forcastlist.setForecast(objectforecastPeriod.getJSONObject(n).isNull("forecast") ? "" : objectforecastPeriod.getJSONObject(n).getString("forecast"));

                            ForcastList.add(forcastlist);

                        }
                    }catch (Exception e){

                    }
                }

                list.setForecastPeriod(ForcastList);

                JSONObject objecttideArea = posts.getJSONObject("tideArea");

                for(int g=0; g < objecttideArea.length(); g++) {
                    try {
                        if(objecttideArea.getJSONArray("tideForecastPeriod") != null){
                            JSONArray objecttideForecastPeriod = objecttideArea.getJSONArray("tideForecastPeriod");
                            for(int t=0; t<objecttideForecastPeriod.length(); t++) {
                                JSONArray objecttideData = objecttideForecastPeriod.getJSONObject(t).getJSONArray("tideData");
                                if(tideList.size() >= 4) {
                                    tideList.removeAll(tideList);
                                }
                                for(int h=0; h<objecttideData.length(); h++){

                                    if(h > 0 && (objecttideData.getJSONObject(h).getString("instance") == null || objecttideData.getJSONObject(h).getString("instance").equalsIgnoreCase(objecttideData.getJSONObject(h-1).getString("instance")))){

                                    }else {
                                        tideModel tidelist = new tideModel();

                                        tidelist.setSequence(objecttideData.getJSONObject(h).isNull("sequence") ? "" : objecttideData.getJSONObject(h).getString("sequence"));
                                        tidelist.setInstance(objecttideData.getJSONObject(h).isNull("instance") ? "" : objecttideData.getJSONObject(h).getString("instance"));
                                        tidelist.setLocalTime(objecttideData.getJSONObject(h).isNull("localTime") ? "" : objecttideData.getJSONObject(h).getString("localTime"));
                                        tidelist.setUtcTime(objecttideData.getJSONObject(h).isNull("utcTime") ? "" : objecttideData.getJSONObject(h).getString("utcTime"));
                                        tidelist.setValue(objecttideData.getJSONObject(h).isNull("value") ? "" : objecttideData.getJSONObject(h).getString("value"));

                                        tideList.add(tidelist);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }

                list.setTideData(tideList);
                list.setLocalTime(posts.getJSONObject("station").getJSONObject("period").getString("localTime"));
                list.setUtcTime(posts.getJSONObject("station").getJSONObject("period").getString("utcTime"));
                list.setStationAltitude(posts.getJSONObject("station").isNull("stationAltitude") ? "" : posts.getJSONObject("station").getString("stationAltitude"));
                list.setTemperature(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("temperature") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("temperature"));
                list.setMaximumTemperature(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("maximumTemperature") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("maximumTemperature"));
                list.setMinimumTemperature(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("minimumTemperature") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("minimumTemperature"));
                list.setHumidity(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("humidity") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("humidity"));
                list.setWindSpeed(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("windSpeed") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("windSpeed"));
                list.setStationPressure(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("stationPressure") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("stationPressure"));
                list.setSeaLevelPressure(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("seaLevelPressure") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("seaLevelPressure"));
                list.setstationDescription(posts.getJSONObject("station").isNull("description") ? "" : posts.getJSONObject("station").getString("description"));
                WhetherList.add(list);
//                }
            }

        } catch (JSONException e) {
            Log.d("WeatherAsync" , " Exception e : " + e.getMessage());

            e.printStackTrace();
        }
    }
    private void connCheck() {
        HomeActivity context = activity.get();
            context.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.showDialogWithAlertHeaderSingleButton(context, "Network", context.getResources().getString(R.string.no_internet_connection),
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {
                                    //
                                }
                            });
                }
            });

    }
}