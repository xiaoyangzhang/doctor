package com.yhyt.health.model.vo.app;

/**
 * @author gsh
 * @create 2017-12-08 11:07
 **/
public class DiagnoseInfoVo {
    private String mainDoctor;
    private String type;
    private String diagnoseResult;
    private String hospital;

    public String getMainDoctor() {
        return mainDoctor;
    }

    public void setMainDoctor(String mainDoctor) {
        this.mainDoctor = mainDoctor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiagnoseResult() {
        return diagnoseResult;
    }

    public void setDiagnoseResult(String diagnoseResult) {
        this.diagnoseResult = diagnoseResult;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}
