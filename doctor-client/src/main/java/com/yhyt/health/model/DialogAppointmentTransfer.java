package com.yhyt.health.model;

import java.io.Serializable;
import java.util.Date;

public class DialogAppointmentTransfer implements Serializable {
    private static final long serialVersionUID = 453266930985321705L;
    private Long id;

    private Long patientId;

    private Long hospitalId;

    private Long dialogDetailId;

    private Long departmentId;

    private Long launchDoctorId;

    private Long doctorId;

    private Byte state;

    private Byte signState;

    private Date appointmentTime;

    private Date createTime;
    private Long operatorDoctorId;
    private Long launchDepartmentId;

    public Long getOperatorDoctorId() {
        return operatorDoctorId;
    }

    public void setOperatorDoctorId(Long operatorDoctorId) {
        this.operatorDoctorId = operatorDoctorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Long getDialogDetailId() {
        return dialogDetailId;
    }

    public void setDialogDetailId(Long dialogDetailId) {
        this.dialogDetailId = dialogDetailId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getLaunchDoctorId() {
        return launchDoctorId;
    }

    public void setLaunchDoctorId(Long launchDoctorId) {
        this.launchDoctorId = launchDoctorId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Byte getSignState() {
        return signState;
    }

    public void setSignState(Byte signState) {
        this.signState = signState;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getLaunchDepartmentId() {
        return launchDepartmentId;
    }

    public void setLaunchDepartmentId(Long launchDepartmentId) {
        this.launchDepartmentId = launchDepartmentId;
    }
}