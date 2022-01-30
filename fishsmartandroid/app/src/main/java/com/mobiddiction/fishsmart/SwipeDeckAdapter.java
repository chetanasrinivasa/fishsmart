package com.mobiddiction.fishsmart;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mobiddiction.fishsmart.Gallery.GalleryDetailActivity;
import com.mobiddiction.fishsmart.Gallery.GalleryFragment;
import com.mobiddiction.fishsmart.Gallery.GalleryItem;

import java.util.ArrayList;

public class SwipeDeckAdapter extends BaseAdapter {

    private Context context;

    private static LayoutInflater inflater = null;
    ArrayList<GalleryItem> items;
    GalleryFragment fragment;
    boolean fromHome;

    public SwipeDeckAdapter( Context context, ArrayList<GalleryItem> items, GalleryFragment fragment, boolean fromHome) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.items = items;
        this.fragment = fragment;
        this.fromHome = fromHome;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setCustomRow(final int position, final int cellId, final View convertView) {
        if (fromHome) {
            if(position <= items.size() - 1) {
                try {
                    GalleryItem item = items.get(position);
                    ImageView image = convertView.findViewById(cellId);

                    if (image != null) {

                        Glide.with(context)
                                .load(item.getMedia())
                                .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                                .into(image);

                        image.setId(position);

                        image.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                Intent detailIntent = new Intent(context, GalleryDetailActivity.class);
                                detailIntent.putExtra("position", "" + v.getId());
                                detailIntent.putExtra("items", items);
                                context.startActivity(detailIntent);

                            }
                        });
                        image.setVisibility(View.VISIBLE);
                    }
                    Log.d("URLLLLLLLL", "item.getMedia() : " + item.getMedia());
                } catch (Exception ix) {
                    ImageView image = convertView.findViewById(cellId);
                    image.setVisibility(View.VISIBLE);
                    GalleryItem item = items.get(position);
                    Glide.with(context)
                            .load(item.getMedia())
                            .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(image);
                }
            }
        } else {
            try {
                GalleryItem item = items.get(position);
                ImageView image = convertView.findViewById(cellId);

                if (image != null) {

                    Glide.with(context)
                            .load(item.getMedia())
                            .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(image);

                    image.setId(position);

                    image.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Intent detailIntent = new Intent(context, GalleryDetailActivity.class);
                            detailIntent.putExtra("position", "" + v.getId());
                            detailIntent.putExtra("items", items);
                            context.startActivity(detailIntent);

                        }
                    });
                    image.setVisibility(View.VISIBLE);
                }
                Log.d("URLLLLLLLL", "item.getMedia() : " + item.getMedia());
            } catch (Exception ix) {
            }
        }
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
//        if(v == null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            // normally use a viewholder
//            v = inflater.inflate(R.layout.gallery_row_home, parent, false);
//        }


        convertView = inflater.inflate(R.layout.gallery_row, parent, false);
        setCustomRow((position), R.id.image1, convertView);

        return v;
    }
}