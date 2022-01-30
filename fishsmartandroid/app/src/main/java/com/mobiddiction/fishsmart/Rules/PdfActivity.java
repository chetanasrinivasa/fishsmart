package com.mobiddiction.fishsmart.Rules;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.FileUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;

//import com.github.barteksc.pdfviewer.PDFView;
//import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.mobiddiction.fishsmart.R;

import java.io.InputStream;
import java.net.URL;

public class PdfActivity extends AppCompatActivity {
//    private PDFView pdfView;
    private WebView pdfView;
    ImageButton sharebtn;
    String sf;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        sharebtn = findViewById(R.id.sharebtn);
        imgBack = findViewById(R.id.imgBack);
        pdfView = findViewById(R.id.pdfView);

        Bundle extras = getIntent().getExtras();
        sf = extras.getString("fs");

        if (!extras.getBoolean("share")) {
            sharebtn.setVisibility(View.GONE);
        } else {
            sharebtn.setVisibility(View.VISIBLE);
        }

        pdfView.requestFocus();
        pdfView.getSettings().setJavaScriptEnabled(true);
        pdfView.loadUrl("http://docs.google.com/gview?embedded=true&url="+sf);
        pdfView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try  {
////                    InputStream input = new URL(sf).openStream();
////                    pdfView.fromStream(input)
////                            // .enableAntialiasing(true)
////                            // .spacing(8) // in dp
////                            // .autoSpacing(false)
////                            .pageFitPolicy(FitPolicy.WIDTH)
////                            // .pageSnap(true) // snap pages to screen boundaries
////                            // .pageFling(true) // make a fling change only a single page like ViewPager
////                            .load();
////                    pdfView.requestFocus();
////                    pdfView.getSettings().setJavaScriptEnabled(true);
//////                    pdfView.getSettings().setSupportZoom(true);
////                    pdfView.loadUrl("http://docs.google.com/gview?embedded=true&url="+sf);
////                    pdfView.setWebViewClient(new WebViewClient() {
////                        @Override
////                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                            view.loadUrl(url);
////                            return true;
////                        }
////                    });
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();

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

    }
}
