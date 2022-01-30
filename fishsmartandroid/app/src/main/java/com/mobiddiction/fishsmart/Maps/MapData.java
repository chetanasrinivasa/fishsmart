package com.mobiddiction.fishsmart.Maps;

import java.util.List;

/**
 * Created by andrewindayang on 8/07/2016.
 */
public class MapData {

    public int id ;
    public String Title;
    public String description;
    public String color;
    public String mapType;
    public double topLeftLat;
    public double topLeftLon;
    public double bottomRightLat;
    public double bottomRightLon;
    public List<MapPinsLatLon> mapPinsList;
}
