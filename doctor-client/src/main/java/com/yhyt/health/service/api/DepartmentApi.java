package com.yhyt.health.service.api;

import com.yhyt.health.model.DeptDoctor;
import com.yhyt.health.model.DeptGroup;
import com.yhyt.health.model.DeptGroupPatient;
import com.yhyt.health.result.AppResult;

public interface DepartmentApi {

    AppResult addDeptDoctor(String token,DeptDoctor deptDoctor);

    /**
     * 获取科室医生列表
     *
     * @param departId
     * @return
     */
    AppResult getDeptDoctorList(long departId,Integer pageIndex,Integer pageSize);

    /**
     * 更新就诊须知
     *
     * @param id
     * @param notice
     * @return
     */
    AppResult updateDeptNotice(long id, String notice);

    /**
     * 修改已分组患者的分组
     *
     * @param deptGroupPatient
     * @return
     */
    AppResult updatePatientGroup(String token, DeptGroupPatient deptGroupPatient);

    /**
     * 给患者分组
     *
     * @param deptGroupPatient
     * @return
     */
    AppResult addPatientGroup(String token,DeptGroupPatient deptGroupPatient);

    /**
     * 获取分组下的患者
     *
     * @param deptGroupId
     * @return
     */
    AppResult queryGroupPatients(long deptGroupId);

    /**
     * 获取分组列表
     *
     * @param deptId
     * @return
     */
    AppResult queryDeptGroups(String token, Long deptId);

    /**
     * 删除分组
     *
     * @param id
     * @return
     */
    AppResult deleteDeptGroups(long id);

    AppResult updateDeptGroup(DeptGroup deptGroup);

    AppResult addDeptGroup(String token, DeptGroup deptGroup);

    AppResult queryDepartmentById(String token,long id);

    /**
     * 获取医生科室列表
     *
     * @param doctorId
     * @return
     */
    AppResult getDoctorDeptList(String token, long doctorId);

    AppResult getDoctorByDepartmentIdForPatient(String token, Long departmentId,Integer pageIndex,Integer pageSize);

    /**
     * 转诊患者接诊/拒绝
     *
     * @param appointmentId
     * @param state
     * @return
     */
    AppResult receiveTransferPatient(String token,Long appointmentId, byte state);

    /**
     * 确认接诊/拒绝
     *
     * @param appointmentId
     * @return
     */
    AppResult confirmTransferPatient(String token,long appointmentId, byte state);

    AppResult addPatientDefaultGroup(Long departmentId, Long patientId);

    AppResult queryDeptGroupPatients(Long deptId);

    /**
     * 患者端根据部门id获取科室医生列表
     * @param roomId 房间id
     * @param type   @查询类型 医生端1 患者端2
     * @param userId 谁发起的查询（用户id）
     * @return
     */
    AppResult getDoctorsByRoomId(final String roomId,final String type,final String userId);
}
