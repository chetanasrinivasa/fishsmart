package com.mobiddiction.fishsmart.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "NOTIFICATION".
 */
public class Notification {

    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private String id;
    private String notifTitle;
    private String notifText;
    private String notifSound;
    private String push;
    private String segment;
    private String beacon;
    private String scheduled;
    private String status;
    private String responseMessage;
    private String version;
    private String callToAction;

    // KEEP FIELDS - put your custom fields here
    public NotificationImage image;
    public NotificationDevice device;

    // KEEP FIELDS END

    public Notification() {
    }

    public Notification(String createdBy, String createdDate, String lastModifiedBy, String lastModifiedDate, String id, String notifTitle, String notifText, String notifSound, String push, String segment, String beacon, String scheduled, String status, String responseMessage, String version, String callToAction) {
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.id = id;
        this.notifTitle = notifTitle;
        this.notifText = notifText;
        this.notifSound = notifSound;
        this.push = push;
        this.segment = segment;
        this.beacon = beacon;
        this.scheduled = scheduled;
        this.status = status;
        this.responseMessage = responseMessage;
        this.version = version;
        this.callToAction = callToAction;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotifTitle() {
        return notifTitle;
    }

    public void setNotifTitle(String notifTitle) {
        this.notifTitle = notifTitle;
    }

    public String getNotifText() {
        return notifText;
    }

    public void setNotifText(String notifText) {
        this.notifText = notifText;
    }

    public String getNotifSound() {
        return notifSound;
    }

    public void setNotifSound(String notifSound) {
        this.notifSound = notifSound;
    }

    public String getPush() {
        return push;
    }

    public void setPush(String push) {
        this.push = push;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getBeacon() {
        return beacon;
    }

    public void setBeacon(String beacon) {
        this.beacon = beacon;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCallToAction() {
        return callToAction;
    }

    public void setCallToAction(String callToAction) {
        this.callToAction = callToAction;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
