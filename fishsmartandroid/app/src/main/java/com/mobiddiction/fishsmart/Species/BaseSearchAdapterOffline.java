package com.mobiddiction.fishsmart.Species;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.dao.AllSpecies;

import java.text.DecimalFormat;

/**
 * Created by AI on 11/09/2017.
 */

public abstract class BaseSearchAdapterOffline extends BaseAdapter {


    private static DecimalFormat df2 = new DecimalFormat("");

    Context context;
    LinearLayout mainLayout;
    ImageView imageView;
    TextView searchitem;

    protected final View getView(View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.searchsuggestionitem, parent, false);
        searchitem = convertView.findViewById(R.id.searchitem);
        mainLayout = convertView.findViewById(R.id.mainlayout);
        imageView = convertView.findViewById(R.id.imageview);
        return convertView;
    }

    protected final void populateSpecies(final Context context, final AllSpecies allSpecies) {
        searchitem.setText(allSpecies.getTitle());
        Glide.with(context)
                .load(allSpecies.getThumbnailImage())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder_grey).error(R.drawable.placeholder_grey))
                .into(new SimpleTarget<Drawable>() {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        final ShapeDrawable background = new ShapeDrawable();
                        background.getPaint().setColor(Color.parseColor("#ffffff"));
                        final Drawable[] layers = {background, resource};
                        imageView.setImageDrawable(new LayerDrawable(layers));
                    }
                });


        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SpeciesDetailActivity.class);
                intent.putExtra("fishid", allSpecies.getId());
                intent.putExtra("fromsearchoffline", true);
                ((Activity) context).startActivityForResult(intent, 10);
                ((Activity) context).overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);

            }
        });

    }

}