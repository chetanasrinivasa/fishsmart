package com.mobiddiction.fishsmart.Network;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.bean.CatchLogImageBean;
import com.mobiddiction.fishsmart.util.Log;
import com.mobiddiction.fishsmart.util.Utils;

import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;

public class UploadPicAPI extends AsyncTask<Void, Void, Integer> {
    private WeakReference<Context> mCaller;
    public ResponseListner handler;
    public String mesg;
    public MultipartRequest multipartReq;
    public File file = null;
    public String profilePicDestination;
    public String profilePicName;
    public String TAG_UPLOAD_IMAGE;
    public String API_UPLOAD_PIC;
    public CatchLogImageBean catchLogImageBean;

    public UploadPicAPI(Context caller, String profilePicDestination, String profilePicName, ResponseListner handler) {
        this.mCaller = new WeakReference<>(caller);
        this.handler = handler;
        this.profilePicDestination = profilePicDestination;
        this.profilePicName = profilePicName;
        this.TAG_UPLOAD_IMAGE = "TAG_UPLOAD_IMAGE";
    }

    @Override
    protected Integer doInBackground(Void... params) {
        int result = -1;
        try {
            Context context = mCaller.get();
            if (context != null) {
                multipartReq = new MultipartRequest(context);
                if (this.profilePicDestination != null && (this.profilePicDestination != ""
                        || !this.profilePicDestination.equals("") || !this.profilePicDestination.equals("null"))) {
                    file = new File(this.profilePicDestination);
                    if (file.exists()) {
                        multipartReq.addFile("file", file.toString(), file.getName());
                    }
                }
                this.API_UPLOAD_PIC = Config.HOST + "media/uploadImage";
                result = parse(multipartReq.execute(this.API_UPLOAD_PIC));
            }
        } catch (Exception e) {
            result = -1;
            mesg = "Unable to upload please try again.";
            Log.error(this.getClass() + "", e);
        }
        return result;
    }

    protected void onPostExecute(Integer result) {
        if (result == 0) {
            this.handler.onResponse(this.TAG_UPLOAD_IMAGE, ResponseConfig.API_SUCCESS, catchLogImageBean);
        } else if (result > 0) {
            this.handler.onResponse(this.TAG_UPLOAD_IMAGE, ResponseConfig.API_FAIL, mesg);

        } else {
            Context context = mCaller.get();
            if (context != null) {
                if (context instanceof Activity) {
                    if (result == -1 || result == -2 || result == -3) {
//					WebInterface.showAPIErrorAlert(mCaller,
//							Config.ERROR_NETWORK, this.mesg);
                    } else if (result == -4) {
//					WebInterface.showAPIErrorAlert(mCaller,
//							Config.ERROR_UNKNOWN, this.mesg);
                    }
                    this.handler.onResponse(this.TAG_UPLOAD_IMAGE, ResponseConfig.API_FAIL, this.mesg);

                }
            }
        }
    }

    public int parse(String response) {
        int code = 0;
        JSONObject jsonDoc;
        try {
            Log.print("===== RESPONSE ======" + response);
            jsonDoc = new JSONObject(response);
            code = 0;
            mesg = "Success";
            if (response != null) {
                catchLogImageBean = new CatchLogImageBean();
                catchLogImageBean.createdBy = jsonDoc.getString("createdBy");
                catchLogImageBean.createdDate = jsonDoc.getString("createdDate");
                catchLogImageBean.lastModifiedBy = jsonDoc.getString("lastModifiedBy");
                catchLogImageBean.lastModifiedDate = jsonDoc.getString("lastModifiedDate");
                catchLogImageBean.id = jsonDoc.getInt("id");
                catchLogImageBean.name = jsonDoc.getString("name");
                catchLogImageBean.url = jsonDoc.getString("url");
                catchLogImageBean.deleted = jsonDoc.getString("deleted");
            }
        } catch (Exception e) {
            code = -4;
            Log.error(this.getClass() + " :: parse()", e);
            e.printStackTrace();
        } finally {
            Utils.freeMemory();
        }
        return code;
    }
}