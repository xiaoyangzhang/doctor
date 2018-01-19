package com.yhyt.health.mapper;

import com.yhyt.health.model.PatientDialogCase;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface PatientDialogCaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PatientDialogCase record);

    int insertSelective(PatientDialogCase record);

    PatientDialogCase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PatientDialogCase record);

    int updateByPrimaryKey(PatientDialogCase record);

    PatientDialogCase selectPatientDialogCase(Map<String, Object> map);
}