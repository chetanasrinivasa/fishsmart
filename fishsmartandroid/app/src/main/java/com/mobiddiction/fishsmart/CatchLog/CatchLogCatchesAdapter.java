package com.mobiddiction.fishsmart.CatchLog;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
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
import com.mobiddiction.fishsmart.bean.CatchLogBean;
import com.mobiddiction.fishsmart.bean.CatchLogLocationBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CatchLogCatchesAdapter extends RecyclerView.Adapter<CatchLogCatchesAdapter.MyViewHolder> {

    private ArrayList<CatchLogLocationBean> catchLogCatchesList;
    private ArrayList<CatchLogBean> catchlogBeanList;
    private CatchLogLocationBean catchLogLocationBean;
    private CatchLogBean catchLogBean;
    private Context context;
    private Typeface typeface;
    private File file;
    private Uri imageUri;

    public CatchLogCatchesAdapter(Context context, ArrayList<CatchLogLocationBean> catchLogCatchesList, ArrayList<CatchLogBean> catchlogBeanList) {
        this.context = context;
        this.catchLogCatchesList = catchLogCatchesList;
        this.catchlogBeanList = catchlogBeanList;
        this.typeface = Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_catches, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int pos) {
        catchLogLocationBean = catchLogCatchesList.get(pos);
        catchLogBean = catchlogBeanList.get(pos);

        viewHolder.txtCatchesLocationName.setTypeface(typeface);
        viewHolder.txtCatchesLocationName.setText(catchLogLocationBean.name);

        viewHolder.txtCatchesLocationDateTime.setTypeface(typeface);
        // viewHolder.txtCatchesLocationDateTime.setText("" + getDate(Long.parseLong(catchLogLocationBean.createdDate), "dd/MM/yyyy hh:mm a"));
        viewHolder.txtCatchesLocationDateTime.setText("" + getDate(Long.parseLong(catchLogBean.startDate), "dd/MM/yyyy hh:mm a"));

        if (catchLogLocationBean.imageUrl == null || catchLogLocationBean.imageUrl.equals("")) {
            viewHolder.imgPhoto.setPadding(75, 75, 75,75);
            Glide.with(this.context)
                    .load(R.drawable.placeholder_grey)
                    .apply(new RequestOptions().placeholder(R.drawable.placeholder_grey).error(R.drawable.placeholder_grey))
                    .into(viewHolder.imgPhoto);
        } else {
            if (catchLogLocationBean.imageUrl.contains("/storage") ||
                    catchLogLocationBean.imageUrl.contains("emulated")) {
                file = new File(catchLogLocationBean.imageUrl);
                imageUri = Uri.fromFile(file);
                Glide.with(this.context)
                        .load(imageUri)
//                        .placeholder(R.drawable.placeholder_grey)
//                        .error(R.drawable.placeholder_grey)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .centerCrop()
                        .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(viewHolder.imgPhoto);
            } else {
                Glide.with(this.context)
                        .load(catchLogLocationBean.imageUrl)
//                        .placeholder(R.drawable.placeholder_grey)
//                        .error(R.drawable.placeholder_grey)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .centerCrop()
                        .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(viewHolder.imgPhoto);
            }
        }
        viewHolder.llRow.setTag(pos);
        viewHolder.llRow.setOnClickListener((View.OnClickListener) context);
    }

    @Override
    public int getItemCount() {
        return catchLogCatchesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llRow;
        public ImageView imgPhoto;
        public TextView txtCatchesLocationName;
        public TextView txtCatchesLocationDateTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            llRow = itemView.findViewById(R.id.llRow);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            txtCatchesLocationName = itemView.findViewById(R.id.txtCatchesLocationName);
            txtCatchesLocationDateTime = itemView.findViewById(R.id.txtCatchesLocationDateTime);
        }
    }

    public String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
