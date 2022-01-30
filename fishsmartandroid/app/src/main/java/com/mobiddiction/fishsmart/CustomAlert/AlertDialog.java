package com.mobiddiction.fishsmart.CustomAlert;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by andrewi on 13/01/2017.
 */

public class AlertDialog {
    private static String emailText;
    private static InputMethodManager imm;


    public static void showDialogWithHeaderFortouchId(Context context, OnItemClickListener onItemClickListener) {
        new AlertView("REST SUPER", "Would you like to use touch ID to access your super account", "", null, new String[]{"Not now","Yes please"}, context, AlertView.Style.Alert,true,false,  onItemClickListener,true,true).show();
    }

    public static void showDialogWithHeaderForDeleteBeneficiary(Context context,String heading, String message, OnItemClickListener onItemClickListener) {
        new AlertView(heading, message, "", null, new String[]{"Cancel","Go To Setting"}, context, AlertView.Style.Alert,true,false,  onItemClickListener,true,false).show();
    }

    public static void showDialogWithoutAlertHeader(Context context,String title,String message, OnItemClickListener onItemClickListener) {
        new AlertView(title, message, "Cancel", new String[]{"OK"}, null, context, AlertView.Style.Alert,false,true,  onItemClickListener).show();
    }

    public static void showDialogWithAlertHeaderThreeButton(Context context,String header,String message, OnItemClickListener onItemClickListener) {
        new AlertView(header, message, "", null, new String[]{"Camera","Gallery"}, context, AlertView.Style.Alert,true,false,  onItemClickListener,true,false).show();
    }

    public static void showDialogWithAlertHeaderSingleButton(Context context,String header,String message, OnItemClickListener onItemClickListener) {
        AlertView alertView = new AlertView(header, message, null, new String[]{"OK"}, null, context, AlertView.Style.Alert,true,true,  onItemClickListener);
        if(alertView != null) {

            alertView.show();
            alertView.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(Object o) {

                }
            });
        }
    }

    public static void showDialogWithoutAlertHeadelaunchWakeAPI(Context context,String message, OnItemClickListener onItemClickListener) {
        AlertView alertView =  new AlertView("", message, null, new String[]{"OK"}, null, context, AlertView.Style.Alert,false,true,  onItemClickListener);
        alertView.show();
        alertView.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {

            }
        });
    }


    public static void showDialogWithoutHeaderTwoButtonLaunchWake(Context context, String message, OnItemClickListener onItemClickListener) {
        AlertView alertView = new AlertView("", message, "Cancel", null, new String[]{"Yes"}, context, AlertView.Style.Alert,false,false,  onItemClickListener);
        alertView.show();
        alertView.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {

            }
        });

    }

    public static void showDialogWithHeaderTwoButtonRedText(Context context, String header, String message,String button,String color, boolean isTitleBold, OnItemClickListener onItemClickListener) {
        new AlertView(header, message, "Cancel", null, new String[]{button}, context, AlertView.Style.Alert,true,false,  onItemClickListener, color,isTitleBold).show();
    }

    public static void showDialogWithHeaderTwoButtonUpload(Context context, String header, String message,String button, OnItemClickListener onItemClickListener) {
        new AlertView(header, message, "Cancel", null, new String[]{button}, context, AlertView.Style.Alert,true,false,  onItemClickListener).show();
    }
    public static void showDialogWithHeaderTwoButton(Context context, String header, String message, OnItemClickListener onItemClickListener) {
        new AlertView(header, message, "Cancel", null, new String[]{"OK"}, context, AlertView.Style.Alert,true,false,  onItemClickListener).show();
    }

    public static void showDialogWithoutHeaderTwoButton(Context context, String message, OnItemClickListener onItemClickListener) {
        new AlertView("", message, "Cancel", null, new String[]{"OK"}, context, AlertView.Style.Alert,false,false,  onItemClickListener).show();
    }

    public static void showDialogWithHeaderTwoButtonForCall(Context context, String header, String message, OnItemClickListener onItemClickListener){
        new AlertView(header, message, null, null, new String[]{"Cancel","Call"}, context, AlertView.Style.Alert,true,false, onItemClickListener).show();
    }

    public static boolean isEmailValid(String email) {
        if(email.contains(" ")){
            email = email.replace(" ","");
        }
        return !(email == null || TextUtils.isEmpty(email)) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
