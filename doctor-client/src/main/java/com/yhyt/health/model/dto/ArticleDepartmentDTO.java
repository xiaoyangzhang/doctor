package com.yhyt.health.model.dto;

import java.io.Serializable;

public class ArticleDepartmentDTO implements Serializable{

    private static final long serialVersionUID = -4501904204538344649L;
    private Long id;
    private String name;
    private Long dictDepartmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDictDepartmentId() {
        return dictDepartmentId;
    }

    public void setDictDepartmentId(Long dictDepartmentId) {
        this.dictDepartmentId = dictDepartmentId;
    }
}
