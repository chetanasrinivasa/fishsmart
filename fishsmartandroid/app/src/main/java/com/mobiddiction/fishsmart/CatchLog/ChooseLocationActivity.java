package com.mobiddiction.fishsmart.CatchLog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.util.Utils;

import java.text.DecimalFormat;

/**
 * Created by Krunal on 30/11/2018.
 */
public class ChooseLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageButton backbtn;
    private ImageView img;
    private GoogleMap googleMap;
    private TextView txtPlaceYourCatch, longitude, latitude, edit;
    private double catchLatitude;
    private double catchLongitude;
    private LinearLayout mapLayout;
    private Marker marker;
    private DecimalFormat numberFormat = new DecimalFormat("#.00000");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            catchLatitude = b.getDouble(AddCatchlogActivity.EXTRA_LATITUDE);
            catchLongitude = b.getDouble(AddCatchlogActivity.EXTRA_LONGITUDE);
        }

        backbtn = findViewById(R.id.backbtn);
        img = findViewById(R.id.img);
        txtPlaceYourCatch = findViewById(R.id.txtPlaceYourCatch);
        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        edit = findViewById(R.id.edit);
        mapLayout = findViewById(R.id.mapLayout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        backbtn.setVisibility(View.GONE);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLatLon();
            }
        });

        mapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLatLon();
            }
        });

        edit.setVisibility(View.GONE);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPlaceYourCatch.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);
                mapLayout.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                marker.setVisible(false);

                LatLng latlng = googleMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
                latitude.setText("Latitude: " + numberFormat.format(latlng.latitude));
                longitude.setText("Longitude: " + numberFormat.format(latlng.longitude));

                googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        LatLng latlng = googleMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
                        latitude.setText("Latitude: " + numberFormat.format(latlng.latitude));
                        longitude.setText("Longitude: " + numberFormat.format(latlng.longitude));
                    }
                });
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;

        googleMap.getUiSettings().setAllGesturesEnabled(true);

        latitude.setText("Latitude: " + numberFormat.format(catchLatitude));
        longitude.setText("Longitude: " + numberFormat.format(catchLongitude));

        LatLng latLng;
        if (catchLatitude == 0.0)
            latLng = new LatLng(-33.865143, 151.209900);
        else
            latLng = new LatLng(catchLatitude, catchLongitude);

        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();
        // Setting the position for the marker
        markerOptions.position(latLng);
        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title("Latitude: " + numberFormat.format(catchLatitude) + " - " + "Longitude: " + numberFormat.format(catchLongitude));
        // Clears the previously touched position
        googleMap.clear();
        // Animating to the touched position
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        // Placing a marker on the touched position
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backbtn.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                marker = googleMap.addMarker(markerOptions);
                marker.showInfoWindow();
            }
        }, 2000);
    }

    private void getMyLatLon() {
        LatLng latlng = this.googleMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
        Intent intent = getIntent();
        intent.putExtra("lat", String.valueOf(latlng.latitude));
        intent.putExtra("lon", String.valueOf(latlng.longitude));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.freeMemory();
    }
}