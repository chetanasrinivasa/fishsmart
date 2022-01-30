package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.dao.AllSpecies;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALL_SPECIES".
*/
public class AllSpeciesDao extends AbstractDao<AllSpecies, Void> {

    public static final String TABLENAME = "ALL_SPECIES";

    /**
     * Properties of entity AllSpecies.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", false, "ID");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Description = new Property(2, String.class, "description", false, "DESCRIPTION");
        public final static Property SpeciesType = new Property(3, String.class, "speciesType", false, "SPECIES_TYPE");
        public final static Property SizeLimit = new Property(4, String.class, "sizeLimit", false, "SIZE_LIMIT");
        public final static Property BagLimit = new Property(5, String.class, "bagLimit", false, "BAG_LIMIT");
        public final static Property Grouped = new Property(6, Boolean.class, "grouped", false, "GROUPED");
        public final static Property Image = new Property(7, String.class, "image", false, "IMAGE");
        public final static Property ThumbnailImage = new Property(8, String.class, "thumbnailImage", false, "THUMBNAIL_IMAGE");
        public final static Property ByteThumbnailFresh = new Property(9, byte[].class, "byteThumbnailFresh", false, "BYTE_THUMBNAIL_FRESH");
        public final static Property ByteImageFresh = new Property(10, byte[].class, "byteImageFresh", false, "BYTE_IMAGE_FRESH");
        public final static Property SubHeader = new Property(11, String.class, "subHeader", false, "SUB_HEADER");
        public final static Property RuleBagLimit = new Property(12, String.class, "ruleBagLimit", false, "RULE_BAG_LIMIT");
        public final static Property GroupType = new Property(13, String.class, "groupType", false, "GROUP_TYPE");
    }


    public AllSpeciesDao(DaoConfig config) {
        super(config);
    }
    
    public AllSpeciesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALL_SPECIES\" (" + //
                "\"ID\" TEXT," + // 0: id
                "\"TITLE\" TEXT," + // 1: title
                "\"DESCRIPTION\" TEXT," + // 2: description
                "\"SPECIES_TYPE\" TEXT," + // 3: speciesType
                "\"SIZE_LIMIT\" TEXT," + // 4: sizeLimit
                "\"BAG_LIMIT\" TEXT," + // 5: bagLimit
                "\"GROUPED\" INTEGER," + // 6: grouped
                "\"IMAGE\" TEXT," + // 7: image
                "\"THUMBNAIL_IMAGE\" TEXT," + // 8: thumbnailImage
                "\"BYTE_THUMBNAIL_FRESH\" BLOB," + // 9: byteThumbnailFresh
                "\"BYTE_IMAGE_FRESH\" BLOB," + // 10: byteImageFresh
                "\"SUB_HEADER\" TEXT," + // 11: subHeader
                "\"RULE_BAG_LIMIT\" TEXT," + // 12: ruleBagLimit
                "\"GROUP_TYPE\" TEXT);"); // 13: groupType
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALL_SPECIES\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, AllSpecies entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }

        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(3, description);
        }

        String speciesType = entity.getSpeciesType();
        if (speciesType != null) {
            stmt.bindString(4, speciesType);
        }

        String sizeLimit = entity.getSizeLimit();
        if (sizeLimit != null) {
            stmt.bindString(5, sizeLimit);
        }

        String bagLimit = entity.getBagLimit();
        if (bagLimit != null) {
            stmt.bindString(6, bagLimit);
        }

        Boolean grouped = entity.getGrouped();
        if (grouped != null) {
            stmt.bindLong(7, grouped ? 1L: 0L);
        }

        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(8, image);
        }

        String thumbnailImage = entity.getThumbnailImage();
        if (thumbnailImage != null) {
            stmt.bindString(9, thumbnailImage);
        }

        byte[] byteThumbnailFresh = entity.getByteThumbnailFresh();
        if (byteThumbnailFresh != null) {
            stmt.bindBlob(10, byteThumbnailFresh);
        }

        byte[] byteImageFresh = entity.getByteImageFresh();
        if (byteImageFresh != null) {
            stmt.bindBlob(11, byteImageFresh);
        }

        String subHeader = entity.getSubHeader();
        if (subHeader != null) {
            stmt.bindString(12, subHeader);
        }

        String ruleBagLimit = entity.getRuleBagLimit();
        if (ruleBagLimit != null) {
            stmt.bindString(13, ruleBagLimit);
        }

        String groupType = entity.getGroupType();
        if (groupType != null) {
            stmt.bindString(14, groupType);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, AllSpecies entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }

        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(3, description);
        }

        String speciesType = entity.getSpeciesType();
        if (speciesType != null) {
            stmt.bindString(4, speciesType);
        }

        String sizeLimit = entity.getSizeLimit();
        if (sizeLimit != null) {
            stmt.bindString(5, sizeLimit);
        }

        String bagLimit = entity.getBagLimit();
        if (bagLimit != null) {
            stmt.bindString(6, bagLimit);
        }

        Boolean grouped = entity.getGrouped();
        if (grouped != null) {
            stmt.bindLong(7, grouped ? 1L: 0L);
        }

        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(8, image);
        }

        String thumbnailImage = entity.getThumbnailImage();
        if (thumbnailImage != null) {
            stmt.bindString(9, thumbnailImage);
        }

        byte[] byteThumbnailFresh = entity.getByteThumbnailFresh();
        if (byteThumbnailFresh != null) {
            stmt.bindBlob(10, byteThumbnailFresh);
        }

        byte[] byteImageFresh = entity.getByteImageFresh();
        if (byteImageFresh != null) {
            stmt.bindBlob(11, byteImageFresh);
        }

        String subHeader = entity.getSubHeader();
        if (subHeader != null) {
            stmt.bindString(12, subHeader);
        }

        String ruleBagLimit = entity.getRuleBagLimit();
        if (ruleBagLimit != null) {
            stmt.bindString(13, ruleBagLimit);
        }

        String groupType = entity.getGroupType();
        if (groupType != null) {
            stmt.bindString(14, groupType);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public AllSpecies readEntity(Cursor cursor, int offset) {
        AllSpecies entity = new AllSpecies( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // description
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // speciesType
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // sizeLimit
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // bagLimit
            cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0, // grouped
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // image
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // thumbnailImage
            cursor.isNull(offset + 9) ? null : cursor.getBlob(offset + 9), // byteThumbnailFresh
            cursor.isNull(offset + 10) ? null : cursor.getBlob(offset + 10), // byteImageFresh
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // subHeader
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // ruleBagLimit
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // groupType
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, AllSpecies entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDescription(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSpeciesType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSizeLimit(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setBagLimit(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setGrouped(cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0);
        entity.setImage(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setThumbnailImage(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setByteThumbnailFresh(cursor.isNull(offset + 9) ? null : cursor.getBlob(offset + 9));
        entity.setByteImageFresh(cursor.isNull(offset + 10) ? null : cursor.getBlob(offset + 10));
        entity.setSubHeader(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setRuleBagLimit(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setGroupType(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(AllSpecies entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(AllSpecies entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }


    @Override
    protected boolean hasKey(AllSpecies entity) {
        return false;
    }
}