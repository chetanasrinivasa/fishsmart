package com.mobiddiction.fishsmart.Rules;

import java.util.ArrayList;

/**
 * Created by Archa on 9/05/2016.
 */
public class RulesModel {

    String fishingGuideType;
    ArrayList<ListTypeModel> listtypemodel = new ArrayList<ListTypeModel>();

    public String getFishingGuideType() {
        return fishingGuideType;
    }

    public void setFishingGuideType(String fishingGuideType) {
        this.fishingGuideType = fishingGuideType;
    }

    public ArrayList<ListTypeModel> getListtypemodel() {
        return listtypemodel;
    }

    public void setListtypemodel(ArrayList<ListTypeModel> listtypemodel) {
        this.listtypemodel = listtypemodel;
    }
}