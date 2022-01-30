package com.daogenerator;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;
import de.greenrobot.daogenerator.ToOne;
/**
 * Run this by running "gradle run" from this module's root directory (daogenerator).
 *
 * @author andrewindayang
 */
public class DaoGenerator {

    /******* READ ME !!! IMPORTANT INFORMATION !!! *******
     *
     DAO MASTER FILE
     - On DaoMaster.DevOpenHelper.onUpgrade put this line:
     MigrationHelper.getInstance().migrateRest(db);
     - Delete following code : dropAllTables(db, true);
     onCreate(db);


     DAO GENERATOR FILE (THIS FILE)
     - INCREASE THE SCHEMA DB VERSION AS WELL
     - EVERYTIME ADDED NEW TABLE OR ADDED NEW PROPERTY MAKE SURE UPGRADE THAT.
     - CURRENT VERSION = 4

     MIGRATIONHELPER JAVA CLASS
     - IF THERE IS NEW TABLE THAT ADDED AND GENERATE ON DAO GENERATOR, PLEASE ADD THE DAO.CLASS INTO MIGRATION HELPER TO HELP YOU TO UPGRADE THE DB EASILY.
     - EXAMPLE :    public void migrateRestDb(SQLiteDatabase db) {
     MigrationHelper.getInstance().migrate(db, TokensDao.class, UserAddressDao.class, UserDetailsDao.class, //ADD YOUR NEW DAO HERE//);
     }
     *
     */

    public static final int DB_VERSION = 40;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(DB_VERSION, "com.mobiddiction.fishsmart.dao");
        schema.enableKeepSectionsByDefault();
        addEntitiesToSchema(schema);
        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, "../app/src/main/java");
//        new org.greenrobot.greendao.generator.DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    private static void addEntitiesToSchema(Schema schema) {

        Entity firstTimeLoad = schema.addEntity("FirstTimeLoad");
        firstTimeLoad.addBooleanProperty("isFirstTimeLoad");



        Entity Data = schema.addEntity("Data");
        Data.addIdProperty();


        Entity NEWSpeciesData = schema.addEntity("NEWSpeciesData");
        NEWSpeciesData.addStringProperty("id");
        NEWSpeciesData.addStringProperty("createdBy");
        NEWSpeciesData.addStringProperty("createdDate");
        NEWSpeciesData.addStringProperty("lastModifiedBy");
        NEWSpeciesData.addStringProperty("lastModifiedDate");
        NEWSpeciesData.addStringProperty("name");
        NEWSpeciesData.addStringProperty("type_id");
        NEWSpeciesData.addStringProperty("content");
        NEWSpeciesData.addBooleanProperty("featured");



        Entity SpeciesDataObject = schema.addEntity("SpeciesDataObject");
        SpeciesDataObject.addIdProperty();

        /**
         *
         */

        Entity FreshDataNew = schema.addEntity("FreshDataNew");
        FreshDataNew.addIdProperty();

        Entity SaltDataNew = schema.addEntity("SaltDataNew");
        SaltDataNew.addIdProperty();

        /**
         * ALL SALT WATER DATA
         */
        Entity SaltWaterNewData = schema.addEntity("SaltWaterNewData");
        SaltWaterNewData.addStringProperty("id");
        SaltWaterNewData.addStringProperty("title");
        SaltWaterNewData.addStringProperty("description");
        SaltWaterNewData.addStringProperty("speciesType");
        SaltWaterNewData.addStringProperty("sizeLimit");
        SaltWaterNewData.addStringProperty("bagLimit");
        SaltWaterNewData.addBooleanProperty("grouped");
        SaltWaterNewData.addStringProperty("image");
        SaltWaterNewData.addStringProperty("thumbnailImage");

        SaltWaterNewData.addByteArrayProperty("byteThumbnailSalt");
        SaltWaterNewData.addByteArrayProperty("byteImageSalt");
        SaltWaterNewData.addStringProperty("ruleBagLimit");
        SaltWaterNewData.addStringProperty("subHeader");
//        SaltWaterNewData.addStringProperty("subHeader");

        Entity SaltWaterfishGroup = schema.addEntity("SaltWaterfishGroup");
        SaltWaterfishGroup.addStringProperty("SaltID");
        SaltWaterfishGroup.addIntProperty("id");
        SaltWaterfishGroup.addStringProperty("groupName");
        SaltWaterfishGroup.addStringProperty("groupTitle");
        SaltWaterfishGroup.addStringProperty("groupDescription");
        SaltWaterfishGroup.addStringProperty("groupType");

        Entity SaltWaterfishFacts = schema.addEntity("SaltWaterfishFacts");
        SaltWaterfishFacts.addStringProperty("SaltID");
        SaltWaterfishFacts.addIntProperty("id");
        SaltWaterfishFacts.addStringProperty("about");
        SaltWaterfishFacts.addStringProperty("size");
        SaltWaterfishFacts.addStringProperty("confusingSpecies");
        SaltWaterfishFacts.addStringProperty("characteristics");

        Entity SaltWaterrules = schema.addEntity("SaltWaterrules");
        SaltWaterrules.addStringProperty("SaltID");
        SaltWaterrules.addIntProperty("id");
        SaltWaterrules.addStringProperty("legalSize");
        SaltWaterrules.addStringProperty("bagLimit");
        SaltWaterrules.addStringProperty("possession");
        SaltWaterrules.addStringProperty("ruleBagLimit");



        /**
         * ALL FRESH WATER DATA
         */
        Entity FreshWaterNewData = schema.addEntity("FreshWaterNewData");
        FreshWaterNewData.addStringProperty("id");
        FreshWaterNewData.addStringProperty("title");
        FreshWaterNewData.addStringProperty("description");
        FreshWaterNewData.addStringProperty("speciesType");
        FreshWaterNewData.addStringProperty("sizeLimit");
        FreshWaterNewData.addStringProperty("bagLimit");
        FreshWaterNewData.addBooleanProperty("grouped");
        FreshWaterNewData.addStringProperty("image");
        FreshWaterNewData.addStringProperty("thumbnailImage");

        FreshWaterNewData.addByteArrayProperty("byteThumbnailFresh");
        FreshWaterNewData.addByteArrayProperty("byteImageFresh");
        FreshWaterNewData.addStringProperty("ruleBagLimit");
        FreshWaterNewData.addStringProperty("subHeader");
//        FreshWaterNewData.addStringProperty("subHeader");

        Entity FreshWaterfishGroup = schema.addEntity("FreshWaterfishGroup");
        FreshWaterfishGroup.addIntProperty("id");
        FreshWaterfishGroup.addStringProperty("FreshID");
        FreshWaterfishGroup.addStringProperty("groupName");
        FreshWaterfishGroup.addStringProperty("groupTitle");
        FreshWaterfishGroup.addStringProperty("groupDescription");
        FreshWaterfishGroup.addStringProperty("groupType");

        Entity FreshWaterfishFacts = schema.addEntity("FreshWaterfishFacts");
        FreshWaterfishFacts.addIntProperty("id");
        FreshWaterfishFacts.addStringProperty("about");
        FreshWaterfishFacts.addStringProperty("FreshID");
        FreshWaterfishFacts.addStringProperty("size");
        FreshWaterfishFacts.addStringProperty("confusingSpecies");
        FreshWaterfishFacts.addStringProperty("characteristics");

        Entity FreshWaterrules = schema.addEntity("FreshWaterrules");
        FreshWaterrules.addIntProperty("id");
        FreshWaterrules.addStringProperty("FreshID");
        FreshWaterrules.addStringProperty("legalSize");
        FreshWaterrules.addStringProperty("bagLimit");
        FreshWaterrules.addStringProperty("possession");
        FreshWaterrules.addStringProperty("ruleBagLimit");




        /**
         * FRESH WATER SPECIES --> single
         */
        Entity FreshData = schema.addEntity("FreshData");
        FreshData.addIdProperty();
        FreshData.addBooleanProperty("success");

        Entity Fresh = schema.addEntity("Fresh");
        Fresh.addStringProperty("id");
        Fresh.addStringProperty("speciesType");
        Fresh.addStringProperty("title");
        Fresh.addStringProperty("description");
        Fresh.addStringProperty("bagLimitForCardView");
        Fresh.addStringProperty("sizeLimit");
        Fresh.addStringProperty("image");
        Fresh.addStringProperty("thumbnail");
        Fresh.addBooleanProperty("grouped");
        Fresh.addStringProperty("groupName");


        /**
         * SALT WATER SPECIES --> single
         */
        Entity SaltData = schema.addEntity("SaltData");
        SaltData.addIdProperty();
        SaltData.addBooleanProperty("success");

        Entity Salt = schema.addEntity("Salt");
        Salt.addStringProperty("id");
        Salt.addStringProperty("speciesType");
        Salt.addStringProperty("title");
        Salt.addStringProperty("description");
        Salt.addStringProperty("bagLimitForCardView");
        Salt.addStringProperty("sizeLimit");
        Salt.addStringProperty("image");
        Salt.addStringProperty("thumbnail");
        Salt.addBooleanProperty("grouped");
        Salt.addStringProperty("groupName");


        /**
         * ALL SPECIES
         */
        Entity AllSpecies = schema.addEntity("AllSpecies");
        AllSpecies.addStringProperty("id");
        AllSpecies.addStringProperty("title");
        AllSpecies.addStringProperty("description");
        AllSpecies.addStringProperty("speciesType");
        AllSpecies.addStringProperty("sizeLimit");
        AllSpecies.addStringProperty("bagLimit");
        AllSpecies.addBooleanProperty("grouped");
        AllSpecies.addStringProperty("image");
        AllSpecies.addStringProperty("thumbnailImage");
        AllSpecies.addByteArrayProperty("byteThumbnailFresh");
        AllSpecies.addByteArrayProperty("byteImageFresh");
        AllSpecies.addStringProperty("subHeader");
        AllSpecies.addStringProperty("ruleBagLimit");
        AllSpecies.addStringProperty("groupType");

        Entity AllSpeciesGroup = schema.addEntity("AllSpeciesGroup");
        AllSpeciesGroup.addIntProperty("id");
        AllSpeciesGroup.addStringProperty("FreshID");
        AllSpeciesGroup.addStringProperty("groupName");
        AllSpeciesGroup.addStringProperty("groupTitle");
        AllSpeciesGroup.addStringProperty("groupDescription");
        AllSpeciesGroup.addStringProperty("groupType");

        Entity AllSpeciesfishFacts = schema.addEntity("AllSpeciesfishFacts");
        AllSpeciesfishFacts.addIntProperty("id");
        AllSpeciesfishFacts.addStringProperty("about");
        AllSpeciesfishFacts.addStringProperty("FreshID");
        AllSpeciesfishFacts.addStringProperty("size");
        AllSpeciesfishFacts.addStringProperty("confusingSpecies");
        AllSpeciesfishFacts.addStringProperty("characteristics");

        Entity AllSpeciesrules = schema.addEntity("AllSpeciesrules");
        AllSpeciesrules.addIntProperty("id");
        AllSpeciesrules.addStringProperty("FreshID");
        AllSpeciesrules.addStringProperty("legalSize");
        AllSpeciesrules.addStringProperty("bagLimit");
        AllSpeciesrules.addStringProperty("possession");
        AllSpeciesrules.addStringProperty("ruleBagLimit");

        Entity loginDetail = schema.addEntity("LoginDetail");
        loginDetail.addStringProperty("email");
        loginDetail.addStringProperty("password");

        Entity LoginResponse = schema.addEntity("LoginResponse");
        LoginResponse.addStringProperty("Authorization");
        LoginResponse.addStringProperty("userId");
        LoginResponse.addStringProperty("changePassword");
        LoginResponse.addStringProperty("termsAccepted");


        /**
         * ONBOARDING
         */
        Entity Onboarding = schema.addEntity("Onboarding");
        Onboarding.addStringProperty("createdBy");
        Onboarding.addStringProperty("createdDate");
        Onboarding.addStringProperty("lastModifiedBy");
        Onboarding.addStringProperty("lastModifiedDate");
        Onboarding.addStringProperty("id");
        Onboarding.addStringProperty("headline");
        Onboarding.addStringProperty("shortDescription");
        Onboarding.addStringProperty("imageId");
        Onboarding.addStringProperty("imageURL");
        Onboarding.addStringProperty("videoURL");
        Onboarding.addStringProperty("externalURL");
        Onboarding.addStringProperty("enabled");
        Onboarding.addStringProperty("featured");
        Onboarding.addStringProperty("backgroundImageId");
        Onboarding.addStringProperty("backgroundImageURL");
        Onboarding.addStringProperty("backgroundColor");


        /**
         * TERM & CONDITION
         */
        Entity TermAndCondition = schema.addEntity("TermAndCondition");
        TermAndCondition.addStringProperty("text");
        TermAndCondition.addStringProperty("html");

        /**
         * POLICIES
         */
        Entity Policies = schema.addEntity("Policies");
        Policies.addStringProperty("createdBy");
        Policies.addStringProperty("createdDate");
        Policies.addStringProperty("lastModifiedBy");
        Policies.addStringProperty("lastModifiedDate");
        Policies.addStringProperty("id");
        Policies.addStringProperty("title");
        Policies.addStringProperty("text");
        Policies.addStringProperty("html");
        Policies.addStringProperty("docUrl");
        Policies.addBooleanProperty("required");

        /**
         * Sign Up
         */
        Entity SignUp = schema.addEntity("SignUp");
        SignUp.addStringProperty("id");
        SignUp.addStringProperty("firstName");
        SignUp.addStringProperty("lastName");
        SignUp.addStringProperty("password");
        SignUp.addStringProperty("matchingPassword");
        SignUp.addStringProperty("email");
        SignUp.addStringProperty("mobile");
        SignUp.addBooleanProperty("isUsing2FA");
        SignUp.addBooleanProperty("enabled");
        SignUp.addStringProperty("description");
        SignUp.addStringProperty("clientId");


        /**
         * map
         */
        Entity NewMap = schema.addEntity("NewMap");
        NewMap.addStringProperty("id");
        NewMap.addStringProperty("createdBy");
        NewMap.addStringProperty("createdDate");
        NewMap.addStringProperty("lastModifiedBy");
        NewMap.addStringProperty("lastModifiedDate");
        NewMap.addStringProperty("name");
        NewMap.addStringProperty("type_id");
        NewMap.addStringProperty("content");


        Entity NewMapData = schema.addEntity("NewMapData");
        NewMapData.addStringProperty("id");
        NewMapData.addStringProperty("Title");
        NewMapData.addStringProperty("description");
        NewMapData.addStringProperty("color");
        NewMapData.addStringProperty("mapType");
        NewMapData.addDoubleProperty("zoom");
        NewMapData.addDoubleProperty("topLeftLat");
        NewMapData.addDoubleProperty("topLeftLon");
        NewMapData.addDoubleProperty("bottomRightLat");
        NewMapData.addDoubleProperty("bottomRightLon");

        Entity NewMapPinsLatLonData = schema.addEntity("NewMapPinsLatLonData");
        NewMapPinsLatLonData.addStringProperty("id");
        NewMapPinsLatLonData.addDoubleProperty("lat");
        NewMapPinsLatLonData.addIntProperty("zoom");
        NewMapPinsLatLonData.addDoubleProperty("lon");


        /**
         * GUIDE
         */
        Entity NewGuide = schema.addEntity("NewGuide");
        NewGuide.addStringProperty("id");
        NewGuide.addStringProperty("createdBy");
        NewGuide.addStringProperty("createdDate");
        NewGuide.addStringProperty("lastModifiedBy");
        NewGuide.addStringProperty("lastModifiedDate");
        NewGuide.addStringProperty("name");
        NewGuide.addStringProperty("type_id");
        NewGuide.addStringProperty("content");


        Entity NewRulesGuide = schema.addEntity("NewRulesGuide");
        NewRulesGuide.addStringProperty("fishingGuideType");
        NewRulesGuide.addStringProperty("id");

        Entity NewListTypeModel = schema.addEntity("NewListTypeModel");
        NewListTypeModel.addStringProperty("id");
        NewListTypeModel.addStringProperty("title");
        NewListTypeModel.addStringProperty("description");
        NewListTypeModel.addStringProperty("pdfUrl");


        /**
         * CONTACT_LICENCE
         */
        Entity NewLicence = schema.addEntity("NewLicence");
        NewLicence.addStringProperty("id");
        NewLicence.addStringProperty("createdBy");
        NewLicence.addStringProperty("createdDate");
        NewLicence.addStringProperty("lastModifiedBy");
        NewLicence.addStringProperty("lastModifiedDate");
        NewLicence.addStringProperty("name");
        NewLicence.addStringProperty("type_id");
        NewLicence.addStringProperty("content");

        Entity NewLicenceData = schema.addEntity("NewLicenceData");
        NewLicenceData.addStringProperty("contactAndLicensingType");
        NewLicenceData.addStringProperty("title");
        NewLicenceData.addStringProperty("description");
        NewLicenceData.addStringProperty("url");
        NewLicenceData.addStringProperty("phone");
        NewLicenceData.addStringProperty("mobile");
        NewLicenceData.addStringProperty("subType");


        /**
         * NOTIFICATION_ROLES
         */
        Entity TermConditionVersion = schema.addEntity("TermConditionVersion");
        TermConditionVersion.addStringProperty("id");

        /**
         * GALLERY
         */
        Entity Gallery = schema.addEntity("Gallery");
        Gallery.addStringProperty("createdBy");
        Gallery.addStringProperty("createdDate");
        Gallery.addStringProperty("lastModifiedBy");
        Gallery.addStringProperty("lastModifiedDate");
        Gallery.addStringProperty("id");
        Gallery.addStringProperty("name");
        Gallery.addStringProperty("galleryId");
        Gallery.addStringProperty("description");
        Gallery.addStringProperty("note");
        Gallery.addStringProperty("adminNote");
        Gallery.addStringProperty("approved");
        Gallery.addStringProperty("pending");

        /**
         * GALLERY_IMAGE
         */
        Entity GalleryImage = schema.addEntity("GalleryImage");
        GalleryImage.addStringProperty("createdBy");
        GalleryImage.addStringProperty("createdDate");
        GalleryImage.addStringProperty("lastModifiedDate");
        GalleryImage.addStringProperty("id");
        GalleryImage.addStringProperty("name");
        GalleryImage.addStringProperty("state");
        GalleryImage.addStringProperty("url");
        GalleryImage.addStringProperty("favourite");
        GalleryImage.addStringProperty("deleted");
        GalleryImage.addStringProperty("clientId");
        GalleryImage.addStringProperty("userId");
        GalleryImage.addStringProperty("newsId");
        GalleryImage.addStringProperty("eventId");
        GalleryImage.addStringProperty("driverId");
        GalleryImage.addStringProperty("galleryId");
        GalleryImage.addStringProperty("jobId");
        GalleryImage.addStringProperty("catchId");
        GalleryImage.addStringProperty("note");
        GalleryImage.addStringProperty("expiry");
        GalleryImage.addStringProperty("profile");
        GalleryImage.implementsSerializable();

        /**
         * NOTIFICATION
         */
        Entity Notification = schema.addEntity("Notification");
        Notification.addStringProperty("createdBy");
        Notification.addStringProperty("createdDate");
        Notification.addStringProperty("lastModifiedBy");
        Notification.addStringProperty("lastModifiedDate");
        Notification.addStringProperty("id");
        Notification.addStringProperty("notifTitle");
        Notification.addStringProperty("notifText");
        Notification.addStringProperty("notifSound");
        Notification.addStringProperty("push");
        Notification.addStringProperty("segment");
        Notification.addStringProperty("beacon");
        Notification.addStringProperty("scheduled");
        Notification.addStringProperty("status");
        Notification.addStringProperty("responseMessage");
        Notification.addStringProperty("version");
        Notification.addStringProperty("callToAction");

        /**
         * NOTIFICATION_IMAGE
         */
        Entity NotificationImage = schema.addEntity("NotificationImage");
        NotificationImage.addStringProperty("id");
        NotificationImage.addStringProperty("notification_id");
        NotificationImage.addStringProperty("name");
        NotificationImage.addStringProperty("url");
        NotificationImage.addStringProperty("favourite");
        NotificationImage.addStringProperty("deleted");

        /**
         * NOTIFICATION_Device
         */
        Entity NotificationDevice = schema.addEntity("NotificationDevice");
        NotificationDevice.addStringProperty("id");
        NotificationDevice.addStringProperty("udid");
        NotificationDevice.addStringProperty("deviceToken");
        NotificationDevice.addStringProperty("deviceName");
        NotificationDevice.addStringProperty("deviceOS");
        NotificationDevice.addStringProperty("deviceType");
        NotificationDevice.addStringProperty("appVersion");
        NotificationDevice.addStringProperty("active");
        NotificationDevice.addStringProperty("pushNotifications");
        NotificationDevice.addStringProperty("awsEndpoint");
        NotificationDevice.addStringProperty("user");
        NotificationDevice.addStringProperty("version");

        /**
         * Device_Data
         */
        Entity DeviceData = schema.addEntity("DeviceData");
        DeviceData.addStringProperty("id");
        DeviceData.addStringProperty("udid");
        DeviceData.addStringProperty("deviceToken");
        DeviceData.addStringProperty("deviceName");
        DeviceData.addStringProperty("deviceOS");
        DeviceData.addStringProperty("deviceType");
        DeviceData.addStringProperty("appVersion");
        DeviceData.addStringProperty("active");
        DeviceData.addStringProperty("pushNotifications");
        DeviceData.addStringProperty("awsEndpoint");
        DeviceData.addStringProperty("user");
        DeviceData.addStringProperty("version");

        /**
         * USER_OBJ
         */
        Entity UserObj = schema.addEntity("UserObj");
        UserObj.addStringProperty("createdBy");
        UserObj.addStringProperty("createdDate");
        UserObj.addStringProperty("lastModifiedBy");
        UserObj.addStringProperty("lastModifiedDate");
        UserObj.addStringProperty("id");
        UserObj.addStringProperty("firstName");
        UserObj.addStringProperty("lastName");
        UserObj.addStringProperty("email");
        UserObj.addStringProperty("mobile");
        UserObj.addStringProperty("password");
        UserObj.addStringProperty("enabled");
        UserObj.addStringProperty("description");
        UserObj.addStringProperty("invitationAccepted");
        UserObj.addStringProperty("clientId");
        UserObj.addStringProperty("changePassword");
        UserObj.addStringProperty("termsAcceptedNew");
        UserObj.addStringProperty("imageId");
        UserObj.addStringProperty("using2FA");
        UserObj.addStringProperty("image");

        /**
         * NOTIFICATION_ROLES
         */
        Entity NotificationRoles = schema.addEntity("NotificationRoles");
        NotificationRoles.addStringProperty("id");
        NotificationRoles.addStringProperty("name");

        /**
         * USER_HISTORY
         */
        Entity UserHistory = schema.addEntity("UserHistory");
        UserHistory.addStringProperty("id");
        UserHistory.addStringProperty("description");
        UserHistory.addStringProperty("date");

    }
}
