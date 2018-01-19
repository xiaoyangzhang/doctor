package com.yhyt.health.mapper;


import java.util.List;
import java.util.Map;

import com.yhyt.health.model.Dictionary;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

	List<Dictionary> findDictionaryList(Map<String, Object> params);

	List<Dictionary> findDictionaryListdictName(Map<String, Object> map);
}