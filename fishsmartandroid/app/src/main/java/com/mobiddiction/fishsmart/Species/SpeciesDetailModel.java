package com.mobiddiction.fishsmart.Species;

import io.realm.RealmObject;

/**
 * Created by Archa on 4/05/2016.
 */
public class SpeciesDetailModel extends RealmObject {

    String title;
    String description;
    String speciesType;
    String sizeLimit;
    String fishGroupName;
    String fishGroupTitle;
    String fishGroupDesc;
    String about;
    String id;
    boolean grouped;
    String size;
    String confusingSpecies;
    String characteristics;
    String legalSize;
    String bagLimit;
    String possession;
    String thumbnailImage;

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

    public String getSpeciesType() {
        return speciesType;
    }

    public void setSpeciesType(String speciesType) {
        this.speciesType = speciesType;
    }

    public String getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(String sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getConfusingSpecies() {
        return confusingSpecies;
    }

    public void setConfusingSpecies(String confusingSpecies) {
        this.confusingSpecies = confusingSpecies;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getLegalSize() {
        return legalSize;
    }

    public void setLegalSize(String legalSize) {
        this.legalSize = legalSize;
    }

    public String getBagLimit() {
        return bagLimit;
    }

    public void setBagLimit(String bagLimit) {
        this.bagLimit = bagLimit;
    }

    public String getPossession() {
        return possession;
    }

    public void setPossession(String possession) {
        this.possession = possession;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getFishGroupName() {
        return fishGroupName;
    }

    public void setFishGroupName(String fishGroupName) {
        this.fishGroupName = fishGroupName;
    }

    public String getFishGroupTitle() {
        return fishGroupTitle;
    }

    public void setFishGroupTitle(String fishGroupTitle) {
        this.fishGroupTitle = fishGroupTitle;
    }

    public String getFishGroupDesc() {
        return fishGroupDesc;
    }

    public void setFishGroupDesc(String fishGroupDesc) {
        this.fishGroupDesc = fishGroupDesc;
    }
    public Boolean getGrouped() {
        return grouped;
    }

    public void setGrouped(Boolean grouped) {
        this.grouped = grouped;
    }

}
