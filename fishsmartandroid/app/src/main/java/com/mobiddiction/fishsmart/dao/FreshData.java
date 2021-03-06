package com.mobiddiction.fishsmart.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import java.util.List;
// KEEP INCLUDES END
/**
 * Entity mapped to table "FRESH_DATA".
 */
public class FreshData {

    private Long id;
    private Boolean success;

    // KEEP FIELDS - put your custom fields here
    public List<Fresh> data;
    // KEEP FIELDS END

    public FreshData() {
    }

    public FreshData(Long id) {
        this.id = id;
    }

    public FreshData(Long id, Boolean success) {
        this.id = id;
        this.success = success;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
