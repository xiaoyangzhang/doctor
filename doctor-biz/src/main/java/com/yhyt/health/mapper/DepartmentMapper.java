package com.yhyt.health.mapper;

import com.yhyt.health.model.Department;
import com.yhyt.health.model.vo.DepartmentHospitalVO;
import com.yhyt.health.model.vo.DepartmentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<DepartmentVO> getDepartmentsByHospitalId(long hospitalId);

    DepartmentVO vewDepartmentRelateInfo(long id);
    DepartmentVO getDeptInfo(long id);

    List<Department> getDepartmentList(long id);

    List<DepartmentHospitalVO> selectDoctorDepartment(Map<String, Object> map);

    List<Department> getDeptListByName(String name);

    DepartmentHospitalVO selectByPrimaryKeyForApp(Long id);

    DepartmentHospitalVO selectDeptInfoForApp(Long id);

    List<DepartmentHospitalVO> getDepartmentTransfers(Map<String, Object> map);

    List<DepartmentVO> getDepartmentByDoctorId(Long doctorId);

    List<Department> findDepartmentList(Map<String, Object> params);
    List<Long> findDoctorIdsByDoctorId(@Param("doctorId")long doctorId);
}