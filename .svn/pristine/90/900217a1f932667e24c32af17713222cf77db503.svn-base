package com.yhyt.health.mapper;

import com.yhyt.health.model.DeptPatientLable;
import com.yhyt.health.model.vo.DeptPatientLableVo;

import java.util.List;

public interface DeptPatientLableMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeptPatientLable record);

    int insertSelective(DeptPatientLable record);

    DeptPatientLable selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeptPatientLable record);

    int updateByPrimaryKey(DeptPatientLable record);

    List<DeptPatientLableVo> queryDeptPatientLabels(DeptPatientLable deptPatientLable);

    int deleteDeptPatientByDepartmentId(long deptmentId);

    int insertDeptPatientLableBatch(List<DeptPatientLable> DeptPatientLables);
}