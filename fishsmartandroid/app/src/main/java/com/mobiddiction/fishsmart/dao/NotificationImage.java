package com.mobiddiction.fishsmart.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "NOTIFICATION_IMAGE".
 */
public class NotificationImage {

    private String id;
    private String notification_id;
    private String name;
    private String url;
    private String favourite;
    private String deleted;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public NotificationImage() {
    }

    public NotificationImage(String id, String notification_id, String name, String url, String favourite, String deleted) {
        this.id = id;
        this.notification_id = notification_id;
        this.name = name;
        this.url = url;
        this.favourite = favourite;
        this.deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
