package com.mobiddiction.fishsmart.CatchLog;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.CatchLogSpeciesCaughtBean;
import com.mobiddiction.fishsmart.util.Utils;

import java.util.ArrayList;

/**
 * Created by Krunal on 28/11/2018.
 */
public class CatchLogSpeciesFragment extends Fragment implements View.OnClickListener {
    private TextView txtSaltWater;
    private TextView txtFreshWater;
    private SwipeRefreshLayout swiperefreshSalt;
    private SwipeRefreshLayout swiperefreshFresh;
    private RecyclerView listviewSalt;
    private RecyclerView listviewFresh;
    private ArrayList<ArrayList<CatchLogSpeciesCaughtBean>> allWaterList;
    private final static String LOG_TAG = CatchLogSpeciesFragment.class.getSimpleName();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catchlog_species, container, false);
        txtSaltWater = view.findViewById(R.id.txtSaltWater);
        txtFreshWater = view.findViewById(R.id.txtFreshWater);
        swiperefreshSalt = view.findViewById(R.id.swiperefreshSalt);
        swiperefreshFresh = view.findViewById(R.id.swiperefreshFresh);
        listviewSalt = view.findViewById(R.id.listviewSalt);
        listviewFresh = view.findViewById(R.id.listviewFresh);
        allWaterList = (ArrayList<ArrayList<CatchLogSpeciesCaughtBean>>) getArguments().getSerializable("allWaterList");
        ArrayList<CatchLogSpeciesCaughtBean> saltWaterList = allWaterList.get(0);
        ArrayList<CatchLogSpeciesCaughtBean> freshWaterList = allWaterList.get(1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        listviewSalt.setLayoutManager(gridLayoutManager);
        listviewSalt.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        listviewFresh.setLayoutManager(gridLayoutManager2);
        listviewFresh.setHasFixedSize(true);

        CatchLogSpeciesSaltWaterAdapter catchLogSpeciesSaltWaterAdapter = new CatchLogSpeciesSaltWaterAdapter(getActivity(), saltWaterList);
        listviewSalt.setAdapter(catchLogSpeciesSaltWaterAdapter);

        CatchLogSpeciesFreshWaterAdapter catchLogSpeciesFreshWaterAdapter = new CatchLogSpeciesFreshWaterAdapter(getActivity(), freshWaterList);
        listviewFresh.setAdapter(catchLogSpeciesFreshWaterAdapter);

        txtSaltWater.setOnClickListener(this);
        txtFreshWater.setOnClickListener(this);

        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        final SwipeRefreshLayout mySwipeSaltRefreshLayout = view.findViewById(R.id.swiperefreshSalt);
        mySwipeSaltRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        allWaterList = (ArrayList<ArrayList<CatchLogSpeciesCaughtBean>>) getArguments().getSerializable("allWaterList");
                        ArrayList<CatchLogSpeciesCaughtBean> saltWaterList = allWaterList.get(0);
                        CatchLogSpeciesSaltWaterAdapter catchLogSpeciesSaltWaterAdapter = new CatchLogSpeciesSaltWaterAdapter(getActivity(), saltWaterList);
                        listviewSalt.setAdapter(catchLogSpeciesSaltWaterAdapter);
                        mySwipeSaltRefreshLayout.setRefreshing(false);
                    }
                }
        );

        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        final SwipeRefreshLayout mySwipeFreshRefreshLayout = view.findViewById(R.id.swiperefreshFresh);
        mySwipeFreshRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        allWaterList = (ArrayList<ArrayList<CatchLogSpeciesCaughtBean>>) getArguments().getSerializable("allWaterList");
                        ArrayList<CatchLogSpeciesCaughtBean> freshWaterList = allWaterList.get(1);
                        CatchLogSpeciesFreshWaterAdapter catchLogSpeciesFreshWaterAdapter = new CatchLogSpeciesFreshWaterAdapter(getActivity(), freshWaterList);
                        listviewFresh.setAdapter(catchLogSpeciesFreshWaterAdapter);
                        mySwipeFreshRefreshLayout.setRefreshing(false);
                    }
                }
        );

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSaltWater:
                swiperefreshFresh.setVisibility(View.GONE);
                listviewFresh.setVisibility(View.GONE);
                listviewSalt.setVisibility(View.VISIBLE);
                swiperefreshSalt.setVisibility(View.VISIBLE);

                txtSaltWater.setBackgroundResource(R.drawable.button_redborder);
                txtFreshWater.setBackgroundResource(0);

                txtSaltWater.setTextColor(getResources().getColor(R.color.selectedtabtext));
                txtFreshWater.setTextColor(getResources().getColor(R.color.unselectedtabtext));
                break;
            case R.id.txtFreshWater:
                swiperefreshSalt.setVisibility(View.GONE);
                listviewSalt.setVisibility(View.GONE);
                swiperefreshFresh.setVisibility(View.VISIBLE);
                listviewFresh.setVisibility(View.VISIBLE);

                txtSaltWater.setBackgroundResource(0);
                txtFreshWater.setBackgroundResource(R.drawable.button_redborder);

                txtSaltWater.setTextColor(getResources().getColor(R.color.unselectedtabtext));
                txtFreshWater.setTextColor(getResources().getColor(R.color.selectedtabtext));
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.freeMemory();
    }
}