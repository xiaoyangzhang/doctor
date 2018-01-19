package com.yhyt.health.model;

import java.io.Serializable;
import java.util.Date;

public class DeptCooperation implements Serializable {

    private static final long serialVersionUID = 7181720744248128798L;
    private Long id;

    private Long doctorIdOriginator;

    private Long hospitalIdOriginator;

    private Long deparmentIdOriginator;

    private Long doctorIdCooperation;

    private Long hospitalIdCooperation;

    private Long deparmentIdCooperation;

    private Byte state;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorIdOriginator() {
        return doctorIdOriginator;
    }

    public void setDoctorIdOriginator(Long doctorIdOriginator) {
        this.doctorIdOriginator = doctorIdOriginator;
    }

    public Long getHospitalIdOriginator() {
        return hospitalIdOriginator;
    }

    public void setHospitalIdOriginator(Long hospitalIdOriginator) {
        this.hospitalIdOriginator = hospitalIdOriginator;
    }

    public Long getDeparmentIdOriginator() {
        return deparmentIdOriginator;
    }

    public void setDeparmentIdOriginator(Long deparmentIdOriginator) {
        this.deparmentIdOriginator = deparmentIdOriginator;
    }

    public Long getHospitalIdCooperation() {
        return hospitalIdCooperation;
    }

    public void setHospitalIdCooperation(Long hospitalIdCooperation) {
        this.hospitalIdCooperation = hospitalIdCooperation;
    }

    public Long getDeparmentIdCooperation() {
        return deparmentIdCooperation;
    }

    public void setDeparmentIdCooperation(Long deparmentIdCooperation) {
        this.deparmentIdCooperation = deparmentIdCooperation;
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

    public Long getDoctorIdCooperation() {
        return doctorIdCooperation;
    }

    public void setDoctorIdCooperation(Long doctorIdCooperation) {
        this.doctorIdCooperation = doctorIdCooperation;
    }

}