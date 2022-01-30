package com.mobiddiction.fishsmart.Weather;

import android.os.AsyncTask;
import android.util.Log;

import com.mobiddiction.fishsmart.Configuration.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Archa on 25/05/2016.
 */
public class LocWeatherAsync  extends AsyncTask<String, Void, Integer> {

    private WeakReference<WeatherActivity> activity;
    ArrayList<WeatherModel> WhetherList = new ArrayList<WeatherModel>();  //safe
    ArrayList<forecastModel> ForcastList = new ArrayList<forecastModel>();  //safe
    ArrayList<tideModel> tideList = new ArrayList<tideModel>();  //safe
    ArrayList<astroModel> astroList = new ArrayList<astroModel>();  //safe

    public LocWeatherAsync(WeatherActivity fragment, ArrayList whetherList) {
        activity = new WeakReference<>(fragment);
        this.WhetherList = whetherList;
    }

    @Override
    protected void onPreExecute() {
        //  activity.progress_bar.setVisibility(View.VISIBLE);
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
            Log.d("URLLLLLLLLLLLLLLLLL", "url:  " + url);
            int responseCode = conn.getResponseCode();

            Log.d("URLLLLLLLLLLLLLLLLL", "whether responsecode " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                Log.d("URLLLLLLLLLLLLLLLLL", "whether response : " + response);

                parseResult(response.toString());

                result = 1; // Successful
            }
            else
            {
                //  parseResult("");
                result = 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(Integer result)
    {
        WeatherActivity context = activity.get();
        if (context != null) {
            // activity.progress_bar.setVisibility(View.GONE);
            if (result == 1)
            {
                context.receiveItems();
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
                        }
                    }

                    JSONObject objectarea = posts.getJSONObject("area");

                    for(int m=0; m<objectarea.length(); m++)
                    {
                        JSONArray objectforecastPeriod = objectarea.getJSONArray("forecastPeriod");

                        for(int n=0; n<objectforecastPeriod.length(); n++)
                        {
                            forecastModel forcastlist = new forecastModel();

                            forcastlist.setStartTimeLocal(objectforecastPeriod.getJSONObject(n).isNull("startTimeLocal") ? "" : objectforecastPeriod.getJSONObject(n).getString("startTimeLocal"));
                            forcastlist.setEndTimeLocal(objectforecastPeriod.getJSONObject(n).isNull("endTimeLocal") ? "" : objectforecastPeriod.getJSONObject(n).getString("endTimeLocal"));
                            forcastlist.setStartUtcLocal(objectforecastPeriod.getJSONObject(n).isNull("startUtcLocal") ? "" : objectforecastPeriod.getJSONObject(n).getString("startUtcLocal"));
                            forcastlist.setEndUtcLocal(objectforecastPeriod.getJSONObject(n).isNull("endUtcLocal") ? "" : objectforecastPeriod.getJSONObject(n).getString("endUtcLocal"));
                            forcastlist.setForecast(objectforecastPeriod.getJSONObject(n).isNull("forecast") ? "" : objectforecastPeriod.getJSONObject(n).getString("forecast"));

                            ForcastList.add(forcastlist);
                        }
                    }

                    list.setForecastPeriod(ForcastList);

                    JSONObject objecttideArea = posts.getJSONObject("tideArea");

                    for(int g=0; g<objecttideArea.length(); g++)
                    {
                        try {
                            JSONArray objecttideForecastPeriod = objecttideArea.getJSONArray("tideForecastPeriod");

                            for (int t = 0; t < objecttideForecastPeriod.length(); t++) {
                                JSONArray objecttideData = objecttideForecastPeriod.getJSONObject(t).getJSONArray("tideData");

                                if (tideList.size() >= 4) {
                                    tideList.removeAll(tideList);
                                }
                                for (int h = 0; h < objecttideData.length(); h++) {
                                    if (h > 0 && (objecttideData.getJSONObject(h).getString("instance") == null || objecttideData.getJSONObject(h).getString("instance").equalsIgnoreCase(objecttideData.getJSONObject(h - 1).getString("instance")))) {

                                    } else {
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
                        }catch (Exception e){

                        }
                    }

                    list.setTideData(tideList);

                    list.setStationAltitude(posts.getJSONObject("station").isNull("stationAltitude") ? "" : posts.getJSONObject("station").getString("stationAltitude"));

                    list.setstationDescription(posts.getJSONObject("station").getString("description"));
                    list.setLocalTime(posts.getJSONObject("station").getJSONObject("period").getString("localTime"));
                    list.setUtcTime(posts.getJSONObject("station").getJSONObject("period").getString("utcTime"));



                    list.setTemperature(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("temperature") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("temperature"));
                    list.setMaximumTemperature(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("maximumTemperature") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("maximumTemperature"));
                    list.setMinimumTemperature(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("minimumTemperature") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("minimumTemperature"));
                    list.setHumidity(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("humidity") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("humidity"));
                    list.setWindSpeed(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("windSpeed") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("windSpeed"));
                    list.setStationPressure(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("stationPressure") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("stationPressure"));
                    list.setSeaLevelPressure(posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").isNull("seaLevelPressure") ? "" : posts.getJSONObject("station").getJSONObject("period").getJSONObject("level").getString("seaLevelPressure"));

                    WhetherList.add(list);
//                }
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}