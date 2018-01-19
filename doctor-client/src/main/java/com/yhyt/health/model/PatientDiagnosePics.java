package com.yhyt.health.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PatientDiagnosePics {
    private Long id;

    private String caseId;

    private Long deptPatientSignId;

    private Long dialogId;

    private Long type;

    private String picUrl;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId == null ? null : caseId.trim();
    }

    public Long getDeptPatientSignId() {
        return deptPatientSignId;
    }

    public void setDeptPatientSignId(Long deptPatientSignId) {
        this.deptPatientSignId = deptPatientSignId;
    }

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}