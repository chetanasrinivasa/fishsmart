package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.dao.SaltWaterfishGroup;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SALT_WATERFISH_GROUP".
*/
public class SaltWaterfishGroupDao extends AbstractDao<SaltWaterfishGroup, Void> {

    public static final String TABLENAME = "SALT_WATERFISH_GROUP";

    /**
     * Properties of entity SaltWaterfishGroup.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property SaltID = new Property(0, String.class, "SaltID", false, "SALT_ID");
        public final static Property Id = new Property(1, Integer.class, "id", false, "ID");
        public final static Property GroupName = new Property(2, String.class, "groupName", false, "GROUP_NAME");
        public final static Property GroupTitle = new Property(3, String.class, "groupTitle", false, "GROUP_TITLE");
        public final static Property GroupDescription = new Property(4, String.class, "groupDescription", false, "GROUP_DESCRIPTION");
        public final static Property GroupType = new Property(5, String.class, "groupType", false, "GROUP_TYPE");
    }


    public SaltWaterfishGroupDao(DaoConfig config) {
        super(config);
    }
    
    public SaltWaterfishGroupDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SALT_WATERFISH_GROUP\" (" + //
                "\"SALT_ID\" TEXT," + // 0: SaltID
                "\"ID\" INTEGER," + // 1: id
                "\"GROUP_NAME\" TEXT," + // 2: groupName
                "\"GROUP_TITLE\" TEXT," + // 3: groupTitle
                "\"GROUP_DESCRIPTION\" TEXT," + // 4: groupDescription
                "\"GROUP_TYPE\" TEXT);"); // 5: groupType
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SALT_WATERFISH_GROUP\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, SaltWaterfishGroup entity) {
        stmt.clearBindings();

        String SaltID = entity.getSaltID();
        if (SaltID != null) {
            stmt.bindString(1, SaltID);
        }

        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }

        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(3, groupName);
        }

        String groupTitle = entity.getGroupTitle();
        if (groupTitle != null) {
            stmt.bindString(4, groupTitle);
        }

        String groupDescription = entity.getGroupDescription();
        if (groupDescription != null) {
            stmt.bindString(5, groupDescription);
        }

        String groupType = entity.getGroupType();
        if (groupType != null) {
            stmt.bindString(6, groupType);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SaltWaterfishGroup entity) {
        stmt.clearBindings();
 
        String SaltID = entity.getSaltID();
        if (SaltID != null) {
            stmt.bindString(1, SaltID);
        }
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(3, groupName);
        }
 
        String groupTitle = entity.getGroupTitle();
        if (groupTitle != null) {
            stmt.bindString(4, groupTitle);
        }
 
        String groupDescription = entity.getGroupDescription();
        if (groupDescription != null) {
            stmt.bindString(5, groupDescription);
        }
 
        String groupType = entity.getGroupType();
        if (groupType != null) {
            stmt.bindString(6, groupType);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public SaltWaterfishGroup readEntity(Cursor cursor, int offset) {
        SaltWaterfishGroup entity = new SaltWaterfishGroup( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // SaltID
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // groupName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // groupTitle
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // groupDescription
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // groupType
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SaltWaterfishGroup entity, int offset) {
        entity.setSaltID(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setGroupName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGroupTitle(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGroupDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGroupType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(SaltWaterfishGroup entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(SaltWaterfishGroup entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(SaltWaterfishGroup entity) {
        return false;
    }
}
