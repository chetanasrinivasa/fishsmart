package com.mobiddiction.fishsmart.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "SALT_WATERFISH_GROUP".
 */
public class SaltWaterfishGroup {

    private String SaltID;
    private Integer id;
    private String groupName;
    private String groupTitle;
    private String groupDescription;
    private String groupType;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public SaltWaterfishGroup() {
    }

    public SaltWaterfishGroup(String SaltID, Integer id, String groupName, String groupTitle, String groupDescription, String groupType) {
        this.SaltID = SaltID;
        this.id = id;
        this.groupName = groupName;
        this.groupTitle = groupTitle;
        this.groupDescription = groupDescription;
        this.groupType = groupType;
    }

    public String getSaltID() {
        return SaltID;
    }

    public void setSaltID(String SaltID) {
        this.SaltID = SaltID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
