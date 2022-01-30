package com.mobiddiction.fishsmart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LicensingFragment extends Fragment {
    
    TextView header, paytext, calltext, renewText;
    LinearLayout dynamiclayout, calllayout, paylayout, renewlayout;
    public Dialog dialog = null;
    public static SharedPreferences pref;
    TextView desc; // dynamiclayout desc
    private boolean isFirstLoad;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create a SharedPreferences object
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        isFirstLoad = sharedPreferences.getBoolean(getResources().getString(R.string.is_first_time_load), true);

        View view = inflater.inflate(R.layout.fragment_morelicnse, container, false);
        //((MyApplication) getActivity().getApplication()).getTrackingManager().trackScreen(ScreenEnum.LICENSING, null);
        FirebaseAnalytics.getInstance(getActivity().getApplicationContext()).setCurrentScreen(getActivity(),ScreenEnum.LICENSING, null);
        header = view.findViewById(R.id.header);
        paytext = view.findViewById(R.id.paytext);
        calltext = view.findViewById(R.id.calltext);
        renewText = view.findViewById(R.id.renewtext);
        dynamiclayout = view.findViewById(R.id.dynamiclayout);
        calllayout = view.findViewById(R.id.calllayout);
        paylayout = view.findViewById(R.id.paylayout);
        renewlayout = view.findViewById(R.id.renewlayout);
        pref = getActivity().getSharedPreferences("fishsmart", 0);
        setDialog();
        header.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Bold.otf"));
        paytext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        calltext.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
        renewText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

        calllayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1300 369 365"));
                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sIntent);
            }
        });

//            NetworkManager.getInstance().getLicense();
        paylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("fs", "https://www.onegov.nsw.gov.au//gls_portal/LicenceForm.mvc/NewApplication?formId=ecfa7925-86a8-411b-81e8-725d05491d2e&agencyID=23adb6eb-2c36-4169-b365-bf26ef2367f8");
                intent.putExtra("share", false);
                startActivity(intent);*/
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.redirect_title))
                        .setMessage(getResources().getString(R.string.redirect_message))
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener (){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Go ahead",new DialogInterface.OnClickListener (){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent viewIntent =
                                        new Intent(Intent.ACTION_VIEW);
                                viewIntent.setData(Uri.parse(getResources().getString(R.string.url_pay_online)));
                                startActivity(viewIntent);
                            }
                        })
                        .setView(R.layout.layout_alertview)
                        .create()
                        .show();
            }
        });

        renewlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("fs", "https://www.onegov.nsw.gov.au/gls_portal/snsw/Renew");
                intent.putExtra("share", false);
                startActivity(intent);*/
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.redirect_title))
                        .setMessage(getResources().getString(R.string.redirect_message))
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener (){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Go ahead",new DialogInterface.OnClickListener (){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent viewIntent =
                                        new Intent(Intent.ACTION_VIEW);
                                viewIntent.setData(Uri.parse(getResources().getString(R.string.url_renew_licence)));
                                startActivity(viewIntent);
                            }
                        })
                        .setView(R.layout.layout_alertview)
                        .create()
                        .show();
            }
        });

        if (isFirstLoad) {
            isFirstLoad = false;
            NetworkManager.getInstance().getLicense();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getResources().getString(R.string.is_first_time_load), false);
            editor.apply();
        } else {
            receivedItems();
        }

        return view;
    }

    private void setDialog() {
        if(desc != null && desc.getText().equals(""))
        {
            Log.d("desc", "view is empty");
            dynamiclayout.removeAllViews();
//                    receivedItems();
        }
        else
        {
            Log.d("desc", "view is not empty");
        }

        if(!pref.getBoolean("vergincalllicense", false))
        {
            try {

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable(){

                    public void run() {

                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        final View dialoglayout = inflater.inflate(R.layout.first_popup, null);
                        YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                        TextView title = dialoglayout.findViewById(R.id.title);
                        TextView desc = dialoglayout.findViewById(R.id.desc);
                        Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);

                        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                        thanksbtn.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                        title.setText("Licensing");
                        desc.setText("You can pay the recreational fishing licence fee here, find out more about the licence fee and whether you may be exempt.");

                        dialog = new Dialog(getActivity());

                        dialog.setCanceledOnTouchOutside(false);

                        thanksbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putBoolean("vergincalllicense", true);
                                edit.commit();
                            }
                        });

                        int divierId = dialog.getContext().getResources()
                                .getIdentifier("android:id/titleDivider", null, null);
                        View divider = dialog.findViewById(divierId);
                        if(divider != null)
                            divider.setVisibility(View.INVISIBLE);

                        dialog.setContentView(dialoglayout);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.show();

                    }
                });


            }catch (Exception ix)
            {

            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(BasicEvent event) {
        if(event == BasicEvent.LICENCE_SUCCESS){
            receivedItems();
        }
    }
    public void receivedItems()
    {
        for(int j = 0; j< ModelManager.getInstance().getNewLicenceNSWRECREATIONALLICENSEFEEData().size(); j++)
        {
            if(ModelManager.getInstance().getNewLicenceNSWRECREATIONALLICENSEFEEData().get(j).getSubType().replaceAll("\\s+","").equals("NSWRECREATIONALLICENSEFEE"))
            {
                View child = getActivity().getLayoutInflater().inflate(R.layout.morelicense_item, null);

                TextView title = child.findViewById(R.id.title);
                desc = child.findViewById(R.id.desc);

                title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));
                desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Bariol_Regular.otf"));

                title.setText(ModelManager.getInstance().getNewLicenceNSWRECREATIONALLICENSEFEEData().get(j).getTitle());
                desc.setText(ModelManager.getInstance().getNewLicenceNSWRECREATIONALLICENSEFEEData().get(j).getDescription());

                dynamiclayout.addView(child);
            }
        }
    }
}