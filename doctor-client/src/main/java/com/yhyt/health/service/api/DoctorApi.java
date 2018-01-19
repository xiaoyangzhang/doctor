package com.yhyt.health.service.api;

import com.yhyt.health.model.*;
import com.yhyt.health.model.vo.app.PatientAppVO;
import com.yhyt.health.result.AppResult;

import java.util.List;

public interface DoctorApi {

    AppResult addDoctorDisease(DoctorDisease doctorDisease);

    AppResult getDoctorReviewInfo(long id);

    AppResult addDoctorReview(String token, DoctorReview doctorReview);

    AppResult updateDoctor(String token, Doctor doctor, String safeCode, String verificationCode);

    AppResult queryDoctorById(long id);

    /**
     * 发起预约
     *
     * @param dialogAppointment
     * @return
     */
    AppResult addAppointment(DialogAppointment dialogAppointment);

    AppResult updateAppointment(DialogAppointment dialogAppointment);

    /**
     * 获取预约列表
     *
     * @param dialogId
     * @return
     */
    AppResult queryAppointmentList(long dialogId);

    /**
     * 获取转诊列表
     *
     * @param dialogId
     * @return
     */
    AppResult queryAppointmentTransferList(long dialogId);

    AppResult createAppointmentTransfer(DialogAppointmentTransfer dialogAppointmentTransfer);

    AppResult updateAppointmentTransfer(DialogAppointmentTransfer dialogAppointmentTransfer);

    /**
     * 获取科室下医生列表
     *
     * @param deptId
     * @return
     */
    AppResult queryDoctorsByDeptId(long deptId);

    AppResult queryDeptOrDoctorOrDisease(String keyWord, int type);

    /**
     * 验证旧手机号的验证码
     *
     * @return
     */
    AppResult verifyOldPhone(String token, String username, String verificationCode);

    AppResult udpateDoctorStrogpoint(String token, Doctor doctor);

    AppResult queryDoctorByUserName(String token, Doctor doctor);

    /**
     * 获取工作站处理中患者列表
     *
     * @param deptId
     * @return
     */
    List<PatientAppVO> getDealingDiagnoseList(long deptId,Integer page,Integer pageSize);

    /**
     * 获取工作站已拒绝患者列表
     *
     * @param deptId
     * @return
     */
    List<PatientAppVO> getRefusedDiagnoseList(long deptId,Integer page,Integer pageSize);

    /**
     * 添加诊断记录
     *
     * @param patientDiagnoseRecords
     * @return
     */
    AppResult addDiagnoseRecords(PatientDiagnoseRecords patientDiagnoseRecords, long deptId, long patientId);

    AppResult logout(String token, Device device, Long userId);

    /**
     * 医生关注患者
     * @param token
     * @param doctorCare
     * @return
     */
    AppResult doctorCarePatient(String token, DoctorCare doctorCare);

    /**
     * 验证token
     * @param token
     * @return
     */
    String checkToken(String token);

//    SysUpgrade isUpgrade(String appVersion,Byte clientType,String appType);
}
