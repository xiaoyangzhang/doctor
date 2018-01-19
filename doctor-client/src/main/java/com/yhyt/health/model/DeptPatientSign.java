package com.yhyt.health.model;

import java.util.Date;

public class DeptPatientSign {
    private Long id;

    private Long departmentId;

    private Long patientId;

    private Date diagnoseDate;

    private String mainDoctor;

    private Byte type;

    private Byte isRepeat;

    private String diagnoseResult;

    private Byte state;

    private Byte source;

    private Date createTime;

    private Date reviewTime;

    private String refuseReason;

    private String operator;

    private String receiveDoctor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Date getDiagnoseDate() {
        return diagnoseDate;
    }

    public void setDiagnoseDate(Date diagnoseDate) {
        this.diagnoseDate = diagnoseDate;
    }

    public String getMainDoctor() {
        return mainDoctor;
    }

    public void setMainDoctor(String mainDoctor) {
        this.mainDoctor = mainDoctor == null ? null : mainDoctor.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(Byte isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getDiagnoseResult() {
        return diagnoseResult;
    }

    public void setDiagnoseResult(String diagnoseResult) {
        this.diagnoseResult = diagnoseResult == null ? null : diagnoseResult.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason == null ? null : refuseReason.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getReceiveDoctor() {
        return receiveDoctor;
    }

    public void setReceiveDoctor(String receiveDoctor) {
        this.receiveDoctor = receiveDoctor;
    }
}