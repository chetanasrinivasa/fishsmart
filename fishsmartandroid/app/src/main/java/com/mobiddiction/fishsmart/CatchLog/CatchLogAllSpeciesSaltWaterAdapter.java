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

import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.CatchLogSpeciesCaughtBean;
import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.List;

public class CatchLogAllSpeciesSaltWaterAdapter extends RecyclerView.Adapter<CatchLogAllSpeciesSaltWaterAdapter.MyViewHolder> {

    private List<CatchLogSpeciesCaughtBean> catchLogSpeciesSaltWaterList;
    private CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean;
    private Context context;
    private Typeface typeface;

    public CatchLogAllSpeciesSaltWaterAdapter(Context context, List<CatchLogSpeciesCaughtBean> catchLogSpeciesSaltWaterList) {
        this.context = context;
        this.catchLogSpeciesSaltWaterList = catchLogSpeciesSaltWaterList;
        this.typeface = Typeface.createFromAsset(context.getAssets(), "Bariol_Bold.otf");

        Iterator<CatchLogSpeciesCaughtBean> it = this.catchLogSpeciesSaltWaterList.iterator();
        CatchLogSpeciesCaughtBean mesaureCaughtBean;
        // Iterate in reverse.
        while(it.hasNext()) {
            mesaureCaughtBean = it.next();
            if (mesaureCaughtBean.image == null || mesaureCaughtBean.image.equals("")) {
                it.remove();
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_species, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int pos) {
        catchLogSpeciesCaughtBean = catchLogSpeciesSaltWaterList.get(pos);
        System.out.println("======== IMAGE PATH ======" + catchLogSpeciesCaughtBean.image);

        if (catchLogSpeciesCaughtBean.image != null && !catchLogSpeciesCaughtBean.image.equals("")) {
            Picasso.get()
                    .load(catchLogSpeciesCaughtBean.image)
                    .placeholder(R.drawable.placeholder_grey)
                    .into(viewHolder.imgPhoto);
        }else{
            Picasso.get()
                    .load(R.drawable.placeholder_grey)
                    .into(viewHolder.imgPhoto);
        }


        /*Glide.with(this.context)
                .load(catchLogSpeciesCaughtBean.image)
                .apply(new RequestOptions().override(225, 225).placeholder(R.drawable.placeholder_grey))
                .into(new SimpleTarget<Drawable>() {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        final ShapeDrawable background = new ShapeDrawable();
                        background.getPaint().setColor(Color.parseColor("#ffffff"));
                        final Drawable[] layers = {background, resource};
                        viewHolder.imgPhoto.setImageDrawable(new LayerDrawable(layers));
                    }
                });*/


        viewHolder.txtSpeciesName.setTypeface(typeface);
        viewHolder.txtSpeciesName.setText(catchLogSpeciesCaughtBean.species);
        viewHolder.llSpeciesRow.setTag(catchLogSpeciesCaughtBean);
        viewHolder.llSpeciesRow.setOnClickListener((View.OnClickListener) this.context);
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