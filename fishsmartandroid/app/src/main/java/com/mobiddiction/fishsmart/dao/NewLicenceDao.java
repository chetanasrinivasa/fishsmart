package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.dao.NewLicence;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NEW_LICENCE".
*/
public class NewLicenceDao extends AbstractDao<NewLicence, Void> {

    public static final String TABLENAME = "NEW_LICENCE";

    /**
     * Properties of entity NewLicence.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", false, "ID");
        public final static Property CreatedBy = new Property(1, String.class, "createdBy", false, "CREATED_BY");
        public final static Property CreatedDate = new Property(2, String.class, "createdDate", false, "CREATED_DATE");
        public final static Property LastModifiedBy = new Property(3, String.class, "lastModifiedBy", false, "LAST_MODIFIED_BY");
        public final static Property LastModifiedDate = new Property(4, String.class, "lastModifiedDate", false, "LAST_MODIFIED_DATE");
        public final static Property Name = new Property(5, String.class, "name", false, "NAME");
        public final static Property Type_id = new Property(6, String.class, "type_id", false, "TYPE_ID");
        public final static Property Content = new Property(7, String.class, "content", false, "CONTENT");
    }


    public NewLicenceDao(DaoConfig config) {
        super(config);
    }
    
    public NewLicenceDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NEW_LICENCE\" (" + //
                "\"ID\" TEXT," + // 0: id
                "\"CREATED_BY\" TEXT," + // 1: createdBy
                "\"CREATED_DATE\" TEXT," + // 2: createdDate
                "\"LAST_MODIFIED_BY\" TEXT," + // 3: lastModifiedBy
                "\"LAST_MODIFIED_DATE\" TEXT," + // 4: lastModifiedDate
                "\"NAME\" TEXT," + // 5: name
                "\"TYPE_ID\" TEXT," + // 6: type_id
                "\"CONTENT\" TEXT);"); // 7: content
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NEW_LICENCE\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, NewLicence entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }

        String createdBy = entity.getCreatedBy();
        if (createdBy != null) {
            stmt.bindString(2, createdBy);
        }

        String createdDate = entity.getCreatedDate();
        if (createdDate != null) {
            stmt.bindString(3, createdDate);
        }

        String lastModifiedBy = entity.getLastModifiedBy();
        if (lastModifiedBy != null) {
            stmt.bindString(4, lastModifiedBy);
        }

        String lastModifiedDate = entity.getLastModifiedDate();
        if (lastModifiedDate != null) {
            stmt.bindString(5, lastModifiedDate);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }

        String type_id = entity.getType_id();
        if (type_id != null) {
            stmt.bindString(7, type_id);
        }

        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(8, content);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, NewLicence entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String createdBy = entity.getCreatedBy();
        if (createdBy != null) {
            stmt.bindString(2, createdBy);
        }
 
        String createdDate = entity.getCreatedDate();
        if (createdDate != null) {
            stmt.bindString(3, createdDate);
        }
 
        String lastModifiedBy = entity.getLastModifiedBy();
        if (lastModifiedBy != null) {
            stmt.bindString(4, lastModifiedBy);
        }
 
        String lastModifiedDate = entity.getLastModifiedDate();
        if (lastModifiedDate != null) {
            stmt.bindString(5, lastModifiedDate);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }
 
        String type_id = entity.getType_id();
        if (type_id != null) {
            stmt.bindString(7, type_id);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(8, content);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public NewLicence readEntity(Cursor cursor, int offset) {
        NewLicence entity = new NewLicence( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // createdBy
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // createdDate
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // lastModifiedBy
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // lastModifiedDate
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // name
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // type_id
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // content
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, NewLicence entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCreatedBy(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreatedDate(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLastModifiedBy(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLastModifiedDate(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setType_id(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setContent(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(NewLicence entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(NewLicence entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(NewLicence entity) {
        return false;
    }
}