package com.yhyt.health.model;

import java.util.Date;

public class DeptEvaluateLog {
    private Long id;

    private Long departmentId;

    private Float speedScore;

    private Float attitudeScore;

    private Float satisfyScore;

    private Long patientId;

    private String content;

    private Date createTime;

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

    public Float getSpeedScore() {
        return speedScore;
    }

    public void setSpeedScore(Float speedScore) {
        this.speedScore = speedScore;
    }

    public Float getAttitudeScore() {
        return attitudeScore;
    }

    public void setAttitudeScore(Float attitudeScore) {
        this.attitudeScore = attitudeScore;
    }

    public Float getSatisfyScore() {
        return satisfyScore;
    }

    public void setSatisfyScore(Float satisfyScore) {
        this.satisfyScore = satisfyScore;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}