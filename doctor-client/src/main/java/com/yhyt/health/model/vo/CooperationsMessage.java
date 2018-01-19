package com.yhyt.health.model.vo;

import java.util.Date;

public class CooperationsMessage {

    private Long id;

    private String state;

    private String fromLogo;

    private String fromHospitaName;

    private String fromDepartmentName;

    private String toHospitalName;

    private String toDepartmentName;

    private String toLogo;

    private String type;

    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFromLogo() {
        return fromLogo;
    }

    public void setFromLogo(String fromLogo) {
        this.fromLogo = fromLogo;
    }

    public String getToLogo() {
        return toLogo;
    }

    public void setToLogo(String toLogo) {
        this.toLogo = toLogo;
    }

    public String getFromHospitaName() {
        return fromHospitaName;
    }

    public void setFromHospitaName(String fromHospitaName) {
        this.fromHospitaName = fromHospitaName;
    }

    public String getFromDepartmentName() {
        return fromDepartmentName;
    }

    public void setFromDepartmentName(String fromDepartmentName) {
        this.fromDepartmentName = fromDepartmentName;
    }

    public String getToHospitalName() {
        return toHospitalName;
    }

    public void setToHospitalName(String toHospitalName) {
        this.toHospitalName = toHospitalName;
    }

    public String getToDepartmentName() {
        return toDepartmentName;
    }

    public void setToDepartmentName(String toDepartmentName) {
        this.toDepartmentName = toDepartmentName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
