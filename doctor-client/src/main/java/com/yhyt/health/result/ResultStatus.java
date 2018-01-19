package com.yhyt.health.result;

public class ResultStatus {

    private String code;//返回代码
    private String message;//返回信息

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultStatus(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ResultStatus() {
        super();
    }

}
