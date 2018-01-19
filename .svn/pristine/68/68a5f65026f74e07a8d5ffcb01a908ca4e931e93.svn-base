package com.yhyt.health.mapper;

import com.yhyt.health.model.PatientDiagnosePics;
import com.yhyt.health.model.vo.app.PatientDiagnosePicsCaseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientDiagnosePicsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PatientDiagnosePics record);

    int insertSelective(PatientDiagnosePics record);

    PatientDiagnosePics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PatientDiagnosePics record);

    int updateByPrimaryKey(PatientDiagnosePics record);

    List<PatientDiagnosePics> selectBySignId(long signId);

    List<PatientDiagnosePics> selectByDepartmentIdPatientId(@Param("departmentId") Long departmentId,@Param("patientId") Long patientId);

    List<PatientDiagnosePicsCaseVo> selectByCaseIdAndType(String caseId, Byte type);

    List<PatientDiagnosePicsCaseVo> selectCasePicsByCaseIdAndType(String caseId, Byte type);

    List<PatientDiagnosePics> selectByDialogId(Long dialogId);
}