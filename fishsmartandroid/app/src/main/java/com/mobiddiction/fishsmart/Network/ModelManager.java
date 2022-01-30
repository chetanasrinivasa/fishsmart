package com.mobiddiction.fishsmart.Network;

import com.mobiddiction.fishsmart.MyApplication;
import com.mobiddiction.fishsmart.dao.AllSpecies;
import com.mobiddiction.fishsmart.dao.AllSpeciesDao;
import com.mobiddiction.fishsmart.dao.AllSpeciesGroup;
import com.mobiddiction.fishsmart.dao.AllSpeciesGroupDao;
import com.mobiddiction.fishsmart.dao.AllSpeciesfishFacts;
import com.mobiddiction.fishsmart.dao.AllSpeciesfishFactsDao;
import com.mobiddiction.fishsmart.dao.AllSpeciesrules;
import com.mobiddiction.fishsmart.dao.AllSpeciesrulesDao;
import com.mobiddiction.fishsmart.dao.DaoMaster;
import com.mobiddiction.fishsmart.dao.DaoSession;
import com.mobiddiction.fishsmart.dao.DeviceData;
import com.mobiddiction.fishsmart.dao.FirstTimeLoad;
import com.mobiddiction.fishsmart.dao.Fresh;
import com.mobiddiction.fishsmart.dao.FreshDao;
import com.mobiddiction.fishsmart.dao.FreshData;
import com.mobiddiction.fishsmart.dao.FreshWaterNewData;
import com.mobiddiction.fishsmart.dao.FreshWaterNewDataDao;
import com.mobiddiction.fishsmart.dao.FreshWaterfishFacts;
import com.mobiddiction.fishsmart.dao.FreshWaterfishFactsDao;
import com.mobiddiction.fishsmart.dao.FreshWaterfishGroup;
import com.mobiddiction.fishsmart.dao.FreshWaterfishGroupDao;
import com.mobiddiction.fishsmart.dao.FreshWaterrules;
import com.mobiddiction.fishsmart.dao.FreshWaterrulesDao;
import com.mobiddiction.fishsmart.dao.Gallery;
import com.mobiddiction.fishsmart.dao.GalleryImage;
import com.mobiddiction.fishsmart.dao.GalleryImageDao;
import com.mobiddiction.fishsmart.dao.LoginDetail;
import com.mobiddiction.fishsmart.dao.LoginResponse;
import com.mobiddiction.fishsmart.dao.NEWSpeciesData;
import com.mobiddiction.fishsmart.dao.NEWSpeciesDataDao;
import com.mobiddiction.fishsmart.dao.NewGuide;
import com.mobiddiction.fishsmart.dao.NewLicence;
import com.mobiddiction.fishsmart.dao.NewLicenceData;
import com.mobiddiction.fishsmart.dao.NewLicenceDataDao;
import com.mobiddiction.fishsmart.dao.NewListTypeModel;
import com.mobiddiction.fishsmart.dao.NewListTypeModelDao;
import com.mobiddiction.fishsmart.dao.NewMap;
import com.mobiddiction.fishsmart.dao.NewMapData;
import com.mobiddiction.fishsmart.dao.NewMapDataDao;
import com.mobiddiction.fishsmart.dao.NewMapPinsLatLonData;
import com.mobiddiction.fishsmart.dao.NewMapPinsLatLonDataDao;
import com.mobiddiction.fishsmart.dao.NewRulesGuide;
import com.mobiddiction.fishsmart.dao.Onboarding;
import com.mobiddiction.fishsmart.dao.Policies;
import com.mobiddiction.fishsmart.dao.Salt;
import com.mobiddiction.fishsmart.dao.SaltDao;
import com.mobiddiction.fishsmart.dao.SaltData;
import com.mobiddiction.fishsmart.dao.SaltWaterNewData;
import com.mobiddiction.fishsmart.dao.SaltWaterNewDataDao;
import com.mobiddiction.fishsmart.dao.SaltWaterfishFacts;
import com.mobiddiction.fishsmart.dao.SaltWaterfishFactsDao;
import com.mobiddiction.fishsmart.dao.SaltWaterfishGroup;
import com.mobiddiction.fishsmart.dao.SaltWaterfishGroupDao;
import com.mobiddiction.fishsmart.dao.SaltWaterrules;
import com.mobiddiction.fishsmart.dao.SaltWaterrulesDao;
import com.mobiddiction.fishsmart.dao.SignUp;
import com.mobiddiction.fishsmart.dao.SpeciesDataObject;
import com.mobiddiction.fishsmart.dao.TermAndCondition;
import com.mobiddiction.fishsmart.dao.TermConditionVersion;
import com.mobiddiction.fishsmart.dao.Notification;
import com.mobiddiction.fishsmart.dao.NotificationDao;
import com.mobiddiction.fishsmart.dao.NotificationDevice;
import com.mobiddiction.fishsmart.dao.NotificationImage;
import com.mobiddiction.fishsmart.dao.NotificationImageDao;
import com.mobiddiction.fishsmart.dao.NotificationRoles;
import com.mobiddiction.fishsmart.dao.UserHistory;
import com.mobiddiction.fishsmart.dao.UserObj;

import java.util.List;


/**
 * Created by AI on 16/06/2017.
 */

public class ModelManager {

    private static final String TAG = ModelManager.class.getSimpleName();
    private static ModelManager instance;
    private final DaoSession daoSession;



    private ModelManager() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApplication.getRestApplication(), "fishsmart-db", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static synchronized ModelManager getInstance() {
        if (instance == null) {
            instance = new ModelManager();
        }
        return instance;
    }

    //-----------------------------------------------remove-----------------------------------------
    public synchronized void removeNewGuide(){daoSession.getNewGuideDao().deleteAll();}
    public synchronized void removeNewRulesGuide(){daoSession.getNewRulesGuideDao().deleteAll();}
    public synchronized void removeNewListTypeModel(){daoSession.getNewListTypeModelDao().deleteAll();}
    public synchronized void removeNewMapData(){daoSession.getNewMapDataDao().deleteAll();}
    public synchronized void removeNewMapPinsLatLonData(){daoSession.getNewMapPinsLatLonDataDao().deleteAll();}
    public synchronized void removeNewMaps(){daoSession.getNewMapDao().deleteAll();}
    public synchronized void removeAllData(){daoSession.getDataDao().deleteAll();}
    public synchronized void removeAllNewSpeciesData(){daoSession.getNEWSpeciesDataDao().deleteAll();}
    public synchronized void removeAllDataObject(){daoSession.getSpeciesDataObjectDao().deleteAll();}
    public synchronized void removeAllFreshWater(){daoSession.getFreshWaterNewDataDao().deleteAll();}
    public synchronized void removeAllSaltWater(){daoSession.getSaltWaterNewDataDao().deleteAll();}
    public synchronized void removeAllFirstTime(){daoSession.getFirstTimeLoadDao().deleteAll();}
    public synchronized void removeAllFreshDATAWaterNEW(){daoSession.getFreshDataDao().deleteAll();}
    public synchronized void removeAllFreshWaterNEW(){daoSession.getFreshDao().deleteAll();}
    public synchronized void removeAllSaltDATAWaterNEW(){daoSession.getSaltDataDao().deleteAll();}
    public synchronized void removeAllSaltWaterNEW(){daoSession.getSaltDao().deleteAll();}
    public synchronized void removeAllFreshWaterfishGroup(){daoSession.getFreshWaterfishGroupDao().deleteAll();}
    public synchronized void removeAllFreshWaterfishFacts(){daoSession.getFreshWaterfishFactsDao().deleteAll();}
    public synchronized void removeAllFreshWaterrules(){daoSession.getFreshWaterrulesDao().deleteAll();}
    public synchronized void removeAllSaltWaterfishGroup(){daoSession.getSaltWaterfishGroupDao().deleteAll();}
    public synchronized void removeAllSaltWaterfishFacts(){daoSession.getSaltWaterfishFactsDao().deleteAll();}
    public synchronized void removeAllSaltWaterrules(){daoSession.getSaltWaterrulesDao().deleteAll();}
    public synchronized void removeAllSpeciesData(){daoSession.getAllSpeciesDao().deleteAll();}
    public synchronized void removeAllSpeciesGroup(){daoSession.getAllSpeciesGroupDao().deleteAll();}
    public synchronized void removeAllSpeciesfishFacts(){daoSession.getAllSpeciesfishFactsDao().deleteAll();}
    public synchronized void removeAllSpeciesrules(){daoSession.getAllSpeciesrulesDao().deleteAll();}
    public synchronized void removeOnboarding(){daoSession.getOnboardingDao().deleteAll();}
    public synchronized void removeLogin(){daoSession.getLoginResponseDao().deleteAll();}
    public synchronized void removeLogindetail(){daoSession.getLoginDetailDao().deleteAll();}
    public synchronized void removeTermAndCondition(){daoSession.getTermAndConditionDao().deleteAll();}
    public synchronized void removePolicies(){daoSession.getPoliciesDao().deleteAll();}
    public synchronized void removeSignUp(){daoSession.getSignUpDao().deleteAll();}
    public synchronized void removeNewLicenceData(){daoSession.getNewLicenceDataDao().deleteAll();}
    public synchronized void removeNewLicence(){daoSession.getNewLicenceDao().deleteAll();}
    public synchronized void removeTermAndConditionID(){daoSession.getTermConditionVersionDao().deleteAll();}
    public synchronized void removeGallery(){daoSession.getGalleryDao().deleteAll();}
    public synchronized void removeGalleryImage(){daoSession.getGalleryImageDao().deleteAll();}
    public synchronized void removeNotificationRole(){daoSession.getNotificationRolesDao().deleteAll();}
    public synchronized void removeNotificationDevice(){daoSession.getNotificationDeviceDao().deleteAll();}
    public synchronized void removeDeviceData(){daoSession.getDeviceDataDao().deleteAll();}
    public synchronized void removeuserObj(){daoSession.getUserObjDao().deleteAll();}
    public synchronized void removeUserHistory(){daoSession.getUserHistoryDao().deleteAll();}
    public synchronized void removeNotificationImage(){daoSession.getNotificationImageDao().deleteAll();}
    public synchronized void removeNotification(){daoSession.getNotificationDao().deleteAll();}

    //-----------------------------------------insert-----------------------------------------------
    public synchronized void insertGallery(List<Gallery> galleries){
        daoSession.getGalleryDao().insertInTx(galleries);
    }
    public synchronized void insertGalleryImage(List<GalleryImage> gallerieImages){
        daoSession.getGalleryImageDao().insertInTx(gallerieImages);
    }
    public synchronized void insertNewLicence(List<NewLicence> newLicence){
        daoSession.getNewLicenceDao().insertInTx(newLicence);
    }
    public synchronized void insertTermAndConditionVersion(TermConditionVersion userHistories){
        daoSession.getTermConditionVersionDao().insertInTx(userHistories);
    }
    public synchronized void insertNewLicenceData(List<NewLicenceData> newLicenceData){
        daoSession.getNewLicenceDataDao().insertInTx(newLicenceData);
    }
    public synchronized void insertNewListType(List<NewListTypeModel> newListTypeModels){
        daoSession.getNewListTypeModelDao().insertInTx(newListTypeModels);
    }
    public synchronized void insertNewRulesGuide(List<NewRulesGuide> newRulesGuides){
        daoSession.getNewRulesGuideDao().insertInTx(newRulesGuides);
    }
    public synchronized void insertnewGuides(List<NewGuide> newGuides){
        daoSession.getNewGuideDao().insertInTx(newGuides);
    }
    public synchronized void insertnewMapPinsLatLonData(List<NewMapPinsLatLonData> newMapPinsLatLonData){
        daoSession.getNewMapPinsLatLonDataDao().insertInTx(newMapPinsLatLonData);
    }
    public synchronized void insertNewMapData(List<NewMapData> newMapdata){
        daoSession.getNewMapDataDao().insertInTx(newMapdata);
    }
    public synchronized void insertNewMap(List<NewMap> newMap){
        daoSession.getNewMapDao().insertInTx(newMap);
    }
    public synchronized void insertSignUp(SignUp signUp){
        daoSession.getSignUpDao().insertInTx(signUp);
    }
    public synchronized void insertTermAndCondition(TermAndCondition termAndCondition){
        daoSession.getTermAndConditionDao().insertInTx(termAndCondition);
    }
    public synchronized void insertPolicies(Policies policies){
        daoSession.getPoliciesDao().insertInTx(policies);
    }
    public synchronized void insertLoginDetail(LoginDetail loginResponses){
        daoSession.getLoginDetailDao().insertInTx(loginResponses);
    }
    public synchronized void insertLoginResponse(LoginResponse loginResponses){
        daoSession.getLoginResponseDao().insertInTx(loginResponses);
    }
    public synchronized void insertOnboarding(List<Onboarding> onboarding){
        daoSession.getOnboardingDao().insertInTx(onboarding);
    }
    public synchronized void insertFirstTime(FirstTimeLoad additionalData){
        daoSession.getFirstTimeLoadDao().insertInTx(additionalData);
    }
    public synchronized void insertAllNewSpecies(List<NEWSpeciesData> additionalData){
        daoSession.getNEWSpeciesDataDao().insertInTx(additionalData);
    }
    public synchronized void insertAllSpeciesDataObject(List<SpeciesDataObject> additionalData){
        daoSession.getSpeciesDataObjectDao().insertInTx(additionalData);
    }
    public synchronized void insertAllFreshWaterNewData(FreshWaterNewData additionalData){
        daoSession.getFreshWaterNewDataDao().insertInTx(additionalData);
    }
    public synchronized void insertAllSaltWaterNewData(SaltWaterNewData additionalData){
        daoSession.getSaltWaterNewDataDao().insertInTx(additionalData);
    }
    public synchronized void insertFresh(List<Fresh> additionalData){
        daoSession.getFreshDao().insertInTx(additionalData);
    }

    public synchronized void insertFreshData(FreshData additionalData){
        daoSession.getFreshDataDao().insertInTx(additionalData);
    }

    public synchronized void insertSalt(List<Salt> additionalData){
        daoSession.getSaltDao().insertInTx(additionalData);
    }

    public synchronized void insertSaltData(SaltData additionalData){
        daoSession.getSaltDataDao().insertInTx(additionalData);
    }

    public synchronized void insertFreshWaterfishGroup(FreshWaterfishGroup additionalData){
        daoSession.getFreshWaterfishGroupDao().insertInTx(additionalData);
    }

    public synchronized void insertFreshWaterfishFacts(FreshWaterfishFacts additionalData){
        daoSession.getFreshWaterfishFactsDao().insertInTx(additionalData);
    }

    public synchronized void insertFreshWaterrules(FreshWaterrules additionalData){
        daoSession.getFreshWaterrulesDao().insertInTx(additionalData);
    }


    public synchronized void insertSaltWaterfishGroup(SaltWaterfishGroup additionalData){
        daoSession.getSaltWaterfishGroupDao().insertInTx(additionalData);
    }

    public synchronized void insertSaltWaterfishFacts(SaltWaterfishFacts additionalData){
        daoSession.getSaltWaterfishFactsDao().insertInTx(additionalData);
    }

    public synchronized void insertSaltWaterrules(SaltWaterrules additionalData){
        daoSession.getSaltWaterrulesDao().insertInTx(additionalData);
    }

    public synchronized void insertAllSpecies(List<AllSpecies> allSpecies){
        daoSession.getAllSpeciesDao().insertInTx(allSpecies);
    }

    public synchronized void insertAllSpeciesGroup(List<AllSpeciesGroup> AllSpeciesGroup){
        daoSession.getAllSpeciesGroupDao().insertInTx(AllSpeciesGroup);
    }

    public synchronized void insertAllSpeciesfishFacts(List<AllSpeciesfishFacts> AllSpeciesfishFacts){
        daoSession.getAllSpeciesfishFactsDao().insertInTx(AllSpeciesfishFacts);
    }

    public synchronized void insertAllSpeciesrules(List<AllSpeciesrules> AllSpeciesrules){
        daoSession.getAllSpeciesrulesDao().insertInTx(AllSpeciesrules);
    }

    public synchronized void insertNotificationuserRole(List<NotificationRoles> notificationRoles){
        daoSession.getNotificationRolesDao().insertInTx(notificationRoles);
    }

    public synchronized void insertUserHistory(List<UserHistory> userHistories){
        daoSession.getUserHistoryDao().insertInTx(userHistories);
    }

    public synchronized void insertUserObj(List<UserObj> userObj){
        daoSession.getUserObjDao().insertInTx(userObj);
    }

    public synchronized void insertNotificationDevice(List<NotificationDevice> notificationDevice){
        daoSession.getNotificationDeviceDao().insertInTx(notificationDevice);
    }

    public synchronized void insertDeviceData(List<DeviceData> deviceData){
        daoSession.getDeviceDataDao().insertInTx(deviceData);
    }

    /**
     *
     * @param notificationImage
     */
    public synchronized void insertNotificationImage(List<NotificationImage> notificationImage){
        daoSession.getNotificationImageDao().insertInTx(notificationImage);
    }
    /**
     *
     * @param notifications
     */
    public synchronized void insertNotification(List<Notification> notifications){
        daoSession.getNotificationDao().insertInTx(notifications);
    }




    //-----------------------------------------get-----------------------------------------------

    public synchronized List<Gallery> getGallery(){
        return daoSession.getGalleryDao().queryBuilder().list();
    }
    public synchronized List<GalleryImage> getGalleryImage(){
        return daoSession.getGalleryImageDao().queryBuilder().orderDesc(GalleryImageDao.Properties.CreatedDate).list();
    }
    public synchronized List<NewListTypeModel> getNewListTypeModelbyid(String id){
        return daoSession.getNewListTypeModelDao().queryBuilder().where(NewListTypeModelDao.Properties.Id.eq(id)).list();
    }
    public synchronized List<NewListTypeModel> getNewListTypeModel(){
        return daoSession.getNewListTypeModelDao().queryBuilder().list();
    }
    public synchronized List<NewRulesGuide> getNewRulesGuide(){
        return daoSession.getNewRulesGuideDao().queryBuilder().list();
    }
    public synchronized List<Onboarding> getOnboarding(){
        return daoSession.getOnboardingDao().queryBuilder().list();
    }
    public synchronized boolean isFirstTimeLoad(){
        return daoSession.getFirstTimeLoadDao().queryBuilder().list().get(0).getIsFirstTimeLoad();
    }

    public synchronized List<FirstTimeLoad> getFirstLoad(){
        return daoSession.getFirstTimeLoadDao().queryBuilder().list();
    }
    public synchronized List<FreshWaterNewData> getFreshWater(){
        return daoSession.getFreshWaterNewDataDao().queryBuilder().orderAsc(FreshWaterNewDataDao.Properties.Title).list();
    }
    public synchronized List<SaltWaterNewData> getSaltWater(){
        return daoSession.getSaltWaterNewDataDao().queryBuilder().orderAsc(SaltWaterNewDataDao.Properties.Title).list();
    }


    public synchronized List<AllSpecies> getSaltFromALL(){
        return daoSession.getAllSpeciesDao().queryBuilder().where(AllSpeciesDao.Properties.SpeciesType.eq("SALTWATER")).orderAsc(AllSpeciesDao.Properties.Title).list();
    }


    public synchronized List<SaltWaterfishGroup> getSaltWaterfishGroup(){
        return daoSession.getSaltWaterfishGroupDao().queryBuilder().orderAsc().list();
    }

    public synchronized List<FreshWaterfishGroup> getFreshWaterFishGroup(){
        return daoSession.getFreshWaterfishGroupDao().queryBuilder().orderAsc().list();
    }

    public synchronized AllSpecies getAllSpecies(String id){
        return daoSession.getAllSpeciesDao().queryBuilder().where(AllSpeciesDao.Properties.Id.eq(id)).list().get(0);
    }

    public synchronized FreshWaterNewData getFreshWaterSpeciesByID(String id){
        return daoSession.getFreshWaterNewDataDao().queryBuilder().where(FreshWaterNewDataDao.Properties.Id.eq(id)).list().get(0);
    }

    public synchronized FreshWaterrules getFreshWaterRules(String id){
        return daoSession.getFreshWaterrulesDao().queryBuilder().where(FreshWaterrulesDao.Properties.FreshID.eq(id)).list().get(0);
    }

    public synchronized FreshWaterfishFacts getFreshWaterFishFact(String id){
        return daoSession.getFreshWaterfishFactsDao().queryBuilder().where(FreshWaterfishFactsDao.Properties.FreshID.eq(id)).list().get(0);
    }
    public synchronized FreshWaterfishGroup getFreshWaterGroupFishByID(String id){
        return daoSession.getFreshWaterfishGroupDao().queryBuilder().where(FreshWaterfishGroupDao.Properties.FreshID.eq(id)).list().get(0);
    }

    public synchronized SaltWaterNewData getSaltWaterSpeciesByID(String id){
        return daoSession.getSaltWaterNewDataDao().queryBuilder().where(SaltWaterNewDataDao.Properties.Id.eq(id)).list().get(0);
    }

    public synchronized SaltWaterrules getSaltWaterRules(String id){
        return daoSession.getSaltWaterrulesDao().queryBuilder().where(SaltWaterrulesDao.Properties.SaltID.eq(id)).list().get(0);
    }

    public synchronized SaltWaterfishFacts getSaltWaterFishFact(String id){
        return daoSession.getSaltWaterfishFactsDao().queryBuilder().where(SaltWaterfishFactsDao.Properties.SaltID.eq(id)).list().get(0);
    }

    public synchronized SaltWaterfishGroup getSaltWaterGroupFishByID(String id){
        return daoSession.getSaltWaterfishGroupDao().queryBuilder().where(SaltWaterfishGroupDao.Properties.SaltID.eq(id)).list().get(0);
    }

    public synchronized List<AllSpecies> getSpeciesByString(String title){
        return daoSession.getAllSpeciesDao().queryBuilder().where(AllSpeciesDao.Properties.Title.like("%" + title + "%")).list();
    }

    public synchronized List<NewLicenceData> getNewLicenceData(){
        return daoSession.getNewLicenceDataDao().queryBuilder().list();
    }

    public synchronized List<NewLicenceData> getNewLicenceVISITAFISHERIESOFFICEData(){
        return daoSession.getNewLicenceDataDao().queryBuilder().where(NewLicenceDataDao.Properties.SubType.eq("VISITAFISHERIESOFFICE")).list();
    }
    public synchronized List<NewLicenceData> getNewLicenceCONTACTUSData(){
        return daoSession.getNewLicenceDataDao().queryBuilder().where(NewLicenceDataDao.Properties.SubType.eq("CONTACTUS")).list();
    }

    public synchronized List<NewLicenceData> getNewLicenceREPORTData(){
        return daoSession.getNewLicenceDataDao().queryBuilder().where(NewLicenceDataDao.Properties.SubType.eq("REPORT")).list();
    }

    public synchronized List<NewLicenceData> getNewLicenceNSWRECREATIONALLICENSEFEEData(){
        return daoSession.getNewLicenceDataDao().queryBuilder().where(NewLicenceDataDao.Properties.SubType.eq("NSWRECREATIONALLICENSEFEE")).orderAsc(NewLicenceDataDao.Properties.Title).list();
    }

    public synchronized AllSpeciesrules getFreshWaterRulesOffline(String id){
        return daoSession.getAllSpeciesrulesDao().queryBuilder().where(AllSpeciesrulesDao.Properties.FreshID.eq(id)).list().get(0);
    }

    public synchronized AllSpeciesfishFacts getFreshWaterFishFactOffline(String id){
        return daoSession.getAllSpeciesfishFactsDao().queryBuilder().where(AllSpeciesfishFactsDao.Properties.FreshID.eq(id)).list().get(0);
    }
    public synchronized AllSpeciesGroup getFreshWaterGroupFishByIDOffline(String id){
        return daoSession.getAllSpeciesGroupDao().queryBuilder().where(AllSpeciesGroupDao.Properties.FreshID.eq(id)).list().get(0);
    }

    public synchronized Policies getPolicies(){
        if(daoSession.getPoliciesDao().queryBuilder().list().size() > 0) {
            return daoSession.getPoliciesDao().queryBuilder().list().get(0);
        }else{
            return null;
        }
    }



    public synchronized AllSpecies getSpeciesByid(String id){
        return daoSession.getAllSpeciesDao().queryBuilder().where(AllSpeciesDao.Properties.Id.eq(id)).list().get(0);
    }


    public synchronized List<Fresh> getFresh(){
        return daoSession.getFreshDao().queryBuilder().orderAsc(FreshDao.Properties.Title).list();
    }
    public synchronized List<Salt> getSalt(){
        return daoSession.getSaltDao().queryBuilder().orderAsc(SaltDao.Properties.Title).list();
    }
    public synchronized NEWSpeciesData getNewSpeciesData(){
        if(daoSession.getNEWSpeciesDataDao().queryBuilder().list().size() >= 1 && daoSession.getNEWSpeciesDataDao().queryBuilder().list().get(0) != null) {
            return daoSession.getNEWSpeciesDataDao().queryBuilder().list().get(0);
        } else{
            return null;
        }
    }

    public synchronized List<NEWSpeciesData> getNewSpeciesDataFeatured(){
        if(daoSession.getNEWSpeciesDataDao().queryBuilder().list().size() >= 1 && daoSession.getNEWSpeciesDataDao().queryBuilder().list().get(0) != null) {
            return daoSession.getNEWSpeciesDataDao().queryBuilder().where(NEWSpeciesDataDao.Properties.Featured.eq(true)).orderAsc(NEWSpeciesDataDao.Properties.Name).list();
        } else{
            return null;
        }
    }

    public synchronized LoginResponse getLoginResponse(){

        if(daoSession.getLoginResponseDao().queryBuilder().list().size() > 0) {
            return daoSession.getLoginResponseDao().queryBuilder().list().get(0);
        } else{
            return null;
        }
    }

    public synchronized TermAndCondition getTermAndCondition(){
        if(daoSession.getTermAndConditionDao().queryBuilder().list().size() >= 1 &&  daoSession.getTermAndConditionDao().queryBuilder().list().get(0) != null) {
            return daoSession.getTermAndConditionDao().queryBuilder().list().get(0);
        } else{
            return  null;
        }
    }

    public synchronized LoginDetail getLoginDetails(){
        if(daoSession.getLoginDetailDao().queryBuilder().list().size() > 0) {
            return daoSession.getLoginDetailDao().queryBuilder().list().get(0);
        }else{
            return  null;
        }
    }

    public synchronized NewMapData getListMapDataByid(String mapId){
        return daoSession.getNewMapDataDao().queryBuilder().where(NewMapDataDao.Properties.Id.eq(mapId)).list().get(0);
    }
    public synchronized List<NewMapData> getListMapData(){
        return daoSession.getNewMapDataDao().queryBuilder().list();
    }
    public synchronized List<NewMapPinsLatLonData> getMapPins(String id){
        return daoSession.getNewMapPinsLatLonDataDao().queryBuilder().where(NewMapPinsLatLonDataDao.Properties.Id.eq(id)).list();
    }

    public synchronized NotificationImage getNotificationImageByNotificationId(String notifId){
        if( daoSession.getNotificationImageDao().queryBuilder().where(NotificationImageDao.Properties.Notification_id.eq(notifId)).list().size() > 0) {
            return daoSession.getNotificationImageDao().queryBuilder().where(NotificationImageDao.Properties.Notification_id.eq(notifId)).list().get(0);
        }else{
            return null;
        }
    }

    public synchronized List<Notification> getNotification(){
//        return daoSession.getNotificationDao().queryBuilder().where(NotificationDao.Properties.Status.notEq("PENDING")).list();
        return daoSession.getNotificationDao().queryBuilder().orderDesc(NotificationDao.Properties.CreatedDate).list();
    }

    public synchronized List<Notification> getNotificationAlertUnRead(){
//        return daoSession.getNotificationDao().queryBuilder().where(NotificationDao.Properties.Status.notEq("PENDING")).list();
        return daoSession.getNotificationDao().queryBuilder().where(NotificationDao.Properties.Push.eq("true"), NotificationDao.Properties.Status.notEq("READ")).orderDesc(NotificationDao.Properties.CreatedDate).list();
    }

    public synchronized List<NotificationDevice> getNotificationDeviceData(){
        return daoSession.getNotificationDeviceDao().loadAll();
    }

    public synchronized List<DeviceData> getDeviceData(){
        return daoSession.getDeviceDataDao().loadAll();
    }

}
