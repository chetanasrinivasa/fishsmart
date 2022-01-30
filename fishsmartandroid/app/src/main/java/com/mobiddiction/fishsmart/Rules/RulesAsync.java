package com.mobiddiction.fishsmart.Rules;

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
public class RulesAsync extends AsyncTask<String, Void, Integer> {

    RulesActivity.ExploreFragment activity;
    ArrayList<RulesModel> RulesList = new ArrayList<>();  //safe

    public RulesAsync(RulesActivity.ExploreFragment fragment, ArrayList rulesList) {
        activity = fragment;
        this.RulesList = rulesList;
    }

    @Override
    protected void onPreExecute() {
           activity.progress_bar.setVisibility(View.VISIBLE);
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

                Log.d("LOG", "Rules response : " + response);

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
        activity.progress_bar.setVisibility(View.GONE);
        if (result == 1)
        {
           // activity.setUserVisibleHint(true);
            activity.RulesListDownloaded();
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

                    RulesModel ruleslist = new RulesModel();

                  //  ruleslist.setFishingGuideType(object.isNull("type") ? "" : object.getString("type"));

                    ruleslist.setFishingGuideType(object.getJSONObject("fishingGuideType").isNull("type") ? "" : object.getJSONObject("fishingGuideType").getString("type"));

                    JSONArray listtype = object.getJSONArray("listOfFishingGuideForEachType");

                    ArrayList<ListTypeModel> FishTypeList = new ArrayList<>();  //safe

                    for(int j=0; j<listtype.length(); j++)
                    {
                        ListTypeModel typelistdata = new ListTypeModel();

                        typelistdata.setId(listtype.getJSONObject(j).isNull("id") ? "" : listtype.getJSONObject(j).getString("id"));
                        typelistdata.setTitle(listtype.getJSONObject(j).isNull("title") ? "" : listtype.getJSONObject(j).getString("title"));
                        typelistdata.setDescription(listtype.getJSONObject(j).isNull("description") ? "" : listtype.getJSONObject(j).getString("description"));
                        typelistdata.setPdfUrl(listtype.getJSONObject(j).isNull("pdfUrl") ? "" : listtype.getJSONObject(j).getString("pdfUrl"));

                        FishTypeList.add(typelistdata);
                    }

                    ruleslist.setListtypemodel(FishTypeList);

                    RulesList.add(ruleslist);
                }
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}