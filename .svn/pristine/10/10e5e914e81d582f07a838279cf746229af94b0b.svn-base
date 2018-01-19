package com.yhyt.health.service;

import com.yhyt.health.model.DeptCooperation;
import com.yhyt.health.result.AppResult;

import java.util.List;
import java.util.Map;

/**
 * 作者: gsh
 *
 * @version 创建时间：2017年8月15日 下午4:09:39
 * 科室合作机构service
 */
public interface DeptCooperationService {

    AppResult addDeptCooperation(DeptCooperation deptCooperation, String token);

    AppResult getDeptCooperationReviewByid(String token, Long id);

    AppResult auditDeptCooperationReviews(String token, Long id, String state);

    List<DeptCooperation> getDeptCooperations(Map<String, Object> map);

    AppResult getCooperationsMessages(String token, Integer pageIndex, Integer pageSize);
}
