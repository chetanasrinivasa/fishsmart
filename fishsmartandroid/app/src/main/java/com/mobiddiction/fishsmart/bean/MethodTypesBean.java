package com.mobiddiction.fishsmart.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class MethodTypesBean implements Serializable {
    public String createdBy;
    public String createdDate;
    public String lastModifiedBy;
    public String lastModifiedDate;
    public int id;
    public String name;
    public int type_id;
    public boolean featured;
    public boolean active;
    public String content;
    public ArrayList<MethodContentBean> methodContentList = new ArrayList<>();
    public boolean isSelected;
}