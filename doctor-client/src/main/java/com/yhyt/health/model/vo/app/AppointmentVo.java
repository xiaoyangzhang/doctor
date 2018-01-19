package com.yhyt.health.model.vo.app;

import java.io.Serializable;
import java.util.Date;

/**
 * 患者预约医生对象
 */
public class AppointmentVo implements Serializable {

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 医生名称
     */
    private String doctorName;

    /**
     *  医生id
     */
    private Long doctorId;

    /**
     * 患者名称
     */
    private String patientName;

    /**
     * 患者id
     */
    private Long patientId;

    /**
     * 预约时间
     */
    private String appointmentTime;

    /**
     * 上下午标示
     */
    private String lastAfternoon;

    /**
     * 预约id
     */
    private Long appointmentId;


    /**
     * @return 医院名称
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * @return 部门名称
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @return 医生名称
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * @return 医生id
     */
    public Long getDoctorId() {
        return doctorId;
    }

    /**
     *
     * @return 患者名称
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     *
     * @return 预约时间
     */
    public String getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * 医院名称
     * @param hospitalName
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    /**
     * 部门名称
     * @param departmentName
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * 医生名称
     * @param doctorName
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     * 医生id
     * @param doctorId
     */
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * 患者名称
     * @param patientName
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     * 预约时间
     * @param appointmentTime
     */
    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getLastAfternoon() {
        return lastAfternoon;
    }

    public void setLastAfternoon(String lastAfternoon) {
        this.lastAfternoon = lastAfternoon;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
