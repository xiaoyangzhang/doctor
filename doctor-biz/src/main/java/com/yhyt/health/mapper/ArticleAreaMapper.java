package com.yhyt.health.mapper;

import com.yhyt.health.model.ArticleArea;
import com.yhyt.health.model.dto.ArticleAreaDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleAreaMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByArticleId(@Param("articleId") Long articleId);

    int insert(ArticleArea record);

    int insertSelective(ArticleArea record);

    ArticleArea selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleArea record);

    int updateByPrimaryKey(ArticleArea record);

    List<ArticleAreaDTO> selectByArticleId(@Param("articleId")Long articleId);
}