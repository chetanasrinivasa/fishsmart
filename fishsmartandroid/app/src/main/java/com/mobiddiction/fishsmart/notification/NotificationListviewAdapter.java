package com.mobiddiction.fishsmart.notification;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.dao.Notification;

import java.util.List;

public class NotificationListviewAdapter extends NotificationBaseAdapter {

    List<Notification> notification;
    NotificationActivity notificationActivity;
    public NotificationListviewAdapter(Context context, List<Notification> notification, NotificationActivity notificationActivity){
        this.context = context;
        reloadData();
        this.notification = notification;
        this.notificationActivity = notificationActivity;
    }

    @Override
    protected void reloadData() {
        ModelManager manager = ModelManager.getInstance();
        this.notification = manager.getNotification();
    }

    @Override
    public int getCount() {
        return notification.size();
    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(convertView, parent);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        populateDashboard(parent.getContext(), viewHolder,notification.get(position));

        return view;
    }
}