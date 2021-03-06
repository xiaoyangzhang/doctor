package com.yhyt.health.model.query;

import com.yhyt.health.page.BasePage;

import java.io.Serializable;

public class DiseaseQuery extends BasePage implements Serializable {

    private static final long serialVersionUID = 5332133097423885089L;
    private String name;

    private Long id;//科室 id
    private Long doctorId;
//    private Long deptId;

    private Long questionnaireId;//问卷 id

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

   /* public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }*/

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
}
