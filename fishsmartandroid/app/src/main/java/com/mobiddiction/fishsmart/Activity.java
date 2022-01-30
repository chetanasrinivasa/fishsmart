package com.mobiddiction.fishsmart;

/**
 * Created by andrewi on 9/04/2018
 */

import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;


public abstract class Activity extends AppCompatActivity
{

    // Overridden for action bar style pre lollipop
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    // Overridden for Material-like transitions
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    // Overridden for Material-like transitions
    @Override
    public void startActivity(Intent intent)
    {
        super.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            if (upIntent == null){
                return super.onOptionsItemSelected(item);
            }
            upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(upIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
