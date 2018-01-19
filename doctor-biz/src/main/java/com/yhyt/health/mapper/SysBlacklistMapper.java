package com.yhyt.health.mapper;

import com.yhyt.health.model.SysBlacklist;
import org.springframework.stereotype.Repository;

@Repository
public interface SysBlacklistMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysBlacklist record);

    int insertSelective(SysBlacklist record);

    SysBlacklist selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysBlacklist record);

    int updateByPrimaryKey(SysBlacklist record);
}