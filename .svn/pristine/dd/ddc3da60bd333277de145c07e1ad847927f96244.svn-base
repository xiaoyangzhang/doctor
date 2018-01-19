package com.yhyt.health.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class DoctorReviewVO implements Serializable {
    private static final long serialVersionUID = -5511003419165417060L;
    private Long id;
    private Byte sex;
    private String realname;
    private String reason;
    private String mobileNumber;
    private String title;
    private String summary;
    private String strongpoint;
    private String practiceCertificate;
    private String qualificationCertificate;
    private Byte state;
    private Date applyTime;
    private Date reviewTime;
    private String hospitalName;
    private String operator;
    private String departmentName;
    private Date birthday;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operatorName) {
        this.operator = operatorName;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStrongpoint() {
        return strongpoint;
    }

    public void setStrongpoint(String strongpoint) {
        this.strongpoint = strongpoint;
    }

    public String getPracticeCertificate() {
        return practiceCertificate;
    }

    public void setPracticeCertificate(String practiceCertificate) {
        this.practiceCertificate = practiceCertificate;
    }

    public String getQualificationCertificate() {
        return qualificationCertificate;
    }

    public void setQualificationCertificate(String qualificationCertificate) {
        this.qualificationCertificate = qualificationCertificate;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMobileNumber() {
        if (!StringUtils.isEmpty(mobileNumber))
            return mobileNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        return "";
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
