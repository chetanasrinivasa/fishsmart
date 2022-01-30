package com.mobiddiction.fishsmart.Profile;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.CustomAlert.AlertDialog;
import com.mobiddiction.fishsmart.CustomAlert.OnItemClickListener;
import com.mobiddiction.fishsmart.MainFrameActivity;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Network.ResponseConfig;
import com.mobiddiction.fishsmart.Network.ResponseListner;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.dao.LoginDetail;
import com.mobiddiction.fishsmart.util.Log;
import com.mobiddiction.fishsmart.util.Utils;

/**
 * Created by Krunal on 12/12/2018.
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView backbtn;
    private EditText edtOldPassword;
    private EditText edtNewPassword;
    private EditText edtConfirmNewPassword;
    private TextView txtUpdate;
    private RelativeLayout relProgressBar;
    boolean isResetpass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change_password);

        Typeface font = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");

        backbtn = findViewById(R.id.backbtn);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = findViewById(R.id.edtConfirmNewPassword);
        txtUpdate = findViewById(R.id.txtUpdate);
        relProgressBar = findViewById(R.id.relProgressBar);

        edtOldPassword.setTypeface(font);
        edtNewPassword.setTypeface(font);
        edtConfirmNewPassword.setTypeface(font);
        txtUpdate.setTypeface(font);

        backbtn.setOnClickListener(this);
        txtUpdate.setOnClickListener(this);
        isResetpass = getIntent().getBooleanExtra("NEED_CHANGE_PASS",false);


        if(isResetpass == true) {
            backbtn.setVisibility(View.GONE);
            edtOldPassword.setHint("Temporary Password");
        } else {
            backbtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.backbtn:
                finish();
                break;
            case R.id.txtUpdate:
                String validate = validation();
                if (validate == null) {
                    updatePasswordAPI();
                } else {
                    // Show alert over here.
                    AlertDialog.showDialogWithAlertHeaderSingleButton(ChangePasswordActivity.this, "Alert", validate,
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {

                                }
                            });
                }
                break;
        }
    }

    private String validation() {
        String validate = null;
        if (edtOldPassword.getText().toString().trim().isEmpty()) {
            validate = "Old password can not be blank.";
            edtOldPassword.requestFocus();
        } else if (!edtOldPassword.getText().toString().trim().equals(ModelManager.getInstance().getLoginDetails().getPassword())) {
            validate = "Please enter valid old password.";
            edtOldPassword.requestFocus();
        } else if (!Config.isValidPassword(edtOldPassword.getText().toString())) {
            validate = "Password needs to be 6 characters long with 1 capital.";
            edtOldPassword.requestFocus();
        } else if (edtNewPassword.getText().toString().trim().isEmpty()) {
            validate = "New password can not be blank.";
            edtNewPassword.requestFocus();
        } else if (!Config.isValidPassword(edtNewPassword.getText().toString())) {
            validate = "New password needs to be 6 characters long with 1 capital.";
            edtNewPassword.requestFocus();
        } else if (edtConfirmNewPassword.getText().toString().trim().isEmpty()) {
            validate = "Confirm new password can not be blank.";
            edtConfirmNewPassword.requestFocus();
        } else if (!Config.isValidPassword(edtConfirmNewPassword.getText().toString())) {
            validate = "Confirm new password needs to be 6 characters long with 1 capital.";
            edtConfirmNewPassword.requestFocus();
        } else if (!edtNewPassword.getText().toString().trim().equals(edtConfirmNewPassword.getText().toString().trim())) {
            validate = "Confirm new password does not match with new password.";
            edtConfirmNewPassword.requestFocus();
        }
        return validate;
    }

    ResponseListner responseListner = new ResponseListner() {
        @Override
        public void onResponse(String tag, int result, Object obj) {
            if (result == ResponseConfig.API_SUCCESS) {
                if (tag.equals(ResponseConfig.TAG_CHANGE_PASSWORD)) {
                    ChangePasswordActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String email = ModelManager.getInstance().getLoginDetails().getEmail();
                            ModelManager.getInstance().removeLogindetail();
                            ModelManager.getInstance().insertLoginDetail(new LoginDetail(email, edtNewPassword.getText().toString().trim()));
                            ModelManager.getInstance().getLoginDetails().setPassword(edtNewPassword.getText().toString().trim());
                            edtOldPassword.setText("");
                            edtNewPassword.setText("");
                            edtConfirmNewPassword.setText("");

                            AlertDialog.showDialogWithAlertHeaderSingleButton(ChangePasswordActivity.this, "Alert", "Password updated successfully.",
                                    new OnItemClickListener() {
                                        @Override
                                        public void onItemClick(Object o, int position) {

                                            if(isResetpass){
                                                startActivity(new Intent(ChangePasswordActivity.this, MainFrameActivity.class));
                                            }
                                            finish();
                                        }
                                    });
                        }
                    });
                }
            } else {
                Log.print("=========== API CALL FAIL ============");
            }

            ChangePasswordActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    relProgressBar.setVisibility(View.GONE);
                }
            });
        }
    };

    private void updatePasswordAPI() {
        if (Utils.isOnline(ChangePasswordActivity.this)) {
            relProgressBar.setVisibility(View.VISIBLE);
            NetworkManager.getInstance().changePassword(ModelManager.getInstance().getLoginResponse().getAuthorization(),
                    "application/json", ModelManager.getInstance().getLoginResponse().getUserId(), generatePasswordJson(), responseListner);
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

    private JsonObject generatePasswordJson() {
        JsonObject mainJson = new JsonObject();
        mainJson.addProperty("oldPassword", edtOldPassword.getText().toString().trim());
        mainJson.addProperty("newPassword", edtNewPassword.getText().toString().trim());
        return mainJson;
    }
}
