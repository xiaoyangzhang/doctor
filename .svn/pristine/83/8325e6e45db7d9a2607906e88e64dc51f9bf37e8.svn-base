package com.yhyt.health.mapper;

import com.yhyt.health.model.DoctorReview;
import com.yhyt.health.model.query.DoctorReviewQuery;
import com.yhyt.health.model.vo.DoctorReviewVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DoctorReviewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DoctorReview record);

    int insertSelective(DoctorReview record);

    DoctorReview selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DoctorReview record);

    int updateByPrimaryKey(DoctorReview record);

    List<DoctorReviewVO> getDoctorReviewListPage(DoctorReviewQuery query);

    DoctorReviewVO viewDoctorApplyInfo(long id);

    int releaseTask(String operator);
    int releaseOneTask(long id);

    List<DoctorReview> getDoctorReviewList(Map<String, Object> map);
    DoctorReview getDoctorReviewingByDoctorId(@Param("doctorId") Long doctorId);
}