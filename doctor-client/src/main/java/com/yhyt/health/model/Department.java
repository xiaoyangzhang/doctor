package com.yhyt.health.model;

import java.util.Date;

public class Department {
    private Long id;
    private Long hospitalId;
    private String name;
    private String logo;
    private String summary;
    private String exhibitUrl;
    private String healthUrl;
    private String typicalIllUrl;
    private Byte isFree;
    private String serviceTime;
    private Date createTime;
    private Date updateTime;
    private String notice;
    private String operator;

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.name = name == null ? null : name.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getExhibitUrl() {
        return exhibitUrl;
    }

    public void setExhibitUrl(String exhibitUrl) {
        this.exhibitUrl = exhibitUrl == null ? null : exhibitUrl.trim();
    }

    public String getHealthUrl() {
        return healthUrl;
    }

    public void setHealthUrl(String healthUrl) {
        this.healthUrl = healthUrl == null ? null : healthUrl.trim();
    }

    public String getTypicalIllUrl() {
        return typicalIllUrl;
    }

    public void setTypicalIllUrl(String typicalIllUrl) {
        this.typicalIllUrl = typicalIllUrl == null ? null : typicalIllUrl.trim();
    }

    public Byte getIsFree() {
        return isFree;
    }

    public void setIsFree(Byte isFree) {
        this.isFree = isFree;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}