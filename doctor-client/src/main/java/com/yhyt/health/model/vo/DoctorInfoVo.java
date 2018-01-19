package com.yhyt.health.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yhyt.health.model.SysBlacklist;
import com.yhyt.health.model.SysFeedback;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * app返回医生信息
 *
 * @author gsh
 * @create 2017-08-30 19:39
 **/
public class DoctorInfoVo implements Serializable {

    private static final long serialVersionUID = 4492709150502350120L;
    private Long id;
    private Long departDoctorId;//科室医生关联表 id
    private Long diseaseDoctorId;//疾病医生关联表 id
    private String username;
    private String realname;
    private Date birthday;
    private String headImage;
    private Byte sex;
    private String mobileNumber;
    private Long departmentId;
    private String summary;
    private String strongpoint;
    private String practiceCertificate;
    private String qualificationCertificate;
    private Byte isBlacklist;
    private String operator;
    private Byte isOnline;
    private Byte state;
    private String title;
    private String duty;
    private String education;
    private String email;
    private Long hospitalId;
    private String name;//科室名称
    private String logo;
    private List<SysBlacklist> blacklistList;
    private List<SysFeedback> feedbackList;
    private byte isAdmin;
    private Date createTime;
    private Date lastTime;
    private String department;
    private String hospital;
    private String auditState;//医生审核状态

    public Long getDiseaseDoctorId() {
        return diseaseDoctorId;
    }

    public void setDiseaseDoctorId(Long diseaseDoctorId) {
        this.diseaseDoctorId = diseaseDoctorId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartDoctorId() {
        return departDoctorId;
    }

    public void setDepartDoctorId(Long departDoctorId) {
        this.departDoctorId = departDoctorId;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getMobileNumber() {
        if (!StringUtils.isEmpty(mobileNumber))
            return mobileNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        return "";
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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

    public Byte getIsBlacklist() {
        return isBlacklist;
    }

    public void setIsBlacklist(Byte isBlacklist) {
        this.isBlacklist = isBlacklist;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Byte getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Byte isOnline) {
        this.isOnline = isOnline;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SysBlacklist> getBlacklistList() {
        return blacklistList;
    }

    public void setBlacklistList(List<SysBlacklist> blacklistList) {
        this.blacklistList = blacklistList;
    }

    public List<SysFeedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<SysFeedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }
}
