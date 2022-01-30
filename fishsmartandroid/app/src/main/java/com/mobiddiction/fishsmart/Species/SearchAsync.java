package com.mobiddiction.fishsmart.Species;

import android.os.AsyncTask;
import android.util.Log;

import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.NewSpecies.NewSpeciesActivity;

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
 * Created by Archa on 16/05/2016.
 */
public class SearchAsync extends AsyncTask<String, Void, Integer> {

    private WeakReference<NewSpeciesActivity> activity;
    ArrayList<SearchModel> SearchList = new ArrayList<>();  //safe

    public SearchAsync(NewSpeciesActivity fragment, ArrayList searchList) {
        activity = new WeakReference<>(fragment);
        this.SearchList = searchList;
    }

    @Override
    protected void onPreExecute() {
        //mActivity.progress_bar.setVisibility(View.VISIBLE);
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
        NewSpeciesActivity context = activity.get();
        if (context != null) {
            // mActivity.progress_bar.setVisibility(View.GONE);
            if (result == 1)
            {
                context.receiveItems();
            }
        }
    }

    private void parseResult(String response) {

        try {

            JSONObject json = new JSONObject(response);

            if (json.has("success") && json.getString("success").equals("true")) {

                JSONArray posts = json.getJSONArray("speciesList");

                for( int i=0; i<posts.length(); i++ )
                {
                    JSONObject object = posts.getJSONObject(i);

                    SearchModel searchlist = new SearchModel();

                    searchlist.setId(object.isNull("id") ? "" : object.getString("id"));
                    searchlist.setTitle(object.isNull("title") ? "" : object.getString("title"));

                    SearchList.add(searchlist);
                }
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}