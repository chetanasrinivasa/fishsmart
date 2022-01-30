package com.mobiddiction.fishsmart.Species;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.mobiddiction.fishsmart.Configuration.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Archa on 12/10/2015.
 */
public class FreshWaterAsync extends AsyncTask<String, Void, Integer> {

    SpeciesActivity.FreshwaterFragment activity;
    SpeciesDownloader downloader;
    ArrayList<SpeciesModel> SpeciesList = new ArrayList<>();  //safe

    public FreshWaterAsync(SpeciesActivity.FreshwaterFragment fragment, ArrayList speciesList) {
        activity = fragment;
        this.SpeciesList = speciesList;
    }

    public FreshWaterAsync(SpeciesDownloader downloader, ArrayList tempList) {
        this.downloader = downloader;
        this.SpeciesList = tempList;
    }

    @Override
    protected void onPreExecute() {
        if (activity != null) {
            activity.progress_bar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Integer doInBackground(String... params) {

        Integer result = 0;

        try {

            URL url = new URL(Config.HOST + params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //  conn.setDoOutput(false);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

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
        if (activity != null) {
            activity.progress_bar.setVisibility(View.GONE);
            if (result == 1) {
                activity.receiveItems();
            }
        } else if (downloader != null) {
            downloader.hasDownloadedFreshWater();
        }
    }

    private void parseResult(String response) {

        try {

            JSONObject json = new JSONObject(response);

            if (json.has("success") && json.getString("success").equals("true")) {

                JSONArray posts = json.getJSONArray("data");

                for( int i=0; i<posts.length(); i++ )
                {
                    JSONObject object = posts.getJSONObject(i);

                    SpeciesModel specieslist = new SpeciesModel();

                    specieslist.setId(object.isNull("id") ? "" : object.getString("id"));
                    specieslist.setSpeciesType(object.isNull("speciesType") ? "" : object.getString("speciesType"));
                    specieslist.setTitle(object.isNull("title") ? "" : object.getString("title"));
                    specieslist.setDescription(object.isNull("description") ? "" : object.getString("description"));
                    specieslist.setBagLimitForCardView(object.isNull("bagLimitForCardView") ? "" : object.getString("bagLimitForCardView"));
                    specieslist.setSizeLimit(object.isNull("sizeLimit") ? "" : object.getString("sizeLimit"));
                    specieslist.setImage(object.isNull("image") ? "" : object.getString("image"));
                    specieslist.setThumbnail(object.isNull("thumbnail") ? "" : object.getString("thumbnail"));
                    specieslist.setGroupName(object.isNull("groupName") ? "" : object.getString("groupName"));
                    specieslist.setGrouped(!object.isNull("grouped") && object.getBoolean("grouped"));
                    SpeciesList.add(specieslist);
                }
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}