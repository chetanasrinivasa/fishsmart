package com.mobiddiction.fishsmart.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class TermsAndConditionsBean implements Parcelable {
    public String createdBy;
    public String createdDate;
    public String lastModifiedBy;
    public String lastModifiedDate;
    public String id;
    public String text;
    public String html;
    public ArrayList<TermsAndConditionsPolicyBean> policies = new ArrayList<>();

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
        dest.writeString(this.text);
        dest.writeString(this.html);
        dest.writeTypedList(this.policies);
    }

    public TermsAndConditionsBean() {
    }

    protected TermsAndConditionsBean(Parcel in) {
        this.createdBy = in.readString();
        this.createdDate = in.readString();
        this.lastModifiedBy = in.readString();
        this.lastModifiedDate = in.readString();
        this.id = in.readString();
        this.text = in.readString();
        this.html = in.readString();
        this.policies = in.createTypedArrayList(TermsAndConditionsPolicyBean.CREATOR);
    }

    public static final Parcelable.Creator<TermsAndConditionsBean> CREATOR = new Parcelable.Creator<TermsAndConditionsBean>() {
        @Override
        public TermsAndConditionsBean createFromParcel(Parcel source) {
            return new TermsAndConditionsBean(source);
        }

        @Override
        public TermsAndConditionsBean[] newArray(int size) {
            return new TermsAndConditionsBean[size];
        }
    };

    public ArrayList<TermsAndConditionsPolicyBean> getPolicies() {
        return policies;
    }

    public void setPolicies(ArrayList<TermsAndConditionsPolicyBean> policies) {
        this.policies = policies;
    }
}