package com.yhyt.health.model;

import java.util.Date;

public class DialogAppointmentNotice {
    private Long id;

    private Long doctorId;

    private Long patientId;

    private Long dialogAppointmentId;

    private Byte state;

    private String message;

    private String isDoctorShow;

    private String isPatientShow;

    private Byte type;

    private Long departmentId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDialogAppointmentId() {
        return dialogAppointmentId;
    }

    public void setDialogAppointmentId(Long dialogAppointmentId) {
        this.dialogAppointmentId = dialogAppointmentId;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsDoctorShow() {
        return isDoctorShow;
    }

    public void setIsDoctorShow(String isDoctorShow) {
        this.isDoctorShow = isDoctorShow;
    }

    public String getIsPatientShow() {
        return isPatientShow;
    }

    public void setIsPatientShow(String isPatientShow) {
        this.isPatientShow = isPatientShow;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}