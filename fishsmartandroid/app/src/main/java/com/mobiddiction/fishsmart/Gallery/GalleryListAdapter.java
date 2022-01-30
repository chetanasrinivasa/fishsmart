package com.mobiddiction.fishsmart.Gallery;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.dao.GalleryImage;
import com.squareup.picasso.Picasso;

import java.util.List;


public class GalleryListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    List<GalleryImage> items;
    Context ctx;
    GalleryFragment fragment;
    boolean fromHome;

    public GalleryListAdapter(Context ctx, List<GalleryImage> items, GalleryFragment fragment, boolean fromHome) {
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
        this.ctx = ctx;
        this.fragment = fragment;
        this.fromHome = fromHome;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setCustomRow(final int position, final int cellId, final View convertView) {
        if (fromHome) {
            try {
                if (position <= items.size() - 1) {
                    try {
                        GalleryImage item = items.get(position);
                        ImageView image = convertView.findViewById(cellId);
                        if (image != null && item.getUrl() != null && !item.getUrl().equals("")) {
                            Picasso.get()
                                    .load(item.getUrl())
                                    .into(image);

                            image.setId(position);
                            image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent detailIntent = new Intent(ctx, GalleryDetailActivity.class);
                                    detailIntent.putExtra("position", "" + v.getId());
                                    ctx.startActivity(detailIntent);
                                }
                            });
                            image.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception ix) {
                        ImageView image = convertView.findViewById(cellId);
                        image.setVisibility(View.VISIBLE);
                        GalleryImage item = items.get(position);
                        if (item.getUrl() != null && !item.getUrl().equals("")) {
                            Picasso.get()
                                    .load(item.getUrl())
                                    .into(image);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                GalleryImage item = items.get(position);
                ImageView image = convertView.findViewById(cellId);

                if (image != null && item.getUrl() != null && !item.getUrl().equals("")) {
                    Picasso.get()
                            .load(item.getUrl())
                            .into(image);
                    /*Glide.with(ctx)
                            .load(item.getUrl())
                            .apply(new RequestOptions().fitCenter())
                            .into(image);*/
//                    image.setId(position);
                    image.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent detailIntent = new Intent(ctx, GalleryDetailActivity.class);
                            detailIntent.putExtra("position", "" + position);
                            ctx.startActivity(detailIntent);
                        }
                    });
                    image.setVisibility(View.VISIBLE);
                }
            } catch (Exception ix) {
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (fromHome) {
            try {
                convertView = inflater.inflate(R.layout.gallery_row_home, parent, false);
                setCustomRow((position), R.id.image1, convertView);
                setCustomRow((position * 7) + 1, R.id.image2, convertView);
                setCustomRow((position * 7) + 2, R.id.image3, convertView);
                setCustomRow((position * 7) + 3, R.id.image4, convertView);
                setCustomRow((position * 7) + 4, R.id.image5, convertView);
                setCustomRow((position * 7) + 5, R.id.image6, convertView);
                setCustomRow((position * 7) + 6, R.id.image7, convertView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                convertView = inflater.inflate(R.layout.gallery_row, parent, false);

//            AppCompatImageView imageview = convertView.findViewById(R.id.image1);
//            if (convertView.findViewById(R.id.image1) != null) {
//                Glide.with(ctx)
//                        .load(items.get(position).getUrl())
//                        .apply(new RequestOptions())
//                        .into(imageview);
////                    image.setId(position);
//                imageview.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        Intent detailIntent = new Intent(ctx, GalleryDetailActivity.class);
//                        detailIntent.putExtra("position", "" + position);
//                        ctx.startActivity(detailIntent);
//                    }
//                });
//                imageview.setVisibility(View.VISIBLE);
//            }
                setCustomRow((position), R.id.image1, convertView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        if (position == (int) Math.ceil((double)items.size()/7)) {
//            Log.d("REFRESH", "position == items.size()");
//            fragment.downloadNext();
//        }
        return convertView;
    }
}