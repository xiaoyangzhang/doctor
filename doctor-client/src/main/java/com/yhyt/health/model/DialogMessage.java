package com.yhyt.health.model;

public class DialogMessage {

    private String type;
    /*
	 * 1-文本 2-图片 3-声音
	 */

    private String title;
	/*
	 * 通知
	 * 
	 */

    private String content;
	/*
	 * 预约成功
	 */

    private String result;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
	
	/*
	 * 预约成功    2图片类型result为图片地址，3声音类型result为声音获取地址
	 */
}
