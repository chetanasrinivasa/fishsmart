package com.mobiddiction.fishsmart.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CatchLogImageBean implements Serializable {
    public String createdBy;
    public String createdDate;
    public String lastModifiedBy;
    public String lastModifiedDate;
    public int id;
    public String name;
    public String state;
    public String url;
    public String photoTitle;
    public String photoComment;
    public String favourite;
    public String deleted;
    public String clientId;

    public String userId;
    public String newsId;
    public String eventId;
    public String driverId;
    public String galleryId;
    public String jobId;
    public String catchId;
    public String note;
    public String expiry;
    public String profile;

    public ArrayList<CatchLogTagsBean> catchLogTagsList = new ArrayList<>();
}