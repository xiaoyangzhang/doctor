package com.yhyt.health.mapper;

import com.yhyt.health.model.DoctorRequestpatient;
import com.yhyt.health.model.vo.app.PatientAppVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DoctorRequestpatientMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DoctorRequestpatient record);

    int insertSelective(DoctorRequestpatient record);

    DoctorRequestpatient selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DoctorRequestpatient record);

    int updateByPrimaryKey(DoctorRequestpatient record);

    /**
     * 获取工作站处理中患者列表
     *
     * @param deptId
     * @return
     */
    List<PatientAppVO> getDealingDiagnoseList(long deptId);

    /**
     * 获取工作站已拒绝患者列表
     *
     * @param deptId
     * @return
     */

    List<PatientAppVO> getRefusedDiagnoseList(long deptId);

    /**
     * 获取工作站新求诊患者列表
     *
     * @param deptId
     * @return
     */
    List<PatientAppVO> getNewRequestPatientList(long deptId);

    Long getNewRequestPatientListCount(Long departmentId);

    Long getDealingPatientListCount(Map<String,Object> map);

    DoctorRequestpatient selectByDeptAndPatient(@Param("departmentId")Long departmentId,@Param("patientId")Long patientId);
}