package com.yhyt.health.mapper;

import com.yhyt.health.model.DeptDoctor;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.vo.DoctorVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptDoctorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeptDoctor record);

    int insertSelective(DeptDoctor record);

    DeptDoctor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeptDoctor record);

    int updateByPrimaryKey(DeptDoctor record);

    List<Doctor> getDepartAdminByDepartId(long departId);

    List<DoctorVO> getDepartDoctorListPage(String doctorName, Long departId);

    List<DeptDoctor> getDepartDoctorList(long deptId);

    List<DeptDoctor> getDepartDoctorListByDoctorId(long doctorId);

    int addDepartDoctorBatch(@Param("list") List<DeptDoctor> deptDoctors);

    int deleteBatch(String idArr);

    int deleteByDoctorId(long doctorId);

    int deleteByDeptId(long deptId);

    List<DoctorVO> getDepartDoctorListPage(String doctorName);

    DeptDoctor getDeptDoctorByDepartIdAndDoctorId(Map<String, Object> map);

    int deleteDeptDoctorByDoctorIdAndDeptId(Map<String, Object> map);
    List<Long> selectByDeptID(Long deptId);
    Long selectByDoctorId(Long doctorId);

}