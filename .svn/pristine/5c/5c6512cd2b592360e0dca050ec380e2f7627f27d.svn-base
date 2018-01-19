package com.yhyt.health.model;

import com.yhyt.health.model.dto.ArticleAreaDTO;
import com.yhyt.health.model.dto.ArticleDepartmentDTO;

import java.io.Serializable;
import java.util.List;

public class ArticleDTO implements Serializable {

    private static final long serialVersionUID = -9017990299881906955L;
    private Article article;
    private List<ArticleDepartment> articleDepartments;
    private List<ArticleArea> articleAreas;

    private List<ArticleLog> articleLogs;

    private List<ArticleLogDTO> articleLogDTOS;
    private List<ArticleAreaDTO> articleAreaDTOS;
    private List<ArticleDepartmentDTO> articleDepartmentDTOS;
    private String publishDept;//发布对象
    private String publishArea;//发布地区

    private String operator;
    /*private Map<String,Object> bodyMap=new HashMap<>();

    public Map<String, Object> getBodyMap() {
        if (article != null && StringUtils.isNotEmpty(article.getBody()))
        return bodyMap;
    }
*/

    public List<ArticleLogDTO> getArticleLogDTOS() {
        return articleLogDTOS;
    }

    public void setArticleLogDTOS(List<ArticleLogDTO> articleLogDTOS) {
        this.articleLogDTOS = articleLogDTOS;
    }

    public List<ArticleAreaDTO> getArticleAreaDTOS() {
        return articleAreaDTOS;
    }

    public void setArticleAreaDTOS(List<ArticleAreaDTO> articleAreaDTOS) {
        this.articleAreaDTOS = articleAreaDTOS;
    }

    public List<ArticleDepartmentDTO> getArticleDepartmentDTOS() {
        return articleDepartmentDTOS;
    }

    public void setArticleDepartmentDTOS(List<ArticleDepartmentDTO> articleDepartmentDTOS) {
        this.articleDepartmentDTOS = articleDepartmentDTOS;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPublishDept() {
        return publishDept;
    }

    public void setPublishDept(String publishDept) {
        this.publishDept = publishDept;
    }

    public String getPublishArea() {
        return publishArea;
    }

    public void setPublishArea(String publishArea) {
        this.publishArea = publishArea;
    }

    public List<ArticleLog> getArticleLogs() {
        return articleLogs;
    }

    public void setArticleLogs(List<ArticleLog> articleLogs) {
        this.articleLogs = articleLogs;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<ArticleDepartment> getArticleDepartments() {
        return articleDepartments;
    }

    public void setArticleDepartments(List<ArticleDepartment> articleDepartments) {
        this.articleDepartments = articleDepartments;
    }

    public List<ArticleArea> getArticleAreas() {
        return articleAreas;
    }

    public void setArticleAreas(List<ArticleArea> articleAreas) {
        this.articleAreas = articleAreas;
    }
}
