package com.mobiddiction.fishsmart.More;

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
public class MoreContactAsync extends AsyncTask<String, Void, Integer> {

    MoreActivity.ContactFragment activity;
    ArrayList<MoreModel> MoreList = new ArrayList<>();  //safe

    public MoreContactAsync(MoreActivity.ContactFragment fragment, ArrayList moreList) {
        activity = fragment;
        this.MoreList = moreList;
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
//           activity.receiveItems();
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

                    if(object.getString("contactAndLicensingType").equals("CONTACT"))
                    {
                        JSONArray postsdata = object.getJSONArray("listOfData");

                        for( int j=0; j<postsdata.length(); j++ ) {

                            JSONObject object1 = postsdata.getJSONObject(j);


                            if (object1.getString("contactAndLicensingSubType").equals("REPORT")) {
                                JSONArray postsdata1 = object1.getJSONArray("listOfData");

                                for (int k = 0; k < postsdata1.length(); k++) {
                                    MoreModel morelist = new MoreModel();

                                    morelist.setContactAndLicensingType(postsdata1.getJSONObject(k).isNull("contactAndLicensingSubType") ? "" : postsdata1.getJSONObject(k).getString("contactAndLicensingSubType"));
                                    morelist.setTitle(postsdata1.getJSONObject(k).isNull("title") ? "" : postsdata1.getJSONObject(k).getString("title"));
                                    morelist.setDescription(postsdata1.getJSONObject(k).isNull("description") ? "" : postsdata1.getJSONObject(k).getString("description"));
                                    morelist.setUrl(postsdata1.getJSONObject(k).isNull("url") ? "" : postsdata1.getJSONObject(k).getString("url"));
                                    morelist.setPhone(postsdata1.getJSONObject(k).isNull("phone") ? "" : postsdata1.getJSONObject(k).getString("phone"));
                                    morelist.setMobile(postsdata1.getJSONObject(k).isNull("mobile") ? "" : postsdata1.getJSONObject(k).getString("mobile"));
                                    morelist.setSubType("REPORT");

                                    MoreList.add(morelist);
                                }
                            }

                            if(object1.getString("contactAndLicensingSubType").equals("CONTACTUS"))
                            {
                                JSONArray postsdata1 = object1.getJSONArray("listOfData");

                                for( int k=0; k<postsdata1.length(); k++)
                                {
                                    MoreModel morelist = new MoreModel();

                                    morelist.setContactAndLicensingType(postsdata1.getJSONObject(k).isNull("contactAndLicensingSubType") ? "" : postsdata1.getJSONObject(k).getString("contactAndLicensingSubType"));
                                    morelist.setTitle(postsdata1.getJSONObject(k).isNull("title") ? "" : postsdata1.getJSONObject(k).getString("title"));
                                    morelist.setDescription(postsdata1.getJSONObject(k).isNull("description") ? "" : postsdata1.getJSONObject(k).getString("description"));
                                    morelist.setUrl(postsdata1.getJSONObject(k).isNull("url") ? "" : postsdata1.getJSONObject(k).getString("url"));
                                    morelist.setPhone(postsdata1.getJSONObject(k).isNull("phone") ? "" : postsdata1.getJSONObject(k).getString("phone"));
                                    morelist.setMobile(postsdata1.getJSONObject(k).isNull("mobile") ? "" : postsdata1.getJSONObject(k).getString("mobile"));
                                    morelist.setSubType("CONTACTUS");

                                    MoreList.add(morelist);
                                }
                            }
                            if(object1.getString("contactAndLicensingSubType").equals("VISITAFISHERIESOFFICE"))
                            {
                                JSONArray postsdata1 = object1.getJSONArray("listOfData");

                                for( int k=0; k<postsdata1.length(); k++)
                                {
                                    MoreModel morelist = new MoreModel();

                                    morelist.setContactAndLicensingType(postsdata1.getJSONObject(k).isNull("contactAndLicensingSubType") ? "" : postsdata1.getJSONObject(k).getString("contactAndLicensingSubType"));
                                    morelist.setTitle(postsdata1.getJSONObject(k).isNull("title") ? "" : postsdata1.getJSONObject(k).getString("title"));
                                    morelist.setDescription(postsdata1.getJSONObject(k).isNull("description") ? "" : postsdata1.getJSONObject(k).getString("description"));
                                    morelist.setUrl(postsdata1.getJSONObject(k).isNull("url") ? "" : postsdata1.getJSONObject(k).getString("url"));
                                    morelist.setPhone(postsdata1.getJSONObject(k).isNull("phone") ? "" : postsdata1.getJSONObject(k).getString("phone"));
                                    morelist.setMobile(postsdata1.getJSONObject(k).isNull("mobile") ? "" : postsdata1.getJSONObject(k).getString("mobile"));
                                    morelist.setSubType("VISITAFISHERIESOFFICE");

                                    MoreList.add(morelist);
                                }
                            }
                        }
                    }

                    if(object.getString("contactAndLicensingType").equals("LICENSING"))
                    {
                        JSONArray postsdata = object.getJSONArray("listOfData");

                        for( int j=0; j<postsdata.length(); j++ )
                        {
                            JSONObject object1 = postsdata.getJSONObject(j);

                            if(object1.getString("contactAndLicensingSubType").equals("NSWRECREATIONALLICENSEFEE"))
                            {
                                JSONArray postsdata1 = object1.getJSONArray("listOfData");

                                for( int k=0; k<postsdata1.length(); k++)
                                {
                                    MoreModel morelist = new MoreModel();

                                    morelist.setContactAndLicensingType(postsdata1.getJSONObject(k).isNull("contactAndLicensingSubType") ? "" : postsdata1.getJSONObject(k).getString("contactAndLicensingSubType"));
                                    morelist.setTitle(postsdata1.getJSONObject(k).isNull("title") ? "" : postsdata1.getJSONObject(k).getString("title"));
                                    morelist.setDescription(postsdata1.getJSONObject(k).isNull("description") ? "" : postsdata1.getJSONObject(k).getString("description"));
                                    morelist.setUrl(postsdata1.getJSONObject(k).isNull("url") ? "" : postsdata1.getJSONObject(k).getString("url"));
                                    morelist.setPhone(postsdata1.getJSONObject(k).isNull("phone") ? "" : postsdata1.getJSONObject(k).getString("phone"));
                                    morelist.setMobile(postsdata1.getJSONObject(k).isNull("mobile") ? "" : postsdata1.getJSONObject(k).getString("mobile"));
                                    morelist.setSubType("NSWRECREATIONALLICENSEFEE");

                                    MoreList.add(morelist);
                                }
                            }
                        }
                    }
                }
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}