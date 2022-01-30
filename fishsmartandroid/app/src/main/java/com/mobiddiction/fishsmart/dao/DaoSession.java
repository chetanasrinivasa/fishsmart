package com.mobiddiction.fishsmart.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;


import com.mobiddiction.fishsmart.dao.FirstTimeLoad;
import com.mobiddiction.fishsmart.dao.Data;
import com.mobiddiction.fishsmart.dao.NEWSpeciesData;
import com.mobiddiction.fishsmart.dao.SpeciesDataObject;
import com.mobiddiction.fishsmart.dao.FreshDataNew;
import com.mobiddiction.fishsmart.dao.SaltDataNew;
import com.mobiddiction.fishsmart.dao.SaltWaterNewData;
import com.mobiddiction.fishsmart.dao.SaltWaterfishGroup;
import com.mobiddiction.fishsmart.dao.SaltWaterfishFacts;
import com.mobiddiction.fishsmart.dao.SaltWaterrules;
import com.mobiddiction.fishsmart.dao.FreshWaterNewData;
import com.mobiddiction.fishsmart.dao.FreshWaterfishGroup;
import com.mobiddiction.fishsmart.dao.FreshWaterfishFacts;
import com.mobiddiction.fishsmart.dao.FreshWaterrules;
import com.mobiddiction.fishsmart.dao.FreshData;
import com.mobiddiction.fishsmart.dao.Fresh;
import com.mobiddiction.fishsmart.dao.SaltData;
import com.mobiddiction.fishsmart.dao.Salt;
import com.mobiddiction.fishsmart.dao.AllSpecies;
import com.mobiddiction.fishsmart.dao.AllSpeciesGroup;
import com.mobiddiction.fishsmart.dao.AllSpeciesfishFacts;
import com.mobiddiction.fishsmart.dao.AllSpeciesrules;
import com.mobiddiction.fishsmart.dao.LoginDetail;
import com.mobiddiction.fishsmart.dao.LoginResponse;
import com.mobiddiction.fishsmart.dao.Onboarding;
import com.mobiddiction.fishsmart.dao.TermAndCondition;
import com.mobiddiction.fishsmart.dao.Policies;
import com.mobiddiction.fishsmart.dao.SignUp;
import com.mobiddiction.fishsmart.dao.NewMap;
import com.mobiddiction.fishsmart.dao.NewMapData;
import com.mobiddiction.fishsmart.dao.NewMapPinsLatLonData;
import com.mobiddiction.fishsmart.dao.NewGuide;
import com.mobiddiction.fishsmart.dao.NewRulesGuide;
import com.mobiddiction.fishsmart.dao.NewListTypeModel;
import com.mobiddiction.fishsmart.dao.NewLicence;
import com.mobiddiction.fishsmart.dao.NewLicenceData;
import com.mobiddiction.fishsmart.dao.TermConditionVersion;
import com.mobiddiction.fishsmart.dao.Gallery;
import com.mobiddiction.fishsmart.dao.GalleryImage;
import com.mobiddiction.fishsmart.dao.Notification;
import com.mobiddiction.fishsmart.dao.NotificationImage;
import com.mobiddiction.fishsmart.dao.NotificationDevice;
import com.mobiddiction.fishsmart.dao.DeviceData;
import com.mobiddiction.fishsmart.dao.UserObj;
import com.mobiddiction.fishsmart.dao.NotificationRoles;
import com.mobiddiction.fishsmart.dao.UserHistory;

import com.mobiddiction.fishsmart.dao.FirstTimeLoadDao;
import com.mobiddiction.fishsmart.dao.DataDao;
import com.mobiddiction.fishsmart.dao.NEWSpeciesDataDao;
import com.mobiddiction.fishsmart.dao.SpeciesDataObjectDao;
import com.mobiddiction.fishsmart.dao.FreshDataNewDao;
import com.mobiddiction.fishsmart.dao.SaltDataNewDao;
import com.mobiddiction.fishsmart.dao.SaltWaterNewDataDao;
import com.mobiddiction.fishsmart.dao.SaltWaterfishGroupDao;
import com.mobiddiction.fishsmart.dao.SaltWaterfishFactsDao;
import com.mobiddiction.fishsmart.dao.SaltWaterrulesDao;
import com.mobiddiction.fishsmart.dao.FreshWaterNewDataDao;
import com.mobiddiction.fishsmart.dao.FreshWaterfishGroupDao;
import com.mobiddiction.fishsmart.dao.FreshWaterfishFactsDao;
import com.mobiddiction.fishsmart.dao.FreshWaterrulesDao;
import com.mobiddiction.fishsmart.dao.FreshDataDao;
import com.mobiddiction.fishsmart.dao.FreshDao;
import com.mobiddiction.fishsmart.dao.SaltDataDao;
import com.mobiddiction.fishsmart.dao.SaltDao;
import com.mobiddiction.fishsmart.dao.AllSpeciesDao;
import com.mobiddiction.fishsmart.dao.AllSpeciesGroupDao;
import com.mobiddiction.fishsmart.dao.AllSpeciesfishFactsDao;
import com.mobiddiction.fishsmart.dao.AllSpeciesrulesDao;
import com.mobiddiction.fishsmart.dao.LoginDetailDao;
import com.mobiddiction.fishsmart.dao.LoginResponseDao;
import com.mobiddiction.fishsmart.dao.OnboardingDao;
import com.mobiddiction.fishsmart.dao.TermAndConditionDao;
import com.mobiddiction.fishsmart.dao.PoliciesDao;
import com.mobiddiction.fishsmart.dao.SignUpDao;
import com.mobiddiction.fishsmart.dao.NewMapDao;
import com.mobiddiction.fishsmart.dao.NewMapDataDao;
import com.mobiddiction.fishsmart.dao.NewMapPinsLatLonDataDao;
import com.mobiddiction.fishsmart.dao.NewGuideDao;
import com.mobiddiction.fishsmart.dao.NewRulesGuideDao;
import com.mobiddiction.fishsmart.dao.NewListTypeModelDao;
import com.mobiddiction.fishsmart.dao.NewLicenceDao;
import com.mobiddiction.fishsmart.dao.NewLicenceDataDao;
import com.mobiddiction.fishsmart.dao.TermConditionVersionDao;
import com.mobiddiction.fishsmart.dao.GalleryDao;
import com.mobiddiction.fishsmart.dao.GalleryImageDao;
import com.mobiddiction.fishsmart.dao.NotificationDao;
import com.mobiddiction.fishsmart.dao.NotificationImageDao;
import com.mobiddiction.fishsmart.dao.NotificationDeviceDao;
import com.mobiddiction.fishsmart.dao.DeviceDataDao;
import com.mobiddiction.fishsmart.dao.UserObjDao;
import com.mobiddiction.fishsmart.dao.NotificationRolesDao;
import com.mobiddiction.fishsmart.dao.UserHistoryDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig firstTimeLoadDaoConfig;
    private final DaoConfig dataDaoConfig;
    private final DaoConfig nEWSpeciesDataDaoConfig;
    private final DaoConfig speciesDataObjectDaoConfig;
    private final DaoConfig freshDataNewDaoConfig;
    private final DaoConfig saltDataNewDaoConfig;
    private final DaoConfig saltWaterNewDataDaoConfig;
    private final DaoConfig saltWaterfishGroupDaoConfig;
    private final DaoConfig saltWaterfishFactsDaoConfig;
    private final DaoConfig saltWaterrulesDaoConfig;
    private final DaoConfig freshWaterNewDataDaoConfig;
    private final DaoConfig freshWaterfishGroupDaoConfig;
    private final DaoConfig freshWaterfishFactsDaoConfig;
    private final DaoConfig freshWaterrulesDaoConfig;
    private final DaoConfig freshDataDaoConfig;
    private final DaoConfig freshDaoConfig;
    private final DaoConfig saltDataDaoConfig;
    private final DaoConfig saltDaoConfig;
    private final DaoConfig allSpeciesDaoConfig;
    private final DaoConfig allSpeciesGroupDaoConfig;
    private final DaoConfig allSpeciesfishFactsDaoConfig;
    private final DaoConfig allSpeciesrulesDaoConfig;
    private final DaoConfig loginDetailDaoConfig;
    private final DaoConfig loginResponseDaoConfig;
    private final DaoConfig onboardingDaoConfig;
    private final DaoConfig termAndConditionDaoConfig;
    private final DaoConfig policiesDaoConfig;
    private final DaoConfig signUpDaoConfig;
    private final DaoConfig newMapDaoConfig;
    private final DaoConfig newMapDataDaoConfig;
    private final DaoConfig newMapPinsLatLonDataDaoConfig;
    private final DaoConfig newGuideDaoConfig;
    private final DaoConfig newRulesGuideDaoConfig;
    private final DaoConfig newListTypeModelDaoConfig;
    private final DaoConfig newLicenceDaoConfig;
    private final DaoConfig newLicenceDataDaoConfig;
    private final DaoConfig termConditionVersionDaoConfig;
    private final DaoConfig galleryDaoConfig;
    private final DaoConfig galleryImageDaoConfig;
    private final DaoConfig notificationDaoConfig;
    private final DaoConfig notificationImageDaoConfig;
    private final DaoConfig notificationDeviceDaoConfig;
    private final DaoConfig deviceDataDaoConfig;
    private final DaoConfig userObjDaoConfig;
    private final DaoConfig notificationRolesDaoConfig;
    private final DaoConfig userHistoryDaoConfig;

    private final FirstTimeLoadDao firstTimeLoadDao;
    private final DataDao dataDao;
    private final NEWSpeciesDataDao nEWSpeciesDataDao;
    private final SpeciesDataObjectDao speciesDataObjectDao;
    private final FreshDataNewDao freshDataNewDao;
    private final SaltDataNewDao saltDataNewDao;
    private final SaltWaterNewDataDao saltWaterNewDataDao;
    private final SaltWaterfishGroupDao saltWaterfishGroupDao;
    private final SaltWaterfishFactsDao saltWaterfishFactsDao;
    private final SaltWaterrulesDao saltWaterrulesDao;
    private final FreshWaterNewDataDao freshWaterNewDataDao;
    private final FreshWaterfishGroupDao freshWaterfishGroupDao;
    private final FreshWaterfishFactsDao freshWaterfishFactsDao;
    private final FreshWaterrulesDao freshWaterrulesDao;
    private final FreshDataDao freshDataDao;
    private final FreshDao freshDao;
    private final SaltDataDao saltDataDao;
    private final SaltDao saltDao;
    private final AllSpeciesDao allSpeciesDao;
    private final AllSpeciesGroupDao allSpeciesGroupDao;
    private final AllSpeciesfishFactsDao allSpeciesfishFactsDao;
    private final AllSpeciesrulesDao allSpeciesrulesDao;
    private final LoginDetailDao loginDetailDao;
    private final LoginResponseDao loginResponseDao;
    private final OnboardingDao onboardingDao;
    private final TermAndConditionDao termAndConditionDao;
    private final PoliciesDao policiesDao;
    private final SignUpDao signUpDao;
    private final NewMapDao newMapDao;
    private final NewMapDataDao newMapDataDao;
    private final NewMapPinsLatLonDataDao newMapPinsLatLonDataDao;
    private final NewGuideDao newGuideDao;
    private final NewRulesGuideDao newRulesGuideDao;
    private final NewListTypeModelDao newListTypeModelDao;
    private final NewLicenceDao newLicenceDao;
    private final NewLicenceDataDao newLicenceDataDao;
    private final TermConditionVersionDao termConditionVersionDao;
    private final GalleryDao galleryDao;
    private final GalleryImageDao galleryImageDao;
    private final NotificationDao notificationDao;
    private final NotificationImageDao notificationImageDao;
    private final NotificationDeviceDao notificationDeviceDao;
    private final DeviceDataDao deviceDataDao;
    private final UserObjDao userObjDao;
    private final NotificationRolesDao notificationRolesDao;
    private final UserHistoryDao userHistoryDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        firstTimeLoadDaoConfig = daoConfigMap.get(FirstTimeLoadDao.class).clone();
        firstTimeLoadDaoConfig.initIdentityScope(type);

        dataDaoConfig = daoConfigMap.get(DataDao.class).clone();
        dataDaoConfig.initIdentityScope(type);

        nEWSpeciesDataDaoConfig = daoConfigMap.get(NEWSpeciesDataDao.class).clone();
        nEWSpeciesDataDaoConfig.initIdentityScope(type);

        speciesDataObjectDaoConfig = daoConfigMap.get(SpeciesDataObjectDao.class).clone();
        speciesDataObjectDaoConfig.initIdentityScope(type);

        freshDataNewDaoConfig = daoConfigMap.get(FreshDataNewDao.class).clone();
        freshDataNewDaoConfig.initIdentityScope(type);

        saltDataNewDaoConfig = daoConfigMap.get(SaltDataNewDao.class).clone();
        saltDataNewDaoConfig.initIdentityScope(type);

        saltWaterNewDataDaoConfig = daoConfigMap.get(SaltWaterNewDataDao.class).clone();
        saltWaterNewDataDaoConfig.initIdentityScope(type);

        saltWaterfishGroupDaoConfig = daoConfigMap.get(SaltWaterfishGroupDao.class).clone();
        saltWaterfishGroupDaoConfig.initIdentityScope(type);

        saltWaterfishFactsDaoConfig = daoConfigMap.get(SaltWaterfishFactsDao.class).clone();
        saltWaterfishFactsDaoConfig.initIdentityScope(type);

        saltWaterrulesDaoConfig = daoConfigMap.get(SaltWaterrulesDao.class).clone();
        saltWaterrulesDaoConfig.initIdentityScope(type);

        freshWaterNewDataDaoConfig = daoConfigMap.get(FreshWaterNewDataDao.class).clone();
        freshWaterNewDataDaoConfig.initIdentityScope(type);

        freshWaterfishGroupDaoConfig = daoConfigMap.get(FreshWaterfishGroupDao.class).clone();
        freshWaterfishGroupDaoConfig.initIdentityScope(type);

        freshWaterfishFactsDaoConfig = daoConfigMap.get(FreshWaterfishFactsDao.class).clone();
        freshWaterfishFactsDaoConfig.initIdentityScope(type);

        freshWaterrulesDaoConfig = daoConfigMap.get(FreshWaterrulesDao.class).clone();
        freshWaterrulesDaoConfig.initIdentityScope(type);

        freshDataDaoConfig = daoConfigMap.get(FreshDataDao.class).clone();
        freshDataDaoConfig.initIdentityScope(type);

        freshDaoConfig = daoConfigMap.get(FreshDao.class).clone();
        freshDaoConfig.initIdentityScope(type);

        saltDataDaoConfig = daoConfigMap.get(SaltDataDao.class).clone();
        saltDataDaoConfig.initIdentityScope(type);

        saltDaoConfig = daoConfigMap.get(SaltDao.class).clone();
        saltDaoConfig.initIdentityScope(type);

        allSpeciesDaoConfig = daoConfigMap.get(AllSpeciesDao.class).clone();
        allSpeciesDaoConfig.initIdentityScope(type);

        allSpeciesGroupDaoConfig = daoConfigMap.get(AllSpeciesGroupDao.class).clone();
        allSpeciesGroupDaoConfig.initIdentityScope(type);

        allSpeciesfishFactsDaoConfig = daoConfigMap.get(AllSpeciesfishFactsDao.class).clone();
        allSpeciesfishFactsDaoConfig.initIdentityScope(type);

        allSpeciesrulesDaoConfig = daoConfigMap.get(AllSpeciesrulesDao.class).clone();
        allSpeciesrulesDaoConfig.initIdentityScope(type);

        loginDetailDaoConfig = daoConfigMap.get(LoginDetailDao.class).clone();
        loginDetailDaoConfig.initIdentityScope(type);

        loginResponseDaoConfig = daoConfigMap.get(LoginResponseDao.class).clone();
        loginResponseDaoConfig.initIdentityScope(type);

        onboardingDaoConfig = daoConfigMap.get(OnboardingDao.class).clone();
        onboardingDaoConfig.initIdentityScope(type);

        termAndConditionDaoConfig = daoConfigMap.get(TermAndConditionDao.class).clone();
        termAndConditionDaoConfig.initIdentityScope(type);

        policiesDaoConfig = daoConfigMap.get(PoliciesDao.class).clone();
        policiesDaoConfig.initIdentityScope(type);

        signUpDaoConfig = daoConfigMap.get(SignUpDao.class).clone();
        signUpDaoConfig.initIdentityScope(type);

        newMapDaoConfig = daoConfigMap.get(NewMapDao.class).clone();
        newMapDaoConfig.initIdentityScope(type);

        newMapDataDaoConfig = daoConfigMap.get(NewMapDataDao.class).clone();
        newMapDataDaoConfig.initIdentityScope(type);

        newMapPinsLatLonDataDaoConfig = daoConfigMap.get(NewMapPinsLatLonDataDao.class).clone();
        newMapPinsLatLonDataDaoConfig.initIdentityScope(type);

        newGuideDaoConfig = daoConfigMap.get(NewGuideDao.class).clone();
        newGuideDaoConfig.initIdentityScope(type);

        newRulesGuideDaoConfig = daoConfigMap.get(NewRulesGuideDao.class).clone();
        newRulesGuideDaoConfig.initIdentityScope(type);

        newListTypeModelDaoConfig = daoConfigMap.get(NewListTypeModelDao.class).clone();
        newListTypeModelDaoConfig.initIdentityScope(type);

        newLicenceDaoConfig = daoConfigMap.get(NewLicenceDao.class).clone();
        newLicenceDaoConfig.initIdentityScope(type);

        newLicenceDataDaoConfig = daoConfigMap.get(NewLicenceDataDao.class).clone();
        newLicenceDataDaoConfig.initIdentityScope(type);

        termConditionVersionDaoConfig = daoConfigMap.get(TermConditionVersionDao.class).clone();
        termConditionVersionDaoConfig.initIdentityScope(type);

        galleryDaoConfig = daoConfigMap.get(GalleryDao.class).clone();
        galleryDaoConfig.initIdentityScope(type);

        galleryImageDaoConfig = daoConfigMap.get(GalleryImageDao.class).clone();
        galleryImageDaoConfig.initIdentityScope(type);

        notificationDaoConfig = daoConfigMap.get(NotificationDao.class).clone();
        notificationDaoConfig.initIdentityScope(type);

        notificationImageDaoConfig = daoConfigMap.get(NotificationImageDao.class).clone();
        notificationImageDaoConfig.initIdentityScope(type);

        notificationDeviceDaoConfig = daoConfigMap.get(NotificationDeviceDao.class).clone();
        notificationDeviceDaoConfig.initIdentityScope(type);

        deviceDataDaoConfig = daoConfigMap.get(DeviceDataDao.class).clone();
        deviceDataDaoConfig.initIdentityScope(type);

        userObjDaoConfig = daoConfigMap.get(UserObjDao.class).clone();
        userObjDaoConfig.initIdentityScope(type);

        notificationRolesDaoConfig = daoConfigMap.get(NotificationRolesDao.class).clone();
        notificationRolesDaoConfig.initIdentityScope(type);

        userHistoryDaoConfig = daoConfigMap.get(UserHistoryDao.class).clone();
        userHistoryDaoConfig.initIdentityScope(type);

        firstTimeLoadDao = new FirstTimeLoadDao(firstTimeLoadDaoConfig, this);
        dataDao = new DataDao(dataDaoConfig, this);
        nEWSpeciesDataDao = new NEWSpeciesDataDao(nEWSpeciesDataDaoConfig, this);
        speciesDataObjectDao = new SpeciesDataObjectDao(speciesDataObjectDaoConfig, this);
        freshDataNewDao = new FreshDataNewDao(freshDataNewDaoConfig, this);
        saltDataNewDao = new SaltDataNewDao(saltDataNewDaoConfig, this);
        saltWaterNewDataDao = new SaltWaterNewDataDao(saltWaterNewDataDaoConfig, this);
        saltWaterfishGroupDao = new SaltWaterfishGroupDao(saltWaterfishGroupDaoConfig, this);
        saltWaterfishFactsDao = new SaltWaterfishFactsDao(saltWaterfishFactsDaoConfig, this);
        saltWaterrulesDao = new SaltWaterrulesDao(saltWaterrulesDaoConfig, this);
        freshWaterNewDataDao = new FreshWaterNewDataDao(freshWaterNewDataDaoConfig, this);
        freshWaterfishGroupDao = new FreshWaterfishGroupDao(freshWaterfishGroupDaoConfig, this);
        freshWaterfishFactsDao = new FreshWaterfishFactsDao(freshWaterfishFactsDaoConfig, this);
        freshWaterrulesDao = new FreshWaterrulesDao(freshWaterrulesDaoConfig, this);
        freshDataDao = new FreshDataDao(freshDataDaoConfig, this);
        freshDao = new FreshDao(freshDaoConfig, this);
        saltDataDao = new SaltDataDao(saltDataDaoConfig, this);
        saltDao = new SaltDao(saltDaoConfig, this);
        allSpeciesDao = new AllSpeciesDao(allSpeciesDaoConfig, this);
        allSpeciesGroupDao = new AllSpeciesGroupDao(allSpeciesGroupDaoConfig, this);
        allSpeciesfishFactsDao = new AllSpeciesfishFactsDao(allSpeciesfishFactsDaoConfig, this);
        allSpeciesrulesDao = new AllSpeciesrulesDao(allSpeciesrulesDaoConfig, this);
        loginDetailDao = new LoginDetailDao(loginDetailDaoConfig, this);
        loginResponseDao = new LoginResponseDao(loginResponseDaoConfig, this);
        onboardingDao = new OnboardingDao(onboardingDaoConfig, this);
        termAndConditionDao = new TermAndConditionDao(termAndConditionDaoConfig, this);
        policiesDao = new PoliciesDao(policiesDaoConfig, this);
        signUpDao = new SignUpDao(signUpDaoConfig, this);
        newMapDao = new NewMapDao(newMapDaoConfig, this);
        newMapDataDao = new NewMapDataDao(newMapDataDaoConfig, this);
        newMapPinsLatLonDataDao = new NewMapPinsLatLonDataDao(newMapPinsLatLonDataDaoConfig, this);
        newGuideDao = new NewGuideDao(newGuideDaoConfig, this);
        newRulesGuideDao = new NewRulesGuideDao(newRulesGuideDaoConfig, this);
        newListTypeModelDao = new NewListTypeModelDao(newListTypeModelDaoConfig, this);
        newLicenceDao = new NewLicenceDao(newLicenceDaoConfig, this);
        newLicenceDataDao = new NewLicenceDataDao(newLicenceDataDaoConfig, this);
        termConditionVersionDao = new TermConditionVersionDao(termConditionVersionDaoConfig, this);
        galleryDao = new GalleryDao(galleryDaoConfig, this);
        galleryImageDao = new GalleryImageDao(galleryImageDaoConfig, this);
        notificationDao = new NotificationDao(notificationDaoConfig, this);
        notificationImageDao = new NotificationImageDao(notificationImageDaoConfig, this);
        notificationDeviceDao = new NotificationDeviceDao(notificationDeviceDaoConfig, this);
        deviceDataDao = new DeviceDataDao(deviceDataDaoConfig, this);
        userObjDao = new UserObjDao(userObjDaoConfig, this);
        notificationRolesDao = new NotificationRolesDao(notificationRolesDaoConfig, this);
        userHistoryDao = new UserHistoryDao(userHistoryDaoConfig, this);

        registerDao(FirstTimeLoad.class, firstTimeLoadDao);
        registerDao(Data.class, dataDao);
        registerDao(NEWSpeciesData.class, nEWSpeciesDataDao);
        registerDao(SpeciesDataObject.class, speciesDataObjectDao);
        registerDao(FreshDataNew.class, freshDataNewDao);
        registerDao(SaltDataNew.class, saltDataNewDao);
        registerDao(SaltWaterNewData.class, saltWaterNewDataDao);
        registerDao(SaltWaterfishGroup.class, saltWaterfishGroupDao);
        registerDao(SaltWaterfishFacts.class, saltWaterfishFactsDao);
        registerDao(SaltWaterrules.class, saltWaterrulesDao);
        registerDao(FreshWaterNewData.class, freshWaterNewDataDao);
        registerDao(FreshWaterfishGroup.class, freshWaterfishGroupDao);
        registerDao(FreshWaterfishFacts.class, freshWaterfishFactsDao);
        registerDao(FreshWaterrules.class, freshWaterrulesDao);
        registerDao(FreshData.class, freshDataDao);
        registerDao(Fresh.class, freshDao);
        registerDao(SaltData.class, saltDataDao);
        registerDao(Salt.class, saltDao);
        registerDao(AllSpecies.class, allSpeciesDao);
        registerDao(AllSpeciesGroup.class, allSpeciesGroupDao);
        registerDao(AllSpeciesfishFacts.class, allSpeciesfishFactsDao);
        registerDao(AllSpeciesrules.class, allSpeciesrulesDao);
        registerDao(LoginDetail.class, loginDetailDao);
        registerDao(LoginResponse.class, loginResponseDao);
        registerDao(Onboarding.class, onboardingDao);
        registerDao(TermAndCondition.class, termAndConditionDao);
        registerDao(Policies.class, policiesDao);
        registerDao(SignUp.class, signUpDao);
        registerDao(NewMap.class, newMapDao);
        registerDao(NewMapData.class, newMapDataDao);
        registerDao(NewMapPinsLatLonData.class, newMapPinsLatLonDataDao);
        registerDao(NewGuide.class, newGuideDao);
        registerDao(NewRulesGuide.class, newRulesGuideDao);
        registerDao(NewListTypeModel.class, newListTypeModelDao);
        registerDao(NewLicence.class, newLicenceDao);
        registerDao(NewLicenceData.class, newLicenceDataDao);
        registerDao(TermConditionVersion.class, termConditionVersionDao);
        registerDao(Gallery.class, galleryDao);
        registerDao(GalleryImage.class, galleryImageDao);
        registerDao(Notification.class, notificationDao);
        registerDao(NotificationImage.class, notificationImageDao);
        registerDao(NotificationDevice.class, notificationDeviceDao);
        registerDao(DeviceData.class, deviceDataDao);
        registerDao(UserObj.class, userObjDao);
        registerDao(NotificationRoles.class, notificationRolesDao);
        registerDao(UserHistory.class, userHistoryDao);
    }
    
    public void clear() {
        firstTimeLoadDaoConfig.getIdentityScope().clear();
        dataDaoConfig.getIdentityScope().clear();
        nEWSpeciesDataDaoConfig.getIdentityScope().clear();
        speciesDataObjectDaoConfig.getIdentityScope().clear();
        freshDataNewDaoConfig.getIdentityScope().clear();
        saltDataNewDaoConfig.getIdentityScope().clear();
        saltWaterNewDataDaoConfig.getIdentityScope().clear();
        saltWaterfishGroupDaoConfig.getIdentityScope().clear();
        saltWaterfishFactsDaoConfig.getIdentityScope().clear();
        saltWaterrulesDaoConfig.getIdentityScope().clear();
        freshWaterNewDataDaoConfig.getIdentityScope().clear();
        freshWaterfishGroupDaoConfig.getIdentityScope().clear();
        freshWaterfishFactsDaoConfig.getIdentityScope().clear();
        freshWaterrulesDaoConfig.getIdentityScope().clear();
        freshDataDaoConfig.getIdentityScope().clear();
        freshDaoConfig.getIdentityScope().clear();
        saltDataDaoConfig.getIdentityScope().clear();
        saltDaoConfig.getIdentityScope().clear();
        allSpeciesDaoConfig.getIdentityScope().clear();
        allSpeciesGroupDaoConfig.getIdentityScope().clear();
        allSpeciesfishFactsDaoConfig.getIdentityScope().clear();
        allSpeciesrulesDaoConfig.getIdentityScope().clear();
        loginDetailDaoConfig.getIdentityScope().clear();
        loginResponseDaoConfig.getIdentityScope().clear();
        onboardingDaoConfig.getIdentityScope().clear();
        termAndConditionDaoConfig.getIdentityScope().clear();
        policiesDaoConfig.getIdentityScope().clear();
        signUpDaoConfig.getIdentityScope().clear();
        newMapDaoConfig.getIdentityScope().clear();
        newMapDataDaoConfig.getIdentityScope().clear();
        newMapPinsLatLonDataDaoConfig.getIdentityScope().clear();
        newGuideDaoConfig.getIdentityScope().clear();
        newRulesGuideDaoConfig.getIdentityScope().clear();
        newListTypeModelDaoConfig.getIdentityScope().clear();
        newLicenceDaoConfig.getIdentityScope().clear();
        newLicenceDataDaoConfig.getIdentityScope().clear();
        termConditionVersionDaoConfig.getIdentityScope().clear();
        galleryDaoConfig.getIdentityScope().clear();
        galleryImageDaoConfig.getIdentityScope().clear();
        notificationDaoConfig.getIdentityScope().clear();
        notificationImageDaoConfig.getIdentityScope().clear();
        notificationDeviceDaoConfig.getIdentityScope().clear();
        deviceDataDaoConfig.getIdentityScope().clear();
        userObjDaoConfig.getIdentityScope().clear();
        notificationRolesDaoConfig.getIdentityScope().clear();
        userHistoryDaoConfig.getIdentityScope().clear();
    }

    public FirstTimeLoadDao getFirstTimeLoadDao() {
        return firstTimeLoadDao;
    }

    public DataDao getDataDao() {
        return dataDao;
    }

    public NEWSpeciesDataDao getNEWSpeciesDataDao() {
        return nEWSpeciesDataDao;
    }

    public SpeciesDataObjectDao getSpeciesDataObjectDao() {
        return speciesDataObjectDao;
    }

    public FreshDataNewDao getFreshDataNewDao() {
        return freshDataNewDao;
    }

    public SaltDataNewDao getSaltDataNewDao() {
        return saltDataNewDao;
    }

    public SaltWaterNewDataDao getSaltWaterNewDataDao() {
        return saltWaterNewDataDao;
    }

    public SaltWaterfishGroupDao getSaltWaterfishGroupDao() {
        return saltWaterfishGroupDao;
    }

    public SaltWaterfishFactsDao getSaltWaterfishFactsDao() {
        return saltWaterfishFactsDao;
    }

    public SaltWaterrulesDao getSaltWaterrulesDao() {
        return saltWaterrulesDao;
    }

    public FreshWaterNewDataDao getFreshWaterNewDataDao() {
        return freshWaterNewDataDao;
    }

    public FreshWaterfishGroupDao getFreshWaterfishGroupDao() {
        return freshWaterfishGroupDao;
    }

    public FreshWaterfishFactsDao getFreshWaterfishFactsDao() {
        return freshWaterfishFactsDao;
    }

    public FreshWaterrulesDao getFreshWaterrulesDao() {
        return freshWaterrulesDao;
    }

    public FreshDataDao getFreshDataDao() {
        return freshDataDao;
    }

    public FreshDao getFreshDao() {
        return freshDao;
    }

    public SaltDataDao getSaltDataDao() {
        return saltDataDao;
    }

    public SaltDao getSaltDao() {
        return saltDao;
    }

    public AllSpeciesDao getAllSpeciesDao() {
        return allSpeciesDao;
    }

    public AllSpeciesGroupDao getAllSpeciesGroupDao() {
        return allSpeciesGroupDao;
    }

    public AllSpeciesfishFactsDao getAllSpeciesfishFactsDao() {
        return allSpeciesfishFactsDao;
    }

    public AllSpeciesrulesDao getAllSpeciesrulesDao() {
        return allSpeciesrulesDao;
    }

    public LoginDetailDao getLoginDetailDao() {
        return loginDetailDao;
    }

    public LoginResponseDao getLoginResponseDao() {
        return loginResponseDao;
    }

    public OnboardingDao getOnboardingDao() {
        return onboardingDao;
    }

    public TermAndConditionDao getTermAndConditionDao() {
        return termAndConditionDao;
    }

    public PoliciesDao getPoliciesDao() {
        return policiesDao;
    }

    public SignUpDao getSignUpDao() {
        return signUpDao;
    }

    public NewMapDao getNewMapDao() {
        return newMapDao;
    }

    public NewMapDataDao getNewMapDataDao() {
        return newMapDataDao;
    }

    public NewMapPinsLatLonDataDao getNewMapPinsLatLonDataDao() {
        return newMapPinsLatLonDataDao;
    }

    public NewGuideDao getNewGuideDao() {
        return newGuideDao;
    }

    public NewRulesGuideDao getNewRulesGuideDao() {
        return newRulesGuideDao;
    }

    public NewListTypeModelDao getNewListTypeModelDao() {
        return newListTypeModelDao;
    }

    public NewLicenceDao getNewLicenceDao() {
        return newLicenceDao;
    }

    public NewLicenceDataDao getNewLicenceDataDao() {
        return newLicenceDataDao;
    }

    public TermConditionVersionDao getTermConditionVersionDao() {
        return termConditionVersionDao;
    }

    public GalleryDao getGalleryDao() {
        return galleryDao;
    }

    public GalleryImageDao getGalleryImageDao() {
        return galleryImageDao;
    }

    public NotificationDao getNotificationDao() {
        return notificationDao;
    }

    public NotificationImageDao getNotificationImageDao() {
        return notificationImageDao;
    }

    public NotificationDeviceDao getNotificationDeviceDao() {
        return notificationDeviceDao;
    }

    public DeviceDataDao getDeviceDataDao() {
        return deviceDataDao;
    }

    public UserObjDao getUserObjDao() {
        return userObjDao;
    }

    public NotificationRolesDao getNotificationRolesDao() {
        return notificationRolesDao;
    }

    public UserHistoryDao getUserHistoryDao() {
        return userHistoryDao;
    }

}
