package com.mobiddiction.fishsmart.Maps;

import java.util.ArrayList;

/**
 * Created by Archa on 17/05/2016.
 */
public class MapModel {

    public String id;
    public String title;
    public String desc;
    public String color;
    public String mapType;

   ArrayList<PinsModel> mapPins = new ArrayList<PinsModel>();

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }*/

    public ArrayList<PinsModel> getMapPins() {
        return mapPins;
    }

    public void setMapPins(ArrayList<PinsModel> mapPins) {
        this.mapPins = mapPins;
    }
}
