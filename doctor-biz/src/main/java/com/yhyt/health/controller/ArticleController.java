package com.yhyt.health.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.model.Article;
import com.yhyt.health.model.ArticleDTO;
import com.yhyt.health.model.ArticleLogDTO;
import com.yhyt.health.model.query.ArticleQuery;
import com.yhyt.health.model.vo.ArticleVO;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {

    private static Logger logger  = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;
    @PostMapping("/article/add")
    public int addArticle(@RequestBody ArticleDTO articleDTO){

        return articleService.addArticle(articleDTO);
    }

    @PostMapping("/article/edit")
    public AppResult editArticle(@RequestBody ArticleDTO articleDTO){
        AppResult result = new AppResult();

        articleService.editArticle(articleDTO);
        return result;
    }

    @PatchMapping("/article/state/update")
    public AppResult updateArticleState(@RequestBody ArticleLogDTO articleLogDTO){
        AppResult result = new AppResult();
        articleService.updateArticleState(articleLogDTO);
        return result;
    }

    @GetMapping("/article/get/{id}/{action}")
    public ArticleDTO getArticleInfo(@PathVariable("id")Long id,@PathVariable("action")String action){

        return articleService.selectById(id,action);
    }

    @GetMapping("/article/list")
    public PageInfo<ArticleVO> queryArticleListPage(@RequestParam("quertStr")String queryStr){
        ArticleQuery articleQuery = JSON.parseObject(queryStr, ArticleQuery.class);
        return articleService.queryArticleListPage(articleQuery);
    }

    @PostMapping("/schedule/update/state")
    public AppResult updateStateForSchedule(){
        AppResult result = new AppResult();
        result.setResultCode(ResultCode.SUCCESS);
        articleService.updateStateForSchedule();
        return result;
    }

//    @GetMapping("/article/detail/{id}")
//    public Article getArticleDetail(@PathVariable("id")Long id){
//        return articleService.getArticleDetail(id);
//    }
}
