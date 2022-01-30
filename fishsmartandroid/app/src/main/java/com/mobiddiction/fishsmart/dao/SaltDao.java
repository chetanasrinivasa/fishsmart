package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.dao.Salt;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SALT".
*/
public class SaltDao extends AbstractDao<Salt, Void> {

    public static final String TABLENAME = "SALT";

    /**
     * Properties of entity Salt.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", false, "ID");
        public final static Property SpeciesType = new Property(1, String.class, "speciesType", false, "SPECIES_TYPE");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Description = new Property(3, String.class, "description", false, "DESCRIPTION");
        public final static Property BagLimitForCardView = new Property(4, String.class, "bagLimitForCardView", false, "BAG_LIMIT_FOR_CARD_VIEW");
        public final static Property SizeLimit = new Property(5, String.class, "sizeLimit", false, "SIZE_LIMIT");
        public final static Property Image = new Property(6, String.class, "image", false, "IMAGE");
        public final static Property Thumbnail = new Property(7, String.class, "thumbnail", false, "THUMBNAIL");
        public final static Property Grouped = new Property(8, Boolean.class, "grouped", false, "GROUPED");
        public final static Property GroupName = new Property(9, String.class, "groupName", false, "GROUP_NAME");
    }


    public SaltDao(DaoConfig config) {
        super(config);
    }
    
    public SaltDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SALT\" (" + //
                "\"ID\" TEXT," + // 0: id
                "\"SPECIES_TYPE\" TEXT," + // 1: speciesType
                "\"TITLE\" TEXT," + // 2: title
                "\"DESCRIPTION\" TEXT," + // 3: description
                "\"BAG_LIMIT_FOR_CARD_VIEW\" TEXT," + // 4: bagLimitForCardView
                "\"SIZE_LIMIT\" TEXT," + // 5: sizeLimit
                "\"IMAGE\" TEXT," + // 6: image
                "\"THUMBNAIL\" TEXT," + // 7: thumbnail
                "\"GROUPED\" INTEGER," + // 8: grouped
                "\"GROUP_NAME\" TEXT);"); // 9: groupName
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SALT\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, Salt entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }

        String speciesType = entity.getSpeciesType();
        if (speciesType != null) {
            stmt.bindString(2, speciesType);
        }

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }

        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(4, description);
        }

        String bagLimitForCardView = entity.getBagLimitForCardView();
        if (bagLimitForCardView != null) {
            stmt.bindString(5, bagLimitForCardView);
        }

        String sizeLimit = entity.getSizeLimit();
        if (sizeLimit != null) {
            stmt.bindString(6, sizeLimit);
        }

        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(7, image);
        }

        String thumbnail = entity.getThumbnail();
        if (thumbnail != null) {
            stmt.bindString(8, thumbnail);
        }

        Boolean grouped = entity.getGrouped();
        if (grouped != null) {
            stmt.bindLong(9, grouped ? 1L: 0L);
        }

        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(10, groupName);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Salt entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String speciesType = entity.getSpeciesType();
        if (speciesType != null) {
            stmt.bindString(2, speciesType);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(4, description);
        }
 
        String bagLimitForCardView = entity.getBagLimitForCardView();
        if (bagLimitForCardView != null) {
            stmt.bindString(5, bagLimitForCardView);
        }
 
        String sizeLimit = entity.getSizeLimit();
        if (sizeLimit != null) {
            stmt.bindString(6, sizeLimit);
        }
 
        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(7, image);
        }
 
        String thumbnail = entity.getThumbnail();
        if (thumbnail != null) {
            stmt.bindString(8, thumbnail);
        }
 
        Boolean grouped = entity.getGrouped();
        if (grouped != null) {
            stmt.bindLong(9, grouped ? 1L: 0L);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(10, groupName);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public Salt readEntity(Cursor cursor, int offset) {
        Salt entity = new Salt( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // speciesType
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // description
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // bagLimitForCardView
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // sizeLimit
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // image
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // thumbnail
            cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0, // grouped
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // groupName
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Salt entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setSpeciesType(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDescription(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBagLimitForCardView(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSizeLimit(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setImage(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setThumbnail(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setGrouped(cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0);
        entity.setGroupName(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(Salt entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(Salt entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(Salt entity) {
        return false;
    }
}
