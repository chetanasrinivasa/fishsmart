package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.dao.SaltWaterrules;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SALT_WATERRULES".
*/
public class SaltWaterrulesDao extends AbstractDao<SaltWaterrules, Void> {

    public static final String TABLENAME = "SALT_WATERRULES";

    /**
     * Properties of entity SaltWaterrules.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property SaltID = new Property(0, String.class, "SaltID", false, "SALT_ID");
        public final static Property Id = new Property(1, Integer.class, "id", false, "ID");
        public final static Property LegalSize = new Property(2, String.class, "legalSize", false, "LEGAL_SIZE");
        public final static Property BagLimit = new Property(3, String.class, "bagLimit", false, "BAG_LIMIT");
        public final static Property Possession = new Property(4, String.class, "possession", false, "POSSESSION");
        public final static Property RuleBagLimit = new Property(5, String.class, "ruleBagLimit", false, "RULE_BAG_LIMIT");
    }


    public SaltWaterrulesDao(DaoConfig config) {
        super(config);
    }
    
    public SaltWaterrulesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SALT_WATERRULES\" (" + //
                "\"SALT_ID\" TEXT," + // 0: SaltID
                "\"ID\" INTEGER," + // 1: id
                "\"LEGAL_SIZE\" TEXT," + // 2: legalSize
                "\"BAG_LIMIT\" TEXT," + // 3: bagLimit
                "\"POSSESSION\" TEXT," + // 4: possession
                "\"RULE_BAG_LIMIT\" TEXT);"); // 5: ruleBagLimit
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SALT_WATERRULES\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, SaltWaterrules entity) {
        stmt.clearBindings();

        String SaltID = entity.getSaltID();
        if (SaltID != null) {
            stmt.bindString(1, SaltID);
        }

        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }

        String legalSize = entity.getLegalSize();
        if (legalSize != null) {
            stmt.bindString(3, legalSize);
        }

        String bagLimit = entity.getBagLimit();
        if (bagLimit != null) {
            stmt.bindString(4, bagLimit);
        }

        String possession = entity.getPossession();
        if (possession != null) {
            stmt.bindString(5, possession);
        }

        String ruleBagLimit = entity.getRuleBagLimit();
        if (ruleBagLimit != null) {
            stmt.bindString(6, ruleBagLimit);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SaltWaterrules entity) {
        stmt.clearBindings();
 
        String SaltID = entity.getSaltID();
        if (SaltID != null) {
            stmt.bindString(1, SaltID);
        }
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String legalSize = entity.getLegalSize();
        if (legalSize != null) {
            stmt.bindString(3, legalSize);
        }
 
        String bagLimit = entity.getBagLimit();
        if (bagLimit != null) {
            stmt.bindString(4, bagLimit);
        }
 
        String possession = entity.getPossession();
        if (possession != null) {
            stmt.bindString(5, possession);
        }
 
        String ruleBagLimit = entity.getRuleBagLimit();
        if (ruleBagLimit != null) {
            stmt.bindString(6, ruleBagLimit);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public SaltWaterrules readEntity(Cursor cursor, int offset) {
        SaltWaterrules entity = new SaltWaterrules( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // SaltID
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // legalSize
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // bagLimit
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // possession
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // ruleBagLimit
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SaltWaterrules entity, int offset) {
        entity.setSaltID(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setLegalSize(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBagLimit(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPossession(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setRuleBagLimit(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(SaltWaterrules entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(SaltWaterrules entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(SaltWaterrules entity) {
        return false;
    }
}
