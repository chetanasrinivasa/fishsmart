package com.mobiddiction.fishsmart.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "DEVICE_DATA".
 */
public class DeviceData {

    private String id;
    private String udid;
    private String deviceToken;
    private String deviceName;
    private String deviceOS;
    private String deviceType;
    private String appVersion;
    private String active;
    private String pushNotifications;
    private String awsEndpoint;
    private String user;
    private String version;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public DeviceData() {
    }

    public DeviceData(String id, String udid, String deviceToken, String deviceName, String deviceOS, String deviceType, String appVersion, String active, String pushNotifications, String awsEndpoint, String user, String version) {
        this.id = id;
        this.udid = udid;
        this.deviceToken = deviceToken;
        this.deviceName = deviceName;
        this.deviceOS = deviceOS;
        this.deviceType = deviceType;
        this.appVersion = appVersion;
        this.active = active;
        this.pushNotifications = pushNotifications;
        this.awsEndpoint = awsEndpoint;
        this.user = user;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getPushNotifications() {
        return pushNotifications;
    }

    public void setPushNotifications(String pushNotifications) {
        this.pushNotifications = pushNotifications;
    }

    public String getAwsEndpoint() {
        return awsEndpoint;
    }

    public void setAwsEndpoint(String awsEndpoint) {
        this.awsEndpoint = awsEndpoint;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
