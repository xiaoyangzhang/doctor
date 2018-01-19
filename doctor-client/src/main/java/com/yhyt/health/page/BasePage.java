package com.yhyt.health.page;

import java.io.Serializable;

public class BasePage implements Serializable {

    private static final long serialVersionUID = -4039222818034859909L;
    private Integer pageIndex=1;
    private Integer pageSize=10;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
