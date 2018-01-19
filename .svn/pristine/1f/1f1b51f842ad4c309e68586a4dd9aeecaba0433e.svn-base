package com.yhyt.health.mapper;

import com.yhyt.health.model.PatientCasearchives;
import com.yhyt.health.model.vo.PatientDetailVo;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface PatientCasearchivesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PatientCasearchives record);

    int insertSelective(PatientCasearchives record);

    PatientCasearchives selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PatientCasearchives record);

    int updateByPrimaryKey(PatientCasearchives record);

    PatientDetailVo selectPatientCasearchives(Map<String, Object> map);

    PatientCasearchives selectByPatientAndDept(Long departmentId, Long patientId);
}