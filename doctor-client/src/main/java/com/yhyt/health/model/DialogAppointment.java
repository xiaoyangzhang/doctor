package com.yhyt.health.model;

import java.util.Date;

public class DialogAppointment {
    private Long id;

    private Long dialogDetailId;

    private Long doctorIdLaunch;

    private Long doctorIdAppointment;

    private Long hospitalId;

    private Long departmentId;

    private Long patientIdAppointment;

    private Byte state;

    private Byte signState;

    private Date appointmentTime;

    private String demand;

    private Date createTime;

    private Byte ampm;

    private String treatRoom;

    private String treatNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDialogDetailId() {
        return dialogDetailId;
    }

    public void setDialogDetailId(Long dialogDetailId) {
        this.dialogDetailId = dialogDetailId;
    }

    public Long getDoctorIdLaunch() {
        return doctorIdLaunch;
    }

    public void setDoctorIdLaunch(Long doctorIdLaunch) {
        this.doctorIdLaunch = doctorIdLaunch;
    }

    public Long getDoctorIdAppointment() {
        return doctorIdAppointment;
    }

    public void setDoctorIdAppointment(Long doctorIdAppointment) {
        this.doctorIdAppointment = doctorIdAppointment;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public Byte getAmpm() {
        return ampm;
    }

    public void setAmpm(Byte ampm) {
        this.ampm = ampm;
    }

    public Long getPatientIdAppointment() {
        return patientIdAppointment;
    }

    public void setPatientIdAppointment(Long patientIdAppointment) {
        this.patientIdAppointment = patientIdAppointment;
    }

    public String getTreatRoom() {
        return treatRoom;
    }

    public void setTreatRoom(String treatRoom) {
        this.treatRoom = treatRoom;
    }

    public String getTreatNum() {
        return treatNum;
    }

    public void setTreatNum(String treatNum) {
        this.treatNum = treatNum;
    }
}