package com.yhyt.health.mapper;

import com.yhyt.health.model.SysConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    SysConfig selectByPrimaryKey(Long id);

    List<SysConfig> selectSysConfig(@Param("configType") String configType);

    List<SysConfig> selectSysConfigSalt(@Param("configType")String configType,@Param("salt1") String salt1, @Param("salt2") String salt2);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);
}