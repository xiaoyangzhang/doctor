package com.yhyt.health.model.vo.app;

import java.io.Serializable;
import java.util.Date;

public class AppointmentTransferPatientVO implements Serializable {

    private static final long serialVersionUID = -1992465175065857053L;
    private Long id; //转诊 id
    private String name;//患者姓名
    private String headImage;//患者头像
    private byte sex;
    private Integer age;
    private Date birthday;
    private String mainDisease;//疑似病例
    private String sourceHospital;
    private String sourceDepartment;
    private Date applyTime;
    private String city;
    private String idType; //1:求诊,2:预约,3:转诊
    private Long departmentId; //科室 id
    private Long requestPatientId; //患者 id
    private String state;
    private String roomId;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMainDisease() {
        return mainDisease;
    }

    public void setMainDisease(String mainDisease) {
        this.mainDisease = mainDisease;
    }

    public String getSourceHospital() {
        return sourceHospital;
    }

    public void setSourceHospital(String sourceHospital) {
        this.sourceHospital = sourceHospital;
    }

    public String getSourceDepartment() {
        return sourceDepartment;
    }

    public void setSourceDepartment(String sourceDepartment) {
        this.sourceDepartment = sourceDepartment;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getRequestPatientId() {
        return requestPatientId;
    }

    public void setRequestPatientId(Long requestPatientId) {
        this.requestPatientId = requestPatientId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
