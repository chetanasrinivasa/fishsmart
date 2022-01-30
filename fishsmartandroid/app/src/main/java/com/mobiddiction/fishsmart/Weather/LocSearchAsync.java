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
public class LocSearchAsync extends AsyncTask<String, Void, Integer> {

    private WeakReference<WeatherActivity> activity;
    ArrayList<LocSearchModel> SearchList = new ArrayList<LocSearchModel>();  //safe

    public LocSearchAsync(WeatherActivity fragment, ArrayList searchList) {
        activity = new WeakReference<>(fragment);
        this.SearchList = searchList;
    }

    @Override
    protected void onPreExecute() {
        //activity.progress_bar.setVisibility(View.VISIBLE);
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
            conn.setRequestProperty("accept", "application/json");
            int responseCode = conn.getResponseCode();

            Log.d("LOG", "responsecode " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                Log.d("LOG", "Species response : " + response);

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

                JSONArray posts = json.getJSONArray("data");

                for( int i=0; i<posts.length(); i++ )
                {
                    JSONObject object = posts.getJSONObject(i);

                    LocSearchModel searchlist = new LocSearchModel();

                    searchlist.setId(object.isNull("id") ? "" : object.getString("id"));
                    searchlist.setName(object.isNull("name") ? "" : object.getString("name"));
                    searchlist.setLat(object.isNull("lat") ? "" : object.getString("lat"));
                    searchlist.setLon(object.isNull("lon") ? "" : object.getString("lon"));

                    SearchList.add(searchlist);
                }
//            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}