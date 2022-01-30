package com.mobiddiction.fishsmart.TermAndCondition;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.mobiddiction.fishsmart.R;

/**
 * Created by Krunal on 18/12/2018.
 */
public class PrivacyStatementActivity extends AppCompatActivity {

    WebView webView;
    public Dialog dialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_statement);
        webView = findViewById(R.id.webView);
        ImageView backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView.setWebViewClient(new MyWebViewClient());
        openURL("https://www.industry.nsw.gov.au/privacy");

        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        ViewCompat.setNestedScrollingEnabled(webView, false);
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

}