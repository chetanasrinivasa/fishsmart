package com.mobiddiction.fishsmart.Species;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.mobiddiction.fishsmart.Configuration.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Archa on 4/05/2016.
 */
public class SpeciesDetailAsync extends AsyncTask<String, Void, Integer> {
    private WeakReference<SpeciesDetailActivity> activity;
    SpeciesActivity.SavedSpeciesFragment fragment;
    private WeakReference<Context> context;

    String id;
    ArrayList<SpeciesDetailModel> SpeciesDetailList = new ArrayList<>();  //safe

    public SpeciesDetailAsync(SpeciesActivity.SavedSpeciesFragment fragment, ArrayList speciesdetailList) {
        this.fragment = fragment;
        this.SpeciesDetailList = speciesdetailList;
    }

    public SpeciesDetailAsync(SpeciesDetailActivity fragment, ArrayList speciesdetailList) {
        activity = new WeakReference<>(fragment);
        this.SpeciesDetailList = speciesdetailList;
    }

    public SpeciesDetailAsync(Context context, ArrayList speciesdetailList) {
        this.context = new WeakReference<>(context);
        this.SpeciesDetailList = speciesdetailList;
    }

    @Override
    protected void onPreExecute() {
        SpeciesDetailActivity context = activity.get();
        if (context != null) {
            context.progress_bar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Integer doInBackground(String... params) {

        Integer result = 0;

        try {

            URL url = new URL(Config.HOST + params[0]);
            Log.d("LOG", "url " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //  conn.setDoOutput(false);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            int responseCode = conn.getResponseCode();

            Log.d("LOG", "detail responsecode " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                Log.d("LOG", "Speciesdetail response : " + response);

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
        Context context = this.context.get();
        if (context != null) {
            if (result == 1)
            {
                SpeciesDownloader speciesDownloader = SpeciesDownloader.getInstance();
                SpeciesDownloader.saveSpeciesDetails(context, SpeciesDetailList);
                SpeciesDetailActivity activity = this.activity.get();
                if (activity != null) {
                    activity.DisplayDetail(id);
                    activity.progress_bar.setVisibility(View.GONE);
                } else if (fragment != null) {
                    fragment.downloadDone(id);
                }


            }
        }
    }

    private void parseResult(String response) {

        try {

            JSONObject json = new JSONObject(response);

            if (json.has("success") && json.getString("success").equals("true")) {

                JSONObject posts = json.getJSONObject("data");
                Context context = this.context.get();
                if (context != null) {
                    SpeciesDetailActivity activity = this.activity.get();
                    for( int i=0; i<posts.length(); i++ )
                    {
                        id = posts.getString("id");

                        SpeciesDetailModel speciesdetaillist = new SpeciesDetailModel();

                        speciesdetaillist.id = id;
                        speciesdetaillist.setTitle(posts.isNull("title") ? "" : posts.getString("title"));
                        speciesdetaillist.setDescription(posts.isNull("description") ? "" : posts.getString("description"));
                        speciesdetaillist.setGrouped(posts.getBoolean("grouped"));
                        speciesdetaillist.setSpeciesType(posts.isNull("speciesType") ? "" : posts.getString("speciesType"));
                        speciesdetaillist.setSizeLimit(posts.isNull("sizeLimit") ? "" : posts.getString("sizeLimit"));
                        speciesdetaillist.setThumbnailImage(posts.isNull("thumbnailImage") ? "" : posts.getString("thumbnailImage"));

                        if (activity != null) {
                            context = activity.getApplicationContext();
                        } else if (fragment != null) {
                            context = fragment.getContext();
                        }
                        FutureTarget<File> future = Glide.with(context)
                                .load(speciesdetaillist.getThumbnailImage())
                                .downloadOnly(500,500);


                        if(posts.isNull("fishGroup"))
                        {
                            speciesdetaillist.setFishGroupName("");
                            speciesdetaillist.setFishGroupTitle("");
                            speciesdetaillist.setFishGroupDesc("");
                        }
                        else
                        {
                            speciesdetaillist.setFishGroupName(posts.getJSONObject("fishGroup").isNull("groupName") ? "" : posts.getJSONObject("fishGroup").getString("groupName"));
                            speciesdetaillist.setFishGroupTitle(posts.getJSONObject("fishGroup").isNull("groupTitle") ? "" : posts.getJSONObject("fishGroup").getString("groupTitle"));
                            speciesdetaillist.setFishGroupDesc(posts.getJSONObject("fishGroup").isNull("groupDescription") ? "" : posts.getJSONObject("fishGroup").getString("groupDescription"));
                        }

                        speciesdetaillist.setAbout(posts.getJSONObject("fishFacts").isNull("about") ? "" : posts.getJSONObject("fishFacts").getString("about"));
                        speciesdetaillist.setSize(posts.getJSONObject("fishFacts").isNull("size") ? "" : posts.getJSONObject("fishFacts").getString("size"));
                        speciesdetaillist.setConfusingSpecies(posts.getJSONObject("fishFacts").isNull("confusingSpecies") ? "" : posts.getJSONObject("fishFacts").getString("confusingSpecies"));
                        speciesdetaillist.setCharacteristics(posts.getJSONObject("fishFacts").isNull("characteristics") ? "" : posts.getJSONObject("fishFacts").getString("characteristics"));

                        speciesdetaillist.setLegalSize(posts.getJSONObject("rules").isNull("legalSize") ? "" : posts.getJSONObject("rules").getString("legalSize"));
                        speciesdetaillist.setBagLimit(posts.getJSONObject("rules").isNull("bagLimit") ? "" : posts.getJSONObject("rules").getString("bagLimit"));
                        speciesdetaillist.setPossession(posts.getJSONObject("rules").isNull("possession") ? "" : posts.getJSONObject("rules").getString("possession"));

                        SpeciesDetailList.add(speciesdetaillist);
                    }
                }
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}