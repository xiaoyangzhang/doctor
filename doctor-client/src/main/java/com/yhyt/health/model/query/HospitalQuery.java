package com.yhyt.health.model.query;

import com.yhyt.health.page.BasePage;

import java.io.Serializable;

public class HospitalQuery extends BasePage implements Serializable {

    private static final long serialVersionUID = -6601431967101418911L;
    private String name;
    private String provinceCode;
    private String cityCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
