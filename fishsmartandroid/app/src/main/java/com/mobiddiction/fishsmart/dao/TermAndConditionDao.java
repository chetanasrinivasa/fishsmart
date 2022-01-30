package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.dao.TermAndCondition;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TERM_AND_CONDITION".
*/
public class TermAndConditionDao extends AbstractDao<TermAndCondition, Void> {

    public static final String TABLENAME = "TERM_AND_CONDITION";

    /**
     * Properties of entity TermAndCondition.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Text = new Property(0, String.class, "text", false, "TEXT");
        public final static Property Html = new Property(1, String.class, "html", false, "HTML");
    }


    public TermAndConditionDao(DaoConfig config) {
        super(config);
    }
    
    public TermAndConditionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TERM_AND_CONDITION\" (" + //
                "\"TEXT\" TEXT," + // 0: text
                "\"HTML\" TEXT);"); // 1: html
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TERM_AND_CONDITION\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, TermAndCondition entity) {
        stmt.clearBindings();

        String text = entity.getText();
        if (text != null) {
            stmt.bindString(1, text);
        }

        String html = entity.getHtml();
        if (html != null) {
            stmt.bindString(2, html);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TermAndCondition entity) {
        stmt.clearBindings();
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(1, text);
        }
 
        String html = entity.getHtml();
        if (html != null) {
            stmt.bindString(2, html);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public TermAndCondition readEntity(Cursor cursor, int offset) {
        TermAndCondition entity = new TermAndCondition( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // text
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // html
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TermAndCondition entity, int offset) {
        entity.setText(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setHtml(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(TermAndCondition entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(TermAndCondition entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(TermAndCondition entity) {
        return false;
    }
}
