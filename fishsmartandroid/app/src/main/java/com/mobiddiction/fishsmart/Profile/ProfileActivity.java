package com.mobiddiction.fishsmart.Profile;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiddiction.fishsmart.CustomAlert.AlertDialog;
import com.mobiddiction.fishsmart.CustomAlert.OnItemClickListener;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Network.ResponseConfig;
import com.mobiddiction.fishsmart.Network.ResponseListner;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.TermAndCondition.PrivacyStatementActivity;
import com.mobiddiction.fishsmart.TermAndCondition.TermsAndConditionActivity;
import com.mobiddiction.fishsmart.bean.UserBean;
import com.mobiddiction.fishsmart.bll.CatchLogBll;
import com.mobiddiction.fishsmart.notification.AppConstant;
import com.mobiddiction.fishsmart.util.Log;
import com.mobiddiction.fishsmart.util.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Krunal on 12/12/2018.
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView backbtn;
    private RelativeLayout relProfileView;
    private ImageView imgProfilePic;
    private TextView txtName;
    private TextView txtEmail;
    private RelativeLayout relTermsAndConditionView;
    private RelativeLayout relPrivacyPolicyView;
    private RelativeLayout relChangePasswordView;
    private RelativeLayout relFeedbackView;
    private TextView txtLogout;
    private TextView txtDeleteAccount;
    private TextView txtTc;
    private TextView txtPp;
    private TextView txtCP;
    private UserBean myUserBean;
    private RelativeLayout relProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Typeface font = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");

        backbtn = findViewById(R.id.backbtn);
        relProfileView = findViewById(R.id.relProfileView);
        imgProfilePic = findViewById(R.id.imgProfilePic);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        relTermsAndConditionView = findViewById(R.id.relTermsAndConditionView);
        relPrivacyPolicyView = findViewById(R.id.relPrivacyPolicyView);
        txtTc = findViewById(R.id.txtTc);
        txtPp = findViewById(R.id.txtPp);
        relChangePasswordView = findViewById(R.id.relChangePasswordView);
        relFeedbackView = findViewById(R.id.relFeedbackView);
        txtCP = findViewById(R.id.txtCP);
        txtLogout = findViewById(R.id.txtLogout);
        txtDeleteAccount = findViewById(R.id.txtDeleteAccount);
        relProgressBar = findViewById(R.id.relProgressBar);

        txtName.setTypeface(font);
        txtEmail.setTypeface(font);
        txtLogout.setTypeface(font);
        txtDeleteAccount.setTypeface(font);
        txtTc.setTypeface(font);
        txtPp.setTypeface(font);
        txtCP.setTypeface(font);

        backbtn.setOnClickListener(this);
        relProfileView.setOnClickListener(this);
        relTermsAndConditionView.setOnClickListener(this);
        relPrivacyPolicyView.setOnClickListener(this);
        relChangePasswordView.setOnClickListener(this);
        relFeedbackView.setOnClickListener(this);
        txtLogout.setOnClickListener(this);
        txtDeleteAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.backbtn:
                finish();
                break;
            case R.id.relProfileView:
                intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                intent.putExtra("PROFILE_DATA", myUserBean);
                startActivity(intent);
                break;
            case R.id.relTermsAndConditionView:
                Intent tcIntent = new Intent(ProfileActivity.this, TermsAndConditionActivity.class);
                startActivity(tcIntent);
                break;
            case R.id.relPrivacyPolicyView:
                Intent ppIntent = new Intent(ProfileActivity.this, PrivacyStatementActivity.class);
                startActivity(ppIntent);
                break;
            case R.id.relChangePasswordView:
                intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.relFeedbackView:
                String[] to = {"help@mobiddiction.com.au"}; //create contact email
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:")); //only email apps should handle this
                emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback From Android App.");
                String deviceOs = null;
                try {
                    deviceOs = Build.VERSION.BASE_OS;
                    if(deviceOs == null || deviceOs.equals("")){
                        deviceOs = "ANDROID OS " + Build.VERSION.SDK_INT;
                    }
                } catch (java.lang.NoSuchFieldError e) {
                    e.printStackTrace();
                    deviceOs = "ANDROID OS " + Build.VERSION.SDK_INT;
                }
                String textDetails =
                        "App Name: Fishsmart" + "\n" + "App Version: 2" + "\n" + "OS Version: " +
                                deviceOs + "\n" + "Device Name: " + AppConstant.getDeviceName() +
                                "\n" + "\n"  + "Sent From My Android";
                emailIntent.putExtra(Intent.EXTRA_TEXT, textDetails);
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                }else{
                    Toast.makeText(ProfileActivity.this, "No email app available",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.txtLogout:
                // Show alert and perform the API
                AlertDialog.showDialogWithHeaderTwoButton(ProfileActivity.this, "Alert", "Do you really want to logout?",
                        new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 0) {
                                    /*// Update Device Data
                                    if (ModelManager.getInstance() != null && ModelManager.getInstance().getDeviceData() != null &&
                                            ModelManager.getInstance().getDeviceData().size() > 0) {
                                        DeviceData deviceData =  ModelManager.getInstance().getDeviceData().get(0);
                                        new HomeActivity().updateDevice(ProfileActivity.this, deviceData.getUdid(), deviceData.getId());
                                    }*/
                                    ModelManager.getInstance().removeLogindetail();
                                    ModelManager.getInstance().removeLogin();
                                    finish();
                                }
                            }
                        });
                break;
            case R.id.txtDeleteAccount:
                // Show alert and perform the API
                AlertDialog.showDialogWithHeaderTwoButton(ProfileActivity.this, "Alert", "Are you sure you want to delete the account?",
                        new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 0) {
                                    deleteUserAccount();
                                }
                            }
                        });
                break;
        }
    }

    private void setUserData(final UserBean userBean) {
        ProfileActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (userBean.image.size() > 0) {
                    Picasso.get()
                            .load(userBean.image.get(0).url)
                            .into(imgProfilePic);

                }
                txtName.setText(userBean.firstName + " " + userBean.lastName);
                txtEmail.setText(userBean.email);
            }
        });
    }

    ResponseListner responseListner = new ResponseListner() {
        @Override
        public void onResponse(String tag, int result, Object obj) {
            if (result == ResponseConfig.API_SUCCESS) {
                if (tag.equals(ResponseConfig.TAG_GET_USER_PROFILE)) {
                    UserBean userBean = (UserBean) obj;
                    myUserBean = userBean;
                    setUserData(userBean);
                } else if (tag.equals(ResponseConfig.TAG_DELETE_ACCOUNT)) {
                    CatchLogBll catchLogBll = new CatchLogBll(ProfileActivity.this);
                    catchLogBll.deleteAllCatchLog();
                    ModelManager.getInstance().removeAllData();
                    ModelManager.getInstance().removeLogindetail();
                    ModelManager.getInstance().removeLogin();
                    finish();
                }


            } else {
                Log.print("=========== API CALL FAIL ============");
            }

            ProfileActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    relProgressBar.setVisibility(View.GONE);
                }
            });
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        if (Utils.isOnline(ProfileActivity.this)) {
            relProgressBar.setVisibility(View.VISIBLE);
            NetworkManager.getInstance().getUserProfile(ModelManager.getInstance().getLoginResponse().getAuthorization(), "application/json",
                    ModelManager.getInstance().getLoginResponse().getUserId(), responseListner);
        } else {
            relProgressBar.setVisibility(View.GONE);
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                        }
                    });
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

    private void deleteUserAccount() {
        if (Utils.isOnline(ProfileActivity.this)) {
            NetworkManager.getInstance().deleteAccount(ModelManager.getInstance().getLoginResponse().getAuthorization(), "application/json",
                    ModelManager.getInstance().getLoginResponse().getUserId(), responseListner);
        } else {
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                        }
                    });
        }
    }
    @Subscribe
    public void onEventMainThread(BasicEvent event) {
        /*if(event == BasicEvent.NOTIFICATION_UPDATE_DEVICE){
            NetworkManager.getInstance().getNotification(ModelManager.getInstance().getLoginResponse().getAuthorization(),ModelManager.getInstance().getLoginResponse().getUserId()+"");
        }
        if(event == BasicEvent.NOTIFICATION_DEVICE_NOT_FOUND){
            new HomeActivity().registerDevice(ProfileActivity.this);
        }
        if(event == BasicEvent.NOTIFICATION_ADD_DEVICE){
            NetworkManager.getInstance().getNotification(ModelManager.getInstance().getLoginResponse().getAuthorization(),ModelManager.getInstance().getLoginResponse().getUserId()+"");
        }
        if(event == BasicEvent.GET_NOTIFICATION_SUCCESS){
            ModelManager.getInstance().removeLogindetail();
            ModelManager.getInstance().removeLogin();
            finish();
        }*/
    }
}
