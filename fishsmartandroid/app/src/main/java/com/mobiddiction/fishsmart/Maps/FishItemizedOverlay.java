package com.mobiddiction.fishsmart.Maps;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import org.osmdroid.api.IMapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

/**
 * Created by Archa on 21/04/2016.
 */
public class FishItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
    MapsActivity ctx;

    public FishItemizedOverlay(Drawable pDefaultMarker, ArrayList<OverlayItem> overlayItemList, MapsActivity context) {
        super(pDefaultMarker);

        this.overlayItemList = overlayItemList;
        this.ctx = context;
        // TODO Auto-generated constructor stub

    }

    public void addItem(GeoPoint p, String title, String snippet){
        OverlayItem newItem = new OverlayItem(title, snippet, p);
        overlayItemList.add(newItem);
        populate();

    }

    @Override
    public boolean onSnapToItem(int arg0, int arg1, Point arg2, IMapView arg3) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected OverlayItem createItem(int arg0) {
        // TODO Auto-generated method stub
        return overlayItemList.get(arg0);
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return overlayItemList.size();
    }

    @Override
    protected boolean onTap(final int index) {

        /*AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        dialog.setTitle(item.getTitle());
        dialog.setMessage(item.getSnippet());
        dialog.show();*/

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                OverlayItem item = overlayItemList.get(index);
                new MarkerTapAsync(ctx).execute("/api/map?Id=" + item.getTitle());
            }
        });

        return true;
    }
}