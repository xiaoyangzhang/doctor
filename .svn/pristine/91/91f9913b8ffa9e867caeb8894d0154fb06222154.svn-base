package com.yhyt.health.mapper;

import com.yhyt.health.model.Article;
import com.yhyt.health.model.query.ArticleQuery;
import com.yhyt.health.model.vo.ArticleAppVo;
import com.yhyt.health.model.vo.ArticleVO;
import com.yhyt.health.model.vo.app.ArticleClickCountVo;
import com.yhyt.health.model.vo.app.ArticleTypeCountVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yhyt.health.model.query.ArticleQuery;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List<ArticleVO> queryArticleListPage(ArticleQuery articleQuery);
    /**
     * 分页查询医师培训&参与课提列表
     * @return
     */
    List<ArticleAppVo> getArticleAppVoLista(Map<String,Object> map);

    /**
     * 更新文章查看次数
     * @param id
     * @param count
     * @param updateTime
     */
    void updateClickCountById(final @Param("id") Long id,
                              final @Param("count") Long count,
                              final @Param("updateTime") Date updateTime);

    /**
     * 根据id获取点击文章后的返回对象
     * @param id
     * @return
     */
    ArticleClickCountVo getArticleClickCountVo(final @Param("id") Long id);


    /**
     * 根据上架状态，获取不同文章的数据条数
     * @param state
     * @return
     */
    List<ArticleTypeCountVo> getArticleCount(final @Param("state") String state);


    /**
     * 上架
     * @param now
     * @return
     */
    int onForSchedule(@Param("now") String now);

    /**
     * 下架
     * @param now
     * @return
     */
    int offForSchedule(@Param("now") String now);

    /**
     * 过期
     * @param now
     * @return
     */
    int outForSchedule(@Param("now") String now);

    /**
     * 根据查询区域获取
     * type = 1 表示从发布范围全国全科室中获取最新的发布时间
     * type = 2 表示从发布范围全国当前科室中获取最新的发布时间
     * type = 3 表示从发布范围非全国全科室中获取最新的发布时间
     * type = 4 表示从发布范围全国当前科室中获取最新的发布时间
     * @return
     */
    List<Article> getTypeIssueTimeByType(final @Param("type") String type,
                                         final @Param("areaId") String areaId,
                                         final @Param("departmentId") Long departmentId);

    List<Article> getTypeIssueTimeByTypea(final @Param("type") String type,
                                         final @Param("areaId") String areaId,
                                         final @Param("departmentId") Long departmentId);

    List<Article> getTypeIssueTimeByTypeb(final @Param("type") String type,
                                         final @Param("areaId") String areaId,
                                         final @Param("departmentId") Long departmentId);

    List<Article> getTypeIssueTimeByTypec(final @Param("type") String type,
                                         final @Param("areaId") String areaId,
                                         final @Param("departmentId") Long departmentId);


}