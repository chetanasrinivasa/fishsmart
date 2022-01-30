package com.mobiddiction.fishsmart.Maps;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.util.Utils;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Archa on 21/04/2016.
 */
public class MapsActivity extends BaseTabActivity implements View.OnTouchListener {

    ArrayList<MapModel> MapList;
    Polyline myPath_marine;
    Polyline myPath_re;
    MapView mapView;
    RelativeLayout dropdownbtn, mainlayout;
    LinearLayout dropdownlayout, layoutmarine, layoutartificial, layoutfish;
    Boolean dropdownFlag = false;
    TextView mapnametext;
    Drawable markerfish, markerartificial, currentloc, marineicon;
    ToggleButton marinetogglebtn, artificialtogglebtn, fishtogglebtn;
    public Dialog dialog = null;
    ProgressBar progress_bar;
    ImageButton mylocation;
    public Handler handler = new Handler();
    ImageView arrowimg;

    FishItemizedOverlay myItemizedOverlay_fish = null;
    ArtificialItemizedOverlay myItemizedOverlay_artificial = null;
    MarineItemizedOverlay myItemizedOverlay_marine = null;

    MyItemizedOverlay current_location = null;

    GeoPoint startPoint;
    ItemizedIconOverlay<OverlayItem> currentLocationOverlay;
    OverlayItem myLocationOverlayItem;

    OverlayItem artificialReefItem;
    OverlayItem marineParkZoneItem;
    OverlayItem fishAggrDeviceItem;

    boolean executeFlag = true;
    private int mPtrCount = 0;

    private float mPrimStartTouchEventX = -1;
    private float mPrimStartTouchEventY = -1;
    private float mSecStartTouchEventX = -1;
    private float mSecStartTouchEventY = -1;
    private float mPrimSecStartTouchDistance = 0;
    private int mViewScaledTouchSlop = 0;

    boolean ispinching = false;

    public ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
    public ArrayList<OverlayItem> fishItemList = new ArrayList<OverlayItem>();
    public ArrayList<OverlayItem> marineItemList = new ArrayList<OverlayItem>();
    public ArrayList<OverlayItem> curLocItemList = new ArrayList<OverlayItem>();

    public static final String TAG = "MapsActivity";

//    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState/*, R.layout.activity_maps, false*/);
        setContentView(R.layout.activity_maps);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(false);

        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        TextView nav_title = mActionBarView.findViewById(R.id.nav_title);
//
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .addApi(LocationServices.API)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .build();
//
//            mGoogleApiClient.connect();
//            Config.GpsOff = false;
//        }
//        else
//        {
//            Config.GpsOff = true;
//        }

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        Log.d("fishsmart", "on gps...");

        nav_title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
        nav_title.setText(Html.fromHtml("MAPS"));

        mapView = findViewById(R.id.mapview);
        arrowimg = findViewById(R.id.arrowimg);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setUseDataConnection(true);
        mapView.setOnTouchListener(this);

        // Configuration.getInstance().setTileFileSystemCacheMaxBytes(500000L);
        // Configuration.getInstance().setCacheMapTileOvershoot((short) 1000);

    //    OpenStreetMapTileProviderConstants.DEBUGMODE=true;
     //   OpenStreetMapTileProviderConstants.DEBUG_TILE_PROVIDERS=true;

       // String cachePath = getCacheDir().getAbsolutePath();
       // OpenStreetMapTileProviderConstants.setCachePath(cachePath);

        HomeTextChanger("MAPS",this);

     //   mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.setClickable(true);

        mapView.getOverlays().clear();

        markerfish = getResources().getDrawable(R.drawable.fadxicon);
        int markerWidth1 = markerfish.getIntrinsicWidth();
        int markerHeight1 = markerfish.getIntrinsicHeight();
        markerfish.setBounds(0, markerHeight1, markerWidth1, 0);

        markerartificial = getResources().getDrawable(R.drawable.reefxicon);
        int markerWidth2 = markerartificial.getIntrinsicWidth();
        int markerHeight2 = markerartificial.getIntrinsicHeight();
        markerartificial.setBounds(0, markerHeight2, markerWidth2, 0);

        currentloc = getResources().getDrawable(R.drawable.marker_default);
        int markerWidth3 = currentloc.getIntrinsicWidth();
        int markerHeight3 = currentloc.getIntrinsicHeight();
        currentloc.setBounds(0, markerHeight3, markerWidth3, 0);

        marineicon = getResources().getDrawable(R.drawable.transparent);
        marineicon.setBounds(0, 0, 0, 0);

        current_location = new MyItemizedOverlay(currentloc, curLocItemList, MapsActivity.this);

        myItemizedOverlay_fish = new FishItemizedOverlay(markerfish, fishItemList, MapsActivity.this);
        myItemizedOverlay_artificial = new ArtificialItemizedOverlay(markerartificial, overlayItemList, MapsActivity.this);
        myItemizedOverlay_marine = new MarineItemizedOverlay(marineicon, marineItemList, MapsActivity.this);

        IMapController mapController = mapView.getController();
        mapController.setZoom(14.0);
        if(Config.latitude == 0.0)
            startPoint = new GeoPoint(-33.865143, 151.209900);
        else
            startPoint = new GeoPoint(Config.latitude, Config.longitude);
        mapController.setCenter(startPoint);

        final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(myLocationOverlayItem);

        currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items , new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

            public boolean onItemSingleTapUp(final int index, final OverlayItem item) {

                return true;
            }
            public boolean onItemLongPress(final int index, final OverlayItem item) {
                return true;
            }
        }, MapsActivity.this);

        this.mapView.getOverlays().add(this.currentLocationOverlay);

        // current location

        // end current loc

        progress_bar = findViewById(R.id.progress_bar);
        dropdownbtn = findViewById(R.id.dropdownbtn);
        mainlayout = findViewById(R.id.mainlayout);
        layoutmarine = findViewById(R.id.layoutmarine);
        layoutartificial = findViewById(R.id.layoutartificial);
        layoutfish = findViewById(R.id.layoutfish);
        dropdownlayout = findViewById(R.id.dropdownlayout);
        mapnametext = findViewById(R.id.mapnametext);
        mylocation = findViewById(R.id.mylocation);

        marinetogglebtn = findViewById(R.id.marinetogglebtn);
        artificialtogglebtn = findViewById(R.id.artificialtogglebtn);
        fishtogglebtn = findViewById(R.id.fishtogglebtn);

        marinetogglebtn.setSelected(true);
        marinetogglebtn.setChecked(true);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                progress_bar.setVisibility(View.VISIBLE);
                MapList = new ArrayList<MapModel>();
                invokeApi();

            }
        }, 2000);

        dropdownbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!dropdownFlag) {

                    dropdownlayout.setVisibility(View.VISIBLE);
                        mapView.setAlpha(0.30f);
                        arrowimg.setBackgroundResource(R.drawable.uparrow);
                        dropdownFlag = true;

                } else {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progress_bar.setVisibility(View.VISIBLE);
                            mapView.getOverlays().remove(myItemizedOverlay_marine);
                            mapView.getOverlays().remove(myItemizedOverlay_artificial);
                            mapView.getOverlays().remove(myItemizedOverlay_fish);
                            mapView.getOverlays().remove(myPath_re);

                            invokeApi();
                        }
                    }, 500);

                    dropdownlayout.setVisibility(View.GONE);
                    arrowimg.setBackgroundResource(R.drawable.downarrow);
                    mapView.setAlpha(1f);
                    dropdownFlag = false;
                }
            }
        });

        mylocation.setOnClickListener(listener);
        marinetogglebtn.setOnClickListener(listener);
        artificialtogglebtn.setOnClickListener(listener);
        fishtogglebtn.setOnClickListener(listener);

        // green color to plot #7BBD64

        final ViewConfiguration viewConfig = ViewConfiguration.get(this);
        mViewScaledTouchSlop = viewConfig.getScaledTouchSlop();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int action = (event.getAction() & MotionEvent.ACTION_MASK);
        switch (action) {

            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:

                if (dropdownFlag == true) {

                    dropdownlayout.setVisibility(View.GONE);
                    arrowimg.setBackgroundResource(R.drawable.downarrow);
                    mapView.setAlpha(1f);
                    dropdownFlag = false;
                }

                mPtrCount++;
                if (mPtrCount == 1 && mPrimStartTouchEventY == -1 && mPrimStartTouchEventY == -1) {
                    mPrimStartTouchEventX = event.getX(0);
                    mPrimStartTouchEventY = event.getY(0);
                  //  Log.d("TAG", String.format("POINTER ONE X = %.5f, Y = %.5f", mPrimStartTouchEventX, mPrimStartTouchEventY));
                }
                if (mPtrCount == 2) {
                    // Starting distance between fingers
                    mSecStartTouchEventX = event.getX(1);
                    mSecStartTouchEventY = event.getY(1);
                    mPrimSecStartTouchDistance = distance(event, 0, 1);
                 //   Log.d("TAG", String.format("POINTER TWO X = %.5f, Y = %.5f", mSecStartTouchEventX, mSecStartTouchEventY));
                }

                break;

            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                //action is finishing at this point, so now I can do my refreshActionOnMap();

                mPtrCount--;
                if (mPtrCount < 2) {
                    mSecStartTouchEventX = -1;
                    mSecStartTouchEventY = -1;
                }
                if (mPtrCount < 1) {
                    mPrimStartTouchEventX = -1;
                    mPrimStartTouchEventY = -1;
                }

                if(!ispinching)
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mapView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                            @Override
                            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                                if (v.isInLayout()) {
                                    if (executeFlag == true) {
                                        if (!mapView.isAnimating()) {

                                            executeFlag = false;

                                            ExecutorService executor = Executors.newSingleThreadExecutor();
                                            try {
                                                progress_bar.setVisibility(View.VISIBLE);
                                                executor.submit(new MyTask()).get(5000, TimeUnit.MINUTES); // Timeout of 30 seconds.
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            } catch (ExecutionException e) {
                                                e.printStackTrace();
                                            } catch (TimeoutException e) {
                                                e.printStackTrace();
                                            }
                                            executor.shutdown();

                                            if (executor.isShutdown()) {
                                                //  progress_bar.setVisibility(View.GONE);
                                                Log.d("process##", "yes shutdown");
                                            } else {
                                                Log.d("process##", "not shutdown");
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                }, 5000);

                break;

            case MotionEvent.ACTION_MOVE:

                boolean isPrimMoving = isScrollGesture(event, 0, mPrimStartTouchEventX, mPrimStartTouchEventY);
                boolean isSecMoving = (mPtrCount > 1 && isScrollGesture(event, 1, mSecStartTouchEventX, mSecStartTouchEventY));

                // There is a chance that the gesture may be a scroll
                if (mPtrCount > 1 && isPinchGesture(event)) {
                    Log.d("TAG", "PINCH! OUCH!");
                    ispinching = true;

                } else if (isPrimMoving || isSecMoving) {
                    /*// A 1 finger or 2 finger scroll.
                    if (isPrimMoving && isSecMoving) {
                        Log.d("TAG", "Two finger scroll");
                    } else {
                        Log.d("TAG", "One finger scroll");
                    }*/

                    Log.d("TAG", "scroll");
                    ispinching = false;
                }

                break;
        }
        return false;
    }

    private boolean isScrollGesture(MotionEvent event, int ptrIndex, float originalX, float originalY){
        float moveX = Math.abs(event.getX(ptrIndex) - originalX);
        float moveY = Math.abs(event.getY(ptrIndex) - originalY);

        return moveX > mViewScaledTouchSlop || moveY > mViewScaledTouchSlop;
    }

    private boolean isPinchGesture(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            final float distanceCurrent = distance(event, 0, 1);
            final float diffPrimX = mPrimStartTouchEventX - event.getX(0);
            final float diffPrimY = mPrimStartTouchEventY - event.getY(0);
            final float diffSecX = mSecStartTouchEventX - event.getX(1);
            final float diffSecY = mSecStartTouchEventY - event.getY(1);

            // if the distance between the two fingers has increased past
            // our threshold
            // mPinchClamp = false; // don't clamp initially
            return Math.abs(distanceCurrent - mPrimSecStartTouchDistance) > mViewScaledTouchSlop
                    // and the fingers are moving in opposing directions
                    && (diffPrimY * diffSecY) <= 0
                    && (diffPrimX * diffSecX) <= 0;
        }

        return false;
    }

    private float distance(MotionEvent event, int first, int second) {
        if (event.getPointerCount() >= 2) {
            final float x = event.getX(first) - event.getX(second);
            final float y = event.getY(first) - event.getY(second);

            return (float) Math.sqrt(x * x + y * y);
        } else {
            return 0;
        }
    }

    static class MyPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d("TAG", "PINCH! OUCH!");
            return true;
        }
    }

    class MyTask implements Runnable
    {
        public void run() {
            // add your code here
            mapView.getOverlays().remove(myItemizedOverlay_marine);
            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            invokeApi();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .addApi(LocationServices.API)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .build();
//
//            mGoogleApiClient.connect();
//            Config.GpsOff = false;
//        }
//        else
//        {
//            Config.GpsOff = true;
//        }
//        if(Config.GpsOff)
//        {
//            showGPSDisabledAlertToUser();
//        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        Utils.freeMemory();

     //   mapView.setBuiltInZoomControls(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

      //  MapList.clear();
    }

    private static GeoPoint geoPointFromScreenCoords(int x, int y, int oldleft, int oldright, MapView vw){
        if (x < 0 || y < 0 || x > vw.getWidth() || y > vw.getHeight()){
            return null; // coord out of bounds
        }
        // Get the top left GeoPoint
        Projection projection = vw.getProjection();
        GeoPoint geoPointTopLeft = (GeoPoint) projection.fromPixels(0, 0);
        Point topLeftPoint = new Point();
        // Get the top left Point (includes osmdroid offsets)
        projection.toPixels(geoPointTopLeft, topLeftPoint);
        // get the GeoPoint of any point on screen
        GeoPoint rtnGeoPoint = (GeoPoint) projection.fromPixels(x, y);
        return rtnGeoPoint;
    }

    private static GeoPoint geoPointFromScreenCoordsBottom(int x, int y, int oldleft, int oldright, MapView vw){
        if (x < 0 || y < 0 || x > vw.getWidth() || y > vw.getHeight()){
            return null; // coord out of bounds
        }
        // Get the top left GeoPoint
        Projection projection = vw.getProjection();
        GeoPoint geoPointTopLeft = (GeoPoint) projection.fromPixels(x, y);
        Point topLeftPoint = new Point();
        // Get the top left Point (includes osmdroid offsets)
        projection.toPixels(geoPointTopLeft, topLeftPoint);
        // get the GeoPoint of any point on screen
        GeoPoint rtnGeoPoint = (GeoPoint) projection.fromPixels(x, y);
        return rtnGeoPoint;
    }

    private static Point pointFromGeoPoint(GeoPoint gp, MapView vw){

        Point rtnPoint = new Point();
        Projection projection = vw.getProjection();
        projection.toPixels(gp, rtnPoint);
        // Get the top left GeoPoint
        GeoPoint geoPointTopLeft = (GeoPoint) projection.fromPixels(0, 0);
        Point topLeftPoint = new Point();
        // Get the top left Point (includes osmdroid offsets)
        projection.toPixels(geoPointTopLeft, topLeftPoint);
        rtnPoint.x-= topLeftPoint.x; // remove offsets
        rtnPoint.y -= topLeftPoint.y;
        if (rtnPoint.x > vw.getWidth() || rtnPoint.y > vw.getHeight() ||
                rtnPoint.x < 0 || rtnPoint.y < 0){
            return null; // gp must be off the screen
        }
        return rtnPoint;
    }

    public void invokeApi()
    {
        if(marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked())
        {
            Log.d("receiveItems", "MARINEPARKZONES ");
            mapnametext.setText("Marine Protected Areas and Spearfishing Closures");

          //  mapView.getOverlays().remove(myItemizedOverlay_marine);
         //   mapView.getOverlays().remove(myItemizedOverlay_artificial);
         //   mapView.getOverlays().remove(myItemizedOverlay_fish);
          //  mapView.getOverlays().remove(myPath_re);
         //   mapView.getOverlays().remove(myPath_marine);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=MARINEPARKZONES&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");
                }
            });
        }
        else if(marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked())
        {
            Log.d("receiveItems", "MARINEPARKZONES&mapType=ARTIFICIALREEFS");
            mapnametext.setText("Marine Protected Areas and Spearfishing Closures,Artificial Reefs");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=MARINEPARKZONES&mapType=ARTIFICIALREEFS&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");

                }
            });
        }
        else if(marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked()) {

            Log.d("receiveItems", "MARINEPARKZONES&mapType=ARTIFICIALREEFS&mapType=RECREATIONALFISHINGAREAS");
            mapnametext.setText("Marine Protected Areas and Spearfishing Closures,Artificial Reefs,Recreational Fishing");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=MARINEPARKZONES&mapType=ARTIFICIALREEFS&mapType=RECREATIONALFISHINGAREAS&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");

                }
            });

        }
        else if(marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            Log.d("receiveItems", "MARINEPARKZONES&mapType=ARTIFICIALREEFS&mapType=FISHAGGREGATINGDEVICES");
            mapnametext.setText("Marine Protected Areas and Spearfishing Closures,Artificial Reefs,Recreational Fishing Havens,Fish Aggregating Devices");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=MARINEPARKZONES&mapType=ARTIFICIALREEFS&mapType=FISHAGGREGATINGDEVICES&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");

                }
            });

        }
        else if(!marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            Log.d("receiveItems", "ARTIFICIALREEFS&mapType=FISHAGGREGATINGDEVICES");
            mapnametext.setText("Artificial Reefs,Fish Aggregating Devices");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=ARTIFICIALREEFS&mapType=FISHAGGREGATINGDEVICES&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");

                }
            });

        }
        else if(!marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            Log.d("receiveItems", "RECREATIONALFISHINGAREAS&mapType=FISHAGGREGATINGDEVICES");
            mapnametext.setText("Fish Aggregating Devices");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=RECREATIONALFISHINGAREAS&mapType=FISHAGGREGATINGDEVICES&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");

                }
            });

        }
        else if(marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            Log.d("receiveItems", "MARINEPARKZONES&mapType=FISHAGGREGATINGDEVICES");
            mapnametext.setText("Marine Protected Areas and Spearfishing Closures,Fish Aggregating Devices");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=MARINEPARKZONES&mapType=FISHAGGREGATINGDEVICES&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");

                }
            });

        }
        else if(marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            Log.d("receiveItems", "MARINEPARKZONES&mapType=ARTIFICIALREEFS&mapType=FISHAGGREGATINGDEVICES");
            mapnametext.setText("Marine Protected Areas and Spearfishing Closures,Artificial Reefs,Fish Aggregating Devices");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=MARINEPARKZONES&mapType=ARTIFICIALREEFS&mapType=FISHAGGREGATINGDEVICES&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");

                }
            });
        }
        else if(!marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && fishtogglebtn.isChecked())
        {
            Log.d("receiveItems", "FISHAGGREGATINGDEVICES");
            mapnametext.setText("Fish Aggregating Devices");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=FISHAGGREGATINGDEVICES&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");

                }
            });
        }
        else if(!marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked()) {

            Log.d("receiveItems", "MARINEPARKZONES&mapType=ARTIFICIALREEFS ");
            mapnametext.setText("Marine Protected Areas and Spearfishing Closures,Artificial Reefs");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=ARTIFICIALREEFS");

                }
            });

        }
        else if(!marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked()) {

            Log.d("receiveItems", "ARTIFICIALREEFS ");
            mapnametext.setText("Artificial Reefs");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=ARTIFICIALREEFS&mapType=RECREATIONALFISHINGAREAS&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");

                }
            });

        }
        else if(marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked()) {

            Log.d("receiveItems", "MARINEPARKZONES&mapType=RECREATIONALFISHINGAREAS");
            mapnametext.setText("Marine Protected Areas and Spearfishing Closures,Recreational Fishing Havens");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=MARINEPARKZONES&mapType=RECREATIONALFISHINGAREAS&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");

                }
            });

        }
        else if(marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            Log.d("receiveItems", "MARINEPARKZONES&mapType=FISHAGGREGATINGDEVICES");
            mapnametext.setText("Marine Protected Areas and Spearfishing Closures,Fish Aggregating Devices");

            mapView.getOverlays().remove(myItemizedOverlay_artificial);
            mapView.getOverlays().remove(myItemizedOverlay_fish);
            mapView.getOverlays().remove(myPath_re);

            GeoPoint centerPoint = mapView.getProjection().getBoundingBox().getCenter();
            Point p = pointFromGeoPoint(centerPoint, mapView);
            final GeoPoint p2 = geoPointFromScreenCoords(p.x, p.y, 0, 0, mapView);
            final GeoPoint t2 = geoPointFromScreenCoordsBottom(p.y, p.x, 0, 0, mapView);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    new MapAsync(MapsActivity.this, MapList).execute("/api/mapData?mapType=MARINEPARKZONES&mapType=FISHAGGREGATINGDEVICES&topLeftLat=" + p2.getLatitude() + "&topLeftLon=" + p2.getLongitude() + "&bottomRightLat=" + t2.getLatitude() + "&bottomRightLon=" + t2.getLongitude() + "&zoomLevel=10");

                }
            });

        }
        else if(!marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked())
        {
            mapnametext.setText("");
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

                        title.setText("Please select one option");
                        thanksbtn.setText("Ok");
                        desc.setText("");
                        dialog = new Dialog(MapsActivity.this);

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
    }

    View.OnClickListener listener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.mylocation)
            {
                GeoPoint current;
                if(Config.latitude == 0.0)
                    current = new GeoPoint(-33.865143, 151.209900);
                else
                    current = new GeoPoint(Config.latitude, Config.longitude);
                current_location.addItem(current, "currentlocation", "currentlocation");
                mapView.getOverlays().add(current_location);
                mapView.getController().animateTo(current);

            }

            if (v.getId() == R.id.marinetogglebtn)
            {
                if(marinetogglebtn.isChecked())
                {
                    marinetogglebtn.setSelected(true);
                }
                else
                {
                    marinetogglebtn.setSelected(false);
                }
            }

            if (v.getId() == R.id.artificialtogglebtn)
            {
                if(artificialtogglebtn.isChecked())
                {
                    artificialtogglebtn.setSelected(true);
                }
                else
                {
                    artificialtogglebtn.setSelected(false);
                }
            }

            if (v.getId() == R.id.fishtogglebtn)
            {
                if(fishtogglebtn.isChecked())
                {
                    fishtogglebtn.setSelected(true);
                }
                else
                {
                    fishtogglebtn.setSelected(false);
                }
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.key:
                try {

                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable(){

                        public void run() {

                            LayoutInflater inflater = getLayoutInflater();
                            final View dialoglayout = inflater.inflate(R.layout.mapkey_popup, null);
                            YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                            TextView title = dialoglayout.findViewById(R.id.title);
                            TextView text1 = dialoglayout.findViewById(R.id.text1);
                            TextView text2 = dialoglayout.findViewById(R.id.text2);
                            TextView text3 = dialoglayout.findViewById(R.id.text3);
                            TextView text4 = dialoglayout.findViewById(R.id.text4);
                            TextView text5 = dialoglayout.findViewById(R.id.text5);
                            TextView text6 = dialoglayout.findViewById(R.id.text6);
                            TextView text7 = dialoglayout.findViewById(R.id.text7);
                            TextView text8 = dialoglayout.findViewById(R.id.text8);
                            Button thanksbtn  = dialoglayout.findViewById(R.id.thanksbtn);

                            title.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                            thanksbtn.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                            text1.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                            text2.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                            text3.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                            text4.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                            text5.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                            text6.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                            text7.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));
                            text8.setTypeface(Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf"));

                            dialog = new Dialog(MapsActivity.this);

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

                return true;
            case R.id.info:

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
                                desc.setText("Due to limitations with GPS technology and mapping software, this information is a guide only. It does not replace legislation applying to, or affecting recreational fishing. It cannot be used as a defence in a court of law. \nFull operation of the app requires a network connection. If you know you are going to be in an area with poor signal strength consider saving a guide within the Rules section. If you experience poor performance please refresh the app.");

                                dialog = new Dialog(MapsActivity.this);

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

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void receiveItems() {

        Log.d("receiveItems", "receiveItems");

      //  mapView.getOverlays().clear();

        myPath_marine = new Polyline();
        myPath_marine.setColor(Color.BLACK);
        myPath_marine.getPaint().setStrokeWidth(10);

        myPath_re = new Polyline();
        myPath_re.setColor(Color.BLACK);
        myPath_re.getPaint().setStrokeWidth(10);

        if(marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked())
        {
            final int mapsize = MapList.size();

            if(mapsize < 50)
            for (int i = 0; i < mapsize; i++) {

                if (MapList.get(i).mapType.equals("Marine Protected Areas and Spearfishing Closures")) {

                    myPath_marine.getPaint().setColor(Color.BLACK);
                    myPath_marine.getPaint().setStyle(Paint.Style.FILL);

                 //   final int mappinsize = MapList.get(i).getMapPins().size();

                    final int finalI = i;

                    myPath_marine.addPoint(new GeoPoint(Float.parseFloat(MapList.get(i).title), Float.parseFloat(MapList.get(i).desc)));
                    myPath_marine.addPoint(new GeoPoint(Float.parseFloat(MapList.get(i).color), Float.parseFloat(MapList.get(i).id)));

                    mapView.getOverlays().add(myPath_marine);
                    mapView.getOverlays().add(myItemizedOverlay_marine);
                    marineItemList.add(marineParkZoneItem);


                   /* Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {

                        public void run() {*/

                         //   if(mappinsize < 500)
                            /*for (int j = 0; j < mappinsize; j++) {

                                String lat = MapList.get(finalI).getMapPins().get(j).lat;
                                String lng = MapList.get(finalI).getMapPins().get(j).lon;
                                String id = MapList.get(finalI).id;
                                String maptype = MapList.get(finalI).getMapPins().get(j).mapType;

                                if (maptype.equals("Marine Park Zones")) {

                                    myPath_marine.addPoint(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                                    myItemizedOverlay_marine.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                    marineParkZoneItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                                }
                            }*/

                       /* }
                    });*/
                }



            }


        }
        else if(marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked())
        {
            int mapsize = MapList.size();

        //    if(mapsize < 20)
            for (int i = 0; i < mapsize; i++) {

                if (MapList.get(i).mapType.equals("Marine Protected Areas and Spearfishing Closures")) {

                    myPath_marine.getPaint().setColor(Color.parseColor(MapList.get(i).color));
                    myPath_marine.getPaint().setStyle(Paint.Style.FILL);

                    final int mappinsize = MapList.get(i).getMapPins().size();

                    Handler h = new Handler(Looper.getMainLooper());
                    final int finalI = i;
                    h.post(new Runnable() {

                        public void run() {

                            if (MapList != null && MapList.size() != 0)
                            //    if(mappinsize < 500)
                                for (int j = 0; j < mappinsize; j++) {

                                    String lat = MapList.get(finalI).getMapPins().get(j).lat;
                                    String lng = MapList.get(finalI).getMapPins().get(j).lon;
                                    String id = MapList.get(finalI).id;
                                    String maptype = MapList.get(finalI).getMapPins().get(j).mapType;

                                    if (maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                                        myPath_marine.addPoint(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                                        myItemizedOverlay_marine.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                        marineParkZoneItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                                    }
                                }

                            mapView.getOverlays().add(myPath_marine);
                            mapView.getOverlays().add(myItemizedOverlay_marine);
                            marineItemList.add(marineParkZoneItem);


                        }
                    });

                }
                else if(MapList.get(i).mapType.equals("Artificial Reefs"))
                {
                    int mappinsize = MapList.get(i).getMapPins().size();

                    if (MapList != null && MapList.size() != 0)

                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Artificial Reefs"))
                            {
                                myItemizedOverlay_artificial.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                artificialReefItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_artificial);
                    overlayItemList.add(artificialReefItem);
                }
            }
        }
        else if(marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked()) {

            int mapsize = MapList.size();

        //    if(mapsize < 20)
            for (int i = 0; i < mapsize; i++) {

                if (MapList.get(i).mapType.equals("Marine Protected Areas and Spearfishing Closures")) {

                    myPath_marine.getPaint().setColor(Color.parseColor(MapList.get(i).color));
                    myPath_marine.getPaint().setStyle(Paint.Style.FILL);

                    int mappinsize = MapList.get(i).getMapPins().size();

                    if (MapList != null && mapsize != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if (maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                                myPath_marine.addPoint(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                                myItemizedOverlay_marine.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                marineParkZoneItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myPath_marine);
                    mapView.getOverlays().add(myItemizedOverlay_marine);
                    marineItemList.add(marineParkZoneItem);

                }

                else if(MapList.get(i).mapType.equals("Artificial Reefs")) {

                    int mappinsize = MapList.get(i).getMapPins().size();

                    if (MapList != null && mapsize != 0)

                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Artificial Reefs"))
                            {
                                myItemizedOverlay_artificial.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                artificialReefItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_artificial);
                    overlayItemList.add(artificialReefItem);
                }

            }
        }
        else if(marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            int mapsize = MapList.size();

         //   if(mapsize < 20)
            for (int i = 0; i < mapsize; i++) {

                String color = MapList.get(i).color;
                int mappinsize = MapList.get(i).getMapPins().size();
                String main_maptype = MapList.get(i).mapType;

                if (main_maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                    myPath_marine.getPaint().setColor(Color.parseColor(color));
                    myPath_marine.getPaint().setStyle(Paint.Style.FILL);

                    if (MapList != null && mapsize != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            final String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if (maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                                myPath_marine.addPoint(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                                myItemizedOverlay_marine.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                marineParkZoneItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myPath_marine);
                    mapView.getOverlays().add(myItemizedOverlay_marine);
                    marineItemList.add(marineParkZoneItem);

                }
                else if (main_maptype.equals("Artificial Reefs")) {

                    if (MapList != null && mapsize != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            final String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Artificial Reefs"))
                            {
                                myItemizedOverlay_artificial.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                artificialReefItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_artificial);
                    overlayItemList.add(artificialReefItem);
                }
                else if (main_maptype.equals("Fish Aggregating Devices")) {

                    if (MapList != null && mapsize != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Fish Aggregating Devices"))
                            {
                                myItemizedOverlay_fish.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                fishAggrDeviceItem = new OverlayItem(id, "fishAggrDevice", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_fish);
                    fishItemList.add(fishAggrDeviceItem);
                }
            }
        }
        else if(!marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            int mapsize = MapList.size();

         //   if(mapsize < 20)
            for (int i = 0; i < mapsize; i++) {

                String color = MapList.get(i).color;
                int mappinsize = MapList.get(i).getMapPins().size();
                String main_maptype = MapList.get(i).mapType;

                if (main_maptype.equals("Artificial Reefs")) {

                    if (MapList != null && mapsize != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Artificial Reefs"))
                            {
                                myItemizedOverlay_artificial.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                artificialReefItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_artificial);
                    overlayItemList.add(artificialReefItem);
                }
                else if (main_maptype.equals("Fish Aggregating Devices")) {

                    if (MapList != null && mapsize != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Fish Aggregating Devices"))
                            {
                                myItemizedOverlay_fish.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                fishAggrDeviceItem = new OverlayItem(id, "fishAggrDevice", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_fish);
                    fishItemList.add(fishAggrDeviceItem);
                }
            }
        }
        else if(!marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            int mapsize = MapList.size();

        //    if(mapsize < 20)
            for (int i = 0; i < mapsize; i++) {

                String color = MapList.get(i).color;
                int mappinsize = MapList.get(i).getMapPins().size();
                String main_maptype = MapList.get(i).mapType;

                if (main_maptype.equals("Fish Aggregating Devices")) {

                    if (MapList != null && mapsize != 0)
                     //   if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Fish Aggregating Devices"))
                            {
                                myItemizedOverlay_fish.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                fishAggrDeviceItem = new OverlayItem(id, "fishAggrDevice", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_fish);
                    fishItemList.add(fishAggrDeviceItem);
                }
            }
        }
        else if(marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            int mapsize = MapList.size();

        //    if(mapsize < 20)
            for (int i = 0; i < mapsize; i++) {

                String color = MapList.get(i).color;
                int mappinsize = MapList.get(i).getMapPins().size();
                String main_maptype = MapList.get(i).mapType;

                if (main_maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                    myPath_marine.getPaint().setColor(Color.parseColor(color));
                    myPath_marine.getPaint().setStyle(Paint.Style.FILL);

                    if (MapList != null && mapsize != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if (maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                                myPath_marine.addPoint(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                                myItemizedOverlay_marine.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                marineParkZoneItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myPath_marine);
                    mapView.getOverlays().add(myItemizedOverlay_marine);
                    marineItemList.add(marineParkZoneItem);

                }
                else if (main_maptype.equals("Fish Aggregating Devices")) {

                    if (MapList != null && MapList.size() != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < MapList.get(i).getMapPins().size(); j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Fish Aggregating Devices"))
                            {
                                myItemizedOverlay_fish.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                fishAggrDeviceItem = new OverlayItem(id, "fishAggrDevice", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_fish);
                    fishItemList.add(fishAggrDeviceItem);
                }
            }
        }
        else if(marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            int mapsize = MapList.size();

        //    if(mapsize < 20)
            for (int i = 0; i < mapsize; i++) {

                String color = MapList.get(i).color;
                int mappinsize = MapList.get(i).getMapPins().size();
                String main_maptype = MapList.get(i).mapType;

                if (main_maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                    myPath_marine.getPaint().setColor(Color.parseColor(color));
                    myPath_marine.getPaint().setStyle(Paint.Style.FILL);

                    if (MapList != null && MapList.size() != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if (maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                                myPath_marine.addPoint(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                                myItemizedOverlay_marine.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                marineParkZoneItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myPath_marine);
                    mapView.getOverlays().add(myItemizedOverlay_marine);
                    marineItemList.add(marineParkZoneItem);

                }
                else if (main_maptype.equals("Artificial Reefs")) {

                    if (MapList != null && mapsize != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Artificial Reefs"))
                            {
                                myItemizedOverlay_artificial.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                artificialReefItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_artificial);
                    overlayItemList.add(artificialReefItem);
                }

                else if (main_maptype.equals("Fish Aggregating Devices")) {

                    if (MapList != null && mapsize != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Fish Aggregating Devices"))
                            {
                                myItemizedOverlay_fish.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                fishAggrDeviceItem = new OverlayItem( id, "fishAggrDevice", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_fish);
                    fishItemList.add(fishAggrDeviceItem);
                }
            }
        }
        else if(!marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && fishtogglebtn.isChecked())
        {

            int mapsize = MapList.size();

        //    if(mapsize < 20)
            for (int i = 0; i < mapsize; i++) {

                int mappinsize = MapList.get(i).getMapPins().size();
                String main_maptype = MapList.get(i).mapType;

                if (main_maptype.equals("Fish Aggregating Devices")) {

                    if (MapList != null && mapsize != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Fish Aggregating Devices"))
                            {
                                myItemizedOverlay_fish.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                fishAggrDeviceItem = new OverlayItem(id, "fishAggrDevice", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_fish);
                    fishItemList.add(fishAggrDeviceItem);
                }
            }
        }
        else if(!marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked()) {

            int mapsize = MapList.size();

         //   if(mapsize < 20)
            for (int i = 0; i < mapsize; i++) {

                int mappinsize = MapList.get(i).getMapPins().size();
                String main_maptype = MapList.get(i).mapType;

                if (main_maptype.equals("Artificial Reefs")) {

                    if (MapList != null && MapList.size() != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Artificial Reefs"))
                            {
                                myItemizedOverlay_artificial.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                artificialReefItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_artificial);
                    overlayItemList.add(artificialReefItem);
                }
            }
        }
        else if(!marinetogglebtn.isChecked() && artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked()) {

            int mapsize = MapList.size();

         //   if(mapsize < 20)
            for (int i = 0; i < mapsize; i++) {

                String color = MapList.get(i).color;
                int mappinsize = MapList.get(i).getMapPins().size();
                String main_maptype = MapList.get(i).mapType;

                if (main_maptype.equals("Artificial Reefs")) {

                    if (MapList != null && mapsize != 0)
                    //    if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(MapList.get(i).getMapPins().get(j).mapType.equals("Artificial Reefs"))
                            {
                                myItemizedOverlay_artificial.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                artificialReefItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_artificial);
                    overlayItemList.add(artificialReefItem);
                }
            }
        }
        else if(marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && !fishtogglebtn.isChecked()) {

            int mapsize = MapList.size();

          //  if(mapsize < 500)
            for (int i = 0; i < mapsize; i++) {

                String color = MapList.get(i).color;
                int mappinsize = MapList.get(i).getMapPins().size();
                String main_maptype = MapList.get(i).mapType;

                if (main_maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                    myPath_marine.getPaint().setColor(Color.parseColor(color));
                    myPath_marine.getPaint().setStyle(Paint.Style.FILL);

                    if (MapList != null && MapList.size() != 0)
                     //   if(mappinsize < 500)
                        for (int j = 0; j < MapList.get(i).getMapPins().size(); j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if (maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                                myPath_marine.addPoint(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                                myItemizedOverlay_marine.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                marineParkZoneItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myPath_marine);
                    mapView.getOverlays().add(myItemizedOverlay_marine);
                    marineItemList.add(marineParkZoneItem);

                }
            }
        }
        else if(marinetogglebtn.isChecked() && !artificialtogglebtn.isChecked() && fishtogglebtn.isChecked()) {

            int mapsize = MapList.size();

        //    if(mapsize < 20)
            for (int i = 0; i < mapsize; i++) {

                String color = MapList.get(i).color;
                int mappinsize = MapList.get(i).getMapPins().size();
                String main_maptype = MapList.get(i).mapType;

                if (main_maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                    myPath_marine.getPaint().setColor(Color.parseColor(color));
                    myPath_marine.getPaint().setStyle(Paint.Style.FILL);

                    if (MapList != null && mapsize != 0)
                      //  if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if (maptype.equals("Marine Protected Areas and Spearfishing Closures")) {

                                myPath_marine.addPoint(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                                myItemizedOverlay_marine.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                marineParkZoneItem = new OverlayItem("Here", "Current Position", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));

                            }
                        }

                    mapView.getOverlays().add(myPath_marine);
                    mapView.getOverlays().add(myItemizedOverlay_marine);
                    marineItemList.add(marineParkZoneItem);

                }

                else if (main_maptype.equals("Fish Aggregating Devices")) {

                    if (MapList != null && mapsize != 0)
                     //   if(mappinsize < 500)
                        for (int j = 0; j < mappinsize; j++) {

                            String lat = MapList.get(i).getMapPins().get(j).lat;
                            String lng = MapList.get(i).getMapPins().get(j).lon;
                            String id = MapList.get(i).id;
                            String maptype = MapList.get(i).getMapPins().get(j).mapType;

                            if(maptype.equals("Fish Aggregating Devices"))
                            {
                                myItemizedOverlay_fish.addItem(new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)), id, id);
                                fishAggrDeviceItem = new OverlayItem(id, "fishAggrDevice", new GeoPoint(Float.parseFloat(lat), Float.parseFloat(lng)));
                            }
                        }

                    mapView.getOverlays().add(myItemizedOverlay_fish);
                    fishItemList.add(fishAggrDeviceItem);
                }
            }
        }

        //////

        mapView.invalidate();
        progress_bar.setVisibility(View.GONE);

        executeFlag = true;
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

}