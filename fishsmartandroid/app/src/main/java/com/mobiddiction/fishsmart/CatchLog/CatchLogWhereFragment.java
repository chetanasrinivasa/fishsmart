package com.mobiddiction.fishsmart.CatchLog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.CatchLogBean;
import com.mobiddiction.fishsmart.util.Utils;

import java.util.ArrayList;

/**
 * Created by Krunal on 29/11/2018.
 */
@SuppressLint("ValidFragment")
public class CatchLogWhereFragment extends Fragment implements View.OnClickListener {
    private ImageButton mylocation;
//    private ArrayList<CatchLogLocationBean> locationList;
    private ArrayList<CatchLogBean> catchLogBeanList;


    @SuppressLint("ValidFragment")
    public CatchLogWhereFragment(ArrayList<CatchLogBean> catchLogBeanList){
        this.catchLogBeanList = catchLogBeanList;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catchlog_map, container, false);
//        locationList = (ArrayList<CatchLogLocationBean>) getArguments().getSerializable("catchesList");
        mylocation = view.findViewById(R.id.mylocation);
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        mylocation.setOnClickListener(this);
        return view;
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        try {
//            map = googleMap;
//            map.setMyLocationEnabled(true);
//            map.getUiSettings().setMyLocationButtonEnabled(false);
//            generateMapPins();
//        } catch (SecurityException e) {
//            e.printStackTrace();
//            Toast.makeText(getActivity(), "Please grant location permission.", Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mylocation:
//                LatLng latLng;
//                if (Config.latitude == 0.0)
//                    latLng = new LatLng(-33.865143, 151.209900);
//                else
//                    latLng = new LatLng(Config.latitude, Config.longitude);
//
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
//                map.animateCamera(cameraUpdate);
                break;
        }
    }

//    private void generateMapPins() {
//        double lat = 0;
//        double lng = 0;
//        LatLngBounds bounds;
//        builder = new LatLngBounds.Builder();
//
//        for(int i = 0; i < catchLogBeanList.size(); i++){
//
//            lat = catchLogBeanList.get(i).location.lat;
//            lng = catchLogBeanList.get(i).location.lon;
//            drawMarker(new LatLng(lat, lng), catchLogBeanList.get(i).location.name,catchLogBeanList.get(i).id);
//        }
//        bounds = builder.build();
//        int width = getResources().getDisplayMetrics().widthPixels;
//        int height = getResources().getDisplayMetrics().heightPixels;
//        int padding = (int) (width * 0.20);
//        CameraUpdate cu;
//        if (catchLogBeanList.size() == 1) {
//            LatLng latLng = new LatLng(lat, lng);
//            cu = CameraUpdateFactory.newLatLngZoom(latLng, 10);
//        } else {
//            cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
//        }
//        map.animateCamera(cu);
//    }
//
//    private void drawMarker(LatLng point, String name, int id) {
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
//        markerOptions.title(name);
//        markerOptions.position(point);
//        map.addMarker(markerOptions).setTag(id);
//        builder.include(markerOptions.getPosition());
//        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                for(int i = 0; i < catchLogBeanList.size(); i++){
//                    if (marker.getTag().toString().equalsIgnoreCase(catchLogBeanList.get(i).id + "")) {
//                        Intent intent = new Intent(getActivity(), AddCatchlogActivity.class);
//                        intent.putExtra("CatchLogData", catchLogBeanList.get(i));
//                        intent.putExtra("isRecordExist", 1);
//                        startActivity(intent);
//                    }
//                }
//            }
//        });
//    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.freeMemory();
    }
}