package com.mobiddiction.fishsmart.Network;

import android.content.Context;

import com.mobiddiction.fishsmart.util.Log;
import com.mobiddiction.fishsmart.util.Utils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MultipartRequest {
    public Context caller;
    public MultipartBody.Builder  builder;
    private OkHttpClient client;

    public MultipartRequest(Context caller) {
        this.caller = caller;
        this.builder = new MultipartBody.Builder ();
        this.builder.setType(MultipartBody.FORM);
        this.client = new OkHttpClient();
    }

    public void addString(String name, String value) {
        this.builder.addFormDataPart(name, value);
    }

    public void addFile(String name, String filePath, String fileName) {
        this.builder.addFormDataPart(name, fileName, RequestBody.create(MediaType.parse("image/jpeg"), new File(filePath)));
    }

    public void addZipFile(String name, String filePath, String fileName) {
        this.builder.addFormDataPart(name, fileName, RequestBody.create(MediaType.parse("application/zip"), new File(filePath)));
    }

    public String execute(String url) {
        RequestBody requestBody = null;
        Request request = null;
        Response response = null;
        int code = 200;
        String strResponse = null;

        try {
            requestBody = this.builder.build();
            request = new Request.Builder().header("Authorization", ModelManager.getInstance().getLoginResponse().getAuthorization())
                    .header("Content-Type", "application/json")
                    .url(url).post(requestBody).build();
            Log.print("::::::: REQ :: " + request);
//            client.setConnectTimeout(2, TimeUnit.MINUTES);
//            client.setReadTimeout(2, TimeUnit.MINUTES);
            response = client.newCall(request).execute();
            // Log.print("::::::: response :: " + response.body().string());

            if (!response.isSuccessful())
                throw new IOException();

            code = response.networkResponse().code();
            Log.print("====== Code =====" + code);

            if (response.isSuccessful()) {
                strResponse = response.body().string();
            } else if (code == 404) {
                // ** "Invalid URL or Server not available, please try again" */
                strResponse = "Invalid URL or Server not available, please try again.";
            } else if (code == 408) {
                // * "Connection timeout, please try again", */
                strResponse = "Connection timeout, please try again.";
            } else if (code == 503) {
                // *
                // "Invalid URL or Server is not responding, please try again",
                // */
                strResponse = "Invalid URL or Server is not responding, please try again.";
            }
        } catch (Exception e) {
            Log.error("Exception", e);
        } finally {
            requestBody = null;
            request = null;
            response = null;
            builder = null;
            if (client != null)
                client = null;
            Utils.freeMemory();
        }
        return strResponse;
    }
}