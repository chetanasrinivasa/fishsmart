package com.mobiddiction.fishsmart.Species;

import io.realm.RealmObject;

/**
 * Created by Archa on 2/05/2016.
 */
public class SpeciesModel extends RealmObject {

    String id;
    String speciesType;
    String title;
    String description;
    String bagLimitForCardView;
    String sizeLimit;
    String image;
    String thumbnail;
    String groupName;
    Boolean grouped;
    Boolean isSaved = false;

    // More information

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpeciesType() {
        return speciesType;
    }

    public void setSpeciesType(String speciesType) {
        this.speciesType = speciesType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBagLimitForCardView() {
        return bagLimitForCardView;
    }

    public void setBagLimitForCardView(String bagLimitForCardView) {
        this.bagLimitForCardView = bagLimitForCardView;
    }

    public String getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(String sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getGrouped() {
        return grouped;
    }

    public void setGrouped(Boolean grouped) {
        this.grouped = grouped;
    }
}
