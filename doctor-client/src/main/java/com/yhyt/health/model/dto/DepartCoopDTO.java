package com.yhyt.health.model.dto;

import java.io.Serializable;

public class DepartCoopDTO implements Serializable {

    private static final long serialVersionUID = -5037829480649815128L;
    private String hospital;
    private String department;
    private byte state;//合作状态

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
