package com.yhyt.health.model.vo;

import java.util.Date;

/**
 * 待诊患者信息
 *
 * @author gsh
 * @create 2017-08-30 10:06
 **/
public class AppointmentPatientVo {

    private Long id;

    private Long requestPatientId;

    private Integer idType;//转诊 id：3求诊 id:1预约id：2

    private String state;

    private String realname;

    private String headImage;

    private String sex;

    private Integer age;

    private String newlyDiagnosed;//1初诊 2复诊

    private String hasInsurance;//是否参加社保1:是2:否

    private String appointmentDoctorName;

    private String departmentName;

    private String hospitalName;

    private Date appointmentTime;

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNewlyDiagnosed() {
        return newlyDiagnosed;
    }

    public void setNewlyDiagnosed(String newlyDiagnosed) {
        this.newlyDiagnosed = newlyDiagnosed;
    }

    public String getHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(String hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public String getAppointmentDoctorName() {
        return appointmentDoctorName;
    }

    public void setAppointmentDoctorName(String appointmentDoctorName) {
        this.appointmentDoctorName = appointmentDoctorName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestPatientId() {
        return requestPatientId;
    }

    public void setRequestPatientId(Long requestPatientId) {
        this.requestPatientId = requestPatientId;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
