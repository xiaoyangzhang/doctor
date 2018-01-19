package com.yhyt.health.mapper;

import com.yhyt.health.model.DeptGroupPatient;
import com.yhyt.health.model.vo.DeptGroupPatinentsVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptGroupPatientMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeptGroupPatient record);

    int insertSelective(DeptGroupPatient record);

    DeptGroupPatient selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeptGroupPatient record);

    int updateByPrimaryKey(DeptGroupPatient record);

    List<DeptGroupPatient> queryGroupPatients(long deptGroupId);

    DeptGroupPatient getDeptGroupPatinent(Map<String, Object> map);

    int updateDeptGroupId(Long newDeptGroupId, Long oldDeptGroupId);

    List<DeptGroupPatinentsVo> getDeptGroupPatinents(Long deptGroupId);
}