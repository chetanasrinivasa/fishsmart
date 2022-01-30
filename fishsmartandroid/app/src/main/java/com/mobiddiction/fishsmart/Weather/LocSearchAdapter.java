package com.mobiddiction.fishsmart.Weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobiddiction.fishsmart.R;

import java.util.ArrayList;

public class LocSearchAdapter extends ArrayAdapter<LocSearchModel> {

    Context mContext;
    int layoutResourceId;
    ArrayList<LocSearchModel> data = new ArrayList<LocSearchModel>();

    public LocSearchAdapter(Context mContext, int layoutResourceId, ArrayList<LocSearchModel> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*
         * The convertView argument is essentially a "ScrapView" as described is Lucas post
         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
         * It will have a non-null value when ListView is asking you recycle the row layout.
         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
         */
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // object item based on the position
        final LocSearchModel objectItem = data.get(position);

        TextView searchtext = convertView.findViewById(R.id.searchitem);
        searchtext.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Regular.otf"));

        searchtext.setText(objectItem.getName());

        LinearLayout mainlayout = convertView.findViewById(R.id.mainlayout);

        mainlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, WeatherActivity.class);
                intent.putExtra("lat", objectItem.getLat());
                intent.putExtra("lon", objectItem.getLon());
                mContext.startActivity(intent);
            //    ((Activity) mContext).overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);
                ((Activity) mContext).finish();

            }
        });

        return convertView;

    }
}