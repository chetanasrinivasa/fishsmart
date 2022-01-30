package com.mobiddiction.fishsmart.Species;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mobiddiction.fishsmart.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Archa on 5/01/2016.
 */

public class SpeciesFreshAdapter extends RecyclerView.Adapter<SpeciesFreshAdapter.CustomViewHolder> {
    Context mContext;
    ArrayList<SpeciesModel> sources;
    Handler handler = new Handler();
    SpeciesDownloader speciesDownloader = SpeciesDownloader.getInstance();

    public SpeciesFreshAdapter(Context context, ArrayList<SpeciesModel> sources) {
        this.sources = sources;
        this.mContext = context;
    }

    @Override
    // customViewHolder is a main view holder community_home
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpeciesViewHolder(LayoutInflater.from(mContext).inflate(R.layout.species_listitem, parent, false));
    }


    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int position) {
        final SpeciesModel item = sources.get(position);
        final SpeciesViewHolder f_holder = (SpeciesViewHolder) customViewHolder;

        Glide.with(mContext)
                .load(item.getThumbnail())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(f_holder.icon);

        f_holder.exclaimtext.setText(item.getGroupName());
        f_holder.description.setText(item.getTitle());
        f_holder.sizelimittext.setText("Size Limit: " + item.getSizeLimit());
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
                intent.putExtra("ISSALTFISH",false);
            intent.putExtra("isSaved", f_holder.savebtn.isSelected());

            ((Activity) mContext).startActivityForResult(intent, 10);
            ((Activity) mContext).overridePendingTransition(R.anim.slideright_to_left, R.anim.slidleft_to_right);
            }
        });

        f_holder.savebtn.setSelected(false);
        f_holder.savebtn.setChecked(false);

        f_holder.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog[] dialog = {null};

                if(f_holder.savebtn.isChecked()) {
                    f_holder.savebtn.setSelected(true);
                    SpeciesDownloader.saveFish(item.getId());

                    try {

                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable(){

                            public void run() {

                                LayoutInflater inflater = LayoutInflater.from(mContext);
                                final View dialoglayout = inflater.inflate(R.layout.save_species_popup, null);
                                YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                dialog[0] = new Dialog(mContext);

                                int divierId = dialog[0].getContext().getResources()
                                        .getIdentifier("android:id/titleDivider", null, null);
                                View divider = dialog[0].findViewById(divierId);
                                if(divider != null)
                                    divider.setVisibility(View.INVISIBLE);

                                dialog[0].setContentView(dialoglayout);
                                dialog[0].getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog[0].show();

                            }
                        });


                    }
                    catch (Exception ix) {

                    }

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            dialog[0].dismiss();

                        }
                    }, 1500);
                }
                else {
                    f_holder.savebtn.setSelected(false);
                    SpeciesDownloader.unsaveFish(item.getId());
                }

            }
        });

        if (SpeciesDownloader.isFishSaved(item.getId())) {
            f_holder.savebtn.setSelected(true);
            // f_holder.savebtn.setChecked(true);
        } else {
            f_holder.savebtn.setSelected(false);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
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

    public class SpeciesViewHolder extends CustomViewHolder {

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