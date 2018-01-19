package com.yhyt.health.mapper;

import com.yhyt.health.model.DialogSign;
import org.springframework.stereotype.Repository;

@Repository
public interface DialogSignMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DialogSign record);

    int insertSelective(DialogSign record);

    DialogSign selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DialogSign record);

    int updateByPrimaryKey(DialogSign record);

    DialogSign selectByDeptIdAndPatientId(long deptId,long patientId);
}