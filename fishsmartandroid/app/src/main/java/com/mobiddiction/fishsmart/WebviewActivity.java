package com.mobiddiction.fishsmart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class WebviewActivity extends AppCompatActivity {

    WebView webView;
    ImageButton sharebtn;
    String sf;
    ImageView imgBack;
    TextView navTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webView);
        sharebtn = findViewById(R.id.sharebtn);
        imgBack = findViewById(R.id.imgBack);
        navTitle = findViewById(R.id.nav_title);

        Bundle extras = null;
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if (extras == null) {
                sf = null;

            }else {
                sf = extras.getString("fs");
                if(sf.equals("http://bit.ly/1U7tDIj")){
                    navTitle.setText("WEATHER DETAILS");
                }

                if (!extras.getBoolean("share")) {
                    sharebtn.setVisibility(View.GONE);
                } else {
                    sharebtn.setVisibility(View.VISIBLE);
                }

            }
        } else {
            sf = (String) savedInstanceState.getSerializable("fs");
        }
        webView.setWebViewClient(new MyWebViewClient());

        if ((extras != null) && (sf != null)){
            Log.d("pdfurl webview", sf);
        }
        openURL(sf);

        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        set.setSupportZoom(true);

        set.setDomStorageEnabled(true);
        set.setAppCacheEnabled(true);
        set.setLoadsImagesAutomatically(true);
        set.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sf);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share via"));

                /*Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sf);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                Uri screenshotUri = Uri.parse("");
                sendIntent.setType("image/*");
                sendIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(sendIntent);*/
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            WebviewActivity.this.finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}