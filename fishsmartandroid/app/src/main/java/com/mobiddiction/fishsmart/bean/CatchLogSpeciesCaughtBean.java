package com.mobiddiction.fishsmart.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class CatchLogSpeciesCaughtBean implements Serializable {
    public String createdBy;
    public String createdDate;
    public String lastModifiedBy;
    public String lastModifiedDate;
    public int id;
    public String species;
    public String speciesName;
    public String method;
    public String bait;
    public int kept;
    public int released;
    public String image;
    public ArrayList<CatchLogSpeciesCaughtMeasureBean> measurements = new ArrayList<>();

    // For local purpose only
    public HashMap<String, String> methodIds = new HashMap<>();
    public boolean isSpeciesName;
    public boolean isMethodSelcted;
    public boolean isBaitSelcted;
    public boolean isLureSelcted;

//    public boolean isKept;
//    public boolean isReleased;
//    "type_id": 1000,
//    "featured": false,
//    "active": true
}