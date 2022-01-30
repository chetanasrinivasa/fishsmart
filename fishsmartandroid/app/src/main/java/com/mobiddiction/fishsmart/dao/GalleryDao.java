package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.dao.Gallery;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GALLERY".
*/
public class GalleryDao extends AbstractDao<Gallery, Void> {

    public static final String TABLENAME = "GALLERY";

    /**
     * Properties of entity Gallery.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property CreatedBy = new Property(0, String.class, "createdBy", false, "CREATED_BY");
        public final static Property CreatedDate = new Property(1, String.class, "createdDate", false, "CREATED_DATE");
        public final static Property LastModifiedBy = new Property(2, String.class, "lastModifiedBy", false, "LAST_MODIFIED_BY");
        public final static Property LastModifiedDate = new Property(3, String.class, "lastModifiedDate", false, "LAST_MODIFIED_DATE");
        public final static Property Id = new Property(4, String.class, "id", false, "ID");
        public final static Property Name = new Property(5, String.class, "name", false, "NAME");
        public final static Property GalleryId = new Property(6, String.class, "galleryId", false, "GALLERY_ID");
        public final static Property Description = new Property(7, String.class, "description", false, "DESCRIPTION");
        public final static Property Note = new Property(8, String.class, "note", false, "NOTE");
        public final static Property AdminNote = new Property(9, String.class, "adminNote", false, "ADMIN_NOTE");
        public final static Property Approved = new Property(10, String.class, "approved", false, "APPROVED");
        public final static Property Pending = new Property(11, String.class, "pending", false, "PENDING");
    }


    public GalleryDao(DaoConfig config) {
        super(config);
    }
    
    public GalleryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GALLERY\" (" + //
                "\"CREATED_BY\" TEXT," + // 0: createdBy
                "\"CREATED_DATE\" TEXT," + // 1: createdDate
                "\"LAST_MODIFIED_BY\" TEXT," + // 2: lastModifiedBy
                "\"LAST_MODIFIED_DATE\" TEXT," + // 3: lastModifiedDate
                "\"ID\" TEXT," + // 4: id
                "\"NAME\" TEXT," + // 5: name
                "\"GALLERY_ID\" TEXT," + // 6: galleryId
                "\"DESCRIPTION\" TEXT," + // 7: description
                "\"NOTE\" TEXT," + // 8: note
                "\"ADMIN_NOTE\" TEXT," + // 9: adminNote
                "\"APPROVED\" TEXT," + // 10: approved
                "\"PENDING\" TEXT);"); // 11: pending
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GALLERY\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, Gallery entity) {
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

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }

        String galleryId = entity.getGalleryId();
        if (galleryId != null) {
            stmt.bindString(7, galleryId);
        }

        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(8, description);
        }

        String note = entity.getNote();
        if (note != null) {
            stmt.bindString(9, note);
        }

        String adminNote = entity.getAdminNote();
        if (adminNote != null) {
            stmt.bindString(10, adminNote);
        }

        String approved = entity.getApproved();
        if (approved != null) {
            stmt.bindString(11, approved);
        }

        String pending = entity.getPending();
        if (pending != null) {
            stmt.bindString(12, pending);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Gallery entity) {
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
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }
 
        String galleryId = entity.getGalleryId();
        if (galleryId != null) {
            stmt.bindString(7, galleryId);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(8, description);
        }
 
        String note = entity.getNote();
        if (note != null) {
            stmt.bindString(9, note);
        }
 
        String adminNote = entity.getAdminNote();
        if (adminNote != null) {
            stmt.bindString(10, adminNote);
        }
 
        String approved = entity.getApproved();
        if (approved != null) {
            stmt.bindString(11, approved);
        }
 
        String pending = entity.getPending();
        if (pending != null) {
            stmt.bindString(12, pending);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public Gallery readEntity(Cursor cursor, int offset) {
        Gallery entity = new Gallery( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // createdBy
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // createdDate
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // lastModifiedBy
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // lastModifiedDate
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // id
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // name
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // galleryId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // description
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // note
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // adminNote
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // approved
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11) // pending
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Gallery entity, int offset) {
        entity.setCreatedBy(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCreatedDate(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLastModifiedBy(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLastModifiedDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setGalleryId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDescription(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setNote(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setAdminNote(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setApproved(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPending(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(Gallery entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(Gallery entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(Gallery entity) {
        return false;
    }
}
