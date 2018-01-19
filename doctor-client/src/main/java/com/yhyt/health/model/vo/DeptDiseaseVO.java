package com.yhyt.health.model.vo;

import java.io.Serializable;

public class DeptDiseaseVO implements Serializable {

    private static final long serialVersionUID = 7409516618726228674L;
    private Long id;//科室疾病关联表主键
    private String diseaseName;
    private Long diseaseId;
    private boolean flag=false;//是否添加，默认 false

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
}
