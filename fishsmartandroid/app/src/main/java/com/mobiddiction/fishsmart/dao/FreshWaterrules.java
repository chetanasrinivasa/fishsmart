package com.mobiddiction.fishsmart.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "FRESH_WATERRULES".
 */
public class FreshWaterrules {

    private Integer id;
    private String FreshID;
    private String legalSize;
    private String bagLimit;
    private String possession;
    private String ruleBagLimit;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public FreshWaterrules() {
    }

    public FreshWaterrules(Integer id, String FreshID, String legalSize, String bagLimit, String possession, String ruleBagLimit) {
        this.id = id;
        this.FreshID = FreshID;
        this.legalSize = legalSize;
        this.bagLimit = bagLimit;
        this.possession = possession;
        this.ruleBagLimit = ruleBagLimit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFreshID() {
        return FreshID;
    }

    public void setFreshID(String FreshID) {
        this.FreshID = FreshID;
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

    public String getRuleBagLimit() {
        return ruleBagLimit;
    }

    public void setRuleBagLimit(String ruleBagLimit) {
        this.ruleBagLimit = ruleBagLimit;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}