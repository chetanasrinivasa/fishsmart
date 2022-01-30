package com.mobiddiction.fishsmart.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CatchLogBean implements Serializable {
    public String createdBy;
    public String createdDate;
    public String lastModifiedBy;
    public String lastModifiedDate;
    public int id;
    public int userId;
    public String name;
    public boolean hasLicense;
    public boolean fromBoat;
    public String imagePath;
    public String startDate;
    public String endDate;
    public String comment;
    /* 0 = Private
     * 1 = Fisher of the year
     * 2 = Public
     * 3 = Research
     */
    public int privacy;
    public boolean catchAndRelease;
    public String content;
    public String myJson;

    public CatchLogLocationBean location = new CatchLogLocationBean();
    public ArrayList<CatchLogSpeciesCaughtBean> speciesCaughtList = new ArrayList<>();
    public ArrayList<CatchLogImageBean> catchLogImageList = new ArrayList<>();
//    "type_id": 1000,
//    "featured": false,
//    "active": true
}