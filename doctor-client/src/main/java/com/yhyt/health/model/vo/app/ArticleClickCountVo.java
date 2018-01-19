package com.yhyt.health.model.vo.app;

import java.io.Serializable;

public class ArticleClickCountVo implements Serializable {

    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章类型 1-培训 2-课题
     */
    private String type;

    /**
     * 点击次数
     */
    private int clickCount;

    /**
     * 文章id
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * 文章类型 1-培训 2-课题
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * 点击次数
     * @return
     */
    public int getClickCount() {
        return clickCount;
    }

    /**
     * 文章id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 文章类型 1-培训 2-课题
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 点击次数
     * @param  clickCount
     */
    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
}
