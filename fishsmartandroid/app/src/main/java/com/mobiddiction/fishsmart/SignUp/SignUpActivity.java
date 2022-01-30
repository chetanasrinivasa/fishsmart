package com.mobiddiction.fishsmart.SignUp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.CustomAlert.OnItemClickListener;
import com.mobiddiction.fishsmart.MainFrameActivity;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Profile.ProfileActivity;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.RegistrationModel;
import com.mobiddiction.fishsmart.SignIn.SignInActivity;
import com.mobiddiction.fishsmart.TermAndCondition.PrivacyStatementActivity;
import com.mobiddiction.fishsmart.TermAndCondition.TermsAndConditionActivity;
import com.mobiddiction.fishsmart.dao.LoginDetail;
import com.mobiddiction.fishsmart.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class SignUpActivity extends BaseTabActivity {

    TextView next1, tAndC, privacyPolicy;
    CircleImageView profile_image;
    Button letsGoButton;
    TextView emailEditText,lastNameEditText,firstNameEditText,passwordEditText,confirmPasswordEditText,signIn;
    TextFieldBoxes firstNameFloat,lastNameFloat,emailFloat,passwordFloat,confirmPasswordFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        next1 = findViewById(R.id.next1);
        tAndC = findViewById(R.id.t_and_c);
        privacyPolicy = findViewById(R.id.privacy_policy);
        profile_image = findViewById(R.id.profile_image);
        letsGoButton = findViewById(R.id.letsGoButton);
        emailEditText = findViewById(R.id.emailEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        firstNameFloat = findViewById(R.id.firstNameFloat);
        lastNameFloat = findViewById(R.id.lastNameFloat);
        emailFloat = findViewById(R.id.emailFloat);
        passwordFloat = findViewById(R.id.passwordFloat);
        confirmPasswordFloat = findViewById(R.id.confirmPasswordFloat);
        signIn = findViewById(R.id.signIn);

        tAndC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tcIntent = new Intent(SignUpActivity.this, TermsAndConditionActivity.class);
                startActivity(tcIntent);
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ppIntent = new Intent(SignUpActivity.this, PrivacyStatementActivity.class);
                startActivity(ppIntent);
            }
        });

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
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * uncomment below when the UI has been confirmed
                 */
                startActivity(new Intent(SignUpActivity.this, MainFrameActivity.class));

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        letsGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstNameEditText.getText().toString().equalsIgnoreCase("")){
                    firstNameEditText.setError("First Name cannot be empty");
                }
                else if(lastNameEditText.getText().toString().equalsIgnoreCase("")){
                    lastNameEditText.setError("Last Name cannot be empty");
                }
                else if(!Config.isEmailValid(emailEditText.getText().toString())){
                    emailEditText.setError("Email address not valid");
                }
                else if(!Config.isValidPassword(passwordEditText.getText().toString())){
                    passwordEditText.setError("Password needs to be 6 characters long with 1 capital.");
                }
                else if(!Config.isValidPassword(confirmPasswordEditText.getText().toString())){
                    confirmPasswordEditText.setError("Password needs to be 6 characters long with 1 capital.");
                }
                else if(!passwordEditText.getText().toString().equalsIgnoreCase(confirmPasswordEditText.getText().toString())){
                    confirmPasswordEditText.setError("Password does not match the confirm password.");
                }else {

                    List<Integer> imageId = new ArrayList<>();
                    NetworkManager.getInstance().doRegistration(new RegistrationModel(firstNameEditText.getText().toString(),
                            lastNameEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            confirmPasswordEditText.getText().toString(),
                            emailEditText.getText().toString(),
                            imageId));

                }
            }
        });
        checkNull();
    }

    @SuppressLint("NewApi")
    public void checkEmailAddress(){
        if(emailEditText.getText().toString().equals("") && !Config.isEmailValid(emailEditText.getText().toString())){
            emailEditText.setError("Email address not valid");
        } else if(Config.isEmailValid(emailEditText.getText().toString())) {
            /**nothing to do*/
        } else{
            emailEditText.setError("Email address not valid");
        }
    }
    public void checkNull(){
        firstNameFloat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        lastNameFloat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        emailFloat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        passwordFloat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        confirmPasswordFloat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
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

        if(event == BasicEvent.SIGN_UP_SUCCESS){
            String email = emailEditText.getText().toString();
            String pwd = passwordEditText.getText().toString();
            ModelManager.getInstance().insertLoginDetail(new LoginDetail(email, pwd));
            com.mobiddiction.fishsmart.CustomAlert.AlertDialog.showDialogWithAlertHeaderSingleButton(this,
                    "User Registration.",
                    "Registration successful.",
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            startActivity(new Intent(SignUpActivity.this, MainFrameActivity.class));
                        }
                    });



        }

        if(event == BasicEvent.SIGN_UP_FAILED){
            com.mobiddiction.fishsmart.CustomAlert.AlertDialog.showDialogWithAlertHeaderSingleButton(this,
                    "Sign Up Failed",
                    "An account for that email already exists. Please enter a different email.",
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            ModelManager.getInstance().removeLogin();
                        }
                    });
        }

        if(event == BasicEvent.SIGN_UP_PASSWORD){
            com.mobiddiction.fishsmart.CustomAlert.AlertDialog.showDialogWithAlertHeaderSingleButton(this,
                    "Password Error",
                    "Password needs to be 6 characters long with 1 capital.",
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            ModelManager.getInstance().removeLogin();
                        }
                    });
        }

        if(event == BasicEvent.SIGN_UP_DUPLICATE){
            com.mobiddiction.fishsmart.CustomAlert.AlertDialog.showDialogWithAlertHeaderSingleButton(this,
                    "Email Address Exist",
                    "An account for that email already exists. Please enter a different email.",
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            ModelManager.getInstance().removeLogin();
                        }
                    });
        }

    }

}
