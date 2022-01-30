package com.mobiddiction.fishsmart.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TermsAndConditionsPolicyBean implements Parcelable {
    public String createdBy;
    public String createdDate;
    public String lastModifiedBy;
    public String lastModifiedDate;
    public String id;
    public String title;
    public String text;
    public String html;
    public String docUrl;
    public Boolean required;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createdBy);
        dest.writeString(this.createdDate);
        dest.writeString(this.lastModifiedBy);
        dest.writeString(this.lastModifiedDate);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.text);
        dest.writeString(this.html);
        dest.writeString(this.docUrl);
        dest.writeValue(this.required);
    }

    public TermsAndConditionsPolicyBean() {
    }

    protected TermsAndConditionsPolicyBean(Parcel in) {
        this.createdBy = in.readString();
        this.createdDate = in.readString();
        this.lastModifiedBy = in.readString();
        this.lastModifiedDate = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.text = in.readString();
        this.html = in.readString();
        this.docUrl = in.readString();
        this.required = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<TermsAndConditionsPolicyBean> CREATOR = new Parcelable.Creator<TermsAndConditionsPolicyBean>() {
        @Override
        public TermsAndConditionsPolicyBean createFromParcel(Parcel source) {
            return new TermsAndConditionsPolicyBean(source);
        }

        @Override
        public TermsAndConditionsPolicyBean[] newArray(int size) {
            return new TermsAndConditionsPolicyBean[size];
        }
    };
}