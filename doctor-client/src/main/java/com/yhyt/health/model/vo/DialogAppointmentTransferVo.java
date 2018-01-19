package com.yhyt.health.model.vo;

import java.util.Date;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月29日 上午9:45:08
 * 类说明
 */
public class DialogAppointmentTransferVo {

    private Long id;

    private Long patientId;

    private String patientName;

    private String patientHeadImage;

    private Long hospitalId;

    private String hospitalLogo;

    private String hospitalName;

    private Long dialogDetailId;

    private Long departmentId;

    private String departmentLogo;

    private String departmentName;

    private Long launchDoctorId;

    private Long launchDepartmentId;

    private String launchDoctorName;

    private String launchDepartmentName;

    private String launchHospitalName;

    private String launchHospitalLogo;

    private String launchDepartmentLogo;

    private Long doctorId;

    private String doctorName;

    private Byte state;

    private Byte signState;

    private Date appointmentTime;

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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getLaunchDoctorId() {
        return launchDoctorId;
    }

    public void setLaunchDoctorId(Long launchDoctorId) {
        this.launchDoctorId = launchDoctorId;
    }

    public String getLaunchDoctorName() {
        return launchDoctorName;
    }

    public void setLaunchDoctorName(String launchDoctorName) {
        this.launchDoctorName = launchDoctorName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public String getHospitalLogo() {
        return hospitalLogo;
    }

    public void setHospitalLogo(String hospitalLogo) {
        this.hospitalLogo = hospitalLogo;
    }

    public String getLaunchHospitalLogo() {
        return launchHospitalLogo;
    }

    public void setLaunchHospitalLogo(String launchHospitalLogo) {
        this.launchHospitalLogo = launchHospitalLogo;
    }

    public String getPatientHeadImage() {
        return patientHeadImage;
    }

    public void setPatientHeadImage(String patientHeadImage) {
        this.patientHeadImage = patientHeadImage;
    }

    public String getDepartmentLogo() {
        return departmentLogo;
    }

    public void setDepartmentLogo(String departmentLogo) {
        this.departmentLogo = departmentLogo;
    }

    public String getLaunchDepartmentLogo() {
        return launchDepartmentLogo;
    }

    public void setLaunchDepartmentLogo(String launchDepartmentLogo) {
        this.launchDepartmentLogo = launchDepartmentLogo;
    }

    public Long getLaunchDepartmentId() {
        return launchDepartmentId;
    }

    public void setLaunchDepartmentId(Long launchDepartmentId) {
        this.launchDepartmentId = launchDepartmentId;
    }
}
