package com.mobiddiction.fishsmart.ForgotPassword;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.CustomAlert.AlertDialog;
import com.mobiddiction.fishsmart.CustomAlert.OnItemClickListener;
import com.mobiddiction.fishsmart.EmptyBodyRequest;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Network.ResponseConfig;
import com.mobiddiction.fishsmart.Network.ResponseListner;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.util.Log;
import com.mobiddiction.fishsmart.util.Utils;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ExtendedEditText emailEditText;
    private Button send;

    ResponseListner responseListner = new ResponseListner() {
        @Override
        public void onResponse(String tag, int result, Object obj) {
            if (result == ResponseConfig.API_SUCCESS) {
                if (tag.equals(ResponseConfig.TAG_RESET_PASSWORD)) {
                    Log.print("=========== API CALL SUCCESS ============");
                }
            } else {
                Log.print("=========== API CALL FAIL ============");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.morpinch)));

        emailEditText = findViewById(R.id.emailEditText);
        send = findViewById(R.id.send_button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Config.isEmailValid(emailEditText.getText().toString())){
                    resetPasswordAPI();
                    AlertDialog.showDialogWithAlertHeaderSingleButton(ForgotPasswordActivity.this, "Alert", "If you are an active user in our system, you should receive a Password Reset Email shortly.",
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {
                                    finish();
                                }
                            });
                    emailEditText.setText("");
                } else{
                    AlertDialog.showDialogWithAlertHeaderSingleButton(ForgotPasswordActivity.this, "Error", "Please Enter valid email address",
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {

                                }
                            });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetPasswordAPI() {
        if (Utils.isOnline(ForgotPasswordActivity.this)) {
            NetworkManager.getInstance().resetPassword(emailEditText.getText().toString(), EmptyBodyRequest.INSTANCE, responseListner);
        } else {
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
    }
}
