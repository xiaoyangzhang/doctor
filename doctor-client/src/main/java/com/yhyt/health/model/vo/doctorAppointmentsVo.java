package com.yhyt.health.model.vo;

import java.io.Serializable;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月28日 上午9:22:03
 * 类说明
 */
public class doctorAppointmentsVo implements Serializable {

    private static final long serialVersionUID = -8222116520215513987L;

    private Long id;

    private String username;

    private String realname;

    private String title;

    private String amCount;

    private String pmCount;

    private String strongpoint;

    private String headImage;

    private Byte sex;

    private Byte state;

    private String duty;

    private String education;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmCount() {
        return amCount;
    }

    public void setAmCount(String amCount) {
        this.amCount = amCount;
    }

    public String getPmCount() {
        return pmCount;
    }

    public void setPmCount(String pmCount) {
        this.pmCount = pmCount;
    }

    public String getStrongpoint() {
//        if (null == strongpoint) {
//            return "";
//        }
        return strongpoint;
    }

    public void setStrongpoint(String strongpoint) {
        this.strongpoint = strongpoint;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}
