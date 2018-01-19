package com.yhyt.health.service.api;

import com.yhyt.health.model.vo.app.ArticleTypeCountVo;
import com.yhyt.health.result.AppResult;

import java.util.List;

/**
 * 文章app端需要的service
 * @author wangzhan
 * @version 1.0
 * data 2017/12/06
 */
public interface ArticleApi {

  /**
   * 分页查询文章列表
   * @param token       token从头中获取
   * @param userId      用户id
   * @param type        类型 1培训 2课题
   * @param pageIndex   页码
   * @param pageSize    每页数量
   * @return
   */
  AppResult getArticleList(final String token,
                           final String userId,
                           final String type,
                           Integer pageIndex,
                           Integer pageSize);

  /**
   * 增加文章查看次数
   * @param id 文章id
   * @param count 默认＋1
   * @return
   */
  AppResult increaseViewingTimes(final Long id,
                                 final Long count);

  /**
   * 根据上架状态，获取不同文章的数据条数
   * @param state
   * @return
   */
  List<ArticleTypeCountVo> getArticleCount(final String state);

  /**
   * 根据id获取医略
   * @param id
   * @return
   */
  AppResult getArticleDetail(Long id);

}
