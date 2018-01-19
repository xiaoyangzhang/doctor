package com.yhyt.health.model.vo;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class DoctorExtVO implements Serializable {
    private static final long serialVersionUID = 6102522958392513615L;
    private Long id;
    private String hospitalName;
    private String departmentName;
    private String title;
    private String duty;
    private String education;
    private String strongpoint;
    private String summary;
    private String username;
    private String realname;
    private String diseases;
    private Long doctorDiseaseId;
    private Integer seq;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Long getDoctorDiseaseId() {
        return doctorDiseaseId;
    }

    public void setDoctorDiseaseId(Long doctorDiseaseId) {
        this.doctorDiseaseId = doctorDiseaseId;
    }

    public String getDiseases() {
        if (diseases == null) {
            return "";
        }
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getUsername() {
        if (!StringUtils.isEmpty(username))
            return username.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        return "";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getStrongpoint() {
        return strongpoint;
    }

    public void setStrongpoint(String strongpoint) {
        this.strongpoint = strongpoint;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
