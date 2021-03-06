package com.mobiddiction.fishsmart.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "FRESH_WATERFISH_GROUP".
 */
public class FreshWaterfishGroup {

    private Integer id;
    private String FreshID;
    private String groupName;
    private String groupTitle;
    private String groupDescription;
    private String groupType;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public FreshWaterfishGroup() {
    }

    public FreshWaterfishGroup(Integer id, String FreshID, String groupName, String groupTitle, String groupDescription, String groupType) {
        this.id = id;
        this.FreshID = FreshID;
        this.groupName = groupName;
        this.groupTitle = groupTitle;
        this.groupDescription = groupDescription;
        this.groupType = groupType;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
