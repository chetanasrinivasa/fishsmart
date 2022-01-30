package com.mobiddiction.fishsmart.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "NEW_LIST_TYPE_MODEL".
 */
public class NewListTypeModel {

    private String id;
    private String title;
    private String description;
    private String pdfUrl;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public NewListTypeModel() {
    }

    public NewListTypeModel(String id, String title, String description, String pdfUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pdfUrl = pdfUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
