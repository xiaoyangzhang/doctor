package com.yhyt.health.model.vo.app;

import java.io.Serializable;

public class ArticleTypeCountVo implements Serializable{

    /**
     *  类型
     */
    private String type;

    /**
     *  数量
     */
    private Long count;

    /**
     * 类型
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *  数量
     * @return
     */
    public Long getCount() {
        return count;
    }

    /**
     * 类型
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *  数量
     * @param count
     */
    public void setCount(Long count) {
        this.count = count;
    }
}
