package com.mobiddiction.fishsmart.Maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.UiSettings;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mobiddiction.fishsmart.MainFrameActivity;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mobiddiction.fishsmart.dao.NewMapData;
import com.mobiddiction.fishsmart.notification.NotificationActivity;
import com.mobiddiction.fishsmart.util.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.Property.NONE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

public class MapboxActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {
    private static final String TAG = "MapboxActivity";
    MapView mapView;
//    GoogleApiClient googleApiClient = null;
    RelativeLayout setting;
    MapboxMap mbMap;
    private ImageButton mylocation,btn_key,info_key, menu_key;
    private Button hiddenButton;
    public static SharedPreferences pref;
    public Dialog dialog = null;
    private ImageView imgNotification;
    private int GMS_VERSION = 1100;
    PermissionsManager permissionsManager;
    LocationComponent locationComponent;
    ImageView loaderimage;
    RelativeLayout overlay;
    TextView mapnametext;
    private static final LatLng BOUND_CORNER_NW = new LatLng(-8.491377105132457, 108.26584125231903);
    private static final LatLng BOUND_CORNER_SE = new LatLng(-42.73740968175186, 158.19629538046348);
    private static final LatLngBounds RESTRICTED_BOUNDS_AREA = new LatLngBounds.Builder()
                                                                    .include(BOUND_CORNER_NW)
                                                                    .include(BOUND_CORNER_SE)
                                                                    .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_mapbox);
        mapView = findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        setting = findViewById(R.id.setting);
        mylocation = findViewById(R.id.mylocation);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        btn_key = mActionBarView.findViewById(R.id.btn_key);
        info_key = mActionBarView.findViewById(R.id.info_key);
        hiddenButton = findViewById(R.id.hiddenButton);
        info_key.setVisibility(View.VISIBLE);
        loaderimage = findViewById(R.id.loaderimage);
        overlay = findViewById(R.id.overlay);
        mapnametext= findViewById(R.id.mapnametext);

        TextView nav_title = mActionBarView.findViewById(R.id.nav_title);
        nav_title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        nav_title.setText(Html.fromHtml("MAPS"));
        pref = getSharedPreferences("fishsmart", 0);
        if(!pref.getBoolean("map", false)) {
            showDeclaration();
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean("map", true);
            edit.commit();
        }else{
//            setUpMap();
            //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        }

        hiddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertOpenSetting();
            }
        });

        btn_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable(){

                        public void run() {

                            LayoutInflater inflater = getLayoutInflater();
                            final View dialoglayout = inflater.inflate(R.layout.mapkey_popup, null);
                            YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                            Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);

                            dialog = new Dialog(MapboxActivity.this);

                            dialog.setCanceledOnTouchOutside(false);

                            thanksbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    dialog.dismiss();
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
        });
        imgNotification = mActionBarView.findViewById(R.id.imgNotification);
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapboxActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        NetworkManager.getInstance().getMap();
        info_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeclaration();
            }
        });
        mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation(true);
            }
        });
        /**move the camera into the current position**/
    }

    @SuppressLint("NewApi")
    private void refreshData(){
        loaderimage.setVisibility(View.VISIBLE);
        overlay.setVisibility(View.VISIBLE);
        String layer = "";
        if (mbMap != null) {
            mbMap.clear();

            layer = "fishsmartareas-FILL";
            toggleLayer(layer, true);
            layer = "fishsmart-ar";
            toggleLayer(layer, true);
            layer = "fishsmart-fad";
            toggleLayer(layer, true);
            btn_key.setVisibility(View.VISIBLE);
        }
        loaderimage.setVisibility(View.GONE);
        overlay.setVisibility(View.GONE);
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this)
                    .pulseEnabled(true)
                    .build();

            LocationComponent locationComponent = mbMap.getLocationComponent();

            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)
                            .build());
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.NORMAL);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                startActivity(new Intent(MapboxActivity.this, MainFrameActivity.class));
            } else {
                setDialog();
            }

        }else if(requestCode == GMS_VERSION){
            if (resultCode == RESULT_OK) {
                //startActivity(new Intent(PolygonAndMarkerMapDrawingActivity.this, MainFrameActivity.class));
            } else if (resultCode == RESULT_CANCELED){
                Utils.freeMemory();
                this.finish();
            }
        }
    }

    private void setDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MapboxActivity.this);
        builder.setMessage("Location not enabled. Please enable location")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.dismiss();
                        startActivity(new Intent(MapboxActivity.this, MainFrameActivity.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.dismiss();
                        startActivity(new Intent(MapboxActivity.this, MainFrameActivity.class));
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
       this.mbMap = mapboxMap;
        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/nswdpifisheries/ckn9ju3vy023d17juh75khej9"),
                new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                Log.d(TAG, "map style loaded");

                mbMap.addOnMapClickListener(MapboxActivity.this::onMapClick);
                enableLocationComponent(style);
                refreshData();
            }
        });
        UiSettings uiSettings = mapboxMap.getUiSettings();
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);
        // Set the minimum zoom level of the map camera
        mapboxMap.setMinZoomPreference(3);
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(-31.61224,153.08681)) // Sets the new camera position
                .zoom(5) // Sets the zoom
                .bearing(0) // Rotate the camera
//                    .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder

        mbMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 2000);
    }

    private void toggleLayer(String layername, boolean visible) {
        mbMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                Layer layer = style.getLayer(layername);
                if (layer != null) {
//                    if (VISIBLE.equals(layer.getVisibility().getValue())) {
                    if (visible) {
                        layer.setProperties(visibility(VISIBLE));
                    } else {
                        layer.setProperties(visibility(NONE));
                    }
                }
            }
        });
    }

    public void showDeclaration(){

        try {
            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable(){
                public void run() {
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialoglayout = inflater.inflate(R.layout.first_popup, null);
                    YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                    TextView title = dialoglayout.findViewById(R.id.title);
                    TextView desc = dialoglayout.findViewById(R.id.desc);
                    Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);

                    title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                    desc.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                    thanksbtn.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

                    title.setText("Disclaimer");
                    desc.setText("Due to limitations with GPS technology and mapping software, this information is a guide only. It does not replace legislation applying to, or affecting recreational fishing. It cannot be used as a defence in a court of law.\n\nFull operation of the app requires a network connection. If you experience poor performance please refresh the app.\n\nSpearfishers are reminded that in addition to the closures shown here, spearfishing is prohibited in freshwater and adjacent to all NSW ocean beaches, except for the last 20 metres at each end of the beach.");

                    desc.setMovementMethod(new ScrollingMovementMethod());
                    dialog = new Dialog(MapboxActivity.this);

                    dialog.setCanceledOnTouchOutside(true);

                    thanksbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
//                            setUpMap();
                            //client = new GoogleApiClient.Builder(PolygonAndMarkerMapDrawingActivity.this).addApi(AppIndex.API).build();
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

    private void setLocation(boolean showAlert){
        if (ActivityCompat.checkSelfPermission(MapboxActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapboxActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (showAlert) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapboxActivity.this);
                builder.setMessage("Please enable location permission")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                dialog.dismiss();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }
            setting.setVisibility(View.VISIBLE);
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(-31.61224,153.08681)) // Sets the new camera position
                    .zoom(5) // Sets the zoom
                    .bearing(0) // Rotate the camera
//                    .tilt(30) // Set the camera tilt
                    .build(); // Creates a CameraPosition from the builder

            mbMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), 3000);
        } else {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            final Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                mbMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
//                mbMap.setMyLocationEnabled(true);
            } else {
                setting.setVisibility(View.GONE);
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(-31.61224,153.08681)) // Sets the new camera position
                        .zoom(5) // Sets the zoom
                        .bearing(0) // Rotate the camera
//                        .tilt(30) // Set the camera tilt
                        .build(); // Creates a CameraPosition from the builder

                mbMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 3000);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ConnectionResult.SUCCESS == checkGooglePlayServices()) {
            setLocationPermissionlayout();
        }
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onMapClick(@NonNull com.mapbox.mapboxsdk.geometry.LatLng point) {
        String htmlText="";
        String[] rowItem;
        PointF pointf = mbMap.getProjection().toScreenLocation(point);
        RectF rectF = new RectF(pointf.x - 10, pointf.y - 10, pointf.x + 10, pointf.y + 10);
        ArrayList<String> layerList = new ArrayList(Arrays.asList( "fishsmartareas-FILL", "fishsmart-ar", "fishsmart-fad", "Miscellaneous"));

        String name, desc;
        Set<String> rows = new LinkedHashSet<String>();
        List<Feature> featureList = mbMap.queryRenderedFeatures(rectF, "fishsmartareas-FILL");
        if (featureList.size() > 0) {
            for (Feature feature : featureList) {
                String rowText = "";
                String sub = feature.getStringProperty("B_SUBTYPE_1");
                if(sub != null) {
                    rowText +=  "<div style= \" text-align:center; font-weight:bold; font-size: 16px\">"+sub+"</div>";
                }
                name = feature.getStringProperty("C_NAME_1");
                if(name != null) {
                    rowText += "<div style=\"text-align:center;font-weight:bold; font-size: 15px\">"+name+"</div>";
                }
                desc = feature.getStringProperty("F_POPUPINFO");
                if(desc != null) {
                    desc = desc.replace("parksaustralia.gov.au/marine","<a href=\"https://parksaustralia.gov.au/marine/\">parksaustralia.gov.au/marine</a>");
                    rowText += "<div style= padding:0px; font-size: 18px; text-align:left >"+desc+"</div>";
                }
                htmlText += "<tr style = \"border-bottom: 1px solid #000000;\"><td><br>"+rowText+"<br></td></tr>";
            }
        }

        featureList = mbMap.queryRenderedFeatures(rectF, "fishsmart-ar");
        if (featureList.size() > 0) {
            Feature feature = featureList.get(0);
            name = feature.getStringProperty("NAME");
            desc = feature.getStringProperty("POPUPINFO");
            htmlText += "<tr style = \"border-bottom: 1px solid #000000;\"><td><div style=\"text-align:center;font-weight:bold; font-size: 16px\"><br>"+name+"</div><div style= padding:0px; font-size: 18px; text-align:left>"+desc+"</div></td></tr>";
        }

        featureList = mbMap.queryRenderedFeatures(rectF, "fishsmart-fad");
        if (featureList.size() > 0) {
            Feature feature = featureList.get(0);
            name = feature.getStringProperty("NAME");
            desc = feature.getStringProperty("NEAREST_AC");
            String status = feature.getStringProperty("STATUS");
            htmlText += "<tr style = \"border-bottom: 1px solid #000000;\"><td><div style=\"text-align:center;font-weight:bold; font-size: 16px\"><br>"+name+"</div><div style= padding:0px; font-size: 18px; text-align:left>"+desc+"</div><div style= padding:3px 3px 3px 3px font-size: 18px; text-align:left>"+status+"</div></td></tr>";
        }

        featureList = mbMap.queryRenderedFeatures(rectF, "fishsmart-misc");
        if (featureList.size() > 0) {
            Feature feature = featureList.get(0);
            name = feature.getStringProperty("NAME");
            desc = feature.getStringProperty("POPUPINFO");
            htmlText += "<tr style = \"border-bottom: 1px solid #000000;\"><td><div style=\"text-align:center;font-weight:bold; font-size: 16px\"><br>"+name+"</div><div style= padding:0px; font-size: 18px; text-align:left>"+desc+"</div></td></tr>";
        }

        if(!htmlText.isEmpty()) {
            String fullHtmlText = "<html xmlns:fo=\"http://www.w3.org/1999/XSL/Format\" xmlns:msxsl=\"urn:schemas-microsoft-com:xslt\"><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"></head><body style=\"margin:0px 0px 0px 0px;overflow:auto;background:#FFFFFF;\"><table border=1 frame=void rules=rows style=\"font-family:Arial,Verdana,Times;width:100%;border-collapse:collapse;padding:3px 3px 3px 3px\">"+htmlText+"</table></body></html>";
            Handler h = new Handler(Looper.getMainLooper());
            String finalHtmlText = fullHtmlText;
            h.post(new Runnable(){
                public void run() {
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialoglayout = inflater.inflate(R.layout.map_popup, null);
                    YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);
                    Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);
                    WebView webView = dialoglayout.findViewById(R.id.webview);
                    TextView titletext = dialoglayout.findViewById(R.id.titletext);
                    String mimeType = "text/html";
                    String encoding = "utf-8";
                    thanksbtn.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                    titletext.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                    if(finalHtmlText != null && finalHtmlText.contains("html")) {
                        webView.loadDataWithBaseURL(null, finalHtmlText, mimeType, encoding, null);
                        webView.setVisibility(View.VISIBLE);
                        titletext.setVisibility(View.GONE);
                    } else {
                        titletext.setText(finalHtmlText);
                        webView.setVisibility(View.GONE);
                        titletext.setVisibility(View.VISIBLE);
                    }
                    dialog = new Dialog(MapboxActivity.this);
                    dialog.setCanceledOnTouchOutside(false);
                    thanksbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    int divierId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
                    View divider = dialog.findViewById(divierId);
                    if(divider != null)
                        divider.setVisibility(View.INVISIBLE);
                    dialog.setContentView(dialoglayout);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.show();
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
//        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mbMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
//            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void toggleMenu(View vw) {
        Intent i = new Intent();
        i.setAction("com.fishsmart.toggle");
        sendBroadcast(i);
    }

    private void alertOpenSetting() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapboxActivity.this);
        alertDialogBuilder.setTitle("Location, Storage and Telephone permissions are required to use this app.");
        alertDialogBuilder.setMessage("Please enable these permissions in Permissions under app settings.");
        alertDialogBuilder.setNegativeButton("Go to setting", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                goToSettings();
            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    private void goToSettings() {
        Intent myAppSettings = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getApplication().getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myAppSettings, 0);
    }

    private int checkGooglePlayServices() {
        GoogleApiAvailability mGoogleApiAvailability = GoogleApiAvailability.getInstance();
        int result = mGoogleApiAvailability.isGooglePlayServicesAvailable(this.getApplicationContext());
        if( result != ConnectionResult.SUCCESS){
            mGoogleApiAvailability.showErrorDialogFragment(MapboxActivity.this, result, GMS_VERSION, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Intent intent = new Intent(MapboxActivity.this, MainFrameActivity.class);
                    MapboxActivity.this.startActivity(intent);
                }
            });
        }
        return result;
    }
    public void setLocationPermissionlayout()
    {
        if (ContextCompat.checkSelfPermission(MapboxActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            setting.setVisibility(View.VISIBLE);
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(-31.61224,153.08681)) // Sets the new camera position
                    .zoom(5) // Sets the zoom
                    .bearing(0) // Rotate the camera
//                    .tilt(30) // Set the camera tilt
                    .build(); // Creates a CameraPosition from the builder
        } else{
            setting.setVisibility(View.GONE);
        }
    }
}