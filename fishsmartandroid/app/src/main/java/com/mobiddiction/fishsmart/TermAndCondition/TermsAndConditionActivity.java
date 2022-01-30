package com.mobiddiction.fishsmart.TermAndCondition;

import android.app.Dialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.mobiddiction.fishsmart.R;

import java.io.IOException;
import java.io.InputStream;

public class TermsAndConditionActivity extends AppCompatActivity {

    WebView webView;
    public Dialog dialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        webView = findViewById(R.id.webViews);
        ImageView backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.requestFocus();

        // get input stream handle to file
        try {
            // read the HTML from the file
            InputStream fin = getAssets().open("tAndC.html");
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer);
            fin.close();
            // load the HTML
            webView.loadData(new String(buffer), "text/html", "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}