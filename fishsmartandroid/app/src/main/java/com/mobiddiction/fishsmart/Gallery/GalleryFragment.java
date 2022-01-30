package com.mobiddiction.fishsmart.Gallery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobiddiction.fishsmart.CustomAlert.AlertDialog;
import com.mobiddiction.fishsmart.CustomAlert.OnItemClickListener;
import com.mobiddiction.fishsmart.ImageGridViewAdapter;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.SignIn.SignInActivity;
import com.mobiddiction.fishsmart.SplashActivity;
import com.mobiddiction.fishsmart.dao.GalleryImage;
import com.mobiddiction.fishsmart.util.CustomScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class GalleryFragment extends Fragment {

    int paging = 0;
    private static final String TAG = "GalleryFragment";
    ArrayList<GalleryItem> fullItems = new ArrayList<GalleryItem>();
    RecyclerView gridViewContacts;
    ListView listView;
    CustomScrollView nestedScrollView;
    GalleryListAdapter adapter;
    Handler handler = new Handler();
    boolean fromHome = false;
    ProgressBar wheel;
    private TextView errorText;

    public static GalleryFragment newInstance(boolean isFromHomes) {
        Bundle args = new Bundle();
        args.putBoolean("fromHome", isFromHomes);
        GalleryFragment f = new GalleryFragment();
        f.setArguments(args);
        return f;
    }

    @SuppressLint("ValidFragment")
    public GalleryFragment() {
    }

//    @Override
//    public void onCreate (Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        gridViewContacts = rootView.findViewById(R.id.gridViewContacts);
//        gridViewContacts.setItemAnimator(null);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        wheel = rootView.findViewById(R.id.progress_wheel);
        fromHome = getArguments().getBoolean("fromHome");
        gridViewContacts = rootView.findViewById(R.id.gridViewContacts);
        gridViewContacts.setItemAnimator(null);
        nestedScrollView = rootView.findViewById(R.id.nestedScrollView);
        listView = rootView.findViewById(R.id.list);
        errorText = rootView.findViewById(R.id.error_info);
        Log.d(TAG,"Requesting Images from Server");
        NetworkManager.getInstance().getGallery();

        return rootView;
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
        EventBus.getDefault().removeStickyEvent(event);
        if (event == BasicEvent.GALLERY_SUCCESS) {
            errorText.setVisibility(View.GONE);
            wheel.setVisibility(View.GONE);
            List<GalleryImage> galleryImageList = ModelManager.getInstance().getGalleryImage();
            if (fromHome) {
                StaggeredGridLayoutManager staggeredGridLayoutManagerHome = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL){
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                Log.d(TAG,"onEventMainThread fromHome");
                gridViewContacts.setLayoutManager(staggeredGridLayoutManagerHome);
                ImageGridViewAdapter customAdapter = new ImageGridViewAdapter(getActivity(), (ArrayList) galleryImageList);
                gridViewContacts.setAdapter(customAdapter);
            }
            else{
                Log.d(TAG,"onEventMainThread NOT fromHome");
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                gridViewContacts.setLayoutManager(staggeredGridLayoutManager);
                ImageGridViewAdapter customAdapter = new ImageGridViewAdapter(getActivity(), (ArrayList) galleryImageList);
                gridViewContacts.setAdapter(customAdapter);


            }
        }else if(event == BasicEvent.GALLERY_FAILED){
            errorText.setVisibility(View.VISIBLE);
        }
    }


    public void notifyGalleryDownloadSuccess(List<GalleryItem> items) {

        Log.d(TAG, "notifyDownloadSuccess " + items.size());

        for (GalleryItem item : items) {
            fullItems.add(item);
            // Log.d("MAP", "item " + item.getUrl());

        }

        handler.post(new Runnable() {

            @Override
            public void run() {
                wheel.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });


        isDownload = false;
        paging++;
    }

    boolean isDownload;

    public void downloadNext() {
        if (isDownload) {
        } else {
            // new GalleryAsyncTask(this, paging).execute();
            isDownload = true;
        }
    }
}