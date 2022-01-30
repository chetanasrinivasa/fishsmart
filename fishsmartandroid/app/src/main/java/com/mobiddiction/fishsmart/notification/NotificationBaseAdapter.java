package com.mobiddiction.fishsmart.notification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.dao.Notification;
import com.mobiddiction.fishsmart.dao.NotificationImage;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class NotificationBaseAdapter extends BaseAdapter {


    private static DecimalFormat df2 = new DecimalFormat("");

    Context context;

    protected final View getView(View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.notification_listview_item, parent, false);
        ViewHolder holder = loadHolder(new ViewHolder(), convertView);
        convertView.setTag(holder);
        return convertView;
    }

    protected final void populateDashboard(final Context context, final ViewHolder holder, final Notification notification) {


        if (notification.getCallToAction() != null && !notification.getCallToAction().equalsIgnoreCase("") && URLUtil.isValidUrl(notification.getCallToAction())) {
            holder.moreInfo.setVisibility(View.VISIBLE);
            holder.moreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notification.getCallToAction()));
                    context.startActivity(browserIntent);
                }
            });
        } else {
            holder.moreInfo.setVisibility(View.GONE);
        }

        if (notification.getNotifTitle() != null) {
            holder.title.setText(notification.getNotifTitle());
        } else {
            holder.title.setText("");
        }

        if (notification.getNotifText() != null) {
            holder.caption.setText(notification.getNotifText());
        } else {
            holder.caption.setText("");
        }

        if (notification.getCreatedDate() != null) {
            holder.date.setText(AppConstant.Epoch2DateStringtime(notification.getCreatedDate()));
        }

        final NotificationImage notificationImage = ModelManager.getInstance().getNotificationImageByNotificationId(notification.getId());


        if (URLUtil.isValidUrl(notification.getNotifSound())) {
            holder.play_button.setVisibility(View.VISIBLE);
            String videoId = notification.getNotifSound().replace("https://www.youtube.com/watch?v=", "");
            String thumbnail = "http://img.youtube.com/vi/" + videoId + "/0.jpg";
            if (!thumbnail.equals("")) {
                Picasso.get()
                        .load(thumbnail)
                        .error(R.mipmap.ic_launcher)
                        .into(holder.imageListItem);
            }


        } else if (notificationImage != null && !notificationImage.getUrl().equalsIgnoreCase("")) {
            Log.d("NotificationAdapter", "notificationImage URL : " + notificationImage.getUrl());
            if (notificationImage.getUrl() != null && !notificationImage.getUrl().equals("")) {
                Picasso.get()
                        .load(notificationImage.getUrl())
                        .error(R.mipmap.ic_launcher)
                        .into(holder.imageListItem);
            }
            holder.play_button.setVisibility(View.GONE);
        } else {
            holder.imageListItem.setVisibility(View.GONE);
            holder.play_button.setVisibility(View.GONE);
        }

        holder.cardviewRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.play_button.getVisibility() == View.VISIBLE) {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notification.getNotifSound()));
                    context.startActivity(browserIntent);
                }
            }
        });
        holder.title.setTypeface(Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf"));
        holder.caption.setTypeface(Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf"));
        holder.date.setTypeface(Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf"));
    }

    protected abstract void reloadData();

    private ViewHolder loadHolder(ViewHolder holder, View convertView) {
        ButterKnife.bind(holder, convertView);
        return holder;
    }

    protected static class ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.caption)
        TextView caption;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.imageListItem)
        RoundRectCornerImageView imageListItem;

        @BindView(R.id.cardviewRelative)
        RelativeLayout cardviewRelative;

        @BindView(R.id.play_button)
        ImageView play_button;

        @BindView(R.id.moreInfo)
        Button moreInfo;
    }
}