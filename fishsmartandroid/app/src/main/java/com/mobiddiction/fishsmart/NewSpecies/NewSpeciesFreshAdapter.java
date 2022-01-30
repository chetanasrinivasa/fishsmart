package com.mobiddiction.fishsmart.NewSpecies;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.Species.SpeciesDetailActivity;
import com.mobiddiction.fishsmart.Species.SpeciesDownloader;
import com.mobiddiction.fishsmart.dao.FreshWaterNewData;
import com.mobiddiction.fishsmart.dao.FreshWaterfishGroup;
import com.mobiddiction.fishsmart.util.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Archa on 5/01/2016.
 */

public class NewSpeciesFreshAdapter extends RecyclerView.Adapter<NewSpeciesFreshAdapter.CustomViewHolder> {
    Context mContext;
    List<FreshWaterNewData> sources;
    Handler handler = new Handler();
    SpeciesDownloader speciesDownloader = SpeciesDownloader.getInstance();

    public NewSpeciesFreshAdapter(Context context, List<FreshWaterNewData> sources) {
        this.sources = sources;
        this.mContext = context;
    }

    @Override
    @NonNull
    // customViewHolder is a main view holder community_home
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpeciesViewHolder(LayoutInflater.from(mContext).inflate(R.layout.species_listitem, parent, false));
    }


    public void onBindViewHolder(@NonNull final CustomViewHolder customViewHolder, final int position) {
        try {
            final FreshWaterNewData item = sources.get(position);
            final FreshWaterfishGroup freshwaterNew = ModelManager.getInstance().getFreshWaterGroupFishByID(item.getId());
            final SpeciesViewHolder f_holder = (SpeciesViewHolder) customViewHolder;

            if (item.getThumbnailImage() != null && !item.getThumbnailImage().equals("")) {
                Picasso.get()
                        .load(item.getThumbnailImage())
                        .placeholder(R.drawable.fishsmart_logo)
                        .into(f_holder.icon);
            }else{
                Picasso.get()
                        .load(R.drawable.fishsmart_logo)
                        .into(f_holder.icon);
            }

            /*Glide.with(mContext)
                    .load(item.getThumbnailImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().placeholder(R.drawable.fishsmart_logo).error(R.drawable.fishsmart_logo))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            final ShapeDrawable background = new ShapeDrawable();
                            background.getPaint().setColor(Color.parseColor("#ffffff"));
                            final Drawable[] layers = {background, resource};
                            f_holder.icon.setImageDrawable(new LayerDrawable(layers));
                        }
                    });*/

            if (freshwaterNew.getGroupName() == null || freshwaterNew.getGroupName().equals("")) {
                f_holder.exclamlayout.setVisibility(View.GONE);
            } else {
                if (item.getGrouped() != null && !item.getGrouped())
                    f_holder.exclamlayout.setVisibility(View.GONE);
                else
                    f_holder.exclamlayout.setVisibility(View.VISIBLE);
            }

            f_holder.description.setText(item.getTitle());
            f_holder.sizelimittext.setText("Size Limit: " + item.getSizeLimit());
            if (item.getSubHeader().equals("null")) {
                f_holder.fishsubname.setText("");
            } else {
                f_holder.fishsubname.setText(item.getSubHeader());
            }

            if (item.getBagLimit() == null || item.getBagLimit().equals("")) {
                f_holder.exclamlayout.setVisibility(View.VISIBLE);
            } else {
                if (item.getGrouped() != null && !item.getGrouped()) {
                    f_holder.exclamlayout.setVisibility(View.INVISIBLE);
                } else {
                    f_holder.exclamlayout.setVisibility(View.INVISIBLE);
                }

                if (item.getGrouped()) {
                    f_holder.rulesapply.setText("Rules Apply");
                } else {
                    if (item.getBagLimit() == null || item.getBagLimit().equals("null")) {
                        f_holder.rulesapply.setText("Bag Limit: -");
                    } else {
                        f_holder.rulesapply.setText("Bag Limit: " + item.getBagLimit());
                    }
                }
            }

            if (item.getTitle() == null || item.getTitle().equals("")) {
                f_holder.exclamlayout.setVisibility(View.GONE);
            } else {
                if (item.getGrouped() != null && !item.getGrouped())
                    f_holder.exclamlayout.setVisibility(View.GONE);
                else
                    f_holder.exclamlayout.setVisibility(View.VISIBLE);
            }

            if(freshwaterNew.getGroupName()!=null && !TextUtils.isEmpty(freshwaterNew.getGroupName().trim())){
                f_holder.exclamlayout.setVisibility(View.VISIBLE);
                f_holder.exclaimtext.setText(Utils.toTitleCase(freshwaterNew.getGroupName().toLowerCase()));
            }
            else{
                f_holder.exclamlayout.setVisibility(View.GONE);
            }

            f_holder.mainlayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, SpeciesDetailActivity.class);
                    intent.putExtra("fishid", item.getId());
                    intent.putExtra("ISSALTFISH", false);
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

                    if (f_holder.savebtn.isChecked()) {
                        f_holder.savebtn.setSelected(true);
                        SpeciesDownloader.saveFish(item.getId());

                        try {

                            Handler h = new Handler(Looper.getMainLooper());
                            h.post(new Runnable() {

                                public void run() {

                                    LayoutInflater inflater = LayoutInflater.from(mContext);
                                    final View dialoglayout = inflater.inflate(R.layout.save_species_popup, null);
                                    YoYo.with(Techniques.FadeIn).duration(500).playOn(dialoglayout);

                                    dialog[0] = new Dialog(mContext);

                                    int divierId = dialog[0].getContext().getResources()
                                            .getIdentifier("android:id/titleDivider", null, null);
                                    View divider = dialog[0].findViewById(divierId);
                                    if (divider != null)
                                        divider.setVisibility(View.INVISIBLE);

                                    dialog[0].setContentView(dialoglayout);
                                    dialog[0].getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog[0].show();

                                }
                            });


                        } catch (Exception ix) {

                        }

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                dialog[0].dismiss();

                            }
                        }, 1500);
                    } else {
                        f_holder.savebtn.setSelected(false);
                        SpeciesDownloader.unsaveFish(item.getId());
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
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