package com.mobiddiction.fishsmart.CatchLog;

import android.content.Context;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.CatchLogLocationBean;

import java.util.ArrayList;

public class ChooseLocationFromListAdapter extends RecyclerView.Adapter<ChooseLocationFromListAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(CatchLogLocationBean item);
    }

    private ArrayList<CatchLogLocationBean> catchLogLocationList;
    private Context context;
    private Typeface typeface;
    private OnItemClickListener listener;

    public ChooseLocationFromListAdapter(Context context, ArrayList<CatchLogLocationBean> catchLogLocationList,
                                         OnItemClickListener listener) {
        this.context = context;
        this.catchLogLocationList = catchLogLocationList;
        this.typeface = Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf");
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_choose_location_from_lists, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int pos) {
        CatchLogLocationBean catchLogLocationBean = catchLogLocationList.get(pos);
        // onClickListener
        viewHolder.bind(catchLogLocationBean, pos, listener);
    }

    @Override
    public int getItemCount() {
        try {
            return catchLogLocationList.size();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtLocationName;
        public TextView txtLatitude;
        public TextView txtLongitude;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtLocationName = itemView.findViewById(R.id.txtLocationName);
            txtLatitude = itemView.findViewById(R.id.txtLatitude);
            txtLongitude = itemView.findViewById(R.id.txtLongitude);
        }

        public void bind(final CatchLogLocationBean item, final int pos, final OnItemClickListener listener) {
            txtLocationName.setTypeface(typeface);
            txtLatitude.setTypeface(typeface);
            txtLongitude.setTypeface(typeface);

            txtLocationName.setText(item.name);
            txtLatitude.setText("Latitude: " + item.lat);
            txtLongitude.setText("Longitude: " + item.lon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}