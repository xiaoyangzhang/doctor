package com.yhyt.health.model.vo.app;

import java.io.Serializable;
import java.util.List;

public class DeptDoctorAppVO implements Serializable {

    private static final long serialVersionUID = 3087401603447903831L;
    private String hospital;//医院名称
    private String department;//科室名称
    private Integer deptPersonNum;

    private List<DoctorAppVO> doctorAppVOList;

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

    public Integer getDeptPersonNum() {
        return deptPersonNum;
    }

    public void setDeptPersonNum(Integer deptPersonNum) {
        this.deptPersonNum = deptPersonNum;
    }

    public List<DoctorAppVO> getDoctorAppVOList() {
        return doctorAppVOList;
    }

    public void setDoctorAppVOList(List<DoctorAppVO> doctorAppVOList) {
        this.doctorAppVOList = doctorAppVOList;
    }
}
