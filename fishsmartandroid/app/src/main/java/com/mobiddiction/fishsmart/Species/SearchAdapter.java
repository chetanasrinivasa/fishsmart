package com.mobiddiction.fishsmart.Species;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobiddiction.fishsmart.R;

import java.util.ArrayList;

public class SearchAdapter extends ArrayAdapter<SearchModel> {

    Context mContext;
    int layoutResourceId;
    ArrayList<SearchModel> data = new ArrayList<SearchModel>();

    public SearchAdapter(Context mContext, int layoutResourceId, ArrayList<SearchModel> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
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
        final SearchModel objectItem = data.get(position);

        TextView searchtext = convertView.findViewById(R.id.searchitem);

        ImageView imageView = convertView.findViewById(R.id.imageview);
        searchtext.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Regular.otf"));
        searchtext.setText(objectItem.getTitle());
        LinearLayout mainlayout = convertView.findViewById(R.id.mainlayout);

        mainlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, SpeciesDetailActivity.class);
                intent.putExtra("fishid", objectItem.getId());

                ((Activity) mContext).startActivityForResult(intent, 10);
                ((Activity) mContext).overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);

            }
        });

        return convertView;

    }
}