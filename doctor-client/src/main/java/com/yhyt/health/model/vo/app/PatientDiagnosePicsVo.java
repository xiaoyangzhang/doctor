package com.yhyt.health.model.vo.app;

import java.util.Date;

/**
 * 图片Vo类
 *
 * @author gsh
 * @create 2017-09-04 19:48
 **/
public class PatientDiagnosePicsVo {

    private Long id;
    private String picUrl;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
