package com.mobiddiction.fishsmart.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mobiddiction.fishsmart.dao.UserObj;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_OBJ".
*/
public class UserObjDao extends AbstractDao<UserObj, Void> {

    public static final String TABLENAME = "USER_OBJ";

    /**
     * Properties of entity UserObj.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property CreatedBy = new Property(0, String.class, "createdBy", false, "CREATED_BY");
        public final static Property CreatedDate = new Property(1, String.class, "createdDate", false, "CREATED_DATE");
        public final static Property LastModifiedBy = new Property(2, String.class, "lastModifiedBy", false, "LAST_MODIFIED_BY");
        public final static Property LastModifiedDate = new Property(3, String.class, "lastModifiedDate", false, "LAST_MODIFIED_DATE");
        public final static Property Id = new Property(4, String.class, "id", false, "ID");
        public final static Property FirstName = new Property(5, String.class, "firstName", false, "FIRST_NAME");
        public final static Property LastName = new Property(6, String.class, "lastName", false, "LAST_NAME");
        public final static Property Email = new Property(7, String.class, "email", false, "EMAIL");
        public final static Property Mobile = new Property(8, String.class, "mobile", false, "MOBILE");
        public final static Property Password = new Property(9, String.class, "password", false, "PASSWORD");
        public final static Property Enabled = new Property(10, String.class, "enabled", false, "ENABLED");
        public final static Property Description = new Property(11, String.class, "description", false, "DESCRIPTION");
        public final static Property InvitationAccepted = new Property(12, String.class, "invitationAccepted", false, "INVITATION_ACCEPTED");
        public final static Property ClientId = new Property(13, String.class, "clientId", false, "CLIENT_ID");
        public final static Property ChangePassword = new Property(14, String.class, "changePassword", false, "CHANGE_PASSWORD");
        public final static Property TermsAcceptedNew = new Property(15, String.class, "termsAcceptedNew", false, "TERMS_ACCEPTED_NEW");
        public final static Property ImageId = new Property(16, String.class, "imageId", false, "IMAGE_ID");
        public final static Property Using2FA = new Property(17, String.class, "using2FA", false, "USING2_FA");
        public final static Property Image = new Property(18, String.class, "image", false, "IMAGE");
    }


    public UserObjDao(DaoConfig config) {
        super(config);
    }
    
    public UserObjDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_OBJ\" (" + //
                "\"CREATED_BY\" TEXT," + // 0: createdBy
                "\"CREATED_DATE\" TEXT," + // 1: createdDate
                "\"LAST_MODIFIED_BY\" TEXT," + // 2: lastModifiedBy
                "\"LAST_MODIFIED_DATE\" TEXT," + // 3: lastModifiedDate
                "\"ID\" TEXT," + // 4: id
                "\"FIRST_NAME\" TEXT," + // 5: firstName
                "\"LAST_NAME\" TEXT," + // 6: lastName
                "\"EMAIL\" TEXT," + // 7: email
                "\"MOBILE\" TEXT," + // 8: mobile
                "\"PASSWORD\" TEXT," + // 9: password
                "\"ENABLED\" TEXT," + // 10: enabled
                "\"DESCRIPTION\" TEXT," + // 11: description
                "\"INVITATION_ACCEPTED\" TEXT," + // 12: invitationAccepted
                "\"CLIENT_ID\" TEXT," + // 13: clientId
                "\"CHANGE_PASSWORD\" TEXT," + // 14: changePassword
                "\"TERMS_ACCEPTED_NEW\" TEXT," + // 15: termsAcceptedNew
                "\"IMAGE_ID\" TEXT," + // 16: imageId
                "\"USING2_FA\" TEXT," + // 17: using2FA
                "\"IMAGE\" TEXT);"); // 18: image
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_OBJ\"";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, UserObj entity) {
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

        String firstName = entity.getFirstName();
        if (firstName != null) {
            stmt.bindString(6, firstName);
        }

        String lastName = entity.getLastName();
        if (lastName != null) {
            stmt.bindString(7, lastName);
        }

        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(8, email);
        }

        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(9, mobile);
        }

        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(10, password);
        }

        String enabled = entity.getEnabled();
        if (enabled != null) {
            stmt.bindString(11, enabled);
        }

        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(12, description);
        }

        String invitationAccepted = entity.getInvitationAccepted();
        if (invitationAccepted != null) {
            stmt.bindString(13, invitationAccepted);
        }

        String clientId = entity.getClientId();
        if (clientId != null) {
            stmt.bindString(14, clientId);
        }

        String changePassword = entity.getChangePassword();
        if (changePassword != null) {
            stmt.bindString(15, changePassword);
        }

        String termsAcceptedNew = entity.getTermsAcceptedNew();
        if (termsAcceptedNew != null) {
            stmt.bindString(16, termsAcceptedNew);
        }

        String imageId = entity.getImageId();
        if (imageId != null) {
            stmt.bindString(17, imageId);
        }

        String using2FA = entity.getUsing2FA();
        if (using2FA != null) {
            stmt.bindString(18, using2FA);
        }

        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(19, image);
        }
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserObj entity) {
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
 
        String firstName = entity.getFirstName();
        if (firstName != null) {
            stmt.bindString(6, firstName);
        }
 
        String lastName = entity.getLastName();
        if (lastName != null) {
            stmt.bindString(7, lastName);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(8, email);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(9, mobile);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(10, password);
        }
 
        String enabled = entity.getEnabled();
        if (enabled != null) {
            stmt.bindString(11, enabled);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(12, description);
        }
 
        String invitationAccepted = entity.getInvitationAccepted();
        if (invitationAccepted != null) {
            stmt.bindString(13, invitationAccepted);
        }
 
        String clientId = entity.getClientId();
        if (clientId != null) {
            stmt.bindString(14, clientId);
        }
 
        String changePassword = entity.getChangePassword();
        if (changePassword != null) {
            stmt.bindString(15, changePassword);
        }
 
        String termsAcceptedNew = entity.getTermsAcceptedNew();
        if (termsAcceptedNew != null) {
            stmt.bindString(16, termsAcceptedNew);
        }
 
        String imageId = entity.getImageId();
        if (imageId != null) {
            stmt.bindString(17, imageId);
        }
 
        String using2FA = entity.getUsing2FA();
        if (using2FA != null) {
            stmt.bindString(18, using2FA);
        }
 
        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(19, image);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public UserObj readEntity(Cursor cursor, int offset) {
        UserObj entity = new UserObj( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // createdBy
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // createdDate
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // lastModifiedBy
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // lastModifiedDate
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // id
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // firstName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // lastName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // email
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // mobile
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // password
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // enabled
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // description
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // invitationAccepted
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // clientId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // changePassword
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // termsAcceptedNew
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // imageId
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // using2FA
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18) // image
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserObj entity, int offset) {
        entity.setCreatedBy(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCreatedDate(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLastModifiedBy(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLastModifiedDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFirstName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLastName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setEmail(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMobile(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPassword(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setEnabled(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setDescription(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setInvitationAccepted(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setClientId(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setChangePassword(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setTermsAcceptedNew(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setImageId(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setUsing2FA(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setImage(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(UserObj entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(UserObj entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

    @Override
    protected boolean hasKey(UserObj entity) {
        return false;
    }
}
