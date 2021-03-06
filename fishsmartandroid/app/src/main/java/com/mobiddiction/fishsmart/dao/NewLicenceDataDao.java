package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


import com.mobiddiction.fishsmart.dao.NewLicenceData;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NEW_LICENCE_DATA".
*/
public class NewLicenceDataDao extends AbstractDao<NewLicenceData, Void> {

    public static final String TABLENAME = "NEW_LICENCE_DATA";

    /**
     * Properties of entity NewLicenceData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ContactAndLicensingType = new Property(0, String.class, "contactAndLicensingType", false, "CONTACT_AND_LICENSING_TYPE");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Description = new Property(2, String.class, "description", false, "DESCRIPTION");
        public final static Property Url = new Property(3, String.class, "url", false, "URL");
        public final static Property Phone = new Property(4, String.class, "phone", false, "PHONE");
        public final static Property Mobile = new Property(5, String.class, "mobile", false, "MOBILE");
        public final static Property SubType = new Property(6, String.class, "subType", false, "SUB_TYPE");
    }


    public NewLicenceDataDao(DaoConfig config) {
        super(config);
    }
    
    public NewLicenceDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NEW_LICENCE_DATA\" (" + //
                "\"CONTACT_AND_LICENSING_TYPE\" TEXT," + // 0: contactAndLicensingType
                "\"TITLE\" TEXT," + // 1: title
                "\"DESCRIPTION\" TEXT," + // 2: description
                "\"URL\" TEXT," + // 3: url
                "\"PHONE\" TEXT," + // 4: phone
                "\"MOBILE\" TEXT," + // 5: mobile
                "\"SUB_TYPE\" TEXT);"); // 6: subType
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NEW_LICENCE_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, NewLicenceData entity) {
        stmt.clearBindings();

        String contactAndLicensingType = entity.getContactAndLicensingType();
        if (contactAndLicensingType != null) {
            stmt.bindString(1, contactAndLicensingType.replaceAll("\\s+",""));
        }

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }

        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(3, description);
        }

        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(4, url);
        }

        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }

        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(6, mobile);
        }

        String subType = entity.getSubType();
        if (subType != null) {
            stmt.bindString(7, subType.replaceAll("\\s+",""));
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, NewLicenceData entity) {
        stmt.clearBindings();
 
        String contactAndLicensingType = entity.getContactAndLicensingType();
        if (contactAndLicensingType != null) {
            stmt.bindString(1, contactAndLicensingType.replaceAll("\\s+",""));
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(3, description);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(4, url);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(6, mobile);
        }
 
        String subType = entity.getSubType();
        if (subType != null) {
            stmt.bindString(7, subType.replaceAll("\\s+",""));
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public NewLicenceData readEntity(Cursor cursor, int offset) {
        NewLicenceData entity = new NewLicenceData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // contactAndLicensingType
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // description
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // url
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // phone
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // mobile
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // subType
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, NewLicenceData entity, int offset) {
        entity.setContactAndLicensingType(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDescription(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPhone(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMobile(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSubType(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(NewLicenceData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(NewLicenceData entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(NewLicenceData entity) {
        return false;
    }
}
