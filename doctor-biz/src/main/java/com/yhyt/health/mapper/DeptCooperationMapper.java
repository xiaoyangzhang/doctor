package com.yhyt.health.mapper;

import com.yhyt.health.model.DeptCooperation;
import com.yhyt.health.model.vo.CooperationsMessage;
import com.yhyt.health.model.vo.DepartmentCooperationVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptCooperationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeptCooperation record);

    int insertSelective(DeptCooperation record);

    DeptCooperation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeptCooperation record);

    int updateByPrimaryKey(DeptCooperation record);

    List<DeptCooperation> getDeptCooperations(Map<String, Object> map);

    DepartmentCooperationVo getDeptCooperationInfo(Map<String, Object> map);

    DeptCooperation selectByDeptAndDeptCooper(Long deparmentIdOriginator, Long deparmentIdCooperation);

    List<CooperationsMessage> getCooperationsMessages(Map<String, Object> map);
}