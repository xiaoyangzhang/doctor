package com.yhyt.health.result;

import com.yhyt.health.constant.ResultCode;

import java.io.Serializable;

public class DoctorResult<T> implements Serializable {

    private static final long serialVersionUID = -6581247166032567521L;
    private ResultCode resultCode;
    private T result;
    private String code;
    private String errorMsg;

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.code = resultCode.val();
        this.errorMsg = resultCode.msg();
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
