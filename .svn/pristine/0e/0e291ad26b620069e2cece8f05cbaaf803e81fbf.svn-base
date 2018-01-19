package com.yhyt.health.mapper;

import com.yhyt.health.model.DeptGroup;
import com.yhyt.health.model.vo.DeptGroupVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeptGroup record);

    int insertSelective(DeptGroup record);

    DeptGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeptGroup record);

    int updateByPrimaryKey(DeptGroup record);

    List<DeptGroupVo> queryDeptGroups(long departmentId);

    List<DeptGroup> queryDeptGroupsByMap(Map<String, Object> map);

    DeptGroup queryDefaultGroup(Long departementId);
}