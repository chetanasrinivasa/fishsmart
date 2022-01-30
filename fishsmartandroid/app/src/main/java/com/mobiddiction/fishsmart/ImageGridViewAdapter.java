package com.mobiddiction.fishsmart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.mobiddiction.fishsmart.Gallery.GalleryDetailActivity;
import com.mobiddiction.fishsmart.dao.GalleryImage;
import com.mobiddiction.fishsmart.util.Utils;

import java.util.ArrayList;

public class ImageGridViewAdapter   extends RecyclerView.Adapter {

    private static final String TAG = ImageGridViewAdapter.class.getSimpleName();
    private boolean DEBUG = false;
    ArrayList<GalleryImage> Images;
    Context context;
    private  int imageWidth;
    public ImageGridViewAdapter(Context context, ArrayList personImages) {
        this.context = context;
        this.Images = personImages;
        imageWidth = Utils.getScreenWidth()/2;
    }
    @Override
    public ImageViewHoloder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ImageViewHoloder vh = new ImageViewHoloder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ImageViewHoloder imageViewHoloder = (ImageViewHoloder) holder;
        if(DEBUG)
            Log.d(TAG,"onBindViewHolder" + "position = " + position);

        Glide.with(context)
                .asBitmap()
                .format(DecodeFormat.PREFER_RGB_565)
                .encodeQuality(50)
                .override(imageWidth)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                .load(Images.get(position).getUrl())
                .signature(new ObjectKey(String.valueOf(Images.get(position).getUrl().hashCode())))
                //.skipMemoryCache(true)
                .into(new BitmapImageViewTarget(imageViewHoloder.image) {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(resource, transition);
                        int bitmapWidth = resource.getWidth();
                        int bitmapHeight = resource.getHeight();

                        if(DEBUG)
                            Log.d(TAG,"onResourceReady");

                        /*if(DEBUG)
                        Log.d(TAG,"resource height  = " + bitmapHeight + "resource width  = " + bitmapWidth + "b4 height  = " + imageViewHoloder.image.getLayoutParams().height + "B4 width  = " + imageViewHoloder.image.getLayoutParams().width);
*/
                        imageViewHoloder.image.getLayoutParams().width = imageWidth;
                        // Apply the new width for ImageView programmatically
                        imageViewHoloder.image.getLayoutParams().height = ((imageWidth)*bitmapHeight)/bitmapWidth;
                        imageViewHoloder.image.setImageBitmap(resource);
                        imageViewHoloder.tabsLayout.setPreventCornerOverlap(false); //it is very important
                        if(DEBUG)
                            Log.d(TAG,"position = " + position + " After height  = " + imageViewHoloder.image.getLayoutParams().height + " After width  = " + imageViewHoloder.image.getLayoutParams().width);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//                            imageViewHoloder.image.setBackground(round);
//                        else
//                            imageViewHoloder.image.setBackgroundDrawable(round);
                    }

                    @Override
                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);


                    }
                });
//                    image.setId(position);
        imageViewHoloder.image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, GalleryDetailActivity.class);
                detailIntent.putExtra("position", "" + position);
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Images.size() ;
    }
    public class ImageViewHoloder extends RecyclerView.ViewHolder {
        // init the item view's
        private final String TAG = ImageViewHoloder.class.getSimpleName();
        CardView tabsLayout;
        ImageView image;
        public ImageViewHoloder(View itemView) {
            super(itemView);
            if(DEBUG)
                Log.d(TAG,"Constructor");
            // get the reference of item view's
            tabsLayout = itemView.findViewById(R.id.tabsLayout);
            tabsLayout.setPreventCornerOverlap(false);
            tabsLayout.setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
            image = itemView.findViewById(R.id.image1);
        }

    }
}