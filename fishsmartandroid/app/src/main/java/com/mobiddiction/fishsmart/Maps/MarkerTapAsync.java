package com.mobiddiction.fishsmart.Maps;

import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Archa on 3/06/2016.
 */
public class MarkerTapAsync extends AsyncTask<String, Void, Integer> {

    private WeakReference<MapsActivity> activity;
    public Dialog dialog = null;

    public MarkerTapAsync(MapsActivity activity) {

        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        //activity.progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Integer doInBackground(String... params) {

        Integer result = 0;

        try {

            URL url = new URL(Config.HOST + params[0]);

            Log.d("map url ", "" + url);

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

                Log.d("LOG", "Map response : " + response);

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
        if (result == 1)
        {
           // activity.receiveItems();
        }
    }

    private void parseResult(String response) {

        try {

             final JSONObject json = new JSONObject(response);

            if (json.has("success") && json.getString("success").equals("true")) {

                try {

                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable(){

                        public void run() {
                            MapsActivity context = activity.get();
                            if (context != null) {
                                LayoutInflater inflater = context.getLayoutInflater();
                                final View dialoglayout = inflater.inflate(R.layout.map_popup, null);
                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);
                                WebView webView = dialoglayout.findViewById(R.id.webview);
                                TextView titletext = dialoglayout.findViewById(R.id.titletext);

                                String mimeType = "text/html";
                                String encoding = "utf-8";

                                thanksbtn.setTypeface(Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf"));
                                titletext.setTypeface(Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf"));

                                try {

                                    if(json.getString("data").contains("html"))
                                    {
                                        webView.loadData(json.getString("data"), mimeType, encoding);
                                        webView.setVisibility(View.VISIBLE);
                                        titletext.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        titletext.setText(json.getString("data"));
                                        webView.setVisibility(View.GONE);
                                        titletext.setVisibility(View.VISIBLE);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                dialog = new Dialog(context);

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
                                if(divider != null)
                                    divider.setVisibility(View.INVISIBLE);

                                dialog.setContentView(dialoglayout);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.show();
                            }

                        }
                    });


                }catch (Exception ix)
                {

                    MapsActivity context = activity.get();
                    if (context != null) {
                        context.finish();
                    }
                }
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}