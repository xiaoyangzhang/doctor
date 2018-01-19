package com.yhyt.health.vo;

import java.io.Serializable;
import java.util.Date;

public class DoctorResultVo  implements Serializable {
	
    private static final long serialVersionUID = -3848848723250467661L;
    
    private Long id;
    
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

    private Byte isOnline;

    private Byte state;

    private String title;

    private String duty;

    private String education;

    private String email;

    private String operator;

    private Date createTime;

    private Date lastTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
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
		return mobileNumber;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}