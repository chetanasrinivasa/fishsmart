package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.dao.NotificationRoles;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NOTIFICATION_ROLES".
*/
public class NotificationRolesDao extends AbstractDao<NotificationRoles, Void> {

    public static final String TABLENAME = "NOTIFICATION_ROLES";

    /**
     * Properties of entity NotificationRoles.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", false, "ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
    }


    public NotificationRolesDao(DaoConfig config) {
        super(config);
    }
    
    public NotificationRolesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOTIFICATION_ROLES\" (" + //
                "\"ID\" TEXT," + // 0: id
                "\"NAME\" TEXT);"); // 1: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOTIFICATION_ROLES\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, NotificationRoles entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, NotificationRoles entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public NotificationRoles readEntity(Cursor cursor, int offset) {
        NotificationRoles entity = new NotificationRoles( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // name
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, NotificationRoles entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(NotificationRoles entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(NotificationRoles entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(NotificationRoles entity) {
        return false;
    }
}
