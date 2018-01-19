package com.yhyt.health.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PatientCasearchives {
    private Long id;

    private Long patientId;

    private Long departmentId;

    private Long dialogId;

    private String caseId;

    private Byte type;

    private String suspectedcase;

    private String mainsuit;

    private String diseasedescription;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getSuspectedcase() {
        return suspectedcase;
    }

    public void setSuspectedcase(String suspectedcase) {
        this.suspectedcase = suspectedcase == null ? null : suspectedcase.trim();
    }

    public String getMainsuit() {
        return mainsuit;
    }

    public void setMainsuit(String mainsuit) {
        this.mainsuit = mainsuit == null ? null : mainsuit.trim();
    }

    public String getDiseasedescription() {
        return diseasedescription;
    }

    public void setDiseasedescription(String diseasedescription) {
        this.diseasedescription = diseasedescription == null ? null : diseasedescription.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}