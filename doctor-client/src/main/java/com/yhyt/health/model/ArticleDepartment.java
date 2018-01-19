package com.yhyt.health.model;

import java.util.Date;

public class ArticleDepartment {
    private Long id;

    private Long articleId;

    private Long dictDepartmentId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getDictDepartmentId() {
        return dictDepartmentId;
    }

    public void setDictDepartmentId(Long dictDepartmentId) {
        this.dictDepartmentId = dictDepartmentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}