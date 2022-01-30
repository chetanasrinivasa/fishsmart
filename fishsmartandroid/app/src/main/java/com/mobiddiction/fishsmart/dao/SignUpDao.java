package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.dao.SignUp;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SIGN_UP".
*/
public class SignUpDao extends AbstractDao<SignUp, Void> {

    public static final String TABLENAME = "SIGN_UP";

    /**
     * Properties of entity SignUp.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", false, "ID");
        public final static Property FirstName = new Property(1, String.class, "firstName", false, "FIRST_NAME");
        public final static Property LastName = new Property(2, String.class, "lastName", false, "LAST_NAME");
        public final static Property Password = new Property(3, String.class, "password", false, "PASSWORD");
        public final static Property MatchingPassword = new Property(4, String.class, "matchingPassword", false, "MATCHING_PASSWORD");
        public final static Property Email = new Property(5, String.class, "email", false, "EMAIL");
        public final static Property Mobile = new Property(6, String.class, "mobile", false, "MOBILE");
        public final static Property IsUsing2FA = new Property(7, Boolean.class, "isUsing2FA", false, "IS_USING2_FA");
        public final static Property Enabled = new Property(8, Boolean.class, "enabled", false, "ENABLED");
        public final static Property Description = new Property(9, String.class, "description", false, "DESCRIPTION");
        public final static Property ClientId = new Property(10, String.class, "clientId", false, "CLIENT_ID");
    }


    public SignUpDao(DaoConfig config) {
        super(config);
    }
    
    public SignUpDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SIGN_UP\" (" + //
                "\"ID\" TEXT," + // 0: id
                "\"FIRST_NAME\" TEXT," + // 1: firstName
                "\"LAST_NAME\" TEXT," + // 2: lastName
                "\"PASSWORD\" TEXT," + // 3: password
                "\"MATCHING_PASSWORD\" TEXT," + // 4: matchingPassword
                "\"EMAIL\" TEXT," + // 5: email
                "\"MOBILE\" TEXT," + // 6: mobile
                "\"IS_USING2_FA\" INTEGER," + // 7: isUsing2FA
                "\"ENABLED\" INTEGER," + // 8: enabled
                "\"DESCRIPTION\" TEXT," + // 9: description
                "\"CLIENT_ID\" TEXT);"); // 10: clientId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SIGN_UP\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, SignUp entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }

        String firstName = entity.getFirstName();
        if (firstName != null) {
            stmt.bindString(2, firstName);
        }

        String lastName = entity.getLastName();
        if (lastName != null) {
            stmt.bindString(3, lastName);
        }

        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }

        String matchingPassword = entity.getMatchingPassword();
        if (matchingPassword != null) {
            stmt.bindString(5, matchingPassword);
        }

        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(6, email);
        }

        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(7, mobile);
        }

        Boolean isUsing2FA = entity.getIsUsing2FA();
        if (isUsing2FA != null) {
            stmt.bindLong(8, isUsing2FA ? 1L: 0L);
        }

        Boolean enabled = entity.getEnabled();
        if (enabled != null) {
            stmt.bindLong(9, enabled ? 1L: 0L);
        }

        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(10, description);
        }

        String clientId = entity.getClientId();
        if (clientId != null) {
            stmt.bindString(11, clientId);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SignUp entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String firstName = entity.getFirstName();
        if (firstName != null) {
            stmt.bindString(2, firstName);
        }
 
        String lastName = entity.getLastName();
        if (lastName != null) {
            stmt.bindString(3, lastName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
 
        String matchingPassword = entity.getMatchingPassword();
        if (matchingPassword != null) {
            stmt.bindString(5, matchingPassword);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(6, email);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(7, mobile);
        }
 
        Boolean isUsing2FA = entity.getIsUsing2FA();
        if (isUsing2FA != null) {
            stmt.bindLong(8, isUsing2FA ? 1L: 0L);
        }
 
        Boolean enabled = entity.getEnabled();
        if (enabled != null) {
            stmt.bindLong(9, enabled ? 1L: 0L);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(10, description);
        }
 
        String clientId = entity.getClientId();
        if (clientId != null) {
            stmt.bindString(11, clientId);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public SignUp readEntity(Cursor cursor, int offset) {
        SignUp entity = new SignUp( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // firstName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // lastName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // password
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // matchingPassword
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // email
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mobile
            cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0, // isUsing2FA
            cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0, // enabled
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // description
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // clientId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SignUp entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setFirstName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLastName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPassword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMatchingPassword(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setEmail(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMobile(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIsUsing2FA(cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0);
        entity.setEnabled(cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0);
        entity.setDescription(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setClientId(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(SignUp entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(SignUp entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(SignUp entity) {
        return false;
    }
}