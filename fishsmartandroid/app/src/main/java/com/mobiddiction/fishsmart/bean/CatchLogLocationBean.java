package com.mobiddiction.fishsmart.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CatchLogLocationBean implements Serializable {
    public String createdBy;
    public String createdDate;
    public String lastModifiedBy;
    public String lastModifiedDate;
    public int id;
    public String version;
    public String suiteNumber;
    public String streetName;
    public String streetNumber;
    public String name;
    public String suburb;
    public String postcode;
    public double lat;
    public double lon;
    public String state;
    public String country;
    public ArrayList<CatchLogTagsBean> tags = new ArrayList<>();
    public boolean deleted;
    public String imageUrl;
}