package com.yhyt.health.model.query;

import com.yhyt.health.page.BasePage;

import java.io.Serializable;
import java.util.Date;

public class DoctorQuery extends BasePage implements Serializable {

    private static final long serialVersionUID = -800685918319192124L;
    private String username;
    private String realname;
    private Date registerTimeStart;
    private Date registerTimeEnd;
    private Long deptDoctorId;//科室医生关联表 id
    private Byte state;//认证状态
    private Long diseaseId;
    private Long id;//医生 id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Long getDeptDoctorId() {
        return deptDoctorId;
    }

    public void setDeptDoctorId(Long deptDoctorId) {
        this.deptDoctorId = deptDoctorId;
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

    public Date getRegisterTimeStart() {
        return registerTimeStart;
    }

    public void setRegisterTimeStart(Date registerTimeStart) {
        this.registerTimeStart = registerTimeStart;
    }

    public Date getRegisterTimeEnd() {
        return registerTimeEnd;
    }

    public void setRegisterTimeEnd(Date registerTimeEnd) {
        this.registerTimeEnd = registerTimeEnd;
    }
}
