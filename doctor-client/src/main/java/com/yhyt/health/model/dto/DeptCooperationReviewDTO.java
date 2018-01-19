package com.yhyt.health.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class DeptCooperationReviewDTO implements Serializable {

    private static final long serialVersionUID = -640502134576670344L;
    private String initiatorHospital;//发起医院
    private String initiatorDepartment;//发起科室
    private String initiatorDoctor;//发起医生
    private Date initiatorTime;//发起时间
    private String initiatorReviewDoctor;//发起科室审批人
    private Date initiatorReviewTime;//发起科室审批时间
    private String localReviewDoctor;//合作科室审批人
    private String localReviewHospital;//合作医院
    private String localReviewDept;//合作科室
    private Date localReviewTime;//合作科室审批时间
    private byte state;
    private String stateValue;

    public String getLocalReviewHospital() {
        return localReviewHospital;
    }

    public void setLocalReviewHospital(String localReviewHospital) {
        this.localReviewHospital = localReviewHospital;
    }

    public String getLocalReviewDept() {
        return localReviewDept;
    }

    public void setLocalReviewDept(String localReviewDept) {
        this.localReviewDept = localReviewDept;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getInitiatorTime() {
        return initiatorTime;
    }

    public void setInitiatorTime(Date initiatorTime) {
        this.initiatorTime = initiatorTime;
    }

    public String getInitiatorHospital() {
        return initiatorHospital;
    }

    public void setInitiatorHospital(String initiatorHospital) {
        this.initiatorHospital = initiatorHospital;
    }

    public String getInitiatorDepartment() {
        return initiatorDepartment;
    }

    public void setInitiatorDepartment(String initiatorDepartment) {
        this.initiatorDepartment = initiatorDepartment;
    }

    public String getInitiatorDoctor() {
        return initiatorDoctor;
    }

    public void setInitiatorDoctor(String initiatorDoctor) {
        this.initiatorDoctor = initiatorDoctor;
    }

    public String getLocalReviewDoctor() {
        return localReviewDoctor;
    }

    public void setLocalReviewDoctor(String localReviewDoctor) {
        this.localReviewDoctor = localReviewDoctor;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")

    public Date getLocalReviewTime() {
        return localReviewTime;
    }

    public void setLocalReviewTime(Date localReviewTime) {
        this.localReviewTime = localReviewTime;
    }

    public String getInitiatorReviewDoctor() {
        return initiatorReviewDoctor;
    }

    public void setInitiatorReviewDoctor(String initiatorReviewDoctor) {
        this.initiatorReviewDoctor = initiatorReviewDoctor;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")

    public Date getInitiatorReviewTime() {
        return initiatorReviewTime;
    }

    public void setInitiatorReviewTime(Date initiatorReviewTime) {
        this.initiatorReviewTime = initiatorReviewTime;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public String getStateValue() {
        return stateValue;
    }

    public void setStateValue(String stateValue) {
        this.stateValue = stateValue;
    }
}
