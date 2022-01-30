package com.mobiddiction.fishsmart.SignIn;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.ForgotPassword.ForgotPasswordActivity;
//import com.mobiddiction.fishsmart.CustomAlert.AlertDialog;
//import com.mobiddiction.fishsmart.CustomAlert.*;

import com.mobiddiction.fishsmart.MainFrameActivity;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Profile.ChangePasswordActivity;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.SignUp.SignUpActivity;
import com.mobiddiction.fishsmart.dao.LoginDetail;
import com.mobiddiction.fishsmart.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class SignInActivity extends BaseTabActivity {


    ExtendedEditText emailEditText, passwordEditText;
    Button letsGoButton, btnSkip;
    TextView needAccount, forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        letsGoButton = findViewById(R.id.letsGoButton);
        btnSkip = findViewById(R.id.btnSkip);
        needAccount = findViewById(R.id.needAccount);
        forgotPassword = findViewById(R.id.forgotPassword);

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkEmailAddress();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        letsGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkManager.getInstance().doLogin(new SignInBody(emailEditText.getText().toString(), passwordEditText.getText().toString()));
            }
        });

        needAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, MainFrameActivity.class));
            }
        });

        //removes the focus from the textview & clears the soft keyboard
        passwordEditText.setOnEditorActionListener(new ExtendedEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    passwordEditText.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(passwordEditText.getWindowToken(), 0);
                    NetworkManager.getInstance().doLogin(new SignInBody(emailEditText.getText().toString(), passwordEditText.getText().toString()));
                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        Utils.freeMemory();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(BasicEvent event) {
Log.d("Signin","onEventMainThread - event = " + String.valueOf(event));
        if (event == BasicEvent.DO_LOGIN_SUCCESS) {
            ModelManager.getInstance().removeLogindetail();
            ModelManager.getInstance().insertLoginDetail(new LoginDetail(emailEditText.getText().toString(), passwordEditText.getText().toString()));
            finish();
            if(ModelManager.getInstance().getLoginResponse().getChangePassword().equals("true")){
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                intent.putExtra("NEED_CHANGE_PASS",true);
                startActivity(intent);
            }else {
                startActivity(new Intent(this, MainFrameActivity.class));
            }
        }

        if (event == BasicEvent.DO_LOGIN_FAILED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.login_failed_title))
                    .setMessage(getResources().getString(R.string.login_failed_message))
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener (){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ModelManager.getInstance().removeLogindetail();
                        }
                    })
                    .setView(R.layout.layout_alertview)
                    .create()
                    .show();
            /*AlertDialog.showDialogWithAlertHeaderSingleButton(this,
                    getResources().getString(R.string.login_failed_title),
                    getResources().getString(R.string.login_failed_message),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            ModelManager.getInstance().removeLogindetail();
                        }
                    });*/
        }


        if (event == BasicEvent.DO_LOGIN_401) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Activate your account")
                    .setMessage("Seems you don\\'t have the right access.Enter the correct email & password Or Please register if not registered yet ")
                    .setPositiveButton("Register",new DialogInterface.OnClickListener (){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent;
                            intent = new Intent(SignInActivity.this, SignUpActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Ok",new DialogInterface.OnClickListener (){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ModelManager.getInstance().removeLogindetail();
                        }
                    })
                    .setView(R.layout.layout_alertview)
                    .create()
                    .show();

           /* AlertDialog.showDialogWithAlertHeaderSingleButton(getContext(),
                    "Activate your account",
                    "Please activate your account from the link sent to your email.",
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            ModelManager.getInstance().removeLogindetail();
                        }
                    });*/
        }
    }


    @SuppressLint("NewApi")
    public void checkEmailAddress() {
        if (emailEditText.getText().toString().equals("") && !Config.isEmailValid(emailEditText.getText().toString())) {
            emailEditText.setError("Email address not valid");
        } else if (Config.isEmailValid(emailEditText.getText().toString())) {
            /**nothing to do*/
        } else {
            emailEditText.setError("Email address not valid");
        }
    }


}
