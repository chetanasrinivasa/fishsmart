package com.mobiddiction.fishsmart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.analytics.FirebaseAnalytics;

@SuppressLint("ValidFragment")
public class NewsFragment extends Fragment {

    WebView webView;
    public Dialog dialog = null;
    public static SharedPreferences pref;

    @SuppressLint("ValidFragment")
    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
       // ((MyApplication) getActivity().getApplication()).getTrackingManager().trackScreen(ScreenEnum.NEWS, null);
        FirebaseAnalytics.getInstance(getActivity().getApplicationContext()).setCurrentScreen(getActivity(),ScreenEnum.NEWS, null);
        //  new MoreAsync(NewsFragment.this, MoreList).execute("/api/contactAndLicensing");

        pref = getActivity().getSharedPreferences("fishsmart", 0);
        setDialog();

        webView = view.findViewById(R.id.webView);

        webView.setWebViewClient(new MyWebViewClient());
        openURL("https://m.facebook.com/NSWDPIFisheries/");

        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        ViewCompat.setNestedScrollingEnabled(webView, false);

        return view;
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void openURL(String url) {
        webView.loadUrl(url);
        webView.requestFocus();
    }

    private void setDialog() {
        if(!pref.getBoolean("vergincallnews", false))
        {
            try {

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable(){

                    public void run() {

                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        final View dialoglayout = inflater.inflate(R.layout.first_popup, null);
                        YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                        TextView title = dialoglayout.findViewById(R.id.title);
                        TextView desc = dialoglayout.findViewById(R.id.desc);
                        Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);

                        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        thanksbtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        title.setText("News");
                        desc.setText("Check out our Facebook newsfeed Feed here.");

                        dialog = new Dialog(getActivity());

                        dialog.setCanceledOnTouchOutside(false);

                        thanksbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putBoolean("vergincallnews", true);
                                edit.commit();
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
                });


            }catch (Exception ix)
            {

            }
        }
    }

}