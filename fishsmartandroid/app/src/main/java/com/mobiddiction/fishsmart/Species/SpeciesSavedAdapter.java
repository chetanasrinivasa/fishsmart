package com.mobiddiction.fishsmart.Species;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mobiddiction.fishsmart.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class SpeciesSavedAdapter extends RecyclerView.Adapter<SpeciesSavedAdapter.CustomViewHolder> {
    SpeciesActivity.SavedSpeciesFragment mFragment;
    Context mContext;
    ArrayList<SpeciesModel> sources;
    Handler handler = new Handler();
    SpeciesDownloader speciesDownloader = SpeciesDownloader.getInstance();

    public SpeciesSavedAdapter(Context context, SpeciesActivity.SavedSpeciesFragment fragment, ArrayList<SpeciesModel> sources) {
        this.sources = sources;
        mFragment = fragment;
        this.mContext = context;
    }

    @Override
    // customViewHolder is a main view holder community_home
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SpeciesViewHolder(LayoutInflater.from(mContext).inflate(R.layout.species_listitem, parent, false));
    }

    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int position) {

        final SpeciesModel item = sources.get(position);

        final SpeciesSavedAdapter.SpeciesViewHolder f_holder = (SpeciesViewHolder) customViewHolder;

        Glide.with(mContext).load(item.getThumbnail()).apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).into(f_holder.icon);

        f_holder.exclaimtext.setText(item.getGroupName());
        f_holder.description.setText(item.getTitle());
        f_holder.sizelimittext.setText("Size Limit : " + item.getSizeLimit());
        f_holder.fishsubname.setText(item.getDescription());

        if (item.getBagLimitForCardView().equals(""))
        {
            f_holder.rulesapply.setText("Rules Apply");
            f_holder.exclamlayout.setVisibility(View.VISIBLE);
        }
        else
        {
            if(!item.getGrouped())
            {
                f_holder.exclamlayout.setVisibility(View.INVISIBLE);
            }
            else
            {
                f_holder.exclamlayout.setVisibility(View.INVISIBLE);
            }
            if(item.getBagLimitForCardView().equals("null"))
            {
                f_holder.rulesapply.setText("Bag Limit: N/A");
            }
            else
            {
                f_holder.rulesapply.setText("Bag Limit: "+ item.getBagLimitForCardView());
            }
        }

        if (item.getGroupName().equals(""))
        {
            f_holder.exclamlayout.setVisibility(View.GONE);
        }
        else
        {
            if(!item.getGrouped())
                f_holder.exclamlayout.setVisibility(View.GONE);
            else
                f_holder.exclamlayout.setVisibility(View.VISIBLE);
        }

        f_holder.mainlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, SpeciesDetailActivity.class);
                intent.putExtra("fishid", item.getId());
                intent.putExtra("isSaved", f_holder.savebtn.isSelected());

                ((Activity) mContext).startActivityForResult(intent, 10);
                ((Activity) mContext).overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);

            }
        });

        f_holder.savebtn.setSelected(true);
        f_holder.savebtn.setChecked(true);

        f_holder.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                f_holder.savebtn.setSelected(false);
                SpeciesDownloader.unsaveFish(item.getId());
                EventBus.getDefault().post(item);
                mFragment.loadSaved();
            }
        });

    }

    @Override
    public int getItemCount() {

        return sources.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public CustomViewHolder(View v) {
            super(v);
        }
    }

    public class SpeciesViewHolder extends SpeciesSavedAdapter.CustomViewHolder {

        private TextView exclaimtext, description, sizelimittext, fishsubname, rulesapply;
        private LinearLayout exclamlayout, mainlayout;
        private ImageView icon;
        private ToggleButton savebtn;

        public SpeciesViewHolder(View view) {
            super(view);

            this.savebtn = view.findViewById(R.id.savespeciesbtn);

            this.icon = view.findViewById(R.id.fishimg);

            this.exclaimtext = view.findViewById(R.id.exclaimtext);
            this.exclaimtext.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Regular.otf"));

            this.exclamlayout = view.findViewById(R.id.exclamlayout);
            this.mainlayout = view.findViewById(R.id.mainlayout);

            this.description = view.findViewById(R.id.fishname);
            this.description.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Bold.otf"));

            this.sizelimittext = view.findViewById(R.id.sizelimittext);
            this.sizelimittext.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Regular.otf"));

            this.fishsubname = view.findViewById(R.id.fishsubname);
            this.fishsubname.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Regular.otf"));

            this.rulesapply = view.findViewById(R.id.rulesapply);
            this.rulesapply.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Regular.otf"));


        }
    }
}
