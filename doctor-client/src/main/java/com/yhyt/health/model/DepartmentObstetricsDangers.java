package com.yhyt.health.model;

import java.util.Date;

public class DepartmentObstetricsDangers {
    private Long id;

    private Long departmentObstetricsId;

    private Long dictionaryId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentObstetricsId() {
        return departmentObstetricsId;
    }

    public void setDepartmentObstetricsId(Long departmentObstetricsId) {
        this.departmentObstetricsId = departmentObstetricsId;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}