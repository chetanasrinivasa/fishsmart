package com.mobiddiction.fishsmart.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import java.util.List;
// KEEP INCLUDES END
/**
 * Entity mapped to table "SALT_DATA_NEW".
 */
public class SaltDataNew {

    private Long id;

    // KEEP FIELDS - put your custom fields here
    public List<SaltWaterNewData> SALTWATER;
    // KEEP FIELDS END

    public SaltDataNew() {
    }

    public SaltDataNew(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}