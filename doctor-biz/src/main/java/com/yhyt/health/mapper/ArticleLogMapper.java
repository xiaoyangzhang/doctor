package com.yhyt.health.mapper;

import com.yhyt.health.model.ArticleLog;
import com.yhyt.health.model.ArticleLogDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleLog record);

    int insertSelective(ArticleLog record);

    ArticleLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleLog record);

    int updateByPrimaryKey(ArticleLog record);

    List<ArticleLogDTO> selectByArticleId(@Param("articleId")Long articleId);

}