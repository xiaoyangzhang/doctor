package com.yhyt.health.mapper;

import com.yhyt.health.model.ArticleDepartment;
import com.yhyt.health.model.dto.ArticleDepartmentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDepartmentMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByArticleId(@Param("articleId") Long articleId);

    int insert(ArticleDepartment record);

    int insertSelective(ArticleDepartment record);

    ArticleDepartment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleDepartment record);

    int updateByPrimaryKey(ArticleDepartment record);

    List<ArticleDepartmentDTO> selectByArticleId(@Param("articleId")Long articleId);
}