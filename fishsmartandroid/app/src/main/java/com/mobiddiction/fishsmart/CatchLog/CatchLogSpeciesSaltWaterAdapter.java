package com.mobiddiction.fishsmart.CatchLog;

import android.content.Context;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.CatchLogSpeciesCaughtBean;

import java.util.ArrayList;

public class CatchLogSpeciesSaltWaterAdapter extends RecyclerView.Adapter<CatchLogSpeciesSaltWaterAdapter.MyViewHolder> {

    private ArrayList<CatchLogSpeciesCaughtBean> catchLogSpeciesSaltWaterList;
    private CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean;
    private Context context;
    private Typeface typeface;

    public CatchLogSpeciesSaltWaterAdapter(Context context, ArrayList<CatchLogSpeciesCaughtBean> catchLogSpeciesSaltWaterList) {
        this.context = context;
        this.catchLogSpeciesSaltWaterList = catchLogSpeciesSaltWaterList;
        this.typeface = Typeface.createFromAsset(context.getAssets(), "Bariol_Bold.otf");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_species, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int pos) {
        catchLogSpeciesCaughtBean = catchLogSpeciesSaltWaterList.get(pos);
        Glide.with(this.context)
                .load(catchLogSpeciesCaughtBean.image)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter())
                .into(viewHolder.imgPhoto);
        viewHolder.txtSpeciesName.setTypeface(typeface);
        viewHolder.txtSpeciesName.setText(catchLogSpeciesCaughtBean.species);
    }

    @Override
    public int getItemCount() {
        return catchLogSpeciesSaltWaterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llSpeciesRow;
        public ImageView imgPhoto;
        public TextView txtSpeciesName;

        public MyViewHolder(View itemView) {
            super(itemView);
            llSpeciesRow = itemView.findViewById(R.id.llSpeciesRow);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            txtSpeciesName = itemView.findViewById(R.id.txtSpeciesName);
        }
    }
}