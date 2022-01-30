package com.mobiddiction.fishsmart.CatchLog;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.CatchLogBean;
import com.mobiddiction.fishsmart.bean.CatchLogLocationBean;
import com.mobiddiction.fishsmart.util.Utils;

import java.util.ArrayList;

/**
 * Created by Krunal on 28/11/2018.
 */
public class CatchLogCatchesFragment extends Fragment {
    private RecyclerView listviewCatches;
    private final static String LOG_TAG = CatchLogCatchesFragment.class.getSimpleName();
    private ArrayList<CatchLogLocationBean> locationList;
    private ArrayList<CatchLogBean> catchlogBean;
    private CatchLogCatchesAdapter catchLogCatchesAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catchlog_catches, container, false);
        listviewCatches = view.findViewById(R.id.listviewCatches);

        setLocationList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        listviewCatches.setLayoutManager(linearLayoutManager);
        listviewCatches.setHasFixedSize(true);

        setAdapter();

        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        final SwipeRefreshLayout mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        setLocationList();
                        setAdapter();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        return view;
    }

    private void setLocationList(){
        catchlogBean = (ArrayList<CatchLogBean>) getArguments().getSerializable("CatchLogData");
        locationList = (ArrayList<CatchLogLocationBean>) getArguments().getSerializable("catchesList");
        if (locationList.size() > 0) {
            listviewCatches.setVisibility(View.VISIBLE);
        } else {
            listviewCatches.setVisibility(View.GONE);
        }
    }

    private void setAdapter(){
        catchLogCatchesAdapter = new CatchLogCatchesAdapter(getActivity(), locationList, catchlogBean);
        listviewCatches.setAdapter(catchLogCatchesAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.freeMemory();
    }
}