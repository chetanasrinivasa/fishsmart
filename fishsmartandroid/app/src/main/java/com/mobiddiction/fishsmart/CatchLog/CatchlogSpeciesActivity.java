package com.mobiddiction.fishsmart.CatchLog;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.CatchLogSpeciesCaughtBean;
import com.mobiddiction.fishsmart.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Krunal on 30/11/2018.
 */
public class CatchlogSpeciesActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton backbtn;
    private EditText edtSearch;
    private TextView txtSaltWater;
    private TextView txtFreshWater;
    private RecyclerView listviewSalt;
    private RecyclerView listviewFresh;
    private RecyclerView listviewSearch;
    private List<CatchLogSpeciesCaughtBean> saltwaterSpeciesList;
    private List<CatchLogSpeciesCaughtBean> freshwaterSpeciesList;
    private List<CatchLogSpeciesCaughtBean> searchSpeciesList;
    private int lastSelectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catchlog_species);

        backbtn = findViewById(R.id.backbtn);
        edtSearch = findViewById(R.id.edtSearch);
        txtSaltWater = findViewById(R.id.txtSaltWater);
        txtFreshWater = findViewById(R.id.txtFreshWater);
        listviewSalt = findViewById(R.id.listviewSalt);
        listviewFresh = findViewById(R.id.listviewFresh);
        listviewSearch = findViewById(R.id.listviewSearch);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        txtSaltWater.setTypeface(typeface);
        txtFreshWater.setTypeface(typeface);
        edtSearch.setTypeface(typeface);

        if (CatchlogActivity.allCatchWaterList != null && CatchlogActivity.allCatchWaterList.size() > 0) {
            saltwaterSpeciesList = CatchlogActivity.allCatchWaterList.get(0);
            Collections.sort(saltwaterSpeciesList, new ObjectComparator());
            freshwaterSpeciesList = CatchlogActivity.allCatchWaterList.get(1);
            Collections.sort(freshwaterSpeciesList, new ObjectComparator());
        }else{
            Intent i = new Intent(this, CatchlogActivity.class);
            startActivity(i);
            finish();
        }
        searchSpeciesList = new ArrayList<>();
        searchSpeciesList.addAll(saltwaterSpeciesList);
        searchSpeciesList.addAll(freshwaterSpeciesList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        listviewSalt.setLayoutManager(gridLayoutManager);
        listviewSalt.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 2);
        gridLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        listviewFresh.setLayoutManager(gridLayoutManager2);
        listviewFresh.setHasFixedSize(true);

        CatchLogAllSpeciesSaltWaterAdapter catchLogAllSpeciesSaltWaterAdapter = new CatchLogAllSpeciesSaltWaterAdapter(this, saltwaterSpeciesList);
        listviewSalt.setAdapter(catchLogAllSpeciesSaltWaterAdapter);

        CatchLogAllSpeciesFreshWaterAdapter catchLogAllSpeciesFreshWaterAdapter = new CatchLogAllSpeciesFreshWaterAdapter(this, freshwaterSpeciesList);
        listviewFresh.setAdapter(catchLogAllSpeciesFreshWaterAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        listviewSearch.setLayoutManager(linearLayoutManager);
        listviewSearch.setHasFixedSize(true);

        txtSaltWater.setOnClickListener(this);
        txtFreshWater.setOnClickListener(this);
        backbtn.setOnClickListener(this);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                search(editable.toString());
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.backbtn:
                intent = getIntent();
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
            case R.id.txtSaltWater:
                lastSelectedTab = 1;
                listviewFresh.setVisibility(View.GONE);
                listviewSalt.setVisibility(View.VISIBLE);

                txtSaltWater.setBackgroundResource(R.drawable.button_redborder);
                txtFreshWater.setBackgroundResource(0);

                txtSaltWater.setTextColor(getResources().getColor(R.color.selectedtabtext));
                txtFreshWater.setTextColor(getResources().getColor(R.color.unselectedtabtext));
                break;
            case R.id.txtFreshWater:
                lastSelectedTab = 2;
                listviewSalt.setVisibility(View.GONE);
                listviewFresh.setVisibility(View.VISIBLE);

                txtSaltWater.setBackgroundResource(0);
                txtFreshWater.setBackgroundResource(R.drawable.button_redborder);

                txtSaltWater.setTextColor(getResources().getColor(R.color.unselectedtabtext));
                txtFreshWater.setTextColor(getResources().getColor(R.color.selectedtabtext));
                break;
            case R.id.llSpeciesRow:
                String id;
                String speciesName;
                intent = getIntent();
                if (listviewSalt.getVisibility() == View.VISIBLE) {
                    CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean = (CatchLogSpeciesCaughtBean) view.getTag();
                    id = String.valueOf(catchLogSpeciesCaughtBean.id);
                    speciesName = catchLogSpeciesCaughtBean.species;
                } else {
                    CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean = (CatchLogSpeciesCaughtBean) view.getTag();
                    id = String.valueOf(catchLogSpeciesCaughtBean.id);
                    speciesName = catchLogSpeciesCaughtBean.species;
                }
                intent.putExtra("species_id", id);
                intent.putExtra("species_name", speciesName);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.llSpeciesSearchRow:
                intent = getIntent();
                CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean = (CatchLogSpeciesCaughtBean) view.getTag();
                intent.putExtra("species_id", String.valueOf(catchLogSpeciesCaughtBean.id));
                intent.putExtra("species_name", catchLogSpeciesCaughtBean.species);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void search(String text) {
        if (text.length() <= 0) {
            if (lastSelectedTab == 1) {
                listviewSalt.setVisibility(View.VISIBLE);
            } else if (lastSelectedTab == 2) {
                listviewFresh.setVisibility(View.VISIBLE);
            }
        } else {
            listviewSalt.setVisibility(View.GONE);
            listviewFresh.setVisibility(View.GONE);
            listviewSearch.setVisibility(View.VISIBLE);
            List<CatchLogSpeciesCaughtBean> searchList = new ArrayList<>();
            if (text.length() > 1) {
                for (CatchLogSpeciesCaughtBean searchData : searchSpeciesList) {
                    if (searchData.species.toLowerCase().contains(text.toLowerCase())) {
                        searchList.add(searchData);
                    }
                }
            } else {
                if (text.length() == 0)
                    searchList.clear();
            }
            CatchLogAllSpeciesSearchAdapter catchLogAllSpeciesSearchAdapter = new CatchLogAllSpeciesSearchAdapter(this, searchList);
            listviewSearch.setAdapter(catchLogAllSpeciesSearchAdapter);
        }
    }

    public class ObjectComparator implements Comparator<CatchLogSpeciesCaughtBean> {
        @Override
        public int compare(CatchLogSpeciesCaughtBean o1, CatchLogSpeciesCaughtBean o2) {
            return o1.species.compareTo(o2.species);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.freeMemory();
    }
}