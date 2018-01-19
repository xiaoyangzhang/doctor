package com.yhyt.health.mapper;

import com.yhyt.health.model.Hospital;
import com.yhyt.health.model.query.HospitalQuery;
import com.yhyt.health.model.vo.HospitalVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HospitalMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Hospital record);

    int insertSelective(Hospital record);

    Hospital selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Hospital record);

    int updateByPrimaryKey(Hospital record);

    List<HospitalVO> getHospitalListPage(HospitalQuery hospitalQuery);

    List<Hospital> queryHospitals(Map<String, Object> map);

    Hospital queryHospitalsBydeptid(Map<String, Object> map);

    List<Hospital> findHospitalList(Map<String, Object> params);
}