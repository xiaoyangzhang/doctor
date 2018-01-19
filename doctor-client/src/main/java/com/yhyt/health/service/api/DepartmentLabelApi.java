package com.yhyt.health.service.api;
/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月25日 下午3:31:04
 * 类说明
 */

import com.yhyt.health.model.DeptLabel;
import com.yhyt.health.model.DeptPatientLable;
import com.yhyt.health.result.AppResult;

public interface DepartmentLabelApi {

    //查询科室下的标签
    AppResult queryDepartmentLabels(String token, DeptLabel deptLabel);

    //查询科室下患者的标签
    AppResult queryDepartmentPatientLabels(String token, DeptPatientLable deptPatientLable);

    //增加标签
    AppResult addDepartmentLabels(String token, DeptLabel deptLabel);

    //删除标签
    AppResult deleteDepartmentLabels(String token, DeptLabel deptLabel);

    //删除标签
    AppResult updateDepartmentLabels(String token, DeptLabel deptLabel);

    //修改科室下患者的标签
    AppResult updateDepartmentPatientLabels(String token, String ids, Long departmentId, Long patientId);
}
