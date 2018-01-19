package com.yhyt.health.mapper;

import com.yhyt.health.model.DoctorDisease;
import com.yhyt.health.model.query.DoctorDiseaseQuery;
import com.yhyt.health.model.vo.DoctorDiseaseVO;
import com.yhyt.health.model.vo.DoctorExtVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorDiseaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DoctorDisease record);

    int insertSelective(DoctorDisease record);

    DoctorDisease selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DoctorDisease record);

    int updateByPrimaryKey(DoctorDisease record);

    List<DoctorDiseaseVO> getDoctorDiseaseListPage(DoctorDiseaseQuery query);

    int addDoctorDiseaseBatch(@Param("list") List<DoctorDisease> doctorDiseases);

    int removeDoctorDiseaseByDiseaeId(long diseaseId);

    int removeDoctorDiseaseByDoctorId(long doctorId);

    int deleteBatch(String idArr);

    List<DoctorDiseaseVO> getDiseaseListByDoctorId(DoctorDiseaseQuery doctorDiseaseQuery);

    List<DoctorExtVO> getDoctorsByDiseaseId(long diseaseId);

    DoctorDisease selectByDoctorIdAndDiseaseId(@Param("doctorId") Long doctorId, @Param("diseaseId") long diseaseId);
    List<Long> selectDoctorDiseaseIdSByDoctorName(String name);
}