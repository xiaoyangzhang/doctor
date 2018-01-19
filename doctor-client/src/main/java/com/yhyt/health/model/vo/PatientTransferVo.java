package com.yhyt.health.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author gsh
 * @create 2017-12-07 15:15
 **/
public class PatientTransferVo {

    private Long transferId;
    private Long launchDepartmentId;
    private String launchDepartmentLogo;
    private String launchDepartmentName;
    private String launchHospitalName;
    private Long departmentId;
    private String departmentLogo;
    private String departmentName;
    private String hospitalName;
    private String state;
    private String stateName;
    private String roomId;
    private Date appointmentTime;

    @JsonFormat(pattern = "yyyy年MM月dd日",timezone = "GMT+8")
    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getLaunchDepartmentLogo() {
        return launchDepartmentLogo;
    }

    public void setLaunchDepartmentLogo(String launchDepartmentLogo) {
        this.launchDepartmentLogo = launchDepartmentLogo;
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentLogo() {
        return departmentLogo;
    }

    public void setDepartmentLogo(String departmentLogo) {
        this.departmentLogo = departmentLogo;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Long getLaunchDepartmentId() {
        return launchDepartmentId;
    }

    public void setLaunchDepartmentId(Long launchDepartmentId) {
        this.launchDepartmentId = launchDepartmentId;
    }

    public String getLaunchDepartmentName() {
        return launchDepartmentName;
    }

    public void setLaunchDepartmentName(String launchDepartmentName) {
        this.launchDepartmentName = launchDepartmentName;
    }

    public String getLaunchHospitalName() {
        return launchHospitalName;
    }

    public void setLaunchHospitalName(String launchHospitalName) {
        this.launchHospitalName = launchHospitalName;
    }
}
