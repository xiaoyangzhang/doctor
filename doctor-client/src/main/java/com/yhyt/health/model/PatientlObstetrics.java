package com.yhyt.health.model;

import java.io.Serializable;
import java.util.Date;

public class PatientlObstetrics implements Serializable {

    private static final long serialVersionUID = -80618019253202277L;
    private Long id;
    private String realname;
    private String username;
    private String birthday;
    private String sex;
    private Integer age;
    private String idno;
    private String hospital;
    private String insuranceLocation;
    private String hasinsurance;
    
    
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public String getInsuranceLocation() {
		return insuranceLocation;
	}
	public void setInsuranceLocation(String insuranceLocation) {
		this.insuranceLocation = insuranceLocation;
	}
	public String getHasinsurance() {
		return hasinsurance;
	}
	public void setHasinsurance(String hasinsurance) {
		this.hasinsurance = hasinsurance;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

}