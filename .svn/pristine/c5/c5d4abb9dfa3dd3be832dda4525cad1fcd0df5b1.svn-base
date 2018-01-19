package com.yhyt.health.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class DeptCooperationReview implements Serializable {
    private static final long serialVersionUID = -6835412274115080558L;
    private Long id;

    private Long deptCooperationId;

    private Long hospitalId;

    private Long deparmentId;

    private Byte type;

    private Long doctorIdReview;

    private String message;

    private Byte state;

    private Date createTime;
    private Date reviewTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptCooperationId() {
        return deptCooperationId;
    }

    public void setDeptCooperationId(Long deptCooperationId) {
        this.deptCooperationId = deptCooperationId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Long getDeparmentId() {
        return deparmentId;
    }

    public void setDeparmentId(Long deparmentId) {
        this.deparmentId = deparmentId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getDoctorIdReview() {
        return doctorIdReview;
    }

    public void setDoctorIdReview(Long doctorIdReview) {
        this.doctorIdReview = doctorIdReview;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}