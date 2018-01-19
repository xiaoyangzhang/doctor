package com.yhyt.health.mapper;

import com.yhyt.health.model.DeptCooperationReview;
import com.yhyt.health.model.dto.DeptCooperationReviewDTO;
import com.yhyt.health.model.query.DepartCoopQuery;
import com.yhyt.health.model.vo.DeptCooperationReviewAppVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptCooperationReviewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeptCooperationReview record);

    int insertSelective(DeptCooperationReview record);

    DeptCooperationReview selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeptCooperationReview record);

    int updateByPrimaryKey(DeptCooperationReview record);

    /**合作科室查询-当前科室作为合作方
     * @param departId 当前科室 id
     * @return
     */
    List<DeptCooperationReviewDTO> getDepartCoopsByDepartId(DepartCoopQuery query);
    /**
     * 当前科室作为发起方
     */
    List<DeptCooperationReviewDTO> getDepartCoopsOriByDepartId(DepartCoopQuery query);

    /**
     * @param map
     * @return
     */
    int auditDeptCooperationReview(Map<String, Object> map);

    List<DeptCooperationReview> selectDeptCooperationReiew(Map<String, Object> map);

    List<DeptCooperationReviewAppVo> selectDeptCooperationReiewForApp(Map<String, Object> map);
}