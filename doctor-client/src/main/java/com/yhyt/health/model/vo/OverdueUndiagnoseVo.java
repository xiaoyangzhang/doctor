package com.yhyt.health.model.vo;

import java.util.Date;
import java.util.List;

/**
 * 过期预约
 *
 * @author gsh
 * @create 2017-09-01 21:19
 **/
public class OverdueUndiagnoseVo {

    private Long id;

    private Date appointmentTimeOver;

    List<AppointmentPatientVo> patientDetailVos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAppointmentTimeOver() {
        return appointmentTimeOver;
    }

    public void setAppointmentTimeOver(Date appointmentTimeOver) {
        this.appointmentTimeOver = appointmentTimeOver;
    }

    public List<AppointmentPatientVo> getPatientDetailVos() {
        return patientDetailVos;
    }

    public void setPatientDetailVos(List<AppointmentPatientVo> patientDetailVos) {
        this.patientDetailVos = patientDetailVos;
    }
}
