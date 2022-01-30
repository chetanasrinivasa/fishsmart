package com.mobiddiction.fishsmart.Gallery;

import android.os.AsyncTask;
import android.util.Log;

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
 * Created by Archa on 17/07/2015.
 */
public class GalleryAsyncTask extends AsyncTask<String, Void, String> {

    static final String TAG = "GalleryAsyncTask";

    private GalleryFragment delegate;
    ArrayList<GalleryItem> galleryItems = new ArrayList<GalleryItem>();
    int paging = 0;

    public GalleryAsyncTask(GalleryFragment  delegate, int paging) {
        this.delegate = delegate;
        this.paging = paging;
    }

    @Override
    protected String doInBackground(
            String... params) {
        // TODO Auto-generated method stub

        StringBuilder html = new StringBuilder();
        try{

            URL url = new URL(Config.HOST +"/galleryImage/approved/1");

            Log.d("GALLERY URL", "" + url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);

            conn.addRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.addRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestMethod("GET");

            conn.connect();
            int responseCode=conn.getResponseCode();

            Log.d("responseCode", " responseCode " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                Log.d("responseCode", " responseCode HTTP_OK");

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    for(;;)
                    {
                        String line = br.readLine();

                        if (line == null) break;
                        html.append(line+'\n');
                    }
                    br.close();

                }
                conn.disconnect();

                Log.d("GALLERY", " response " + html.toString());

            }

        }catch(Exception ex)
        {
            Log.d("responseCode", " ex " + ex.toString());
        }

        try {

            JSONArray full_objs = new JSONArray(html.toString());

            for (int i = 0; i < full_objs.length(); i++)
            {
                JSONObject obj = full_objs.getJSONObject(i);

                JSONArray media = obj.getJSONArray("media");

                GalleryItem item = new GalleryItem();

                item.setId(obj.isNull("id") ? "" : obj.getString("id"));
                item.setDateCreated(obj.isNull("dateCreated") ? "" : obj.getString("dateCreated"));
                item.setFeedDate(obj.isNull("feedDate") ? "" : obj.getString("feedDate"));
                item.setFeedUrl(obj.isNull("feedUrl") ? "" : obj.getString("feedUrl"));
                item.setText(obj.isNull("text") ? "" : obj.getString("text"));
                item.setUserName(obj.isNull("userName") ? "" : obj.getString("userName"));
                item.setType(obj.getJSONObject("type").isNull("name") ? "" : obj.getJSONObject("type").getString("name"));
                item.setProfilePic(obj.isNull("profilePic") ? "" : obj.getString("profilePic"));

                for(int j=0;j<media.length();j++) {
                    item.setMedia(media.getString(j));
                }

                galleryItems.add(item);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(String response) {
        // TODO Auto-generated method stub
        //super.onPostExecute(result);
            delegate.notifyGalleryDownloadSuccess(galleryItems);
//        }

    }

}