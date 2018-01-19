package com.yhyt.health.model.dto;

import com.yhyt.health.model.Doctor;

import java.io.Serializable;

public class DoctorDTO implements Serializable {

    private static final long serialVersionUID = -4822477184916155664L;
    private Doctor doctor;
    private Long hospitalId;
    private Long departmentId;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
