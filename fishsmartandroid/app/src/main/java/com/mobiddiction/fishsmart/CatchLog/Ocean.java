package com.mobiddiction.fishsmart.CatchLog;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ocean {

    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("name")
    @Expose
    private String name;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}