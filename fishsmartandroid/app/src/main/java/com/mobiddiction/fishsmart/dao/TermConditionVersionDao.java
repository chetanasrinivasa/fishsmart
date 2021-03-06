package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


import com.mobiddiction.fishsmart.dao.TermConditionVersion;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TERM_CONDITION_VERSION".
*/
public class TermConditionVersionDao extends AbstractDao<TermConditionVersion, Void> {

    public static final String TABLENAME = "TERM_CONDITION_VERSION";

    /**
     * Properties of entity TermConditionVersion.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", false, "ID");
    }


    public TermConditionVersionDao(DaoConfig config) {
        super(config);
    }
    
    public TermConditionVersionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TERM_CONDITION_VERSION\" (" + //
                "\"ID\" TEXT);"); // 0: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TERM_CONDITION_VERSION\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, TermConditionVersion entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TermConditionVersion entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public TermConditionVersion readEntity(Cursor cursor, int offset) {
        TermConditionVersion entity = new TermConditionVersion( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0) // id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TermConditionVersion entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(TermConditionVersion entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(TermConditionVersion entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(TermConditionVersion entity) {
        return false;
    }
}
