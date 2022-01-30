package com.mobiddiction.fishsmart.notification;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.dao.DeviceData;
import com.pnikosis.materialishprogress.ProgressWheel;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class NotificationActivity extends AppCompatActivity {
    private ImageView backbtn;
    ListView listview;
    TextView emptyView;
    ProgressWheel progress_wheel;
    ImageView alert_icons;
    private static final String TAG = "NotificationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        backbtn = findViewById(R.id.backbtn);
        listview = findViewById(R.id.listview);
        emptyView = findViewById(R.id.emptyView);
        progress_wheel = findViewById(R.id.progress_wheel);
        alert_icons = findViewById(R.id.alert_icons);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getNotificationList();
    }

    public void setNotificationUI(){
        if(ModelManager.getInstance().getNotification().size() > 0) {
            listview.setAdapter(new NotificationListviewAdapter(this, ModelManager.getInstance().getNotification(), NotificationActivity.this));
            listview.setEmptyView(emptyView);
            emptyView.setVisibility(View.GONE);
            alert_icons.setVisibility(View.GONE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
            alert_icons.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(base));
    }

    @Subscribe
    public void onEventMainThread(BasicEvent event) {
        if(event == BasicEvent.GET_NOTIFICATION_SUCCESS){
            setNotificationUI();
            progress_wheel.setVisibility(View.GONE);
        }

        if(event == BasicEvent.GET_NOTIFICATION_FAILED){
            emptyView.setVisibility(View.VISIBLE);
            alert_icons.setVisibility(View.VISIBLE);
            progress_wheel.setVisibility(View.GONE);
            showPopUpErrorMessage();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        EventBus.getDefault().register(this);
    }

    private void showPopUpErrorMessage(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(NotificationActivity.this);
                builder.setMessage("Sorry! There seems to be an error, please try again.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                finish();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void getNotificationList(){
        progress_wheel.setBarColor(ContextCompat.getColor(this, R.color.fish_orange));
        progress_wheel.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        alert_icons.setVisibility(View.GONE);
        if (ModelManager.getInstance() != null && ModelManager.getInstance().getLoginDetails() != null) {
            if (ModelManager.getInstance().getLoginResponse().getUserId() != null &&
                    !ModelManager.getInstance().getLoginResponse().getUserId().equals("")) {
                try {
                    NetworkManager.getInstance().getNotification(ModelManager.getInstance().getLoginResponse().getAuthorization(),ModelManager.getInstance().getLoginResponse().getUserId()+"");
                    Log.d(TAG, "getNotification");
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (ModelManager.getInstance() != null && ModelManager.getInstance().getDeviceData() != null && ModelManager.getInstance().getDeviceData().size() > 0) {
                DeviceData deviceData =  ModelManager.getInstance().getDeviceData().get(0);
                String deviceId = deviceData.getId();
                String authorization = "";
                try {
                    if(ModelManager.getInstance().getLoginResponse() != null) {
                        authorization = ModelManager.getInstance().getLoginResponse().getAuthorization();
                        NetworkManager.getInstance().getNotificationByDeviceId(authorization, deviceId + "");
                        Log.d(TAG, "getNotificationByDeviceId");
                    } else{

                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }else{
                emptyView.setVisibility(View.VISIBLE);
                alert_icons.setVisibility(View.VISIBLE);
                progress_wheel.setVisibility(View.GONE);
            }
        }
    }
}