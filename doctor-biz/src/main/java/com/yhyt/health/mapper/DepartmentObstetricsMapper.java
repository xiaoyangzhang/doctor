package com.yhyt.health.mapper;

import java.util.List;
import java.util.Map;

import com.yhyt.health.model.DepartmentObstetrics;
import com.yhyt.health.model.DepartmentObstetricsPatient;
import com.yhyt.health.model.PatientlObstetrics;
import com.yhyt.health.model.query.ObstetricsQuery;
import com.yhyt.health.model.vo.app.PatientObstetricInfoVo;
import org.apache.ibatis.annotations.Param;

public interface DepartmentObstetricsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DepartmentObstetrics record);

    int insertSelective(DepartmentObstetrics record);

    DepartmentObstetrics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DepartmentObstetrics record);

    int updateByPrimaryKey(DepartmentObstetrics record);

	List<PatientlObstetrics> getObstetricsList(ObstetricsQuery doctorDiseaseQuery);

	List<DepartmentObstetricsPatient> getObstetricsDetailList(Map ap);

    PatientObstetricInfoVo getPatientlObsterics(@Param("deptPatientSignId") Long deptPatientSignId);

	List<PatientlObstetrics> getObstetricsListExport(ObstetricsQuery doctorDiseaseQuery);

	List<DepartmentObstetricsPatient> getObstetricsDetailListMail(Map ap);

	List<PatientlObstetrics> getObstetricsListSingle(ObstetricsQuery doctorDiseaseQuery);
}