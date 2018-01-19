package com.yhyt.health.model;

import java.util.Date;

public class DeptLabel extends DeptLabelKey {
    private Long departmentId;

    private Date createTime;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}