package com.mobiddiction.fishsmart.Search;

import android.os.Bundle;

import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.R;

/**
 * Created by Archa on 10/05/2016.
 */
public class SearchActivity extends BaseTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState/*, R.layout.activity_search, false*/);
        setContentView(R.layout.activity_search);

      //  BaseTabActivity.HomeTextChanger("SPECIES");

    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
     //   appData.putBoolean(SearchableActivity.JARGON, true);
        startSearch(null, false, appData, false);
        return true;
    }
}
