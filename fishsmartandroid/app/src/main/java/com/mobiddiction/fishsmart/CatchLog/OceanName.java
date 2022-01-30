package com.mobiddiction.fishsmart.CatchLog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OceanName {

    @SerializedName("ocean")
    @Expose
    private Ocean ocean;

    public Ocean getOcean() {
        return ocean;
    }

    public void setOcean(Ocean ocean) {
        this.ocean = ocean;
    }

}