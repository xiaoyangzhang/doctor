package com.yhyt.health.model;

import java.util.Date;

public class DeptDictDept {
    private Long id;

    private Long departmentId;

    private Long dictDepartmentId;

    private Byte dictDepartmentLevel;

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

    public Long getDictDepartmentId() {
        return dictDepartmentId;
    }

    public void setDictDepartmentId(Long dictDepartmentId) {
        this.dictDepartmentId = dictDepartmentId;
    }

    public Byte getDictDepartmentLevel() {
        return dictDepartmentLevel;
    }

    public void setDictDepartmentLevel(Byte dictDepartmentLevel) {
        this.dictDepartmentLevel = dictDepartmentLevel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}