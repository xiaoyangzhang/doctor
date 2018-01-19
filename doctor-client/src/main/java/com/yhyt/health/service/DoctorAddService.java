package com.yhyt.health.service;

import com.yhyt.health.model.DialogAppointment;
import com.yhyt.health.result.AppResult;

public interface DoctorAddService {

    AppResult getAllTitle();

    AppResult updatepassword(String username, String originpassword, String newpassword);

    AppResult appointmentexpire(String doctorid, String departmentid);

    AppResult triagedoctorlist(String departmentid);

    AppResult triageconfirm(String token, DialogAppointment dialogAppointment);

    /**
     * 撤销分诊
     *
     * @param appointmentId
     * @return
     */
    AppResult dismissAssignDoctor(long appointmentId);

    AppResult appointmentdoctorlist(String token,String departmentid,Integer pageIndex,Integer pageSize);

    AppResult searchpatient(String key, String departmentid);

    AppResult searchdepartmentpatient(String searchdate, String departmentid);

    AppResult transferpatient(String departmentid,Integer pageIndex,Integer pageSize);

    /**
     * 诊后扫描签到申请列表
     *
     * @param departmentid
     * @return
     */
    AppResult dialogsignlist(String departmentid,Integer pageIndex,Integer pageSize);

    /**
     * 签到详情
     *
     * @param departmentid
     * @param patientid
     * @return
     */
    AppResult dialogsigndetail(String departmentid, String patientid);

    AppResult casedetail(String patientid, String caseid);
}
