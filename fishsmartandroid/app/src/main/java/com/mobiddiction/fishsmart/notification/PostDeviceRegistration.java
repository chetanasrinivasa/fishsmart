package com.mobiddiction.fishsmart.notification;

public class PostDeviceRegistration {

    public String udid,deviceToken,deviceName,deviceOS,deviceType,appVersion,awsEndpoint;
    public boolean pushNotifications,active;
    public int user, version;



    public PostDeviceRegistration(String udid,String deviceToken,String deviceName,String deviceOS,String deviceType,String appVersion,String awsEndpoint,
                                  boolean pushNotifications,boolean active,int user, int version){
        this.udid = udid;
        this.deviceToken = deviceToken;
        this.deviceName = deviceName;
        this.deviceOS = deviceOS;
        this.deviceType = deviceType;
        this.appVersion = appVersion;
        this.awsEndpoint = awsEndpoint;
        this.pushNotifications = pushNotifications;
        this.active = active;
        this.user = user;
        this.version = version;

    }
}
