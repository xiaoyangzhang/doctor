package com.yhyt.health.mapper;

import com.yhyt.health.model.Dialog;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ImMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dialog record);

    int insertSelective(Dialog record);

    Dialog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dialog record);

    int updateByPrimaryKey(Dialog record);

    void addDialog(Map<String, Object> map);
}