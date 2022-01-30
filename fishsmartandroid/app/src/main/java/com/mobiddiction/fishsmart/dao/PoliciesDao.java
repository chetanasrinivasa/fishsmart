package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.dao.Policies;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "POLICIES".
*/
public class PoliciesDao extends AbstractDao<Policies, Void> {

    public static final String TABLENAME = "POLICIES";

    /**
     * Properties of entity Policies.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property CreatedBy = new Property(0, String.class, "createdBy", false, "CREATED_BY");
        public final static Property CreatedDate = new Property(1, String.class, "createdDate", false, "CREATED_DATE");
        public final static Property LastModifiedBy = new Property(2, String.class, "lastModifiedBy", false, "LAST_MODIFIED_BY");
        public final static Property LastModifiedDate = new Property(3, String.class, "lastModifiedDate", false, "LAST_MODIFIED_DATE");
        public final static Property Id = new Property(4, String.class, "id", false, "ID");
        public final static Property Title = new Property(5, String.class, "title", false, "TITLE");
        public final static Property Text = new Property(6, String.class, "text", false, "TEXT");
        public final static Property Html = new Property(7, String.class, "html", false, "HTML");
        public final static Property DocUrl = new Property(8, String.class, "docUrl", false, "DOC_URL");
        public final static Property Required = new Property(9, Boolean.class, "required", false, "REQUIRED");
    }


    public PoliciesDao(DaoConfig config) {
        super(config);
    }
    
    public PoliciesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"POLICIES\" (" + //
                "\"CREATED_BY\" TEXT," + // 0: createdBy
                "\"CREATED_DATE\" TEXT," + // 1: createdDate
                "\"LAST_MODIFIED_BY\" TEXT," + // 2: lastModifiedBy
                "\"LAST_MODIFIED_DATE\" TEXT," + // 3: lastModifiedDate
                "\"ID\" TEXT," + // 4: id
                "\"TITLE\" TEXT," + // 5: title
                "\"TEXT\" TEXT," + // 6: text
                "\"HTML\" TEXT," + // 7: html
                "\"DOC_URL\" TEXT," + // 8: docUrl
                "\"REQUIRED\" INTEGER);"); // 9: required
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"POLICIES\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, Policies entity) {
        stmt.clearBindings();

        String createdBy = entity.getCreatedBy();
        if (createdBy != null) {
            stmt.bindString(1, createdBy);
        }

        String createdDate = entity.getCreatedDate();
        if (createdDate != null) {
            stmt.bindString(2, createdDate);
        }

        String lastModifiedBy = entity.getLastModifiedBy();
        if (lastModifiedBy != null) {
            stmt.bindString(3, lastModifiedBy);
        }

        String lastModifiedDate = entity.getLastModifiedDate();
        if (lastModifiedDate != null) {
            stmt.bindString(4, lastModifiedDate);
        }

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(5, id);
        }

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(6, title);
        }

        String text = entity.getText();
        if (text != null) {
            stmt.bindString(7, text);
        }

        String html = entity.getHtml();
        if (html != null) {
            stmt.bindString(8, html);
        }

        String docUrl = entity.getDocUrl();
        if (docUrl != null) {
            stmt.bindString(9, docUrl);
        }

        Boolean required = entity.getRequired();
        if (required != null) {
            stmt.bindLong(10, required ? 1L: 0L);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Policies entity) {
        stmt.clearBindings();
 
        String createdBy = entity.getCreatedBy();
        if (createdBy != null) {
            stmt.bindString(1, createdBy);
        }
 
        String createdDate = entity.getCreatedDate();
        if (createdDate != null) {
            stmt.bindString(2, createdDate);
        }
 
        String lastModifiedBy = entity.getLastModifiedBy();
        if (lastModifiedBy != null) {
            stmt.bindString(3, lastModifiedBy);
        }
 
        String lastModifiedDate = entity.getLastModifiedDate();
        if (lastModifiedDate != null) {
            stmt.bindString(4, lastModifiedDate);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(5, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(6, title);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(7, text);
        }
 
        String html = entity.getHtml();
        if (html != null) {
            stmt.bindString(8, html);
        }
 
        String docUrl = entity.getDocUrl();
        if (docUrl != null) {
            stmt.bindString(9, docUrl);
        }
 
        Boolean required = entity.getRequired();
        if (required != null) {
            stmt.bindLong(10, required ? 1L: 0L);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public Policies readEntity(Cursor cursor, int offset) {
        Policies entity = new Policies( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // createdBy
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // createdDate
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // lastModifiedBy
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // lastModifiedDate
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // id
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // title
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // text
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // html
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // docUrl
            cursor.isNull(offset + 9) ? null : cursor.getShort(offset + 9) != 0 // required
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Policies entity, int offset) {
        entity.setCreatedBy(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCreatedDate(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLastModifiedBy(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLastModifiedDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTitle(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setText(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setHtml(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDocUrl(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setRequired(cursor.isNull(offset + 9) ? null : cursor.getShort(offset + 9) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(Policies entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(Policies entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(Policies entity) {
        return false;
    }
}