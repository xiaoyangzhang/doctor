package com.yhyt.health.model.query;

import com.yhyt.health.page.BasePage;

import java.io.Serializable;

public class DoctorReviewQuery extends BasePage implements Serializable {

    private static final long serialVersionUID = 8761558390471830773L;
    private String mobileNumber;
    private String realname;
    private String hospitalName;
    private Byte reviewState;
    private String operator;

    public void setReviewState(Byte reviewState) {
        this.reviewState = reviewState;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Byte getReviewState() {
        return reviewState;
    }

    public void setReviewState(byte reviewState) {
        this.reviewState = reviewState;
    }
}
