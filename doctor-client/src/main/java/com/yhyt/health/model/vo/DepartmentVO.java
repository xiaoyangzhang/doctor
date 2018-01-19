package com.yhyt.health.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yhyt.health.model.dto.DepartCoopDTO;
import com.yhyt.health.model.dto.DeptCooperationReviewDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DepartmentVO implements Serializable {

    private static final long serialVersionUID = 197672271830466440L;
    private String name;//科室名称
    private String hospital;
    private Long hospitalId;
    private String departmentCategory;//科室分类名称
    private String summary;
    private Long id;//科室 id
    private String admin;
    private String icon;
    private byte departLevel;//科室分类层级
    private Long categoryId;//科室分类 id
    private String exhibitUrl;
    private String healthUrl;
    private String typicalIllUrl;
    private Byte isFree;
    private String notice;
    private String operator;
    private Date updateTime;
    private String qrCode;//科室二維碼

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getNotice() {
        return notice;
    }

    private List<DeptCooperationReviewDTO> departCoopList;//外院合作科室-本科室为合作方
    private List<DeptCooperationReviewDTO> departCoopOriList;//外院合作科室-本科室为发起方

    private List<DepartCoopDTO> departCoopDTOList;//本院合作科室

    public List<DepartCoopDTO> getDepartCoopDTOList() {
        return departCoopDTOList;
    }

    public void setDepartCoopDTOList(List<DepartCoopDTO> departCoopDTOList) {
        this.departCoopDTOList = departCoopDTOList;
    }

    public List<DeptCooperationReviewDTO> getDepartCoopOriList() {
        return departCoopOriList;
    }

    public void setDepartCoopOriList(List<DeptCooperationReviewDTO> departCoopOriList) {
        this.departCoopOriList = departCoopOriList;
    }

    public List<DeptCooperationReviewDTO> getDepartCoopList() {
        return departCoopList;
    }

    public void setDepartCoopList(List<DeptCooperationReviewDTO> departCoopList) {
        this.departCoopList = departCoopList;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getExhibitUrl() {
        return exhibitUrl;
    }

    public void setExhibitUrl(String exhibitUrl) {
        this.exhibitUrl = exhibitUrl;
    }

    public String getHealthUrl() {
        return healthUrl;
    }

    public void setHealthUrl(String healthUrl) {
        this.healthUrl = healthUrl;
    }

    public String getTypicalIllUrl() {
        return typicalIllUrl;
    }

    public void setTypicalIllUrl(String typicalIllUrl) {
        this.typicalIllUrl = typicalIllUrl;
    }

    public Byte getIsFree() {
        return isFree;
    }

    public void setIsFree(Byte isFree) {
        this.isFree = isFree;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public byte getDepartLevel() {
        return departLevel;
    }

    public void setDepartLevel(byte departLevel) {
        this.departLevel = departLevel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentCategory() {
        return departmentCategory;
    }

    public void setDepartmentCategory(String departmentCategory) {
        this.departmentCategory = departmentCategory;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }
}
