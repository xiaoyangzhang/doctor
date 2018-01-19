package com.yhyt.health.service;

import com.github.pagehelper.PageInfo;
import com.yhyt.health.model.Department;
import com.yhyt.health.model.DeptDisease;
import com.yhyt.health.model.DeptDoctor;
import com.yhyt.health.model.dto.DepartmentDTO;
import com.yhyt.health.model.query.DiseaseQuery;
import com.yhyt.health.model.vo.*;
import com.yhyt.health.result.AppResult;

import java.util.List;
import java.util.Map;


public interface DepartmentService {
    int addDepartment(DepartmentDTO departmentDTO);

    int updateDepartment(DepartmentDTO departmentDTO);

    List<DepartmentVO> getDepartmentsByHospitalId(long hospitalId);

    DepartmentVO viewDepartmentRelateInfo(long departmentId);
    DepartmentVO getDeptInfo(long departmentId);

    int relateDepartWithDisease(List<DeptDisease> list);

    /**
     * 批量添加医生到科室
     * @param list
     * @return
     */
    int relateDepartWithDoctor(List<DeptDoctor> list);

    PageInfo<DoctorVO> getDoctorListPage(String name, Integer page, Integer pageSize, long departId);

    PageInfo<DeptDiseaseVO> getDepartDiseaseListPage(DiseaseQuery diseaseQuery);

    int deleteByDoctorId(long doctorId);

    /**
     * 获取科室
     *
     * @param id
     * @return
     */
    DepartmentDTO selectDeptById(long id);

    AppResult addDisease2Dept(DeptDisease deptDisease);

    int deleteDeptDiseaseById(long id);

    List<DepartmentHospitalVO> selectDoctorDepartment(Map<String, Object> map);

    AppResult practiceDepartments(String token, Long doctorId);

    /**
     * 根据科室 id 查看本科室在本院的合作科室
     * @param deptId
     * @return
     */
//    List<DepartCoopDTO> getLocalHospitalByDeptId(long deptId);

    /**
     * 更新免费咨询服务
     *
     * @param id
     * @param isFree
     * @return
     */
    int updateDeptServiceState(long id, byte isFree,String operator);

    /**
     * 更新就诊须知
     *
     * @param id
     * @param notice
     * @return
     */
    int updateDeptNotice(Department department);

    int deleteDeptCategory(long categoryId, long deptId);

    AppResult updateDoctorDepartments(String token, Long departmentId);

    /**
     * 根据科室 id 查询其所属科室分类和分类对应的疾病总数
     */
    DeptCategoryVO getDeptCategoryByDeptId(long id);

    /**
     * 科室一键添加疾病
     * @param deptId
     * @return
     */
    int oneKeyAddDisease(long deptId);
}
