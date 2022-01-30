package com.mobiddiction.fishsmart.Network;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.mobiddiction.fishsmart.dao.AllSpeciesDao;
import com.mobiddiction.fishsmart.dao.AllSpeciesGroupDao;
import com.mobiddiction.fishsmart.dao.AllSpeciesfishFactsDao;
import com.mobiddiction.fishsmart.dao.AllSpeciesrulesDao;
import com.mobiddiction.fishsmart.dao.DataDao;
import com.mobiddiction.fishsmart.dao.FirstTimeLoadDao;
import com.mobiddiction.fishsmart.dao.FreshDao;
import com.mobiddiction.fishsmart.dao.FreshDataDao;
import com.mobiddiction.fishsmart.dao.FreshDataNewDao;
import com.mobiddiction.fishsmart.dao.FreshWaterNewDataDao;
import com.mobiddiction.fishsmart.dao.FreshWaterfishFactsDao;
import com.mobiddiction.fishsmart.dao.FreshWaterfishGroupDao;
import com.mobiddiction.fishsmart.dao.FreshWaterrulesDao;
import com.mobiddiction.fishsmart.dao.GalleryDao;
import com.mobiddiction.fishsmart.dao.GalleryImageDao;
import com.mobiddiction.fishsmart.dao.LoginDetailDao;
import com.mobiddiction.fishsmart.dao.LoginResponseDao;
import com.mobiddiction.fishsmart.dao.NEWSpeciesDataDao;
import com.mobiddiction.fishsmart.dao.NewGuideDao;
import com.mobiddiction.fishsmart.dao.NewLicenceDao;
import com.mobiddiction.fishsmart.dao.NewLicenceDataDao;
import com.mobiddiction.fishsmart.dao.NewListTypeModelDao;
import com.mobiddiction.fishsmart.dao.NewMapDao;
import com.mobiddiction.fishsmart.dao.NewMapDataDao;
import com.mobiddiction.fishsmart.dao.NewMapPinsLatLonDataDao;
import com.mobiddiction.fishsmart.dao.NewRulesGuideDao;
import com.mobiddiction.fishsmart.dao.OnboardingDao;
import com.mobiddiction.fishsmart.dao.PoliciesDao;
import com.mobiddiction.fishsmart.dao.SaltDao;
import com.mobiddiction.fishsmart.dao.SaltDataDao;
import com.mobiddiction.fishsmart.dao.SaltDataNewDao;
import com.mobiddiction.fishsmart.dao.SaltWaterNewDataDao;
import com.mobiddiction.fishsmart.dao.SaltWaterfishFactsDao;
import com.mobiddiction.fishsmart.dao.SaltWaterfishGroupDao;
import com.mobiddiction.fishsmart.dao.SaltWaterrulesDao;
import com.mobiddiction.fishsmart.dao.SignUpDao;
import com.mobiddiction.fishsmart.dao.SpeciesDataObjectDao;
import com.mobiddiction.fishsmart.dao.TermAndConditionDao;
import com.mobiddiction.fishsmart.dao.TermConditionVersionDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by andrewi on 12/08/2016.
 */

public final class MigrationHelper {
    public static boolean DEBUG = true;
    private static String TAG = "MigrationHelper";
    private static MigrationHelper instance;

    public MigrationHelper() {
    }

    public static MigrationHelper getInstance() {
        if(instance == null) {
            instance = new MigrationHelper();
        }
        return instance;
    }

    public void migrateRestDb(SQLiteDatabase db) {
        MigrationHelper.getInstance().migrate(db,
                SaltDataNewDao.class,
                FreshDataNewDao.class,
                DataDao.class,
                FirstTimeLoadDao.class,
                NEWSpeciesDataDao.class,
                SpeciesDataObjectDao.class,
                SaltWaterNewDataDao.class,
                SaltWaterfishGroupDao.class,
                SaltWaterfishFactsDao.class,
                SaltWaterrulesDao.class,
                FreshWaterNewDataDao.class,
                FreshWaterfishGroupDao.class,
                FreshWaterfishFactsDao.class,
                FreshWaterrulesDao.class,
                FreshDataDao.class,
                FreshDao.class,
                SaltDataDao.class,
                SaltDao.class,
                AllSpeciesDao.class,
                AllSpeciesGroupDao.class,
                AllSpeciesfishFactsDao.class,
                AllSpeciesrulesDao.class,
                OnboardingDao.class,
                LoginResponseDao.class,
                LoginDetailDao.class,
                TermAndConditionDao.class,
                SignUpDao.class,
                NewMapDao.class,
                NewMapDataDao.class,
                NewMapPinsLatLonDataDao.class,
                NewGuideDao.class,
                NewRulesGuideDao.class,
                NewListTypeModelDao.class,
                NewLicenceDao.class,
                NewLicenceDataDao.class,
                PoliciesDao.class,
                TermConditionVersionDao.class,
                GalleryDao.class,
                GalleryImageDao.class);
    }
    public static void migrate(SQLiteDatabase db, Class... daoClasses) {
        Database database = new StandardDatabase(db);
        generateTempTables(database, daoClasses);
        dropAllTables(db, true, daoClasses);
        createAllTables(db, false, daoClasses);
        restoreData(db, daoClasses);
    }

    private static void generateTempTables(Database db, Class... daoClasses) {
        for(int i = 0; i < daoClasses.length; ++i) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            StringBuilder insertTableStringBuilder = new StringBuilder();
            insertTableStringBuilder.append("CREATE TEMPORARY TABLE ").append(tempTableName);
            insertTableStringBuilder.append(" AS SELECT * FROM ").append(tableName).append(";");
            try {
                db.execSQL(insertTableStringBuilder.toString());
                if (DEBUG) {
                    Log.d(TAG, "the table " + tableName + " columns are " + getColumnsStr(daoConfig));
                    Log.d(TAG, "generate temp table " + tempTableName);
                }
            }catch(Exception e){

            }
        }

    }

    private static String getColumnsStr(DaoConfig daoConfig) {
        if(daoConfig == null) {
            return "no columns";
        } else {
            StringBuilder builder = new StringBuilder();

            for(int i = 0; i < daoConfig.allColumns.length; ++i) {
                builder.append(daoConfig.allColumns[i]);
                builder.append(",");
            }

            if(builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }

            return builder.toString();
        }
    }

    private static void dropAllTables(SQLiteDatabase db, boolean ifExists, @NonNull Class... daoClasses) {
        reflectMethod(db, "dropTable", ifExists, daoClasses);
        if(DEBUG) {
            Log.d(TAG, "drop all table");
        }

    }

    private static void createAllTables(SQLiteDatabase db, boolean ifNotExists, @NonNull Class... daoClasses) {
        reflectMethod(db, "createTable", ifNotExists, daoClasses);
        if(DEBUG) {
            Log.d(TAG, "create all table");
        }

    }

    private static void reflectMethod(SQLiteDatabase db, String methodName, boolean isExists, @NonNull Class... daoClasses) {
        if(daoClasses.length >= 1) {
            try {
                Class[] e = daoClasses;
                int var5 = daoClasses.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    Class cls = e[var6];
                    Method method = cls.getDeclaredMethod(methodName, SQLiteDatabase.class, Boolean.TYPE);
                    method.invoke(null, db, Boolean.valueOf(isExists));
                }
            } catch (NoSuchMethodException var9) {
                var9.printStackTrace();
            } catch (InvocationTargetException var10) {
                var10.printStackTrace();
            } catch (IllegalAccessException var11) {
                var11.printStackTrace();
            }

        }
    }

    private static void restoreData(SQLiteDatabase db, Class... daoClasses) {
        for(int i = 0; i < daoClasses.length; ++i) {
            Database database = new StandardDatabase(db);
            DaoConfig daoConfig = new DaoConfig(database, daoClasses[i]);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            List columns = getColumns(db, tempTableName);
            ArrayList properties = new ArrayList(columns.size());

            for(int dropTableStringBuilder = 0; dropTableStringBuilder < daoConfig.properties.length; ++dropTableStringBuilder) {
                String insertTableStringBuilder = daoConfig.properties[dropTableStringBuilder].columnName;
                if(columns.contains(insertTableStringBuilder)) {
                    properties.add(insertTableStringBuilder);
                }
            }

            if(properties.size() > 0) {
                String var10 = TextUtils.join(",", properties);
                StringBuilder var12 = new StringBuilder();
                var12.append("INSERT INTO ").append(tableName).append(" (");
                var12.append(var10);
                var12.append(") SELECT ");
                var12.append(var10);
                var12.append(" FROM ").append(tempTableName).append(";");
                db.execSQL(var12.toString());
                if(DEBUG) {
                    Log.d(TAG, "restore data to " + tableName);
                    Log.d(TAG, "the table " + tableName + " columns are " + getColumnsStr(daoConfig));
                }
            }

            StringBuilder var11 = new StringBuilder();
            var11.append("DROP TABLE ").append(tempTableName);
            try {
                db.execSQL(var11.toString());
                if (DEBUG) {
                    Log.d(TAG, "the table " + tableName + " columns are " + getColumnsStr(daoConfig));
                    Log.d(TAG, "generate temp table " + tempTableName);
                }
            }catch(Exception e){

            }
        }

    }

    private static List<String> getColumns(SQLiteDatabase db, String tableName) {
        Object columns = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null);
            if(null != cursor && cursor.getColumnCount() > 0) {
                columns = Arrays.asList(cursor.getColumnNames());
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }

            if(null == columns) {
                columns = new ArrayList();
            }

        }

        return (List)columns;
    }
}


//public class MigrationHelper {
//    private static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION = "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS";
//    private static MigrationHelper instance;
//
//    public static MigrationHelper getInstance() {
//        if(instance == null) {
//            instance = new MigrationHelper();
//        }
//        return instance;
//    }
//
//    public void migrateRestDb(SQLiteDatabase db) {
//
//        MigrationHelper.getInstance().migrate(db,
//                MemberUDIDDao.class,
//                UserLoginDao.class,
//                TokensDao.class,
//                MembersResponseDataDao.class,
//                MembersResponseDao.class,
//                DataDao.class,
//                ResourceDao.class,
//                UserAddressDao.class,
//                InvestmentBalanceResultDataDao.class,
//                InvestmentBalanceResultDao.class,
//                BalanceInfoDao.class,
//                CountryDataDao.class,
//                CountryDao.class,
//                SuburbDataDao.class,
//                SuburbDao.class,
//                StateDao.class,
//                CountrySuburbDao.class,
//                InvestmentDataDao.class,
//                InvestmentDao.class,
//                InvestmentOptionDao.class,
//                TransactionDataDao.class,
//                AccountsDao.class,
//                TransactionInvestmentDao.class,
//                TaxComponentDao.class,
//                BenefitComponentDao.class,
//                ReportableContributionsDao.class,
//                PensionComponentsDao.class,
//                ItemDao.class,
//                BeneficiariesDataDao.class,
//                BeneficiariesDao.class,
//                MemberBeneficiaryDetailsDao.class,
//                MemberBeneficiaryDetailsAddressDao.class,
//                RelationshipDataDao.class,
//                RelationshipDao.class,
//                InsuranceCoversResultDataDao.class,
//                InsuranceCoversResultDao.class,
//                NotificationAlertDataDao.class,
//                NotificationAlertDao.class,
//                NotificationImageDao.class,
//                DashboardKentikoDataDao.class,
//                DashboardCMSDocumentDao.class,
//                DashboardCMSMenuItemDao.class,
//                KentikoTCDataDao.class,
//                CMSDocumentDao.class,
//                CMSMenuItemDao.class,
//                KentikoFSGDataDao.class,
//                KentikoFSGDocumentDao.class,
//                FSGCMSMenuItemDao.class,
//                KentikoPDSDataDao.class,
//                PDSCMSDocumentDao.class,
//                PDSCMSMenuItemDao.class,
//                KentikoSuperBalanceDataDao.class,
//                SuperBalanceCMSDocumentDao.class,
//                SuperBalanceCMSMenuItemDao.class,
//                KentikoDisclaimerViewDataDao.class,
//                DisclaimerViewCMSDocumentDao.class,
//                DisclaimerViewCMSMenuItemDao.class,
//                KentikoDisclaimerEditDataDao.class,
//                DisclaimerEditCMSDocumentDao.class,
//                DisclaimerEditCMSMenuItemDao.class,
//                KentikoSuperDetailsDataDao.class,
//                SuperDetailsCMSDocumentDao.class,
//                SuperDetailsCMSMenuItemDao.class,
//                GenericDisclaimerDetailsDataDao.class,
//                GenericDisclaimerCMSDocumentDao.class,
//                GenericDisclaimerCMSMenuItemDao.class,
//                GenericAdditionalDisclaimerDetailsDataDao.class,
//                GenericAdditionalDisclaimerCMSDocumentDao.class,
//                GenericAdditionalDisclaimerCMSMenuItemDao.class,
//                FirstTimeRegisteredDetailsDataDao.class,
//                FirstTimeRegisteredCMSDocumentDao.class,
//                FirstTimeRegisteredCMSMenuItemDao.class,
//                WelcomeBackDetailsDataDao.class,
//                WelcomeBackCMSDocumentDao.class,
//                WelcomeBackCMSMenuItemDao.class,
//                AccountDetailUpdateTFNDataDao.class,
//                AccountDetailUpdateTFNCMSDocumentDao.class,
//                AccountDetailUpdateTFNCMSMenuItemDao.class,
//                ConsolidateSuperDataDao.class,
//                ConsolidateSuperCMSDocumentDao.class,
//                ConsolidateSuperMenuItemDao.class,
//                Non_Registered_DataDao.class,
//                Non_Registered_DocumentDao.class,
//                Non_RegisteredDao.class,
//                Burger_Tools_DataDao.class,
//                Burger_Tools_DocumentDao.class,
//                Burger_ToolsDao.class,
//                Burger_Forms_DataDao.class,
//                Burger_Forms_DocumentDao.class,
//                Burger_FormsDao.class,
//                Burger_About_DataDao.class,
//                Burger_About_DocumentDao.class,
//                Burger_AboutDao.class,
//                PDS_REST_CORPORATE_SUPER_DataDao.class,
//                PDS_REST_CORPORATE_SUPER_DocumentDao.class,
//                PDS_REST_CORPORATE_SUPERDao.class,
//                PDS_REST_SELECT_DataDao.class,
//                PDS_REST_SELECT_DocumentDao.class,
//                PDS_REST_SELECTDao.class,
//                PDS_REST_PENSION_DataDao.class,
//                PDS_REST_PENSION_DocumentDao.class,
//                PDS_REST_PENSIONDao.class,
//                Burger_Contact_DataDao.class,
//                Burger_Contact_DocumentDao.class,
//                Burger_ContactDao.class,
//                StatementDocumentDataDao.class,
//                StatementDocumentDao.class,
//                StatementDao.class,
//                BPAYMemberDataDao.class,
//                BPAYMemberContributionDao.class,
//                BPAYMemberDao.class,
//                SingleNotificationDataDao.class,
//                SingleNotificationDao.class,
//                UserTitleDataDao.class,
//                UserTitleDao.class,
//                QuizDao.class,
//                AccountTypeDataDao.class,
//                AccountTypeDao.class,
//                TransactionTypeDataDao.class,
//                TransactionTypeDao.class,
//                TransactionResourcesDao.class,
//                MemberTransactionDataDao.class,
//                TransactionAccountDao.class,
//                AppPinDao.class,
//                EncryptAvokaTakeRestDao.class,
//                LaunchWakeDataDao.class,
//                LaunchWakeSettingsDao.class,
//                LaunchWakeErrorMessageDao.class,
//                LaunchWakeDatasDao.class);
//    }
//
//
//    public void migrate(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
//        generateTempTables(db, daoClasses);
//        dropAllTables(db, true,daoClasses);
//        createAllTables(db, false,daoClasses);
//        restoreData(db, daoClasses);
//    }
//
//    private void generateTempTables(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
//        for(int i = 0; i < daoClasses.length; i++) {
//            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
//
//            String divider = "";
//            String tableName = daoConfig.tablename;
//            String tempTableName = daoConfig.tablename.concat("_TEMP");
//            ArrayList<String> properties = new ArrayList<String>();
//
//            StringBuilder createTableStringBuilder = new StringBuilder();
//
//            createTableStringBuilder.append("CREATE TABLE ").append(tempTableName).append(" (");
//
//            for(int j = 0; j < daoConfig.properties.length; j++) {
//                String columnName = daoConfig.properties[j].columnName;
//
//                if(getColumns(db, tableName).contains(columnName)) {
//                    properties.add(columnName);
//
//                    String type = null;
//
//                    try {
//                        type = getTypeByClass(daoConfig.properties[j].type);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    createTableStringBuilder.append(divider).append(columnName).append(" ").append(type);
//
//                    if(daoConfig.properties[j].primaryKey) {
//                        createTableStringBuilder.append(" PRIMARY KEY");
//                    }
//
//                    divider = ",";
//                }
//            }
//            createTableStringBuilder.append(");");
//            if(createTableStringBuilder.toString().contains(" ();")){
//                continue;
//            }
//
//            db.execSQL(createTableStringBuilder.toString());
//
//            StringBuilder insertTableStringBuilder = new StringBuilder();
//
//            insertTableStringBuilder.append("INSERT INTO ").append(tempTableName).append(" (");
//            insertTableStringBuilder.append(TextUtils.join(",", properties));
//            insertTableStringBuilder.append(") SELECT ");
//            insertTableStringBuilder.append(TextUtils.join(",", properties));
//            insertTableStringBuilder.append(" FROM ").append(tableName).append(";");
//
//            db.execSQL(insertTableStringBuilder.toString());
//        }
//    }
//
//
//    private void dropAllTables(SQLiteDatabase db, boolean ifExists,Class<? extends AbstractDao<?, ?>>... daoClasses){
//        for(int i = 0; i < daoClasses.length; i++) {
//            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
//            String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\""+daoConfig.tablename+"\"";
//            db.execSQL(sql);
//        }
//    }
//    private void createAllTables(SQLiteDatabase db, boolean ifNotExists,Class<? extends AbstractDao<?, ?>>... daoClasses){
//        for(int i = 0; i < daoClasses.length; i++) {
//            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
//
//            String divider = "";
//            String tableName = daoConfig.tablename;
//            ArrayList<String> properties = new ArrayList<String>();
//
//            StringBuilder createTableStringBuilder = new StringBuilder();
//
//            String constraint = ifNotExists? "IF NOT EXISTS ": "";
//
//            createTableStringBuilder.append("CREATE TABLE ").append(constraint).append(tableName).append(" (");
//
//            for(int j = 0; j < daoConfig.properties.length; j++) {
//                String columnName = daoConfig.properties[j].columnName;
//                properties.add(columnName);
//                String type = null;
//
//                try {
//                    type = getTypeByClass(daoConfig.properties[j].type);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                createTableStringBuilder.append(divider).append(columnName).append(" ").append(type);
//
//                if(daoConfig.properties[j].primaryKey) {
//                    createTableStringBuilder.append(" PRIMARY KEY");
//                }
//
//                divider = ",";
//            }
//            createTableStringBuilder.append(");");
//            db.execSQL(createTableStringBuilder.toString());
//        }
//    }
//
//    private void restoreData(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
//        for(int i = 0; i < daoClasses.length; i++) {
//            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
//
//            String tableName = daoConfig.tablename;
//            String tempTableName = daoConfig.tablename.concat("_TEMP");
//            ArrayList<String> properties = new ArrayList<String>();
//
//            for (int j = 0; j < daoConfig.properties.length; j++) {
//                String columnName = daoConfig.properties[j].columnName;
//
//                if(getColumns(db, tempTableName).contains(columnName)) {
//                    properties.add(columnName);
//                }
//            }
//
//            StringBuilder insertTableStringBuilder = new StringBuilder();
//
//            insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
//            insertTableStringBuilder.append(TextUtils.join(",", properties));
//            insertTableStringBuilder.append(") SELECT ");
//            insertTableStringBuilder.append(TextUtils.join(",", properties));
//            insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
//
//            StringBuilder dropTableStringBuilder = new StringBuilder();
//
//            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
//
//
//            if(insertTableStringBuilder.toString().contains("()")){
//                continue;
//            }
//
//            db.execSQL(insertTableStringBuilder.toString());
//            db.execSQL(dropTableStringBuilder.toString());
//        }
//    }
//
//    private String getTypeByClass(Class<?> type) throws Exception {
//        /**
//         * ADD MORE CLASS ON THIS IF YOUR CLASS USE OTHER THAN BELOW
//         */
//        if(type.equals(String.class)) {
//            return "TEXT";
//        }
//        if(type.equals(Integer.class) || type.equals(Date.class)) {
//            return "INTEGER";
//        }
//        if(type.equals(Boolean.class)) {
//            return "BOOLEAN";
//        }
//        if(type.equals(Double.class)) {
//            return "DOUBLE";
//        }
//        if(type.equals(Long.class)) {
//            return "LONG";
//        }
//        if(type.equals(Float.class)) {
//            return "FLOAT";
//        }
//
//        Exception exception = new Exception(CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - Class: ").concat(type.toString()));
//        throw exception;
//    }
//
//    private static List<String> getColumns(SQLiteDatabase db, String tableName) {
//        List<String> columns = new ArrayList<String>();
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
//            if (cursor != null) {
//                columns = new ArrayList<String>(Arrays.asList(cursor.getColumnNames()));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null)
//                cursor.close();
//        }
//        return columns;
//    }
//}

