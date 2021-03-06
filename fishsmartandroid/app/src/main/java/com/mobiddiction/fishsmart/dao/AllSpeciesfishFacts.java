package com.mobiddiction.fishsmart.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "ALL_SPECIESFISH_FACTS".
 */
public class AllSpeciesfishFacts {

    private Integer id;
    private String about;
    private String FreshID;
    private String size;
    private String confusingSpecies;
    private String characteristics;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public AllSpeciesfishFacts() {
    }

    public AllSpeciesfishFacts(Integer id, String about, String FreshID, String size, String confusingSpecies, String characteristics) {
        this.id = id;
        this.about = about;
        this.FreshID = FreshID;
        this.size = size;
        this.confusingSpecies = confusingSpecies;
        this.characteristics = characteristics;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFreshID() {
        return FreshID;
    }

    public void setFreshID(String FreshID) {
        this.FreshID = FreshID;
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

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
