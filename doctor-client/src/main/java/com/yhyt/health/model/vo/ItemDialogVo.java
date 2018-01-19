package com.yhyt.health.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author gsh
 * @create 2018-01-04 14:33
 **/
public class ItemDialogVo {

    private Long dialogId;
    private Long patientId;
    private String patientName;
    private String patientSex;
    private Long patientAges;
    private String resideLocation;
    private String doctorName;
    private Date appointmentTime;
    private String ampm;
    private String state;
    private String signState;
    private String roomId;
    private String title;

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public Long getPatientAges() {
        return patientAges;
    }

    public void setPatientAges(Long patientAges) {
        this.patientAges = patientAges;
    }

    public String getResideLocation() {
        return resideLocation;
    }

    public void setResideLocation(String resideLocation) {
        this.resideLocation = resideLocation;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    @JsonFormat(pattern = "yyyy年MM月dd日",timezone = "GMT+8")
    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
