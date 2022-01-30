package com.mobiddiction.fishsmart.CatchLog;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.CatchLogLocationBean;
import com.mobiddiction.fishsmart.util.Utils;

import java.util.ArrayList;

/**
 * Created by Krunal on 18/1/2019.
 */
public class ChooseLocationFromListActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private ImageButton backbtn;
    private GoogleMap googleMap;
    private RecyclerView recyclerLocation;
    private EditText edtSearch;
    private ArrayList<CatchLogLocationBean> localLocationList = new ArrayList<>();
    private double catchLatitude;
    private double catchLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location_from_list);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            catchLatitude = b.getDouble(AddCatchlogActivity.EXTRA_LATITUDE);
            catchLongitude = b.getDouble(AddCatchlogActivity.EXTRA_LONGITUDE);
        }

        localLocationList = CatchlogActivity.catchlogAllLocationList;
        backbtn = findViewById(R.id.backbtn);
        edtSearch = findViewById(R.id.edtSearch);
        recyclerLocation = findViewById(R.id.recyclerLocation);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChooseLocationFromListActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerLocation.setLayoutManager(linearLayoutManager);
        recyclerLocation.setHasFixedSize(true);
        ChooseLocationFromListAdapter chooseLocationFromListAdapter = new ChooseLocationFromListAdapter(ChooseLocationFromListActivity.this, localLocationList,
                new ChooseLocationFromListAdapter.OnItemClickListener() {
                    @Override public void onItemClick(CatchLogLocationBean item) {
                        catchLatitude = item.lat;
                        catchLongitude = item.lon;
                        getMyLatLon();
                    }
                });
        recyclerLocation.setAdapter(chooseLocationFromListAdapter);

        backbtn.setVisibility(View.GONE);
        backbtn.setOnClickListener(this);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                search(editable.toString().trim());
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        // mapFragment.getView().setClickable(false);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.getUiSettings().setAllGesturesEnabled(false);

        setGoogleMapData(catchLatitude, catchLongitude);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backbtn.setVisibility(View.VISIBLE);
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Intent intent = new Intent(ChooseLocationFromListActivity.this, ChooseLocationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putDouble(AddCatchlogActivity.EXTRA_LATITUDE, catchLatitude);
                        bundle.putDouble(AddCatchlogActivity.EXTRA_LONGITUDE, catchLongitude);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, AddCatchlogActivity.REQUEST_CHOOSE_LOCATION);
                    }
                });

            }
        }, 2000);
    }

    private void getMyLatLon() {
        Intent intent = getIntent();
        intent.putExtra("lat", String.valueOf(catchLatitude));
        intent.putExtra("lon", String.valueOf(catchLongitude));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.backbtn:
                getMyLatLon();
                break;
        }
    }

    private void search(String text) {
        ArrayList<CatchLogLocationBean> searchList = new ArrayList<>();
        if (text.length() == 0) {
            ChooseLocationFromListAdapter chooseLocationFromListAdapter = new ChooseLocationFromListAdapter(ChooseLocationFromListActivity.this,
                    localLocationList, new ChooseLocationFromListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(CatchLogLocationBean item) {
                    catchLatitude = item.lat;
                    catchLongitude = item.lon;
                    getMyLatLon();
                }
            });
            recyclerLocation.setAdapter(chooseLocationFromListAdapter);
            searchList.clear();
        } else {
            try {
                for (CatchLogLocationBean catchLogLocationBean : localLocationList) {
                    if (catchLogLocationBean.name.toLowerCase().contains(text.toLowerCase())) {
                        searchList.add(catchLogLocationBean);
                    }
                }
                ChooseLocationFromListAdapter chooseLocationFromListAdapter = new ChooseLocationFromListAdapter(ChooseLocationFromListActivity.this,
                        searchList, new ChooseLocationFromListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(CatchLogLocationBean item) {
                        catchLatitude = item.lat;
                        catchLongitude = item.lon;
                        getMyLatLon();
                    }
                });
                recyclerLocation.setAdapter(chooseLocationFromListAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setGoogleMapData(double latitude, double longitude) {
        LatLng latLng;
        if (latitude == 0.0)
            latLng = new LatLng(-33.865143, 151.209900);
        else
            latLng = new LatLng(latitude, longitude);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
        googleMap.addMarker(markerOptions);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                if (requestCode == AddCatchlogActivity.REQUEST_CHOOSE_LOCATION) {
                    String lat = data.getStringExtra("lat").trim();
                    String lon = data.getStringExtra("lon").trim();
                    catchLatitude = Double.parseDouble(lat);
                    catchLongitude = Double.parseDouble(lon);
                    getMyLatLon();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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