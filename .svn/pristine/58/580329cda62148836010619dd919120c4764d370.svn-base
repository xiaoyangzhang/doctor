package com.yhyt.health.mapper;

import com.yhyt.health.model.PatientDiagnoseRecords;
import com.yhyt.health.model.vo.PatientVisited;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientDiagnoseRecordsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PatientDiagnoseRecords record);

    int insertSelective(PatientDiagnoseRecords record);

    PatientDiagnoseRecords selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PatientDiagnoseRecords record);

    int updateByPrimaryKey(PatientDiagnoseRecords record);

    List<PatientVisited> getDepartPatientsVisited(long departId);

    List<PatientVisited> getDepartPatientsVisitedByPatient(@Param("patientId") Long patientId,@Param("departmentId")Long departmentId);

    Long getDepartPatientsVisitedCount(Long departmentId);
}