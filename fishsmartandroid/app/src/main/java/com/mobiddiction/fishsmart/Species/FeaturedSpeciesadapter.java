package com.mobiddiction.fishsmart.Species;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.dao.AllSpecies;
import com.mobiddiction.fishsmart.dao.NEWSpeciesData;
import com.mobiddiction.fishsmart.util.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class FeaturedSpeciesadapter extends RecyclerView.Adapter<FeaturedSpeciesadapter.CustomViewHolder> {
    Context mContext;
    List<NEWSpeciesData> sources;
    Handler handler = new Handler();
    SpeciesDownloader speciesDownloader = SpeciesDownloader.getInstance();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(AllSpecies item);
    }

    public FeaturedSpeciesadapter(Context context, List<NEWSpeciesData> sources, OnItemClickListener listener) {
        this.sources = sources;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    // customViewHolder is a main view holder community_home
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpeciesViewHolder(LayoutInflater.from(mContext).inflate(R.layout.homespecies_item, parent, false));
    }


    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int position) {
//        final SpeciesModel item = sources.get(position);
        final SpeciesViewHolder f_holder = (SpeciesViewHolder) customViewHolder;
        final AllSpecies item ;
        try {
           /* List<NEWSpeciesData> newSpeciesDataListFeaturedTrue = ModelManager.getInstance().getNewSpeciesDataFeatured();
            if(newSpeciesDataListFeaturedTrue != null) {*/
            if(sources != null) {
                item = ModelManager.getInstance().getSpeciesByid(sources.get(position).getId() + "");
            }else
                return;
//            newSpeciesDataListFeaturedTrue.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if(item == null) {
            return;
        }

        if (item.getThumbnailImage() != null && !item.getThumbnailImage().equals("")) {
            Picasso.get()
                    .load(item.getThumbnailImage())
                    .placeholder(R.drawable.placeholder_grey)
                    .into(f_holder.icon);
        }else {
            Picasso.get()
                    .load(R.drawable.placeholder_grey)
                    .into(f_holder.icon);
        }

        /*Glide.with(mContext)
                .load(item.getThumbnailImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().placeholder(R.drawable.placeholder_grey).error(R.drawable.placeholder_grey))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        final ShapeDrawable background = new ShapeDrawable();
                        background.getPaint().setColor(Color.parseColor("#ffffff"));
                        final Drawable[] layers = {background, resource};
                        f_holder.icon.setImageDrawable(new LayerDrawable(layers));
                    }
                });*/

        if(item.getGrouped()){
            f_holder.exclaimtext.setText(Utils.toTitleCase(item.getGroupType().toLowerCase()));
            f_holder.rulesapply.setText("Rules Apply");
        } else{
            if (item.getBagLimit().equals("null")) {
                f_holder.rulesapply.setText("Bag Limit: N/A");
            } else {
                f_holder.rulesapply.setText("Bag Limit: " + item.getBagLimit());
            }
        }

        f_holder.description.setText(item.getTitle());
        f_holder.sizelimittext.setText("Size Limit: " + item.getSizeLimit());
        if (item.getSubHeader().equals("null")) {
            f_holder.fishsubname.setText("");
        } else {
            f_holder.fishsubname.setText(item.getSubHeader());
        }

        if (item.getBagLimit().equals("")) {
            f_holder.exclamlayout.setVisibility(View.VISIBLE);
        } else {
            if (item.getGrouped() != null && !item.getGrouped()) {

                f_holder.exclamlayout.setVisibility(View.INVISIBLE);
            } else {
                f_holder.exclamlayout.setVisibility(View.INVISIBLE);
            }
        }

        if (item.getTitle().equals("")) {
            f_holder.exclamlayout.setVisibility(View.INVISIBLE);
        } else {
            if (item.getGrouped() != null && !item.getGrouped())
                f_holder.exclamlayout.setVisibility(View.INVISIBLE);
            else
                f_holder.exclamlayout.setVisibility(View.VISIBLE);
        }

        f_holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getItemCount() {
        if (sources != null) {
            return sources.size();
        } else{
            return 0;
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class SpeciesViewHolder extends CustomViewHolder {

        private TextView exclaimtext, description, sizelimittext, fishsubname, rulesapply;
        private LinearLayout exclamlayout, mainlayout, linklayout;
        private ImageView icon;
        public LinearLayout llRow;

        public SpeciesViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.fishimg);
            llRow = itemView.findViewById(R.id.llRow);
            exclaimtext = itemView.findViewById(R.id.exclaimtext);
            exclaimtext.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Regular.otf"));

            exclamlayout = itemView.findViewById(R.id.exclamlayout);
            mainlayout = itemView.findViewById(R.id.mainlayout);
            linklayout = itemView.findViewById(R.id.linklayout);

            description = itemView.findViewById(R.id.fishname);
            description.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Bold.otf"));

            sizelimittext = itemView.findViewById(R.id.sizelimittext);
            sizelimittext.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Regular.otf"));

            fishsubname = itemView.findViewById(R.id.fishsubname);
            fishsubname.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Regular.otf"));

            rulesapply = itemView.findViewById(R.id.rulesapply);
            rulesapply.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Bariol_Regular.otf"));

        }
    }
}