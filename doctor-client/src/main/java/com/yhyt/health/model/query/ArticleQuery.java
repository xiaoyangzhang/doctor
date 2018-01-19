package com.yhyt.health.model.query;

import com.yhyt.health.page.BasePage;

import java.io.Serializable;

public class ArticleQuery extends BasePage implements Serializable{

    private static final long serialVersionUID = 9170307535556446531L;
    private String title;
    private Byte state;

    /**
     * 1：培训
     * 2：课题
     */
    private Byte type;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}
