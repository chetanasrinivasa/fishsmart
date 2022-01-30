package com.mobiddiction.fishsmart.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "FRESH".
 */
public class Fresh {

    private String id;
    private String speciesType;
    private String title;
    private String description;
    private String bagLimitForCardView;
    private String sizeLimit;
    private String image;
    private String thumbnail;
    private Boolean grouped;
    private String groupName;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Fresh() {
    }

    public Fresh(String id, String speciesType, String title, String description, String bagLimitForCardView, String sizeLimit, String image, String thumbnail, Boolean grouped, String groupName) {
        this.id = id;
        this.speciesType = speciesType;
        this.title = title;
        this.description = description;
        this.bagLimitForCardView = bagLimitForCardView;
        this.sizeLimit = sizeLimit;
        this.image = image;
        this.thumbnail = thumbnail;
        this.grouped = grouped;
        this.groupName = groupName;
    }

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

    public Boolean getGrouped() {
        return grouped;
    }

    public void setGrouped(Boolean grouped) {
        this.grouped = grouped;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
