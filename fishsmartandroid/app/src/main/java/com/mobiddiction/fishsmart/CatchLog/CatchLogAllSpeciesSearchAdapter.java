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

import java.util.List;

public class CatchLogAllSpeciesSearchAdapter extends RecyclerView.Adapter<CatchLogAllSpeciesSearchAdapter.MyViewHolder> {

    private List<CatchLogSpeciesCaughtBean> catchLogSpeciesFreshWaterList;
    private CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean;
    private Context context;
    private Typeface typeface;

    public CatchLogAllSpeciesSearchAdapter(Context context, List<CatchLogSpeciesCaughtBean> catchLogSpeciesFreshWaterList) {
        this.context = context;
        this.catchLogSpeciesFreshWaterList = catchLogSpeciesFreshWaterList;
        this.typeface = Typeface.createFromAsset(context.getAssets(), "Bariol_Bold.otf");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_catch_species_search, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int pos) {
        catchLogSpeciesCaughtBean = catchLogSpeciesFreshWaterList.get(pos);

        try {
            if (catchLogSpeciesCaughtBean.image != null && !catchLogSpeciesCaughtBean.image.equals("")) {
                Picasso.get()
                        .load(catchLogSpeciesCaughtBean.image)
                        .placeholder(R.drawable.placeholder_grey)
                        .into(viewHolder.imgSpeciesPhoto);
            }else{
                Picasso.get()
                        .load(R.drawable.placeholder_grey)
                        .into(viewHolder.imgSpeciesPhoto);
            }

        /*Glide.with(this.context)
                .load(catchLogSpeciesCaughtBean.image)
                .apply(new RequestOptions().override(50,50)).placeholder(R.drawable.placeholder_grey)
                .into(new SimpleTarget<Drawable>() {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        final ShapeDrawable background = new ShapeDrawable();
                        background.getPaint().setColor(Color.parseColor("#ffffff"));
                        final Drawable[] layers = {background, resource};
                        viewHolder.imgSpeciesPhoto.setImageDrawable(new LayerDrawable(layers));
                    }
                });*/

            viewHolder.txtCatcheSpeciesName.setTypeface(typeface);
            viewHolder.txtCatcheSpeciesName.setText(catchLogSpeciesCaughtBean.species);
            viewHolder.llSpeciesSearchRow.setTag(catchLogSpeciesCaughtBean);
            viewHolder.llSpeciesSearchRow.setOnClickListener((View.OnClickListener) this.context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return catchLogSpeciesFreshWaterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llSpeciesSearchRow;
        public ImageView imgSpeciesPhoto;
        public TextView txtCatcheSpeciesName;

        public MyViewHolder(View itemView) {
            super(itemView);
            llSpeciesSearchRow = itemView.findViewById(R.id.llSpeciesSearchRow);
            imgSpeciesPhoto = itemView.findViewById(R.id.imgSpeciesPhoto);
            txtCatcheSpeciesName = itemView.findViewById(R.id.txtCatcheSpeciesName);
        }
    }
}