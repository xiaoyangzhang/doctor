package com.yhyt.health.service;

import com.yhyt.health.model.DeptPatientSign;
import com.yhyt.health.model.DialogAppointment;
import com.yhyt.health.model.DialogAppointmentTransfer;
import com.yhyt.health.result.AppResult;

import java.util.Date;
import java.util.Map;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月17日 下午4:14:15
 * 类说明
 */
public interface DialogService {

    int insertDialogAppointment(DialogAppointment dialogAppointment);

    DialogAppointment getDialogAppointmentById(Long id);

    AppResult confirmDialogAppointments(String token,DialogAppointment dialogAppointment);

    int insertDialogAppointmentTransfer(DialogAppointmentTransfer dialogAppointmentTransfer);

    DialogAppointmentTransfer getDialogAppointmentTransferById(Long id);

    AppResult addDialogSign(String token, DeptPatientSign deptPatientSign, long appointmentId);

    AppResult confirmDialogAppointmentTransfers(String token, DialogAppointmentTransfer appointmentTransfer);

    AppResult addDialogAppointments(String token, DialogAppointment appointment,String roomId);

    AppResult getDialogAppointment(String token, long id);

    //发起转诊
    AppResult addDialogAppointmentTransfers(String token, DialogAppointmentTransfer appointmentTransfer,String roomId);

    //获取当前科室下的预约医生
    AppResult getDoctorAppointments(String token, Long appointmentTime);

    //获取当前可以转诊的科室
    AppResult getDepartmentTransfers(String token);

    AppResult getDialogAppointmentTransfers(String token, DialogAppointmentTransfer appointmentTransfer);

    AppResult queryUndiagnoseList(String token, Map<String, Object> map,Integer pageIndex,Integer pageSize);

    AppResult queryUndiagnoseListDoctor(String token, Map<String, Object> map,Integer pageIndex,Integer pageSize);

    AppResult queryOverdueUndiagnoseList(String token, Map<String, Object> map,Integer pageIndex,Integer pageSize);

    AppResult querypatientRecords(String token, Long patientId,Integer pageIndex,Integer pageSize);

    AppResult dismissAssignDoctor(String token, Long appointmentId);

    AppResult queryAppointsCountByDate(String token,Long departmentId, Date beginTime, Date endTime, Long doctorId);

    AppResult searchyUndiagnoseAllpatient(String token, String key, Long departmentId);

    AppResult getMyPatientDialog(String token, Long patientId,String type,Integer pageIndex,Integer pageSize);

    AppResult getMyPatientTransfer(String token, Long patientId,String type,Integer pageIndex,Integer pageSize);

    AppResult overdueUndiagnose();
}
